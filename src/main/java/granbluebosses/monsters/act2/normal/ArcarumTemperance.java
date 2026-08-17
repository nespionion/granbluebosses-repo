package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import static granbluebosses.GranblueBosses.makeID;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.EmpowerEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.action.CleanseDebuffAction;
import granbluebosses.action.DispelBuffAction;
import granbluebosses.cards.rewards.Arcarum.ArcarumMoonCard;
import granbluebosses.cards.rewards.Arcarum.ArcarumTemperanceCard;
import granbluebosses.intents.enums.CustomIntentEnums;
import granbluebosses.relics.act2.ArcarumMoonReversed;
import granbluebosses.relics.act2.ArcarumTemperanceReversed;
import granbluebosses.util.MonsterUtils;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.config.ConfigMenu;
import granbluebosses.util.Sounds;

public class ArcarumTemperance extends CustomMonster {
    protected static final String MONSTER_NAME = "Temperance";
    public static final String MONSTER_ID = makeID("ArcarumTemperance");
    protected static final int MONSTER_MAX_HP = 114;
    protected static final int MONSTER_MAX_HP_A_19 = 114 + 14;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "ArcarumTemperance".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public int oathDmg;
    public int samekhDmg;
    public int costDmg;
    public int costHits;

    public boolean isDispelPrepare;

    public static int OATH_INDEX = 0;
    public static int SAMEKH_INDEX = 1;
    public static int COST_INDEX = 2;

    public ArcarumTemperance() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.oathDmg = 10;
            this.samekhDmg = 0;
            this.costDmg = 1;
            this.costHits = 14;
        } else {
            this.oathDmg = 4;
            this.samekhDmg = 0;
            this.costDmg = 1;
            this.costHits = 14;
        }

        this.isDispelPrepare = true;

        this.damage.add(new DamageInfo(this, this.oathDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.samekhDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.costDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    public void usePreBattleAction() {
        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_BATTLE_ARCARUM);
        }
        this.isDispelPrepare = true;

        super.usePreBattleAction();
    }

    public void useOathMove(){
        addToBot(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
        addToBot(new SFXAction(Sounds.TEMPERANCE_OATH));
        addToBot(new ShoutAction(this, DIALOG[0]));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(OATH_INDEX), AbstractGameAction.AttackEffect.NONE));

        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (AbstractDungeon.ascensionLevel >= 17){
                c.setCostForTurn(c.cost + 1);
            } else if (c.cost <= 1 && this.currentHealth * 2 <= this.maxHealth){
                c.setCostForTurn(c.cost + 1);
            } else if (c.cost > 1 && this.currentHealth * 2 > this.maxHealth){
                c.setCostForTurn(c.cost + 1);
            }
        }

        if (this.oathDmg < 10){
            this.oathDmg = 10;
        }

        this.damage.set(OATH_INDEX, new DamageInfo(this, this.oathDmg, DamageInfo.DamageType.NORMAL));
        this.damage.get(OATH_INDEX).applyPowers(this, AbstractDungeon.player);
    }

    public void useSamekh(){
        addToBot(new SFXAction(Sounds.TEMPERANCE_MAGIC));
        addToBot(new ShoutAction(this, DIALOG[1]));

        if (this.isDispelPrepare){
            addToBot(new VFXAction(new EmpowerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            addToBot(new DispelBuffAction(AbstractDungeon.player, this, AbstractDungeon.ascensionLevel >= 17));
        } else {
            addToBot(new VFXAction(new EmpowerEffect(this.hb.cX, this.hb.cY)));
            addToBot(new CleanseDebuffAction(this, this, AbstractDungeon.ascensionLevel >= 17));
        }
    }

    public void useCostMove(){
        addToBot(new VFXAction(new InflameEffect(AbstractDungeon.player)));
        addToBot(new SFXAction(Sounds.TEMPERANCE_COST));
        addToBot(new ShoutAction(this, DIALOG[2]));

        for (int i = 0; i < this.costHits; i++){
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(COST_INDEX), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useOathMove();
                break;
            case 1:
                this.useSamekh();
                break;
            case 2:
                this.useCostMove();
                break;
        }
        this.prepareIntent();
    }

    protected void prepareIntent() {
        if (this.currentHealth * 2 > this.maxHealth && AbstractDungeon.player.powers.stream().anyMatch(o -> o.type == AbstractPower.PowerType.BUFF)){
            this.isDispelPrepare = true;
            this.setMove(MOVES[1], (byte) 1, Intent.UNKNOWN);
            this.createIntent();
            addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.UNKNOWN));
            return;
        } else if (this.currentHealth * 2 <= this.maxHealth && this.powers.stream().anyMatch(o -> o.type == AbstractPower.PowerType.DEBUFF)){
            this.isDispelPrepare = false;
            this.setMove(MOVES[1], (byte) 1, Intent.UNKNOWN);
            this.createIntent();
            addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.UNKNOWN));
            return;
        }
        switch (this.nextMove) {
            case 0:
                this.setMove(MOVES[2], (byte) 2, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(COST_INDEX).base, this.costHits, true);
                this.createIntent();
                addToBot(new SetMoveAction(this, MOVES[2], (byte) 2, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(COST_INDEX).base, this.costHits, true));
                break;
            case 1:
            case 2:
                this.setMove(MOVES[0], (byte) 0, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(OATH_INDEX).base, 1, false);
                this.createIntent();
                addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(OATH_INDEX).base, 1, false));
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[0], (byte) 0, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(OATH_INDEX).base, 1, false);
            this.createIntent();
            addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(OATH_INDEX).base, 1, false));
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
            MonsterUtils.handleCardPlusRelicLinkedReward(new ArcarumTemperanceReversed(), new ArcarumTemperanceCard());
        }

        Act2Arcarum.resumeMainMusic();

        super.die();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}


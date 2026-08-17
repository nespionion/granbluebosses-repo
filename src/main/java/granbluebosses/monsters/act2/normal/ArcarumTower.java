package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import static granbluebosses.GranblueBosses.makeID;

import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.EmpowerEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.cards.rewards.Arcarum.ArcarumMoonCard;
import granbluebosses.cards.rewards.Arcarum.ArcarumTowerCard;
import granbluebosses.powers.aMonsters.GbfDOTPower;
import granbluebosses.relics.act2.ArcarumMoonReversed;
import granbluebosses.relics.act2.ArcarumTowerReversed;
import granbluebosses.util.MonsterUtils;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.config.ConfigMenu;
import granbluebosses.util.Sounds;

public class ArcarumTower extends CustomMonster {
    protected static final String MONSTER_NAME = "The Tower";
    public static final String MONSTER_ID = makeID("ArcarumTower");
    protected static final int MONSTER_MAX_HP = 160;
    protected static final int MONSTER_MAX_HP_A_19 = 160 + 16;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "ArcarumTower".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public int disasterDmg;
    public int disasterStacks;
    public int callousFistDmg;
    public int callousFistStacks;
    public float goddessLinkBlockMult;
    public int goddessLinkStacks;

    public ArcarumTower() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
            this.goddessLinkBlockMult = 1.0f;
        } else {
            this.setHp(MONSTER_MAX_HP);
            this.goddessLinkBlockMult = 0.5f;
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.disasterDmg = 16;
            this.disasterStacks = 2;
            this.callousFistDmg = 16;
            this.callousFistStacks = 2;
            this.goddessLinkStacks = 1;
        } else {
            this.disasterDmg = 16;
            this.disasterStacks = 3;
            this.callousFistDmg = 16;
            this.callousFistStacks = 2;
            this.goddessLinkStacks = 2;
        }

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);

        this.damage.add(new DamageInfo(this, this.callousFistDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.disasterDmg, DamageInfo.DamageType.HP_LOSS));
    }

    public void usePreBattleAction() {
        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_BATTLE_ARCARUM);
        }

        super.usePreBattleAction();
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useCallousFist();
                break;
            case 1:
                this.useGoddessLink();
                break;
            case 2:
                this.useDisaster();
                break;
        }
        this.prepareIntent();
    }

    public void useCallousFist (){
        addToBot(new AnimateSlowAttackAction(this));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (AbstractDungeon.ascensionLevel >= 17){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.callousFistStacks, true), this.callousFistStacks));
        }
    }

    public void useGoddessLink (){
        addToBot(new VFXAction(new EmpowerEffect(this.hb.cX, this.hb.cY)));

        addToBot(new GainBlockAction(this, (int)Math.max(this.currentHealth * this.goddessLinkBlockMult, 1.0f)));

        addToBot(new ApplyPowerAction(this, this, new VulnerablePower(this, this.goddessLinkStacks, true), this.goddessLinkStacks));

        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.goddessLinkStacks * 3), this.goddessLinkStacks * 3));

    }

    public void useDisaster (){

        addToBot(new AnimateShakeAction(this, 0.5f, 0.5f));

        addToBot(new DamageAction(this, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));

        addToBot(new ApplyPowerAction(this, this, new GbfDOTPower(this, this.disasterStacks), this.disasterStacks));

        if (AbstractDungeon.ascensionLevel < 17 || (this.hasPower(StrengthPower.POWER_ID) &&
                AbstractDungeon.monsterRng.randomBoolean(((float) this.getPower(StrengthPower.POWER_ID).amount / 10))
           ))
        {
            addToBot(new RemoveSpecificPowerAction(this, this, StrengthPower.POWER_ID));
        } else if (this.hasPower(StrengthPower.POWER_ID)){
            addToBot(new ReducePowerAction(this, this, StrengthPower.POWER_ID, 1));
        }

        this.disasterDmg *= (int) (AbstractDungeon.ascensionLevel >= 17 ? 1.5f : 2f);

        this.damage.set(1, new DamageInfo(this, this.disasterDmg, DamageInfo.DamageType.HP_LOSS));
        this.damage.get(1).applyPowers(this, AbstractDungeon.player);
    }

    protected void prepareIntent() {
        switch (this.nextMove) {
            case 0:
                this.setMove(MOVES[1], (byte) 1, Intent.DEFEND_BUFF);
                this.createIntent();
                addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.DEFEND_BUFF));
                break;
            case 1:
                this.setMove(MOVES[2], (byte) 2, Intent.UNKNOWN);
                this.createIntent();
                addToBot(new SetMoveAction(this, MOVES[2], (byte) 2, Intent.UNKNOWN));
                break;
            case 2:
                if (AbstractDungeon.ascensionLevel >= 17){
                    this.setMove(MOVES[0], (byte) 0, Intent.ATTACK_DEBUFF, this.damage.get(0).base, 1, false);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.ATTACK_DEBUFF, this.damage.get(0).base, 1, false));
                } else {
                    this.setMove(MOVES[0], (byte) 0, Intent.ATTACK, this.damage.get(0).base, 1, false);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.ATTACK, this.damage.get(0).base, 1, false));
                }
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            if (AbstractDungeon.ascensionLevel >= 17){
                this.setMove(MOVES[0], (byte) 0, Intent.ATTACK_DEBUFF, this.damage.get(0).base, 1, false);
                this.createIntent();
                addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.ATTACK_DEBUFF, this.damage.get(0).base, 1, false));
            } else {
                this.setMove(MOVES[0], (byte) 0, Intent.ATTACK, this.damage.get(0).base, 1, false);
                this.createIntent();
                addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.ATTACK, this.damage.get(0).base, 1, false));
            }
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
            MonsterUtils.handleCardPlusRelicLinkedReward(new ArcarumTowerReversed(), new ArcarumTowerCard());
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


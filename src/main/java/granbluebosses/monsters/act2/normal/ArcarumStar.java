package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Pummel;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.Arcarum.ArcarumStarsCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.relics.act2.ArcarumStarsReversed;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumStar extends CustomMonster {
    protected static final String MONSTER_NAME = "The Star";
    public static final String MONSTER_ID = makeID("ArcarumStar");
    protected static final int MONSTER_MAX_HP = 117;
    protected static final int MONSTER_MAX_HP_A_19 = 117 + 17;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "ArcarumStar".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected int stardeathDmg;
    protected int stardeathHits;
    protected int stardeathStacks;
    protected int deimosStrikeDmg;
    protected int deimosStrikeStacks;
    protected int heavenlyGloryDmg;
    protected int heavenlyGloryStacks;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static int STARDEATH_INDEX = 0;
    public static int DEIMOS_INDEX = 1;

    public ArcarumStar() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.stardeathDmg = 2;
            this.stardeathHits = 5;
            this.stardeathStacks = 2;
            this.deimosStrikeDmg = 2;
            this.deimosStrikeStacks = 9;
            this.heavenlyGloryDmg = 0;
            this.heavenlyGloryStacks = 99;
        } else {
            this.stardeathDmg = 2;
            this.stardeathHits = 4;
            this.stardeathStacks = 1;
            this.deimosStrikeDmg = 6;
            this.deimosStrikeStacks = 2;
            this.heavenlyGloryDmg = 0;
            this.heavenlyGloryStacks = 4;
        }

        this.damage.add(new DamageInfo(this, this.stardeathDmg, DamageInfo.DamageType.NORMAL));

        this.damage.add(new DamageInfo(this, this.deimosStrikeDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {

        if (ConfigMenu.enableDMCAMusic){CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_BATTLE_ARCARUM);
        }

        super.usePreBattleAction();
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useStardeath();
                break;
            case 1:
                this.useDeimosStrike();
                break;
            case 2:
                this.useHeavenlyGlory();
                break;
        }
        this.prepareIntent();
    }
    
    protected void useStardeath(){
        addToBot(new SFXAction(Sounds.STAR_DORADORA));
        addToBot(new ShoutAction(this, DIALOG[1]));

        for (int i = 0; i < this.stardeathHits - 1; i++){
            addToBot(new AnimateFastAttackAction(this));
            addToBot(new PummelDamageAction(AbstractDungeon.player, this.damage.get(STARDEATH_INDEX)));
        }

        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(STARDEATH_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        addToBot(new RemoveSpecificPowerAction(this, this, StrengthPower.POWER_ID));
    }

    protected void useDeimosStrike(){
        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new SFXAction(Sounds.STAR_WISH_GRANTED));
        addToBot(new ShoutAction(this, DIALOG[2]));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(DEIMOS_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.deimosStrikeStacks), this.deimosStrikeStacks));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VigorPower(AbstractDungeon.player, this.deimosStrikeStacks), this.deimosStrikeStacks));
    }

    protected void useHeavenlyGlory(){
        addToBot(new AnimateShakeAction(this, 0.3f, 0.3f));
        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.YELLOW, ShockWaveEffect.ShockWaveType.NORMAL)));
        addToBot(new SFXAction(Sounds.STAR_PRAYERS));
        addToBot(new ShoutAction(this, DIALOG[0]));

        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));

        if (AbstractDungeon.player.hasPower(FrailPower.POWER_ID)){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.heavenlyGloryStacks, true), this.heavenlyGloryStacks));
        } else {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.heavenlyGloryStacks, true), this.heavenlyGloryStacks));
        }
    }

    protected void prepareIntent() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove) {
            case 0:
                this.setMove(MOVES[2], (byte) 2, Intent.DEBUFF);
                break;
            case 1:
                addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.ATTACK, this.stardeathDmg, this.stardeathHits,true));
                break;
            case 2:
                addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.ATTACK_BUFF, this.deimosStrikeDmg, 1, false));
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 0:
            case 2:
                addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.ATTACK_BUFF, this.deimosStrikeDmg, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, MOVES[2], (byte) 2, Intent.ATTACK, this.stardeathDmg, this.stardeathHits,true));
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[2], (byte) 2, Intent.DEBUFF);
        }
    }

    @Override
    public void die() {
        addToBot(new SFXAction(Sounds.STAR_VICTORY));
        addToBot(new ShoutAction(this, DIALOG[3]));

        if (ConfigMenu.enableExtraRewards) {
            MonsterUtils.handleCardPlusRelicLinkedReward(new ArcarumStarsReversed(), new ArcarumStarsCard());
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


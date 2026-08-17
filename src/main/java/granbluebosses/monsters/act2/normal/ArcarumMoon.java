package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.Arcarum.ArcarumMoonCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.cards.MoonCrescent;
import granbluebosses.powers.cards.MoonFull;
import granbluebosses.powers.cards.MoonQuarter;
import granbluebosses.relics.act2.ArcarumMoonReversed;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumMoon extends CustomMonster {
    protected static final String MONSTER_NAME = "The Moon";
    public static final String MONSTER_ID = makeID("ArcarumMoon");
    protected static final int MONSTER_MAX_HP = 118;
    protected static final int MONSTER_MAX_HP_A_19 = 118 + 18;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "ArcarumMoon".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected int phaseOfTheMoonBlock;
    protected int kophDmg;
    protected int kophStacks;
    protected int phobosBlastDmg;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final int KOPH_INDEX = 0;
    public static final int PHOBOS_INDEX = 1;

    public ArcarumMoon() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);

            this.phaseOfTheMoonBlock = 0;
        } else {
            this.setHp(MONSTER_MAX_HP);

            this.phaseOfTheMoonBlock = 17;
        }


        if (AbstractDungeon.ascensionLevel >= 17){
            this.kophDmg = 4;
            this.kophStacks = 4;
            this.phobosBlastDmg = 14;
        } else {
            this.kophDmg = 3;
            this.kophStacks = 3;
            this.phobosBlastDmg = 10;
        }

        this.damage.add(new DamageInfo(this, this.kophDmg, DamageInfo.DamageType.NORMAL));

        this.damage.add(new DamageInfo(this, this.phobosBlastDmg, DamageInfo.DamageType.NORMAL));

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
                this.usePhaseOfTheMoon();
                break;
            case 1:
                this.useKoph();
                break;
            case 2:
                this.usePhobosBlast();
                break;
        }
        this.prepareIntent();
    }
    
    protected void usePhaseOfTheMoon(){
        addToBot(new AnimateShakeAction(this, 0.3f, 0.3f));
        addToBot(new SFXAction(Sounds.MOON_BEWITCH));
        addToBot(new ShoutAction(this, DIALOG[0]));

        if (AbstractDungeon.ascensionLevel >= 7){
            addToBot(new GainBlockAction(this, this.phaseOfTheMoonBlock));
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            addToBot(new ApplyPowerAction(this, this, new MoonQuarter(this)));
        } else {
            addToBot(new ApplyPowerAction(this, this, new MoonCrescent(this)));
        }

    }

    protected void useKoph(){
        addToBot(new SFXAction(Sounds.MOON_CRUMBLE));
        addToBot(new ShoutAction(this, DIALOG[1]));

        addToBot(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(KOPH_INDEX), AbstractGameAction.AttackEffect.NONE));

        if (this.hasPower(MoonCrescent.POWER_ID)){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.kophStacks, true)));
        } else if (this.hasPower(MoonQuarter.POWER_ID)){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, this.kophStacks, true)));
        } else if (this.hasPower(MoonFull.POWER_ID)){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.kophStacks, true)));
        }
    }

    protected void usePhobosBlast(){
        addToBot(new SFXAction(Sounds.MOON_PEOPLE));
        addToBot(new ShoutAction(this, DIALOG[2]));

        addToBot(new VFXAction(new LaserBeamEffect(this.hb.cX, this.hb.cY)));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(PHOBOS_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        
    }

    protected void prepareIntent() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(KOPH_INDEX).base, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, MOVES[2], (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(PHOBOS_INDEX).base, 1, false));
                break;
            case 2:
                addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.BUFF));
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 0:
            case 2:
                addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(KOPH_INDEX).base, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, MOVES[2], (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(PHOBOS_INDEX).base, 1, false));
                break;
        }
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        if (AbstractDungeon.ascensionLevel >= 17 && !this.isDeadOrEscaped() && !this.isEscaping && !this.isDying && !this.hasPower(MoonFull.POWER_ID) && !this.hasPower(MoonCrescent.POWER_ID) && !this.hasPower(MoonQuarter.POWER_ID)){
            addToBot(new ApplyPowerAction(this, this, new MoonCrescent(this)));
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[0], (byte) 0, Intent.BUFF);
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
            MonsterUtils.handleCardPlusRelicLinkedReward(new ArcarumMoonReversed(), new ArcarumMoonCard());
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


package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.Arcarum.ArcarumSunCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.intents.enums.CustomIntentEnums;
import granbluebosses.powers.aMonsters.GbfDOTPower;
import granbluebosses.relics.act2.ArcarumSunReversed;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumSun extends CustomMonster {
    protected static final String MONSTER_NAME = "The Sun";
    public static final String MONSTER_ID = makeID("ArcarumSun");
    protected static final int MONSTER_MAX_HP = 119;
    protected static final int MONSTER_MAX_HP_A_19 = 119 + 19;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "ArcarumSun".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected int reshStacks;
    protected int primalFlareStacks;
    protected int primalFlareBlock;
    protected int coronalEjectionDmg;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public ArcarumSun() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
            this.primalFlareBlock = 19;
        } else {
            this.setHp(MONSTER_MAX_HP);
            this.primalFlareBlock = 19;
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.reshStacks = 2;
            this.primalFlareStacks = 1;
            this.coronalEjectionDmg = 19;
        } else {
            this.reshStacks = 1;
            this.primalFlareStacks = 1;
            this.coronalEjectionDmg = 19;
        }

        this.damage.add(new DamageInfo(this, this.coronalEjectionDmg, DamageInfo.DamageType.NORMAL));

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
                this.useResh();
                break;
            case 1:
                this.usePrimalFlare();
                break;
            case 2:
                this.useCoronalEjection();
                break;
        }

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, 1), 1));
        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));

        this.prepareIntent();
    }

    protected void useResh(){
        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.RED, ShockWaveEffect.ShockWaveType.NORMAL)));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new GbfDOTPower(AbstractDungeon.player, this.reshStacks), this.reshStacks));

        addToBot(new ApplyPowerAction(this, this, new GbfDOTPower(this, this.reshStacks), this.reshStacks));
    }

    protected void usePrimalFlare(){

        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.RED, ShockWaveEffect.ShockWaveType.NORMAL)));


        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.primalFlareStacks, true), this.primalFlareStacks));

        if (this.primalFlareBlock > 0) addToBot(new GainBlockAction(this, this.primalFlareBlock));

        this.primalFlareBlock -= AbstractDungeon.ascensionLevel >= 9 ? 1 : 2;

    }

    protected void useCoronalEjection(){

        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.RED, ShockWaveEffect.ShockWaveType.NORMAL)));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));

        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, StrengthPower.POWER_ID));

        addToBot(new RemoveSpecificPowerAction(this, this, StrengthPower.POWER_ID));

    }

    protected void prepareIntent() {
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.DEFEND_DEBUFF));
                break;
            case 1:
                addToBot(new SetMoveAction(this, MOVES[2], (byte) 2, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(0).base, 1, false));
                break;
            case 2:
                addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.DEBUFF));
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[0], (byte) 0, Intent.DEBUFF);
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
            MonsterUtils.handleCardPlusRelicLinkedReward(new ArcarumSunReversed(), new ArcarumSunCard());
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


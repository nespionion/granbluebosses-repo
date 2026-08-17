package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import static granbluebosses.GranblueBosses.makeID;

import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.action.SetHPToSpecificAmountAction;
import granbluebosses.cards.rewards.Arcarum.ArcarumDevilCard;
import granbluebosses.cards.rewards.Arcarum.ArcarumMoonCard;
import granbluebosses.intents.enums.CustomIntentEnums;
import granbluebosses.powers.aMonsters.GbfDOTPower;
import granbluebosses.powers.common.ATKDown;
import granbluebosses.powers.common.DEFDown;
import granbluebosses.relics.act2.ArcarumDevilReversed;
import granbluebosses.relics.act2.ArcarumMoonReversed;
import granbluebosses.util.MonsterUtils;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.config.ConfigMenu;
import granbluebosses.util.Sounds;

public class ArcarumDevil extends CustomMonster {
    protected static final String MONSTER_NAME = "The Devil";
    public static final String MONSTER_ID = makeID("ArcarumDevil");
    protected static final int MONSTER_MAX_HP = 115;
    protected static final int MONSTER_MAX_HP_A_19 = 115 + 15;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "ArcarumDevil".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public static int POWER_PLANT_INDEX = 0;

    public int allFallDownStacks;
    public int powerPlantDmg;
    public int powerPlantStacks;
    public int hellfireStacks;

    public ArcarumDevil() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.allFallDownStacks = 15;
            this.powerPlantDmg = 15;
            this.powerPlantStacks = 10;
            this.hellfireStacks = 3;
        } else {
            this.allFallDownStacks = 10;
            this.powerPlantDmg = 15;
            this.powerPlantStacks = 5;
            this.hellfireStacks = 2;
        }

        this.damage.add(new DamageInfo(this, this.powerPlantDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
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
                this.useAllFallDown();
                break;
            case 1:
                this.usePowerPlant();
                break;
            case 2:
                this.useHellFire();
                break;
        }
        this.prepareIntent();
    }

    public void useAllFallDown(){
        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.RED, ShockWaveEffect.ShockWaveType.CHAOTIC)));
        addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ATKDown(AbstractDungeon.player, this.allFallDownStacks, true), this.allFallDownStacks));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new DEFDown(AbstractDungeon.player, this.allFallDownStacks, true), this.allFallDownStacks));
    }

    public void usePowerPlant(){
        addToBot(new VFXAction(new FireballEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(POWER_PLANT_INDEX), AbstractGameAction.AttackEffect.FIRE));

        if (AbstractDungeon.player.hasPower(GbfDOTPower.POWER_ID)){
            addToBot(new HealAction(this, this, this.powerPlantStacks));
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, GbfDOTPower.POWER_ID));
        } else if (AbstractDungeon.ascensionLevel >= 17){
            addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
        }
    }

    public void useHellFire(){
        addToBot(new VFXAction(new InflameEffect(AbstractDungeon.player)));
        addToBot(new AnimateShakeAction(this, 0.3f, 0.3f));
        addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new GbfDOTPower(AbstractDungeon.player, this.hellfireStacks), this.hellfireStacks));
    }

    protected void prepareIntent() {
        switch (this.nextMove) {
            case 0:
                if (AbstractDungeon.player.hasPower(GbfDOTPower.POWER_ID)){
                    this.setMove(MOVES[1], (byte) 1, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(POWER_PLANT_INDEX).base, 1, false);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(POWER_PLANT_INDEX).base, 1, false));
                } else {
                    this.setMove(MOVES[1], (byte) 1, Intent.ATTACK, this.damage.get(POWER_PLANT_INDEX).base, 1, false);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.ATTACK, this.damage.get(POWER_PLANT_INDEX).base, 1, false));
                }
                break;
            case 1:
                this.setMove(MOVES[2], (byte) 2, Intent.DEBUFF);
                this.createIntent();
                addToBot(new SetMoveAction(this, MOVES[2], (byte) 2, Intent.DEBUFF));
                break;
            case 2:
                this.setMove(MOVES[0], (byte) 0, Intent.DEBUFF);
                this.createIntent();
                addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.DEBUFF));
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[0], (byte) 0, Intent.DEBUFF);
            this.createIntent();
            addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.DEBUFF));
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
            MonsterUtils.handleCardPlusRelicLinkedReward(new ArcarumDevilReversed(), new ArcarumDevilCard());
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


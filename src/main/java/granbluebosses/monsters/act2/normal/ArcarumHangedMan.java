package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.Arcarum.ArcarumHangedManCard;
import granbluebosses.cards.rewards.Arcarum.ArcarumJusticeCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.common.ATKUp;
import granbluebosses.powers.common.PhalanxPower;
import granbluebosses.relics.act2.ArcarumHangedManReversed;
import granbluebosses.relics.act2.ArcarumJusticeReversed;
import granbluebosses.util.Sounds;
import granbluebosses.util.MonsterUtils;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumHangedMan extends CustomMonster {
    protected static final String MONSTER_NAME = "Hanged Man";
    public static final String MONSTER_ID = makeID("ArcarumHangedMan");
    protected static final int MONSTER_MAX_HP = 112;
    protected static final int MONSTER_MAX_HP_A_19 = 112 + 12;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "ArcarumHangedMan".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    private static int GOLDEN_DAWN_DMG = 12;
    private int eyeOfTheBlackstarBlock = 12;
    private int eyeOfTheBlackstarStacks = 15;
    private int bottomlessRavineDebuffStacks = 2;
    private int bottomlessRavineBuffStacks = 10;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public ArcarumHangedMan() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.eyeOfTheBlackstarStacks += 10;
            this.bottomlessRavineBuffStacks += 5;
        }

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);

        this.damage.add(new DamageInfo(this, GOLDEN_DAWN_DMG, DamageInfo.DamageType.NORMAL));
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
                this.useGoldenDawn();
                break;
            case 1:
                this.useEyeOfTheBlackstar();
                break;
            case 2:
                this.useGoldenDawn();
                break;
            case 3:
                this.useBottomlessRavine();
                break;
        }
        this.prepareIntent();
    }

    // Deal Damage and remove Debuffs
    public void useGoldenDawn(){
        addToBot(new ShoutAction(this, DIALOG[1], 1.0F, 2.0F));
        addToBot(new SFXAction(Sounds.HANGED_DIALOG_GOLDEN));

//        addToBot(new ShoutAction(this, DIALOG[0]));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new RemoveDebuffsAction(this));
        if (AbstractDungeon.player.hasPower(ConfusionPower.POWER_ID)){
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, ConfusionPower.POWER_ID));
        }
    }

    // Gain Block and buff
    public void useEyeOfTheBlackstar(){
        addToBot(new ShoutAction(this, DIALOG[2], 1.0F, 2.0F));
        addToBot(new SFXAction(Sounds.HANGED_DIALOG_BLACKSTAR));

//        addToBot(new ShoutAction(this, DIALOG[0]));
        addToBot(new GainBlockAction(this, this, eyeOfTheBlackstarBlock));
        addToBot(new ApplyPowerAction(this, this, new ATKUp(this, eyeOfTheBlackstarStacks), eyeOfTheBlackstarStacks));
    }

    // Debuff player and buff
    public void useBottomlessRavine(){
        addToBot(new ShoutAction(this, DIALOG[0], 1.0F, 2.0F));
        addToBot(new SFXAction(Sounds.HANGED_DIALOG_RAVINE));

        addToBot(new ApplyPowerAction(this, this, new PhalanxPower(this, this.bottomlessRavineBuffStacks), this.bottomlessRavineBuffStacks));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ConfusionPower(AbstractDungeon.player)));
    }

    protected void prepareIntent() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.DEFEND_BUFF));
//                this.useGoldenDawn();
                break;
            case 1:
                addToBot(new SetMoveAction(this, MOVES[0], (byte) 2, Intent.ATTACK, this.damage.get(0).base));
//                this.useEyeOfTheBlackstar();
                break;
            case 2:
                addToBot(new SetMoveAction(this, MOVES[2], (byte) 3, Intent.DEBUFF));
//                this.useGoldenDawn();
                break;
            case 3:
                addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.ATTACK, this.damage.get(0).base));
//                this.useBottomlessRavine();
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.DEFEND_BUFF));
//                this.useGoldenDawn();
                break;
            case 1:
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    addToBot(new SetMoveAction(this, MOVES[0], (byte) 2, Intent.ATTACK, this.damage.get(0).base));
                } else {
                    addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.ATTACK, this.damage.get(0).base));
                }
//                this.useEyeOfTheBlackstar();
                break;
            case 2:
                addToBot(new SetMoveAction(this, MOVES[2], (byte) 3, Intent.DEBUFF));
//                this.useGoldenDawn();
                break;
            case 3:
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    addToBot(new SetMoveAction(this, MOVES[0], (byte) 2, Intent.ATTACK, this.damage.get(0).base));
                } else {
                    addToBot(new SetMoveAction(this, MOVES[0], (byte) 0, Intent.ATTACK, this.damage.get(0).base));
                }
//                this.useBottomlessRavine();
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            if (AbstractDungeon.aiRng.randomBoolean()) {
                this.setMove(MOVES[0], (byte) 2, Intent.ATTACK, this.damage.get(0).base);
            } else {
                this.setMove(MOVES[0], (byte) 0, Intent.ATTACK, this.damage.get(0).base);
            }
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
            MonsterUtils.handleCardPlusRelicLinkedReward(new ArcarumHangedManReversed(), new ArcarumHangedManCard());
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

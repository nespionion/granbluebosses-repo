package granbluebosses.monsters.act1.normal;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import granbluebosses.GranblueBosses;
import granbluebosses.powers.common.PhalanxPower;

import static granbluebosses.GranblueBosses.makeID;

public class LumiFairy extends CustomMonster {
    protected static final String MONSTER_NAME = "Primalbit";
    public static final String MONSTER_ID = makeID("primalbit");
    protected static final int MONSTER_MAX_HP = 15;
    protected static final int MONSTER_MAX_HP_A_19 = 15 + 5;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 200.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = MONSTER_NAME.toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String PROPAGATION;
    public static final String EMYR_FLASH;
    public static final String PUNISHMENT;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    protected int forceFieldBlock = 6;
    protected final int phalanxPercent;
    protected final int debuffStacks;
    public int minionNum;

    public static final int IGNITE_INDEX = 0;

    public LumiFairy(float x, float y, int minionNum) {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);

        }
        if (AbstractDungeon.ascensionLevel >= 17){
            this.forceFieldBlock += 6;
            this.phalanxPercent = 100;
            this.debuffStacks = 2;
        } else {
            this.phalanxPercent = 50;
            this.debuffStacks = 1;
        }

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);

        this.minionNum = minionNum;
    }

    public LumiFairy(float x, float y) {
        this(x, y, 1);
    }

    public LumiFairy(int minionNum) {
        this(MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y, minionNum);
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        addToBot(new ApplyPowerAction(this, this, new MinionPower(this)));
        if (AbstractDungeon.ascensionLevel < 7 && this.minionNum > 1){
            this.die();
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.usePropagation();
                break;
            case 1:
                this.useEmyrFlash();
                break;
            case 2:
                this.usePunishment();
                break;
        }
        this.prepareIntent();
    }

    public void usePropagation(){
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (mo != this){
                addToBot(new GainBlockAction(mo, this.forceFieldBlock));
            }
        }
        addToBot(new ApplyPowerAction(this, this, new PhalanxPower(this, this.phalanxPercent), this.phalanxPercent));
    }

    public void useEmyrFlash(){
        addToBot(new GainBlockAction(this, this.forceFieldBlock));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.debuffStacks, true), this.debuffStacks));
        if (AbstractDungeon.ascensionLevel >= 17){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, this.debuffStacks, true), this.debuffStacks));
        }
    }

    public void usePunishment(){
        addToBot(new GainBlockAction(this, this.forceFieldBlock));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, this.debuffStacks, true), this.debuffStacks));
        if (AbstractDungeon.ascensionLevel >= 17){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.debuffStacks, true), this.debuffStacks));
        }
    }

    protected void prepareIntent() {
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, EMYR_FLASH, (byte)1, Intent.DEFEND_DEBUFF));
                break;
            case 1:
                addToBot(new SetMoveAction(this, PUNISHMENT, (byte)2, Intent.DEFEND_DEBUFF));
                break;
            case 2:
                addToBot(new SetMoveAction(this, PROPAGATION, (byte)0, Intent.DEFEND_BUFF));
                break;

        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            if (AbstractDungeon.ascensionLevel < 17 || this.minionNum == 1){
                this.setMove(EMYR_FLASH, (byte)1, Intent.DEFEND_DEBUFF);
            } else {
                this.setMove(PUNISHMENT, (byte)2, Intent.DEFEND_DEBUFF);
            }
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        PROPAGATION = MOVES[0];
        EMYR_FLASH = MOVES[1];
        PUNISHMENT = MOVES[2];

    }
}

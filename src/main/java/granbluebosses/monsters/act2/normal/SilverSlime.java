package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import granbluebosses.GranblueBosses;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.common.PhalanxPower;

import java.util.HashMap;

import static granbluebosses.GranblueBosses.makeID;

public class SilverSlime extends CustomMonster {
    protected static final String MONSTER_NAME = "SilverSlime";
    public static final String MONSTER_ID = makeID("SilverSlime");
    protected static final int MONSTER_MAX_HP = 10;
    protected static final int MONSTER_MAX_HP_A_19 = 10 + 5;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = MONSTER_NAME.toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    private HashMap<Integer, AbstractMonster> enemySlots = new HashMap();
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    protected final int tackleDmg;
    protected final int hideMoveBlock;
    protected final int cardRewards;
    private float spawnX = -100.0F;
    private static int minionsInCombat = 0;
    private static int SLIME_PHALANX = 90;

    public SilverSlime() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
            SLIME_PHALANX = 90;
        } else {
            this.setHp(MONSTER_MAX_HP);
            SLIME_PHALANX = 75;
        }



        if (AbstractDungeon.ascensionLevel >= 2){
            this.tackleDmg = 11;
        } else {
            this.tackleDmg = 9;
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.hideMoveBlock = 17;
        } else {
            this.hideMoveBlock = 11;
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.cardRewards = 2;
        } else {
            this.cardRewards = 3;
        }

        this.damage.add(new DamageInfo(this, this.tackleDmg, DamageInfo.DamageType.NORMAL));


        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {
        addToBot(new ApplyPowerAction(this, this, new PhalanxPower(this, SLIME_PHALANX), SLIME_PHALANX));
        super.usePreBattleAction();
        minionsInCombat = 0;
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useTackle();
                break;
            case 1:
                this.useHide();
                break;
            case 2:
                if (AbstractDungeon.ascensionLevel >= 17){
                    this.useBackup();
                }
                this.useBackup();
                break;
            case 3:
                this.useTackle();
                break;
            case 4:
                this.useHide();
                break;
            case 5:
                this.useBackup();
                break;
            case 6:
                this.useEscape();
                break;
        }
        this.prepareIntent();
    }

    public void useHide(){
        addToBot(new GainBlockAction(this, this.hideMoveBlock));
        addToBot(new ApplyPowerAction(this, this, new PhalanxPower(this, SLIME_PHALANX), SLIME_PHALANX));
    }

    public void useTackle(){
        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(this, this, new PhalanxPower(this, SLIME_PHALANX), SLIME_PHALANX));
    }

    public void useBackup(){
        AbstractMonster m = new BronzeSlime(this.spawnX + (-185.0F * 2.0f * (minionsInCombat + 1)), this.hb.cY -100);
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_SUMMON"));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(m, true));
        this.enemySlots.put(minionsInCombat + 1, m);
    }

    public void useEscape(){
        AbstractDungeon.actionManager.addToBottom(new TalkAction(this, "*squeak* *squeak*", 0.3F, 2.5F));
        AbstractDungeon.actionManager.addToBottom(new EscapeAction(this));
        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)6, Intent.ESCAPE));
    }

    protected void prepareIntent() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove){
            case 0:
                addToBot(new SetMoveAction(this, (byte) 1, Intent.DEFEND));
                break;
            case 1:
                addToBot(new SetMoveAction(this, (byte) 2, Intent.UNKNOWN));
                break;
            case 2:
                addToBot(new SetMoveAction(this, (byte) 3, Intent.ATTACK, this.damage.get(0).base, 1, false));
                break;
            case 3:
                addToBot(new SetMoveAction(this, (byte) 4, Intent.DEFEND));
                break;
            case 4:
                addToBot(new SetMoveAction(this, (byte) 5, Intent.UNKNOWN));
                break;
            case 5:
                addToBot(new SetMoveAction(this, (byte) 6, Intent.ESCAPE));
                break;
            case 6:
                addToBot(new SetMoveAction(this, (byte) 6, Intent.ESCAPE));
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove){
            case 0:
                addToBot(new SetMoveAction(this, (byte) 1, Intent.DEFEND));
                break;
            case 1:
                addToBot(new SetMoveAction(this, (byte) 2, Intent.UNKNOWN));
                break;
            case 2:
                addToBot(new SetMoveAction(this, (byte) 4, Intent.DEFEND));
                break;
            case 4:
                addToBot(new SetMoveAction(this, (byte) 6, Intent.ESCAPE));
                break;
            case 6:
                addToBot(new SetMoveAction(this, (byte) 6, Intent.ESCAPE));
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base, 1, false);
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards && !this.escaped) {

            for (int i = 0; i < this.cardRewards; i++){
                RewardItem reward = new RewardItem(AbstractDungeon.player.getCardColor());

                AbstractDungeon.getCurrRoom().rewards.add(reward);
            }

        }

//        EnemyDefeatCondition.addDefeatedEnemy(MONSTER_ID);

        super.die();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import granbluebosses.GranblueBosses;

import static granbluebosses.GranblueBosses.makeID;

public class BronzeSlime extends CustomMonster {
    protected static final String MONSTER_NAME = "BronzeSlime";
    public static final String MONSTER_ID = makeID("BronzeSlime");
    protected static final int MONSTER_MAX_HP = 35;
    protected static final int MONSTER_MAX_HP_A_19 = 35 + 3;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 120.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 120.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = MONSTER_NAME.toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = false;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    protected final int tackleDmg;
    protected final int hideMoveBlock;

    public BronzeSlime(float x, float y) {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, x, y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
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

        this.damage.add(new DamageInfo(this, this.tackleDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
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
        }
        this.prepareIntent();
    }

    public void useHide(){
        addToBot(new GainBlockAction(this, this.hideMoveBlock));
    }

    public void useTackle(){
        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    protected void prepareIntent() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        if (this.nextMove == 0) {
            addToBot(new SetMoveAction(this, (byte) 1, Intent.DEFEND));
        } else {
            addToBot(new SetMoveAction(this, (byte) 0, Intent.ATTACK, this.damage.get(0).base, 1, false));
        }
    }

    protected void prepareIntentA17() {
        if (this.nextMove == 0) {
            addToBot(new SetMoveAction(this, (byte) 1, Intent.DEFEND));
        } else {
            addToBot(new SetMoveAction(this, (byte) 0, Intent.ATTACK, this.damage.get(0).base, 1, false));
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove((byte) 1, Intent.DEFEND);
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}

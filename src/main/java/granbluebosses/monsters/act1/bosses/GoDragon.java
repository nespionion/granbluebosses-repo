package granbluebosses.monsters.act1.bosses;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.powers.common.PhalanxPower;

import static granbluebosses.GranblueBosses.makeID;

public class GoDragon extends CustomMonster {
    protected static final String MONSTER_NAME = "GoDragon";
    public static final String MONSTER_ID = makeID("GoDragon");
    protected static final int MONSTER_MAX_HP = 30;
    protected static final int MONSTER_MAX_HP_A_19 = 30 + 5;
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
    public static final String SWEEPING;
    public static final String IMMORTAL;
    public static final String HALLOW;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    protected int forceFieldBlock = 6;
    protected final int thrustDamage;
    protected final int hallowHits = 3;
    protected final int phalanxPercent;
    protected final int debuffStacks;
    public int minionNum;

    public static final int IGNITE_INDEX = 0;

    public GoDragon(float x, float y) {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);

        }
        if (AbstractDungeon.ascensionLevel >= 17){
            this.forceFieldBlock += 6;
            this.phalanxPercent = 50;
            this.debuffStacks = 1;
            this.thrustDamage = 5;
        } else {
            this.phalanxPercent = 30;
            this.debuffStacks = 1;
            this.thrustDamage = 3;
        }

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);

        this.damage.add(new DamageInfo(this, this.thrustDamage, DamageInfo.DamageType.NORMAL));

        this.minionNum = 1;
    }

    public GoDragon() {
        this(MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
    }

    public GoDragon(int minionNum) {
        this();
        this.minionNum = minionNum;
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
                this.useSweeping();
                break;
            case 1:
                this.useImmortal();
                break;
            case 2:
                this.useHallow();
                break;
        }
        this.prepareIntent();
    }

    public void useSweeping(){
        addToBot(new VFXAction(new SmallLaserEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));

        if (AbstractDungeon.ascensionLevel >= 17){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.debuffStacks, true), this.debuffStacks));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.debuffStacks, true), this.debuffStacks));
        } else if (AbstractDungeon.monsterRng.randomBoolean()){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.debuffStacks, true), this.debuffStacks));
        } else {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.debuffStacks, true), this.debuffStacks));
        }
    }

    public void useImmortal(){
        addToBot(new VFXAction(new SmallLaserEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));

        addToBot(new ApplyPowerAction(this, this, new PhalanxPower(this, this.phalanxPercent), this.phalanxPercent));
    }

    public void useHallow(){
        for (int i  = 0; i < this.hallowHits; i++){
            addToBot(new VFXAction(new SmallLaserEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
        }
    }

    protected void prepareIntent() {
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, IMMORTAL, (byte)1, Intent.ATTACK_DEFEND, this.damage.get(0).base, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, HALLOW, (byte)2, Intent.ATTACK, this.damage.get(0).base, this.hallowHits, true));
                break;
            case 2:
                addToBot(new SetMoveAction(this, SWEEPING, (byte)0, Intent.ATTACK_DEBUFF, this.damage.get(0).base, 1, false));
                break;

        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            if (AbstractDungeon.ascensionLevel < 17 || this.minionNum == 1){
                this.setMove(IMMORTAL, (byte)1, Intent.ATTACK_DEFEND, this.damage.get(0).base, 1, false);
            } else {
                this.setMove(HALLOW, (byte)2, Intent.ATTACK, this.damage.get(0).base, this.hallowHits, true);
            }
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        SWEEPING = MOVES[0];
        IMMORTAL = MOVES[1];
        HALLOW = MOVES[2];

    }
}

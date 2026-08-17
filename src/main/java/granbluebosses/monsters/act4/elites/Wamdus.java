package granbluebosses.monsters.act4.elites;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.config.ConfigMenu;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.incantedOmens.IncantedOmenDamage;
import granbluebosses.powers.incantedOmens.IncantedOmenHits;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class Wamdus extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "Wamdus";
    public static final String MONSTER_ID = makeID("Wamdus");
    protected static final int MONSTER_MAX_HP = 166;
    protected static final int MONSTER_MAX_HP_A_19 = 166 + 16;
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
    protected final int hydrozoaDmg;
    protected final int hydrozoaStacks;
    protected final int forcefulBiteDmg;
    protected final int forcefulBiteHits;
    protected final int innocenceDrainDmg;
    protected final int innocenceDrainHits;
    protected final int innocenceDrainStacks;
    private int omenNum = 0;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String HYDROZOA;
    public static final String FORCEFUL_BITE;
    public static final String INNOCENCE_DRAIN;
    public static final String[] DIALOG;
    public static final int HYDROZOA_INDEX = 0;
    public static final int FORCEFUL_BITE_INDEX = 1;
    public static final int INNOCENCE_DRAIN_INDEX = 2;

    public Wamdus() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        GranblueBosses.logger.info("Defining Wamdus Constructor");
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.forcefulBiteDmg = 4;
            this.forcefulBiteHits = 6;
            this.innocenceDrainDmg = 8;
            this.innocenceDrainHits = 3;
            this.innocenceDrainStacks = 3;
        } else {
            this.forcefulBiteDmg = 6;
            this.forcefulBiteHits = 4;
            this.innocenceDrainDmg = 12;
            this.innocenceDrainHits = 2;
            this.innocenceDrainStacks = 2;
        }

        if (AbstractDungeon.ascensionLevel >= 3){
            this.hydrozoaDmg = 20;
            this.hydrozoaStacks = 3;
        } else {
            this.hydrozoaDmg = 16;
            this.hydrozoaStacks = 2;
        }

        this.omenNum = 0;

        this.damage.add(new DamageInfo(this, this.hydrozoaDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.forcefulBiteDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.innocenceDrainDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_ELITE_WATER);
        } else {
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly("ELITE");
        }
        
        this.omenNum = HYDROZOA_INDEX;

        this.createIntent();
        this.applyOmen();

    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useHydrozoa();
                break;
            case 1:
                this.useForcefulBite();
                break;
            case 2:
                this.useInnocenceDrain();
                break;
            case 3:
                this.stunTurn();
                break;
        }
        this.prepareIntent();
    }

    private void stunTurn(){
        // This should do nothing, since the omen is applied in this.prepareIntent()
    }

    protected void useHydrozoa(){
        // TODO : Add voiceline
        // TODO : Add animation


        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(HYDROZOA_INDEX), AbstractGameAction.AttackEffect.FIRE));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.hydrozoaStacks, true), this.hydrozoaStacks));

        addToBot(new MakeTempCardInDiscardAction(new Dazed(), this.hydrozoaStacks));
    }

    protected void useForcefulBite(){


        for (int i = 0; i < this.forcefulBiteHits; i++){
            addToBot(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, Color.BLUE)));
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(FORCEFUL_BITE_INDEX), AbstractGameAction.AttackEffect.NONE));
        }
    }

    protected void useInnocenceDrain(){

        for (int i = 0; i < this.innocenceDrainHits; i++){
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(INNOCENCE_DRAIN_INDEX), AbstractGameAction.AttackEffect.FIRE));
        }

        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.innocenceDrainStacks), this.innocenceDrainStacks));
    }

    protected void prepareIntent() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, FORCEFUL_BITE, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(FORCEFUL_BITE_INDEX).base, this.forcefulBiteHits, true));
                this.omenNum = FORCEFUL_BITE_INDEX;
                this.createIntent();
                this.applyOmen();
                break;
            case 1:
                addToBot(new SetMoveAction(this, INNOCENCE_DRAIN, (byte) 2, Intent.ATTACK_BUFF, this.damage.get(INNOCENCE_DRAIN_INDEX).base, this.innocenceDrainHits, true));
                this.omenNum = INNOCENCE_DRAIN_INDEX;
                this.createIntent();
                this.applyOmen();
                break;
            case 2:
                addToBot(new SetMoveAction(this, HYDROZOA, (byte) 0, Intent.ATTACK_DEBUFF, this.damage.get(HYDROZOA_INDEX).base, 1, false));
                this.omenNum = HYDROZOA_INDEX;
                this.createIntent();
                this.applyOmen();
                break;
            case 3:
                addToBot(new SetMoveAction(this, HYDROZOA, (byte) 0, Intent.ATTACK_DEBUFF, this.damage.get(HYDROZOA_INDEX).base, 1, false));
                this.omenNum = HYDROZOA_INDEX;
                this.createIntent();
                this.applyOmen();
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 0:
                if (AbstractDungeon.aiRng.randomBoolean() && AbstractDungeon.aiRng.randomBoolean()) {
                    addToBot(new SetMoveAction(this, INNOCENCE_DRAIN, (byte) 2, Intent.ATTACK_BUFF, this.damage.get(INNOCENCE_DRAIN_INDEX).base, this.innocenceDrainHits, true));
                    this.omenNum = INNOCENCE_DRAIN_INDEX;
                    this.applyOmen();
                } else {
                    addToBot(new SetMoveAction(this, FORCEFUL_BITE, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(FORCEFUL_BITE_INDEX).base, this.forcefulBiteHits, true));
                    this.omenNum = FORCEFUL_BITE_INDEX;
                    this.applyOmen();
                }
                break;
            case 1:
                addToBot(new SetMoveAction(this, INNOCENCE_DRAIN, (byte) 2, Intent.ATTACK_BUFF, this.damage.get(INNOCENCE_DRAIN_INDEX).base, this.innocenceDrainHits, true));
                this.omenNum = INNOCENCE_DRAIN_INDEX;
                this.applyOmen();
                break;
            case 2:
                if (AbstractDungeon.aiRng.randomBoolean() && AbstractDungeon.aiRng.randomBoolean()) {
                    addToBot(new SetMoveAction(this, FORCEFUL_BITE, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(FORCEFUL_BITE_INDEX).base, this.forcefulBiteHits, true));
                    this.omenNum = FORCEFUL_BITE_INDEX;
                    this.applyOmen();
                } else {
                    addToBot(new SetMoveAction(this, HYDROZOA, (byte) 0, Intent.ATTACK_DEBUFF, this.damage.get(HYDROZOA_INDEX).base, 1, false));
                    this.omenNum = HYDROZOA_INDEX;
                    this.applyOmen();
                }
                break;
            case 3:
                if (AbstractDungeon.aiRng.randomBoolean() && AbstractDungeon.aiRng.randomBoolean() && AbstractDungeon.aiRng.randomBoolean()) {
                    addToBot(new SetMoveAction(this, INNOCENCE_DRAIN, (byte) 2, Intent.ATTACK_BUFF, this.damage.get(INNOCENCE_DRAIN_INDEX).base, this.innocenceDrainHits, true));
                    this.omenNum = INNOCENCE_DRAIN_INDEX;
                    this.applyOmen();
                } else {
                    addToBot(new SetMoveAction(this, HYDROZOA, (byte) 0, Intent.ATTACK_DEBUFF, this.damage.get(HYDROZOA_INDEX).base, 1, false));
                    this.omenNum = HYDROZOA_INDEX;
                    this.applyOmen();
                }
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(HYDROZOA, (byte) 0, Intent.ATTACK_DEBUFF, this.damage.get(HYDROZOA_INDEX).base, 1, true);
            this.omenNum = HYDROZOA_INDEX;
            this.applyOmen();
        }
    }

    @Override
    public void resolveOmen() {
        addToBot(new RemoveSpecificPowerAction(this, this, IncantedOmenDamage.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(this, this, IncantedOmenHits.POWER_ID));
        this.setMove((byte) 3, Intent.STUN);
        this.createIntent();
        addToBot(new SetMoveAction(this, (byte) 3, Intent.STUN));
    }

    @Override
    public void applyOmen() {
        switch (this.omenNum){
            case HYDROZOA_INDEX:
                addToBot(new ApplyPowerAction(this, this, new IncantedOmenHits(this, 1, 12, null)));
                break;
            case FORCEFUL_BITE_INDEX:
                addToBot(new ApplyPowerAction(this, this, new IncantedOmenHits(this, 5, 1, DamageInfo.DamageType.NORMAL)));
                break;
            case INNOCENCE_DRAIN_INDEX:
                addToBot(new ApplyPowerAction(this, this, new IncantedOmenDamage(this, 3, DamageInfo.DamageType.THORNS)));
                break;
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        HYDROZOA = MOVES[0];
        FORCEFUL_BITE = MOVES[1];
        INNOCENCE_DRAIN = MOVES[2];

    }
}

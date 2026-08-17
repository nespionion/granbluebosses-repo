package granbluebosses.monsters.act4.elites;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import granbluebosses.GranblueBosses;
import granbluebosses.config.ConfigMenu;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.incantedOmens.IncantedOmenDamage;
import granbluebosses.powers.incantedOmens.IncantedOmenHits;
import granbluebosses.powers.incantedOmens.IncantedOmenPowersApplied;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;


public class Wilnas extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "Wilnas";
    public static final String MONSTER_ID = makeID("Wilnas");
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
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String MAGMA_CHAMBER;
    public static final String HEAT_RAY;
    public static final String HADRON_SPHERE;
    private int magmaChamberDmg;
    private int magmaChamberStacks;
    private final float magmaChamberDmgMult;
    private final int hadronSphereDmg;
    private int hadronSphereHits;
    private int heatRayDmg;
    private int omenNum;
    public static final int HEAT_RAY_INDEX = 0;
    public static final int HADRON_SPHERE_INDEX = 1;
    public static final int MAGMA_CHAMBER_INDEX = 2;

    public Wilnas() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        GranblueBosses.logger.info("Defining Wilnas Constructor");
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }
        this.hadronSphereDmg = 6;
        this.magmaChamberDmg = 0;

        if (AbstractDungeon.ascensionLevel >= 17){
            this.heatRayDmg = 36;
            this.magmaChamberDmgMult = 0.7f;
        } else {
            this.heatRayDmg = 30;
            this.magmaChamberDmgMult = 0.5f;
        }

        if (AbstractDungeon.ascensionLevel >= 3){
            this.magmaChamberStacks = 3;
        } else {
            this.magmaChamberStacks = 2;
        }

        this.omenNum = 0;

        this.hadronSphereHits = 2;

        this.damage.add(new DamageInfo(this, this.heatRayDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.hadronSphereDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_ELITE_FIRE);
        } else {
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly("ELITE");
        }

    }

    public void prepareMagmaChamber(){
        if (AbstractDungeon.player != null){
            this.magmaChamberDmg = (int) Math.floor(AbstractDungeon.player.maxHealth * this.magmaChamberDmgMult);
        }

        this.damage.add(new DamageInfo(this, this.magmaChamberDmg, DamageInfo.DamageType.NORMAL));
        this.omenNum = MAGMA_CHAMBER_INDEX;

        this.applyOmen();

    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.stunTurn();
                break;
            case 1:
                this.useMagmaChamber();
                break;
            case 2:
                this.useHadronSphere();
                break;
            case 3:
                this.useHeatRay();
                break;
        }
        this.prepareIntent();
    }

    /*
    *   Magma Chamber : Deal dmg equal to 70% of player's HP and add 2 Burn to discard pile. Cancel by dealing 5 hits.
    *   Hadron Sphere : Deal 6 x X damage and Shuffle and add 1 Burn to the top the Draw Pile (X is the number of burns in draw, discard, and hand). Cancel by dealing 30 dmg.
    *   Heat Ray : Deal 30 dmg and add 2 Burns to the bottom of draw pile (). Cancel by applying 2 stacks of debuffs.
    */

    private void stunTurn(){
        // This should do nothing, since the omen is applied in this.prepareIntent()
    }

    private void useMagmaChamber(){
        // TODO : Add voiceline
        // TODO : Add animation


        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(MAGMA_CHAMBER_INDEX), AbstractGameAction.AttackEffect.FIRE));

        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.magmaChamberStacks), this.magmaChamberStacks));

        addToBot(new MakeTempCardInDiscardAction(new Burn(), 1));
    }

    private void useHadronSphere(){
        // TODO : Add voiceline
        // TODO : Add animation

        for (int i = 0; i < this.hadronSphereHits; i++){
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(HADRON_SPHERE_INDEX), AbstractGameAction.AttackEffect.FIRE));
        }

        addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, false, true, false));
    }

    private void useHeatRay(){
        // TODO : Add voiceline
        // TODO : Add animation

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(MAGMA_CHAMBER_INDEX), AbstractGameAction.AttackEffect.FIRE));

        addToBot(new MakeTempCardInDrawPileAction(new Burn(), 2, false, true, true));
    }

    protected void prepareIntent() {
        if (this.currentHealth * 2.5 == this.maxHealth){ // 40% HP

            addToBot(new SetMoveAction(this, MAGMA_CHAMBER, (byte) 1, Intent.ATTACK_BUFF, this.damage.get(MAGMA_CHAMBER_INDEX).base, 1, true));

            this.omenNum = MAGMA_CHAMBER_INDEX;

            this.applyOmen();

            return;
        }
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove) {
            case 0:
                this.setHadronSphereHits();
                addToBot(new SetMoveAction(this, (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(HADRON_SPHERE_INDEX).base, this.hadronSphereHits, true));
                this.omenNum = HADRON_SPHERE_INDEX;
                this.applyOmen();
                break;
            case 1:
                this.setHadronSphereHits();
                addToBot(new SetMoveAction(this, (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(HADRON_SPHERE_INDEX).base, this.hadronSphereHits, true));
                this.omenNum = HADRON_SPHERE_INDEX;
                this.applyOmen();
                break;
            case 2:
                addToBot(new SetMoveAction(this, (byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(HEAT_RAY_INDEX).base, 1, false));
                this.omenNum = HEAT_RAY_INDEX;
                this.applyOmen();
                break;
            case 3:
                if (AbstractDungeon.aiRng.randomBoolean() && AbstractDungeon.aiRng.randomBoolean()) {
                    addToBot(new SetMoveAction(this, MAGMA_CHAMBER, (byte) 1, Intent.ATTACK_BUFF, this.damage.get(MAGMA_CHAMBER_INDEX).base, 1, true));
                    this.omenNum = MAGMA_CHAMBER_INDEX;
                    this.applyOmen();
                } else {
                    this.setHadronSphereHits();
                    addToBot(new SetMoveAction(this, (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(HADRON_SPHERE_INDEX).base, this.hadronSphereHits, true));
                    this.omenNum = HADRON_SPHERE_INDEX;
                    this.applyOmen();
                    break;
                }
        }
    }

    protected void prepareIntentA17() {
        if (AbstractDungeon.aiRng.randomBoolean()) {
            this.setHadronSphereHits();
            addToBot(new SetMoveAction(this, (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(HADRON_SPHERE_INDEX).base, this.hadronSphereHits, true));
            this.omenNum = HADRON_SPHERE_INDEX;
            this.applyOmen();
        } else {
            if (AbstractDungeon.aiRng.randomBoolean()) {
                addToBot(new SetMoveAction(this, MAGMA_CHAMBER, (byte) 1, Intent.ATTACK_BUFF, this.damage.get(MAGMA_CHAMBER_INDEX).base, 1, true));
                this.omenNum = MAGMA_CHAMBER_INDEX;
                this.applyOmen();
            } else {
                addToBot(new SetMoveAction(this, (byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(HEAT_RAY_INDEX).base, 1, false));
                this.omenNum = HEAT_RAY_INDEX;
                this.applyOmen();
            }
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.prepareMagmaChamber();
            this.setMove(MAGMA_CHAMBER, (byte) 1, Intent.ATTACK_BUFF, this.damage.get(MAGMA_CHAMBER_INDEX).base, 1, true);
            this.omenNum = MAGMA_CHAMBER_INDEX;
            this.applyOmen();
        }
    }

    private void setHadronSphereHits(){
        this.hadronSphereHits = 0;

        if (AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
            return;
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cardID.equals(Burn.ID)){
                this.hadronSphereHits++;
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c.cardID.equals(Burn.ID)){
                this.hadronSphereHits++;
            }
        }

        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.cardID.equals(Burn.ID)){
                this.hadronSphereHits++;
            }
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.hadronSphereHits += 3;
        } else {
            this.hadronSphereHits += 2;
        }
    }

    @Override
    public void resolveOmen() {
        addToBot(new RemoveSpecificPowerAction(this, this, IncantedOmenDamage.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(this, this, IncantedOmenPowersApplied.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(this, this, IncantedOmenHits.POWER_ID));
        this.setMove((byte)0, Intent.STUN);
        this.createIntent();
        addToBot(new SetMoveAction(this, (byte) 0, Intent.STUN));
    }

    @Override
    public void applyOmen() {
        switch (this.omenNum){
            case MAGMA_CHAMBER_INDEX:
                addToBot(new ApplyPowerAction(this, this, new IncantedOmenHits(this, 5, 1, DamageInfo.DamageType.NORMAL)));
                break;
            case HADRON_SPHERE_INDEX:
                addToBot(new ApplyPowerAction(this, this, new IncantedOmenDamage(this, 30, null)));
                break;
            case HEAT_RAY_INDEX:
                addToBot(new ApplyPowerAction(this, this, new IncantedOmenPowersApplied(this, 2, AbstractPower.PowerType.DEBUFF, false, false)));
                break;
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        MAGMA_CHAMBER = MOVES[0];
        HEAT_RAY = MOVES[1];
        HADRON_SPHERE = MOVES[2];
    }
}


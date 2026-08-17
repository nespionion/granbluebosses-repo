package granbluebosses.monsters.act2.elites;

import VideoTheSpire.actions.RunTopLevelEffectAction;
import VideoTheSpire.effects.SimplePlayVideoEffect;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.BlurWaveNormalEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.odious.OdiousNihuyvintaeCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.intents.enums.CustomIntentEnums;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.aMonsters.ColossalBodyPower;
import granbluebosses.powers.aMonsters.act2.TorrentOfLifePower;
import granbluebosses.powers.aMonsters.act2.UniversalUpwellingPower;
import granbluebosses.powers.aMonsters.act2.UnsealingPower;
import granbluebosses.powers.incantedOmens.AbstractIncantedOmen;
import granbluebosses.powers.incantedOmens.IncantedOmenCardPlayed;
import granbluebosses.powers.incantedOmens.IncantedOmenHits;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.odious.OdiousTerrorbow;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;
import static granbluebosses.GranblueBosses.videoPath;

public class OdiousMortality extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "Nihuyvintae";
    public static final String MONSTER_ID = makeID("OdiousMortality");
    protected static final int MONSTER_MAX_HP = 198;
    protected static final int MONSTER_MAX_HP_A_19 = 198 + 58;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "OdiousMortality".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected float OMEN_MULT = 1.6f;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public int redundragoOmenAmt;
    public int redundragoOmenDmg;
    public int annraeterniaOmenAmt;

    public int redundragoDmg;
    public int redundragoBlock;
    public int redundragoStacks;

    public int vendarumDmg;
    public int vendarumStacks;

    public int annraeterniaDmg;
    public int annraeterniaDmgIncrease;
    public int annraeterniaTotalDmg = 0;

    public static final int REDUNDRAGO_INDEX = 0;
    public static final int VENDARUM_INDEX = 1;
    public static final int ANNRAETERNIA_INDEX = 2;


    public OdiousMortality() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);



        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(MONSTER_MAX_HP_A_19);
            this.redundragoBlock = 4;
        } else {
            this.setHp(MONSTER_MAX_HP);
            this.redundragoBlock = 2;
        }

        this.redundragoOmenDmg = 10;
        if (AbstractDungeon.ascensionLevel >= 18){
            this.redundragoOmenAmt = 3;
            this.annraeterniaOmenAmt = 5;

            this.redundragoDmg = 27;
            this.redundragoStacks = 2;

            this.vendarumDmg = 10;
            this.vendarumStacks = 2;

            this.annraeterniaDmg = 15;
            this.annraeterniaDmgIncrease = 7;

        } else {
            this.redundragoOmenAmt = 2;
            this.annraeterniaOmenAmt = 4;

            this.redundragoDmg = 22;
            this.redundragoStacks = 2;

            this.vendarumDmg = 10;
            this.vendarumStacks = 1;

            this.annraeterniaDmg = 15;
            this.annraeterniaDmgIncrease = 5;

        }

        this.damage.add(new DamageInfo(this, this.redundragoDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.vendarumDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.annraeterniaTotalDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {

        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_ELITE_WATER);
        } else {
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly("ELITE");
        }

        if (AbstractDungeon.ascensionLevel >= 18){
            StanceOmen omen = new StanceOmen(this);
            omen.setUpOmenByHp(OMEN_MULT);
            addToBot(new ApplyPowerAction(this, this, omen));
        }

        addToBot(new ApplyPowerAction(this, this, new ColossalBodyPower(this)));

        super.usePreBattleAction();
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useStunTurn();
                break;
            case 1:
                this.useRedundrago();
                break;
            case 2:
                this.useVendarum();
                break;
            case 3:
                this.useAnnraeternia();
                break;
        }
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenHits.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenCardPlayed.POWER_ID));

        this.prepareIntent();
        this.createIntent();

        this.createIntent();
        this.applyOmen();
    }

    public void useStunTurn(){
        // Does nothing. It's here for consistency
    }

    public void useRedundrago(){
//        Deal damage + Gain Block + Gain Universal Upwelling for each hit missing from cancel condition
        int powerCount = Math.toIntExact(this.powers.stream().filter(o -> o.type == AbstractPower.PowerType.BUFF && !o.ID.equals(TorrentOfLifePower.POWER_ID)).count());

        addToBot(new AnimateShakeAction(this, 0.7f, 0.7f));
        addToBot(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(REDUNDRAGO_INDEX), AbstractGameAction.AttackEffect.NONE));

        addToBot(new GainBlockAction(this, this.redundragoBlock + (this.redundragoBlock * powerCount)));

        int upwellingStacks = this.hasPower(IncantedOmenHits.POWER_ID) ? this.getPower(IncantedOmenHits.POWER_ID).amount : 1;
        addToBot(new ApplyPowerAction(this, this, new UniversalUpwellingPower(this, upwellingStacks), upwellingStacks));

    }

    public void useVendarum(){
        // Deal damage + Apply Frail (and Weak) + Gain Torrent of Life

        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.BLUE, ShockWaveEffect.ShockWaveType.CHAOTIC)));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(VENDARUM_INDEX), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.vendarumStacks, true)));

        if (AbstractDungeon.ascensionLevel >= 18){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.vendarumStacks, true)));
        }

        addToBot(new ApplyPowerAction(this, this, new TorrentOfLifePower(this)));


    }

    public void useAnnraeternia(){
        /*
        On Prepare from Trigger at 60% HP: Gain "Torrent of Life"
        If Torrent of Life not active: Deal damage based on number of buffs + Convert all buffs into Strength
        If Torrent of Life is active: Instakill
        Omen: Play 5 Attacks or Remove Torrent of Life (AOE counts as 2)
        Stun on cancel
        */

        addToBot(new SFXAction(Sounds.SFX_ACT2_ELITE_WATER));
        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("odiousmortality/OdiousWaterAnimNoSFX.webm"))));

        if (this.hasPower(TorrentOfLifePower.POWER_ID)){
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 99999));
        } else {
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(ANNRAETERNIA_INDEX), AbstractGameAction.AttackEffect.POISON));

            int powerCount = 1;
            for (AbstractPower pow : this.powers){
                if (pow.type == AbstractPower.PowerType.BUFF && !pow.ID.equals(TorrentOfLifePower.POWER_ID)){
                    addToBot(new RemoveSpecificPowerAction(this, this, pow));
                    powerCount++;
                }
            }

            addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, powerCount), powerCount));

            addToBot(new RemoveSpecificPowerAction(this, this, TorrentOfLifePower.POWER_ID));
        }
    }

    protected void prepareIntent() {
        int powerCount = Math.toIntExact(this.powers.stream().filter(o -> o.type == AbstractPower.PowerType.BUFF && !o.ID.equals(TorrentOfLifePower.POWER_ID)).count());

        GranblueBosses.logger.info("Buff Count = " + powerCount);

        if (
                (AbstractDungeon.player != null && powerCount >= 3) ||
                (AbstractDungeon.ascensionLevel >= 18 && this.currentHealth * this.OMEN_MULT <= this.maxHealth && this.trigger)){

            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));
            this.trigger = false;

            if (!this.hasPower(StunMonsterPower.POWER_ID) && this.nextMove != 0){
                if (!this.hasPower(TorrentOfLifePower.POWER_ID)){
                    this.annraeterniaTotalDmg = this.annraeterniaDmg + (this.annraeterniaDmgIncrease * powerCount);
                    // Prepare Annraeternia (5 Buffs)

                    this.damage.set(ANNRAETERNIA_INDEX, new DamageInfo(this, this.annraeterniaTotalDmg, DamageInfo.DamageType.NORMAL));
                    this.damage.get(ANNRAETERNIA_INDEX).applyPowers(this, AbstractDungeon.player);

                    this.setMove(MOVES[ANNRAETERNIA_INDEX], (byte) 3, Intent.ATTACK_BUFF, this.damage.get(ANNRAETERNIA_INDEX).base);
                    addToBot(new SetMoveAction(this, MOVES[ANNRAETERNIA_INDEX], (byte) 3, Intent.ATTACK_BUFF, this.damage.get(ANNRAETERNIA_INDEX).base));
                } else {
                    this.setMove(MOVES[ANNRAETERNIA_INDEX], (byte) 3, CustomIntentEnums.INSTAKILL);
                    addToBot(new SetMoveAction(this, MOVES[ANNRAETERNIA_INDEX], (byte) 3, CustomIntentEnums.INSTAKILL));
                }

                this.createIntent();
               this.applyOmen();
                OmenUtils.onPrepOmenSFX(this);
                return;
            } else {
                OmenUtils.onCancelOmenSFX(this);
            }
        }

        if (AbstractDungeon.ascensionLevel >= 18) {
            this.prepareIntentA17();
            return;
        }

        switch (this.nextMove){
            case 0:
                this.setMove(MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_BUFF, this.damage.get(VENDARUM_INDEX).base);
                addToBot(new SetMoveAction(this, MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(VENDARUM_INDEX).base));
                break;
            case 1:
                this.setMove(MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_BUFF, this.damage.get(VENDARUM_INDEX).base);
                addToBot(new SetMoveAction(this, MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(VENDARUM_INDEX).base));
                break;
            case 2:
                this.setMove(MOVES[REDUNDRAGO_INDEX], (byte) 1, Intent.ATTACK_BUFF, this.damage.get(REDUNDRAGO_INDEX).base);
                addToBot(new SetMoveAction(this, MOVES[REDUNDRAGO_INDEX], (byte) 1, Intent.ATTACK_BUFF, this.damage.get(REDUNDRAGO_INDEX).base));
                break;
            case 3:
                this.setMove(MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_BUFF, this.damage.get(VENDARUM_INDEX).base);
                addToBot(new SetMoveAction(this, MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(VENDARUM_INDEX).base));
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove){
            case 0:
                this.prepareRandomCommonMove();
                break;
            case 1:
                this.setMove(MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_BUFF, this.damage.get(VENDARUM_INDEX).base);
                addToBot(new SetMoveAction(this, MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(VENDARUM_INDEX).base));
                break;
            case 2:
                this.setMove(MOVES[REDUNDRAGO_INDEX], (byte) 1, Intent.ATTACK_BUFF, this.damage.get(REDUNDRAGO_INDEX).base);
                addToBot(new SetMoveAction(this, MOVES[REDUNDRAGO_INDEX], (byte) 1, Intent.ATTACK_BUFF, this.damage.get(REDUNDRAGO_INDEX).base));
                break;
            case 3:
                this.prepareRandomCommonMove();
                break;
        }
    }

    protected void prepareRandomCommonMove(){
        if (AbstractDungeon.aiRng.randomBoolean()) {
            // Prapare Redundrago
            this.setMove(MOVES[REDUNDRAGO_INDEX], (byte) 1, Intent.ATTACK_BUFF, this.damage.get(REDUNDRAGO_INDEX).base);
            addToBot(new SetMoveAction(this, MOVES[REDUNDRAGO_INDEX], (byte) 1, Intent.ATTACK_BUFF, this.damage.get(REDUNDRAGO_INDEX).base));
            this.createIntent();
        } else {
            // Prapare Vendarum
            this.setMove(MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_BUFF, this.damage.get(VENDARUM_INDEX).base);
            addToBot(new SetMoveAction(this, MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(VENDARUM_INDEX).base));
            this.createIntent();
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_BUFF, this.damage.get(VENDARUM_INDEX).base);
            addToBot(new SetMoveAction(this, MOVES[VENDARUM_INDEX], (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(VENDARUM_INDEX).base));
            this.createIntent();
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }

    @Override
    public void resolveOmen() {
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenHits.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenCardPlayed.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, TorrentOfLifePower.POWER_ID));

        this.setMove((byte)0, Intent.STUN);
        this.createIntent();
        addToTop(new SetMoveAction(this, (byte)0, Intent.STUN));

        OmenUtils.onCancelOmenSFX(this);
    }



    @Override
    public void applyOmen() {
        AbstractIncantedOmen omen = null;
        switch (this.nextMove) {
            // Redundrago (1) Omen - Deal 10 damage with a single hit 2 (3) times
            // Annraeternia (3) Omen - Play 5 Attacks or Remove Torrent of Life
            case 1:
                omen = new IncantedOmenHits(this, this.redundragoOmenAmt, this.redundragoOmenDmg);
                break;
            case 3:
                omen = new IncantedOmenCardPlayed(this, this.annraeterniaOmenAmt, null, AbstractCard.CardType.ATTACK, null, -1);
                break;
            default:
                break;
        }
        if (omen != null) addToBot(new ApplyPowerAction(this, this, omen));

    }

    @Override
    public void die() {
        super.die();
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new TridentOfBrahman(), new AnimaShiva());
            MonsterUtils.handleCardPlusRelicLinkedReward(new OdiousTerrorbow(), new OdiousNihuyvintaeCard());
        }
        Act2Arcarum.resumeMainMusic();
        super.die();
    }
}


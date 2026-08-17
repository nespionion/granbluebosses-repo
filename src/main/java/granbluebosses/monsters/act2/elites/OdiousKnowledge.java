package granbluebosses.monsters.act2.elites;

import VideoTheSpire.actions.RunTopLevelEffectAction;
import VideoTheSpire.effects.SimplePlayVideoEffect;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.action.DispelBuffAction;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.odious.OdiousPapahlukruvaCard;
import granbluebosses.cards.tempInCombat.PapahlukruvaCurse;
import granbluebosses.config.ConfigMenu;
import granbluebosses.intents.enums.CustomIntentEnums;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.aMonsters.ColossalBodyPower;
import granbluebosses.powers.aMonsters.act2.GildedHeavenPower;
import granbluebosses.powers.aMonsters.act2.GoldenCorruptionPower;
import granbluebosses.powers.aMonsters.act2.MechanizationPower;
import granbluebosses.powers.aMonsters.act2.ObservationPower;
import granbluebosses.powers.incantedOmens.AbstractIncantedOmen;
import granbluebosses.powers.incantedOmens.IncantedOmenCardPlayed;
import granbluebosses.powers.incantedOmens.IncantedOmenDamage;
import granbluebosses.powers.incantedOmens.IncantedOmenPowersApplied;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.odious.OdiousBlightrifle;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;
import static granbluebosses.GranblueBosses.videoPath;

public class OdiousKnowledge extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "Papahlukruva";
    public static final String MONSTER_ID = makeID("OdiousKnowledge");
    protected static final int MONSTER_MAX_HP = 198;
    protected static final int MONSTER_MAX_HP_A_19 = 198 + 58;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "OdiousKnowledge".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected float OMEN_MULT = 1.6f;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public int hadhnagpaphalOmenAmt;
    public int asmihilaavOmenAmt;

    public int hadhnagpaphalDmg;
    public int hadhnagpaphalStacks;

    public int asmihilaavDmg;
    public int asmihilaavStacks;

    public int anurnalkkaBaseBlock;

    public static final int HADHNAGPAPHAL_INDEX = 0;
    public static final int ASMIHILAAV_INDEX = 1;


    public OdiousKnowledge() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);



        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 18){
            this.hadhnagpaphalOmenAmt = 40;
            this.asmihilaavOmenAmt = 4;

            this.hadhnagpaphalDmg = 10;
            this.hadhnagpaphalStacks = 1;
            
            this.asmihilaavDmg = 26;
            this.asmihilaavStacks = 3;
            
            this.anurnalkkaBaseBlock = 5;

        } else {
            this.hadhnagpaphalOmenAmt = 20;
            this.asmihilaavOmenAmt = 3;
            
            this.hadhnagpaphalDmg = 7;
            this.hadhnagpaphalStacks = 2;
            
            this.asmihilaavDmg = 22;
            this.asmihilaavStacks = 3;
            
            this.anurnalkkaBaseBlock = 3;

        }

        this.damage.add(new DamageInfo(this, this.hadhnagpaphalDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.asmihilaavDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {

        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_ELITE_LIGHT);
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
                this.useHadhnagpaphal();
                break;
            case 2:
                this.useAsmihilaav();
                break;
            case 3:
                this.useAnurnalkka();
                break;
        }
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenCardPlayed.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenDamage.POWER_ID));

        this.prepareIntent();
        this.createIntent();

        this.createIntent();
        this.applyOmen();
    }

    public void useStunTurn(){
        // Does nothing. It's here for consistency
    }

    public void useHadhnagpaphal(){
//        Deal small damage + Apply Frail + Apply Mechanization
//	        Omen: Deal damage
//          Stun on cancel

        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(ASMIHILAAV_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.asmihilaavStacks, true), this.asmihilaavStacks));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new MechanizationPower(AbstractDungeon.player, this.asmihilaavStacks), this.asmihilaavStacks));

        addToBot(new ApplyPowerAction(this, this, new ObservationPower(this, AbstractDungeon.ascensionLevel >= 18 ? 2 : 1)));
        if (AbstractDungeon.player.hand.findCardById(PapahlukruvaCurse.ID) == null) addToBot(new MakeTempCardInHandAction(new PapahlukruvaCurse()));
    }

    public void useAsmihilaav(){
        // Deal damage + Remove 1 (2) Player buff + Apply Mechanization
        //	Omen: Play Attack cards
        //    Stun on cancel

        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX + 120 * Settings.scale, this.hb.cY + 120 * Settings.scale, Color.YELLOW, ShockWaveEffect.ShockWaveType.NORMAL)));
        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX - 120 * Settings.scale, this.hb.cY + 120 * Settings.scale, Color.YELLOW, ShockWaveEffect.ShockWaveType.CHAOTIC)));
        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY - 120 * Settings.scale, Color.YELLOW, ShockWaveEffect.ShockWaveType.ADDITIVE)));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(ASMIHILAAV_INDEX), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        addToBot(new DispelBuffAction(AbstractDungeon.player, this, this.asmihilaavStacks));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new MechanizationPower(AbstractDungeon.player, this.asmihilaavStacks), this.asmihilaavStacks));

        addToBot(new ApplyPowerAction(this, this, new ObservationPower(this, AbstractDungeon.ascensionLevel >= 18 ? 2 : 1)));
        if (AbstractDungeon.player.hand.findCardById(PapahlukruvaCurse.ID) == null) addToBot(new MakeTempCardInHandAction(new PapahlukruvaCurse()));
    }

    public void useAnurnalkka(){
//        Exhaust Dazed in hand and add Decay based on Dazed exhausted +
//        Gain Block and Artifact based on Dazed in Exhaust Pile +
//        Reset Observation

        addToBot(new SFXAction(Sounds.SFX_ACT2_ELITE_LIGHT));
        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("odiousknowledge/OdiousLightAnimNoSFX.webm"))));

        int dazedAmt = Math.toIntExact(AbstractDungeon.player.exhaustPile.group.stream().filter(o -> o.cardID.equals(Dazed.ID)).count());
        dazedAmt += Math.toIntExact(AbstractDungeon.player.discardPile.group.stream().filter(o -> o.cardID.equals(Dazed.ID)).count());
        dazedAmt += Math.toIntExact(AbstractDungeon.player.drawPile.group.stream().filter(o -> o.cardID.equals(Dazed.ID)).count());
        dazedAmt += Math.toIntExact(AbstractDungeon.player.hand.group.stream().filter(o -> o.cardID.equals(Dazed.ID)).count());

        int decayAmt = Math.toIntExact(AbstractDungeon.player.exhaustPile.group.stream().filter(o -> o.cardID.equals(Decay.ID)).count());
        decayAmt += Math.toIntExact(AbstractDungeon.player.discardPile.group.stream().filter(o -> o.cardID.equals(Decay.ID)).count());
        decayAmt += Math.toIntExact(AbstractDungeon.player.drawPile.group.stream().filter(o -> o.cardID.equals(Decay.ID)).count());
        decayAmt += Math.toIntExact(AbstractDungeon.player.hand.group.stream().filter(o -> o.cardID.equals(Decay.ID)).count());

        addToBot(new GainBlockAction(this, this.anurnalkkaBaseBlock * (dazedAmt + decayAmt)));
        if (AbstractDungeon.ascensionLevel > 20){
            addToBot(new ApplyPowerAction(this, this, new BarricadePower(this)));
        }

        addToBot(new ApplyPowerAction(this, this, new ArtifactPower(this, dazedAmt + decayAmt), dazedAmt + decayAmt));

        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.cardID.equals(Decay.ID)){
                addToBot(new MakeTempCardInHandAction(new Decay()));
                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true));
            }
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cardID.equals(Decay.ID)){
                addToBot(new MakeTempCardInDrawPileAction(new Decay(), 1, true, true));
                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));

            }
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cardID.equals(Decay.ID)){
                addToBot(new MakeTempCardInDiscardAction(new Decay(), 1));
                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));

            }
        }

        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, ObservationPower.POWER_ID));
        
    }

    protected void prepareIntent() {
        if (
                (AbstractDungeon.player != null && this.hasPower(ObservationPower.POWER_ID) && this.getPower(ObservationPower.POWER_ID).amount >= 5) ||
                (AbstractDungeon.ascensionLevel >= 18 && this.currentHealth * this.OMEN_MULT <= this.maxHealth && this.trigger)){

            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));
            this.trigger = false;
            
            if (!this.hasPower(StunMonsterPower.POWER_ID) && this.nextMove != 0){
                addToBot(new SetMoveAction(this, MOVES[2], (byte)3, Intent.DEFEND_DEBUFF));
                this.setMove(MOVES[2], (byte)3, Intent.DEFEND_DEBUFF);
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
            case 1:
                addToBot(new SetMoveAction(this, MOVES[1], (byte)2, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(ASMIHILAAV_INDEX).base, 1, false));
                this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(ASMIHILAAV_INDEX).base, 1, false);
                break;
            case 2:
                addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(HADHNAGPAPHAL_INDEX).base, 1, false));
                this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(HADHNAGPAPHAL_INDEX).base, 1, false);
                break;
            case 3:
                this.prepareRandomCommonMove();
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove){
            case 0:
            case 2:
            case 3:
                addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(HADHNAGPAPHAL_INDEX).base, 1, false));
                this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(HADHNAGPAPHAL_INDEX).base, 1, false);
                break;
            case 1:
                addToBot(new SetMoveAction(this, MOVES[1], (byte)2, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(ASMIHILAAV_INDEX).base, 1, false));
                this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(ASMIHILAAV_INDEX).base, 1, false);
                break;
        }
    }

    protected void prepareRandomCommonMove(){
        if (AbstractDungeon.aiRng.randomBoolean()) {
            addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(HADHNAGPAPHAL_INDEX).base, 1, false));
            this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(HADHNAGPAPHAL_INDEX).base, 1, false);
        } else {
            addToBot(new SetMoveAction(this, MOVES[1], (byte)2, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(ASMIHILAAV_INDEX).base, 1, false));
            this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(ASMIHILAAV_INDEX).base, 1, false);
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(HADHNAGPAPHAL_INDEX).base, 1, false);
            this.createIntent();
            this.applyOmen();
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
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenCardPlayed.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenDamage.POWER_ID));

        this.setMove((byte)0, Intent.STUN);
        this.createIntent();
        addToTop(new SetMoveAction(this, (byte)0, Intent.STUN));
        
        OmenUtils.onCancelOmenSFX(this);
    }



    @Override
    public void applyOmen() {
        AbstractIncantedOmen omen = null;
        switch (this.nextMove){
            case 1:
                omen = new IncantedOmenDamage(this, this.hadhnagpaphalOmenAmt, null);
                break;
            case 2:
                omen = new IncantedOmenCardPlayed(this, this.asmihilaavOmenAmt, null, AbstractCard.CardType.ATTACK, null, -1);
                break;
        }
        if (omen != null) addToBot(new ApplyPowerAction(this, this, omen));

    }

    @Override
    public void die() {
        super.die();
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new TridentOfBrahman(), new AnimaShiva());
            MonsterUtils.handleCardPlusRelicLinkedReward(new OdiousBlightrifle(), new OdiousPapahlukruvaCard());
        }
        Act2Arcarum.resumeMainMusic();
        super.die();
    }
}


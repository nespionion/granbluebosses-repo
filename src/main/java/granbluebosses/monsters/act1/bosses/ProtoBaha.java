package granbluebosses.monsters.act1.bosses;

import VideoTheSpire.actions.RunTopLevelEffectAction;
import VideoTheSpire.effects.SimplePlayVideoEffect;
import basemod.abstracts.CustomMonster;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.combat.EmpowerEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act1Skies;
import granbluebosses.cards.protobaha.optionCards.DaggerOfBahamut;
import granbluebosses.cards.protobaha.optionCards.HarpOfBahamut;
import granbluebosses.cards.protobaha.optionCards.StaffOfBahamut;
import granbluebosses.cards.protobaha.optionCards.SwordOfBahamut;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.aMonsters.OverdriveState;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.powers.aMonsters.StandbyState;
import granbluebosses.powers.aMonsters.act1.protobaha.RagnarokField;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import java.util.ArrayList;

import static granbluebosses.GranblueBosses.*;
import static granbluebosses.rewards.GoldBrickReward.GUARANTEED_GOLD_BRICK;

public class ProtoBaha extends CustomMonster {
    protected static final String MONSTER_NAME = "Proto Bahamut";
    public static final String MONSTER_ID = makeID("ProtoBaha");
    public static final String MAP_ICON = monsterPath("protobaha/protobaha_map_icon.png");
    public static final String OUTLINE = monsterPath("protobaha/protobaha_outline.png");
    protected static final int MONSTER_MAX_HP = 300;
    protected static final int MONSTER_MAX_HP_A_19 = 400;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String)null;
    protected static final String MONSTER_ANIM_URL = "ProtoBaha".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean firstTurn = true;
    protected static final String RAG_FIELD;
    protected static final String SKYFALL;
    protected static final String REGINLEIV;
    protected static final String ARCADIA;
    protected static final String ABDAK_FORCE;
    protected static final String SUPERNOVA;
    protected static final String REGINLEIV_RECIDIVE;
    protected static final String ARCADIA_KHLORON;
    protected static final String UNCHAIN;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    protected int ragFieldAmount;
    protected int skyfallDmg;
    protected int reginleivDmg;
    protected int reginleivHits;
    protected int reginleivRecidiveDmg;
    protected int reginleivRecidiveHits;
    protected int supernovaDmg;
    protected boolean isBeforePhase2Transition = true;
    protected boolean isPhase2TransitionTrigger = false;
    protected int OMEN_MULT1 = 4;
    protected int OMEN_MULT2 = 20;
    protected boolean skyfallTrigger25 = true;
    protected boolean skyfallTrigger5 = true;
    protected boolean isHL = false;
    protected boolean isDMCA = false;
    private String phase1Song = "BOSS_BOTTOM";
    private String phase2Song = "BOSS_CITY";
    public int standbyStacks;
    public int standbyStackIncrease;
    public int overdriveStacks;
    public int overdriveStacksIncrease;

    protected static final int SKYFALL_INDEX = 0;
    protected static final int REGINLEIV_INDEX = 1;
    protected static final int SUPERNOVA_INDEX = 2;
    protected static final int REGINLEIV_RECIDIVE_INDEX = 3;


    public ProtoBaha(){
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);

        } else {
            this.setHp(MONSTER_MAX_HP);;
        }


        this.ragFieldAmount = Integer.max(1, (AbstractDungeon.floorNum / 10) + (AbstractDungeon.ascensionLevel / 10));

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.skyfallDmg = 9999;
        } else {
            this.skyfallDmg = 99;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.reginleivDmg = 4;
        } else {
            this.reginleivDmg = 3;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.reginleivHits = 4;
        } else {
            this.reginleivHits = 4;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.supernovaDmg = 22;
        } else {
            this.supernovaDmg = 20;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.reginleivRecidiveDmg = this.reginleivDmg + 0;
        } else {
            this.reginleivRecidiveDmg = this.reginleivDmg + 0;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.reginleivRecidiveHits = this.reginleivHits + 2;
        } else {
            this.reginleivRecidiveHits = this.reginleivHits + 2;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.standbyStacks = 30;
            this.standbyStackIncrease = 20;
            this.overdriveStacks = 50;
            this.overdriveStacksIncrease = 20;
        } else if (AbstractDungeon.ascensionLevel >= 15) {
            this.standbyStacks = 15;
            this.standbyStackIncrease = 10;
            this.overdriveStacks = 35;
            this.overdriveStacksIncrease = 15;
        } else if (AbstractDungeon.ascensionLevel >= 9) {
            this.standbyStacks = 10;
            this.standbyStackIncrease = 5;
            this.overdriveStacks = 20;
            this.overdriveStacksIncrease = 15;
        } else {
            this.standbyStacks = 5;
            this.standbyStackIncrease = 5;
            this.overdriveStacks = 20;
            this.overdriveStacksIncrease = 10;
        }

        if (AbstractDungeon.ascensionLevel > 20){
            this.OMEN_MULT1 = 2;
            this.OMEN_MULT2 = 4;
        }

        this.isPhase2TransitionTrigger = false;

        this.isDMCA = ConfigMenu.enableDMCAMusic;
        if (this.isDMCA){
            this.phase1Song = Sounds.MUSIC_ACT1_PROTOBAHA1;
            this.phase2Song = Sounds.MUSIC_ACT1_PROTOBAHA2;
        } else {
            this.phase1Song = "BOSS_BOTTOM";
            this.phase2Song = "BOSS_CITY";
        }

        this.damage.add(new DamageInfo(this, this.skyfallDmg));
        this.damage.add(new DamageInfo(this, this.reginleivDmg));
        this.damage.add(new DamageInfo(this, this.supernovaDmg));
        this.damage.add(new DamageInfo(this, this.reginleivDmg));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
        this.state.setAnimation(0, "idle1", true);
    }

    public ProtoBaha(int initialHP){
        this();
        this.maxHealth = initialHP;
        this.currentHealth = initialHP;
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(initialHP);

        } else {
            this.setHp(initialHP);;
        }
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.fadeAll();
        AbstractDungeon.getCurrRoom().playBgmInstantly(this.phase1Song);

        StanceOmen omen = new StanceOmen(this);
        omen.setUpOmenByHp(OMEN_MULT1);
        addToTop(new ApplyPowerAction(this, this, omen));
        super.usePreBattleAction();

        this.setupStandbyState();
    }

    public void setupStandbyState(){
        addToBot(new RemoveSpecificPowerAction(this, this, StandbyState.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(this, this, OverdriveState.POWER_ID));

        StandbyState standbyState = new StandbyState(this, this.standbyStacks);
        addToBot(new ApplyPowerAction(this, this, standbyState));
        this.standbyStacks += this.standbyStackIncrease;
    }

    public void setupOverdriveState(){
        addToBot(new RemoveSpecificPowerAction(this, this, OverdriveState.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(this, this, StandbyState.POWER_ID));

        OverdriveState overdriveState = new OverdriveState(this, this.overdriveStacks);
        addToBot(new ApplyPowerAction(this, this, overdriveState));
        this.overdriveStacks += this.overdriveStacksIncrease;
    }

    @Override
    public void takeTurn() {
        if (isBeforePhase2Transition){
            takeTurnPhase1();
        } else {
            takeTurnPhase2();
        }
    }

    private void takeTurnPhase1() {

        switch (this.nextMove) {
            case 1:
                this.useReginleiv();
                break;
            case 2:
                this.useArcadia();
                break;
            case 3:
                this.useReginleiv();
                break;
            case 4:
                this.useAbdakForce();
                break;
            case 5:
                this.phase2Transition();
                break;
            case 6:
                this.stunTurn();
                break;
            default:
                this.useRagField();
                break;
        }
        this.prepareIntent();
    }

    private void takeTurnPhase2() {

        switch (this.nextMove) {
            case 1:
                this.useSuperNova();
                break;
            case 2:
                this.useArcadiaKhloron();
                break;
            case 3:
                this.useReginleivRecidive();
                break;
            case 4:
                this.useArcadiaKhloron();
                break;
            case 5:
                this.useSkyfall();
                break;
            case 6:
                this.stunTurn();
                break;
            default:
                this.useRagField();
                break;
        }
        this.prepareIntent();
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(RAG_FIELD, (byte)0, Intent.STRONG_DEBUFF);
        }
    }

    protected void prepareIntent(){
        if (isBeforePhase2Transition){
            this.prepareIntentPhase1();
        }
        else {
            this.prepareIntentPhase2();
        }
    }

    protected void prepareIntentPhase1(){
        switch (this.nextMove) {
            case 1:
                addToBot(new SetMoveAction(this, ARCADIA, (byte)2, Intent.STRONG_DEBUFF));
                break;
            case 2:
                addToBot(new SetMoveAction(this, REGINLEIV, (byte)3, Intent.ATTACK, this.damage.get(REGINLEIV_INDEX).base, reginleivHits + (2 * this.inOverdrive()), true));
                break;
            case 3:
                addToBot(new SetMoveAction(this, ABDAK_FORCE, (byte)4, Intent.BUFF));
                break;
            case 4:
                addToBot(new SetMoveAction(this, REGINLEIV, (byte)1, Intent.ATTACK, this.damage.get(REGINLEIV_INDEX).base, reginleivHits + (2 * this.inOverdrive()), true));
                break;
            case 5:
                addToBot(new SetMoveAction(this, REGINLEIV, (byte)1, Intent.ATTACK, this.damage.get(REGINLEIV_INDEX).base, reginleivHits + (2 * this.inOverdrive()), true));
                break;
            case 6:
                addToBot(new SetMoveAction(this, REGINLEIV, (byte)1, Intent.ATTACK, this.damage.get(REGINLEIV_INDEX).base, reginleivHits + (2 * this.inOverdrive()), true));
                break;
            default:
                addToBot(new SetMoveAction(this, REGINLEIV, (byte)1, Intent.ATTACK, reginleivDmg, reginleivHits + (2 * this.inOverdrive()), true));
                break;
        }
    }

    protected void prepareIntentPhase2(){
        if (this.maxHealth >= this.currentHealth * this.OMEN_MULT1 && skyfallTrigger25){
            this.skyfallTrigger25 = false;
            ((StanceOmen) this.getPower(StanceOmen.POWER_ID)).setUpOmenByHp(OMEN_MULT2);

            if (this.nextMove != 6 && !this.hasPower(StunMonsterPower.POWER_ID)) {
                this.overdriveStacks += this.overdriveStacksIncrease;
                this.setupOverdriveState();
                OmenUtils.onPrepOmenSFX(this);
                addToBot(new TextAboveCreatureAction(this, "DANGER!"));
                addToBot(new SetMoveAction(this, SKYFALL, (byte)5, Intent.ATTACK, skyfallDmg, 1, false));
                return;
            } else {
                OmenUtils.onCancelOmenSFX(this);
            }

        }
        if (this.maxHealth >= this.currentHealth * this.OMEN_MULT2 && skyfallTrigger5){
            this.skyfallTrigger5 = false;

            if (this.nextMove != 6 && !this.hasPower(StunMonsterPower.POWER_ID)){
                this.setupOverdriveState();
                OmenUtils.onPrepOmenSFX(this);
                addToBot(new TextAboveCreatureAction(this, "DANGER!"));
                addToBot(new SetMoveAction(this, SKYFALL, (byte)5, Intent.ATTACK, skyfallDmg, 1, false));
                return;
            } else {
                OmenUtils.onCancelOmenSFX(this);
            }

            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));
        }
        switch (this.nextMove) {
            case 1:
                addToBot(new SetMoveAction(this, ARCADIA_KHLORON, (byte)2, Intent.STRONG_DEBUFF));
                break;
            case 2:
                addToBot(new SetMoveAction(this, REGINLEIV_RECIDIVE, (byte)3, Intent.ATTACK, this.damage.get(REGINLEIV_RECIDIVE_INDEX).base + (2 * this.inOverdrive()), reginleivRecidiveDmg, true));
                break;
            case 3:
                addToBot(new SetMoveAction(this, ARCADIA_KHLORON, (byte)4, Intent.STRONG_DEBUFF));
                break;
            case 4:
                addToBot(new SetMoveAction(this, SUPERNOVA, (byte)1, Intent.ATTACK, this.damage.get(SUPERNOVA_INDEX).base + (3 * this.inOverdrive()), 1, false));
                break;
            case 5:
                addToBot(new SetMoveAction(this, ARCADIA_KHLORON, (byte)2, Intent.STRONG_DEBUFF));
                break;
            case 6:
                addToBot(new SetMoveAction(this, ARCADIA_KHLORON, (byte)2, Intent.STRONG_DEBUFF));
                break;
            default:
                addToBot(new SetMoveAction(this, ARCADIA_KHLORON, (byte)2, Intent.STRONG_DEBUFF));
                break;
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (!this.isDying && this.currentHealth * 2 <= this.maxHealth && this.isBeforePhase2Transition) {
            if (!this.isPhase2TransitionTrigger){
                addToBot(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.INTERRUPTED));
                this.isPhase2TransitionTrigger = true;
            }
            this.setMove(UNCHAIN, (byte)5, Intent.UNKNOWN);
            this.createIntent();
            addToBot(new SetMoveAction(this, UNCHAIN, (byte)5, Intent.UNKNOWN));

        } else if (!this.isDying && this.hasPower(StandbyState.POWER_ID)){
            StandbyState tempPower = (StandbyState) this.getPower(StandbyState.POWER_ID);
            tempPower.lowerAmount(info.output);

        } else if (!this.isDying && this.hasPower(OverdriveState.POWER_ID)){
            OverdriveState tempPower = (OverdriveState) this.getPower(OverdriveState.POWER_ID);

            if (tempPower.amount <= info.output) {
                if (this.nextMove == 5) OmenUtils.onCancelOmenSFX(this);
                addToBot(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.INTERRUPTED));
                this.setMove((byte)6, Intent.STUN);
                this.createIntent();
                addToBot(new SetMoveAction(this, (byte)6, Intent.STUN));
            }
            tempPower.lowerAmount(info.output);
        }



    }

    protected void phase2Transition(){
        this.isBeforePhase2Transition = false;
        this.state.setTimeScale(1.0F);
        CardCrawlGame.music.fadeAll();

        AbstractDungeon.scene.fadeOutAmbiance();

        addToBot(new SFXAction(Sounds.PBHL_PHASE_TRANS));

        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("protobaha/protoBahaUnchain.webm"))));

        this.state.setAnimation(0, "trans", false);


        this.state.addAnimation(0, "idle2", true, 0.0F);


        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            CardCrawlGame.music.playTempBGM(this.phase2Song);
        }
//        AbstractDungeon.getCurrRoom().playBgmInstantly(this.phase2Song);

    }

    protected void useRagField(){
//        addToBot(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.3F));

        this.state.setAnimation(0, "rag_field", false);
        this.state.addAnimation(0, "idle1", true, 0.0f);

        addToBot(new SFXAction(Sounds.PBHL_RAG_FIELD));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new RagnarokField(AbstractDungeon.player, this.ragFieldAmount)));
    }

    protected void useSkyfall(){

        addToBot(new SFXAction(Sounds.PBHL_SKYFALL));
        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("protobaha/skyfallMute.webm"))));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(SKYFALL_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    protected void useSuperNova(){
//        this.state.setAnimation(0, "supernova", false);
        this.state.addAnimation(0, "idle2", true, 0.0f);
        
//        addToBot(new VFXAction(new OmegaFlashEffect(this.hb.cX, this.hb.cY ), 0.3F));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(SUPERNOVA_INDEX), AbstractGameAction.AttackEffect.FIRE));

    }

    protected void useReginleiv(){

        if (this.hasPower(makeID(OverdriveState.class.getSimpleName()))){
            for (int i = 0; i < this.reginleivHits + (2 * this.inOverdrive()); i++){
                addToBot(new VFXAction(new LaserBeamEffect(this.hb.cX, this.hb.cY + 60.0F * Settings.scale), 0.1F));

                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(REGINLEIV_INDEX), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new SFXAction(Sounds.PBHL_REGINLEIV));
            }
        }
    }

    protected void useReginleivRecidive(){

        if (this.hasPower(makeID(OverdriveState.class.getSimpleName()))){
            for (int i = 0; i < this.reginleivRecidiveHits + 2; i++){
                addToBot(new VFXAction(new LaserBeamEffect(this.hb.cX, this.hb.cY + 60.0F * Settings.scale), 0.1F));

                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(REGINLEIV_RECIDIVE_INDEX), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new SFXAction(Sounds.PBHL_REGINLEIV));
            }
        }
    }

    protected void useArcadia(){
        addToBot(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.NORMAL), 0.3F));
        addToBot(new SFXAction(Sounds.PBHL_ARCADIA));

        this.useArcadiaDebuff(1);
        if (this.hasPower(makeID(OverdriveState.class.getSimpleName()))){
            this.useArcadiaDebuff(1);
        }
        if (AbstractDungeon.ascensionLevel >= 19){
            this.useArcadiaDebuff(1);
        }
    }

    protected void useArcadiaKhloron(){
        addToBot(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        addToBot(new SFXAction(Sounds.PBHL_ARCADIA));

        this.useArcadiaDebuff(2);
        if (this.hasPower(OverdriveState.POWER_ID)){
            this.useArcadiaDebuff(2);
        }
        if (AbstractDungeon.ascensionLevel >= 19){
            this.useArcadiaDebuff(2);
        }
    }

    private void useArcadiaDebuff(int stacks){
        AbstractPlayer p = AbstractDungeon.player;
        /* Debuffs by random num:
        0: Weak
        1: Frail
        2: Vulnerable
        3: Draw Reduction
        4: Neg Strength
        */
        int debuffToInflict = AbstractDungeon.aiRng.random(3);
        switch (debuffToInflict){
            case 0:
                addToBot(new ApplyPowerAction(p, this, new WeakPower(p, stacks, true)));
                break;
            case 1:
                addToBot(new ApplyPowerAction(p, this, new FrailPower(p, stacks, true)));
                break;
            case 2:
                addToBot(new ApplyPowerAction(p, this, new VulnerablePower(p, stacks, true)));
                break;
            case 3:
                addToBot(new MakeTempCardInDiscardAction(new Dazed(), stacks));
                break;
            default:
                this.addToBot(new ApplyPowerAction(p, this, new StrengthPower(p, -stacks), -stacks));
                if (!p.hasPower("Artifact")) {
                    this.addToBot(new ApplyPowerAction(p, this, new GainStrengthPower(p, stacks), stacks));
                }
                break;
        }
    }

    protected void useAbdakForce(){
        this.addToBot(new VFXAction(this, new EmpowerEffect(this.hb.cX, this.hb.cY), 0.3F));
        addToBot(new SFXAction(Sounds.PBHL_ABDAK_FORCE));

        this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));

        if (this.hasPower(makeID(OverdriveState.class.getSimpleName()))) {
            this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
        }
    }

    protected void stunTurn(){
        this.setupStandbyState();
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {

            if (AbstractDungeon.ascensionLevel >= 5){
                MonsterUtils.addFullHealReward();
            }

            if ((AbstractDungeon.treasureRng.randomBoolean(0.02f) || GUARANTEED_GOLD_BRICK) && AbstractDungeon.player.masterDeck.hasUpgradableCards()){
                MonsterUtils.addGoldBrickReward();
            }

            RewardItem reward = new RewardItem(AbstractCard.CardColor.COLORLESS);
            reward.cards = new ArrayList<>();
            reward.cards.add(new SwordOfBahamut());
            reward.cards.add(new DaggerOfBahamut());
            reward.cards.add(new StaffOfBahamut());
            reward.cards.add(new HarpOfBahamut());

            for (AbstractCard c : reward.cards) {
                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    r.onPreviewObtainCard(c);
                }
            }

            reward.text = "Choose a Bahamut Weapon";

            AbstractDungeon.getCurrRoom().rewards.add(reward);

        }
        Act1Skies.resumeMainMusic();
        super.die();
    }

    public int inOverdrive(){
        int inOverdrive;
        if (this.hasPower(OverdriveState.POWER_ID)){
            inOverdrive = 1;
        } else {
            inOverdrive = 0;
        }
        return inOverdrive;
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        RAG_FIELD = MOVES[0];
        SKYFALL = MOVES[1];
        REGINLEIV = MOVES[2];
        ARCADIA = MOVES[3];
        ABDAK_FORCE = MOVES[4];
        SUPERNOVA = MOVES[5];
        REGINLEIV_RECIDIVE = MOVES[6];
        ARCADIA_KHLORON = MOVES[7];
        UNCHAIN = MOVES[8];
    }
}

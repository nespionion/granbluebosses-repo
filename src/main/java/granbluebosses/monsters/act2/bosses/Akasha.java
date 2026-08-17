package granbluebosses.monsters.act2.bosses;

import VideoTheSpire.actions.RunTopLevelEffectAction;
import VideoTheSpire.effects.SimplePlayVideoEffect;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import com.megacrit.cardcrawl.vfx.combat.*;
import granbluebosses.GranblueBosses;
import granbluebosses.action.DispelBuffAction;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.intents.enums.CustomIntentEnums;
import granbluebosses.powers.aMonsters.act2.akasha.AkashaKarmaPower;
import granbluebosses.powers.aMonsters.act2.akasha.AkashaTimeWarpPower;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.akasha.EnigmaticArmillaRelic;
import granbluebosses.relics.akasha.HollowKeyRelic;
import granbluebosses.util.Sounds;
import granbluebosses.util.MonsterUtils;
import static granbluebosses.intents.enums.CustomIntentEnums.ATTACK_MAGIC;
import granbluebosses.config.ConfigMenu;
import com.badlogic.gdx.graphics.Color;

import static granbluebosses.GranblueBosses.makeID;
import static granbluebosses.GranblueBosses.videoPath;
import static granbluebosses.rewards.GoldBrickReward.GUARANTEED_GOLD_BRICK;

public class Akasha extends CustomMonster {
    protected static final String MONSTER_NAME = "Akasha";
    public static final String MONSTER_ID = makeID("Akasha");
    public static final String MAP_ICON = GranblueBosses.monsterPath("akasha/akasha_map_icon.png");
    public static final String OUTLINE = GranblueBosses.monsterPath("akasha/akasha_outline.png");
    protected static final int MONSTER_MAX_HP = 512;
    protected static final int MONSTER_MAX_HP_A_19 = 512 + 60;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = 0.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 700.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 700.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = MONSTER_NAME.toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    public boolean phaseTransition = false;
    private int currPhase = 1;
    public int turnNum = 1;
    protected static final float[] OMEN_MULTS = new float[]{
            1.5f, 2f, 3f, 4f
    };
    protected int currOmenMultIndex = 0;

    public int megiddoDmg;
    public int ancientFlareDmg;
    public int bloodLightningDmg;
    public int bloodLightningHits;
    public int purificationDmg;
    public int purificationHits;
    public int karmaStacks;
    public int skyRiftDmg;
    public int megiddoStacks;
    public int ancientFlareStacks;
    public static final int MEGIDO_INDEX = 0;
    public static final int ANCIENT_FLARE_INDEX = 1;
    public static final int BLOOD_LIGHT_INDEX = 2;
    public static final int PURIFICATION_INDEX = 3;
    public static final int SKY_RIFT_INDEX = 4;

    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String WAIT_TURN;
    public static final String TIME_WARP;
    public static final String BLOOD_LIGHTNING;
    public static final String MEGIDDO;
    public static final String ANCIENT_FLARE;
    public static final String KARMA;
    public static final String PURIFICATION;
    public static final String SKY_RIFT;

    protected boolean isDMCA = false;
    private String phase1Song = "BOSS_BOTTOM";
    private String phase2Song = "BOSS_CITY";
    private String phase3Song = "BOSS_BEYOND";

    public Akasha() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.megiddoDmg = 14;
            this.ancientFlareDmg = 14;
            this.bloodLightningDmg = 6;
            this.bloodLightningHits = 3;

            this.karmaStacks = 4;

            this.purificationDmg = 15;
            this.purificationHits = 3;
            this.skyRiftDmg = 6;

            this.megiddoStacks = 4;
            this.ancientFlareStacks = 4;
        } else if (AbstractDungeon.ascensionLevel >= 10) {
            this.megiddoDmg = 13;
            this.ancientFlareDmg = 13;
            this.bloodLightningDmg = 5;
            this.bloodLightningHits = 3;

            this.karmaStacks = 3;

            this.purificationDmg = 12;
            this.purificationHits = 3;
            this.skyRiftDmg = 5;

            this.megiddoStacks = 3;
            this.ancientFlareStacks = 3;
        } else {
            this.megiddoDmg = 10;
            this.ancientFlareDmg = 10;
            this.bloodLightningDmg = 3;
            this.bloodLightningHits = 3;

            this.karmaStacks = 3;

            this.purificationDmg = 10;
            this.purificationHits = 2;
            this.skyRiftDmg = 3;

            this.megiddoStacks = 1;
            this.ancientFlareStacks = 1;
        }

        this.currPhase = 1;
        this.currOmenMultIndex = 0;
        this.phaseTransition = false;

        this.isDMCA = ConfigMenu.enableDMCAMusic;
        if (this.isDMCA) {

            this.phase1Song = Sounds.MUSIC_ACT2_AKASHA_P_1;
            this.phase2Song = Sounds.MUSIC_ACT2_AKASHA_P_2;
            this.phase3Song = Sounds.MUSIC_ACT2_AKASHA_P_3;
        } else {
            this.phase1Song = "BOSS_BOTTOM";
            this.phase2Song = "BOSS_CITY";
            this.phase3Song = "BOSS_BEYOND";
        }

        this.damage.add(new DamageInfo(this, this.megiddoDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.ancientFlareDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.bloodLightningDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.purificationDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.skyRiftDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
        this.state.setAnimation(0, "idle1", false);

    }

    public void usePreBattleAction() {
        CardCrawlGame.music.fadeAll();

        this.currOmenMultIndex = 0;
        this.currPhase = 1;
        if (AbstractDungeon.ascensionLevel >= 19){
            this.turnNum = 2;
        } else {
            this.turnNum = 1;
        }

        StanceOmen omen = new StanceOmen(this);
        omen.setUpOmenByHp(OMEN_MULTS[this.currOmenMultIndex]);
        addToTop(new ApplyPowerAction(this, this, omen));

        super.usePreBattleAction();
    }


//        "Waiting",
//        "Time Warp",
//        "Blood Lightning",
//        "Megiddo",
//        "Ancient Flare",
//        "Karma",
//        "Purification",
//        "Sky-Rift"


    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useTimeWarp();
                break;
            case 1:
                this.useMegiddo();
                break;
            case 2:
                this.useBloodLightning();
                break;
            case 3:
                this.useAncientFlare();
                break;
            case 4:
                this.useBloodLightning();
                break;
            case 5:
                this.useKarma();
                break;
            case 6:
                this.usePurification();
                break;
            case 7:
                this.useSkyRift();
                break;
        }
        this.prepareIntent();
        this.turnNum++;
    }

    // Time Warp > Megiddo > Blood Lightning > Ancient Flare > Blood Lightning > Megiddo > Blood Lightning > Ancient Flare
    // Triggers Sky Rift on phase transitions at 66% and 33%
    // Triggers Karma (which prepares Purification) at 50% and 25%
    // Sky Rift triggers have priority over Karma triggers

    // Time Warp:
    // Skips turns based on stacks
    // Builds stacks when BloodLightning, Megiddo, or Ancient Flare are used
    // Builds additional stacks when Karma and Sky Rift are used
    // Does not apply when full block?

    // For each stack it skips a turn on both sides and increases Strength if it's negative
    // All Stacks are consumed immediately
    // Use the EndTurnAction and ExtraTurnAction from Vault card

    public void damage(DamageInfo info) {
        super.damage(info);
        switch (this.currOmenMultIndex){
            case 1:
                if (!this.isDying && this.currentHealth * OMEN_MULTS[this.currOmenMultIndex] <= this.maxHealth && this.currPhase > 1){
                    this.setMove(KARMA, (byte)5, Intent.STRONG_DEBUFF);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, KARMA, (byte)5, Intent.STRONG_DEBUFF));

                    this.currOmenMultIndex = 2;

                    StanceOmen omen = ((StanceOmen) this.getPower(StanceOmen.POWER_ID));
                    if (omen != null) omen.setUpOmenByHp(OMEN_MULTS[this.currOmenMultIndex]);
                }
                break;
            case 3:
                if (!this.isDying && this.currentHealth * OMEN_MULTS[this.currOmenMultIndex] <= this.maxHealth && this.currPhase > 2){
                    this.setMove(KARMA, (byte)5, Intent.STRONG_DEBUFF);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, KARMA, (byte)5, Intent.STRONG_DEBUFF));

                    addToBot(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));
                }
                break;
        }
    }

    protected void prepareIntent() {
        if (!this.isDying && this.currentHealth * OMEN_MULTS[this.currOmenMultIndex] <= this.maxHealth && ((this.currOmenMultIndex == 0 && this.currPhase == 1) || (this.currOmenMultIndex == 2 && this.currPhase == 2))){

            this.setMove(SKY_RIFT, (byte)7, Intent.ATTACK_BUFF, this.damage.get(SKY_RIFT_INDEX).base, this.turnNum, true);
            this.createIntent();
            addToBot(new SetMoveAction(this, SKY_RIFT, (byte)7, Intent.ATTACK_BUFF, this.damage.get(SKY_RIFT_INDEX).base, this.turnNum, true));
            return;
        }

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(AkashaKarmaPower.POWER_ID)){
            if (this.currPhase == 3){
                this.setMove(PURIFICATION, (byte)6, CustomIntentEnums.INSTAKILL);
                this.createIntent();
                addToBot(new SetMoveAction(this, PURIFICATION, (byte)6, CustomIntentEnums.INSTAKILL));
            } else {
                this.purificationHits = Math.toIntExact(AbstractDungeon.player.powers.stream().filter(o -> o.type == AbstractPower.PowerType.BUFF).count());

                this.setMove(PURIFICATION, (byte)6, ATTACK_MAGIC, this.damage.get(PURIFICATION_INDEX).base, this.purificationHits, true);
                this.createIntent();
                addToBot(new SetMoveAction(this, PURIFICATION, (byte)6, ATTACK_MAGIC, this.damage.get(PURIFICATION_INDEX).base, this.purificationHits, true));
            }
            return;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.prepareIntentA17();
            return;
        }

        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, MEGIDDO, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(MEGIDO_INDEX).base, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, BLOOD_LIGHTNING, (byte) 2, Intent.ATTACK, this.damage.get(BLOOD_LIGHT_INDEX).base, this.bloodLightningHits, true));
                break;
            case 2:
                addToBot(new SetMoveAction(this, ANCIENT_FLARE, (byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(ANCIENT_FLARE_INDEX).base, 1, false));
                break;
            case 3:
                addToBot(new SetMoveAction(this, BLOOD_LIGHTNING, (byte) 4, Intent.ATTACK, this.damage.get(BLOOD_LIGHT_INDEX).base, this.bloodLightningHits, true));
                break;
            case 4:
                addToBot(new SetMoveAction(this, MEGIDDO, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(MEGIDO_INDEX).base, 1, false));
                break;
            case 5:
                addToBot(new SetMoveAction(this, BLOOD_LIGHTNING, (byte) 2, Intent.ATTACK, this.damage.get(BLOOD_LIGHT_INDEX).base, this.bloodLightningHits, true));
                break;
            case 6:
                addToBot(new SetMoveAction(this, MEGIDDO, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(MEGIDO_INDEX).base, 1, false));
                break;
            case 7:
                addToBot(new SetMoveAction(this, ANCIENT_FLARE, (byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(ANCIENT_FLARE_INDEX).base, 1, false));
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 0:
                if (AbstractDungeon.monsterRng.randomBoolean()){
                    addToBot(new SetMoveAction(this, MEGIDDO, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(MEGIDO_INDEX).base, 1, false));
                } else {
                    addToBot(new SetMoveAction(this, ANCIENT_FLARE, (byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(ANCIENT_FLARE_INDEX).base, 1, false));
                }
                break;
            case 1:
                addToBot(new SetMoveAction(this, BLOOD_LIGHTNING, (byte) 2, Intent.ATTACK, this.damage.get(BLOOD_LIGHT_INDEX).base, this.bloodLightningHits, true));
                break;
            case 2:
                if (AbstractDungeon.monsterRng.randomBoolean()){
                    addToBot(new SetMoveAction(this, MEGIDDO, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(MEGIDO_INDEX).base, 1, false));
                } else {
                    addToBot(new SetMoveAction(this, ANCIENT_FLARE, (byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(ANCIENT_FLARE_INDEX).base, 1, false));
                }
                break;
            case 3:
                addToBot(new SetMoveAction(this, BLOOD_LIGHTNING, (byte) 4, Intent.ATTACK, this.damage.get(BLOOD_LIGHT_INDEX).base, this.bloodLightningHits, true));
                break;
            case 4:
                addToBot(new SetMoveAction(this, MEGIDDO, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(MEGIDO_INDEX).base, 1, false));
                break;
            case 5:
                addToBot(new SetMoveAction(this, BLOOD_LIGHTNING, (byte) 2, Intent.ATTACK, this.damage.get(BLOOD_LIGHT_INDEX).base, this.bloodLightningHits, true));
                break;
            case 6:
                if (AbstractDungeon.monsterRng.randomBoolean()){
                    addToBot(new SetMoveAction(this, MEGIDDO, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(MEGIDO_INDEX).base, 1, false));
                } else {
                    addToBot(new SetMoveAction(this, ANCIENT_FLARE, (byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(ANCIENT_FLARE_INDEX).base, 1, false));
                }
                break;
            case 7:
                if (AbstractDungeon.monsterRng.randomBoolean()){
                    addToBot(new SetMoveAction(this, MEGIDDO, (byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(MEGIDO_INDEX).base, 1, false));
                } else {
                    addToBot(new SetMoveAction(this, ANCIENT_FLARE, (byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(ANCIENT_FLARE_INDEX).base, 1, false));
                }
                break;
        }
    }

    public void useTimeWarp(){

        if (!AbstractDungeon.player.hasPower(AkashaTimeWarpPower.POWER_ID)){
            addToBot(new VFXAction(new TimeWarpTurnEndEffect()));
            addToBot(new SFXAction("VO_AWAKENEDONE_3"));
            addToBot(new AnimateShakeAction(this, 0.7f, 0.7f));
            AbstractDungeon.getCurrRoom().playBgmInstantly(this.phase1Song);
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new AkashaTimeWarpPower(AbstractDungeon.player, 1), 1));
            addToBot(new ApplyPowerAction(this, this, new AkashaTimeWarpPower(this, 1), 1));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, 1)));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, 1)));
            addToBot(new ApplyPowerAction(this, this, new ConstrictedPower(this, this, 1)));
        }
        // Else Do nothing
        // Serves as Stun Turn

    }

    public void useBloodLightning(){
        // Multi hit based on phase number

        float randomXOffset;
        float randomYOffset;
        for (int i = 0; i < this.bloodLightningHits + this.currPhase; i++){
            randomXOffset = (AbstractDungeon.aiRng.random() - 0.5f) * AbstractDungeon.player.hb.width;
            randomYOffset = ((AbstractDungeon.aiRng.random() - 0.5f) * AbstractDungeon.player.hb.height);
            addToBot(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX + randomXOffset, AbstractDungeon.player.hb.cY + randomYOffset)));
            addToBot(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(BLOOD_LIGHT_INDEX), AbstractGameAction.AttackEffect.NONE));

        }

//        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new AkashaTimeWarpPower(AbstractDungeon.player), this.currPhase+1));
//        addToBot(new ApplyPowerAction(this, this, new AkashaTimeWarpPower(this), this.currPhase+1));

    }

    public void useMegiddo(){
        // Single Hit dmg?
        // Apply Megiddo -> Frail?
        // Apply Megiddo -> Neg Dexterity on later phases?

        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(MEGIDO_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        switch (this.currPhase){
            case 1:
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, megiddoStacks, true), megiddoStacks));
                break;
            case 2:
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.megiddoStacks + this.currPhase, true), this.megiddoStacks + this.currPhase));
                break;
            case 3:
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, 1)));
                if (AbstractDungeon.ascensionLevel >= 19){
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, this.megiddoStacks + this.currPhase, true), this.megiddoStacks + this.currPhase));
                }
                break;
        }

//        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new AkashaTimeWarpPower(AbstractDungeon.player), this.currPhase+1));
//        addToBot(new ApplyPowerAction(this, this, new AkashaTimeWarpPower(this), this.currPhase+1));
    }

    public void useAncientFlare(){
        // Single Hit dmg?
        // Apply ATK Down -> Weak
        // Apply ATK Down -> Neg Strength on later phases?
        //                            Maybe bad idea if not removable bc it makes closing the fight too difficult

        addToBot(new VFXAction(this, new GhostIgniteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-120.0F, 120.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-120.0F, 120.0F) * Settings.scale), 0.05F));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(ANCIENT_FLARE_INDEX), AbstractGameAction.AttackEffect.FIRE));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, this.ancientFlareStacks + this.currPhase, true), ancientFlareStacks));

        if (AbstractDungeon.ascensionLevel >= 19 && this.currPhase >= 3){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -ancientFlareStacks), -ancientFlareStacks));
        }

//        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new AkashaTimeWarpPower(AbstractDungeon.player), this.currPhase+1));
//        addToBot(new ApplyPowerAction(this, this, new AkashaTimeWarpPower(this), this.currPhase+1));
    }

    public void useKarma(){
        // Apply Karma
        //      -> Removed if you play enough cards
        //      -> Changes intent to Purification at the end of the round
        //      -> When removed stun?
        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.DARK_GRAY, ShockWaveEffect.ShockWaveType.NORMAL)));
        addToBot(new SFXAction("VO_AWAKENEDONE_2"));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new AkashaKarmaPower(AbstractDungeon.player, this.karmaStacks), this.karmaStacks));

//        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new AkashaTimeWarpPower(AbstractDungeon.player), this.currPhase+1));
//        addToBot(new ApplyPowerAction(this, this, new AkashaTimeWarpPower(this), this.currPhase+1));
    }

    public void usePurification(){
        // Multi hit based on buffs?
        // Attempt to kill player
        // Removes 1/all buffs
        // Omen? -> Remove Karma to cancel

        addToBot(new VFXAction(new SweepingBeamEffect(this.hb.cX, this.hb.cY - (20f * Settings.scale), true)));
        addToBot(new VFXAction(new SweepingBeamEffect(this.hb.cX, this.hb.cY + (20f * Settings.scale), true)));
        addToBot(new VFXAction(new LaserBeamEffect(this.hb.cX, this.hb.cY + (this.hb.height * 0f) * Settings.scale), 1.5F));

//        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("akasha/Akasha_Purification_No_SFX.webm"))));
//        addToBot(new SFXAction(Sounds.AKASHA_PURIFICATION_SFX));

        if (this.currPhase >= 3){
//            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EndTurnDeathPower(AbstractDungeon.player)));
//            addToBot(new InstantKillAction(AbstractDungeon.player));

            addToBot(new SFXAction(Sounds.AKASHA_PURIFICATION_SFX));
            addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("akasha/Akasha_Purification_No_SFX.webm"))));


            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 99999));
        } else {
            for (int i = 0; i < this.purificationHits; i++){
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(PURIFICATION_INDEX), AbstractGameAction.AttackEffect.NONE));
            }
        }

        if (this.currPhase <= 1){
            addToBot(new DispelBuffAction(AbstractDungeon.player, this, 1));
        } else {
            addToBot(new DispelBuffAction(AbstractDungeon.player, this, true));
        }

        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, AkashaKarmaPower.POWER_ID));
    }

    public void cancelPurification(){
        this.setMove((byte)0, Intent.STUN);
        this.createIntent();
        addToBot(new SetMoveAction(this, (byte)0, Intent.STUN));
    }

    public void useSkyRift(){
        // Trigger - Stance Omen
        // Multi hit based on number of turns passed

        for (int i = 0; i < this.turnNum; i++){
            addToBot(new VFXAction(new CleaveEffect(true)));
            addToBot(new SFXAction("ATTACK_MAGIC_FAST_1"));
            addToBot(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(SKY_RIFT_INDEX), AbstractGameAction.AttackEffect.NONE));
        }

        switch (this.currPhase){
            case 1:
                this.transitionToPhase2();
                break;
            case 2:
                this.transitionToPhase3();
                break;
        }

    }

    public void transitionToPhase2(){
        // TODO : Test phase transition SFX
        addToBot(new SFXAction("VO_AWAKENEDONE_3"));

        this.ancientFlareStacks++;
        this.megiddoStacks++;

        addToBot(new ApplyPowerAction(this, this, new AkashaTimeWarpPower(this, 1), 1));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, 1)));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, 1)));
        addToBot(new ApplyPowerAction(this, this, new ConstrictedPower(this, this, 1)));

        this.state.setAnimation(0, "idle2", false);

        CardCrawlGame.music.fadeAll();
        AbstractDungeon.getCurrRoom().playBgmInstantly(this.phase2Song);

        this.currOmenMultIndex = 1;
        this.currPhase = 2;
        StanceOmen omen = ((StanceOmen) this.getPower(StanceOmen.POWER_ID));
        if (omen != null) omen.setUpOmenByHp(OMEN_MULTS[this.currOmenMultIndex]);
        this.state.setTimeScale(1.0F);
    }

    public void transitionToPhase3(){
        // TODO : Test phase transition SFX
        addToBot(new SFXAction("VO_AWAKENEDONE_3"));

        this.ancientFlareStacks++;
        this.megiddoStacks++;

        addToBot(new ApplyPowerAction(this, this, new AkashaTimeWarpPower(this, 1), 1));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, 1)));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, 1)));

        addToBot(new RemoveSpecificPowerAction(this, this, ConstrictedPower.POWER_ID));

        this.state.setAnimation(0, "idle3", false);

        CardCrawlGame.music.fadeAll();
        AbstractDungeon.getCurrRoom().playBgmInstantly(this.phase3Song);

        this.currOmenMultIndex = 3;
        this.currPhase = 3;

        StanceOmen omen = ((StanceOmen) this.getPower(StanceOmen.POWER_ID));
        if (omen != null) omen.setUpOmenByHp(OMEN_MULTS[this.currOmenMultIndex]);
        this.state.setTimeScale(1.0F);
    }

    @Override
    protected void getMove(int i) {
//        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(AkashaTimeWarpPower.POWER_ID)){
//            this.turnNum += AbstractDungeon.player.getPower(AkashaTimeWarpPower.POWER_ID).amount;
//        }
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(TIME_WARP, (byte)0, Intent.UNKNOWN);
            this.createIntent();
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards){
            MonsterUtils.handleDoubleRelicLinkedReward(new EnigmaticArmillaRelic(), new HollowKeyRelic(), HollowKeyRelic.NAME);

            if (AbstractDungeon.ascensionLevel >= 5){
                MonsterUtils.addFullHealReward();
            }

            if ((AbstractDungeon.treasureRng.randomBoolean(0.02f) || GUARANTEED_GOLD_BRICK) && AbstractDungeon.player.masterDeck.hasUpgradableCards()){
                MonsterUtils.addGoldBrickReward();
            }

        }
        Act2Arcarum.resumeMainMusic();
        super.die();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        WAIT_TURN = MOVES[0];
        TIME_WARP = MOVES[1];
        BLOOD_LIGHTNING = MOVES[2];
        MEGIDDO = MOVES[3];
        ANCIENT_FLARE = MOVES[4];
        KARMA = MOVES[5];
        PURIFICATION = MOVES[6];
        SKY_RIFT = MOVES[7];
    }
}


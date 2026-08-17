package granbluebosses.monsters.act2.bosses;

import VideoTheSpire.actions.RunTopLevelEffectAction;
import VideoTheSpire.effects.SimplePlayVideoEffect;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.other.InchoateWorldCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.GranblueBosses;
import granbluebosses.intents.enums.CustomIntentEnums;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.aMonsters.act2.theworld.TheWorldSpherePower;
import granbluebosses.powers.aMonsters.act2.theworld.TheWorldTerminalPower;
import granbluebosses.powers.incantedOmens.AbstractIncantedOmen;
import granbluebosses.powers.incantedOmens.IncantedOmenCardPlayed;
import granbluebosses.powers.incantedOmens.IncantedOmenDamage;
import granbluebosses.powers.incantedOmens.IncantedOmenPowersApplied;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.theworld.*;
import granbluebosses.util.CustomTags;
import granbluebosses.util.Sounds;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;
import granbluebosses.vfx.ShowCardEffect;

import static granbluebosses.GranblueBosses.makeID;
import static granbluebosses.GranblueBosses.videoPath;
import static granbluebosses.intents.enums.CustomIntentEnums.ATTACK_MAGIC;
import static granbluebosses.rewards.GoldBrickReward.GUARANTEED_GOLD_BRICK;

public class TheWorld extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "TheWorld";
    public static final String MONSTER_ID = makeID("TheWorld");
    public static final String MAP_ICON = GranblueBosses.monsterPath("theworld/theworld_map_icon.png");
    public static final String OUTLINE = GranblueBosses.monsterPath("theworld/theworld_outline.png");
    protected static final int MONSTER_MAX_HP = 521;
    protected static final int MONSTER_MAX_HP_A_19 = 521 + 21;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 550.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "TheWorld".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final float[] OMEN_MULTS = new float[]{
            1.33f, 1.5f, 4f
    };
    protected int currOmenMultIndex = 0;

    public boolean phaseTransition = false;
    private int currPhase = 1;
    public int entropyStacks;
    public int meteorDmg;
    public int meteorHits;
    public int meteorStacks;
    public int projectedWorldStacks;
    public int celestialSphereDmg;
    public int celestialSphereBlock;
    public int celestialSphereStacks;
    public int theUltimateAnswerStacks;
    public int mebicometDmg;
    public int mebicometBaseDmg;
    public int mebicometNerfDmg;
    public int mebicometHits;
    public int starryNovaBlock;
    public int starryNovaStacks;
    public int terminalCrisisDmg;
    public int terminalCrisisStacksToInstakill;

    private static final int METEOR_INDEX = 0;
    private static final int CELESTIAL_SPHERE_INDEX = 1;
    private static final int MEBICOMET_INDEX = 2;
    private static final int MEBICOMET_NERF_INDEX = 3;
    private static final int TERMINAL_CRISIS_INDEX = 4;

    public int turnNum = 0;
    protected boolean isMebicometNerf = false;
    protected int mebicometOmenCancelDebuffAmt;

    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String WAIT_TURN;
    public static final String ENTROPY;
    public static final String METEOR;
    public static final String PROJECTED_WORLD;
    public static final String CELESTIAL_SPHERE;
    public static final String THE_ULTIMATE_ANSWER;
    public static final String MEBICOMET;
    public static final String STARRY_NOVA;
    public static final String TERIMINAL_CRISIS;

    protected boolean isDMCA;
    private String phase1Song;
    private String phase2Song;
    private String phase3Song;

    public TheWorld() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 10) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.entropyStacks = 1;
            this.meteorDmg = 9;
            this.meteorHits = 3;
            this.meteorStacks = 0;

            this.projectedWorldStacks = 5;

            this.celestialSphereDmg = 15;
            this.celestialSphereBlock = 15;
            this.celestialSphereStacks = 1;

            this.theUltimateAnswerStacks = 10;
            this.mebicometDmg = 15;
            this.mebicometBaseDmg = 15;
            this.mebicometNerfDmg = 6;
            this.mebicometHits = 1;
            this.mebicometOmenCancelDebuffAmt = 4;

            this.starryNovaBlock = 10;
            this.starryNovaStacks = 3;

            this.terminalCrisisDmg = 20;
            this.terminalCrisisStacksToInstakill = 5;

        } else if (AbstractDungeon.ascensionLevel >= 10) {
            this.entropyStacks = 2;
            this.meteorDmg = 8;
            this.meteorHits = 3;
            this.meteorStacks = 0;

            this.projectedWorldStacks = 3;

            this.celestialSphereDmg = 10;
            this.celestialSphereBlock = 10;
            this.celestialSphereStacks = 1;

            this.theUltimateAnswerStacks = 7;
            this.mebicometDmg = 10;
            this.mebicometBaseDmg = 10;
            this.mebicometNerfDmg = 5;
            this.mebicometHits = 1;
            this.mebicometOmenCancelDebuffAmt = 3;

            this.starryNovaBlock = 8;
            this.starryNovaStacks = 2;

            this.terminalCrisisDmg = 15;
            this.terminalCrisisStacksToInstakill = 7;
        } else {
            this.entropyStacks = 2;
            this.meteorDmg = 6;
            this.meteorHits = 3;
            this.meteorStacks = 0;

            this.projectedWorldStacks = 2;

            this.celestialSphereDmg = 10;
            this.celestialSphereBlock = 10;
            this.celestialSphereStacks = 1;

            this.theUltimateAnswerStacks = 7;
            this.mebicometDmg = 5;
            this.mebicometBaseDmg = 5;
            this.mebicometNerfDmg = 5;
            this.mebicometHits = 1;
            this.mebicometOmenCancelDebuffAmt = 2;

            this.starryNovaBlock = 6;
            this.starryNovaStacks = 2;

            this.terminalCrisisDmg = 10;
            this.terminalCrisisStacksToInstakill = 10;
        }

        this.currPhase = 1;
        this.phaseTransition = false;
        this.currOmenMultIndex = 0;

        this.isDMCA = ConfigMenu.enableDMCAMusic;
        if (this.isDMCA) {
            this.phase1Song = Sounds.MUSIC_ACT2_WORLD_P_1;
            this.phase2Song = Sounds.MUSIC_ACT2_WORLD_P_1;
            this.phase3Song = Sounds.MUSIC_ACT2_WORLD_P_2;
        } else {
            this.phase1Song = "BOSS_BOTTOM";
            this.phase2Song = "BOSS_CITY";
            this.phase3Song = "BOSS_BEYOND";
        }

        this.damage.add(new DamageInfo(this, this.meteorDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.celestialSphereDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.mebicometDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.mebicometNerfDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.terminalCrisisDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);

        this.state.setAnimation(0, "idle1", true);
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.fadeAll();

        this.currOmenMultIndex = 0;

        StanceOmen omen = new StanceOmen(this);
        omen.setUpOmenByHp(OMEN_MULTS[this.currOmenMultIndex]);
        addToTop(new ApplyPowerAction(this, this, omen));

        AbstractDungeon.getCurrRoom().playBgmInstantly(this.phase1Song);

        super.usePreBattleAction();


    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useWaiting();
                break;
            case 1:
                this.useMeteor();
                break;
            case 2:
                this.useEntropy();
                break;
            case 3:
                if (this.currPhase == 1){
                    this.useProjectedWorld();
                } else {
                    this.useTheUltimateAnswer();
                }
                break;
            case 4:
                this.useCelestialSphere();
                break;
            case 5:
                this.useMebicomet();
                break;
            case 6:
                this.useStarryNova();
                break;
            case 7:
                this.useTerminalCrisis();
                break;
        }
        this.prepareIntent();
    }

    // Phase 1
//      "Waiting",
//      "Entropy",
//      "Meteor",
//      "Projected World",
//      "Celestial Sphere",
//      "The Ultimate Answer",
//      "Mebicomet",
//      "Starry Nova",
//      "Terminal Crisis"

    public void damage(DamageInfo info) {

        super.damage(info);
        switch (this.currOmenMultIndex){
            case 0:
                if (!this.isDying && this.currentHealth * OMEN_MULTS[this.currOmenMultIndex] <= this.maxHealth && this.currPhase == 1){
                    this.setMove(PROJECTED_WORLD, (byte)3, Intent.DEFEND_BUFF);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, PROJECTED_WORLD, (byte)3, Intent.DEFEND_BUFF));

//                    this.currOmenMultIndex = 1;
//                    ((StanceOmen) this.getPower(StanceOmen.POWER_ID)).setUpOmenByHp(OMEN_MULTS[this.currOmenMultIndex]);
                }
                break;
            case 1:
                if (!this.isDying && this.currentHealth * OMEN_MULTS[this.currOmenMultIndex] <= this.maxHealth && this.currPhase == 2){
                    this.setMove(THE_ULTIMATE_ANSWER, (byte)3, Intent.BUFF);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, THE_ULTIMATE_ANSWER, (byte)3, Intent.BUFF));
                }
                break;
        }
    }

    protected void prepareIntent() {
        if (!this.isDying && this.currentHealth * OMEN_MULTS[this.currOmenMultIndex] <= this.maxHealth && this.currOmenMultIndex == 2 && this.currPhase == 3 && this.hasPower(StanceOmen.POWER_ID)){
            if (this.hasPower(TheWorldTerminalPower.POWER_ID) && this.getPower(TheWorldTerminalPower.POWER_ID).amount >= this.terminalCrisisStacksToInstakill){
                this.setMove(TERIMINAL_CRISIS, (byte)7, CustomIntentEnums.INSTAKILL);
                this.createIntent();
                addToBot(new SetMoveAction(this, TERIMINAL_CRISIS, (byte)7, CustomIntentEnums.INSTAKILL));

                addToBot(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));

                addToBot(new SFXAction(Sounds.WORLD_DIALOG_CRISIS_PREP));
                addToBot(new ShoutAction(this, "Your death will not be meaningless"));

                this.createIntent();
                this.applyOmen();

                return;
            }

            this.setMove(TERIMINAL_CRISIS, (byte)7, Intent.ATTACK, this.damage.get(TERMINAL_CRISIS_INDEX).base, 1, false);
            this.createIntent();
            addToBot(new SetMoveAction(this, TERIMINAL_CRISIS, (byte)7, Intent.ATTACK, this.damage.get(TERMINAL_CRISIS_INDEX).base, 1, false));

            addToBot(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));

            addToBot(new SFXAction(Sounds.WORLD_DIALOG_CRISIS_PREP));
            addToBot(new ShoutAction(this, "Your death will not be meaningless"));

            this.createIntent();
            this.applyOmen();

            return;
        }

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.prepareIntentA17();
            this.createIntent();
            this.applyOmen();
            return;
        }

        switch (this.nextMove) {
            case 0:
                this.prepareAuxMove();
                break;
            case 1:
                this.prepareAuxMove();
                break;
            case 2:
                this.prepareAttackMove();
                break;
            case 3:
                if (this.currPhase != 3){
                    this.prepareAuxMove();
                } else {
                    this.prepareAttackMove();
                }
                break;
            case 4:
                this.prepareAttackMove();
                break;
            case 5:
                this.prepareAuxMove();
                break;
            case 6:
                this.prepareAttackMove();
                break;
            case 7:
                this.prepareAuxMove();
                break;
        }
        this.createIntent();
        this.applyOmen();
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 0:
                if (AbstractDungeon.aiRng.randomBoolean()){
                    this.prepareAuxMove();
                } else {
                    this.prepareAttackMove();
                }
                break;
            case 1:
                this.prepareAuxMove();
                break;
            case 2:
                this.prepareAttackMove();
                break;
            case 3:
                if (this.currPhase != 3){
                    this.prepareAuxMove();
                } else {
                    this.prepareAttackMove();
                }
                break;
            case 4:
                this.prepareAttackMove();
                break;
            case 5:
                this.prepareAuxMove();
                break;
            case 6:
                this.prepareAttackMove();
                break;
            case 7:
                this.prepareAuxMove();
                break;
        }
    }

    public void useWaiting(){
        // Stun turn
        // Do noting
    }

    public void useEntropy(){
//        Entropy:
//        Voiceline: "A new Order"
//        Shuffle Dazed (Burn) + Apply Weak

        addToBot(new SFXAction(Sounds.WORLD_DIALOG_ENTROPY_ATK));
        addToBot(new ShoutAction(this, "A new Order..."));

        addToBot(new AnimateSlowAttackAction(this));

        AbstractCard status = AbstractDungeon.ascensionLevel >= 19 ? new Burn() : new Dazed();

        addToBot(new MakeTempCardInDrawPileAction(status, this.entropyStacks, true, true));
    }

    public void useMeteor(){
//        Meteor:
//        Voiceline: "Accept fate"
//        Deal 3-hit damage

        addToBot(new SFXAction(Sounds.WORLD_DIALOG_METEOR_ATK));
        addToBot(new ShoutAction(this, "Accept fate."));

        float meteorHitX;
        float meteorHitY;

        for (int i = 0; i < this.meteorHits; i++) {
            meteorHitX = AbstractDungeon.player.hb.cX + ((AbstractDungeon.monsterRng.random() - 0.5f) * 0.5f * AbstractDungeon.player.hb.width);
            meteorHitY = AbstractDungeon.player.hb.cY + ((AbstractDungeon.monsterRng.random() - 0.5f) * 0.5f * AbstractDungeon.player.hb.height);

            addToBot(new VFXAction(new FireballEffect(this.hb.cX, this.hb.y + this.hb.height, meteorHitX, meteorHitY)));

            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(METEOR_INDEX), AbstractGameAction.AttackEffect.FIRE));
        }

    }

    public void useProjectedWorld(){
//        Projected World
//        Voiceline: "Let us execute the necessary ritual"
//        Gain Barricade
//        Gain 1 (2) stacks of Projected World
//        Works like Thorns
//        Each turn, a stack is replaced by Strength

        addToBot(new SFXAction(Sounds.WORLD_DIALOG_PROJECTED_ATK));
        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("theworld/World_Transition_Phase2.webm"))));
//        addToBot(new SFXAction(Sounds.WORLD_DIALOG_PROJECTED_ATK));
//        addToBot(new ShoutAction(this, "Let us execute the necessary ritual."));


        if (!this.phase1Song.equals(this.phase2Song)){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(this.phase2Song);
        }

        this.state.setAnimation(0, "idle2", false);
        this.currPhase = 2;
        this.currOmenMultIndex = 1;
        ((StanceOmen) this.getPower(StanceOmen.POWER_ID)).setUpOmenByHp(OMEN_MULTS[this.currOmenMultIndex]);
        this.state.setTimeScale(1.0F);

        addToBot(new ApplyPowerAction(this, this, new TheWorldSpherePower(this, this.projectedWorldStacks), this.projectedWorldStacks));

        addToBot(new ApplyPowerAction(this, this, new BarricadePower(this)));
    }

    public void useCelestialSphere(){
//        Celestial Sphere
//        Voiceline: "Still incomplete"
//        Deal small damage + Gain Block

        addToBot(new SFXAction(Sounds.WORLD_DIALOG_SPHERE_ATK));
        addToBot(new ShoutAction(this, "Still incomplete."));
        addToBot(new AnimateShakeAction(this, 0.3f, 0.3f));

        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.PURPLE, ShockWaveEffect.ShockWaveType.NORMAL)));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(CELESTIAL_SPHERE_INDEX), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        addToBot(new GainBlockAction(this, this.celestialSphereBlock));

    }

    public void useTheUltimateAnswer(){
//        The Ultimate Answer
//        Voiceline: "This is the birth a new God"
//        Gain 10 stacks of Terminal
//        Increases number of hits of Mebicomet
//        Increases dmg of Terminal Crisis
//        Reduces by 1 per omen canceled
//        Prepare Mebicomet for next turn

        addToBot(new SFXAction(Sounds.WORLD_DIALOG_ANSWER_ATK));
        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("theworld/World_Transition_Phase3.webm"))));
//        addToBot(new ShoutAction(this, "This is the birth a new God!"));

        if (!this.phase2Song.equals(this.phase3Song)){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(this.phase3Song);
        }

        this.state.setAnimation(0, "idle3", false);

        this.currPhase = 3;
        this.currOmenMultIndex = 2;
        ((StanceOmen) this.getPower(StanceOmen.POWER_ID)).setUpOmenByHp(OMEN_MULTS[this.currOmenMultIndex]);
        this.state.setTimeScale(1.0F);

        addToBot(new RemoveSpecificPowerAction(this, this, TheWorldSpherePower.POWER_ID));

        addToBot(new ApplyPowerAction(this, this, new TheWorldTerminalPower(this, this.theUltimateAnswerStacks), this.theUltimateAnswerStacks));

    }

    public void useMebicomet(){
        // Mebicomet (First use)
        //Voiceline on prep: "Everything is within my reach"
        //Voiceline on attack: "Take this"
        //    Player gains 1 layer of buffer per Arcarum Relic/Card they have.
        //        Voiceline (after attack) if Arcarum: "Damn you, you rebels"
        //        The world loses stacks of Terminal per each buffer
        //    Deal 5 damage as many times as Terminal

        // ==========================================================

        // Mebicomet (Nerfed)
        //Voiceline on prep: "Witness"
        //Voiceline: *groan exclamation*
        //Voiceline on cancel: "Don't get cocky"
        //    Deal (5 - Strength / 2) damage as many times as Terminal

        if (!this.isMebicometNerf){

            int arcarumObtained = this.getArcarumPrimalsAmount();

            if (arcarumObtained > 0){
                addToBot(new SFXAction(Sounds.WORLD_DIALOG_MEBICOMET_CUTSCENE));
                addToBot(new ShoutAction(this, "Damn you, you rebels!"));

                this.flashArcarumPrimals();

                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new BufferPower(AbstractDungeon.player, arcarumObtained), arcarumObtained));


                addToBot(new ReducePowerAction(this, this, TheWorldTerminalPower.POWER_ID, arcarumObtained));

            } else {
                addToBot(new SFXAction(Sounds.WORLD_DIALOG_MEBICOMET_TRIGGER_ATK));
                addToBot(new ShoutAction(this, "Take this!"));
            }

            float meteorHitX;
            float meteorHitY;

            for (int i = 0; i < this.mebicometHits; i++) {
                meteorHitX = AbstractDungeon.player.hb.cX + ((AbstractDungeon.monsterRng.random() - 0.5f) * 0.5f * AbstractDungeon.player.hb.width);
                meteorHitY = AbstractDungeon.player.hb.cY + ((AbstractDungeon.monsterRng.random() - 0.5f) * 0.5f * AbstractDungeon.player.hb.height);

                addToBot(new VFXAction(new FireballEffect(this.hb.cX, this.hb.y + this.hb.height, meteorHitX, meteorHitY)));

                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(MEBICOMET_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

            }
        } else {
            addToBot(new SFXAction(Sounds.WORLD_DIALOG_MEBICOMET_ATK));

            float meteorHitX;
            float meteorHitY;
            for (int i = 0; i < this.mebicometHits; i++) {
                meteorHitX = AbstractDungeon.player.hb.cX + ((AbstractDungeon.monsterRng.random() - 0.5f) * 0.5f * AbstractDungeon.player.hb.width);
                meteorHitY = AbstractDungeon.player.hb.cY + ((AbstractDungeon.monsterRng.random() - 0.5f) * 0.5f * AbstractDungeon.player.hb.height);

                addToBot(new VFXAction(new FireballEffect(this.hb.cX, this.hb.y + this.hb.height, meteorHitX, meteorHitY)));

                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(MEBICOMET_NERF_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
    }

    public void useStarryNova(){
        // Starry Nova
        //Voiceline on prep: "A trial, huh"
        //Voiceline on attack: "This feeling... not bad"
        //Voiceline on cancel: "The conclusion will not change"
        //    Gain Strength based on the amount of Terminal stacks lost (Min 2)
        //    Omen:
        //         -> Play Arcarum card
        //         -> Land 3 debuffs
        //    Stun on cancel

        // ==========================================================


        addToBot(new SFXAction(Sounds.WORLD_DIALOG_STARRY_ATK));
        addToBot(new ShoutAction(this, "This feeling... not bad."));
        addToBot(new AnimateShakeAction(this, 0.7f, 0.7f));

        if (this.hasPower(TheWorldTerminalPower.POWER_ID) && this.getPower(TheWorldTerminalPower.POWER_ID).amount > this.starryNovaStacks){
            addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.getPower(TheWorldTerminalPower.POWER_ID).amount), this.getPower(TheWorldTerminalPower.POWER_ID).amount));
        } else {
            addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.starryNovaStacks), this.starryNovaStacks));
        }
    }

    public void useTerminalCrisis(){
        // Terminal Crisis (Trigger 25% and each turn under 15%)
        //Voiceline on prep: "Your death will not be meaningless"
        //Voiceline on instakill: "I AM GOD"
        //Voiceline on attack: "Your is a necessary sacrifice"
        //Voiceline on cancel: "I won't forgive you"
        //    If Terminal stacks <= 5: Deal (10%  * Terminal stacks) of Max HP as Dmg (Min 20%)
        //    If Terminal stacks > 5: Instakill
        //    Omen:
        //        -> Play Arcarum card
        //        -> Land 3 debuffs
        //        -> Deal (10 * Terminal stacks) damage
        //    Stun on cancel

        if (this.hasPower(TheWorldTerminalPower.POWER_ID) && this.getPower(TheWorldTerminalPower.POWER_ID).amount >= this.terminalCrisisStacksToInstakill){

            addToBot(new SFXAction(Sounds.WORLD_DIALOG_CRISIS_KILL));
            addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("theworld/World_Terminal_Crisis.webm"))));
//            addToBot(new ShoutAction(this, "I am... GOD!"));

//            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EndTurnDeathPower(AbstractDungeon.player)));
//            addToBot(new InstantKillAction(AbstractDungeon.player));

            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 99999));

        } else {
            addToBot(new SFXAction(Sounds.WORLD_DIALOG_CRISIS_ATK));
            addToBot(new ShoutAction(this, "Your is a necessary sacrifice"));

            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));

            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(TERMINAL_CRISIS_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        }

//        addToBot(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));
    }

    @Override
    protected void getMove(int i) {
        this.turnNum++;
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(ENTROPY, (byte)2, Intent.DEBUFF);
            this.createIntent();
        }
    }

//      "Mebicomet",
//    Omen:
//        -> Play Arcarum card
//        -> Land 3 debuffs

//      "Starry Nova",
//    Omen:
//         -> Play Arcarum card
//         -> Deal (10 * Terminal stacks) damage

//      "Terminal Crisis"
//    Omen:
//        -> Play Arcarum card
//        -> Land 3 debuffs
//        -> Deal (10 * Terminal stacks) damage

    @Override
    public void applyOmen() {
        AbstractIncantedOmen[] omens = new AbstractIncantedOmen[3];

//        if (this.nextMove >= 6 || (this.nextMove == 5 && this.isMebicometNerf)){
//            omens[0] = new IncantedOmenCardPlayed(this, 1, CustomTags.ARCARUM_CALL);
//        }

        if ((this.nextMove == 5 && this.isMebicometNerf) || this.nextMove == 7){
            omens[1] = new IncantedOmenPowersApplied(this, this.mebicometOmenCancelDebuffAmt, AbstractPower.PowerType.DEBUFF, false, true);
        }

        if (this.nextMove == 6 || this.nextMove == 7){
            omens[2] = new IncantedOmenDamage(this,
                    this.hasPower(TheWorldTerminalPower.POWER_ID)?
                            this.getPower(TheWorldTerminalPower.POWER_ID).amount * Math.min(AbstractDungeon.ascensionLevel/2, 10) :
                            Math.min(AbstractDungeon.ascensionLevel/2, 10),
                    null
                    );
        }

        for (AbstractIncantedOmen omen : omens){
            if (omen != null) addToBot(new ApplyPowerAction(this, this, omen));
        }
    }

    @Override
    public void resolveOmen() {
        switch (this.nextMove){
            case 5:
                addToBot(new SFXAction(Sounds.WORLD_DIALOG_MEBICOMET_CANCEL));
                addToBot(new ShoutAction(this, "Don't get cocky."));
                break;
            case 6:
                addToBot(new SFXAction(Sounds.WORLD_DIALOG_STARRY_CANCEL));
                addToBot(new ShoutAction(this, "The conclusion will not change"));
                break;
            case 7:
                addToBot(new SFXAction(Sounds.WORLD_DIALOG_CRISIS_CANCEL));
                addToBot(new ShoutAction(this, "I won't forgive you"));
                break;
        }

        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenCardPlayed.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenPowersApplied.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenDamage.POWER_ID));

        this.setMove((byte)0, Intent.STUN);
        this.createIntent();
        addToTop(new SetMoveAction(this, (byte)0, Intent.STUN));

        OmenUtils.onCancelOmenSFX(this);

    }

    public void prepareAttackMove(){

        switch (this.currPhase){
            case 2:
                this.setMove(CELESTIAL_SPHERE, (byte)4, Intent.ATTACK_DEFEND, this.damage.get(CELESTIAL_SPHERE_INDEX).base, 1, false);
                this.createIntent();
                addToBot(new SetMoveAction(this, CELESTIAL_SPHERE, (byte)4, Intent.ATTACK_DEFEND, this.damage.get(CELESTIAL_SPHERE_INDEX).base, 1, false));
                break;
            case 3:
                if (this.hasPower(TheWorldTerminalPower.POWER_ID) && this.getPower(TheWorldTerminalPower.POWER_ID).amount >= (this.terminalCrisisStacksToInstakill - 1) * 2){
                    this.setMove(TERIMINAL_CRISIS, (byte)7, CustomIntentEnums.INSTAKILL);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, TERIMINAL_CRISIS, (byte)7, CustomIntentEnums.INSTAKILL));
                    addToBot(new SFXAction(Sounds.WORLD_DIALOG_CRISIS_PREP));
                    addToBot(new ShoutAction(this, "Your death will not be meaningless"));
                    return;
                }

                if (this.currentHealth * 5 < this.maxHealth &&
                    (
                    !this.hasPower(TheWorldTerminalPower.POWER_ID) ||
                    (this.hasPower(TheWorldTerminalPower.POWER_ID) && this.getPower(TheWorldTerminalPower.POWER_ID).amount <= this.terminalCrisisStacksToInstakill)
                    )
                ){
                    this.terminalCrisisDmg = this.hasPower(TheWorldTerminalPower.POWER_ID)?
                            Math.max(this.getPower(TheWorldTerminalPower.POWER_ID).amount * 10, 20):
                            20;

                    this.damage.set(TERMINAL_CRISIS_INDEX, new DamageInfo(this, this.terminalCrisisDmg, DamageInfo.DamageType.NORMAL));
                    this.damage.get(TERMINAL_CRISIS_INDEX).applyPowers(this, AbstractDungeon.player);

                    this.setMove(TERIMINAL_CRISIS, (byte)7, Intent.ATTACK, this.damage.get(TERMINAL_CRISIS_INDEX).base, 1, false);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, TERIMINAL_CRISIS, (byte)7, Intent.ATTACK, this.damage.get(TERMINAL_CRISIS_INDEX).base, 1, false));

                    addToBot(new SFXAction(Sounds.WORLD_DIALOG_CRISIS_PREP));
                    addToBot(new ShoutAction(this, "Your death will not be meaningless"));

                    return;

                } else if (this.currentHealth * 5 < this.maxHealth) {
                    this.setMove(TERIMINAL_CRISIS, (byte)7, CustomIntentEnums.INSTAKILL);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, TERIMINAL_CRISIS, (byte)7, CustomIntentEnums.INSTAKILL));

                    addToBot(new SFXAction(Sounds.WORLD_DIALOG_CRISIS_PREP));
                    addToBot(new ShoutAction(this, "Your death will not be meaningless"));

                    return;
                }

                this.mebicometDmg = !this.isMebicometNerf ?
                        this.mebicometBaseDmg :
                        this.hasPower(StrengthPower.POWER_ID) ?
                        Math.max(this.mebicometNerfDmg - (this.getPower(StrengthPower.POWER_ID).amount/2), this.mebicometNerfDmg) :
                        this.mebicometNerfDmg;

                this.damage.set(MEBICOMET_INDEX, new DamageInfo(this, this.mebicometDmg, DamageInfo.DamageType.NORMAL));
                this.damage.get(MEBICOMET_INDEX).applyPowers(this, AbstractDungeon.player);

                this.mebicometHits = !this.isMebicometNerf ?
                        this.theUltimateAnswerStacks :
                        this.hasPower(TheWorldTerminalPower.POWER_ID) ?
                        Math.max(this.getPower(TheWorldTerminalPower.POWER_ID).amount, 2) :
                        2;

                this.setMove(MEBICOMET, (byte)5, Intent.ATTACK, this.damage.get(METEOR_INDEX).base, this.mebicometHits, true);
                this.createIntent();
                addToBot(new SetMoveAction(this, MEBICOMET, (byte)5, Intent.ATTACK, this.damage.get(METEOR_INDEX).base, this.mebicometHits, true));


                if (this.isMebicometNerf){
                    addToBot(new SFXAction(Sounds.WORLD_DIALOG_MEBICOMET_PREP));
                    addToBot(new ShoutAction(this, "Witness."));
                } else {
                    addToBot(new SFXAction(Sounds.WORLD_DIALOG_MEBICOMET_TRIGGER_PREP));
                    addToBot(new ShoutAction(this, "Everything is within my reach."));
                }

                break;
            case 1:
                this.setMove(METEOR, (byte)1, Intent.ATTACK, this.damage.get(METEOR_INDEX).base, this.meteorHits, true);
                this.createIntent();
                addToBot(new SetMoveAction(this, METEOR, (byte)1, Intent.ATTACK, this.damage.get(METEOR_INDEX).base, this.meteorHits, true));
            default:
                break;
        }
    }

    public void prepareAuxMove(){
        switch (this.currPhase){
            case 2:
                this.setMove(CELESTIAL_SPHERE, (byte)4, Intent.ATTACK_DEFEND, this.damage.get(CELESTIAL_SPHERE_INDEX).base, 1, false);
                this.createIntent();
                addToBot(new SetMoveAction(this, CELESTIAL_SPHERE, (byte)4, Intent.ATTACK_DEFEND, this.damage.get(CELESTIAL_SPHERE_INDEX).base, 1, false));
                break;
            case 3:
                if (this.hasPower(TheWorldTerminalPower.POWER_ID) && this.getPower(TheWorldTerminalPower.POWER_ID).amount >= this.terminalCrisisStacksToInstakill * 2){
                    this.setMove(TERIMINAL_CRISIS, (byte)7, CustomIntentEnums.INSTAKILL);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, TERIMINAL_CRISIS, (byte)7, CustomIntentEnums.INSTAKILL));
                    return;
                }

                if (this.currentHealth * 6 < this.maxHealth &&
                        (
                                !this.hasPower(TheWorldTerminalPower.POWER_ID) ||
                                        (this.hasPower(TheWorldTerminalPower.POWER_ID) && this.getPower(TheWorldTerminalPower.POWER_ID).amount <= this.terminalCrisisStacksToInstakill)
                        )
                ){
                    this.terminalCrisisDmg = this.hasPower(TheWorldTerminalPower.POWER_ID)?
                            Math.max(this.getPower(TheWorldTerminalPower.POWER_ID).amount * 10, 20):
                            20;

                    this.damage.set(TERMINAL_CRISIS_INDEX, new DamageInfo(this, this.terminalCrisisDmg, DamageInfo.DamageType.NORMAL));
                    this.damage.get(TERMINAL_CRISIS_INDEX).applyPowers(this, AbstractDungeon.player);

                    this.setMove(TERIMINAL_CRISIS, (byte)7, Intent.ATTACK, this.damage.get(TERMINAL_CRISIS_INDEX).base, 1, false);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, TERIMINAL_CRISIS, (byte)7, Intent.ATTACK, this.damage.get(TERMINAL_CRISIS_INDEX).base, 1, false));

                    addToBot(new SFXAction(Sounds.WORLD_DIALOG_CRISIS_PREP));
                    addToBot(new ShoutAction(this, "Your death will not be meaningless"));

                    return;

                } else if (this.currentHealth * 6 < this.maxHealth) {
                    this.setMove(TERIMINAL_CRISIS, (byte)7, CustomIntentEnums.INSTAKILL);
                    this.createIntent();
                    addToBot(new SetMoveAction(this, TERIMINAL_CRISIS, (byte)7, CustomIntentEnums.INSTAKILL));

                    addToBot(new SFXAction(Sounds.WORLD_DIALOG_CRISIS_PREP));
                    addToBot(new ShoutAction(this, "Your death will not be meaningless"));

                    return;
                }

                this.setMove(STARRY_NOVA, (byte)6, Intent.BUFF);
                this.createIntent();
                addToBot(new SetMoveAction(this, STARRY_NOVA, (byte)6, Intent.BUFF));


                addToBot(new SFXAction(Sounds.WORLD_DIALOG_STARRY_PREP));
                addToBot(new ShoutAction(this, "A trial, huh?"));

                break;
            case 1:
                this.setMove(ENTROPY, (byte)2, Intent.DEBUFF);
                this.createIntent();
                addToBot(new SetMoveAction(this, ENTROPY, (byte)2, Intent.DEBUFF));
            default:
                break;
        }
    }

    private int getArcarumPrimalsAmount(){
        if (AbstractDungeon.player == null || AbstractDungeon.player.relics == null || AbstractDungeon.player.masterDeck == null || AbstractDungeon.player.masterDeck.group == null) return 0;
        int retVal = 0;

        for (AbstractRelic r : AbstractDungeon.player.relics){
            if (r.relicId.contains("Arcarum")) retVal++;
        }

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (c.cardID.contains("Arcarum")) retVal++;
        }

        return retVal;
    }

    private void flashArcarumPrimals(){
        if (AbstractDungeon.player == null || AbstractDungeon.player.relics == null || AbstractDungeon.player.masterDeck == null || AbstractDungeon.player.masterDeck.group == null) return;

        for (AbstractRelic r : AbstractDungeon.player.relics){
            if (r.relicId.contains("Arcarum"))
                r.flash();
        }

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (c.cardID.contains("Arcarum"))
                AbstractDungeon.effectList.add(new ShowCardEffect(c));
        }
    }

    @Override
    public void die() {

        // On Death Voiceline: "So you would oppose fate?"

        addToBot(new SFXAction(Sounds.WORLD_DIALOG_DEATH));
        addToBot(new ShoutAction(this, "So you would oppose fate?"));

        if (ConfigMenu.enableExtraRewards) {

            if (AbstractDungeon.ascensionLevel >= 5){
                MonsterUtils.addFullHealReward();
            }

            if ((AbstractDungeon.treasureRng.randomBoolean(0.02f) || GUARANTEED_GOLD_BRICK) && AbstractDungeon.player.masterDeck.hasUpgradableCards()){
                MonsterUtils.addGoldBrickReward();
            }

            if (AbstractDungeon.player == null){
                MonsterUtils.handleCardPlusRelicLinkedReward(new WorldforgingMorosRelic(), new InchoateWorldCard());
            } else {
                switch (AbstractDungeon.player.chosenClass){
                    case IRONCLAD:
                        MonsterUtils.handleCardPlusRelicLinkedReward(new WorldscathingLeonRelic(), new InchoateWorldCard());
                        break;
                    case THE_SILENT:
                        MonsterUtils.handleCardPlusRelicLinkedReward(new WorldstormingAetosRelic(), new InchoateWorldCard());
                        break;
                    case DEFECT:
                        MonsterUtils.handleCardPlusRelicLinkedReward(new WorldbreakingTaurosRelic(), new InchoateWorldCard());
                        break;
                    case WATCHER:
                        MonsterUtils.handleCardPlusRelicLinkedReward(new WorldvexingAngelosRelic(), new InchoateWorldCard());
                        break;
                    default:
                        MonsterUtils.handleCardPlusRelicLinkedReward(new WorldforgingMorosRelic(), new InchoateWorldCard());
                        break;
                }
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
        ENTROPY = MOVES[1];
        METEOR = MOVES[2];
        PROJECTED_WORLD = MOVES[3];
        CELESTIAL_SPHERE = MOVES[4];
        THE_ULTIMATE_ANSWER = MOVES[5];
        MEBICOMET = MOVES[6];
        STARRY_NOVA = MOVES[7];
        TERIMINAL_CRISIS = MOVES[8];
    }
}


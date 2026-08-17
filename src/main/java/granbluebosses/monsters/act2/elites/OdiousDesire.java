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
import com.megacrit.cardcrawl.cards.blue.SelfRepair;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RepairPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.action.DispelBuffAction;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.odious.OdiousNarophirmidasCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.intents.enums.CustomIntentEnums;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.aMonsters.ColossalBodyPower;
import granbluebosses.powers.aMonsters.act2.GildedHeavenPower;
import granbluebosses.powers.aMonsters.act2.GoldenCorruptionPower;
import granbluebosses.powers.incantedOmens.AbstractIncantedOmen;
import granbluebosses.powers.incantedOmens.IncantedOmenCardPlayed;
import granbluebosses.powers.incantedOmens.IncantedOmenPowersApplied;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.odious.OdiousDemonedge;
import granbluebosses.relics.odious.OdiousSealhammer;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;
import static granbluebosses.GranblueBosses.videoPath;

public class OdiousDesire extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "Narophirmidas";
    public static final String MONSTER_ID = makeID("OdiousDesire");
    protected static final int MONSTER_MAX_HP = 198;
    protected static final int MONSTER_MAX_HP_A_19 = 198 + 58;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "OdiousDesire".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected float OMEN_MULT = 1.6f;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public int taavaphaonehOmenAmt;

    public int pyrgodaxthlaoDmg;
    public int pyrgodaxthlaoStacks;

    public int taavaphaonehDmg;
    public int taavaphaonehBlock;
    public int taavaphaonehStacks;

    public int zahavmeshulDmg;

    public static final int PYRGODAXTHLAO_INDEX = 0;
    public static final int TAAVAPHAONEH_INDEX = 1;
    public static final int ZAHAVMESHUL_INDEX = 2;


    public OdiousDesire() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);



        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(MONSTER_MAX_HP_A_19);
            this.taavaphaonehBlock = 10;
        } else {
            this.setHp(MONSTER_MAX_HP);
            this.taavaphaonehBlock = 7;
        }

        if (AbstractDungeon.ascensionLevel >= 18){
            this.taavaphaonehOmenAmt = 2;
            this.pyrgodaxthlaoDmg = 10;
            this.pyrgodaxthlaoStacks = 2;
            this.taavaphaonehDmg = 22;
            this.taavaphaonehStacks = 1;
            this.zahavmeshulDmg = 1;

        } else {
            this.taavaphaonehOmenAmt = 2;
            this.pyrgodaxthlaoDmg = 7;
            this.pyrgodaxthlaoStacks = 2;
            this.taavaphaonehDmg = 22;
            this.taavaphaonehStacks = 3;
            this.zahavmeshulDmg = 1;

        }

        this.damage.add(new DamageInfo(this, this.pyrgodaxthlaoDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.taavaphaonehDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.zahavmeshulDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {

        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_ELITE_EARTH);
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
                this.usePyrgodaxthlao();
                break;
            case 2:
                this.useTaavaphaoneh();
                break;
            case 3:
                this.useZahavmeshul();
                break;
        }

        this.prepareIntent();
        this.createIntent();

        this.createIntent();
        this.applyOmen();
    }

    public void useStunTurn(){
        // Does nothing. It's here for consistency
    }

    public void usePyrgodaxthlao(){
//        Deal damDeal small damage + Apply Vulnerable + Apply Golden Corruption
//	        Omen: Remove ALL Guilden Heaven
//          Stun on cancel

        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, Color.YELLOW)));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(PYRGODAXTHLAO_INDEX), AbstractGameAction.AttackEffect.NONE));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new GoldenCorruptionPower(AbstractDungeon.player, this, this.pyrgodaxthlaoStacks), this.pyrgodaxthlaoStacks));

        if (!this.hasPower(GildedHeavenPower.POWER_ID)) addToBot(new ApplyPowerAction(this, this, new GildedHeavenPower(this, 0)));
    }

    public void useTaavaphaoneh(){
        // Deal damage + Gain Block + Apply Golden Corruption
        //	    Omen: Infict Debuffs
        //      Stun on cancel

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new LaserBeamEffect(this.hb.cX, this.hb.cY), 1.5F));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(TAAVAPHAONEH_INDEX), AbstractGameAction.AttackEffect.NONE));

        addToBot(new GainBlockAction(this, this.taavaphaonehBlock));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new GoldenCorruptionPower(AbstractDungeon.player, this, this.taavaphaonehStacks), this.taavaphaonehStacks));

        if (!this.hasPower(GildedHeavenPower.POWER_ID)) addToBot(new ApplyPowerAction(this, this, new GildedHeavenPower(this, 0)));

        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenCardPlayed.POWER_ID));
    }

    public void useZahavmeshul(){
//        Deal damage equal to 50% of player's HP + Remove 1 (all) Player buff + Apply Golden Corruption + Self Repair for damage dealt
//	        Omen: Remove ALL Guilden Heaven
//          Stun on cancel

        addToBot(new SFXAction(Sounds.SFX_ACT2_ELITE_EARTH));
        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("odiousdesire/OdiousEarthAnimNoSFX.webm"))));


        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(ZAHAVMESHUL_INDEX), AbstractGameAction.AttackEffect.NONE));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new RepairPower(AbstractDungeon.player, this.damage.get(ZAHAVMESHUL_INDEX).output - AbstractDungeon.player.currentBlock), this.damage.get(ZAHAVMESHUL_INDEX).output - AbstractDungeon.player.currentBlock));

        for (int i = 0; AbstractDungeon.player.hasPower(GoldenCorruptionPower.POWER_ID) && i < AbstractDungeon.player.getPower(GoldenCorruptionPower.POWER_ID).amount; i++){
            addToBot(new DispelBuffAction(AbstractDungeon.player, this, 1));
        }
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, GoldenCorruptionPower.POWER_ID));
        
    }

    public void onRemoveGildedHeaven(){
        switch (this.nextMove){
            case 1:
            case 3:

                GranblueBosses.logger.info("Gilded Heaven Stun Attempt Success");

                this.setMove((byte) 0, Intent.STUN);
                this.createIntent();
                addToBot(new SetMoveAction(this, (byte) 0, Intent.STUN));
                
                break;
            default:
                GranblueBosses.logger.info("Gilded Heaven Stun Attempt Failed");
                GranblueBosses.logger.info("Next move index: " + this.nextMove);
                break;
        }
    }

    protected void prepareIntent() {
        if (
                (this.hasPower(GildedHeavenPower.POWER_ID) && this.getPower(GildedHeavenPower.POWER_ID).amount >= 10) ||
                (AbstractDungeon.ascensionLevel >= 18 && this.currentHealth * this.OMEN_MULT <= this.maxHealth && this.trigger)){

            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));
            this.trigger = false;
            
            if (!this.hasPower(StunMonsterPower.POWER_ID) && this.nextMove != 0){
                this.damage.set(ZAHAVMESHUL_INDEX, new DamageInfo(this,
                        Math.max(
                                AbstractDungeon.player.maxHealth / 2,
                                AbstractDungeon.player.currentHealth * (AbstractDungeon.ascensionLevel > 18 ? 2 : 1))
                        )
                );
                this.damage.get(ZAHAVMESHUL_INDEX).applyPowers(this, AbstractDungeon.player);

                addToBot(new SetMoveAction(this, MOVES[2], (byte)3, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(ZAHAVMESHUL_INDEX).base, 1, false));
                this.setMove(MOVES[2], (byte)3, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(ZAHAVMESHUL_INDEX).base, 1, false);
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
                addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.ATTACK_DEFEND, this.damage.get(TAAVAPHAONEH_INDEX).base, 1, false));
                this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(TAAVAPHAONEH_INDEX).base, 1, false);
                break;
            case 2:
                addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(PYRGODAXTHLAO_INDEX).base, 1, false));
                this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(PYRGODAXTHLAO_INDEX).base, 1, false);
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
                addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(PYRGODAXTHLAO_INDEX).base, 1, false));
                this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(PYRGODAXTHLAO_INDEX).base, 1, false);
                break;
            case 1:
                addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.ATTACK_DEFEND, this.damage.get(TAAVAPHAONEH_INDEX).base, 1, false));
                this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(TAAVAPHAONEH_INDEX).base, 1, false);
                break;
        }
    }

    protected void prepareRandomCommonMove(){
        if (AbstractDungeon.aiRng.randomBoolean()) {
            addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(PYRGODAXTHLAO_INDEX).base, 1, false));
            this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(PYRGODAXTHLAO_INDEX).base, 1, false);
        } else {
            addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.ATTACK_DEFEND, this.damage.get(TAAVAPHAONEH_INDEX).base, 1, false));
            this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(TAAVAPHAONEH_INDEX).base, 1, false);
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(PYRGODAXTHLAO_INDEX).base, 1, false);
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

        this.setMove((byte)0, Intent.STUN);
        this.createIntent();
        addToTop(new SetMoveAction(this, (byte)0, Intent.STUN));
        
        OmenUtils.onCancelOmenSFX(this);
    }



    @Override
    public void applyOmen() {
        AbstractIncantedOmen omen = this.nextMove == 2 ?
                new IncantedOmenPowersApplied(this, this.taavaphaonehOmenAmt, AbstractPower.PowerType.DEBUFF, true, false) :
                null;
        if (omen != null) addToBot(new ApplyPowerAction(this, this, omen));

    }

    @Override
    public void die() {
        super.die();
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new TridentOfBrahman(), new AnimaShiva());
            MonsterUtils.handleCardPlusRelicLinkedReward(new OdiousDemonedge(), new OdiousNarophirmidasCard());
        }
        Act2Arcarum.resumeMainMusic();
        super.die();
    }
}


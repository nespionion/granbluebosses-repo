package granbluebosses.monsters.act2.elites;

import VideoTheSpire.actions.RunTopLevelEffectAction;
import VideoTheSpire.effects.SimplePlayVideoEffect;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.action.DispelBuffAction;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.odious.OdiousZamalvochCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.aMonsters.ColossalBodyPower;
import granbluebosses.powers.aMonsters.DebuffOnHit;
import granbluebosses.powers.aMonsters.act2.DemonolatryPagesPower;
import granbluebosses.powers.aMonsters.act2.DerangedPower;
import granbluebosses.powers.aMonsters.act2.GildedHeavenPower;
import granbluebosses.powers.incantedOmens.AbstractIncantedOmen;
import granbluebosses.powers.incantedOmens.IncantedOmenCardPlayed;
import granbluebosses.powers.incantedOmens.IncantedOmenDamage;
import granbluebosses.powers.incantedOmens.IncantedOmenPowersApplied;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.odious.OdiousCodex;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import java.util.ArrayList;
import java.util.Collections;

import static granbluebosses.GranblueBosses.makeID;
import static granbluebosses.GranblueBosses.videoPath;

public class OdiousSanctity extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "Zamalvoch";
    public static final String MONSTER_ID = makeID("OdiousSanctity");
    protected static final int MONSTER_MAX_HP = 198;
    protected static final int MONSTER_MAX_HP_A_19 = 198 + 58;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "OdiousSanctity".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected float OMEN_MULT = 1.6f;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public int odimirofortoOmenAmt;
    public int agimamnaredeOmenAmt;
    public int timaretucreOmenAmt;

    public int odimirofortoDmg;
    public int odimirofortoHits;
    public int odimirofortoStacks;

    public int agimamnaredeDmg;
    public int agimamnaredeBlock;
    public int agimamnaredeStacks;

    public int timaretucreDmg;
    public int timaretucreHits;
    public int timaretucreStacks;
    
    public ArrayList<DebuffOnHit.AvailableDebuffs> debuffsToInfict;

    public static final int ODIMIROFORTO_INDEX = 0;
    public static final int AGIMAMNAREDE_INDEX = 1;
    public static final int TIMARETUCRE_INDEX = 2;


    public OdiousSanctity() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);



        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(MONSTER_MAX_HP_A_19);
            this.agimamnaredeBlock = 12;
        } else {
            this.setHp(MONSTER_MAX_HP);
            this.agimamnaredeBlock = 9;
        }

        if (AbstractDungeon.ascensionLevel >= 18){
            this.odimirofortoOmenAmt = 3;
            this.agimamnaredeOmenAmt = 2;
            this.timaretucreOmenAmt = 5;

            this.odimirofortoDmg = 2;
            this.odimirofortoHits = 8;
            this.odimirofortoStacks = 2;
            
            this.agimamnaredeDmg = 22;
            this.agimamnaredeStacks = 3;
            
            this.timaretucreDmg = 8;
            this.timaretucreHits = 5;

        } else {
            this.odimirofortoOmenAmt = 2;
            this.agimamnaredeOmenAmt = 3;
            this.timaretucreOmenAmt = 5;
            
            this.odimirofortoDmg = 2;
            this.odimirofortoHits = 8;
            this.odimirofortoStacks = 1;
            
            this.agimamnaredeDmg = 18;
            this.agimamnaredeStacks = 2;
            
            this.timaretucreDmg = 7;
            this.timaretucreHits = 5;

        }
        
        this.debuffsToInfict = new ArrayList<>();
        Collections.addAll(debuffsToInfict,
                DebuffOnHit.AvailableDebuffs.WEAK,
                DebuffOnHit.AvailableDebuffs.FRAIL,
                DebuffOnHit.AvailableDebuffs.VULNERABLE,
                DebuffOnHit.AvailableDebuffs.CONSTRICTED
                );

        this.damage.add(new DamageInfo(this, this.odimirofortoDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.agimamnaredeDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.timaretucreDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {

        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_ELITE_DARK);
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
                this.useOdimiroforto();
                break;
            case 2:
                this.useAgimamnarede();
                break;
            case 3:
                this.useTimaretucre();
                break;
        }
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenPowersApplied.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenDamage.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenCardPlayed.POWER_ID));

        this.prepareIntent();
        this.createIntent();

        this.createIntent();
        this.applyOmen();
    }

    public void useStunTurn(){
        // Does nothing. It's here for consistency
    }

    public void useOdimiroforto(){
//        Deal damage + Remove 1 (2) Player buff + Apply Draw reduction
//	Omen cancel: Apply debuffs
//          Stun on cancel

        for (int i = 0; i < this.odimirofortoHits; i++){
            addToBot(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(ODIMIROFORTO_INDEX), AbstractGameAction.AttackEffect.NONE));
        }

        addToBot(new DispelBuffAction(AbstractDungeon.player, this, this.odimirofortoStacks));
        
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, this.odimirofortoStacks), this.odimirofortoStacks));

        if (!this.hasPower(DemonolatryPagesPower.POWER_ID)) addToBot(new ApplyPowerAction(this, this, new DemonolatryPagesPower(this, 1), 1));

    }

    public void useAgimamnarede(){
        // Deal damage + Gain Block + Apply Deranged
        //	Omen cancel: Play Skill cards
        //    Stun on cancel

        addToBot(new VFXAction(new ShockWaveEffect(AbstractDungeon.player.hb.cX + this.hb.cX / 2, this.hb.y, Color.RED, ShockWaveEffect.ShockWaveType.NORMAL)));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(ODIMIROFORTO_INDEX), AbstractGameAction.AttackEffect.FIRE));

        addToBot(new GainBlockAction(this, this.agimamnaredeBlock));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new DerangedPower(AbstractDungeon.player, this.agimamnaredeStacks), this.agimamnaredeStacks));

        if (!this.hasPower(DemonolatryPagesPower.POWER_ID)) addToBot(new ApplyPowerAction(this, this, new DemonolatryPagesPower(this, 1), 1));
        
    }

    public void useTimaretucre(){
//        Deal damage + Gain Strength/DebuffOnHit + Reset Demonolatry Pages
//	        Omen cancel: Play 5 cards.

        addToBot(new SFXAction(Sounds.SFX_ACT2_ELITE_DARK));
        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("odioussanctity/OdiousDarkAnimNoSFX.webm"))));
        for (int i = 0; i < 5; i++){
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(TIMARETUCRE_INDEX), AbstractGameAction.AttackEffect.FIRE));
        }

        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.timaretucreStacks)));
        if (AbstractDungeon.ascensionLevel >= 18 && !debuffsToInfict.isEmpty()){
            addToBot(new ApplyPowerAction(this, this, new DebuffOnHit(this, debuffsToInfict.remove(0), this.timaretucreStacks)));
        } else {
            addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.timaretucreStacks), this.timaretucreStacks));
        }

        addToBot(new RemoveSpecificPowerAction(this, this, DemonolatryPagesPower.POWER_ID));
    }

    protected void prepareIntent() {
        if (
                (AbstractDungeon.player != null && this.hasPower(DemonolatryPagesPower.POWER_ID) && this.getPower(DemonolatryPagesPower.POWER_ID).amount >= 5) ||
                (AbstractDungeon.ascensionLevel >= 18 && this.currentHealth * this.OMEN_MULT <= this.maxHealth && this.trigger)){

            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));
            this.trigger = false;
            
            if (!this.hasPower(StunMonsterPower.POWER_ID) && this.nextMove != 0){
                addToBot(new SetMoveAction(this, MOVES[2], (byte)3, Intent.ATTACK_DEBUFF, this.damage.get(TIMARETUCRE_INDEX).base, 5, true));
                this.setMove(MOVES[2], (byte)3, Intent.ATTACK_DEBUFF, this.damage.get(TIMARETUCRE_INDEX).base, 5, true);
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
            case 1:
                addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(AGIMAMNAREDE_INDEX).base, 1, false));
                this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(AGIMAMNAREDE_INDEX).base, 1, false);
                break;
            case 2:
                addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(ODIMIROFORTO_INDEX).base, this.odimirofortoHits, true));
                this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(ODIMIROFORTO_INDEX).base, this.odimirofortoHits, true);
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
                addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(ODIMIROFORTO_INDEX).base, this.odimirofortoHits, true));
                this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(ODIMIROFORTO_INDEX).base, this.odimirofortoHits, true);
                break;
            case 1:
                addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(AGIMAMNAREDE_INDEX).base, 1, false));
                this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(AGIMAMNAREDE_INDEX).base, 1, false);
                break;
        }
    }

    protected void prepareRandomCommonMove(){
        if (AbstractDungeon.aiRng.randomBoolean()) {
            addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(ODIMIROFORTO_INDEX).base, 1, true));
            this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(ODIMIROFORTO_INDEX).base, this.odimirofortoHits, true);
        } else {
            addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(AGIMAMNAREDE_INDEX).base, 1, false));
            this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(AGIMAMNAREDE_INDEX).base, 1, false);
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(ODIMIROFORTO_INDEX).base, this.odimirofortoHits, true);
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
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenPowersApplied.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenDamage.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenCardPlayed.POWER_ID));

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
                omen = new IncantedOmenPowersApplied(this, this.odimirofortoOmenAmt, AbstractPower.PowerType.DEBUFF, true, false);
                break;
            case 2:
                omen = new IncantedOmenCardPlayed(this, this.agimamnaredeOmenAmt, null, AbstractCard.CardType.SKILL, null, -1);
                break;
            case 3:
                omen = new IncantedOmenCardPlayed(this, this.timaretucreOmenAmt);
                break;
        }
        if (omen != null) addToBot(new ApplyPowerAction(this, this, omen));

    }

    @Override
    public void die() {
        super.die();
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new TridentOfBrahman(), new AnimaShiva());
            MonsterUtils.handleCardPlusRelicLinkedReward(new OdiousCodex(), new OdiousZamalvochCard());
        }
        Act2Arcarum.resumeMainMusic();
        super.die();
    }
}


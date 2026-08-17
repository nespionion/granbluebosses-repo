package granbluebosses.monsters.act2.elites;

import VideoTheSpire.actions.RunTopLevelEffectAction;
import VideoTheSpire.effects.SimplePlayVideoEffect;
import basemod.abstracts.CustomMonster;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import static granbluebosses.GranblueBosses.makeID;
import static granbluebosses.GranblueBosses.videoPath;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.action.ReduceMaxHPAction;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.odious.OdiousBelmervolkCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.aMonsters.CASealedPower;
import granbluebosses.powers.aMonsters.ColossalBodyPower;
import granbluebosses.powers.aMonsters.act2.DoomFirePower;
import granbluebosses.powers.incantedOmens.*;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.act1.TridentOfBrahman;
import granbluebosses.relics.odious.OdiousDemonspear;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

public class OdiousProsperity extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "Belmervolk";
    public static final String MONSTER_ID = makeID("OdiousProsperity");
    protected static final int MONSTER_MAX_HP = 198;
    protected static final int MONSTER_MAX_HP_A_19 = 198 + 58;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "OdiousProsperity".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected float OMEN_MULT = 1.6f;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public int irdizuradeaOmenAmt;
    public int jadubertzeumOmenAmt;
    public int varrazunalaquOmenAmt;

    public int irdizuradeaDmg;
    public int irdizuradeaHits;
    public int irdizuradeaStacks;

    public int jadubertzeumDmg;
    public int jadubertzeumStacks;

    public int varrazunalaquDmg;
    public int varrazunalaquDmgIncrease;

    public static final int IRDIZURADEA_INDEX = 0;
    public static final int JADUBERTZEUM_INDEX = 1;
    public static final int VARRAZUNALAQU_INDEX = 2;


    public OdiousProsperity() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);



        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 18){
            this.irdizuradeaOmenAmt = 2;
            this.jadubertzeumOmenAmt = 4;
            this.varrazunalaquOmenAmt = 12;

            this.irdizuradeaDmg = 3;
            this.irdizuradeaHits = 11;
            this.irdizuradeaStacks = 2;
            this.jadubertzeumDmg = 25;
            this.jadubertzeumStacks = 1;
            this.varrazunalaquDmg = 50;
            this.varrazunalaquDmgIncrease = 10;

        } else {
            this.irdizuradeaOmenAmt = 2;
            this.jadubertzeumOmenAmt = 3;
            this.varrazunalaquOmenAmt = 10;

            this.irdizuradeaDmg = 2;
            this.irdizuradeaHits = 11;
            this.irdizuradeaStacks = 1;
            this.jadubertzeumDmg = 20;
            this.jadubertzeumStacks = 1;
            this.varrazunalaquDmg = 30;
            this.varrazunalaquDmgIncrease = 5;

        }

        this.damage.add(new DamageInfo(this, this.irdizuradeaDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.jadubertzeumDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.varrazunalaquDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {

        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_ELITE_FIRE);
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
                this.useIrdizuradea();
                break;
            case 2:
                this.useJadubertzeum();
                break;
            case 3:
                this.useVarrazunalaqu();
                break;
        }
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenHits.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenPowersApplied.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenDamage.POWER_ID));

        this.prepareIntent();
    }

    public void useStunTurn(){
        // Does nothing. It's here for consistency
    }

    public void useIrdizuradea(){
//        Deal multi hit damage + Remove 1 buff from player + Apply Doomfire
//        Omen: Apply 1 (2) debuff
//        Stun on cancel

        addToBot(new AnimateShakeAction(this, 0.3f, 0.3f));

        float randomXOffset;
        float randomYOffset;

        for (int i = 1; i < this.irdizuradeaHits; i++){
            randomXOffset = (AbstractDungeon.monsterRng.random() - 0.5f) / 2 * this.hb.width;
            randomYOffset = (AbstractDungeon.monsterRng.random() - 0.5f) * this.hb.height;

            addToBot(new VFXAction(new FireballEffect(this.hb.cX + randomXOffset, this.hb.cY + randomYOffset, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            addToBot(new PummelDamageAction(AbstractDungeon.player, this.damage.get(IRDIZURADEA_INDEX)));
        }

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(IRDIZURADEA_INDEX), AbstractGameAction.AttackEffect.FIRE));

        for (AbstractPower pow : AbstractDungeon.player.powers){
            if (pow.type == AbstractPower.PowerType.BUFF) {
                addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, pow));
                break;
            }
        }

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new DoomFirePower(AbstractDungeon.player, this.irdizuradeaStacks), this.irdizuradeaStacks));
    }

    public void useJadubertzeum(){
//        Deal damage + Apply Doomfire and CA Sealed
//        Omen: Deal damage 3 times
//        Stun on cancel

        addToBot(new VFXAction(new FireballEffect(this.hb.cX + (this.hb.width/2), this.hb.cY + (this.hb.height/2), AbstractDungeon.player.hb.cX - (AbstractDungeon.player.hb.width/2), AbstractDungeon.player.hb.cY- (AbstractDungeon.player.hb.height/2))));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(JADUBERTZEUM_INDEX), AbstractGameAction.AttackEffect.FIRE));


        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new DoomFirePower(AbstractDungeon.player, this.jadubertzeumStacks), this.jadubertzeumStacks));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new CASealedPower(AbstractDungeon.player, this.jadubertzeumStacks), this.jadubertzeumStacks));

    }

    public void useVarrazunalaqu(){
//        Deal damage based on Doomfire stacks + Apply debuffs for each Doomfire stack (1: Weak, 3: Frail, 5: Vulnerable, 7: CA Sealed, 10: Lower Max HP) + Reset Doomfire to 0
//        Omen: Deal (10 + 10 per Doomfire stack) amount of Dmg
//        Stun on cancel

        addToBot(new SFXAction(Sounds.SFX_ACT2_ELITE_FIRE));
        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("odiousprosperity/OdiousFireAnimNoSFX.webm"))));


        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(VARRAZUNALAQU_INDEX), AbstractGameAction.AttackEffect.FIRE));

        if (AbstractDungeon.player.hasPower(DoomFirePower.POWER_ID)){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, AbstractDungeon.player.getPower(DoomFirePower.POWER_ID).amount, true), AbstractDungeon.player.getPower(DoomFirePower.POWER_ID).amount));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, AbstractDungeon.player.getPower(DoomFirePower.POWER_ID).amount, true), AbstractDungeon.player.getPower(DoomFirePower.POWER_ID).amount));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, AbstractDungeon.player.getPower(DoomFirePower.POWER_ID).amount, true), AbstractDungeon.player.getPower(DoomFirePower.POWER_ID).amount));
            addToBot(new ReduceMaxHPAction(AbstractDungeon.player, AbstractDungeon.player.getPower(DoomFirePower.POWER_ID).amount));

            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, DoomFirePower.POWER_ID));
        }
    }

    protected void prepareIntent() {
        if (
                (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(DoomFirePower.POWER_ID) && AbstractDungeon.player.getPower(DoomFirePower.POWER_ID).amount > 5) ||
                (AbstractDungeon.ascensionLevel >= 18 && this.currentHealth * this.OMEN_MULT <= this.maxHealth && this.trigger)){

            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));
            this.trigger = false;
            
            if (!this.hasPower(StunMonsterPower.POWER_ID) && this.nextMove != 0){
                if (this.hasPower(DoomFirePower.POWER_ID)) {
                    this.damage.set(VARRAZUNALAQU_INDEX, new DamageInfo(this,
                            this.varrazunalaquDmg + (this.varrazunalaquDmgIncrease * this.getPower(DoomFirePower.POWER_ID).amount))
                    );
                    this.damage.get(VARRAZUNALAQU_INDEX).applyPowers(this, AbstractDungeon.player);
                }

                addToBot(new SetMoveAction(this, MOVES[2], (byte)3, Intent.ATTACK_DEBUFF, this.damage.get(VARRAZUNALAQU_INDEX).base, 1, false));
                this.setMove(MOVES[2], (byte)3, Intent.ATTACK_DEBUFF, this.damage.get(VARRAZUNALAQU_INDEX).base, 1, false);
                
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
                addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(JADUBERTZEUM_INDEX).base, 1, false));
                this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(JADUBERTZEUM_INDEX).base, 1, false);
                this.createIntent();
                this.applyOmen();
                break;
            case 2:
                addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(IRDIZURADEA_INDEX).base, this.irdizuradeaHits, true));
                this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(IRDIZURADEA_INDEX).base, this.irdizuradeaHits, true);
                this.createIntent();
                this.applyOmen();
                break;
            case 3:
                this.prepareRandomCommonMove();
                break;
        }
    }

    protected void prepareIntentA17() {
        this.prepareRandomCommonMove();
    }

    protected void prepareRandomCommonMove(){
        if (AbstractDungeon.aiRng.randomBoolean()) {
            addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(IRDIZURADEA_INDEX).base, this.irdizuradeaHits, true));
            this.setMove(MOVES[0], (byte)1, Intent.ATTACK_DEBUFF, this.damage.get(IRDIZURADEA_INDEX).base, this.irdizuradeaHits, true);
            this.createIntent();
            this.applyOmen();
        } else {
            addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(JADUBERTZEUM_INDEX).base, 1, false));
            this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(JADUBERTZEUM_INDEX).base, 1, false);
            this.createIntent();
            this.applyOmen();
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[1], (byte)2, Intent.ATTACK_DEBUFF, this.damage.get(JADUBERTZEUM_INDEX).base, 1, false);
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
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenHits.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenPowersApplied.POWER_ID));
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenDamage.POWER_ID));

        this.setMove((byte)0, Intent.STUN);
        this.createIntent();
        addToTop(new SetMoveAction(this, (byte)0, Intent.STUN));
        
        OmenUtils.onCancelOmenSFX(this);
    }



    @Override
    public void applyOmen() {
        AbstractIncantedOmen omen = null;
        switch (this.nextMove) {
            case 1:
                omen = new IncantedOmenHits(this, this.irdizuradeaOmenAmt);
                break;
            case 2:
                omen = new IncantedOmenPowersApplied(this, this.jadubertzeumOmenAmt, AbstractPower.PowerType.DEBUFF, false, false);
                break;
            case 3:
                int omenReq = AbstractDungeon.player != null && AbstractDungeon.player.hasPower(DoomFirePower.POWER_ID) ?
                        this.varrazunalaquOmenAmt + (this.varrazunalaquOmenAmt * AbstractDungeon.player.getPower(DoomFirePower.POWER_ID).amount) :
                        this.varrazunalaquOmenAmt
                        ;
                omen = new IncantedOmenDamage(this, omenReq, null);
                break;
        }
        if (omen != null) addToBot(new ApplyPowerAction(this, this, omen));

    }

    @Override
    public void die() {
        super.die();
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new TridentOfBrahman(), new AnimaShiva());
            MonsterUtils.handleCardPlusRelicLinkedReward(new OdiousDemonspear(), new OdiousBelmervolkCard());
        }
        Act2Arcarum.resumeMainMusic();
        super.die();
    }
}


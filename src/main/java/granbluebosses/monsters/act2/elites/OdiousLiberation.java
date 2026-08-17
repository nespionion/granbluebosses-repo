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
import com.megacrit.cardcrawl.vfx.combat.BuffParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.odious.OdiousMacutanmacarCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.intents.enums.CustomIntentEnums;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.aMonsters.ColossalBodyPower;
import granbluebosses.powers.aMonsters.act2.UnsealingPower;
import granbluebosses.powers.incantedOmens.*;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.odious.OdiousSealhammer;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;
import static granbluebosses.GranblueBosses.videoPath;

public class OdiousLiberation extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "Macutanmacar";
    public static final String MONSTER_ID = makeID("OdiousLiberation");
    protected static final int MONSTER_MAX_HP = 198;
    protected static final int MONSTER_MAX_HP_A_19 = 198 + 58;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "OdiousLiberation".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected float OMEN_MULT = 1.6f;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public int petapolyuresfsiOmenAmt;
    public int nosorgoideaOmenAmt;
    public int nosorgoideaOmenCardCost;

    public int petapolyuresfsiDmg;
    public int petapolyuresfsiStacks;

    public int diaskorgidaBlock;
    public int diaskorgidaStacks;

    public int nosorgoideaDmg;
    public int nosorgoideaDmgIncrease;

    public static final int PETAPOLYURESFSI_INDEX = 0;
    public static final int NOSORGOIDEA_INDEX = 1;


    public OdiousLiberation() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);



        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(MONSTER_MAX_HP_A_19);
            this.diaskorgidaBlock = 20;
        } else {
            this.setHp(MONSTER_MAX_HP);
            this.diaskorgidaBlock = 15;
        }

        if (AbstractDungeon.ascensionLevel >= 18){
            this.petapolyuresfsiOmenAmt = 2;
            this.nosorgoideaOmenAmt = 1;
            this.nosorgoideaOmenCardCost = 2;

            this.petapolyuresfsiDmg = 22;
            this.petapolyuresfsiStacks = 2;

            this.diaskorgidaStacks = 1;

            this.nosorgoideaDmg = 42;
            this.nosorgoideaDmgIncrease = 8;

        } else {
            this.petapolyuresfsiOmenAmt = 2;
            this.nosorgoideaOmenAmt = 1;
            this.nosorgoideaOmenCardCost = 2;

            this.petapolyuresfsiDmg = 27;
            this.petapolyuresfsiStacks = 2;

            this.diaskorgidaStacks = 1;

            this.nosorgoideaDmg = 42;
            this.nosorgoideaDmgIncrease = 8;

        }

        this.damage.add(new DamageInfo(this, this.petapolyuresfsiDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.nosorgoideaDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {

        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_ELITE_WIND);
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
                this.usePetapolyuresfsi();
                break;
            case 2:
                this.useDiaskorgida();
                break;
            case 3:
                this.useNosorgoidea();
                break;
        }
        addToTop(new RemoveSpecificPowerAction(this, this, IncantedOmenCardPlayed.POWER_ID));

        this.prepareIntent();
    }

    public void useStunTurn(){
        // Does nothing. It's here for consistency
    }

    public void usePetapolyuresfsi(){
//        Deal damage + Gain 2 Unsealing + Increase the cost of the next 2 cards on the top of your deck by 1
//        Omen: Play 2 (3) Skills
//        Stun on cancel

        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.GREEN, ShockWaveEffect.ShockWaveType.NORMAL)));
        addToBot(new SFXAction("POWER_SHACKLE"));
        addToBot(new AnimateShakeAction(this, 0.5f, 0.5f));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(PETAPOLYURESFSI_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        addToBot(new ApplyPowerAction(this, this, new UnsealingPower(this, this.petapolyuresfsiStacks), this.petapolyuresfsiStacks));

        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cost == c.costForTurn){
                c.setCostForTurn(c.cost + 1);
            }
        }
    }

    public void useDiaskorgida(){
        //Gain Block and ATKUp + Gain 1 Unsealing
        addToBot(new VFXAction(new BuffParticleEffect(this.hb.cX, this.hb.cY)));
        addToBot(new SFXAction("POWER_SHACKLE"));

        addToBot(new GainBlockAction(this, this.diaskorgidaBlock));

        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.diaskorgidaStacks), this.diaskorgidaStacks));

        addToBot(new ApplyPowerAction(this, this, new UnsealingPower(this, this.diaskorgidaStacks), this.diaskorgidaStacks));
    }

    public void useNosorgoidea(){
//        Deal damage based on Unsealing stacks + Player loses 1 Energy for the rest of the battle + Reset Unsealing to 0
//        Omen: Play an Attack that cost 2 (3) or more (AOE counts as 2)
//        Stun on cancel

        addToBot(new SFXAction(Sounds.SFX_ACT2_ELITE_WIND));
        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("odiousprosperity/OdiousWindAnimNoSFX.webm"))));

        addToBot(new VFXAction(new WhirlwindEffect(Color.GREEN, true), 0.2f));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(NOSORGOIDEA_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (this.hasPower(UnsealingPower.POWER_ID)) addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new DrawReductionPower(AbstractDungeon.player, this.getPower(UnsealingPower.POWER_ID).amount), this.getPower(UnsealingPower.POWER_ID).amount));
        
    }

    protected void prepareIntent() {
        if (
                (AbstractDungeon.player != null && this.hasPower(UnsealingPower.POWER_ID) && this.getPower(UnsealingPower.POWER_ID).amount >= 5) ||
                (AbstractDungeon.ascensionLevel >= 18 && this.currentHealth * this.OMEN_MULT <= this.maxHealth && this.trigger)){

            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));
            this.trigger = false;
            
            if (!this.hasPower(StunMonsterPower.POWER_ID) && this.nextMove != 0){
                if (this.hasPower(UnsealingPower.POWER_ID)) {
                    this.damage.set(NOSORGOIDEA_INDEX, new DamageInfo(this,
                            this.nosorgoideaDmg + (this.nosorgoideaDmg * this.getPower(UnsealingPower.POWER_ID).amount))
                    );
                    this.damage.get(NOSORGOIDEA_INDEX).applyPowers(this, AbstractDungeon.player);
                }

                addToBot(new SetMoveAction(this, MOVES[2], (byte)3, Intent.ATTACK_DEBUFF, this.damage.get(NOSORGOIDEA_INDEX).base, 1, false));
                this.setMove(MOVES[2], (byte)3, Intent.ATTACK_DEBUFF, this.damage.get(NOSORGOIDEA_INDEX).base, 1, false);
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
                addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.DEFEND_BUFF));
                this.setMove(MOVES[1], (byte)2, Intent.DEFEND_BUFF);
                this.createIntent();
                this.applyOmen();
                break;
            case 2:
                addToBot(new SetMoveAction(this, MOVES[0], (byte)1, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(PETAPOLYURESFSI_INDEX).base, 1, false));
                this.setMove(MOVES[0], (byte)1, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(PETAPOLYURESFSI_INDEX).base, 1, false);
                this.createIntent();
                this.applyOmen();
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
                addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK, this.damage.get(PETAPOLYURESFSI_INDEX).base, 1, false));
                this.setMove(MOVES[0], (byte)1, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(PETAPOLYURESFSI_INDEX).base, 1, false);
                this.createIntent();
                this.applyOmen();
                break;
            case 1:
                addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.DEFEND_BUFF));
                this.setMove(MOVES[1], (byte)2, Intent.DEFEND_BUFF);
                this.createIntent();
                this.applyOmen();
                break;
        }
    }

    protected void prepareRandomCommonMove(){
        if (AbstractDungeon.aiRng.randomBoolean()) {
            addToBot(new SetMoveAction(this, MOVES[0], (byte)1, Intent.ATTACK, this.damage.get(PETAPOLYURESFSI_INDEX).base, 1, false));
            this.setMove(MOVES[0], (byte)1, CustomIntentEnums.ATTACK_MAGIC, this.damage.get(PETAPOLYURESFSI_INDEX).base, 1, false);
            this.createIntent();
            this.applyOmen();
        } else {
            addToBot(new SetMoveAction(this, MOVES[1], (byte)2, Intent.DEFEND_BUFF));
            this.setMove(MOVES[1], (byte)2, Intent.DEFEND_BUFF);
            this.createIntent();
            this.applyOmen();
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(MOVES[1], (byte)2, Intent.DEFEND_BUFF);
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
        AbstractIncantedOmen omen = null;
        switch (this.nextMove) {
            case 1:
                omen = new IncantedOmenCardPlayed(this, this.petapolyuresfsiOmenAmt, null, AbstractCard.CardType.SKILL, null, 0);
                break;
            case 3:
                int omenReq = AbstractDungeon.player != null && this.hasPower(UnsealingPower.POWER_ID) && this.getPower(UnsealingPower.POWER_ID).amount > 2 ?
                        this.nosorgoideaOmenAmt * 2 :
                        this.nosorgoideaOmenAmt;
                omen = new IncantedOmenCardPlayed(this, omenReq, null, AbstractCard.CardType.ATTACK, null, this.nosorgoideaOmenCardCost);
                break;
        }
        if (omen != null) addToBot(new ApplyPowerAction(this, this, omen));

    }

    @Override
    public void die() {
        super.die();
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new TridentOfBrahman(), new AnimaShiva());
            MonsterUtils.handleCardPlusRelicLinkedReward(new OdiousSealhammer(), new OdiousMacutanmacarCard());
        }
        Act2Arcarum.resumeMainMusic();
        super.die();
    }
}


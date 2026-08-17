package granbluebosses.monsters.act1.elites;

import basemod.abstracts.CustomMonster;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.EmpowerEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act1Skies;
import granbluebosses.cards.rewards.Magna2.ShivaCall;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.powers.aMonsters.act1.PathOfDestruction;
import granbluebosses.relics.act1.TridentOfBrahman;
import granbluebosses.util.Sounds;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class Shiva extends CustomMonster {
    protected static final String MONSTER_NAME = "Shiva";
    public static final String MONSTER_ID = makeID("Shiva");
    protected static final String MONSTER_ANIM_URL = MONSTER_NAME.toLowerCase();
    protected static final int MONSTER_MAX_HP = 85;
    protected static final int MONSTER_MAX_HP_A_19 = 85 + 5;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected int OMEN_MULT = 2;
    protected boolean firstTurn = true;
    protected int rudraBuffStacks = 1;
    protected int rudraDebuffStacks = 2;
    protected int sriRudramDmg = 14;
    protected int mahakalaDmg = 0;
    protected int awakenInnerEyeDmg = 5;
    protected float awakenInnerEyeMult = 1.5f;
    protected int strengthGain = 0;
    protected static final MonsterStrings monsterStrings;
    public static final String RUDRA;
    public static final String SRI_RUDRAM;
    public static final String AWAKEN_INNER_EYE;
    public static final String ENTRY_DIALOG;
    public static final String AWAKEN_DIALOG;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public static final int SRI_RUDRAM_INDEX = 0;
    public static final int AWAKEN_INNER_EYE_INDEX = 1;
    public static final int MAHAKALA_EMPOWERED_INDEX = 2;

    public Shiva() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }
        if (AbstractDungeon.ascensionLevel >= 18) {
            this.awakenInnerEyeDmg += 2;
            this.awakenInnerEyeMult = 2.5f;
            this.rudraBuffStacks += 1;
        } else if (AbstractDungeon.ascensionLevel >= 3) {
            this.awakenInnerEyeDmg += 1;
            this.awakenInnerEyeMult = 2f;
        } else {
            this.awakenInnerEyeDmg -= 1;
            this.awakenInnerEyeMult = 1.5f;
        }
        if (AbstractDungeon.ascensionLevel > 20){
            this.OMEN_MULT = 1;
        }

        this.mahakalaDmg = this.sriRudramDmg;
        this.strengthGain = (int) (this.sriRudramDmg * (this.awakenInnerEyeMult - 1));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
        this.state.setAnimation(0, "idle", true);

        this.damage.add(new DamageInfo(this, this.sriRudramDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.awakenInnerEyeDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.mahakalaDmg, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void usePreBattleAction() {

        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT1_ELITE_SHIVA);
        } else {
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly("ELITE");
        }

        StanceOmen omen = new StanceOmen(this);
        omen.setUpOmenByHp(OMEN_MULT);
        addToBot(new ApplyPowerAction(this, this, omen));

        super.usePreBattleAction();
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useRudra();
                break;
            case 1:
                this.useSriRudram();
                break;
            case 2:
                this.useAwakenInnerEye();
                break;
            case 3:
                this.useSriRudram();
                break;
        }
        this.prepareIntent();
    }

    protected void useRudra(){
        addToBot(new VFXAction(new EmpowerEffect(this.hb.cX, this.hb.cY)));
        this.addToBot(new SFXAction("BUFF_1"));

        this.state.setAnimation(0, "shield", false);
        this.state.addAnimation(0, "idle", true, 0.0f);

        addToBot(new ApplyPowerAction(this, this, new VulnerablePower(this, rudraDebuffStacks+1, true)));
        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, rudraBuffStacks), this.rudraBuffStacks));
    }

    protected void useSriRudram(){
        float vfxSpeed = 0.1F;
        if (Settings.FAST_MODE) {
            vfxSpeed = 0.0F;
        }



        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(new CleaveEffect(true), vfxSpeed));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(SRI_RUDRAM_INDEX), AbstractGameAction.AttackEffect.NONE));

    }

    protected void useAwakenInnerEye(){

        addToBot(new ShoutAction(this, AWAKEN_DIALOG));
        addToBot(new SFXAction(Sounds.SHIVA_AWAKEN_DIALOG));

        addToBot(new SFXAction("ATTACK_DEFECT_BEAM"));
        this.state.setAnimation(0, "attack", false);

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(AWAKEN_INNER_EYE_INDEX), AbstractGameAction.AttackEffect.NONE));

        if (AbstractDungeon.ascensionLevel >= 18){
            addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.strengthGain), this.strengthGain));
        } else {
//            addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, this.strengthGain), this.strengthGain));
//            addToBot(new ApplyPowerAction(this, this, new LoseStrengthPower(this, this.strengthGain), this.strengthGain));
            addToBot(new ApplyPowerAction(this, this, new PathOfDestruction(this, this.strengthGain), this.strengthGain));
        }

        this.state.addAnimation(0, "idle", true, 0.0f);
    }

    protected void prepareIntent() {
        if (this.currentHealth * this.OMEN_MULT <= this.maxHealth && this.trigger) {
            this.trigger = false;
            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));

            if (!this.hasPower(StunMonsterPower.POWER_ID)){
                this.setMove(AWAKEN_INNER_EYE, (byte) 2, Intent.ATTACK_BUFF, this.damage.get(AWAKEN_INNER_EYE_INDEX).base, 1, false);
                this.createIntent();
                addToBot(new SetMoveAction(this, AWAKEN_INNER_EYE, (byte) 2, Intent.ATTACK_BUFF, this.damage.get(AWAKEN_INNER_EYE_INDEX).base, 1, false));
                
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
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, SRI_RUDRAM, (byte) 1, Intent.ATTACK, this.damage.get(SRI_RUDRAM_INDEX).base, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, RUDRA, (byte) 0, Intent.BUFF));
                break;
            case 2:
                addToBot(new TextAboveCreatureAction(this, "DANGER!"));
                addToBot(new SetMoveAction(this, SRI_RUDRAM, (byte) 1, Intent.ATTACK, this.damage.get(SRI_RUDRAM_INDEX).base, 1, false));
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, SRI_RUDRAM, (byte) 1, Intent.ATTACK, this.damage.get(SRI_RUDRAM_INDEX).base, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, RUDRA, (byte) 0, Intent.BUFF));
                break;
            case 2:
                addToBot(new TextAboveCreatureAction(this, "DANGER!"));
                addToBot(new SetMoveAction(this, SRI_RUDRAM, (byte) 3, Intent.ATTACK, this.damage.get(SRI_RUDRAM_INDEX).base, 1, false));
                break;
            case 3:
                addToBot(new TextAboveCreatureAction(this, "DANGER!"));
                addToBot(new SetMoveAction(this, SRI_RUDRAM, (byte) 3, Intent.ATTACK, this.damage.get(SRI_RUDRAM_INDEX).base, 1, false));
                break;
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new TridentOfBrahman(), new AnimaShiva());
            MonsterUtils.handleCardPlusRelicLinkedReward(new TridentOfBrahman(), new ShivaCall());

//            RewardItem reward2 = new RewardItem(new TridentOfBrahman());
//
//            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
//            reward.text = "Forge a pact with this primal beast";
//            reward.relic = new AnimaShiva();
//
//            reward.relicLink = reward2;
//            reward2.relicLink = reward;
//
//            AbstractDungeon.getCurrRoom().rewards.add(reward2);
//            AbstractDungeon.getCurrRoom().rewards.add(reward);
        }
        Act1Skies.resumeMainMusic();
        super.die();
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;

            addToBot(new ShoutAction(this, ENTRY_DIALOG));
            addToBot(new SFXAction(Sounds.SHIVA_ENTRY_DIALOG));
            this.setMove(RUDRA, (byte) 0, Intent.BUFF);

        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        RUDRA = MOVES[0];
        SRI_RUDRAM = MOVES[1];
        AWAKEN_INNER_EYE = MOVES[2];
        ENTRY_DIALOG = DIALOG[0];
        AWAKEN_DIALOG = DIALOG[1];
    }
}
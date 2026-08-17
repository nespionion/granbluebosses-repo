package granbluebosses.monsters.act1.normal;

import basemod.abstracts.CustomMonster;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.EmpowerEffect;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act1Skies;
import granbluebosses.cards.rewards.Magna1.AthenaCall;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.common.PhalanxPower;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.act1.SwordOfPallas;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class Athena extends CustomMonster {
    protected static final String MONSTER_NAME = "Athena";
    public static final String MONSTER_ID = makeID("Athena");
    protected static final int MONSTER_MAX_HP = 70;
    protected static final int MONSTER_MAX_HP_A_19 = 70 + 2;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = MONSTER_NAME.toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    protected final int OMEN_MULT = 5;
    public static final String TETRADRACHM;
    public static final String AIGIS_FEBRUS;
    public static final String MINERVA_THRUST;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    protected int tetradrachmDMG = 11;
    protected int aigisBlock = 6;
    protected int thrustDmg = 15;
    protected final int TETRA_DMG_INDEX = 0;
    protected final int THRUST_DMG_INDEX = 1;

    public Athena() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MONSTER_MAX_HP_A_19);
            this.aigisBlock += 2;
        } else {
            this.setHp(MONSTER_MAX_HP);
        }
        if (AbstractDungeon.ascensionLevel >= 2){
            this.tetradrachmDMG += 3;
        }
        if (AbstractDungeon.ascensionLevel >= 17){
            this.tetradrachmDMG += 2;
            this.thrustDmg += 5;
        }

        this.damage.add(new DamageInfo(this, this.tetradrachmDMG, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.thrustDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);

        if (ConfigMenu.modestyFilter){
            this.state.setAnimation(0, "idle_cen", true);
        } else {
            this.state.setAnimation(0, "idle_uncen", true);
        }
    }

    @Override
    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 17){
            StanceOmen omen = new StanceOmen(this);
            omen.setUpOmenByHp(OMEN_MULT);
            addToTop(new ApplyPowerAction(this, this, omen));
        } else {
            this.trigger = false;
        }

        if (ConfigMenu.enableDMCAMusic){CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT1_BATTLE);
        }
        super.usePreBattleAction();
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useTetradrachm();
                break;
            case 1:
                this.useAigisFerbrus();
                break;
            case 2:
                this.useMinervaThrust();
                break;
        }
        this.prepareIntent();
    }



    protected void prepareIntent() {
        if (this.trigger && this.currentHealth * OMEN_MULT < this.maxHealth){
            this.trigger = false;

            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));

            if (!this.hasPower(StunMonsterPower.POWER_ID)) {
//                if (AbstractDungeon.ascensionLevel >= 17){
//                    addToBot(new ApplyPowerAction(this, this, new PiercingPower(this, 2), 2));
//                }
                OmenUtils.onPrepOmenSFX(this);

                addToBot(new TextAboveCreatureAction(this, "DANGER!"));

                this.setMove(MINERVA_THRUST, (byte) 2, Intent.ATTACK, this.damage.get(THRUST_DMG_INDEX).base, 1, false);

                this.createIntent();

                addToBot(new SetMoveAction(this, MINERVA_THRUST, (byte) 2, Intent.ATTACK, this.damage.get(THRUST_DMG_INDEX).base, 2, true));

                return;
            } else {
                OmenUtils.onCancelOmenSFX(this);
            }
        }
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, AIGIS_FEBRUS, (byte)1, Intent.DEFEND));
                break;
            case 1:
                addToBot(new SetMoveAction(this, TETRADRACHM, (byte)0, Intent.ATTACK, this.damage.get(TETRA_DMG_INDEX).base, 1, false));
                break;
            case 2:
                addToBot(new SetMoveAction(this, AIGIS_FEBRUS, (byte)1, Intent.DEFEND));
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 0:
            case 2:
                if (AbstractDungeon.aiRng.randomBoolean()){
                    addToBot(new SetMoveAction(this, AIGIS_FEBRUS, (byte)1, Intent.DEFEND));
                } else {
                    addToBot(new SetMoveAction(this, TETRADRACHM, (byte)0, Intent.ATTACK, this.damage.get(TETRA_DMG_INDEX).base, 1, false));
                }
                break;
            case 1:
                addToBot(new SetMoveAction(this, TETRADRACHM, (byte)0, Intent.ATTACK, this.damage.get(TETRA_DMG_INDEX).base, 1, false));
                break;
        }
    }

    protected void useTetradrachm(){
        float vfxSpeed = 0.1F;
        if (Settings.FAST_MODE) {
            vfxSpeed = 0.0F;
        }

        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new VFXAction(new FireballEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), vfxSpeed));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(THRUST_DMG_INDEX), AbstractGameAction.AttackEffect.FIRE));
    }

    protected void useAigisFerbrus(){
        addToBot(new VFXAction(new EmpowerEffect(this.hb.cX, this.hb.cY)));
        this.addToBot(new SFXAction("BLOCK_GAIN_1"));

        addToBot(new ApplyPowerAction(this, this, new PhalanxPower(this, 30), 30));

        this.state.setAnimation(0, "shield", false);

        if (ConfigMenu.modestyFilter){
            this.state.addAnimation(0, "idle_cen", true, 0.0f);
        } else {
            this.state.addAnimation(0, "idle_uncen", true, 0.0f);
        }

    }

    protected void useMinervaThrust(){
        float vfxSpeed = 0.1F;
        if (Settings.FAST_MODE) {
            vfxSpeed = 0.0F;
        }

        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(new CleaveEffect(true), vfxSpeed));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(THRUST_DMG_INDEX), AbstractGameAction.AttackEffect.NONE));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(THRUST_DMG_INDEX), AbstractGameAction.AttackEffect.FIRE));

    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove((byte)1, Intent.DEFEND);
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new SwordOfPallas(), new AnimaAthena());
            MonsterUtils.handleCardPlusRelicLinkedReward(new SwordOfPallas(), new AthenaCall());

//            RewardItem reward2 = new RewardItem(new SwordOfPallas());
//
//            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
//            reward.text = "Forge a pact with this primal beast";
//            reward.relic = new AnimaAthena();
//
//            reward.relicLink = reward2;
//            reward2.relicLink = reward;
//
//            AbstractDungeon.getCurrRoom().rewards.add(reward2);
//            AbstractDungeon.getCurrRoom().rewards.add(reward);
        }
        Act1Skies.resumeMainMusic();

//        EnemyDefeatCondition.addDefeatedEnemy(MONSTER_ID);

        super.die();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        TETRADRACHM = MOVES[0];
        AIGIS_FEBRUS = MOVES[1];
        MINERVA_THRUST = MOVES[2];
    }
}

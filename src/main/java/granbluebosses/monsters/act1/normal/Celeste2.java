package granbluebosses.monsters.act1.normal;

import basemod.abstracts.CustomMonster;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.vfx.combat.IntimidateEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act1Skies;
import granbluebosses.cards.rewards.Magna1.CelesteOmega;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.powers.aMonsters.DebuffOnHit;
import granbluebosses.relics.act1.CelesteClawOmega;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class Celeste2 extends CustomMonster {
    protected static final String MONSTER_NAME = "Celeste";
    public static final String MONSTER_ID = makeID("Celeste2");
    protected static final int MONSTER_MAX_HP = 55;
    protected static final int MONSTER_MAX_HP_A_19 = 55 + 4;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = MONSTER_NAME.toLowerCase() + "2";
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected final int OMEN_MULT = 2;
    protected boolean firstTurn = true;
    protected int tranquilFogDmg = 3;
    protected int bizarreFogStacks = 6;
    protected int nullVoidDmg = 0;
    public static final String TRANQUIL_FOG;
    public static final String BIZARRE_FOG;
    public static final String NULL_VOID;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public static final int TRANQUIL_FOG_INDEX = 0;
    public static final int NULL_VOID_INDEX = 1;

    public Celeste2() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }
        if (AbstractDungeon.ascensionLevel >= 2){
            this.tranquilFogDmg += 2;
        }

        this.damage.add(new DamageInfo(this, this.tranquilFogDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, this.nullVoidDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {
        StanceOmen omen = new StanceOmen(this);
        omen.setUpOmenByHp(OMEN_MULT);
        if (ConfigMenu.enableDMCAMusic){CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT1_BATTLE);
        }
        if (AbstractDungeon.ascensionLevel >= 17){
            addToBot(new ApplyPowerAction(this, this, new DebuffOnHit(this, DebuffOnHit.AvailableDebuffs.POISON, 3)));
        }
        addToTop(new ApplyPowerAction(this, this, omen));
        super.usePreBattleAction();

    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useTranquilFog();
                break;
            case 1:
                this.useBizarreFog();
                break;
            case 2:
                this.useNullVoid();
                break;
        }
        this.prepareIntent();
    }

    protected void useTranquilFog(){
        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(TRANQUIL_FOG_INDEX), AbstractGameAction.AttackEffect.POISON));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new RegenPower(AbstractDungeon.player, bizarreFogStacks)));
    }

    protected void useBizarreFog(){
        addToBot(new VFXAction(this, new IntimidateEffect(this.hb.cX, this.hb.cY), 0.5F));

        AbstractPower playerRegen = AbstractDungeon.player.getPower(RegenPower.POWER_ID);
        if (playerRegen != null){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new PoisonPower(AbstractDungeon.player, this, playerRegen.amount)));
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, RegenPower.POWER_ID));
        }
    }

    protected void useNullVoid(){
        addToBot(new AnimateSlowAttackAction(this));

        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this, PoisonPower.POWER_ID));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(NULL_VOID_INDEX), AbstractGameAction.AttackEffect.POISON));

    }

    protected void prepareIntent() {
        if (this.trigger && this.currentHealth * this.OMEN_MULT <= this.maxHealth) {
            this.trigger = false;
            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));

            if (!this.hasPower(StunMonsterPower.POWER_ID)) {
                OmenUtils.onPrepOmenSFX(this);
                this.prepareNullVoid();
                return;
            } else {
                OmenUtils.onCancelOmenSFX(this);
            }
        }
        switch (this.nextMove){
            case 0:
                addToBot(new SetMoveAction(this, BIZARRE_FOG, (byte)1, Intent.STRONG_DEBUFF));
                break;
            case 1:
                addToBot(new SetMoveAction(this, TRANQUIL_FOG, (byte)0, Intent.ATTACK_DEBUFF, this.damage.get(TRANQUIL_FOG_INDEX).base, 1, false));
                break;
        }
    }

    protected void prepareNullVoid(){
        addToBot(new TextAboveCreatureAction(this, "DANGER!"));

        if (AbstractDungeon.player.getPower(PoisonPower.POWER_ID) != null) {
            int nullVoidMult = 1;
            if (AbstractDungeon.ascensionLevel > 17) {
                nullVoidMult++;
            }
            this.nullVoidDmg = AbstractDungeon.player.getPower(PoisonPower.POWER_ID).amount * nullVoidMult;
            this.damage.get(NULL_VOID_INDEX).base = this.nullVoidDmg;
            this.damage.get(NULL_VOID_INDEX).applyPowers(this, AbstractDungeon.player);
        }

        this.setMove(NULL_VOID, (byte) 2, Intent.ATTACK, this.damage.get(NULL_VOID_INDEX).base, 1, false);

        this.createIntent();

        addToBot(new SetMoveAction(this, NULL_VOID, (byte) 2, Intent.ATTACK, this.damage.get(NULL_VOID_INDEX).base, 1, false));

    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {

//            MonsterUtils.handleEndOfBattleRewards(new CelesteClawOmega(), new AnimaCeleste());
            MonsterUtils.handleCardPlusRelicLinkedReward(new CelesteClawOmega(), new CelesteOmega());

//            RewardItem reward2 = new RewardItem(new CelesteClawOmega());
//
//            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
//            reward.text = "Forge a pact with this primal beast";
//            reward.relic = new AnimaCeleste();
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

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(TRANQUIL_FOG, (byte)0, Intent.ATTACK_DEBUFF, this.damage.get(TRANQUIL_FOG_INDEX).base, 1, false);
        }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        TRANQUIL_FOG = MOVES[0];
        BIZARRE_FOG = MOVES[1];
        NULL_VOID = MOVES[2];
    }
}

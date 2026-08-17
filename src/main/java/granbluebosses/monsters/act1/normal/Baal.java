package granbluebosses.monsters.act1.normal;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act1Skies;
import granbluebosses.cards.rewards.Magna1.BaalCall;
import granbluebosses.config.ConfigMenu;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.incantedOmens.AbstractIncantedOmen;
import granbluebosses.powers.incantedOmens.IncantedOmenCardPlayed;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.act1.SolomonAxe;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class Baal extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "Baal";
    public static final String MONSTER_ID = makeID("Baal");
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
    protected final int OMEN_MULT = 5;
    protected static final MonsterStrings monsterStrings;
    public static final String HALF_STEP;
    public static final String ROCK_OUT;
    public static final String ANTHEM;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    protected int anthemDmg = 20;
    protected int omenStacks = 2;

    public Baal() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }
        if (AbstractDungeon.ascensionLevel >= 17){
            this.anthemDmg += 10;
        }

        this.damage.add(new DamageInfo(this, this.anthemDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {
        if (ConfigMenu.enableDMCAMusic){CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT1_BATTLE);
        }
        super.usePreBattleAction();
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useHalfStep();
                break;
            case 1:
                this.useRockOut();
                break;
            case 2:
                this.useAnthemOfSolomon();
                break;
        }
        this.prepareIntent();
    }

    protected void prepareIntent() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, ANTHEM, (byte)2, Intent.ATTACK, this.damage.get(0).base, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, ANTHEM, (byte)2, Intent.ATTACK, this.damage.get(0).base, 1, false));
                break;
            case 2:
                this.choseRandomOmenMove();
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, ANTHEM, (byte)2, Intent.ATTACK, this.damage.get(0).base, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, ANTHEM, (byte)2, Intent.ATTACK, this.damage.get(0).base, 1, false));
                break;
            case 2:
                addToBot(new SetMoveAction(this, ANTHEM, (byte)2, Intent.ATTACK, this.damage.get(0).base, 1, false));
                break;
        }
    }

    protected void useHalfStep(){
        this.createIntent();
        this.applyOmen();
    }

    protected void useRockOut(){
        this.createIntent();
        this.applyOmen();
    }

    protected void useAnthemOfSolomon(){
        addToBot(new VFXAction(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.drawY), 0.2f));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.choseRandomOmenMove();
        }
    }

    @Override
    public void resolveOmen() {
        addToBot(new RemoveSpecificPowerAction(this, this, IncantedOmenCardPlayed.POWER_ID));
        this.choseRandomOmenMove();
    }

    private void choseRandomOmenMove(){
        if (AbstractDungeon.aiRng.randomBoolean()){
            this.setMove(HALF_STEP, (byte)0, Intent.BUFF);
            this.createIntent();
            addToBot(new SetMoveAction(this, HALF_STEP, (byte)0, Intent.BUFF));
            OmenUtils.onPrepOmenSFX(this);
        } else {
            this.setMove(ROCK_OUT, (byte)1, Intent.BUFF);
            this.createIntent();
            addToBot(new SetMoveAction(this, ROCK_OUT, (byte)1, Intent.BUFF));
            OmenUtils.onPrepOmenSFX(this);
        }
    }

    @Override
    public void applyOmen() {
        AbstractIncantedOmen omen;
        if (this.nextMove == 0){
//            GranblueBosses.logger.info("Setting omen card type " + AbstractCard.CardType.SKILL);
            omen = new IncantedOmenCardPlayed(this, this.omenStacks, null, AbstractCard.CardType.SKILL, null, -1);

        } else {
//            GranblueBosses.logger.info("Setting omen card type " + AbstractCard.CardType.ATTACK);
            omen = new IncantedOmenCardPlayed(this, this.omenStacks, null, AbstractCard.CardType.ATTACK, null, -1);
        }
        addToBot(new ApplyPowerAction(this, this, omen));

    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new SolomonAxe(), new AnimaBaal());
            MonsterUtils.handleCardPlusRelicLinkedReward(new SolomonAxe(), new BaalCall());

//            RewardItem reward2 = new RewardItem(new SolomonAxe());
//
//            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
//            reward.text = "Forge a pact with this primal beast";
//            reward.relic = new AnimaBaal();
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
        HALF_STEP = MOVES[0];
        ROCK_OUT = MOVES[1];
        ANTHEM = MOVES[2];
    }


}

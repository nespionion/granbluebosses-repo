package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.Arcarum.ArcarumDeathCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.aMonsters.act2.DeathSentencePower;
import granbluebosses.powers.common.PhalanxPower;
import granbluebosses.relics.act2.ArcarumDeathReversed;
import granbluebosses.util.Sounds;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumDeath extends CustomMonster {
    protected static final String MONSTER_NAME = "Death";
    public static final String MONSTER_ID = makeID("ArcarumDeath");
    protected static final int MONSTER_MAX_HP = 113;
    protected static final int MONSTER_MAX_HP_A_19 = 113 + 13;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "ArcarumDeath".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected int phalanxPerTurn;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected int deathWaltzDmg;
    protected int deathWaltzHits;
    protected int deathlyThirteenDmg;
    protected int deathlyThirteenStacks;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public ArcarumDeath() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        this.deathWaltzHits = 13;

        if (AbstractDungeon.ascensionLevel >= 17){
            this.phalanxPerTurn = 50;
            this.deathlyThirteenDmg = 13;
            this.deathWaltzDmg = 2;
            this.deathlyThirteenStacks = 13;
        } else {
            this.phalanxPerTurn = 25;
            this.deathlyThirteenDmg = 13;
            this.deathWaltzDmg = 1;
            this.deathlyThirteenStacks = 13;
        }

        this.damage.add(new DamageInfo(this, this.deathWaltzDmg, DamageInfo.DamageType.NORMAL));

        this.damage.add(new DamageInfo(this, this.deathlyThirteenDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {
        addToBot(new ApplyPowerAction(this, this, new PhalanxPower(this, this.phalanxPerTurn), this.phalanxPerTurn));

        if (ConfigMenu.enableDMCAMusic){CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_BATTLE_ARCARUM);
        }

        addToBot(new ApplyPowerAction(this, this, new DeathSentencePower(this, 13), 13));

        super.usePreBattleAction();
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useDeathWaltz();
                break;
            case 1:
                this.useDeathlyThirteen();
                break;
        }
        this.prepareIntent();
    }

    protected void useDeathWaltz(){

        addToBot(new ShoutAction(this, DIALOG[2], 1.0F, 2.0F));
        addToBot(new SFXAction(Sounds.DEATH_DIALOG_WALTZ));

        addToBot(new ApplyPowerAction(this, this, new PhalanxPower(this, this.phalanxPerTurn), this.phalanxPerTurn));

        for (int i = 0; i < this.deathWaltzHits / 3; i ++){
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    protected void useDeathlyThirteen(){

        addToBot(new ShoutAction(this, DIALOG[0], 1.0F, 2.0F));
        addToBot(new SFXAction(Sounds.DEATH_DIALOG_13));
        if (!this.hasPower(DeathSentencePower.POWER_ID)) addToBot(new ApplyPowerAction(this, this, new DeathSentencePower(this, this.deathlyThirteenStacks), this.deathlyThirteenStacks));
        addToBot(new ApplyPowerAction(this, this, new PhalanxPower(this, this.phalanxPerTurn), this.phalanxPerTurn));
    }

    protected void prepareIntent() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, (byte) 1, Intent.ATTACK_BUFF, this.deathlyThirteenDmg, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, (byte) 0, Intent.ATTACK, this.deathWaltzDmg, this.deathWaltzHits, true));
                break;
        }
    }

    protected void prepareIntentA17() {
        if (AbstractDungeon.aiRng.randomBoolean()) {
            addToBot(new SetMoveAction(this, (byte) 0, Intent.ATTACK, this.deathWaltzDmg, this.deathWaltzHits, true));
        } else {
            addToBot(new SetMoveAction(this, (byte) 1, Intent.ATTACK_BUFF, this.deathlyThirteenDmg, 1, false));
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove((byte) 0, Intent.ATTACK, this.deathWaltzDmg, this.deathWaltzHits, true);
        }
    }

    @Override
    public void die() {
        addToBot(new ShoutAction(this, DIALOG[1], 1.0F, 2.0F));
        addToBot(new SFXAction(Sounds.DEATH_DIALOG_DIED));

        if (ConfigMenu.enableExtraRewards) {
            MonsterUtils.handleCardPlusRelicLinkedReward(new ArcarumDeathReversed(), new ArcarumDeathCard());
        }

        Act2Arcarum.resumeMainMusic();

        super.die();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}


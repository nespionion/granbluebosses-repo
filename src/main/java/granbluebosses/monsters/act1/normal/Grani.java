package granbluebosses.monsters.act1.normal;

import VideoTheSpire.actions.RunTopLevelEffectAction;
import VideoTheSpire.effects.SimplePlayVideoEffect;
import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.EmpowerEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act1Skies;
import granbluebosses.cards.rewards.Magna1.GraniCall;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.act1.BowOfSigurd;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;
import static granbluebosses.GranblueBosses.videoPath;

public class Grani extends CustomMonster {
    protected static final String MONSTER_NAME = "Grani";
    public static final String MONSTER_ID = makeID("Grani");
    protected static final int MONSTER_MAX_HP = 70;
    protected static final int MONSTER_MAX_HP_A_19 = MONSTER_MAX_HP + 2;
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
    public static final String SARAV;
    public static final String VALAC;
    public static final String BOW;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    protected int bowDmg = 39;
    protected int saravStacks = 1;

    public static final int IGNITE_INDEX = 0;

    public Grani() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }
        if (AbstractDungeon.ascensionLevel >= 2){
            this.saravStacks += 1;
        }
        if (AbstractDungeon.ascensionLevel >= 17){
            this.saravStacks += 1;
            this.bowDmg *= 2;
        }

        this.damage.add(new DamageInfo(this, this.bowDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {
        if (ConfigMenu.enableDMCAMusic){CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT1_BATTLE);
        }
        if (AbstractDungeon.ascensionLevel < 17){
            addToTop(new ApplyPowerAction(AbstractDungeon.player, this, new BarricadePower(AbstractDungeon.player)));
        }
        super.usePreBattleAction();
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useNohlSaravValge();
                break;
            case 1:
                this.useNohlSaravValge();
                break;
            case 2:
                this.useNohlSaravValge();
                break;
            case 3:
                this.useVlac();
                break;
            case 4:
                this.useThunderflashBow();
                break;
        }
        this.prepareIntent();
    }


    protected void useNohlSaravValge(){
        float vfxSpeed = 0.1F;
        if (Settings.FAST_MODE) {
            vfxSpeed = 0.0F;
        }

        addToBot(new VFXAction(new EmpowerEffect(this.hb.cX, this.hb.cY), vfxSpeed));
        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, saravStacks), saravStacks));
    }

    protected void useVlac(){
        addToBot(new AnimateSlowAttackAction(this));
        if (AbstractDungeon.ascensionLevel >= 17){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 99, true), 99));
        } else {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
        }
    }

    protected void useThunderflashBow(){
        addToBot(new SFXAction(Sounds.GRANI_BOW_SFX));

        addToBot(new RunTopLevelEffectAction(new SimplePlayVideoEffect(videoPath("grani/graniSilent.webm"))));

        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    protected void prepareIntent() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, SARAV, (byte)1, Intent.BUFF));
                break;
            case 1:
                addToBot(new SetMoveAction(this, SARAV, (byte)2, Intent.BUFF));
                break;
            case 2:
                addToBot(new SetMoveAction(this, VALAC, (byte)3, Intent.DEBUFF));
                break;
            case 3:
                addToBot(new SetMoveAction(this, BOW, (byte)4, Intent.ATTACK, this.damage.get(0).base, 1, false));
                break;
            case 4:
                addToBot(new SetMoveAction(this, SARAV, (byte)1, Intent.BUFF));
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 1:
            case 3:
            case 4:
                addToBot(new SetMoveAction(this, SARAV, (byte)2, Intent.BUFF));
                break;
            case 2:
                addToBot(new SetMoveAction(this, SARAV, (byte)0, Intent.BUFF));
                break;
            case 0:
                OmenUtils.onPrepOmenSFX(this);
                addToBot(new SetMoveAction(this, BOW, (byte)4, Intent.ATTACK, this.damage.get(0).base, 1, false));
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            if (AbstractDungeon.ascensionLevel >= 17){
                this.setMove(VALAC, (byte)3, Intent.DEBUFF);
                this.createIntent();
                addToBot(new SetMoveAction(this, VALAC, (byte)3, Intent.DEBUFF));
            } else {
                this.setMove(SARAV, (byte)0, Intent.BUFF);
                this.createIntent();
                addToBot(new SetMoveAction(this, SARAV, (byte)0, Intent.BUFF));
            }

        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new BowOfSigurd(), new AnimaGrani());
            MonsterUtils.handleCardPlusRelicLinkedReward(new BowOfSigurd(), new GraniCall());

//            RewardItem reward2 = new RewardItem(new BowOfSigurd());
//
//            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
//            reward.text = "Forge a pact with this primal beast";
//            reward.relic = new AnimaGrani();
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
        SARAV = MOVES[0];
        VALAC = MOVES[1];
        BOW = MOVES[2];

    }
}

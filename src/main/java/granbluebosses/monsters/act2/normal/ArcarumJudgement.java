package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.cards.rewards.Arcarum.ArcarumJudgementCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.GranblueBosses;
import granbluebosses.powers.aMonsters.act2.JudgementTrumpet;
import granbluebosses.powers.incantedOmens.AbstractIncantedOmen;
import granbluebosses.powers.incantedOmens.IncantedOmenPowersApplied;
import granbluebosses.relics.act2.ArcarumJudgementReversed;
import granbluebosses.util.Sounds;
import granbluebosses.util.MonsterUtils;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumJudgement extends CustomMonster implements IncantedOmenEnemy {
    protected static final String MONSTER_NAME = "Judgement";
    public static final String MONSTER_ID = makeID("ArcarumJudgement");
    protected static final int MONSTER_MAX_HP = 120;
    protected static final int MONSTER_MAX_HP_A_19 = 120 + 20;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "ArcarumJudgement".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String TRUMPET_TURN1;
    public static final String VERDICT_SPHERE;
    public static final String GUILTY_VERDICT;

    public final int omenStacks;
    private final int verdictSphereDmg;
    private final int verdictSphereHits;
    private final int guiltyVerdictDmg;
    private int guiltyVerdictStacks;
    private final int trumpetBlock;


    public ArcarumJudgement() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
            this.trumpetBlock = 20;
        } else {
            this.setHp(MONSTER_MAX_HP);
            this.trumpetBlock = 20;
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.omenStacks = 4;
        } else {
            this.omenStacks = 4;
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.verdictSphereDmg = 4;
            this.verdictSphereHits = 5;
            this.guiltyVerdictDmg = 4;
            this.guiltyVerdictStacks = 2;
        } else {
            this.verdictSphereDmg = 5;
            this.verdictSphereHits = 4;
            this.guiltyVerdictDmg = 3;
            this.guiltyVerdictStacks = 1;
        }


        this.damage.add(new DamageInfo(this, this.verdictSphereDmg, DamageInfo.DamageType.NORMAL));

        this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
    }

    @Override
    public void usePreBattleAction() {
        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT2_BATTLE_ARCARUM);
        }

        super.usePreBattleAction();
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useTurn1Trumpet();
                break;
            case 1:
                this.useVerdictSphere();
                break;
            case 2:
                this.useGuiltyVerdict();
                break;
        }
        this.prepareIntent();
    }

    public void useTurn1Trumpet(){
        addToBot(new ShoutAction(this, DIALOG[0], 1.0F, 2.0F));
        addToBot(new SFXAction(Sounds.JUDGE_DIALOG_ENTRY));


        addToBot(new ApplyPowerAction(this, this, new JudgementTrumpet(this, 1), 1));
        addToBot(new GainBlockAction(this, this, this.trumpetBlock));
    }

    public void useVerdictSphere(){

        addToBot(new ShoutAction(this, DIALOG[1], 1.0F, 2.0F));
        addToBot(new SFXAction(Sounds.JUDGE_DIALOG_JUDGEMENT));

        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.GREEN, ShockWaveEffect.ShockWaveType.NORMAL), 0.2f));
        for (int i = 0; i < this.verdictSphereHits; i++){
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

        addToBot(new RemoveSpecificPowerAction(this, this, StrengthPower.POWER_ID));
    }

    public void useGuiltyVerdict(){
        int stackAmount = this.hasPower(StrengthPower.POWER_ID) ? this.getPower(StrengthPower.POWER_ID).amount : 0;

        addToBot(new RemoveSpecificPowerAction(this, this, StrengthPower.POWER_ID));

        addToBot(new ShoutAction(this, DIALOG[2], 1.0F, 2.0F));
        addToBot(new SFXAction(Sounds.JUDGE_DIALOG_GUILTY));

        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Color.GREEN, ShockWaveEffect.ShockWaveType.NORMAL), 0.2f));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, stackAmount, true)));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, stackAmount, true)));
    }

    protected void prepareIntent() {
        addToBot(new SetMoveAction(this, VERDICT_SPHERE, (byte) 1, Intent.ATTACK, this.damage.get(0).base, this.verdictSphereHits, false));
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(TRUMPET_TURN1, (byte) 0, Intent.DEFEND_BUFF);
        }
    }

    @Override
    public void resolveOmen() {
        addToBot(new ShoutAction(this, DIALOG[3], 1.0F, 2.0F));
        addToBot(new SFXAction(Sounds.JUDGE_DIALOG_INNOCENT));

        addToBot(new RemoveSpecificPowerAction(this, this, IncantedOmenPowersApplied.POWER_ID));
        this.setMove(GUILTY_VERDICT, (byte)2, Intent.DEBUFF);
        this.createIntent();
        addToBot(new SetMoveAction(this, GUILTY_VERDICT, (byte)2, Intent.DEBUFF));
    }

    @Override
    public void applyOmen() {
        AbstractIncantedOmen omen = new IncantedOmenPowersApplied(this, this.omenStacks, AbstractPower.PowerType.BUFF, false, false);
        addToBot(new ApplyPowerAction(this, this, omen));

    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
            MonsterUtils.handleCardPlusRelicLinkedReward(new ArcarumJudgementReversed(), new ArcarumJudgementCard());
        }

        Act2Arcarum.resumeMainMusic();

        super.die();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        TRUMPET_TURN1 = MOVES[0];
        VERDICT_SPHERE = MOVES[1];
        GUILTY_VERDICT = MOVES[2];
    }
}

package granbluebosses.monsters.act2.normal;

import basemod.abstracts.CustomMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.cards.rewards.Arcarum.ArcarumJusticeCard;
import granbluebosses.cards.tempInCombat.JusticeTransferPower;
import granbluebosses.powers.aMonsters.act2.DeathSentencePower;
import granbluebosses.powers.aMonsters.act2.UnrighteousnessPower;
import granbluebosses.powers.common.PhalanxPower;
import granbluebosses.relics.act2.ArcarumJusticeReversed;
import granbluebosses.util.Sounds;
import granbluebosses.util.MonsterUtils;
import granbluebosses.acts.Act2Arcarum;
import granbluebosses.config.ConfigMenu;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumJustice extends CustomMonster {
    protected static final String MONSTER_NAME = "Justice";
    public static final String MONSTER_ID = makeID("ArcarumJustice");
    protected static final int MONSTER_MAX_HP = 111;
    protected static final int MONSTER_MAX_HP_A_19 = 111 + 11;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = "ArcarumJustice".toLowerCase();
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected boolean firstTurn = true;
    private final int MULT_TRIGGER;
    protected static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final String JURIST_SCALES;
    public static final String ABRAXAS;
    public static final String REMEMBRANCE;
    private final int abraxasDmg;
    private final int abraxasHits = 2;

    public ArcarumJustice() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }

        if (AbstractDungeon.ascensionLevel >= 2){
            this.abraxasDmg = 12;
        } else {
            this.abraxasDmg = 11;
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            this.MULT_TRIGGER = 2;
        } else {
            this.MULT_TRIGGER = 1;
        }

        this.damage.add(new DamageInfo(this, this.abraxasDmg, DamageInfo.DamageType.NORMAL));

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
                this.useJuristScales();
                break;
            case 1:
                this.useAbraxas();
                break;
            case 2:
                this.useRemembranceSelf();
                break;
            case 3:
                this.useRemembrancePlayer();
                break;
        }
        this.prepareIntent();
    }

    protected void useJuristScales(){

        if (AbstractDungeon.monsterRng.randomBoolean()){
            addToBot(new ShoutAction(this, DIALOG[0]));
            addToBot(new SFXAction(Sounds.JUSTICE_SCALE_1));
        } else {
            addToBot(new ShoutAction(this, DIALOG[1]));
            addToBot(new SFXAction(Sounds.JUSTICE_SCALE_2));
        }

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new UnrighteousnessPower(AbstractDungeon.player, false), 1));

        addToBot(new MakeTempCardInHandAction(new JusticeTransferPower()));
    }

    protected void useAbraxas(){

        this.addToBot(new VFXAction(this, new CleaveEffect(true), 0.1F));
        for (int i = 0; i < this.abraxasHits; i++){
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
        }

        if (AbstractDungeon.ascensionLevel >= 17){

            addToBot(new TextAboveCreatureAction(this, "Punishment Overruled"));
            addToBot(new ShoutAction(this, DIALOG[2]));
            addToBot(new SFXAction(Sounds.JUSTICE_ABRAXAS));

            addToBot(new RemoveSpecificPowerAction(this, this, UnrighteousnessPower.POWER_ID));
        }
    }

    protected void useRemembranceSelf(){

        addToBot(new VFXAction(this, new InflameEffect(this), 0.4F));
        addToBot(new ShoutAction(this, DIALOG[4]));
        addToBot(new SFXAction(Sounds.JUSTICE_REM_SELF));

        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 4, true), 4));
    }

    protected void useRemembrancePlayer(){
        addToBot(new VFXAction(this, new InflameEffect(AbstractDungeon.player), 0.4F));
        addToBot(new ShoutAction(this, DIALOG[3]));
        addToBot(new SFXAction(Sounds.JUSTICE_REM_PLAYER));

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, 2), 2));
    }

    protected void prepareIntent() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove){
            case 0:
                addToBot(new SetMoveAction(this, ABRAXAS, (byte) 1, Intent.ATTACK, this.damage.get(0).base, 2, true));
                break;
            case 1:
                if (AbstractDungeon.player.currentHealth * 2 >= this.currentHealth){
                    addToBot(new SetMoveAction(this, REMEMBRANCE, (byte) 2, Intent.BUFF));
                } else {
                    addToBot(new SetMoveAction(this, REMEMBRANCE, (byte) 3, Intent.UNKNOWN));
                }
                break;
            case 2:
            case 3:
                addToBot(new SetMoveAction(this, JURIST_SCALES, (byte) 0, Intent.DEBUFF));
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove){
            case 0:
                addToBot(new SetMoveAction(this, ABRAXAS, (byte) 1, Intent.ATTACK, this.damage.get(0).base, 2, true));
                break;
            case 1:
                if (AbstractDungeon.player.currentHealth * this.MULT_TRIGGER >= this.currentHealth){
                    addToBot(new SetMoveAction(this, REMEMBRANCE, (byte) 2, Intent.BUFF));
                } else {
                    addToBot(new SetMoveAction(this, REMEMBRANCE, (byte) 3, Intent.UNKNOWN));
                }
                break;
            case 2:
            case 3:
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    addToBot(new SetMoveAction(this, JURIST_SCALES, (byte) 0, Intent.DEBUFF));
                } else {
                    addToBot(new SetMoveAction(this, ABRAXAS, (byte) 1, Intent.ATTACK, this.damage.get(0).base, 2, true));
                }
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(JURIST_SCALES, (byte) 0, Intent.DEBUFF);
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
            MonsterUtils.handleCardPlusRelicLinkedReward(new ArcarumJusticeReversed(), new ArcarumJusticeCard());
        }

        Act2Arcarum.resumeMainMusic();

        super.die();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(MONSTER_ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        JURIST_SCALES = MOVES[0];
        ABRAXAS = MOVES[0];
        REMEMBRANCE = MOVES[0];
    }
}


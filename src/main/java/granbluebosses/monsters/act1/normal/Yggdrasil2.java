package granbluebosses.monsters.act1.normal;

import basemod.abstracts.CustomMonster;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act1Skies;
import granbluebosses.cards.rewards.Magna1.YggdrasilOmega;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.relics.act1.YggdrasilBowOmega;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class Yggdrasil2 extends CustomMonster {
    protected static final String MONSTER_NAME = "Yggdrasil";
    public static final String MONSTER_ID = makeID("Yggdrasil2");
    protected static final int MONSTER_MAX_HP = 50;
    protected static final int MONSTER_MAX_HP_A_19 = 50 + 2;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String) null;
    protected static final String MONSTER_ANIM_URL = MONSTER_NAME.toLowerCase() + "2";
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected int OMEN_MULT = 4;
    protected boolean firstTurn = true;
    protected int axisMundiDmg = 10;
    protected int luminoxGenesiStacks = 2;
    protected int songOfGraceDmg = 3;
    protected int songOfGraceHits = 1;
    protected static final MonsterStrings monsterStrings;
    public static final String LUMINOX_GENESI;
    public static final String AXIS_MUNDI;
    public static final String SONG_OF_GRACE;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;

    public static final int AXIS_MUNDI_INDEX = 0;
    public static final int SONG_OF_GRACE_INDEX = 1;

    public Yggdrasil2() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MONSTER_MAX_HP_A_19);
        } else {
            this.setHp(MONSTER_MAX_HP);
        }
        if (AbstractDungeon.ascensionLevel >= 2){
            this.axisMundiDmg += 4;
        }

        if (AbstractDungeon.ascensionLevel < 17){
            this.songOfGraceDmg = 1;
        }
        if (AbstractDungeon.ascensionLevel > 20){
            this.OMEN_MULT = 2;
        }

        if (ConfigMenu.modestyFilter){
            this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + "Cen.atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + "Cen.json"), 1.0F);
        } else {
            this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);

        }

        this.damage.add(new DamageInfo(this, axisMundiDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, songOfGraceDmg, DamageInfo.DamageType.NORMAL));
        this.songOfGraceHits = 1;

    }

    @Override
    public void usePreBattleAction() {
        StanceOmen omen = new StanceOmen(this);
        omen.setUpOmenByHp(OMEN_MULT);
        addToTop(new ApplyPowerAction(this, this, omen));
        super.usePreBattleAction();
        this.songOfGraceHits = AbstractDungeon.ascensionLevel >= 17 ? AbstractDungeon.player.masterDeck.size() : (int)(AbstractDungeon.player.masterDeck.size() * 0.75);

        if (ConfigMenu.enableDMCAMusic){
            CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT1_BATTLE);
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useLuminoxGenesi();
                break;
            case 1:
                this.useAxisMundi();
                break;
            case 2:
                this.useSongOfGraceHurt();
                this.useSongOfGraceHeal();

        }
        this.prepareIntent();
    }

    protected void useAxisMundi(){
        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(AXIS_MUNDI_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

    }

    protected void useLuminoxGenesi(){
        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.CREAM_COLOR, ShockWaveEffect.ShockWaveType.NORMAL), 0.0F));
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.cost == c.costForTurn){
                c.setCostForTurn(c.cost + 1);
            }
        }
    }

    protected void useSongOfGraceHurt(){
        for (int i = 0; i < songOfGraceHits; i++){
            addToBot(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.NORMAL), 0.3F));
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(SONG_OF_GRACE_INDEX), AbstractGameAction.AttackEffect.NONE));
        }
    }

    protected void useSongOfGraceHeal(){
        for (int i = 0; i < songOfGraceHits; i++){
            addToBot(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.NORMAL), 0.3F));
            addToBot(new HealAction(AbstractDungeon.player, this, this.damage.get(SONG_OF_GRACE_INDEX).base));
        }
    }

    protected void prepareIntent() {
        if (this.currentHealth * this.OMEN_MULT <= this.maxHealth && trigger){
            this.trigger = false;


            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));

            if (!this.hasPower(StunMonsterPower.POWER_ID)) {
                addToBot(new TextAboveCreatureAction(this, "DANGER!"));
                OmenUtils.onPrepOmenSFX(this);
                this.setMove(SONG_OF_GRACE, (byte)2, Intent.ATTACK_BUFF, this.damage.get(SONG_OF_GRACE_INDEX).base, this.songOfGraceHits, true);
                this.createIntent();
                addToBot(new SetMoveAction(this, SONG_OF_GRACE, (byte)2, Intent.ATTACK_BUFF, this.damage.get(SONG_OF_GRACE_INDEX).base, this.songOfGraceHits, true));
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
                addToBot(new SetMoveAction(this, AXIS_MUNDI, (byte)1, Intent.ATTACK, this.damage.get(AXIS_MUNDI_INDEX).base, 1, false));
                break;
            case 1:
                addToBot(new SetMoveAction(this, LUMINOX_GENESI, (byte)0, Intent.UNKNOWN));
                break;
            case 2:
                addToBot(new SetMoveAction(this, LUMINOX_GENESI, (byte)0, Intent.UNKNOWN));
                break;
            default:
                addToBot(new SetMoveAction(this, LUMINOX_GENESI, (byte)0, Intent.UNKNOWN));
                break;
        }
    }

    protected void prepareIntentA17() {
        switch (this.nextMove) {
            case 0:
                addToBot(new SetMoveAction(this, AXIS_MUNDI, (byte)1, Intent.ATTACK, this.damage.get(AXIS_MUNDI_INDEX).base, 1, false));
                break;
            case 1:
                if (AbstractDungeon.aiRng.randomBoolean()) {
                    addToBot(new SetMoveAction(this, LUMINOX_GENESI, (byte)0, Intent.UNKNOWN));
                } else {
                    addToBot(new SetMoveAction(this, AXIS_MUNDI, (byte)1, Intent.ATTACK, this.damage.get(AXIS_MUNDI_INDEX).base, 1, false));
                }
                break;
            case 2:
                addToBot(new SetMoveAction(this, AXIS_MUNDI, (byte)1, Intent.ATTACK, this.damage.get(AXIS_MUNDI_INDEX).base, 1, false));
                break;
            default:
                addToBot(new SetMoveAction(this, AXIS_MUNDI, (byte)1, Intent.ATTACK, this.damage.get(AXIS_MUNDI_INDEX).base, 1, false));
                break;
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            if (AbstractDungeon.ascensionLevel < 17) {
                this.setMove(LUMINOX_GENESI, (byte)0, Intent.DEBUFF);
            } else {
                this.setMove(AXIS_MUNDI, (byte)1, Intent.ATTACK, this.damage.get(AXIS_MUNDI_INDEX).base, 1, false);
            }
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new YggdrasilBowOmega(), new AnimaYggdrasil());
            MonsterUtils.handleCardPlusRelicLinkedReward(new YggdrasilBowOmega(), new YggdrasilOmega());

//            RewardItem reward2 = new RewardItem(new YggdrasilBowOmega());
//
//            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
//            reward.text = "Forge a pact with this primal beast";
//            reward.relic = new AnimaYggdrasil();
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
        LUMINOX_GENESI = MOVES[0];
        AXIS_MUNDI = MOVES[1];
        SONG_OF_GRACE = MOVES[2];
    }
}

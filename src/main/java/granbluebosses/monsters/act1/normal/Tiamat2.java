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
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act1Skies;
import granbluebosses.cards.rewards.Magna1.TiamatOmega;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.OmenUtils;
import granbluebosses.powers.stanceOmens.StanceOmen;
import granbluebosses.powers.aMonsters.DebuffOnHit;
import granbluebosses.relics.act1.TiamatGlaiveOmega;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;


public class Tiamat2 extends CustomMonster {
    protected static final String MONSTER_NAME = "Tiamat";
    public static final String MONSTER_ID = makeID("Tiamat2");
    protected static final int MONSTER_MAX_HP = 70;
    protected static final int MONSTER_MAX_HP_A_19 = 72;
    protected static final float MONSTER_HIT_BOX_X = 0;
    protected static final float MONSTER_HIT_BOX_Y = -30.0F;
    protected static final float MONSTER_HIT_BOX_WIDTH = 400.0F;
    protected static final float MONSTER_HIT_BOX_HEIGHT = 350.0F;
    protected static final String MONSTER_IMG_URL = (String)null;
    protected static final String MONSTER_ANIM_URL = MONSTER_NAME.toLowerCase() + "2";
    protected static final float MONSTER_OFF_SET_X = 0.0F;
    protected static final float MONSTER_OFF_SET_Y = 28.0F;
    protected boolean trigger = true;
    protected int OMEN_MULT = 4;
    protected boolean firstTurn = true;
    protected static final MonsterStrings monsterStrings;
    public static final String CRIPPLING_STORM;
    public static final String WIND_TORRENT;
    public static final String TERROR_ABSOLUTE;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    protected int cripplingStormStacks = 2;
    protected int windTorrentDmg = 10;
    protected int terrorAbsoluteDmg = 17;
    protected int terrorAbsoluteStacks = 2;

    public static final int WIND_TORRENT_INDEX = 0;
    public static final int TERROR_ABSOLUTE_INDEX = 1;

    public Tiamat2() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MONSTER_MAX_HP_A_19);

        } else {
            this.setHp(MONSTER_MAX_HP);;
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.windTorrentDmg += 3;
        } else {
            this.terrorAbsoluteDmg -= 3;
        }
        if (AbstractDungeon.ascensionLevel >= 17) {
            this.terrorAbsoluteStacks *= 1.5f;
        }
        if (AbstractDungeon.ascensionLevel > 20){
            this.OMEN_MULT = 2;
        }

        if (ConfigMenu.modestyFilter){
            this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + "Cen.atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + "Cen.json"), 1.0F);
        } else {
            this.loadAnimation(GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".atlas"), GranblueBosses.monsterPath(MONSTER_ANIM_URL + "/" + MONSTER_ANIM_URL + ".json"), 1.0F);
        }


        this.damage.add(new DamageInfo(this, windTorrentDmg, DamageInfo.DamageType.NORMAL));
        this.damage.add(new DamageInfo(this, terrorAbsoluteDmg, DamageInfo.DamageType.NORMAL));

    }

    @Override
    public void usePreBattleAction() {
        StanceOmen omen = new StanceOmen(this);
        omen.setUpOmenByHp(OMEN_MULT);
        addToTop(new ApplyPowerAction(this, this, omen));
        super.usePreBattleAction();

        if (ConfigMenu.enableDMCAMusic){CardCrawlGame.music.fadeAll();
            AbstractDungeon.getCurrRoom().playBgmInstantly(Sounds.MUSIC_ACT1_BATTLE);
        }

        if (AbstractDungeon.ascensionLevel >= 17){
            addToBot(new ApplyPowerAction(this, this, new DebuffOnHit(this, DebuffOnHit.AvailableDebuffs.FRAIL, 1)));
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                this.useWindTorrent();
                break;
            case 1:
                this.useCripplingStorm();
                break;
            case 2:
                this.useTerrorAbsolute();
                break;
        }
        this.prepareIntent();
    }

    public void useCripplingStorm(){
        this.addToBot(new SFXAction("ATTACK_WHIRLWIND"));
        addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, cripplingStormStacks, true)));
    }

    public void useWindTorrent(){
        this.addToBot(new SFXAction("ATTACK_WHIRLWIND"));
        addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
        addToBot(new AnimateSlowAttackAction(this));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(WIND_TORRENT_INDEX), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    public void useTerrorAbsolute(){
        this.addToBot(new SFXAction("ATTACK_WHIRLWIND"));
        addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
        addToBot(new VFXAction(new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.NORMAL), 0.0F));
        
        addToBot(new MakeTempCardInDiscardAction(new Dazed(), terrorAbsoluteStacks));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(TERROR_ABSOLUTE_INDEX), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    protected  void prepareIntent(){
        if (this.currentHealth * this.OMEN_MULT <= this.maxHealth && trigger){
            this.trigger = false;
            addToTop(new RemoveSpecificPowerAction(this, this, StanceOmen.POWER_ID));

            if (!this.hasPower(StunMonsterPower.POWER_ID)) {
                addToBot(new TextAboveCreatureAction(this, "DANGER!"));
                OmenUtils.onPrepOmenSFX(this);

                this.setMove(TERROR_ABSOLUTE, (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(TERROR_ABSOLUTE_INDEX).base);

                this.createIntent();

                addToBot(new SetMoveAction(this, TERROR_ABSOLUTE, (byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(TERROR_ABSOLUTE_INDEX).base));
                return;
            } else {
                OmenUtils.onCancelOmenSFX(this);
            }
        }
        if (AbstractDungeon.ascensionLevel >= 17){
            this.prepareIntentA17();
            return;
        }
        switch (this.nextMove){
            case 0:
                addToBot(new SetMoveAction(this, CRIPPLING_STORM, (byte)1, Intent.DEBUFF));
                break;
            case 1:
                addToBot(new SetMoveAction(this, WIND_TORRENT, (byte)0, Intent.ATTACK, this.damage.get(WIND_TORRENT_INDEX).base));
                break;
        }
    }

    protected  void prepareIntentA17(){
        switch (this.nextMove){
            case 0:
                if (AbstractDungeon.aiRng.randomBoolean()){
                    addToBot(new SetMoveAction(this, WIND_TORRENT, (byte)0, Intent.ATTACK, this.damage.get(WIND_TORRENT_INDEX).base));
                } else {
                    addToBot(new SetMoveAction(this, CRIPPLING_STORM, (byte)1, Intent.DEBUFF));
                }
                break;
            case 1:
                addToBot(new SetMoveAction(this, WIND_TORRENT, (byte)0, Intent.ATTACK, this.damage.get(WIND_TORRENT_INDEX).base));
                break;
        }

    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn){
            this.firstTurn = false;
            if (AbstractDungeon.ascensionLevel >= 17){
                this.setMove(WIND_TORRENT, (byte)0, Intent.ATTACK, this.damage.get(WIND_TORRENT_INDEX).base);
            } else {
                this.setMove(CRIPPLING_STORM, (byte)1, Intent.DEBUFF);
            }
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new TiamatGlaiveOmega(), new AnimaTiamat());
            MonsterUtils.handleCardPlusRelicLinkedReward(new TiamatGlaiveOmega(), new TiamatOmega());

//            RewardItem reward2 = new RewardItem(new TiamatGlaiveOmega());
//
//            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
//            reward.text = "Forge a pact with this primal beast";
//            reward.relic = new AnimaTiamat();
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
        CRIPPLING_STORM = MOVES[0];
        WIND_TORRENT = MOVES[1];
        TERROR_ABSOLUTE = MOVES[2];
    }
}

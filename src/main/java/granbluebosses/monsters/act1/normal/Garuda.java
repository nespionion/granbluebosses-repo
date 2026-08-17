package granbluebosses.monsters.act1.normal;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.acts.Act1Skies;
import granbluebosses.cards.rewards.Magna1.GarudaCall;
import granbluebosses.config.ConfigMenu;
import granbluebosses.relics.act1.PlumeOfSuparna;
import granbluebosses.util.MonsterUtils;
import granbluebosses.util.Sounds;

import static granbluebosses.GranblueBosses.makeID;

public class Garuda extends CustomMonster {
    protected static final String MONSTER_NAME = "Garuda";
    public static final String MONSTER_ID = makeID("Garuda");
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
    protected int OMEN_MULT = 5;
    protected static final MonsterStrings monsterStrings;
    public static final String GARUTMAN;
    public static final String RASAYANA;
    public static final String SURENDRA;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    protected int surendraDmg = 1;
    protected int surendraHits = 4;
    protected int rasayanaBlockHits = 3;

    public Garuda() {
        super(MONSTER_NAME, MONSTER_ID, MONSTER_MAX_HP, MONSTER_HIT_BOX_X, MONSTER_HIT_BOX_Y, MONSTER_HIT_BOX_WIDTH, MONSTER_HIT_BOX_HEIGHT, MONSTER_IMG_URL, MONSTER_OFF_SET_X, MONSTER_OFF_SET_Y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(MONSTER_MAX_HP_A_19);
            this.rasayanaBlockHits += 1;
        } else {
            this.setHp(MONSTER_MAX_HP);
        }
        if (AbstractDungeon.ascensionLevel >= 7){
            this.surendraHits += 1;
        }
        if (AbstractDungeon.ascensionLevel >= 17){
            this.surendraHits += 1;
        }
        if (AbstractDungeon.ascensionLevel > 20){
            this.OMEN_MULT = 3;
        }

        this.damage.add(new DamageInfo(this, this.surendraDmg, DamageInfo.DamageType.NORMAL));

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
                if (AbstractDungeon.ascensionLevel >= 17){
                    this.useGarutmanA17();
                } else {
                    this.useGarutman();
                }
                break;
            case 1:
                this.useRasayana();
                break;
            case 2:
                this.useSurendra();
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
            case 2:
                addToBot(new SetMoveAction(this, RASAYANA, (byte)1, Intent.DEFEND));
                break;
            case 1:
                addToBot(new SetMoveAction(this, SURENDRA, (byte)2, Intent.ATTACK, this.damage.get(0).base, this.surendraHits, true));
                break;
        }
    }

    protected void prepareIntentA17() {
        if (AbstractDungeon.aiRng.randomBoolean()){
            addToBot(new SetMoveAction(this, RASAYANA, (byte)1, Intent.DEFEND));
        } else {
            addToBot(new SetMoveAction(this, SURENDRA, (byte)2, Intent.ATTACK, this.damage.get(0).base, this.surendraHits, true));
        }
    }

    protected void useGarutman(){
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.rarity == AbstractCard.CardRarity.UNCOMMON || c.rarity == AbstractCard.CardRarity.RARE){
                addToBot(new DiscardSpecificCardAction(c, AbstractDungeon.player.drawPile));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                addToBot(new ApplyPowerAction(this, this, new DexterityPower(this, 1), 1));
                break;
            }
        }
    }

    protected void useGarutmanA17(){
        AbstractCard cardToExhaust = null;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.rarity == AbstractCard.CardRarity.UNCOMMON || c.rarity == AbstractCard.CardRarity.RARE){
                cardToExhaust = c;
                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                addToBot(new ApplyPowerAction(this, this, new DexterityPower(this, 1), 1));
                break;
            }
        }
        if (cardToExhaust == null) return;
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c.rarity == AbstractCard.CardRarity.UNCOMMON || c.rarity == AbstractCard.CardRarity.RARE && cardToExhaust != c){
                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile, true));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                addToBot(new ApplyPowerAction(this, this, new DexterityPower(this, 1), 1));
                break;
            }
        }
//        for (AbstractCard c : AbstractDungeon.player.hand.group){
//            if (c.rarity == AbstractCard.CardRarity.UNCOMMON || c.rarity == AbstractCard.CardRarity.RARE){
//                addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true));
//                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
//                addToBot(new ApplyPowerAction(this, this, new DexterityPower(this, 1), 1));
//                break;
//            }
//        }
    }

    protected void useRasayana(){
        for (int i = 0; i < this.rasayanaBlockHits; i++){
            addToBot(new GainBlockAction(this, this, 1));
        }
    }

    protected void useSurendra(){
        float vfxSpeed = 0.1F;
        if (Settings.FAST_MODE) {
            vfxSpeed = 0.0F;
        }

        for (int i = 0; i < this.surendraHits; i++){
            addToBot(new VFXAction(new WhirlwindEffect(Color.GREEN, true), vfxSpeed));
            addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    protected void getMove(int i) {
        if (this.firstTurn) {
            this.firstTurn = false;
            this.setMove(GARUTMAN, (byte)0, Intent.UNKNOWN);
        }
    }

    @Override
    public void die() {
        if (ConfigMenu.enableExtraRewards) {
//            MonsterUtils.handleEndOfBattleRewards(new PlumeOfSuparna(), new AnimaGaruda());
            MonsterUtils.handleCardPlusRelicLinkedReward(new PlumeOfSuparna(), new GarudaCall());

//            RewardItem reward2 = new RewardItem(new PlumeOfSuparna());
//
//            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
//            reward.text = "Forge a pact with this primal beast";
//            reward.relic = new AnimaGaruda();
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
        GARUTMAN = MOVES[0];
        RASAYANA = MOVES[1];
        SURENDRA = MOVES[2];
    }
}

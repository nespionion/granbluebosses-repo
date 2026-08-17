package granbluebosses.cards.event;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import granbluebosses.cards.BaseCard;
import granbluebosses.cards.faahl.ChaosLegionCard;
import granbluebosses.cards.faahl.ParadiseLostCard;
import granbluebosses.cards.rewards.Arcarum.*;
import granbluebosses.cards.rewards.Magna1.*;
import granbluebosses.cards.rewards.Magna2.AlexielCall;
import granbluebosses.cards.rewards.Magna2.EuropaCall;
import granbluebosses.cards.rewards.Magna2.GrimnirCall;
import granbluebosses.cards.rewards.Magna2.ShivaCall;
import granbluebosses.cards.rewards.Magna3.*;
import granbluebosses.cards.rewards.SixD.*;
import granbluebosses.config.ConfigMenu;
import granbluebosses.monsters.act1.normal.Baal;
import granbluebosses.monsters.act2.normal.ArcarumSun;
import granbluebosses.util.CardStats;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.Arrays;

import static granbluebosses.util.GeneralUtils.removePrefix;
import static granbluebosses.util.TextureLoader.getCardTextureString;

public class PrayersOfTheMoon extends BaseCard {

    public static final String ID = makeID(PrayersOfTheMoon.class.getSimpleName());
    public static final CardStrings currCardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    ; //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC_NUMBER = 0;
    private static final int UPG_MAGIC_NUMBER = 0;
    private static final int COST = 1;
    private static final int UPG_COST = 1; // New number, not added to base cost
    private static final boolean EXHAUST = false;
    private static final boolean EXHAUST_UPG = false;
    private static final boolean ETHEREAL = false;
    private static final boolean UPG_ETHEREAL = false;
    private static final boolean RETAIN = false;
    private static final boolean UPG_RETAIN = false;
    private static final boolean INNATE = false;
    private static final boolean UPG_INNATE = false;
    private static final boolean PURGE = false;
    private static final boolean UPG_PURGE = false;
    private static final boolean FOCUS_SCALING = false;
    private static final CardTags[] cardTags = {

    };

    private static final AbstractCard[] cardMainPool = {
            new CaOngCall(),
            new GilgameshCall(),
            new OdinCall(),
            new PrometheusCall(),
            new ArcarumDeathCard(),
            new ArcarumDevilCard(),
            new ArcarumHangedManCard(),
            new ArcarumJudgementCard(),
            new ArcarumJusticeCard(),
            new ArcarumMoonCard(),
            new ArcarumSunCard(),
            new ArcarumTemperanceCard(),
            new ArcarumTowerCard(),
            new AthenaCall(),
            new BaalCall(),
            new CelesteOmega(),
            new ColossusOmega(),
            new GarudaCall(),
            new GraniCall(),
            new LeviathanOmega(),
            new TiamatOmega(),
            new LuminieraOmega(),
            new TiamatOmega(),
            new YggdrasilOmega(),
            new AlexielCall(),
            new EuropaCall(),
            new GrimnirCall(),
            new ShivaCall()
    };

    private static final AbstractCard[] cardDragonPool = {
            new TheAzuresHalo(),
            new TheBlacksEncroachment(),
            new TheEmeraldsWindfall(),
            new TheGoldsFoundation(),
            new TheVermillionsGlare(),
            new TheWhitesLightfield(),
            new TiamatAuraOmega(),
            new ColossusIraOmega(),
            new LeviathanMareOmega(),
            new YggdrasilArbosOmega(),
            new LuminieraCredoOmega(),
            new CelesteAterOmega(),
    };

    private static final AbstractCard[] cardAstralPool = {
            new ParadiseLostCard(),
            new ChaosLegionCard()
    };


    private static final CardStats info = new CardStats(
            CardColor.COLORLESS, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            COST //The card's base cost. -1 is X cost, -2 is non-defined cost for unplayable cards like curses, or Reflex.
    );


    public PrayersOfTheMoon() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK); //Sets the card's damage and how much it changes when upgraded.
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

        this.setCostUpgrade(UPG_COST);

        this.setExhaust(EXHAUST, EXHAUST_UPG);
        this.setEthereal(ETHEREAL, UPG_ETHEREAL);
        this.setSelfRetain(RETAIN, UPG_RETAIN);
        this.setInnate(INNATE, UPG_INNATE);
        this.purgeOnUse = PURGE;

        tags.addAll(Arrays.asList(cardTags));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.purgeOnUse = UPG_PURGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.cardRng.randomBoolean(0.005f)){
            addToBot(new MakeTempCardInHandAction(cardAstralPool[AbstractDungeon.cardRng.random(cardAstralPool.length-1)].makeCopy()));
        } else if (this.upgraded && AbstractDungeon.cardRng.randomBoolean()){
            addToBot(new MakeTempCardInHandAction(cardDragonPool[AbstractDungeon.cardRng.random(cardDragonPool.length-1)].makeCopy()));
        } else {
            addToBot(new MakeTempCardInHandAction(cardMainPool[AbstractDungeon.cardRng.random(cardMainPool.length-1)].makeCopy()));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (ConfigMenu.modestyFilter){
            String img = getCardTextureString(removePrefix(makeID("PrayersOfTheMoonCen")), this.type);
            this.textureImg = img;
            if (img != null) {
                this.loadCardImage(img);
            }
        }
        super.render(sb);
    }
}
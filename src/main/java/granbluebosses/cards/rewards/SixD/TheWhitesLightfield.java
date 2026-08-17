package granbluebosses.cards.rewards.SixD;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import granbluebosses.cards.BaseCard;
import granbluebosses.powers.cards.TheWhitesLightfieldPower;
import granbluebosses.util.CardStats;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.Arrays;

public class TheWhitesLightfield extends BaseCard {

    public static final String ID = makeID(TheWhitesLightfield.class.getSimpleName());
    public static final CardStrings currCardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    ; //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC_NUMBER = 9;
    private static final int UPG_MAGIC_NUMBER = 3;
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
            CustomTags.SUMMON_CALL
    };


    private static final CardStats info = new CardStats(
            PrimalColor.GBF_PRIMAL_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            COST //The card's base cost. -1 is X cost, -2 is non-defined cost for unplayable cards like curses, or Reflex.
    );


    public TheWhitesLightfield() {
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
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {

        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player, new TheWhitesLightfieldPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
    }


}
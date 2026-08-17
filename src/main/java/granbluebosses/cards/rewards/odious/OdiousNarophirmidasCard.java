package granbluebosses.cards.rewards.odious;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import granbluebosses.action.DispelBuffAction;
import granbluebosses.cards.BaseCard;
import granbluebosses.util.CardStats;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.Arrays;

public class OdiousNarophirmidasCard extends BaseCard {

    public static final String ID = makeID(OdiousNarophirmidasCard.class.getSimpleName());
    public static final CardStrings currCardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    ; //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 2;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 1;
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
            CustomTags.SUMMON_CALL, CustomTags.ODIOUS_CALL
    };


    private static final CardStats info = new CardStats(
            PrimalColor.GBF_PRIMAL_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            COST //The card's base cost. -1 is X cost, -2 is non-defined cost for unplayable cards like curses, or Reflex.
    );


    public OdiousNarophirmidasCard() {
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
        addToBot(new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new DamageAction(p, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters){
            addToBot(new DispelBuffAction(mo, p, this.magicNumber));
        }


        addToBot(new DispelBuffAction(p, p, this.magicNumber));
    }


}
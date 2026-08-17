package granbluebosses.cards.faahl;

import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import granbluebosses.action.DispelBuffAction;
import granbluebosses.cards.BaseCard;
import granbluebosses.cards.BaseSignatureCard;
import granbluebosses.powers.cards.TrancePower;
import granbluebosses.util.CardStats;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.Arrays;

public class ChaosLegionCard extends BaseSignatureCard {

    public static final String ID = makeID(ChaosLegionCard.class.getSimpleName());
    public static final CardStrings currCardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    ; //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 5;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC_NUMBER = 10;
    private static final int UPG_MAGIC_NUMBER = 0;
    private static final int COST = 2;
    private static final int UPG_COST = 2; // New number, not added to base cost
    private static final boolean EXHAUST = false;
    private static final boolean EXHAUST_UPG = false;
    private static final boolean ETHEREAL = false;
    private static final boolean UPG_ETHEREAL = false;
    private static final boolean RETAIN = true;
    private static final boolean UPG_RETAIN = true;
    private static final boolean INNATE = false;
    private static final boolean UPG_INNATE = false;
    private static final boolean PURGE = false;
    private static final boolean UPG_PURGE = false;
    private static final boolean FOCUS_SCALING = false;
    private static final CardTags[] cardTags = {
            CustomTags.SUMMON_CALL
    };
    private static int tranceAmt;

    private static final CardStats info = new CardStats(
            PrimalColor.GBF_PRIMAL_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            COST //The card's base cost. -1 is X cost, -2 is non-defined cost for unplayable cards like curses, or Reflex.
    );


    public ChaosLegionCard() {
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
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);
        if (c.rarity == CardRarity.RARE && (!AbstractDungeon.player.hasPower(TrancePower.POWER_ID) || AbstractDungeon.player.getPower(TrancePower.POWER_ID).amount < 3)){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TrancePower(AbstractDungeon.player, 1), 1));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        super.triggerOnGlowCheck();
        tranceAmt = this.getTranceStacks();

        if (tranceAmt >= 3){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        tranceAmt = this.getTranceStacks();

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));

        if (tranceAmt >= 3){
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), 3));
        }
        if (tranceAmt >= 2){
            addToBot(new DamageAction(m, new DamageInfo(p, 30, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.NONE));
        }
        if (tranceAmt >= 1){
            addToBot(new DispelBuffAction(m, p, 3));
        }
    }

    private int getTranceStacks(){
        return AbstractDungeon.player.hasPower(TrancePower.POWER_ID) ? AbstractDungeon.player.getPower(TrancePower.POWER_ID).amount : 0;
    }

}
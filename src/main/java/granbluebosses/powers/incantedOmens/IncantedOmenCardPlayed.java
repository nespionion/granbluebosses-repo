package granbluebosses.powers.incantedOmens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import granbluebosses.GranblueBosses;
import granbluebosses.util.CardCheckUtils;
import granbluebosses.util.CustomPowerType;

import static granbluebosses.GranblueBosses.makeID;

public class IncantedOmenCardPlayed extends AbstractIncantedOmen{

    public static final String POWER_ID = makeID("IncantedOmenCardPlayed");
    private static final AbstractPower.PowerType TYPE = CustomPowerType.BOSS_MECHANIC;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    protected final CardRarity cardRarity;
    protected final CardType cardType;
    protected final CardTarget cardTarget;
    protected final AbstractCard.CardTags cardTag;
    protected final int cardCost;


    public IncantedOmenCardPlayed(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.cardRarity = null;
        this.cardType = null;
        this.cardTarget = null;
        this.cardCost = -1;
        this.cardTag = null;
    }

    public IncantedOmenCardPlayed(AbstractCreature owner, int amount, AbstractCard.CardRarity rarity, AbstractCard.CardType cardType, AbstractCard.CardTarget cardTarget, int cardCost) {
        super(POWER_ID, owner, amount);

        if (rarity == AbstractCard.CardRarity.BASIC){
            this.cardRarity = AbstractCard.CardRarity.COMMON;
        } else {
            this.cardRarity = rarity;
        }

        this.cardType = cardType;

        this.cardCost = cardCost;

        if (cardTarget == null){
            this.cardTarget = null;
        } else {
            switch (cardTarget){
                case ALL_ENEMY:
                case ALL:
                    this.cardTarget = CardTarget.ALL_ENEMY;;
                    break;
                case SELF:
                case NONE:
                    this.cardTarget = CardTarget.SELF;
                    break;
                default:
                    this.cardTarget = cardTarget;
                    break;
            }
        }

        this.cardTag = null;

        this.updateDescription();
    }

    @Override
    public String getConditionDescription() {
        if (Settings.language == Settings.GameLanguage.ZHS){
            return AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT ?
                    DESCRIPTIONS[1] + CardCheckUtils.generateConditionStringCN(this.amount, this.cardRarity, this.cardType, this.cardTarget, this.cardCost) + DESCRIPTIONS[2] :
                    DESCRIPTIONS[0];
        } else {
            return AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT ?
                    DESCRIPTIONS[1] + CardCheckUtils.generateConditionString(this.amount, this.cardRarity, this.cardType, this.cardTarget, this.cardCost) + DESCRIPTIONS[2] :
                    DESCRIPTIONS[0];
        }


    }

//    @Override
//    public void onAfterCardPlayed(AbstractCard usedCard) {
//        super.onAfterCardPlayed(usedCard);
//        if (this.amount > 0 && CardCheckUtils.checkCard(usedCard, this.cardRarity, this.cardType, this.cardTarget, this.cardCost)){
//            this.stackPower(-1);
//        }
//    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (this.cardTag != null && this.amount > 0 && CardCheckUtils.checkCard(card, this.cardTag)){
            this.reducePower(1);
        }
        else if (this.cardTag == null && this.amount > 0 && CardCheckUtils.checkCard(card, this.cardRarity, this.cardType, this.cardTarget, this.cardCost)
        ){
            this.reducePower(1);
        }
    }
}

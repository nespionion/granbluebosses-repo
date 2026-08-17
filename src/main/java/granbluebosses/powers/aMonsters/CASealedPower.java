package granbluebosses.powers.aMonsters;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;

import static granbluebosses.GranblueBosses.makeID;

public class CASealedPower extends BasePower {

    public static final String NAME = CASealedPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CASealedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return super.canPlayCard(card) && card.rarity != AbstractCard.CardRarity.RARE;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        this.reducePower(1);
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount <= 0){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}
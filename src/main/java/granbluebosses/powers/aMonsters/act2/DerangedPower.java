package granbluebosses.powers.aMonsters.act2;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;

import static granbluebosses.GranblueBosses.makeID;

public class DerangedPower extends BasePower {

    private static final String NAME = DerangedPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DerangedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, amount);

    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (isPlayer){
            addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    public void onCardDraw(AbstractCard card) {
        if (card.cost >= 0 && this.amount > 0) {
            int newCost = AbstractDungeon.cardRandomRng.random(3);
            if (card.cost != newCost) {
                card.cost = newCost;
                card.costForTurn = card.cost;
                card.isCostModified = true;
            }

            card.freeToPlayOnce = false;
        }

    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = this.amount < 2 ? DESCRIPTIONS[0] : DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

}
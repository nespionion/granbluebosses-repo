package granbluebosses.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import static granbluebosses.GranblueBosses.makeID;

public class PhalanxPower extends BasePower{

    public static final String POWER_ID = makeID("PhalanxPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);
    private static int defaultAmount = 30;

    public PhalanxPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, null, defaultAmount);
        this.description = DESCRIPTIONS[0];
        this.canGoNegative = false;
    }

    public PhalanxPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, null, amount);
        this.description = DESCRIPTIONS[0];
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);

        if (this.amount > 100) {
            this.amount = 100;
        }

        if (this.amount <= 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    @Override
    public float atDamageReceive(float damageAmount, DamageInfo.DamageType damageType) {
        return damageType == DamageInfo.DamageType.NORMAL || damageType == DamageInfo.DamageType.THORNS ? super.atDamageReceive(damageAmount - (damageAmount * this.amount * 0.01f), damageType) : super.atDamageReceive(damageAmount, damageType) ;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.name = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}

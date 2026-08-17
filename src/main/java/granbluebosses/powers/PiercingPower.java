package granbluebosses.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static granbluebosses.GranblueBosses.makeID;

public class PiercingPower extends BasePower {

    public static final String POWER_ID = makeID("PiercingPower");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    private final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public PiercingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, false, owner, amount);
        this.priority = 10;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        this.amount -= 1;
        if (this.amount < 1){
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? super.atDamageGive(damage, DamageInfo.DamageType.HP_LOSS) : super.atDamageGive(damage, type);
    }

    @Override
    public void updateDescription() {
        this.description = this.DESCRIPTIONS[0];
    }
}

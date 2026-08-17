package granbluebosses.powers.aMonsters.act1.grandorder;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import granbluebosses.powers.BasePower;
import granbluebosses.util.CustomPowerType;

import static granbluebosses.GranblueBosses.makeID;

public class WormholePower extends BasePower {

    public static final String POWER_ID = makeID("WormholePower");
    private static final AbstractPower.PowerType TYPE = CustomPowerType.BOSS_MECHANIC;
    private static final boolean TURN_BASED = false;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);

    public WormholePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, null, amount);
    }

    public void lowerAmount(int damageAmount){
        if (this.amount > damageAmount) {
            this.amount -= damageAmount;
        } else {
//            addToBot(new ApplyPowerAction(this.owner, this.owner, new StandbyState(this.owner, this.amount2)));
//            if (this.owner instanceof ProtoBaha){
//                ((ProtoBaha) this.owner).setupStandbyState();
//            }
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.name = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}

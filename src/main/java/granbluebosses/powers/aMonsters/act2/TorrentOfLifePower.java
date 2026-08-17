package granbluebosses.powers.aMonsters.act2;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import granbluebosses.monsters.act2.elites.OdiousMortality;
import granbluebosses.powers.BasePower;
import granbluebosses.utilInterfaces.OnOmenCanceledPower;

import static granbluebosses.GranblueBosses.makeID;

public class TorrentOfLifePower extends BasePower implements OnOmenCanceledPower {

    private static final String NAME = TorrentOfLifePower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TorrentOfLifePower(AbstractCreature owner) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        if (!(this.owner instanceof OdiousMortality)) addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void onRemove() {
        super.onRemove();
        if (this.owner instanceof OdiousMortality && ((OdiousMortality) this.owner).intent != AbstractMonster.Intent.STUN){
            ((OdiousMortality) this.owner).resolveOmen();
        }
    }

    @Override
    public void onOmenCancel() {
        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }
}
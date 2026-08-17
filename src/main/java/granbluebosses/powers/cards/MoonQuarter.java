package granbluebosses.powers.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;

import static granbluebosses.GranblueBosses.makeID;

public class MoonQuarter extends BasePower {

    public static final String NAME = MoonQuarter.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied;

    public MoonQuarter(AbstractCreature owner) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damage) {
        return info.type == DamageInfo.DamageType.NORMAL ? super.onAttackToChangeDamage(info, damage) + 2 : super.onAttackToChangeDamage(info, damage);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        this.justApplied = true;
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if (this.justApplied){
            this.justApplied = false;
        } else {
            addToBot(new ApplyPowerAction(this.owner, this.owner, new MoonFull(this.owner)));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }

}
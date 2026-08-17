package granbluebosses.powers.aMonsters.act2;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;
import granbluebosses.utilInterfaces.OnOmenCanceledPower;

import static granbluebosses.GranblueBosses.makeID;

public class DoomFirePower extends BasePower implements OnOmenCanceledPower {

    public static final String NAME = DoomFirePower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DoomFirePower(AbstractCreature owner, int amount) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, amount);;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        if (this.amount > 0 && !AbstractDungeon.player.drawPile.isEmpty()) addToBot(new ExhaustSpecificCardAction(AbstractDungeon.player.drawPile.getTopCard(), AbstractDungeon.player.drawPile));
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        if (this.amount > 0) this.description = DESCRIPTIONS[0] + 1 + DESCRIPTIONS[1];
        else this.description = DESCRIPTIONS[2] + DESCRIPTIONS[0] + 1 + DESCRIPTIONS[1];
    }

    @Override
    public void onOmenCancel() {
        this.reducePower(1);
    }
}
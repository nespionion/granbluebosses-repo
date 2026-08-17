package granbluebosses.powers.relic;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import granbluebosses.powers.BasePower;

import static granbluebosses.GranblueBosses.makeID;

public class LovesRedemption extends BasePower {
    public static final String POWER_ID = makeID("LovesRedemption");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public static final int EOT_REDUCTION = 2;
    public static final int CARD_PLAYED_REDUCTION = 1;

    public LovesRedemption(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damageType == DamageInfo.DamageType.NORMAL && !AbstractDungeon.player.isBloodied ?
                super.atDamageReceive(damage, damageType) * 0.5f :
                super.atDamageReceive(damage, damageType);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
//        this.addToBot(new ReducePowerAction(this.owner, this.owner, this, EOT_REDUCTION));
        this.reducePower(EOT_REDUCTION);
        if (this.amount <= 0){
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
//        this.addToBot(new ReducePowerAction(this.owner, this.owner, this, CARD_PLAYED_REDUCTION));
        this.reducePower(CARD_PLAYED_REDUCTION);
        if (this.amount <= 0){
            addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}

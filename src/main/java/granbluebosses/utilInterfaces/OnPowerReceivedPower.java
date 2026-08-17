package granbluebosses.utilInterfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public interface OnPowerReceivedPower {

    void onReceivePower (AbstractPower power, AbstractCreature target, AbstractCreature source);
}

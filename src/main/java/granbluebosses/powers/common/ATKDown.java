package granbluebosses.powers.common;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;

import static granbluebosses.GranblueBosses.makeID;

public class ATKDown extends BasePower {

    public static final String NAME = ATKDown.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public boolean justApplied;

    public ATKDown(AbstractCreature owner, int amount) {
        this(owner, amount, true);
    }

    public ATKDown(AbstractCreature owner, int amount, boolean stayForOneMoreTurn) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, amount);
        this.justApplied = stayForOneMoreTurn;
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damage) {
        return info.type == DamageInfo.DamageType.NORMAL ? super.onAttackToChangeDamage(info, damage) - (int)Math.max(1, damage * ((float)(this.amount) / 100.0f)) : super.onAttackToChangeDamage(info, damage);
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}
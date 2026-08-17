package granbluebosses.powers.aMonsters.act2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;

import static granbluebosses.GranblueBosses.makeID;

public class UnrighteousnessPower extends BasePower {

    public static final String NAME = UnrighteousnessPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UnrighteousnessPower(AbstractCreature owner, boolean thisTurn) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, thisTurn ? 1 : 2);
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        this.amount--;

        if (this.amount == 0){
            addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.owner.currentHealth / 2, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }

    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }

}
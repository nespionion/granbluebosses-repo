package granbluebosses.powers.aMonsters;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;

import static granbluebosses.GranblueBosses.makeID;

public class ColossalBodyPower extends BasePower {

    public static final String NAME = ColossalBodyPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ColossalBodyPower(AbstractCreature owner) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, -1);

    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType, AbstractCard card) {
        return card.target == AbstractCard.CardTarget.ALL ?
                super.atDamageReceive(damage, damageType, card) * 2f :
                card.target == AbstractCard.CardTarget.ALL_ENEMY ?
                super.atDamageReceive(damage, damageType, card) * 1.5f :
                super.atDamageReceive(damage, damageType, card);
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }

}
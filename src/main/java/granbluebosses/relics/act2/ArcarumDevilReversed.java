package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumDevilReversed extends BaseRelic {
    public static final String NAME = "ArcarumDevilReversed"; //The name will be used for determining the image file as well as the ID.
    public static final String RELIC_ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public ArcarumDevilReversed() {
        super(RELIC_ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        return info.owner.hasPower(VulnerablePower.POWER_ID) ? super.onAttackedToChangeDamage(info, damageAmount) - 1 : super.onAttackedToChangeDamage(info, damageAmount);
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        return info.owner.hasPower(WeakPower.POWER_ID) ? super.onAttackToChangeDamage(info, damageAmount) + 1 : super.onAttackToChangeDamage(info, damageAmount);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ArcarumDevilReversed();
    }
}

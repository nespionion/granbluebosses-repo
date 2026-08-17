package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class PiercingGalewing extends BaseRelic {
    public static final String RELIC_ID = GranblueBosses.makeID("PiercingGalewing");
    public static final int ADD_DAMAGE = 2;

    public PiercingGalewing() {
        super(
                RELIC_ID,       // ID
                "PiercingGalewing",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        return info.type == DamageInfo.DamageType.THORNS && info.owner.isPlayer ?
                super.onAttackToChangeDamage(info, damageAmount) + ADD_DAMAGE :
                super.onAttackToChangeDamage(info, damageAmount);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + ADD_DAMAGE + DESCRIPTIONS[1]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PiercingGalewing();
    }
}

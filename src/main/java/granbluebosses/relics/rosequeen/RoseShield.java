package granbluebosses.relics.rosequeen;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class RoseShield extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("RoseShield");

    public RoseShield() {
        super(
                RELIC_ID,       // ID
                "RoseShield",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX

    }

    public int onLoseHpLast(int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            return damageAmount - 1;
        } else {
            return damageAmount;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RoseShield();
    }
}

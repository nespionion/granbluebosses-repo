package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumStarsReversed extends BaseRelic {
    public static final String NAME = "ArcarumStarsReversed"; //The name will be used for determining the image file as well as the ID.
    public static final String RELIC_ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public static final int MAX_DMG_TAKEN = 100;

    public ArcarumStarsReversed() {
        super(RELIC_ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    public int onLoseHpLast(int damageAmount) {
        if (damageAmount > MAX_DMG_TAKEN) {
            this.flash();
            return MAX_DMG_TAKEN;
        } else {
            return damageAmount;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MAX_DMG_TAKEN + DESCRIPTIONS[1]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ArcarumStarsReversed();
    }
}

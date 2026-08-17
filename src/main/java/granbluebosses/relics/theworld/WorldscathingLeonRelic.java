package granbluebosses.relics.theworld;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class WorldscathingLeonRelic extends BaseRelic {
    public static final String NAME = "WorldscathingLeonRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String RELIC_ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public WorldscathingLeonRelic() {
        super(RELIC_ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        return c.type == AbstractCard.CardType.ATTACK ?
                super.atDamageModify(damage, c) * 1.15f :
                super.atDamageModify(damage, c);
    }

    @Override
    public int onPlayerGainBlock(int blockAmount) {
        return super.onPlayerGainBlock(blockAmount) + Math.max((blockAmount / 10), 1);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new WorldscathingLeonRelic();
    }
}

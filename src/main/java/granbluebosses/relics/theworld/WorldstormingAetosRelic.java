package granbluebosses.relics.theworld;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class WorldstormingAetosRelic extends BaseRelic {
    public static final String NAME = "WorldstormingAetosRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String RELIC_ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public WorldstormingAetosRelic() {
        super(RELIC_ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        return info.owner != null && info.owner.isPlayer ? damageAmount + 1 : damageAmount;
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
        return new WorldstormingAetosRelic();
    }
}

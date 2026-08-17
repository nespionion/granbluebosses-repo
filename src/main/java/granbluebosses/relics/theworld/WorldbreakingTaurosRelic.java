package granbluebosses.relics.theworld;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.action.WorldbreakingTaurosAction;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class WorldbreakingTaurosRelic extends BaseRelic {
    public static final String NAME = "WorldbreakingTaurosRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String RELIC_ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public WorldbreakingTaurosRelic() {
        super(RELIC_ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        addToBot(new WorldbreakingTaurosAction());
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
        return new WorldbreakingTaurosRelic();
    }
}

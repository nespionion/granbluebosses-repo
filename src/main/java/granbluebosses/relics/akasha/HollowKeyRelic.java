package granbluebosses.relics.akasha;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class HollowKeyRelic extends BaseRelic {
    public static final String NAME = HollowKeyRelic.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String RELIC_ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private boolean isActive;

    public HollowKeyRelic() {
        super(RELIC_ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.isActive = true;
        this.grayscale = false;
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        if (this.isActive && AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty()){
            this.isActive = false;
            this.grayscale = true;
            addToTop(new SkipEnemiesTurnAction());
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HollowKeyRelic();
    }
}

package granbluebosses.relics.events;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class TiensPortrait extends BaseRelic {
    private static final String NAME = TiensPortrait.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public TiensPortrait() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        super.onMonsterDeath(m);
        addToBot(new GainGoldAction((int)(AbstractDungeon.player.gold * 0.1f)));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
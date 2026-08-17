package granbluebosses.relics.events;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class CrystalRose extends BaseRelic {
    private static final String NAME = CrystalRose.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public CrystalRose() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (c.color == PrimalColor.GBF_PRIMAL_COLOR) addToBot(new GainBlockAction(AbstractDungeon.player, 4));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
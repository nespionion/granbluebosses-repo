package granbluebosses.relics.odious;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import granbluebosses.campfire.options.CampfireRelicExorcismOption;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.CampfireUtils;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.ArrayList;

import static granbluebosses.GranblueBosses.makeID;

public class OdiousTerrorbow extends BaseRelic {
    private static final String NAME = OdiousTerrorbow.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private static final int BLOCK_GAIN = 4;

    public OdiousTerrorbow() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.counter = 1;
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 1;
    }

    @Override
    public int onPlayerGainBlock(int blockAmount) {
        return super.onPlayerGainBlock(blockAmount) +
                (AbstractDungeon.player.isBloodied ?
                        BLOCK_GAIN * 2 :
                        BLOCK_GAIN
                        );
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);
        if (this.counter > 0 && drawnCard.type == AbstractCard.CardType.POWER) drawnCard.setCostForTurn(drawnCard.costForTurn + 1);
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        super.addCampfireOption(options);
        if (options.stream().noneMatch(c -> c instanceof CampfireRelicExorcismOption)) options.add(new CampfireRelicExorcismOption(CampfireUtils.isExorcismPossible()));

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK_GAIN + DESCRIPTIONS[1] +
                (this.counter > 0 ?
                        DESCRIPTIONS[2] + this.counter + DESCRIPTIONS[3] :
                        ""
                );
    }

}
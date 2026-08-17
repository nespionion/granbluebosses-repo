package granbluebosses.relics.odious;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import granbluebosses.campfire.options.CampfireRelicExorcismOption;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.CampfireUtils;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.ArrayList;

import static granbluebosses.GranblueBosses.makeID;

public class OdiousDemonedge extends BaseRelic {
    private static final String NAME = OdiousDemonedge.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private static final int CARD_DRAW = 2;

    public OdiousDemonedge() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.counter = 1;
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        addToTop(new DrawCardAction(CARD_DRAW));
        addToTop(new LoseEnergyAction(this.counter));
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 1;
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        super.addCampfireOption(options);
        if (options.stream().noneMatch(c -> c instanceof CampfireRelicExorcismOption)) options.add(new CampfireRelicExorcismOption(CampfireUtils.isExorcismPossible()));

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARD_DRAW + DESCRIPTIONS[1] +
                (this.counter > 0 ?
                        DESCRIPTIONS[2] + this.counter + DESCRIPTIONS[3] :
                        ""
                );
    }

}
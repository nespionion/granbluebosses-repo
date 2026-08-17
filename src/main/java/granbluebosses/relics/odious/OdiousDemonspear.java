package granbluebosses.relics.odious;

import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import granbluebosses.campfire.options.CampfireRelicExorcismOption;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.CampfireUtils;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.ArrayList;

import static granbluebosses.GranblueBosses.makeID;

public class OdiousDemonspear extends BaseRelic {
    private static final String NAME = OdiousDemonspear.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private static final float DMG_AMP = 0.1f;

    public OdiousDemonspear() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.counter = 1;
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 1;
    }

    public int onLoseHpLast(int damageAmount) {
        if (this.counter > 0 && damageAmount > 0) {
            this.flash();
            return damageAmount + this.counter;
        } else {
            return damageAmount;
        }
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        super.addCampfireOption(options);
        if (options.stream().noneMatch(c -> c instanceof CampfireRelicExorcismOption)) options.add(new CampfireRelicExorcismOption(CampfireUtils.isExorcismPossible()));

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + Math.round(DMG_AMP * 100) + DESCRIPTIONS[1] +
                (this.counter > 0 ?
                        DESCRIPTIONS[2] + this.counter + DESCRIPTIONS[3] :
                        ""
                        );
    }

}
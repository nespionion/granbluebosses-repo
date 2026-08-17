package granbluebosses.relics.akasha;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.relics.BaseRelic;
import granbluebosses.utilInterfaces.HasRelicCounterpart;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class EnigmaticArmillaRelic extends BaseRelic {
    public static final String NAME = "EnigmaticArmillaRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String RELIC_ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public EnigmaticArmillaRelic() {
        super(RELIC_ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        for (AbstractRelic r : AbstractDungeon.player.relics){
            if (r instanceof HasRelicCounterpart){
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2, (float) Settings.HEIGHT / 2, ((HasRelicCounterpart)r).getCounterpartRelic());
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnigmaticArmillaRelic();
    }
}

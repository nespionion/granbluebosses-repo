package granbluebosses.relics.ancients;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class ProvidenceGlobe extends BaseRelic{
    public static final String NAME = ProvidenceGlobe.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    public static AbstractRelic rewardSwap = null;

    public ProvidenceGlobe() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public void onPreviewObtainCard(AbstractCard c) {
        if (c.hasTag(CustomTags.SUMMON_CALL) && !c.upgraded){
            c.upgrade();
        }
        super.onPreviewObtainCard(c);
    }


    public void onObtainCard(AbstractCard c) {
        if (c.hasTag(CustomTags.SUMMON_CALL) && !c.upgraded) {
            c.upgrade();
        }
        super.onObtainCard(c);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


//    @Override
//    public void onChestOpen(boolean bossChest) {
//        super.onChestOpen(bossChest);
//        if (bossChest && rewardSwap != null){
//            ((BossChest) ((TreasureRoomBoss) AbstractDungeon.getCurrRoom()).chest).relics.set(0, rewardSwap);
//        }
//    }

}
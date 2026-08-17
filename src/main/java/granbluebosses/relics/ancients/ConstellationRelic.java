package granbluebosses.relics.ancients;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class ConstellationRelic extends BaseRelic {
    public static final String NAME = ConstellationRelic.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    public static AbstractRelic rewardSwap = null;

    public ConstellationRelic() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 0;
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        super.onObtainCard(c);
        if (c.hasTag(CustomTags.SUMMON_CALL)){
            this.counter = 1;
        }
    }

//    @Override
//    public void onChestOpen(boolean bossChest) {
//        super.onChestOpen(bossChest);
//        if (bossChest && rewardSwap != null){
//            ((BossChest) ((TreasureRoomBoss) AbstractDungeon.getCurrRoom()).chest).relics.set(0, rewardSwap);
//        }
//    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        addToBot(new GainEnergyAction(this.counter));
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


}
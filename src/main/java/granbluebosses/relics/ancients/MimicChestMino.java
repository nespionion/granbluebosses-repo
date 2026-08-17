package granbluebosses.relics.ancients;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class MimicChestMino extends BaseRelic {
    public static final String NAME = MimicChestMino.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.HEAVY; //The sound played when the relic is clicked.
    private static final int goldReward = 50;

    public MimicChestMino() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        super.onVictory();
        AbstractDungeon.player.energy.energyMaster -= this.counter;
        this.counter = 0;
        AbstractDungeon.getCurrRoom().addGoldToRewards(goldReward);
        AbstractDungeon.getCurrRoom().addCardReward(new RewardItem(AbstractDungeon.player.getCardColor()));
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        super.onObtainCard(c);
        this.counter++;
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= this.counter;
        this.counter = 0;
        super.onUnequip();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + goldReward + DESCRIPTIONS[1];
    }

}
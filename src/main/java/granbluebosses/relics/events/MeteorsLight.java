package granbluebosses.relics.events;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.ArrayList;
import java.util.Collections;

import static granbluebosses.GranblueBosses.makeID;

public class MeteorsLight extends BaseRelic {
    private static final String NAME = MeteorsLight.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public MeteorsLight() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        ArrayList<AbstractRelic> availableRelic = new ArrayList<>();
        Collections.addAll(availableRelic, new BurningBlood(), new CrackedCore(), new SnakeRing(), new PureWater());

        if (availableRelic.stream().allMatch(relic -> AbstractDungeon.player.hasRelic(relic.relicId))) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new Circlet());
            return;
        }

        ArrayList<AbstractRelic> relicsToGive = new ArrayList<>();

        if (!AbstractDungeon.player.hasRelic(BurningBlood.ID)) relicsToGive.add(availableRelic.get(0));
        if (!AbstractDungeon.player.hasRelic(CrackedCore.ID)) relicsToGive.add(availableRelic.get(1));
        if (!AbstractDungeon.player.hasRelic(SnakeRing.ID)) relicsToGive.add(availableRelic.get(2));
        if (!AbstractDungeon.player.hasRelic(PureWater.ID)) relicsToGive.add(availableRelic.get(3));

        AbstractRelic r = relicsToGive.get(AbstractDungeon.relicRng.random(relicsToGive.size()-1));

        AbstractDungeon.combatRewardScreen.open();
        AbstractDungeon.combatRewardScreen.clear();
        AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(r));

        AbstractDungeon.combatRewardScreen.positionRewards();

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
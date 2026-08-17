package granbluebosses.events.conditions;

import basemod.eventUtil.util.Condition;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import granbluebosses.relics.ancients.ConstellationRelic;
import granbluebosses.relics.ancients.DamascusIngot;
import granbluebosses.relics.ancients.LegendaryMerit;
import granbluebosses.relics.ancients.ShieldOfTenets;

public class SidedWithSkiesCondition implements Condition {
    @Override
    public boolean test() {
        return AbstractDungeon.player != null
                && (AbstractDungeon.player.hasRelic(ShieldOfTenets.RELIC_ID)
                || AbstractDungeon.player.hasRelic(LegendaryMerit.ID)
                || AbstractDungeon.player.hasRelic(DamascusIngot.ID))
                ;
    }
}

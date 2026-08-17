package granbluebosses.events.conditions;

import basemod.eventUtil.util.Condition;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import granbluebosses.relics.ancients.*;

public class SidedWithStarsCondition implements Condition {
    @Override
    public boolean test() {
        return AbstractDungeon.player != null
                && (AbstractDungeon.player.hasRelic(ConstellationRelic.ID)
                || AbstractDungeon.player.hasRelic(BlueCrystal.RELIC_ID)
                || AbstractDungeon.player.hasRelic(ProvidenceGlobe.ID))
                ;
    }
}

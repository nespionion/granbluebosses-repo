package granbluebosses.events.conditions;

import basemod.eventUtil.util.Condition;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import granbluebosses.relics.ancients.BlueCrystal;
import granbluebosses.relics.ancients.ConstellationRelic;
import granbluebosses.relics.ancients.ProvidenceGlobe;

public class TwinElementsCondition implements Condition {
    @Override
    public boolean test() {
        return AbstractDungeon.player != null
                && (AbstractDungeon.player.hasRelic(ConstellationRelic.ID)
                || AbstractDungeon.player.hasRelic(BlueCrystal.RELIC_ID)
                || AbstractDungeon.player.hasRelic(ProvidenceGlobe.ID))
                && (AbstractDungeon.player.getCardColor() == AbstractCard.CardColor.RED
                || AbstractDungeon.player.getCardColor() == AbstractCard.CardColor.BLUE)
                ;
    }
}

package granbluebosses.events.conditions;

import basemod.eventUtil.util.Condition;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import granbluebosses.cards.rewards.Magna1.*;
import granbluebosses.relics.ancients.BlueCrystal;
import granbluebosses.relics.ancients.ConstellationRelic;
import granbluebosses.relics.ancients.ProvidenceGlobe;

public class SidedMagna3Condition implements Condition {
    @Override
    public boolean test() {
        return AbstractDungeon.player != null
                && (AbstractDungeon.player.hasRelic(ConstellationRelic.ID)
                || AbstractDungeon.player.hasRelic(BlueCrystal.RELIC_ID)
                || AbstractDungeon.player.hasRelic(ProvidenceGlobe.ID))
                && (AbstractDungeon.player.masterDeck.findCardById(TiamatOmega.CARD_ID) != null
                || AbstractDungeon.player.masterDeck.findCardById(ColossusOmega.CARD_ID) != null
                || AbstractDungeon.player.masterDeck.findCardById(LeviathanOmega.CARD_ID) != null
                || AbstractDungeon.player.masterDeck.findCardById(YggdrasilOmega.CARD_ID) != null
                || AbstractDungeon.player.masterDeck.findCardById(LuminieraOmega.CARD_ID) != null
                || AbstractDungeon.player.masterDeck.findCardById(CelesteOmega.CARD_ID) != null
                );
    }
}

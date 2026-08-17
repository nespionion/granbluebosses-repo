package granbluebosses.relics.protobaha;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class HarpOfBahamutRelic extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("HarpOfBahamut");

    public HarpOfBahamutRelic() {
        super(
                RELIC_ID,       // ID
                "HarpOfBahamut",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX

    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.flash();
        this.addToTop(new MakeTempCardInHandAction(new Miracle()));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HarpOfBahamutRelic();
    }
}
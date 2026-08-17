package granbluebosses.relics.act1;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class SwordOfPallas extends BaseRelic  {
    public static final String RELIC_ID = GranblueBosses.makeID("SwordOfPallas");

    public SwordOfPallas() {
        super(
                RELIC_ID,       // ID
                "SwordOfPallas",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX

    }

    public int onLoseHpLast(int damageAmount) {
        if (damageAmount * 2 > AbstractDungeon.player.currentHealth) {
            this.flash();
            return damageAmount - 2;
        } else {
            return damageAmount;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SwordOfPallas();
    }

}

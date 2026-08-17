package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class FirestormScythe extends BaseRelic {
    public static final String RELIC_ID = GranblueBosses.makeID("FirestormScythe");
    public static final int ADD_DAMAGE = 8;

    public FirestormScythe() {
        super(
                RELIC_ID,       // ID
                "FirestormScythe",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);

    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        return c.rarity == AbstractCard.CardRarity.RARE && c.type == AbstractCard.CardType.ATTACK ?
                super.atDamageModify(damage, c) + ADD_DAMAGE :
                super.atDamageModify(damage, c);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + ADD_DAMAGE + DESCRIPTIONS[1]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FirestormScythe();
    }
}

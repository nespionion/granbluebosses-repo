package granbluebosses.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;

public class SwordOfPallas extends BaseRelic{
    public static final String RELIC_ID = GranblueBosses.makeID("SwordOfPallas");

    public SwordOfPallas() {
        super(
                RELIC_ID,       // ID
                "SwordOfPallas",
                AbstractCard.CardColor.COLORLESS,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX
        this.relicType = RelicType.SHARED;
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

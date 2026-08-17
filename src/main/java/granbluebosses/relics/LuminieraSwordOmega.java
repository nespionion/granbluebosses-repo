package granbluebosses.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;

public class LuminieraSwordOmega extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("LuminieraSwordOmega");

    private static int magicNumber = 2;

    public LuminieraSwordOmega() {
        super(
                RELIC_ID,       // ID
                "LuminieraSwordOmega",
                AbstractCard.CardColor.COLORLESS,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX
        this.relicType = RelicType.SHARED;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, magicNumber), magicNumber));
    }

    @Override
    public void onEquip() {
        super.onEquip();
        AbstractDungeon.player.increaseMaxHp(2, false);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LuminieraSwordOmega();
    }
}

package granbluebosses.relics.protobaha;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class DaggerOfBahamutCoda extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("DaggerOfBahamutCoda");

    public DaggerOfBahamutCoda() {
        super(
                RELIC_ID,                                                     // ID
                "DaggerOfBahamutCoda",
                PrimalColor.GBF_PRIMAL_COLOR,
                AbstractRelic.RelicTier.SPECIAL,                              // Rarity
                AbstractRelic.LandingSound.HEAVY);                            // SFX

    }

    @Override
    public void onEquip() {
        super.onEquip();
        AbstractDungeon.player.increaseMaxHp(4, false);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 2), 2));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DaggerOfBahamutCoda();
    }
}

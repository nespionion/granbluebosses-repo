package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class CalamitousAquashade extends BaseRelic {
    public static final String RELIC_ID = GranblueBosses.makeID("CalamitousAquashade");
    public static final int HP_THRESHOLD = 65;

    public CalamitousAquashade() {
        super(
                RELIC_ID,       // ID
                "CalamitousAquashade",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractDungeon.player.maxHealth >= HP_THRESHOLD){
            this.flash();
            int statIncrease = AbstractDungeon.player.maxHealth / HP_THRESHOLD;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, statIncrease), statIncrease));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, statIncrease), statIncrease));
            addToBot(new GainEnergyAction(statIncrease));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + HP_THRESHOLD + DESCRIPTIONS[1]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CalamitousAquashade();
    }
}

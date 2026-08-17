package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class ArcarumJudgementReversed extends BaseRelic {
    public static final String RELIC_ID = GranblueBosses.makeID("ArcarumJudgementReversed");
    public static final int DRAW_CARDS = 1;
    public static final int STR_GAIN = 1;

    public ArcarumJudgementReversed() {
        super(
                RELIC_ID,       // ID
                "ArcarumJudgementReversed",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (checkForDebuff()){
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STR_GAIN), STR_GAIN));
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, STR_GAIN), STR_GAIN));
        } else {
            addToTop(new DrawCardAction(DRAW_CARDS));
        }
    }

    public boolean checkForDebuff(){
        for (AbstractPower pow : AbstractDungeon.player.powers){
            if (pow.type == AbstractPower.PowerType.DEBUFF) return true;
        }

        return false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ArcarumJudgementReversed();
    }
}

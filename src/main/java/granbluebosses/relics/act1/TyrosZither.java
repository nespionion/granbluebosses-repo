package granbluebosses.relics.act1;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class TyrosZither extends BaseRelic  {

    public static final String RELIC_ID = GranblueBosses.makeID("TyrosZither");

    private static boolean isActive = true;
    private static int magicNumber = 1;

    public TyrosZither() {
        super(
                RELIC_ID,       // ID
                "TyrosZither",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX

    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        isActive = true;
        this.grayscale = false;
    }

    @Override
    public void onVictory() {
        super.onVictory();
        isActive = true;
        this.grayscale = false;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (isActive && c.rarity == AbstractCard.CardRarity.RARE){
            isActive = false;
            this.grayscale = true;
            addToBot(new DrawCardAction(magicNumber));
        }
        super.onPlayCard(c, m);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TyrosZither();
    }

}

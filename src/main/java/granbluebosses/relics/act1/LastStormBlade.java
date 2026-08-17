package granbluebosses.relics.act1;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class LastStormBlade extends BaseRelic  {

    public static final String RELIC_ID = GranblueBosses.makeID("LastStormBlade");

    private static int magicNumber = 3;
    private static boolean isActive = false;

    public LastStormBlade() {
        super(
                RELIC_ID,       // ID
                "LastStormBlade",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX

        this.counter = magicNumber;
        this.grayscale = false;

    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = magicNumber;
        this.grayscale = false;
        isActive = true;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (this.counter > 0 && c.type == AbstractCard.CardType.ATTACK){
            this.counter -= 1;
        }
        if (this.counter == 0 && isActive){
            addToBot(new GainEnergyAction(1));
            this.grayscale = true;
            isActive = false;
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.counter = magicNumber;
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LastStormBlade();
    }

}

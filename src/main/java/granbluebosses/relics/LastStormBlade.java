package granbluebosses.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;

public class LastStormBlade extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("LastStormBlade");

    private static int magicNumber = 3;

    public LastStormBlade() {
        super(
                RELIC_ID,       // ID
                "LastStormBlade",
                AbstractCard.CardColor.COLORLESS,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX
        this.relicType = RelicType.SHARED;
        this.counter = magicNumber;
        this.grayscale = false;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = magicNumber;
        this.grayscale = false;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (this.counter > 0 && c.type == AbstractCard.CardType.ATTACK){
            this.counter -= 1;
        }
        if (this.counter == 0){
            addToBot(new GainEnergyAction(1));
            this.grayscale = true;
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

package granbluebosses.relics.act1;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class PlumeOfSuparna extends BaseRelic  {
    public static final String RELIC_ID = GranblueBosses.makeID("PlumeOfSuparna");

    private static int magicNumber = 3;
    private static boolean isActive = true;

    public PlumeOfSuparna() {
        super(
                RELIC_ID,       // ID
                "PlumeOfSuparna",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX

    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        isActive = true;
        magicNumber = 3;
        this.grayscale = false;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (isActive && c.type == AbstractCard.CardType.POWER){
            magicNumber -= 1;
        }
        if (isActive && magicNumber == 0){
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 1));
            isActive = false;
            this.grayscale = true;
            this.flash();
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        isActive = true;
        magicNumber = 3;
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PlumeOfSuparna();
    }

}

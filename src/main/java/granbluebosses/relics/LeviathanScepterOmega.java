package granbluebosses.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;

public class LeviathanScepterOmega extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("LeviathanScepterOmega");

    private static int magicNumber = 2;

    private static boolean isActive = true;

    public LeviathanScepterOmega() {
        super(
                RELIC_ID,       // ID
                "LeviathanScepterOmega",
                AbstractCard.CardColor.COLORLESS,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX
        this.relicType = RelicType.SHARED;
    }

    @Override
    public void atBattleStart() {
        isActive = true;
        this.grayscale = false;
        super.atBattleStart();
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        if (isActive){
            isActive = false;
            this.grayscale = true;
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
        }
        return super.onPlayerHeal(healAmount);
    }

    @Override
    public void onVictory() {
        super.onVictory();
        isActive = true;
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LeviathanScepterOmega();
    }
}

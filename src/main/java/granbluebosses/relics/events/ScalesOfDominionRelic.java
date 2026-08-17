package granbluebosses.relics.events;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.HashSet;

import static granbluebosses.GranblueBosses.makeID;

public class ScalesOfDominionRelic extends BaseRelic {
    private static final String NAME = ScalesOfDominionRelic.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    public static final int STR_AMT = 1;
    private HashSet<String> cardsFound;

    public ScalesOfDominionRelic() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.cardsFound = new HashSet<>();
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.cardsFound.clear();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (!c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) && !c.hasTag(AbstractCard.CardTags.STARTER_DEFEND) && c.rarity != AbstractCard.CardRarity.BASIC && cardsFound.contains(c.cardID)){
                return;
            }
            cardsFound.add(c.cardID);
        }
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STR_AMT), STR_AMT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STR_AMT + DESCRIPTIONS[1];
    }

}
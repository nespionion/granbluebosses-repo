package granbluebosses.relics.act2;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.HashSet;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumHangedManReversed extends BaseRelic implements OnRemoveCardFromMasterDeckRelic, ClickableRelic {
    public static final String NAME = "ArcarumHangedManReversed"; //The name will be used for determining the image file as well as the ID.
    public static final String RELIC_ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public static final int COPY_THRESHOLD = 3;
    public static HashSet<String> cardsFound = null;
    public boolean isActive = false;

    public ArcarumHangedManReversed() {
        super(RELIC_ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        if (this.isActive) {
            this.flash();
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
        }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        super.onObtainCard(c);
        this.isActive = refreshDeckAmounts();
        this.grayscale = !this.isActive;
    }

    @Override
    public void onRemoveCardFromMasterDeck(AbstractCard abstractCard) {
        this.isActive = refreshDeckAmounts();
        this.grayscale = !this.isActive;
    }


    private boolean refreshDeckAmounts(){
        if (cardsFound == null){
            cardsFound = new HashSet<String>();
        } else {
            cardsFound.clear();
        }
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (c.rarity != AbstractCard.CardRarity.BASIC &&
                    !c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) &&
                    !c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)){ //If the card is not a starter card
                if (cardsFound.contains(c.name)){ // If the card was already found
                    return false;
                } else {                        // If the card has yet to be found
                    cardsFound.add(c.name);     // add it so that it can be found later
                }
            }
        }

        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ArcarumHangedManReversed();
    }

    @Override
    public void onRightClick() {
        this.isActive = refreshDeckAmounts();
        this.grayscale = !this.isActive;
    }
}

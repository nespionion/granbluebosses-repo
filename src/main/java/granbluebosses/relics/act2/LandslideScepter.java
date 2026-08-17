package granbluebosses.relics.act2;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.HashMap;

public class LandslideScepter extends BaseRelic implements OnRemoveCardFromMasterDeckRelic, ClickableRelic {
    public static final String RELIC_ID = GranblueBosses.makeID("LandslideScepter");
    public static final int COPY_THRESHOLD = 3;
    public static HashMap<String, Integer> cardAmounts = null;
    public boolean isActive = false;

    public LandslideScepter() {
        super(
                RELIC_ID,       // ID
                "LandslideScepter",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);
        this.isActive = false;
        this.grayscale = !this.isActive;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        if (this.isActive) {
            this.flash();
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
            addToBot(new GainEnergyAction(1));
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
        if (cardAmounts == null){
            cardAmounts = new HashMap<String, Integer>();
        } else {
            cardAmounts.clear();
        }
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (cardAmounts.containsKey(c.name)){
                cardAmounts.put(c.name, cardAmounts.get(c.name) + 1);
            } else {
                cardAmounts.put(c.name, 1);
            }
        }

        for (int i : cardAmounts.values()){
            if (i >= 3) return true;
        }

        return false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + COPY_THRESHOLD + DESCRIPTIONS[1]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LandslideScepter();
    }

    @Override
    public void onRightClick() {
        this.isActive = refreshDeckAmounts();
        this.grayscale = !this.isActive;
    }
}

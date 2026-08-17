package granbluebosses.relics.events;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class PlusMarkBlue extends BaseRelic {
    public static final String RELIC_ID = GranblueBosses.makeID("PlusMarkBlue");
    private boolean isActive;

    public PlusMarkBlue(int indexInRelicList) {
        super(
                RELIC_ID,       // ID
                "PlusMarkBlue",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.MAGICAL);                            // SFX
        this.isActive = true;
        this.setCounter(indexInRelicList);
    }

    public PlusMarkBlue() {
        this(0);
    }

    @Override
    public void onChestOpen(boolean bossChest) {
        super.onChestOpen(bossChest);
        if (bossChest){
            this.isActive = false;
        }
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.isActive = true;
    }

    public boolean isRelicActive(){
        return this.isActive;
    }

    public void disableRelic(){
        this.isActive = false;
    }

    public void reactivateRelic(){
        this.isActive = true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PlusMarkBlue(this.counter);
    }

    @Override
    public void renderCounter(SpriteBatch sb, boolean inTopPanel) {
        return;
    }
}

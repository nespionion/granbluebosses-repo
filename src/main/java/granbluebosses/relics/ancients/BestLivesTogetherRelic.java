package granbluebosses.relics.ancients;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import granbluebosses.action.CleanseDebuffAction;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class BestLivesTogetherRelic extends BaseRelic {
    public static final String NAME = BestLivesTogetherRelic.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private boolean isActive;

    public BestLivesTogetherRelic() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.isActive = true;
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.isActive = true;
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (this.isActive && (AbstractDungeon.player.currentHealth - damageAmount) <= 1){
            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);

            AbstractDungeon.player.energy.energyMaster++;

            addToBot(new CleanseDebuffAction(AbstractDungeon.player, AbstractDungeon.player, true));

            this.flash();
            this.isActive = false;
            this.grayscale = true;
        }
        return super.onLoseHpLast(damageAmount);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
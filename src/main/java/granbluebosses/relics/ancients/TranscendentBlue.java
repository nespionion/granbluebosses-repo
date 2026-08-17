package granbluebosses.relics.ancients;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class TranscendentBlue extends BaseRelic {
    public static final String NAME = TranscendentBlue.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    public boolean isActive = true;

    public TranscendentBlue() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.isActive = true;
    }

//    @Override
//    public void onPlayerEndTurn() {
//        super.onPlayerEndTurn();
//        if (AbstractDungeon.player.energy.energy > 0){
//            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, 2), 2));
//        }
//    }

//    @Override
//    public void atBattleStartPreDraw() {
//        super.atBattleStartPreDraw();
//        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawReductionPower(AbstractDungeon.player, 99), 99));
//    }


    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (this.isActive){
            this.flash();
            addToTop(new GainEnergyAction(2));
            addToTop(new DrawCardAction(2));
            this.isActive = false;
            this.grayscale = true;
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.isActive = true;
        this.grayscale = false;
    }

    public void onEquip() {
        super.onEquip();
        this.isActive = true;
//        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        super.onEquip();
//        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
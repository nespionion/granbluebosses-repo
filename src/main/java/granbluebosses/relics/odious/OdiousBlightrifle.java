package granbluebosses.relics.odious;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import granbluebosses.campfire.options.CampfireRelicExorcismOption;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.CampfireUtils;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.ArrayList;

import static granbluebosses.GranblueBosses.makeID;

public class OdiousBlightrifle extends BaseRelic {
    private static final String NAME = OdiousBlightrifle.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private static final int POWER_AMT = 2;

    public OdiousBlightrifle() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.counter = 1;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, POWER_AMT), POWER_AMT));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, POWER_AMT), POWER_AMT));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, (this.counter * -3) + 2), (this.counter * -3) + 2));
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 1;
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        super.addCampfireOption(options);
        if (options.stream().noneMatch(c -> c instanceof CampfireRelicExorcismOption)) options.add(new CampfireRelicExorcismOption(CampfireUtils.isExorcismPossible()));

    }

    @Override
    public String getUpdatedDescription() {
        return this.counter > 0 ?
                DESCRIPTIONS[0] + POWER_AMT + DESCRIPTIONS[1] + this.counter + DESCRIPTIONS[2] :
                DESCRIPTIONS[0] + POWER_AMT + DESCRIPTIONS[3]
                ;
    }

}
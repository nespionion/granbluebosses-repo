package granbluebosses.relics.odious;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import granbluebosses.campfire.options.CampfireRelicExorcismOption;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.CampfireUtils;
import granbluebosses.util.primalcards.PrimalColor;

import java.util.ArrayList;

import static granbluebosses.GranblueBosses.makeID;

public class OdiousCodex extends BaseRelic {
    private static final String NAME = OdiousCodex.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    public static final float NERFING_MULT = 0.5f;

    public OdiousCodex() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.counter = 1;
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 1;
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        float percentIncrease = Math.abs(((AbstractDungeon.player.currentHealth - 1.0f) / AbstractDungeon.player.maxHealth) - this.counter) * NERFING_MULT;
        return (info.type == DamageInfo.DamageType.NORMAL) ?
                (int) Math.ceil(super.onAttackToChangeDamage(info, damageAmount) + Math.max(1, damageAmount * percentIncrease)):
                super.onAttackToChangeDamage(info, damageAmount)
                ;
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        super.addCampfireOption(options);
        if (options.stream().noneMatch(c -> c instanceof CampfireRelicExorcismOption)) options.add(new CampfireRelicExorcismOption(CampfireUtils.isExorcismPossible()));

    }

    @Override
    public String getUpdatedDescription() {
        return this.counter > 0 ?
                DESCRIPTIONS[0] + (int)(100 * NERFING_MULT) + DESCRIPTIONS[1] :
                DESCRIPTIONS[2] + (int)(100 * NERFING_MULT) + DESCRIPTIONS[3]
                ;
    }

}
package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.cards.DamageInfo;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;
import static granbluebosses.GranblueBosses.makeID;

public class ArcarumMoonReversed extends BaseRelic {
    public static final String NAME = ArcarumMoonReversed.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private static final float DAMAGE_MOD = 0.03f;

    public ArcarumMoonReversed() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.counter++;
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        return super.onAttackToChangeDamage(info, damageAmount) + (int)(this.counter * DAMAGE_MOD);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + Math.round(DAMAGE_MOD * 100) + DESCRIPTIONS[1];
    }

}
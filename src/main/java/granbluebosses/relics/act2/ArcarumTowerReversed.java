package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class ArcarumTowerReversed extends BaseRelic {
    public static final String NAME = "ArcarumTowerReversed"; //The name will be used for determining the image file as well as the ID.
    public static final String RELIC_ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public static final int DAMAGE_DEALT = 10;

    public ArcarumTowerReversed() {
        super(RELIC_ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        this.counter--;
        if (this.counter <= 0){
            this.counter = 3;
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DAMAGE_DEALT, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.counter = 3;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DAMAGE_DEALT + DESCRIPTIONS[1]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ArcarumTowerReversed();
    }
}

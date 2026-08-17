package granbluebosses.relics.ancients;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.powers.relic.BlueCrystalPower;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class BlueCrystal extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("BlueCrystal");
    public static AbstractRelic rewardSwap = null;

    public BlueCrystal() {
        super(
                RELIC_ID,       // ID
                "BlueCrystal",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.STARTER,                              // Rarity
                LandingSound.MAGICAL);                            // SFX


    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BlueCrystalPower(AbstractDungeon.player)));
    }

//    @Override
//    public void onChestOpen(boolean bossChest) {
//        super.onChestOpen(bossChest);
//        if (bossChest && rewardSwap != null){
//            ((BossChest) ((TreasureRoomBoss) AbstractDungeon.getCurrRoom()).chest).relics.set(0, rewardSwap);
//        }
//    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlueCrystal();
    }
}

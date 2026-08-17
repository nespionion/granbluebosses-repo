package granbluebosses.relics.ancients;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class ShieldOfTenets extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("ShieldOfTenets");
    public static AbstractRelic rewardSwap = null;

    public ShieldOfTenets() {
        super(
                RELIC_ID,       // ID
                "ShieldOfTenets",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.STARTER,                              // Rarity
                LandingSound.MAGICAL);                            // SFX

    }

    public void onEquip() {
        for(RewardItem reward : AbstractDungeon.combatRewardScreen.rewards) {
            if (reward.cards != null) {
                for(AbstractCard c : reward.cards) {
                    this.onPreviewObtainCard(c);
                }
            }
        }

    }

    public void onPreviewObtainCard(AbstractCard c) {
        this.onObtainCard(c);
    }

    public void onObtainCard(AbstractCard c) {
        if (c.rarity == AbstractCard.CardRarity.RARE && c.canUpgrade() && !c.upgraded) {
            c.upgrade();
        }
        super.onObtainCard(c);
    }

    public AbstractRelic makeCopy() {
        return new ShieldOfTenets();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }


//    @Override
//    public void onChestOpen(boolean bossChest) {
//        super.onChestOpen(bossChest);
//        if (bossChest && rewardSwap != null){
//            ((BossChest) ((TreasureRoomBoss) AbstractDungeon.getCurrRoom()).chest).relics.set(0, rewardSwap);
//        }
//    }
}

package granbluebosses.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import granbluebosses.GranblueBosses;

public class GoldBrickReward extends CustomReward {

    private static final Texture ICON = new Texture(GranblueBosses.imagePath("rewards/GoldBrickReward.png"));

    public int amount;

    public static boolean GUARANTEED_GOLD_BRICK = false;
    // TODO : CHANGE THIS TO FALSE!

    public GoldBrickReward() {
        super(ICON, "Upgrade all cards in your deck", CustomRewardEnums.GOLD_BRICK);
        this.amount = 0;
    }



    @Override
    public boolean claimReward() {
        CardGroup upgradableCards = AbstractDungeon.player.masterDeck.getUpgradableCards();

        if (upgradableCards.isEmpty()) return false;
        if (upgradableCards.size() == 1){
            upgradableCards.getTopCard().upgrade();

            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards.getTopCard().makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));

            return true;
        }

        upgradableCards.shuffle(AbstractDungeon.treasureRng);
//        AbstractCard.CardRarity[] cardRarities = {AbstractCard.CardRarity.RARE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardRarity.COMMON, AbstractCard.CardRarity.BASIC};
//        for (AbstractCard.CardRarity cardRarity : cardRarities){
//            for (AbstractCard c : upgradableCards.group){
//                if (c.rarity == cardRarity){
//                    upgradeAllCardsOfRarity(upgradableCards, cardRarity);

//                    return true;
//                }
//            }
//        }
//        upgradableCards.getRandomCard(AbstractDungeon.treasureRng).upgrade();
        upgradeAllCardsOfRarity(upgradableCards, null);

        return true;
    }

    public static void upgradeAllCardsOfRarity(CardGroup upgradableCards, AbstractCard.CardRarity cardRarity){
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));

        for (AbstractCard c : upgradableCards.group){
            if (cardRarity == null || c.rarity == cardRarity){
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                c.upgrade();
            }
        }
    }

}

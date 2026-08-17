package granbluebosses.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import granbluebosses.config.ConfigMenu;
import granbluebosses.rewards.GbfFullHealReward;
import granbluebosses.rewards.GoldBrickReward;
import granbluebosses.rewards.LinkedRewardItem;

import java.util.ArrayList;

public class MonsterUtils {
    public static void handleCardPlusRelicLinkedReward(AbstractRelic weaponRelic, AbstractCard primalCard){
        if (ConfigMenu.enableExtraRewards) {

            RewardItem relicReward = new RewardItem(weaponRelic);

            RewardItem cardReward = new RewardItem(relicReward, RewardItem.RewardType.CARD);

            cardReward.cards = new ArrayList<>(1);
            cardReward.cards.add(primalCard);
            for (AbstractRelic rc : AbstractDungeon.player.relics){
                rc.onPreviewObtainCard(primalCard);
            }
            cardReward.text = "Obtain this Primal's card";

            LinkedRewardItem linkedReward = new LinkedRewardItem(cardReward);

            linkedReward.addRelicLink(new LinkedRewardItem(linkedReward, weaponRelic));

            AbstractDungeon.getCurrRoom().rewards.add(linkedReward);
            AbstractDungeon.getCurrRoom().rewards.add(linkedReward.linkedRewards.get(0));

//            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
//            reward.text = "Obtain this Primal beast's card";
//            reward.relic = animaRelic;

//            reward.relicLink = reward2;
//            reward2.relicLink = reward;

//            AbstractDungeon.getCurrRoom().rewards.add(reward2);
//            AbstractDungeon.getCurrRoom().rewards.add(reward);

        }
    }

    public static void handleEndOfBattleRewardsOld(AbstractRelic weaponRelic, AbstractRelic animaRelic){
        if (ConfigMenu.enableExtraRewards) {

            RewardItem reward2 = new RewardItem(weaponRelic);

            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
            reward.text = "Obtain this Primal's card";
            reward.relic = animaRelic;

            reward.relicLink = reward2;
            reward2.relicLink = reward;

            AbstractDungeon.getCurrRoom().rewards.add(reward2);
            AbstractDungeon.getCurrRoom().rewards.add(reward);
        }
    }

    public static void handleDoubleRelicLinkedReward(AbstractRelic weaponRelic, AbstractRelic animaRelic, String animaString){
        if (ConfigMenu.enableExtraRewards) {

            RewardItem reward2 = new RewardItem(weaponRelic);

            RewardItem reward = new RewardItem(reward2, RewardItem.RewardType.RELIC);
            reward.text = animaString;
            reward.relic = animaRelic;

            reward.relicLink = reward2;
            reward2.relicLink = reward;



            AbstractDungeon.getCurrRoom().rewards.add(reward2);
            AbstractDungeon.getCurrRoom().rewards.add(reward);


            AbstractDungeon.combatRewardScreen.positionRewards();
        }
    }


    public static boolean isLinkedReward(RewardItem r){

        if (AbstractDungeon.getCurrRoom().rewards.size() < 2 ||
                r == null ||
                !AbstractDungeon.getCurrRoom().rewards.contains(r) ||
                AbstractDungeon.getCurrRoom().rewards.indexOf(r) == 0){
            return false;
        }

        int indexOfr = AbstractDungeon.getCurrRoom().rewards.indexOf(r);

        if (AbstractDungeon.getCurrRoom().rewards.get(indexOfr - 1).relicLink == r) return true;
        else return false;
    }

    public static void addGoldBrickReward(){
        AbstractDungeon.getCurrRoom().rewards.add(new GoldBrickReward());
    }

    public static void addFullHealReward(){
        AbstractDungeon.getCurrRoom().rewards.add(new GbfFullHealReward());
    }
}

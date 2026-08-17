package granbluebosses.relics.ancients;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class DamascusIngot extends BaseRelic {
    public static final String NAME = DamascusIngot.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    public static AbstractRelic rewardSwap = null;

    public DamascusIngot() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (!c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) && !c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)){
                c.upgrade();
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));

            }
        }

//        int defendsRemoved = 0;
//        AbstractCard strikeToCopy = null;
//        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
//            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)){
//                addToBot(new UpgradeSpecificCardAction(c));
//                c.upgrade();
//                if (strikeToCopy == null){
//                    strikeToCopy = c;
//                }
//            } else if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
//                AbstractDungeon.player.masterDeck.removeCard(c);
//                defendsRemoved++;
//            }
//        }

//        if (strikeToCopy != null && defendsRemoved > 2){
//            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
//
//            for (int i = 0; i < defendsRemoved/2; i++){
//                group.addToBottom(strikeToCopy.makeCopy());
//            }
//
//            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
//        }


    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


//    @Override
//    public void onChestOpen(boolean bossChest) {
//        super.onChestOpen(bossChest);
//        if (bossChest && rewardSwap != null){
//            ((BossChest) ((TreasureRoomBoss) AbstractDungeon.getCurrRoom()).chest).relics.set(0, rewardSwap);
//        }
//    }

}
package granbluebosses.relics.ancients;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class LegendaryMerit extends BaseRelic {
    public static final String NAME = LegendaryMerit.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private static final int upgradeAmt = 1;
    public static AbstractRelic rewardSwap = null;

    public LegendaryMerit() {
        super(ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
        this.counter = upgradeAmt;
    }

//    @Override
//    public void onEquip() {
//        super.onEquip();
//        CardGroup strikesAndDefendsGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
//
//        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
//            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)){
//                strikesAndDefendsGroup.addToBottom(c);
//            }
//        }
//
//        for (int i = 0; i < upgradeAmt && !strikesAndDefendsGroup.isEmpty(); i++) {
//            AbstractCard c = strikesAndDefendsGroup.getRandomCard(AbstractDungeon.relicRng);
//            if (c.upgraded){
//                i--;
//                strikesAndDefendsGroup.removeCard(c);
//            } else {
//                c.upgrade();
//                strikesAndDefendsGroup.removeCard(c);
//            }
//        }
//    }


    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 0;
        CardGroup cardsToRemove = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (!c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) && !c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)){
                cardsToRemove.addToBottom(c);
                this.counter++;
            }
        }

        float displayCount = 0.0F;

        for (AbstractCard c : cardsToRemove.group){
            c.untip();
            c.unhover();
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)Settings.WIDTH / 3.0F + displayCount, (float)Settings.HEIGHT / 2.0F));
            displayCount += (float)Settings.WIDTH / 6.0F;
            AbstractDungeon.player.masterDeck.removeCard(c);

        }

        this.counter = Math.max(this.counter, upgradeAmt);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        super.onObtainCard(c);
        if (this.counter > 0 && c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS){
            if (!c.upgraded) {
                c.upgrade();
            }
            this.counter = Math.max(0, this.counter-1);
        }


        if (!this.grayscale && this.counter <= 0){
            this.counter = 0;
            this.grayscale = true;
        }

        super.onObtainCard(c);
    }

    @Override
    public void onPreviewObtainCard(AbstractCard c) {
        super.onPreviewObtainCard(c);
        if (!c.upgraded && this.counter > 0 && c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS){
            c.upgrade();
            this.flash();
        }
    }

    @Override
    public String getUpdatedDescription() {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hasRelic(ID))
            return DESCRIPTIONS[5];

        if (this.counter == 1){
            return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
        } else {
            return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[2];
        }


    }

//
//    @Override
//    public void onChestOpen(boolean bossChest) {
//        super.onChestOpen(bossChest);
//        if (bossChest && rewardSwap != null){
//            ((BossChest) ((TreasureRoomBoss) AbstractDungeon.getCurrRoom()).chest).relics.set(0, rewardSwap);
//        }
//    }

}
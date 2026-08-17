package granbluebosses.relics.animas;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import granbluebosses.GranblueBosses;
import granbluebosses.cards.BaseCard;
import granbluebosses.cards.rewards.AlexielCall;
import granbluebosses.relics.BaseRelic;

public class AnimaOdin extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("AnimaOdin");

    public static final BaseCard cardToReceive = new AlexielCall();
    private boolean cardsReceived = true;

    public AnimaOdin() {
        super(
                RELIC_ID,       // ID
                "AnimaOdin",
                AbstractCard.CardColor.COLORLESS,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX
        this.relicType = RelicType.SHARED;
    }

    @Override
    public void onEquip() {
        this.cardsReceived = false;
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        UnlockTracker.markCardAsSeen(cardToReceive.cardID);
        group.addToBottom(cardToReceive.makeCopy());
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
        super.onEquip();
    }

//    public void update() {
//        super.update();
//        if (!this.cardsReceived && !AbstractDungeon.isScreenUp) {
//            AbstractDungeon.combatRewardScreen.open();
//            AbstractDungeon.overlayMenu.proceedButton.setLabel(this.DESCRIPTIONS[2]);
//            this.cardsReceived = true;
//            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
//        }
//    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        super.updateDescription(c);
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}

package granbluebosses.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import granbluebosses.util.CustomTags;

public class UpgradeAllArcarumCardsAction extends AbstractGameAction {
    public UpgradeAllArcarumCardsAction() {
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            AbstractPlayer p = AbstractDungeon.player;
            this.upgradeAllArcarumCardsInGroup(p.hand);
            this.upgradeAllArcarumCardsInGroup(p.drawPile);
            this.upgradeAllArcarumCardsInGroup(p.discardPile);
            this.upgradeAllArcarumCardsInGroup(p.exhaustPile);
            this.isDone = true;
        }

    }

    private void upgradeAllArcarumCardsInGroup(CardGroup cardGroup) {
        for(AbstractCard c : cardGroup.group) {
            if (c.canUpgrade() && c.hasTag(CustomTags.ARCARUM_CALL)) {
                if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                    c.superFlash();
                }

                c.upgrade();
                c.applyPowers();
            }
        }

    }
}

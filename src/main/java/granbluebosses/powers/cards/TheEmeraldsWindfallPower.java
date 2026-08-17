package granbluebosses.powers.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import granbluebosses.powers.BasePower;

import static granbluebosses.GranblueBosses.makeID;

public class TheEmeraldsWindfallPower extends BasePower {

    public static final String NAME = TheEmeraldsWindfallPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static AbstractCard cardToPlay = null;
    private static UseCardAction actionOfCard = null;

    public TheEmeraldsWindfallPower(AbstractCreature owner, int amount) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);

        if (cardToPlay == null || actionOfCard == null){
            cardToPlay = null;
            actionOfCard = null;
            return;
        }

        this.flash();
        for (int i = 0; i < this.amount; i++){
            AbstractMonster m = null;
            if (actionOfCard.target != null && !actionOfCard.target.isDeadOrEscaped()) {
                m = (AbstractMonster)actionOfCard.target;
            } else {
                m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            }

            AbstractCard tmp = cardToPlay.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = cardToPlay.current_x;
            tmp.current_y = cardToPlay.current_y;
            tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, cardToPlay.energyOnUse, true, true), true);
        }

        cardToPlay = null;
        actionOfCard = null;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && this.amount > 0 && cardToPlay == null) {
            cardToPlay = card;
            actionOfCard = action;
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}
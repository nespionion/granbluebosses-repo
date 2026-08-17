package granbluebosses.powers.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;
import granbluebosses.util.TextureLoader;

import static granbluebosses.GranblueBosses.makeID;

public class SentencedTrumpet extends BasePower {

    public static final String NAME = SentencedTrumpet.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SentencedTrumpet(AbstractCreature owner, int amount, int amount2) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, amount);
        this.amount2 = amount2;

    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (this.amount > 0){
//            this.reducePower(1);
            this.amount--;
        }
        if (this.amount <= 0 && this.amount2 <= 0){

            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        if (this.amount <= 0 && this.amount2 > 0){
            this.flash();
            this.amount2--;
            addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
        }
        if (this.amount2 <= 0){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        if (this.amount == 1){
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
        } else if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[3];
        }

        if (this.amount2 <= 1){
            this.description = this.description + DESCRIPTIONS[5];
        } else {
            this.description = this.description + this.amount2 + DESCRIPTIONS[4];
        }
    }

}
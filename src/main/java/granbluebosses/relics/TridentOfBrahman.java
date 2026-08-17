package granbluebosses.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;

public class TridentOfBrahman extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("TridentOfBrahman");

    private static boolean isActive = true;

    public TridentOfBrahman() {
        super(
                RELIC_ID,       // ID
                "TridentOfBrahman",
                AbstractCard.CardColor.COLORLESS,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX
        this.relicType = RelicType.SHARED;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        isActive = true;
        this.grayscale = false;
    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (isActive && c.type == AbstractCard.CardType.ATTACK){
//        if (isActive && (c.rarity == AbstractCard.CardRarity.COMMON || c.rarity == AbstractCard.CardRarity.BASIC) && c.type == AbstractCard.CardType.ATTACK){
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }

            AbstractCard tmp = c.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = c.current_x;
            tmp.current_y = c.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, c.energyOnUse, true, true), true);

            isActive = false;
            this.grayscale = true;
        }
        super.onUseCard(c, action);

    }

    @Override
    public void onVictory() {
        super.onVictory();
        isActive = true;
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TridentOfBrahman();
    }
}

package granbluebosses.relics;

import basemod.helpers.RelicType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;

public class TyrosZither extends BaseRelic {

    public static final String RELIC_ID = GranblueBosses.makeID("TyrosZither");

    private static boolean isActive = true;
    private static int magicNumber = 1;

    public TyrosZither() {
        super(
                RELIC_ID,       // ID
                "TyrosZither",
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
    public void onVictory() {
        super.onVictory();
        isActive = true;
        this.grayscale = false;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (isActive && c.rarity == AbstractCard.CardRarity.RARE){
            isActive = false;
            this.grayscale = true;
            addToBot(new DrawCardAction(magicNumber));
        }
        super.onPlayCard(c, m);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TyrosZither();
    }
}

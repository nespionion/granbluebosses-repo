package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class ArcarumJusticeReversed extends BaseRelic {
    public static final String RELIC_ID = GranblueBosses.makeID("ArcarumJusticeReversed");
    public static final int DAMAGE_DEALT = 3;
    public static final int STACKS = 1;

    public ArcarumJusticeReversed() {
        super(
                RELIC_ID,       // ID
                "ArcarumJusticeReversed",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);

        if (c.type == AbstractCard.CardType.ATTACK && c.cost % 2 == 0){
            AbstractMonster mo;
            if (m == null) mo = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
            else mo = m;

            addToTop(new DamageAction(mo, new DamageInfo(AbstractDungeon.player, DAMAGE_DEALT, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, STACKS, false), STACKS));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DAMAGE_DEALT + DESCRIPTIONS[1] + STACKS + DESCRIPTIONS[2] + DESCRIPTIONS[3]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ArcarumJusticeReversed();
    }
}

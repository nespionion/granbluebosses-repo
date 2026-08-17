package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class SkeletalEclipse extends BaseRelic {
    public static final String RELIC_ID = GranblueBosses.makeID("SkeletalEclipse");
    public static final int ADD_DAMAGE = 2;


    public SkeletalEclipse() {
        super(
                RELIC_ID,       // ID
                "SkeletalEclipse",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (c.type == AbstractCard.CardType.ATTACK){
            this.flash();
            addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, c.cost, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SkeletalEclipse();
    }
}

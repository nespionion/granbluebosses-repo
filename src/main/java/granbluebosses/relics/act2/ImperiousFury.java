package granbluebosses.relics.act2;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class ImperiousFury extends BaseRelic {
    public static final String RELIC_ID = GranblueBosses.makeID("ImperiousFury");
    public static final int CARD_THRESHOLD = 8;

    public ImperiousFury() {
        super(
                RELIC_ID,       // ID
                "ImperiousFury",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        if (AbstractDungeon.player.masterDeck.size() >= CARD_THRESHOLD){
            this.flash();

            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters){
                addToBot(new VFXAction(new LightningEffect(mo.hb.cX, mo.hb.cY)));
            }
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, AbstractDungeon.player.masterDeck.size() / CARD_THRESHOLD, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.LIGHTNING));
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player.masterDeck.size() / CARD_THRESHOLD));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ImperiousFury();
    }
}

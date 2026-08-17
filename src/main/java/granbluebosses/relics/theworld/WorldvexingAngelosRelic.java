package granbluebosses.relics.theworld;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Halt;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.RushdownPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.VioletLotus;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.GranblueBosses.makeID;

public class WorldvexingAngelosRelic extends BaseRelic {
    public static final String NAME = "WorldvexingAngelosRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String RELIC_ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public WorldvexingAngelosRelic() {
        super(RELIC_ID, NAME, PrimalColor.GBF_PRIMAL_COLOR, RARITY, SOUND);
    }

    @Override
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        super.onChangeStance(prevStance, newStance);
        if (prevStance.ID.equals(WrathStance.STANCE_ID)){
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        return AbstractDungeon.player != null &&
                AbstractDungeon.player.stance instanceof CalmStance &&
                info.type == DamageInfo.DamageType.NORMAL ?
                super.onAttackedToChangeDamage(info, damageAmount) * 2 :
                super.onAttackedToChangeDamage(info, damageAmount);
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        return AbstractDungeon.player != null &&
                AbstractDungeon.player.stance instanceof CalmStance &&
                info.type == DamageInfo.DamageType.NORMAL ?
                super.onAttackToChangeDamage(info, damageAmount) * 2 :
                super.onAttackToChangeDamage(info, damageAmount);
    }

    @Override
    public int onPlayerGainBlock(int blockAmount) {
        return super.onPlayerGainBlock(blockAmount) + Math.max((blockAmount / 10), 1);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new WorldvexingAngelosRelic();
    }
}

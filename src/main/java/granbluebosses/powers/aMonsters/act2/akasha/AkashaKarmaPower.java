package granbluebosses.powers.aMonsters.act2.akasha;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import granbluebosses.monsters.act2.bosses.Akasha;
import granbluebosses.powers.BasePower;
import granbluebosses.powers.OmenUtils;

import static granbluebosses.GranblueBosses.makeID;

public class AkashaKarmaPower extends BasePower {

    private static final String NAME = AkashaKarmaPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AkashaKarmaPower(AbstractCreature owner, int amount) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.type == AbstractCard.CardType.ATTACK) this.reducePower(1);
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount <= 0){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        Akasha akashaInstance = (Akasha) AbstractDungeon.getCurrRoom().monsters.getMonster(Akasha.MONSTER_ID);
        if (akashaInstance != null){
            akashaInstance.cancelPurification();
            OmenUtils.onCancelOmenSFX(akashaInstance);
        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        Akasha akashaInstance = (Akasha) AbstractDungeon.getCurrRoom().monsters.getMonster(Akasha.MONSTER_ID);
        if (akashaInstance != null){
            OmenUtils.onPrepOmenSFX(akashaInstance);
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = this.amount == 1 ?
                DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]:
                DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]
        ;
    }

}
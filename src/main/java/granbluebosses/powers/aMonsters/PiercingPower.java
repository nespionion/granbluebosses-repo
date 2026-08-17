package granbluebosses.powers.aMonsters;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import granbluebosses.powers.BasePower;
import granbluebosses.util.CustomPowerType;

import static granbluebosses.GranblueBosses.makeID;

public class PiercingPower extends BasePower {

    public static final String POWER_ID = makeID("PiercingPower");
    private static final AbstractPower.PowerType TYPE = CustomPowerType.BOSS_MECHANIC;
    private static final boolean TURN_BASED = false;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    private final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public PiercingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, false, owner, amount);
        this.priority = 10;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        this.amount -= 1;
        if (this.amount < 1){
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        if (this.owner.isPlayer){
            card.damageTypeForTurn = DamageInfo.DamageType.HP_LOSS;
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.owner == this.owner && info.type == DamageInfo.DamageType.NORMAL){
            info.type = DamageInfo.DamageType.HP_LOSS;
        }
        info.applyPowers(info.owner, target);
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.owner == this.owner && info.type == DamageInfo.DamageType.NORMAL){
            info.type = DamageInfo.DamageType.HP_LOSS;
        }
        AbstractCreature target = AbstractDungeon.actionManager.currentAction != null
                &&  AbstractDungeon.actionManager.currentAction.target != null?
                AbstractDungeon.actionManager.currentAction.target :
                    AbstractDungeon.actionManager.previousAction != null
                    && AbstractDungeon.actionManager.previousAction.target != null ?
                AbstractDungeon.actionManager.previousAction.target :
                null;
        info.applyPowers(info.owner, target);
        return info.output;
    }

    @Override
    public void updateDescription() {
        this.description = this.DESCRIPTIONS[0];
    }
}

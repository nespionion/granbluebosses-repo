package granbluebosses.powers.aMonsters.act1.grandorder;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import granbluebosses.action.SetHPToSpecificAmountAction;
import granbluebosses.powers.BasePower;
import granbluebosses.util.CustomPowerType;

import static granbluebosses.GranblueBosses.makeID;

public class ConjunctionPower extends BasePower {

    public static final String POWER_ID = makeID("ConjunctionPower");
    private static final AbstractPower.PowerType TYPE = CustomPowerType.BOSS_MECHANIC;
    private static final boolean TURN_BASED = false;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);

    public ConjunctionPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, false, owner, -1);
    }

//    @Override
//    public void atEndOfRound() {
//        super.atEndOfRound();
//        if (this.owner != null){
//            AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
//            addToBot(new SetHPToSpecificAmountAction(this.owner, this.owner, 1, AbstractGameAction.AttackEffect.FIRE));
//        }
//    }

//    @Override
//    public void atStartOfTurnPostDraw() {
//        super.atStartOfTurnPostDraw();
//        if (this.owner.currentHealth < 1){
////            addToBot(new SetHPToSpecificAmountAction(this.owner, this.owner, 1, AbstractGameAction.AttackEffect.FIRE));
//            addToBot(new LoseHPAction(this.owner, this.owner, this.owner.currentHealth-1, AbstractGameAction.AttackEffect.FIRE));
//        }
////        addToBot(new CanLoseAction());
//        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
//
//    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new SetHPToSpecificAmountAction(this.owner, this.owner, 1, AbstractGameAction.AttackEffect.FIRE));

//        if (this.owner.currentHealth > 1){
//            addToBot(new LoseHPAction(this.owner, this.owner, this.owner.currentHealth-1, AbstractGameAction.AttackEffect.FIRE));
//        }
//        addToBot(new CanLoseAction());
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = this.DESCRIPTIONS[0];
    }
}

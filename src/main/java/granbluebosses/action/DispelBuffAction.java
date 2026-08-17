package granbluebosses.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import granbluebosses.powers.aMonsters.act2.TorrentOfLifePower;
import granbluebosses.powers.aMonsters.act2.UniversalUpwellingPower;
import granbluebosses.powers.common.DispelCancelPower;

public class DispelBuffAction extends AbstractGameAction {

    private final boolean allBuffs;

    public DispelBuffAction(AbstractCreature target, AbstractCreature source) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.REDUCE_POWER;
        this.target = target;
        this.allBuffs = false;
        this.source = source;
    }

    public DispelBuffAction(AbstractCreature target, AbstractCreature source, boolean allBuffs) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.REDUCE_POWER;
        this.target = target;
        this.amount = 1;
        this.allBuffs = allBuffs;
        this.source = source;
    }

    public DispelBuffAction(AbstractCreature target, AbstractCreature source, int buffAmt) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.REDUCE_POWER;
        this.target = target;
        this.amount = buffAmt;
        this.allBuffs = false;
        this.source = source;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.target.hasPower(DispelCancelPower.POWER_ID)){
                addToBot(new RemoveSpecificPowerAction(this.target, this.source, DispelCancelPower.POWER_ID));
            } else if (this.target.hasPower(UniversalUpwellingPower.POWER_ID)){
                addToBot(new RemoveSpecificPowerAction(this.target, this.source, UniversalUpwellingPower.POWER_ID));
            } else if (this.target.hasPower(TorrentOfLifePower.POWER_ID)){
                addToBot(new RemoveSpecificPowerAction(this.target, this.source, TorrentOfLifePower.POWER_ID));
            } else {
                this.removeBuffs();
            }
            this.isDone = true;
        }

        this.tickDuration();
    }

    public void removeBuffs(){
        AbstractPower pow = null;
        for (int i = 0; i < this.target.powers.size() && (this.allBuffs || this.amount >= 1); i++){
            pow = this.target.powers.get(i);
            if (pow.type == AbstractPower.PowerType.BUFF){
                addToBot(new RemoveSpecificPowerAction(this.target, this.source, pow));
                this.amount -= this.allBuffs ? 0 : 1;
            }
        }
    }
}

package granbluebosses.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import granbluebosses.monsters.IncantedOmenEnemy;

public class CancelOmenAction extends AbstractGameAction {

    public CancelOmenAction(AbstractCreature target) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.REDUCE_POWER;
        this.target = target;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (target instanceof IncantedOmenEnemy){
                ((IncantedOmenEnemy) target).resolveOmen();
            }
        }

        this.tickDuration();
    }
}

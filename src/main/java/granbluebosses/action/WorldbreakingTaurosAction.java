package granbluebosses.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class WorldbreakingTaurosAction extends AbstractGameAction {

    public WorldbreakingTaurosAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && AbstractDungeon.player != null && !AbstractDungeon.player.orbs.isEmpty()) {
            for (AbstractOrb ammo : AbstractDungeon.player.orbs){
                ammo.onEvoke();
            }
        }

        this.tickDuration();
    }
}

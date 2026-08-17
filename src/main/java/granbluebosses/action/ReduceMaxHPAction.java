package granbluebosses.action;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReduceMaxHPAction extends AbstractGameAction {
    private int amount;

    public ReduceMaxHPAction(AbstractCreature c, int amount) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startDuration;
        this.amount = amount;
        this.target = c;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.target.decreaseMaxHealth(MathUtils.round(this.amount));
        }

        this.tickDuration();
    }

}
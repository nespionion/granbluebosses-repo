package granbluebosses.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class SetGBFAnimationAction extends AbstractGameAction {

    public String animationName;
    public boolean isLooping;

    public SetGBFAnimationAction(AbstractCreature target, String animationName) {
        this(target, target, 0, animationName, false);
    }

    public SetGBFAnimationAction(AbstractCreature target, String animationName, boolean isLooping) {
        this(target, target, 0, animationName, isLooping);
    }

    public SetGBFAnimationAction(AbstractCreature target, AbstractCreature source, int amount, String animationName, boolean isLooping) {
        this.setValues(target, source, amount);
        this.animationName = animationName;
        this.isLooping = isLooping;
    }

    @Override
    public void update() {
        target.state.setAnimation(amount, animationName, isLooping);
    }
}

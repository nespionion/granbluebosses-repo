package granbluebosses.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class SetHPToSpecificAmountAction extends AbstractGameAction {

    private int amountToChange = 0;

    public SetHPToSpecificAmountAction(AbstractCreature target, AbstractCreature source, int amount) {
        this(target, source, amount, AttackEffect.NONE);
    }

    public SetHPToSpecificAmountAction(AbstractCreature target, AbstractCreature source, int amount, AbstractGameAction.AttackEffect effect) {
        this.setValues(target, source, amount);
        if (this.target.currentHealth == amount){
            this.actionType = ActionType.SPECIAL;
            this.amountToChange = 0;

        } else if (this.target.currentHealth > amount){
            this.actionType = ActionType.DAMAGE;
            this.amountToChange = this.target.currentHealth - this.amount;

        } else {
            this.actionType = ActionType.HEAL;
            this.amountToChange = this.amount - this.target.currentHealth;
        }

        this.attackEffect = effect;
        this.duration = 0.33F;
    }

    @Override
    public void update() {
        if (this.duration == 0.33F && this.target.currentHealth > 0 && this.actionType == ActionType.DAMAGE) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }

        this.tickDuration();
        if (this.isDone) {
            switch (this.actionType){
                case DAMAGE:
                    this.target.damage(new DamageInfo(this.source, this.amountToChange, DamageInfo.DamageType.HP_LOSS));
                    break;
                case HEAL:
                    this.target.heal(this.amountToChange);
                    break;
                default:
                    break;
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1F));
            }
        }
    }
}

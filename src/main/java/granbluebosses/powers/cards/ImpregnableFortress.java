package granbluebosses.powers.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;

import static granbluebosses.GranblueBosses.makeID;

public class ImpregnableFortress extends BasePower {

    public static final String NAME = ImpregnableFortress.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ImpregnableFortress(AbstractCreature owner, int amount) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.HP_LOSS){
            return damageAmount >= this.owner.currentHealth ? this.owner.currentHealth - 1 : damageAmount;
        } else {
            return damageAmount >= this.owner.currentHealth + this.owner.currentBlock ? this.owner.currentHealth + this.owner.currentBlock - 1 : damageAmount;
        }
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.HP_LOSS){
            return damage >= this.owner.currentHealth ? this.owner.currentHealth - 1 : damage;
        } else {
            return damage >= this.owner.currentHealth + this.owner.currentBlock ? this.owner.currentHealth + this.owner.currentBlock - 1 : damage;
        }
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        this.reducePower(1);
        if (this.amount <= 0){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }

}
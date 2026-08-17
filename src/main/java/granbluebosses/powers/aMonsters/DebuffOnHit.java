package granbluebosses.powers.aMonsters;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import granbluebosses.powers.BasePower;
import granbluebosses.util.CustomPowerType;

import static granbluebosses.GranblueBosses.makeID;

public class DebuffOnHit extends BasePower {
    public static final String POWER_ID = makeID("DebuffOnHit");
    private static final PowerType TYPE = CustomPowerType.BOSS_MECHANIC;
    private static final boolean TURN_BASED = false;
    public AvailableDebuffs debuffToInflict;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public enum AvailableDebuffs {
        VULNERABLE,
        WEAK,
        FRAIL,
        POISON,
        NEG_STRENGTH,
        NEG_DEXTERITY,
        NEG_FOCUS,
        ENTANGLE,
        CONSTRICTED
    }


    public DebuffOnHit(AbstractCreature owner, AvailableDebuffs debuffToInflict, int stacks) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, -1);
        this.debuffToInflict = debuffToInflict;
        this.amount = stacks;

        this.updateDescription2();
    }

    @Override
    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        AbstractPower debuff = getDebuffObject(target);
        addToBot(new ApplyPowerAction(target, this.owner, debuff));
        super.onInflictDamage(info, damageAmount, target);
    }

    private AbstractPower getDebuffObject(AbstractCreature target){
        switch (this.debuffToInflict){
            case WEAK:
                return new WeakPower(target, this.amount, true);
            case FRAIL:
                return new FrailPower(target, this.amount, true);
            case VULNERABLE:
                return new VulnerablePower(target, this.amount, true);
            case NEG_STRENGTH:
                return new StrengthPower(target, -this.amount);
            case NEG_DEXTERITY:
                return new DexterityPower(target, -this.amount);
            case NEG_FOCUS:
                return new FocusPower(target, -this.amount);
            case CONSTRICTED:
                return new ConstrictedPower(target, this.owner, this.amount);
            case POISON:
                return new PoisonPower(target, this.owner, this.amount);
            case ENTANGLE:
                return new EntanglePower(target);
            default:
                return new WeakPower(target, this.amount, true);
        }
    }

    private String getDebuffName(){
        switch (this.debuffToInflict){
            case WEAK:
                return "#y" + WeakPower.NAME;
            case FRAIL:
                return "#y" + FrailPower.NAME;
            case VULNERABLE:
                return "#y" + VulnerablePower.NAME;
            case NEG_STRENGTH:
                return "#y" + StrengthPower.NAME;
            case NEG_DEXTERITY:
                return "#y" + DexterityPower.NAME;
            case NEG_FOCUS:
                return "#y" + FocusPower.NAME;
            case CONSTRICTED:
                return "#y" + ConstrictedPower.NAME;
            case POISON:
                return "#y" + PoisonPower.NAME;
            case ENTANGLE:
                return "#y" + EntanglePower.POWER_ID;
            default:
                return "#y" + this.debuffToInflict.name();
        }
    }


    @Override
    public void updateDescription() {
        super.updateDescription();
    }

    public void updateDescription2(){
        super.updateDescription();
        String debuffName = getDebuffName();
        this.name = powerStrings.NAME + ": " + debuffName;
        this.description = DESCRIPTIONS[0] + "#b" + this.amount + " " + debuffName + ".";
    }
}

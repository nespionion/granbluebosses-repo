package granbluebosses.powers.incantedOmens;

import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.powers.BasePower;
import granbluebosses.powers.OmenUtils;
import granbluebosses.util.CustomPowerType;
import granbluebosses.util.Sounds;
import granbluebosses.utilInterfaces.OnOmenCanceledPower;
import granbluebosses.utilInterfaces.OnPowerReceivedPower;
import granbluebosses.vfx.CustomVFX;

import static granbluebosses.GranblueBosses.makeID;

abstract public class AbstractIncantedOmen extends BasePower implements OnPowerReceivedPower {

    public static final String POWER_ID = makeID("IncantedOmen");
    private static final AbstractPower.PowerType TYPE = CustomPowerType.BOSS_MECHANIC;
    private static final boolean TURN_BASED = false;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);


    public AbstractIncantedOmen(String powerId, AbstractCreature owner, int amount) {
        super(powerId, TYPE, TURN_BASED, owner, amount);
    }

    private AbstractIncantedOmen(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        for (AbstractPower pow : this.owner.powers){
            if (pow.ID.equals(POWER_ID) && pow != this){
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        }
    }

    public void onReceivePower (AbstractPower power, AbstractCreature target, AbstractCreature source) {
        for (AbstractPower pow : this.owner.powers){
            if (pow.ID.equals(POWER_ID) && pow != this){
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
//        this.flash();
        OmenUtils.onPrepOmenSFX(this.owner);
    }

    public void stackPower(int stackAmount) {
        if (stackAmount > 0) {
            return;
        }

        super.stackPower(stackAmount);

        if (this.amount <= 0){
            this.cancelThisOmen();
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        if (reduceAmount < 0) {
            return;
        }

        super.reducePower(reduceAmount);

        if (this.amount <= 0){
            this.cancelThisOmen();
        }
    }

    public void cancelThisOmen(){
        OmenUtils.onCancelOmenSFX(this.owner);

        if (this.owner instanceof IncantedOmenEnemy){
            ((IncantedOmenEnemy)this.owner).resolveOmen();
        }

        for (AbstractPower pow : this.owner.powers){
            if (pow instanceof OnOmenCanceledPower){
                ((OnOmenCanceledPower) pow).onOmenCancel();
            }
        }

        for (AbstractPower pow : AbstractDungeon.player.powers){
            if (pow instanceof OnOmenCanceledPower){
                ((OnOmenCanceledPower) pow).onOmenCancel();
            }
        }

        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    abstract public String getConditionDescription();

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.name = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        this.description = this.getConditionDescription();
    }
}

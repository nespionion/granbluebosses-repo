package granbluebosses.powers.stanceOmens;

import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import granbluebosses.powers.BasePower;
import granbluebosses.util.CustomPowerType;
import granbluebosses.util.Sounds;
import granbluebosses.vfx.CustomVFX;

import static granbluebosses.GranblueBosses.makeID;

public class StanceOmen extends BasePower {
    public static final String POWER_ID = makeID("StanceOmen");
    private static final PowerType TYPE = CustomPowerType.BOSS_MECHANIC;
    private static final boolean TURN_BASED = false;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);

    public StanceOmen(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    public void setUpOmenByHp(int num){
        int percentage = (100/num);
        this.amount = percentage;
        this.description = "This enemy will prepare a powerful attack when under " + percentage + "% health unless it is stunned.";
    }

    public void setUpOmenByHp(float num){
        int percentage = (int) Math.ceil(100/num);
        this.amount = percentage;
        this.description = "This enemy will prepare a powerful attack when under " + percentage + "% health unless it is stunned.";
    }

    public void setUpOmenByXPowerOrHigher(AbstractPower pow, int num){
        this.amount = -1;
        this.description = "This enemy will prepare a powerful attack when it has " + num + " stacks of " + pow.name + " or more.";
    }

    public void setUpOmenXPowerOrLower(AbstractPower pow, int num){
        this.amount = -1;
        this.description = "This enemy will prepare a powerful attack when it has " + num + " stacks of " + pow.name + " or less.";
    }

    public void setUpOmenXPowerOrHigherOnPlayer(AbstractPower pow, int num){
        this.amount = -1;
        this.description = "This enemy will prepare a powerful attack when the player has " + num + " stacks of " + pow.name + " or more.";
    }

    public void setUpOmenXPowerOrLowerOnPlayer(AbstractPower pow, int num){
        this.amount = -1;
        this.description = "This enemy will prepare a powerful attack when the player has " + num + " stacks of " + pow.name + " or less.";
    }

    public void setUpOmenXPowerOnPlayer(AbstractPower pow){
        this.amount = -1;
        this.description = "This enemy will prepare a powerful attack when it has " + pow.name + ".";
    }

    public void setUpOmenByString(String newDescription){
        this.description = newDescription;
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.name = powerStrings.NAME;
    }
}

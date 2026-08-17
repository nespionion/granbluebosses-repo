package granbluebosses.powers.incantedOmens;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import granbluebosses.powers.incantedOmens.AbstractIncantedOmen;
import granbluebosses.util.CardCheckUtils;
import granbluebosses.util.CustomPowerType;
import granbluebosses.utilInterfaces.OnPowerReceivedPower;

import static granbluebosses.GranblueBosses.makeID;

public class IncantedOmenPowersApplied extends AbstractIncantedOmen implements OnPowerReceivedPower {

    public static final String POWER_ID = makeID("IncantedOmenPowersApplied");
    private static final PowerType TYPE = CustomPowerType.BOSS_MECHANIC;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    protected final PowerType powerType;
    protected final boolean mustBeDistinct;
    protected final boolean isArtifactAgnostic;


    public IncantedOmenPowersApplied(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        this.powerType = null;
        this.mustBeDistinct = false;
        this.isArtifactAgnostic = true;
    }

    public IncantedOmenPowersApplied(AbstractCreature owner, int amount, PowerType powerType, boolean mustBeDistinct, boolean isArtifactAgnostic) {
        super(POWER_ID, owner, amount);

        this.powerType = powerType;

        this.mustBeDistinct = mustBeDistinct;

        this.isArtifactAgnostic = isArtifactAgnostic;

        this.updateDescription();

    }

    public void onReceivePower (AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onReceivePower(power, target, source);
        if (target == null || target.isDeadOrEscaped() || target.isDying || target.isEscaping || power == null){
            return;
        }
        if (this.owner == target && (this.powerType == null || this.powerType == power.type)){
            if (this.mustBeDistinct){
                this.reducePower(1);
//                this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            } else {
                this.reducePower(power.amount);
//                this.addToBot(new ReducePowerAction(this.owner, this.owner, this, power.amount));
            }
        }
    }

    @Override
    public String getConditionDescription() {
        if (Settings.language == Settings.GameLanguage.ZHS){
            return AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT ? DESCRIPTIONS[1] + CardCheckUtils.generateConditionStringCN(this.amount, this.powerType, this.mustBeDistinct, this.isArtifactAgnostic) + DESCRIPTIONS[2] : DESCRIPTIONS[0];
        } else {
            return AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT ? DESCRIPTIONS[1] + CardCheckUtils.generateConditionString(this.amount, this.powerType, this.mustBeDistinct, this.isArtifactAgnostic) + DESCRIPTIONS[2] : DESCRIPTIONS[0];
        }


    }
}

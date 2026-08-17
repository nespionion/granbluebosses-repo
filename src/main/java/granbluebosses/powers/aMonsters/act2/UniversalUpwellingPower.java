package granbluebosses.powers.aMonsters.act2;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import granbluebosses.powers.BasePower;
import granbluebosses.powers.common.PhalanxPower;
import granbluebosses.utilInterfaces.OnOmenCanceledPower;

import static granbluebosses.GranblueBosses.makeID;

public class UniversalUpwellingPower extends BasePower implements OnOmenCanceledPower {

    private static final String NAME = UniversalUpwellingPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;
    public boolean justApplied;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UniversalUpwellingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, amount);
        this.justApplied = true;
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        this.justApplied = true;
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if (this.justApplied){
            this.justApplied = false;
            return;
        }

        this.applyRandomBuff();
        addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));

    }

    public void applyRandomBuff(){
        int randomInt = AbstractDungeon.monsterRng.random(8);
        switch (randomInt){
            case 0:
                addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));
                break;
            case 1:
                addToTop(new ApplyPowerAction(this.owner, this.owner, new ArtifactPower(this.owner, 1), 1));
                break;
            case 2:
                addToTop(new ApplyPowerAction(this.owner, this.owner, new MetallicizePower(this.owner, 1), 1));
                break;
            case 3:
                addToTop(new ApplyPowerAction(this.owner, this.owner, new RegenerateMonsterPower((AbstractMonster) this.owner, 1), 1));
                break;
            case 4:
                addToTop(new ApplyPowerAction(this.owner, this.owner, new PlatedArmorPower(this.owner, 2), 2));
                break;
            case 5:
                addToTop(new ApplyPowerAction(this.owner, this.owner, new BufferPower(this.owner, 1), 1));
                break;
            case 6:
                addToTop(new ApplyPowerAction(this.owner, this.owner, new ThornsPower(this.owner, 1), 1));
                break;
            case 7:
                addToTop(new ApplyPowerAction(this.owner, this.owner, new RegenPower(this.owner, 3), 3));
                break;
            case 8:
                addToTop(new ApplyPowerAction(this.owner, this.owner, new PhalanxPower(this.owner, 20), 20));
                break;
        }
    }

    @Override
    public void onOmenCancel() {
        addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));

        for (AbstractPower pow : this.owner.powers){
            if (pow.type == PowerType.BUFF && !pow.ID.equals(this.ID) && !pow.ID.equals(TorrentOfLifePower.POWER_ID)){
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, pow));
                break;
            }
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }

}
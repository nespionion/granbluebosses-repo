package granbluebosses.powers.incantedOmens;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import granbluebosses.powers.incantedOmens.AbstractIncantedOmen;
import granbluebosses.util.CardCheckUtils;

import static granbluebosses.GranblueBosses.makeID;

public class IncantedOmenHits extends AbstractIncantedOmen {

    public static final String NAME = IncantedOmenHits.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public DamageType damageType;

    public IncantedOmenHits(AbstractCreature owner) {
        this(owner, 1, 1, (DamageType) null);
    }

    public IncantedOmenHits(AbstractCreature owner, DamageType damageType) {
        this(owner, 1, 1, damageType);
    }

    public IncantedOmenHits(AbstractCreature owner, int numberOfHits) {
        this(owner, numberOfHits, 1, (DamageType) null);
    }

    public IncantedOmenHits(AbstractCreature owner, int numberOfHits, int dmgThreshold) {
        this(owner, numberOfHits, dmgThreshold, (DamageType) null);
    }

    public IncantedOmenHits(AbstractCreature owner, int numberOfHits, int dmgThreshold, DamageType damageType) {
        super(POWER_ID, owner, numberOfHits);

        this.amount2 = dmgThreshold;

        this.damageType = damageType;

        this.updateDescription();

    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if ((this.damageType == null || this.damageType == info.type) && this.amount2 <= damageAmount){
            this.reducePower(1);
//            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, this.amount));
        }

        return super.onAttacked(info, damageAmount);
    }

    @Override
    public String getConditionDescription() {
        if (Settings.language == Settings.GameLanguage.ZHS){
            return AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT ? DESCRIPTIONS[1] + CardCheckUtils.generateConditionStringCN(this.amount, this.amount2, this.damageType) : DESCRIPTIONS[0];
        } else {
            return AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT ? DESCRIPTIONS[1] + CardCheckUtils.generateConditionString(this.amount, this.amount2, this.damageType) : DESCRIPTIONS[0];
        }

    }

}
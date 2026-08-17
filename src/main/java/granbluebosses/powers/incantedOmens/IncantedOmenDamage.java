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

public class IncantedOmenDamage extends AbstractIncantedOmen {

    public static final String NAME = IncantedOmenDamage.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public DamageType damageType;

    public IncantedOmenDamage(AbstractCreature owner) {
        this(owner, 1, (DamageType) null);
    }

    public IncantedOmenDamage(AbstractCreature owner, DamageType damageType) {
        this(owner, 1, damageType);
    }


    public IncantedOmenDamage(AbstractCreature owner, int amount, DamageType damageType) {
        super(POWER_ID, owner, amount);

        this.damageType = damageType;

        this.updateDescription();

    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (this.damageType == null || this.damageType == info.type){
            this.reducePower(damageAmount);
//            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, this.amount));
        }

        return super.onAttacked(info, damageAmount);
    }

    @Override
    public String getConditionDescription() {
        if (Settings.language == Settings.GameLanguage.ZHS){
            return AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT ? DESCRIPTIONS[1] + CardCheckUtils.generateConditionStringCN(this.amount, this.damageType) : DESCRIPTIONS[0];
        } else {
            return AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT ? DESCRIPTIONS[1] + CardCheckUtils.generateConditionString(this.amount, this.damageType) : DESCRIPTIONS[0];
        }


    }

}
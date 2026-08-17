package granbluebosses.powers.aMonsters.act2.akasha;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import granbluebosses.monsters.act2.bosses.Akasha;
import granbluebosses.powers.BasePower;
import granbluebosses.util.CustomPowerType;

import static granbluebosses.GranblueBosses.makeID;

public class AkashaTimeWarpPower extends BasePower {

    public static final String NAME = AkashaTimeWarpPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = CustomPowerType.BOSS_MECHANIC;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AkashaTimeWarpPower(AbstractCreature owner, int amount) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, amount);

//        this.amount2 = 0;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
//        if (this.amount2 >= 1){
//            addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
//            addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
//            addToBot(new StunMonsterAction(AbstractDungeon.getRandomMonster(), this.owner));
//        }

//        if (this.amount2 >= 1){
//            for (AbstractPower pow : this.owner.powers){
//                if (!pow.ID.equals(POWER_ID)) pow.atEndOfTurn(true);
//            }
//
//            if (this.owner.isPlayer){
//                for (AbstractRelic r : AbstractDungeon.player.relics){
//                    r.onPlayerEndTurn();
//                }
//            }
//        }

        for (AbstractPower pow : this.owner.powers){
            if (!pow.ID.equals(POWER_ID)) {
                for (int i = 0; i < this.amount; i++){
                    pow.atEndOfTurn(this.owner.isPlayer);
                }
            }
        }

        if (this.owner.isPlayer){
            for (AbstractRelic r : AbstractDungeon.player.relics){
                r.onPlayerEndTurn();
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
//        if (this.amount2 >= 1){
//            addToBot(new PressEndTurnButtonAction());
//            this.reducePower(1);
//        }

//        if (this.amount2 >= 1){
//            for (AbstractPower pow : this.owner.powers){
//                if (!pow.ID.equals(POWER_ID)) pow.atStartOfTurn();
//            }
//
//            if (this.owner.isPlayer){
//                for (AbstractRelic r : AbstractDungeon.player.relics){
//                    r.onPlayerEndTurn();
//                }
//            }
//        }

        for (AbstractPower pow : this.owner.powers){
            if (!pow.ID.equals(POWER_ID)) {
                for (int i = 0; i < this.amount; i++){
                    pow.atStartOfTurn();
                }
            }
        }

        if (this.owner.isPlayer){
            for (AbstractRelic r : AbstractDungeon.player.relics){
                r.onPlayerEndTurn();
            }
        }
    }

//    @Override
//    public void atEndOfRound() {
//        super.atEndOfRound();
//        this.reducePower(1);
//    }

//    public void stackPower(int stackAmount) {
//        this.amount2 += stackAmount;
//        this.fontScale = 8.0F;
//        if (this.amount2 <= 0) this.amount2 = 0;
//    }
//
//    @Override
//    public void reducePower(int reduceAmount) {
//        this.amount2 -= reduceAmount;
//        this.fontScale = 8.0F;
//        if (this.amount2 <= 0) this.amount2 = 0;
//    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = this.amount <= 0 ?
                DESCRIPTIONS[3] :
                this.amount2 == 1 ?
                        DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] :
                        DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[2];
//        this.description = DESCRIPTIONS[3];
    }

}
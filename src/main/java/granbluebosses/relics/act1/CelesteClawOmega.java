package granbluebosses.relics.act1;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import granbluebosses.GranblueBosses;
import granbluebosses.powers.relic.CelesteEnmity;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class CelesteClawOmega extends BaseRelic  {
    public static final String RELIC_ID = GranblueBosses.makeID("CelesteClawOmega");

    public boolean isActive = false;

    public static int magicNumber = 2;

    public CelesteClawOmega() {
        super(
                RELIC_ID,       // ID
                "CelesteClawOmega",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX

        this.isActive = false;
    }

    public void atBattleStart() {
        this.isActive = false;
        this.addToBot(new AbstractGameAction() {
            public void update() {
                if (!CelesteClawOmega.this.isActive && AbstractDungeon.player.isBloodied) {
                    CelesteClawOmega.this.flash();
                    CelesteClawOmega.this.pulse = true;
                    AbstractDungeon.player.addPower(new CelesteEnmity(AbstractDungeon.player));
                    CelesteClawOmega.this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, CelesteClawOmega.this));
                    CelesteClawOmega.this.isActive = true;
                    AbstractDungeon.onModifyPower();
                }

                this.isDone = true;
            }
        });
    }

    public void onBloodied() {
        this.flash();
        this.pulse = true;
        if (!this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, new CelesteEnmity(p)));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.isActive = true;
            AbstractDungeon.player.hand.applyPowers();
        }

    }

    public void onNotBloodied() {
        if (this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
            this.addToBot(new RemoveSpecificPowerAction(p, p, CelesteEnmity.POWER_ID));
        }

        this.stopPulse();
        this.isActive = false;
        AbstractDungeon.player.hand.applyPowers();
    }

    public void onVictory() {
        this.pulse = false;
        this.isActive = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CelesteClawOmega();
    }

}

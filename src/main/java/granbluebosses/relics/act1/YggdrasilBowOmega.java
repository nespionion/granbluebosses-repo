package granbluebosses.relics.act1;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class YggdrasilBowOmega extends BaseRelic  {
    public static final String RELIC_ID = GranblueBosses.makeID("YggdrasilBowOmega");

    public static boolean isActive = false;

    public static int magicNumber = 1;

    public YggdrasilBowOmega() {
        super(
                RELIC_ID,       // ID
                "YggdrasilBowOmega",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX

    }

    public void atBattleStart() {
        this.isActive = false;
        this.addToBot(new AbstractGameAction() {
            public void update() {
                if (!YggdrasilBowOmega.this.isActive && AbstractDungeon.player.currentHealth == AbstractDungeon.player.maxHealth) {
                    YggdrasilBowOmega.this.flash();
                    YggdrasilBowOmega.this.pulse = true;
                    AbstractDungeon.player.addPower(new StrengthPower(AbstractDungeon.player, magicNumber));
                    YggdrasilBowOmega.this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, YggdrasilBowOmega.this));
                    YggdrasilBowOmega.this.isActive = true;
                    AbstractDungeon.onModifyPower();
                }

                this.isDone = true;
            }
        });
    }

    @Override
    public void onLoseHp(int damageAmount) {
        this.checkForStrength();
        super.onLoseHp(damageAmount);
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        this.checkForStrength();
        return super.onPlayerHeal(healAmount);
    }

    private void checkForStrength() {
        if (AbstractDungeon.player != null && !this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.currentHealth >= AbstractDungeon.player.maxHealth) {
            this.flash();
            this.pulse = true;
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.isActive = true;
            AbstractDungeon.player.hand.applyPowers();
        } else  if (AbstractDungeon.player != null && this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth) {
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, -magicNumber), -magicNumber));
        }
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
        return new YggdrasilBowOmega();
    }

}

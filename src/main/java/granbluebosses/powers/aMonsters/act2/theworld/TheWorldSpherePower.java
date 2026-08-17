package granbluebosses.powers.aMonsters.act2.theworld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import granbluebosses.monsters.IncantedOmenEnemy;
import granbluebosses.monsters.act2.normal.ArcarumJudgement;
import granbluebosses.powers.BasePower;
import granbluebosses.powers.incantedOmens.IncantedOmenPowersApplied;
import granbluebosses.util.CustomPowerType;
import granbluebosses.util.TextureLoader;

import static granbluebosses.GranblueBosses.makeID;

public class TheWorldSpherePower extends BasePower {
    public static final String POWER_ID = makeID("TheWorldSpherePower");
    private static final PowerType TYPE = CustomPowerType.BOSS_MECHANIC;
    private static final boolean TURN_BASED = false;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);

    public TheWorldSpherePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, amount, false, false);

        this.amount = amount;
        this.canGoNegative = false;

        if (this.amount >= 9) {
            this.amount = 9;
        }

        if (this.amount <= 0) {
            this.amount = 0;
        }

        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        this.updateDescription();

        this.loadSprites();
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1), 1));
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != null && info.owner != this.owner) {
            this.flash();
            this.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }

        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void stackPower(int stackAmount) {

        if (this.amount >= 9) {
            this.amount = 9;
        }

        if (this.amount <= 0) {
            this.amount = 0;
        }

        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        this.loadSprites();

        this.updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);

        if (this.amount >= 9) {
            this.amount = 9;
        }

        if (this.amount <= 0) {
            this.amount = 0;
        }

        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        this.loadSprites();

        this.updateDescription();
    }

    private void loadSprites() {
        String unPrefixed;
        if (this.amount >= 1 && this.amount <= 9){
            unPrefixed = "TheWorldSpherePower" + this.amount;
        } else {
            unPrefixed = "TheWorldSpherePower" + 1;
        }

        Texture normalTexture = TextureLoader.getPowerTexture(unPrefixed);
        Texture hiDefImage = TextureLoader.getHiDefPowerTexture(unPrefixed);
        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        } else {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        this.loadSprites();
    }

    @Override
    public void updateDescription() {
        this.name = powerStrings.NAME;
        this.description = this.DESCRIPTIONS[0] + this.amount + this.DESCRIPTIONS[1];
        super.updateDescription();
    }
}

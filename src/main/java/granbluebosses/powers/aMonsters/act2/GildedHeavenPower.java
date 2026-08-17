package granbluebosses.powers.aMonsters.act2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.GranblueBosses;
import granbluebosses.monsters.act2.elites.OdiousDesire;
import granbluebosses.powers.BasePower;
import granbluebosses.util.TextureLoader;

import static granbluebosses.GranblueBosses.makeID;

public class GildedHeavenPower extends BasePower {

    private static final String NAME = GildedHeavenPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GildedHeavenPower(AbstractCreature owner, int amount) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, owner, amount, false, false);

        this.amount = amount;

        this.updateDescription();

        this.loadSprites();
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        addToBot(new ApplyPowerAction(this.owner, this.owner, new GildedHeavenPower(this.owner, 3), 3));
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount < 0){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else if (this.amount > 10){
            this.amount = 10;
        }
        this.loadSprites();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount < 0){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else if (this.amount > 10){
            this.amount = 10;
        }
        this.loadSprites();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount < this.owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == DamageInfo.DamageType.NORMAL){
            addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        this.loadSprites();
    }

    private void loadSprites() {
        String unPrefixed = "GildedHeavenPower1";
        if (this.amount >= 1 && this.amount <= 10){
            unPrefixed = "GildedHeavenPower" + this.amount;
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

        this.updateDescription();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        if (this.owner instanceof OdiousDesire){
            GranblueBosses.logger.info("Gilded Heaven Stun Attempt");
            ((OdiousDesire) this.owner).onRemoveGildedHeaven();
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0];
    }

}
package granbluebosses.powers.aMonsters.act2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;
import granbluebosses.util.TextureLoader;
import granbluebosses.utilInterfaces.OnOmenCanceledPower;

import static granbluebosses.GranblueBosses.makeID;

public class ObservationPower extends BasePower {

    public static final String NAME = ObservationPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType POWER_TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private static PowerStrings getPowerStrings(String ID) {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }

    public static final PowerStrings powerStrings = getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ObservationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, POWER_TYPE, TURN_BASED, owner, owner, amount, false, false);

        this.amount = amount;

        this.updateDescription();

        this.loadSprites();
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        if (this.amount > 0)
            addToBot(new MakeTempCardInHandAction(new Dazed(), this.amount));
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (this.amount < 1){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }

        if (this.amount > 5){
            this.amount = 5;
        }

        this.loadSprites();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount < 1){
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }

        if (this.amount > 5){
            this.amount = 5;
        }

        this.loadSprites();
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        this.loadSprites();
    }

    private void loadSprites() {
        String unPrefixed = "ObservationPower1";
        if (this.amount == 1 || this.amount == 2 || this.amount == 3 || this.amount == 4 || this.amount == 5){
            unPrefixed = "ObservationPower" + this.amount;
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
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}
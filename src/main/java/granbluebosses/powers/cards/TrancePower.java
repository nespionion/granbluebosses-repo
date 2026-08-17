package granbluebosses.powers.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import granbluebosses.powers.BasePower;
import granbluebosses.util.CustomPowerType;
import granbluebosses.util.TextureLoader;

import static granbluebosses.GranblueBosses.makeID;

public class TrancePower extends BasePower {
    public static final String POWER_ID = makeID("RagnarokField");
    private static final PowerType TYPE = CustomPowerType.UNIQUE;
    private static final boolean TURN_BASED = false;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);

    public TrancePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, null, amount, false, false);

        this.amount = amount;

        this.updateDescription();

        this.loadSprites();

    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        return this.amount >=3 ? super.onAttackToChangeDamage(info, damageAmount) + 3 : super.onAttackToChangeDamage(info, damageAmount);
    }

    private void loadSprites() {
        String unPrefixed = "RagnarokField1";
        if (this.amount == 1 || this.amount == 2 || this.amount == 3){
            unPrefixed = "RagnarokField" + this.amount;
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

    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.name = powerStrings.NAME;
        this.description = this.DESCRIPTIONS[0];
    }
}

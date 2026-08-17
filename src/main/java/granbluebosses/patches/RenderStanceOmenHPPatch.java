package granbluebosses.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.badlogic.gdx.graphics.Color;
import granbluebosses.powers.stanceOmens.StanceOmen;

public class RenderStanceOmenHPPatch {

    private static final float HEALTH_BAR_HEIGHT;
    private static final float HEALTH_BAR_OFFSET_Y;
    private static final Color STANCE_OMEN_UNFULFILED_COLOR;
    private static final Color STANCE_OMEN_FULFILED_COLOR;

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "renderHealthText"
    )
    public static class RenderStanceOmenHPBarPatch {
        public static void Prefix(AbstractCreature __instance, SpriteBatch sb, float y, float ___hbYOffset, float ___blockOffset, float ___blockScale, float ___targetHealthBarWidth, float ___healthHideTimer) {
            if (!Settings.hideCombatElements && __instance.hasPower(StanceOmen.POWER_ID) && __instance.getPower(StanceOmen.POWER_ID).amount > 0 && __instance.currentBlock < 1) {
                float omenPercent = __instance.getPower(StanceOmen.POWER_ID).amount;
                float omenHealthBarWidth = __instance.hb.width * omenPercent * 0.01f;
                float yellowHPBarX = __instance.hb.cX - __instance.hb.width / 2.0F;
//                float yellowHPBarY = __instance.hb.cY - __instance.hb.height / 2.0F + ___hbYOffset;
                renderMonsterStanceOmenHP(__instance, sb, yellowHPBarX, y, omenHealthBarWidth);

//                reRenderHealthBarComponents(__instance, sb, x, y, ___blockOffset, ___blockScale, ___targetHealthBarWidth, ___healthHideTimer);
            }
        }
    }

    public static void renderMonsterStanceOmenHP(AbstractCreature creature, SpriteBatch sb, float x, float y, float omenHealthBarWidth){
        float omenPercent = creature.getPower(StanceOmen.POWER_ID).amount;
        float omenBarWidth;
        if (creature.currentHealth <= creature.maxHealth * omenPercent * 0.01f){
            sb.setColor(STANCE_OMEN_FULFILED_COLOR);
            omenBarWidth = creature.hb.width * ((float)creature.currentHealth / (float)creature.maxHealth);
        } else {
            sb.setColor(STANCE_OMEN_UNFULFILED_COLOR);
            omenBarWidth = omenHealthBarWidth;
        }
        if (creature.currentHealth > 0) {
            //noinspection SuspiciousNameCombination
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        }

        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, omenBarWidth, HEALTH_BAR_HEIGHT);
        //noinspection SuspiciousNameCombination
        sb.draw(ImageMaster.HEALTH_BAR_R, x + omenBarWidth, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);

    }

    static {
        HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
        HEALTH_BAR_OFFSET_Y = -28.0F * Settings.scale;
        STANCE_OMEN_UNFULFILED_COLOR = new Color(Color.YELLOW);
        STANCE_OMEN_FULFILED_COLOR = new Color(Color.WHITE);
    }
}

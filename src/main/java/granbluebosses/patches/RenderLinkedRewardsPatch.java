package granbluebosses.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.rewards.RewardItem;
import granbluebosses.GranblueBosses;
import granbluebosses.util.MonsterUtils;

import java.lang.reflect.Method;

public class RenderLinkedRewardsPatch {
    @SpirePatch(
            clz = RewardItem.class,
            method = "render"
    )
    public static class RewardItemRenderPatch {
        public static void Postfix(RewardItem __instance, SpriteBatch sb) {
            if (__instance.relic != null && MonsterUtils.isLinkedReward(__instance)) {
                try {
                    Method renderRelicLinkMethod = RewardItem.class.getDeclaredMethod("renderRelicLink", SpriteBatch.class);
                    renderRelicLinkMethod.setAccessible(true);
                    renderRelicLinkMethod.invoke(__instance, sb);
                } catch (NoSuchMethodException ex) {
                    GranblueBosses.logger.error("GranblueBosses renderRelicLink method not found.", ex);
                } catch (Exception ex) {
                    GranblueBosses.logger.error("Error while trying to invoke renderRelicLink method.", ex);
                }

            }
        }
    }
}

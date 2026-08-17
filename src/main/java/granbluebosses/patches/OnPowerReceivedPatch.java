package granbluebosses.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import granbluebosses.utilInterfaces.OnPowerReceivedPower;

import java.lang.reflect.Method;

public class OnPowerReceivedPatch {
    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = "update"
    )
    public static class OnPowerReceivedMethodCallPatch {
        public static void Prefix(ApplyPowerAction __instance, float ___duration,  float ___startingDuration) {
            if (__instance.target != null && !__instance.target.isDeadOrEscaped() && ___duration == ___startingDuration) {
                for(AbstractPower pow : __instance.target.powers) {
                    if (pow instanceof OnPowerReceivedPower) ((OnPowerReceivedPower) pow).onReceivePower(ReflectionHacks.getPrivate(__instance, ApplyPowerAction.class, "powerToApply"), __instance.target, __instance.source);
                }

            }
        }
    }
}

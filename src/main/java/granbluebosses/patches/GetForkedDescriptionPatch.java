package granbluebosses.patches;

import actlikeit.events.GetForked;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import granbluebosses.acts.Act1Skies;

//@SpirePatch.CONSTRUCTOR(
//        clz = GetForked.class
//)
//public class GetForkedDescriptionPatch {
//
//    public static void Postfix(GetForked __instance, boolean afterdoor, String ___body){
//        ReflectionHacks.setPrivate(__instance, GetForked.class, "body", ___body + Act1Skies.TEXT[3]);
//
//    }
//
//}


// see "protected attribute captures and reflectionhacks" for how to edit AbstactEvent.body

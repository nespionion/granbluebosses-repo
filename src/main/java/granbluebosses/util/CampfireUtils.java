package granbluebosses.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import granbluebosses.relics.odious.*;

public class CampfireUtils {



    public static boolean isExorcismPossible(){
        boolean relicToExorcise = false;

        relicToExorcise = (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(OdiousDemonspear.ID) && AbstractDungeon.player.getRelic(OdiousDemonspear.ID).counter > 0);

        relicToExorcise = relicToExorcise || (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(OdiousTerrorbow.ID) && AbstractDungeon.player.getRelic(OdiousTerrorbow.ID).counter > 0);

        relicToExorcise = relicToExorcise || (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(OdiousDemonedge.ID) && AbstractDungeon.player.getRelic(OdiousDemonedge.ID).counter > 0);

        relicToExorcise = relicToExorcise || (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(OdiousSealhammer.ID) && AbstractDungeon.player.getRelic(OdiousSealhammer.ID).counter > 0);

        relicToExorcise = relicToExorcise || (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(OdiousBlightrifle.ID) && AbstractDungeon.player.getRelic(OdiousBlightrifle.ID).counter > 0);

        relicToExorcise = relicToExorcise || (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(OdiousCodex.ID) && AbstractDungeon.player.getRelic(OdiousCodex.ID).counter > 0);


        return relicToExorcise;
    }
}

package granbluebosses.powers;

import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import granbluebosses.util.Sounds;
import granbluebosses.vfx.CustomVFX;

public class OmenUtils {
    public static void onPrepOmenSFX(AbstractCreature omenCreature){
        AbstractGameEffect omenPrepAnim1 =
                new VfxBuilder(CustomVFX.OMEN_PREP_TEXTURE, omenCreature.hb.cX, omenCreature.hb.cY, 0.4f)
                        .scale(0.0f, 2.0f, VfxBuilder.Interpolations.SWING)
                        .setColor(Color.RED)
                        .setScale(omenCreature.hb.width / Settings.xScale)
                        .fadeIn(0.4f)
                        .playSoundAt(0.0f, Sounds.COMMON_OMEN_PREP_SOUND)
                        .build()
                ;
        AbstractGameEffect omenPrepAnim2 =
                new VfxBuilder(CustomVFX.OMEN_PREP_TEXTURE, omenCreature.hb.cX, omenCreature.hb.cY, 0.4f)
                        .scale(2.0f, 0.0f, VfxBuilder.Interpolations.SWING)
                        .setColor(Color.RED)
                        .setScale(omenCreature.hb.width / Settings.xScale)
                        .fadeOut(0.4f)
                        .build()
                ;

        AbstractDungeon.actionManager.addToBottom(new VFXAction(omenPrepAnim1));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(omenPrepAnim2));
    }

    public static void onCancelOmenSFX(AbstractCreature omenCreature){
        AbstractGameEffect omenCancelAnim =
                new VfxBuilder(CustomVFX.OMEN_CANCEL_TEXTURE, omenCreature.hb.cX, omenCreature.hb.cY, 0.5f)
                        .scale(0.6f, 2.0f, VfxBuilder.Interpolations.SWING)
                        .setColor(Color.WHITE)
                        .setScale(omenCreature.hb.width / Settings.xScale)
                        .fadeOut(0.6f)
                        .playSoundAt(0.0f, Sounds.COMMON_OMEN_CANCEL_SOUND)
                        .build()
                ;
        AbstractDungeon.actionManager.addToBottom(new VFXAction(omenCancelAnim));
    }
}

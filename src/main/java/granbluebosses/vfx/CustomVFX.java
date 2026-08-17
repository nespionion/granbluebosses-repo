package granbluebosses.vfx;

import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import granbluebosses.GranblueBosses;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class CustomVFX {
    public static Texture OMEN_PREP_TEXTURE = loadImage(GranblueBosses.imagePath("vfx/omen_prep.png"));;
    public static Texture OMEN_CANCEL_TEXTURE = loadImage(GranblueBosses.imagePath("vfx/omen_break.png"));;


}

package granbluebosses.util.primalcards;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;

import static granbluebosses.GranblueBosses.characterPath;
import static granbluebosses.util.primalcards.PrimalColor.GBF_PRIMAL_COLOR;

public class PrimalUtils {
    //Character card images
    private static final String BG_ATTACK = characterPath("gbfprimal/cardback/bg_attack.png");
    private static final String BG_ATTACK_P = characterPath("gbfprimal/cardback/bg_attack_p.png");
    private static final String BG_SKILL = characterPath("gbfprimal/cardback/bg_skill.png");
    private static final String BG_SKILL_P = characterPath("gbfprimal/cardback/bg_skill_p.png");
    private static final String BG_POWER = characterPath("gbfprimal/cardback/bg_power.png");
    private static final String BG_POWER_P = characterPath("gbfprimal/cardback/bg_power_p.png");
    private static final String ENERGY_ORB = characterPath("gbfprimal/cardback/energy_orb.png");
    private static final String ENERGY_ORB_P = characterPath("gbfprimal/cardback/energy_orb_p.png");
    private static final String SMALL_ORB = characterPath("gbfprimal/cardback/small_orb.png");


    private static final Color PRIMAL_EVERYTHING_COLOR = new Color(0.53f, 0.81f, 0.93f, 1.0f);

    public static void registerPrimalColor(){
        BaseMod.addColor(GBF_PRIMAL_COLOR, PRIMAL_EVERYTHING_COLOR,
                BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
    }
}

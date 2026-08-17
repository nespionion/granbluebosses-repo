package granbluebosses.acts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import granbluebosses.monsters.act1.normal.Celeste2;
import granbluebosses.monsters.act2.bosses.Akasha;
import granbluebosses.monsters.act2.bosses.TheWorld;
import granbluebosses.monsters.act2.elites.OdiousLiberation;
import granbluebosses.monsters.act2.elites.OdiousProsperity;
import granbluebosses.monsters.act4.elites.Wamdus;
import granbluebosses.monsters.act4.elites.Wilnas;
import granbluebosses.GranblueBosses;

public class Act2ArcarumScene extends AbstractScene {
    private static final String ATLAS_URL = GranblueBosses.imagePath("scenes/act2/act2skies.atlas");
    private final TextureAtlas.AtlasRegion bg;
    private final TextureAtlas.AtlasRegion arcarumFight;
    private final TextureAtlas.AtlasRegion odiousFireFight;
    private final TextureAtlas.AtlasRegion odiousWaterFight;
    private final TextureAtlas.AtlasRegion odiousEarthFight;
    private final TextureAtlas.AtlasRegion odiousWindFight;
    private final TextureAtlas.AtlasRegion odiousLightFight;
    private final TextureAtlas.AtlasRegion odiousDarkFight;
    private final TextureAtlas.AtlasRegion slimeFight;
    private final TextureAtlas.AtlasRegion akashaFight;
    private final TextureAtlas.AtlasRegion arcarum2Fight;
    private final TextureAtlas.AtlasRegion campfireGlow;
    private final TextureAtlas.AtlasRegion campfireKindling;
    private TextureAtlas.AtlasRegion battleRoom;
    private Color overlayColor;
    private Color tmpColor;
    private Color whiteColor;
    private AbstractRoom currRoom = null;
    private String currMonsterId = null;

    public Act2ArcarumScene() {
        super(ATLAS_URL);
        this.overlayColor = new Color(1.0F, 1.0F, 1.0F, 0.2F);
        this.tmpColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.whiteColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.bg = this.atlas.findRegion("bg");

        TextureAtlas campfireAtlas = new TextureAtlas(Gdx.files.internal("bottomScene/scene.atlas"));
        this.campfireGlow = campfireAtlas.findRegion("mod/campfireGlow");
        this.campfireKindling = campfireAtlas.findRegion("mod/campfireKindling");

        this.arcarumFight = this.atlas.findRegion("world_1_bg");
        this.arcarum2Fight = this.atlas.findRegion("world_2_bg");
        this.odiousFireFight = this.atlas.findRegion("odious_fire_bg");
        this.odiousWaterFight = this.atlas.findRegion("odious_water_bg");
        this.odiousEarthFight = this.atlas.findRegion("odious_earth_bg");
        this.odiousWindFight = this.atlas.findRegion("odious_wind_bg");
        this.odiousLightFight = this.atlas.findRegion("odious_light_bg");
        this.odiousDarkFight = this.atlas.findRegion("odious_dark_bg");
        this.akashaFight = this.atlas.findRegion("akasha_bg");
        this.slimeFight = this.atlas.findRegion("slime_bg");


        this.ambianceName = "AMBIANCE_BEYOND";
        this.fadeInAmbiance();
    }

    @Override
    public void renderCombatRoomBg(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        this.randomizeScene();
        this.renderAtlasRegionIf(sb, this.bg, true);
        this.renderAtlasRegionIf(sb, this.battleRoom, true);

    }

    @Override
    public void nextRoom(AbstractRoom room) {
        this.currMonsterId = null;
        super.nextRoom(room);
    }

    @Override
    public void renderCombatRoomFg(SpriteBatch sb) {

    }

    public void renderSpecificForeground(SpriteBatch sb, String id){
        this.renderAtlasRegionIf(sb, this.atlas.findRegion(id), true);
    }

    @Override
    public void renderCampfireRoom(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        this.renderAtlasRegionIf(sb, this.campfireBg, true);
        sb.setBlendFunction(770, 1);
        this.whiteColor.a = MathUtils.cosDeg((float)(System.currentTimeMillis() / 3L % 360L)) / 10.0F + 0.8F;
        sb.setColor(this.whiteColor);
        this.renderQuadrupleSize(sb, this.campfireGlow, !CampfireUI.hidden);
        sb.setBlendFunction(770, 771);
        sb.setColor(Color.WHITE);
        this.renderAtlasRegionIf(sb, this.campfireKindling, true);
    }

    @Override
    public void randomizeScene() {
        if (this.currRoom == null || !this.isMonsterPresent(AbstractDungeon.getCurrRoom(), this.currMonsterId)){
            this.currRoom = AbstractDungeon.getCurrRoom();
        }
        if (this.currRoom instanceof MonsterRoom){
//            GranblueBosses.logger.info("Monster room found");
            if (this.currMonsterId != null && this.isMonsterPresent(this.currRoom, this.currMonsterId)){
                return;
            }

            // Arcarum / Hard fight rooms ======================================================================
            if (this.isArcarumPresent(this.currRoom)){
                GranblueBosses.logger.info("Arcarum battle found. Loading Arcarum room");
                this.battleRoom = this.arcarumFight;


                // Elites / Odious rooms ======================================================================
            } else if (this.isMonsterPresent(this.currRoom, OdiousProsperity.MONSTER_ID)){
                GranblueBosses.logger.info("Odious Prosperity battle found. Loading Odious Fire room");
                this.battleRoom = this.odiousFireFight;
            } else if (this.isMonsterPresent(this.currRoom, OdiousLiberation.MONSTER_ID)){
                GranblueBosses.logger.info("Odious Liberation battle found. Loading Odious Wind room");
                this.battleRoom = this.odiousWindFight;


                // Bosses
            } else if (this.isMonsterPresent(this.currRoom, TheWorld.MONSTER_ID)){
                GranblueBosses.logger.info("The World battle found. Loading Arcarum room");
                this.battleRoom = this.arcarumFight;
            } else if (this.isMonsterPresent(this.currRoom, Akasha.MONSTER_ID)){
                GranblueBosses.logger.info("Akasha battle found. Loading Akasha's room");
                this.battleRoom = this.akashaFight;


                // Slime / Easy fight / Other rooms ======================================================================
            } else {
                GranblueBosses.logger.info("No unique bg monster found");
                this.battleRoom = this.slimeFight;
            }

            if (!AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty()){
                GranblueBosses.logger.info("Storing monster ID.");
                this.currMonsterId = AbstractDungeon.getCurrRoom().monsters.monsters.get(0).id;
            }
        } else {
            GranblueBosses.logger.info("Not a monster room.");
            this.battleRoom = this.bg;
        }


        // This is for safety reasons: this.battleRoom needs to NOT be null,
        // so I am insuring it has some value, even if not the correct one
        if (this.battleRoom == null){
            GranblueBosses.logger.info("WARNING: The room is null incorrectly.");
            this.battleRoom = this.bg;
        }
    }

    public boolean isMonsterPresent(AbstractRoom room, String monsterID) {
        if (monsterID == null || room == null){
            // GranblueBosses.logger.info("A parameter of isMonsterPresent is null.");
            return false;
        }
        if (room.monsters == null){
            GranblueBosses.logger.info("This room cannot contain monsters.");
            return false;
        }
        if (room.monsters.monsters.isEmpty()){
            GranblueBosses.logger.info("There are no monsters in this room.");
            return false;
        }
        for (String monsterId : room.monsters.getMonsterNames()){
            if (monsterId.equals(monsterID)){
//                GranblueBosses.logger.info("Monster was found.");
                return true;
            }
        }
        GranblueBosses.logger.info("Monster ID not found among monsters in this room.");
        return false;
    }

    public boolean isArcarumPresent(AbstractRoom room) {
        if (!(room instanceof MonsterRoom)){
            // GranblueBosses.logger.info("A parameter of isMonsterPresent is null.");
            return false;
        }
        if (room.monsters == null || room.monsters.monsters == null){
            GranblueBosses.logger.info("This room cannot contain monsters.");
            return false;
        }
        if (room.monsters.monsters.isEmpty()){
            GranblueBosses.logger.info("There are no monsters in this room.");
            return false;
        }
        for (String monsterId : room.monsters.getMonsterNames()){
            if (monsterId.contains("Arcarum")){
//                GranblueBosses.logger.info("Monster was found.");
                return true;
            }
        }
        GranblueBosses.logger.info("Arcarum not found among monsters in this room.");
        return false;
    }
}

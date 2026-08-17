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
import granbluebosses.GranblueBosses;
import granbluebosses.monsters.act1.bosses.GrandOrder;
import granbluebosses.monsters.act1.bosses.ProtoBaha;
import granbluebosses.monsters.act1.elites.Alexiel;
import granbluebosses.monsters.act1.elites.Europa;
import granbluebosses.monsters.act1.elites.Grimnir;
import granbluebosses.monsters.act1.elites.Shiva;
import granbluebosses.monsters.act1.normal.*;

public class Act1SkiesScene extends AbstractScene {
    private static final String ATLAS_URL = GranblueBosses.imagePath("scenes/act1/act1skies.atlas");
    private final TextureAtlas.AtlasRegion bg;
    private final TextureAtlas.AtlasRegion celesteFight;
    private final TextureAtlas.AtlasRegion colosusFight;
    private final TextureAtlas.AtlasRegion leviathanFight;
    private final TextureAtlas.AtlasRegion luminieraFight;
    private final TextureAtlas.AtlasRegion tiamatFight;
    private final TextureAtlas.AtlasRegion yggdrasilFight;
    private final TextureAtlas.AtlasRegion protoBaha1Fight;
    private final TextureAtlas.AtlasRegion protoBaha2Fight;
    private final TextureAtlas.AtlasRegion grandOrder1Fight;
    private final TextureAtlas.AtlasRegion grandOrder2Fight;
    private final TextureAtlas.AtlasRegion grandOrder3Fight;
    private final TextureAtlas.AtlasRegion alexFight;
    private final TextureAtlas.AtlasRegion europaFight;
    private final TextureAtlas.AtlasRegion grimnirFight;
    private final TextureAtlas.AtlasRegion shivaFight;
    private final TextureAtlas.AtlasRegion campfireGlow;
    private final TextureAtlas.AtlasRegion campfireKindling;
    private TextureAtlas.AtlasRegion battleRoom;
    private Color overlayColor;
    private Color tmpColor;
    private Color whiteColor;
    private AbstractRoom currRoom = null;
    private String currMonsterId = null;

    public Act1SkiesScene() {
        super(ATLAS_URL);
        this.overlayColor = new Color(1.0F, 1.0F, 1.0F, 0.2F);
        this.tmpColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.whiteColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.bg = this.atlas.findRegion("bg");

        TextureAtlas campfireAtlas = new TextureAtlas(Gdx.files.internal("bottomScene/scene.atlas"));
        this.campfireGlow = campfireAtlas.findRegion("mod/campfireGlow");
        this.campfireKindling = campfireAtlas.findRegion("mod/campfireKindling");

        this.celesteFight = this.atlas.findRegion("fight-cropped-celeste");
        this.colosusFight = this.atlas.findRegion("fight-cropped-colossus");
        this.leviathanFight = this.atlas.findRegion("fight-cropped-leviathan");
        this.luminieraFight = this.atlas.findRegion("fight-cropped-luminiera");
        this.tiamatFight = this.atlas.findRegion("fight-cropped-tiamat");
        this.yggdrasilFight = this.atlas.findRegion("fight-cropped-yggy");
        this.protoBaha1Fight = this.atlas.findRegion("fight-cropped-protobaha1");
        this.protoBaha2Fight = this.atlas.findRegion("fight-cropped-protobaha2");
        this.grandOrder1Fight = this.atlas.findRegion("fight-cropped-grandorder1");
        this.grandOrder2Fight = this.atlas.findRegion("fight-cropped-grandorder2");
        this.grandOrder3Fight = this.atlas.findRegion("fight-cropped-grandorder3");
        this.alexFight = this.atlas.findRegion("fight-cropped-alex");
        this.europaFight = this.atlas.findRegion("fight-cropped-europa");
        this.grimnirFight = this.atlas.findRegion("fight-cropped-grimnir");
        this.shivaFight = this.atlas.findRegion("fight-cropped-shiva");


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

            if (this.isMonsterPresent(this.currRoom, Celeste.MONSTER_ID)
                    || this.isMonsterPresent(this.currRoom, Celeste2.MONSTER_ID) ){
                GranblueBosses.logger.info("Celeste battle found. Loading Celeste room");
                this.battleRoom = this.celesteFight;
            } else if (this.isMonsterPresent(this.currRoom, Colossus.MONSTER_ID)
                    || this.isMonsterPresent(this.currRoom, Colossus2.MONSTER_ID)
                    || this.isMonsterPresent(this.currRoom, Baal.MONSTER_ID)){
                GranblueBosses.logger.info("Colossus battle found. Loading Colossus room");
                this.battleRoom = this.colosusFight;
            } else if (this.isMonsterPresent(this.currRoom, Leviathan.MONSTER_ID)
                    || this.isMonsterPresent(this.currRoom, Leviathan2.MONSTER_ID)
                    || this.isMonsterPresent(this.currRoom, Grani.MONSTER_ID) ){
                GranblueBosses.logger.info("Levi battle found. Loading Levi room");
                this.battleRoom = this.leviathanFight;
            } else if (this.isMonsterPresent(this.currRoom, Luminiera.MONSTER_ID)
                    || this.isMonsterPresent(this.currRoom, Luminiera2.MONSTER_ID) ){
                GranblueBosses.logger.info("Lumi battle found. Loading Lumi room");
                this.battleRoom = this.luminieraFight;
            } else if (this.isMonsterPresent(this.currRoom, Tiamat.MONSTER_ID)
                    || this.isMonsterPresent(this.currRoom, Tiamat2.MONSTER_ID)
                    || this.isMonsterPresent(this.currRoom, Garuda.MONSTER_ID) ){
                GranblueBosses.logger.info("Tiamat battle found. Loading Tiamat room");
                this.battleRoom = this.tiamatFight;
            } else if (this.isMonsterPresent(this.currRoom, Yggdrasil.MONSTER_ID)
                    || this.isMonsterPresent(this.currRoom, Yggdrasil2.MONSTER_ID)
                    || this.isMonsterPresent(this.currRoom, Athena.MONSTER_ID) ){
                GranblueBosses.logger.info("Yggy battle found. Loading Yggy room");
                this.battleRoom = this.yggdrasilFight;
            } else if (this.isMonsterPresent(this.currRoom, Shiva.MONSTER_ID) ){
                GranblueBosses.logger.info("Shiva battle found. Loading Shiva room");
                this.battleRoom = this.shivaFight;
            } else if (this.isMonsterPresent(this.currRoom, Grimnir.MONSTER_ID) ){
                GranblueBosses.logger.info("Grimnir battle found. Loading Grimnir room");
                this.battleRoom = this.grimnirFight;
            } else if (this.isMonsterPresent(this.currRoom, Alexiel.MONSTER_ID) ){
                GranblueBosses.logger.info("Alex battle found. Loading Alex room");
                this.battleRoom = this.alexFight;
            } else if (this.isMonsterPresent(this.currRoom, Europa.MONSTER_ID) ){
                GranblueBosses.logger.info("Europa battle found. Loading Europa room");
                this.battleRoom = this.europaFight;
            } else if (this.isMonsterPresent(this.currRoom, ProtoBaha.MONSTER_ID) ){
                GranblueBosses.logger.info("PBHL battle found. Loading PBHL room");
                this.battleRoom = this.protoBaha1Fight;
            } else if (this.isMonsterPresent(this.currRoom, GrandOrder.MONSTER_ID) ){
                GranblueBosses.logger.info("GOHL battle found. Loading GOHL room");
                this.battleRoom = this.grandOrder1Fight;
            } else {
                GranblueBosses.logger.info("No unique bg monster found");
                this.battleRoom = this.bg;
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
}

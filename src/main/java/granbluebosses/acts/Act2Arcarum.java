package granbluebosses.acts;

import actlikeit.dungeons.CustomDungeon;
import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import granbluebosses.GranblueBosses;
import granbluebosses.config.ConfigMenu;
import granbluebosses.events.conditions.SidedWithSkiesCondition;
import granbluebosses.events.conditions.SidedWithStarsCondition;
import granbluebosses.events.conditions.TwinElementsCondition;
import granbluebosses.events.skyevents.ScalesOfDominionEvent;
import granbluebosses.events.skyevents.act1.ThreoEahtaTrainingEvent;
import granbluebosses.events.entry.Act2Entry;
import granbluebosses.events.generic.*;
import granbluebosses.events.skyevents.act2.IlsaEvent;
import granbluebosses.events.skyevents.act2.JewelResortCasinoEvent;
import granbluebosses.events.skyevents.act2.PortraitTienEvent;
import granbluebosses.events.starevents.TwinElementsEvent;
import granbluebosses.events.starevents.act2.AstralBlessingEvent;
import granbluebosses.monsters.act2.bosses.Akasha;
import granbluebosses.monsters.act2.bosses.TheWorld;
import granbluebosses.monsters.act2.elites.*;
import granbluebosses.monsters.act2.normal.*;
import granbluebosses.util.Sounds;

import java.util.ArrayList;

public class Act2Arcarum extends CustomDungeon {
    public static final String ID = GranblueBosses.makeID("Act2Arcarum"); //From the main mod file for best practices
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;
    public static final String NAME = TEXT[0];

    public Act2Arcarum() {
        super(NAME, ID, "images/ui/event/panel.png", false, 2, 4,2);

//        this.defineMonsters();
//        GranblueBosses.logger.info("Generated Encounters");


//        this.defineBossEnemies();
//        GranblueBosses.logger.info("Generated Boss Encounters");

//        this.generateWeakEnemies(3);
//        this.generateStrongEnemies(6);
//        this.generateElites(4);

        if (ConfigMenu.enableDMCAMusic){
            this.setMainMusic(Sounds.MUSIC_ACT2_MAIN);
        } else {
            this.setMainMusic(TheCity.id);
        }

        if (ConfigMenu.enableStartOfActEvents){
            if (ConfigMenu.modestyFilter){
                this.onEnterEvent(Act2Entry.class);
            } else {
                this.onEnterEvent(Act2Entry.class);
            }
        }

        this.addTempMusic(Sounds.MUSIC_ACT2_BATTLE, Sounds.MUSIC_ACT2_BATTLE);
        this.addTempMusic(Sounds.MUSIC_ACT2_ELITE_FIRE, Sounds.MUSIC_ACT2_ELITE_FIRE);
        this.addTempMusic(Sounds.MUSIC_ACT2_ELITE_WATER, Sounds.MUSIC_ACT2_ELITE_WATER);
        this.addTempMusic(Sounds.MUSIC_ACT2_ELITE_EARTH, Sounds.MUSIC_ACT2_ELITE_EARTH);
        this.addTempMusic(Sounds.MUSIC_ACT2_ELITE_WIND, Sounds.MUSIC_ACT2_ELITE_WIND);
        this.addTempMusic(Sounds.MUSIC_ACT2_ELITE_LIGHT, Sounds.MUSIC_ACT2_ELITE_LIGHT);
        this.addTempMusic(Sounds.MUSIC_ACT2_ELITE_DARK, Sounds.MUSIC_ACT2_ELITE_DARK);
        this.addTempMusic(Sounds.MUSIC_ACT2_AKASHA_P_1, Sounds.MUSIC_ACT2_AKASHA_P_1);
        this.addTempMusic(Sounds.MUSIC_ACT2_AKASHA_P_2, Sounds.MUSIC_ACT2_AKASHA_P_2);
        this.addTempMusic(Sounds.MUSIC_ACT2_AKASHA_P_3, Sounds.MUSIC_ACT2_AKASHA_P_3);
        this.addTempMusic(Sounds.MUSIC_ACT2_WORLD_P_1, Sounds.MUSIC_ACT2_WORLD_P_1);
        this.addTempMusic(Sounds.MUSIC_ACT2_WORLD_P_2, Sounds.MUSIC_ACT2_WORLD_P_2);
    }

    public Act2Arcarum(CustomDungeon cd, AbstractPlayer p, ArrayList<String> emptyList) {
        super(cd, p, emptyList);
    }
    public Act2Arcarum(CustomDungeon cd, AbstractPlayer p, SaveFile sf) {
        super(cd, p, sf);
    }

    protected static void defineWeakMonster(){
        BaseMod.addMonster(GoldSlime.MONSTER_ID, () -> new GoldSlime());
        BaseMod.addMonsterEncounter(ID, new MonsterInfo(GoldSlime.MONSTER_ID, 6));
        BaseMod.addMonster(SilverSlime.MONSTER_ID, () -> new SilverSlime());
        BaseMod.addMonsterEncounter(ID, new MonsterInfo(SilverSlime.MONSTER_ID, 6));


//            addMonster(GranblueBosses.makeID("Colossus"), () -> new Colossus());
//            addMonster(GranblueBosses.makeID("Tiamat"), () -> new Tiamat());
//            addMonster(GranblueBosses.makeID("Luminiera"), () -> new Luminiera());
//            addMonster(GranblueBosses.makeID("Celeste"), () -> new Celeste());
//            addMonster(GranblueBosses.makeID("Leviathan"), () -> new Leviathan());
//            addMonster(GranblueBosses.makeID("Yggdrasil"), () -> new Yggdrasil());
    }

    protected static void defineStrongMonster(){
        BaseMod.addMonster(ArcarumDeath.MONSTER_ID, () -> new ArcarumDeath());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(ArcarumDeath.MONSTER_ID, 10));
        BaseMod.addMonster(ArcarumDevil.MONSTER_ID, () -> new ArcarumDevil());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(ArcarumDevil.MONSTER_ID, 10));
        BaseMod.addMonster(ArcarumHangedMan.MONSTER_ID, () -> new ArcarumHangedMan());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(ArcarumHangedMan.MONSTER_ID, 10));
        BaseMod.addMonster(ArcarumJudgement.MONSTER_ID, () -> new ArcarumJudgement());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(ArcarumJudgement.MONSTER_ID, 10));
        BaseMod.addMonster(ArcarumJustice.MONSTER_ID, () -> new ArcarumJustice());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(ArcarumJustice.MONSTER_ID, 10));
        BaseMod.addMonster(ArcarumMoon.MONSTER_ID, () -> new ArcarumMoon());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(ArcarumMoon.MONSTER_ID, 10));
        BaseMod.addMonster(ArcarumStar.MONSTER_ID, () -> new ArcarumStar());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(ArcarumStar.MONSTER_ID, 10));
        BaseMod.addMonster(ArcarumSun.MONSTER_ID, () -> new ArcarumSun());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(ArcarumSun.MONSTER_ID, 10));
        BaseMod.addMonster(ArcarumTemperance.MONSTER_ID, () -> new ArcarumTemperance());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(ArcarumTemperance.MONSTER_ID, 10));
        BaseMod.addMonster(ArcarumTower.MONSTER_ID, () -> new ArcarumTower());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(ArcarumTower.MONSTER_ID, 10));

    }

    protected static void defineEliteMonster(){
        GranblueBosses.logger.info("Odious Fire");
        BaseMod.addMonster(OdiousProsperity.MONSTER_ID, () -> new OdiousProsperity());
        BaseMod.addEliteEncounter(ID, new MonsterInfo(OdiousProsperity.MONSTER_ID, 10));

        GranblueBosses.logger.info("Odious Wind");
        BaseMod.addMonster(OdiousLiberation.MONSTER_ID, () -> new OdiousLiberation());
        BaseMod.addEliteEncounter(ID, new MonsterInfo(OdiousLiberation.MONSTER_ID, 10));

        GranblueBosses.logger.info("Odious Water");
        BaseMod.addMonster(OdiousMortality.MONSTER_ID, () -> new OdiousMortality());
        BaseMod.addEliteEncounter(ID, new MonsterInfo(OdiousMortality.MONSTER_ID, 10));

        GranblueBosses.logger.info("Odious Earth");
        BaseMod.addMonster(OdiousDesire.MONSTER_ID, () -> new OdiousDesire());
        BaseMod.addEliteEncounter(ID, new MonsterInfo(OdiousDesire.MONSTER_ID, 10));

        GranblueBosses.logger.info("Odious Light");
        BaseMod.addMonster(OdiousKnowledge.MONSTER_ID, () -> new OdiousKnowledge());
        BaseMod.addEliteEncounter(ID, new MonsterInfo(OdiousKnowledge.MONSTER_ID, 10));

        GranblueBosses.logger.info("Odious Dark");
        BaseMod.addMonster(OdiousSanctity.MONSTER_ID, () -> new OdiousSanctity());
        BaseMod.addEliteEncounter(ID, new MonsterInfo(OdiousSanctity.MONSTER_ID, 10));
    }

    public static void defineMonsters() {
        defineWeakMonster();
        GranblueBosses.logger.info("Generated Weak Encounters");
        defineStrongMonster();
        GranblueBosses.logger.info("Generated Strong Encounters");
        defineEliteMonster();
        GranblueBosses.logger.info("Generated Elite Encounters");
        generateBoss();
        GranblueBosses.logger.info("Generated Boss Encounters");
    }

    @Override
    protected void makeMap() {
        super.makeMap();
    }

    @Override
    protected void generateMonsters() {
        super.generateMonsters();
    }

    //    @Override
//    protected void initializeBoss() {
////        this.addBoss(GranblueBosses.makeID("GrandOrder"), "Grand Order", () -> new GrandOrder(), GrandOrder.MAP_ICON, GrandOrder.OUTLINE);
////        this.addBoss(GranblueBosses.makeID("ProtoBaha"), "Proto Bahamut", () -> new ProtoBaha(), ProtoBaha.MAP_ICON, ProtoBaha.OUTLINE);
//
////        BaseMod.addMonster(ProtoBaha.MONSTER_ID, () -> new ProtoBaha());
////        BaseMod.addBoss(ID, ProtoBaha.MONSTER_ID, ProtoBaha.MAP_ICON, ProtoBaha.OUTLINE);
////        BaseMod.addMonster(GrandOrder.MONSTER_ID, () -> new GrandOrder());
////        BaseMod.addBoss(ID, GrandOrder.MONSTER_ID, GrandOrder.MAP_ICON, GrandOrder.OUTLINE);
//
//        super.initializeBoss();
//
////        bossList.add(GranblueBosses.makeID("GrandOrder"));
////        bossList.add(GranblueBosses.makeID("ProtoBaha"));
////        Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
//    }

    public static void generateBoss(){
        BaseMod.addMonster(Akasha.MONSTER_ID, () -> new Akasha());
        BaseMod.addBoss(ID, Akasha.MONSTER_ID, Akasha.MAP_ICON, Akasha.OUTLINE);

        BaseMod.addMonster(TheWorld.MONSTER_ID, () -> new TheWorld());
        BaseMod.addBoss(ID, TheWorld.MONSTER_ID, TheWorld.MAP_ICON, TheWorld.OUTLINE);
    }

    public static void addEvents() {

        BaseMod.addEvent(FireEvent.EVENT_ID, FireEvent.class, ID);
        BaseMod.addEvent(LushEvent.EVENT_ID, LushEvent.class, ID);
        BaseMod.addEvent(RiverEvent.EVENT_ID, RiverEvent.class, ID);
        BaseMod.addEvent(RuinsEvent.EVENT_ID, RuinsEvent.class, ID);
        BaseMod.addEvent(Shrine6Event.EVENT_ID, Shrine6Event.class, ID);
        BaseMod.addEvent(Shrine10Event.EVENT_ID, Shrine10Event.class, ID);
        BaseMod.addEvent(YatimaEvent.EVENT_ID, YatimaEvent.class, ID);

        BaseMod.addEvent(new AddEventParams.Builder(IlsaEvent.EVENT_ID, IlsaEvent.class).dungeonID(ID).bonusCondition(new SidedWithSkiesCondition()).create());
        BaseMod.addEvent(new AddEventParams.Builder(JewelResortCasinoEvent.EVENT_ID, JewelResortCasinoEvent.class).dungeonID(ID).bonusCondition(new SidedWithSkiesCondition()).create());
        BaseMod.addEvent(new AddEventParams.Builder(PortraitTienEvent.EVENT_ID, PortraitTienEvent.class).dungeonID(ID).bonusCondition(new SidedWithSkiesCondition()).create());
        BaseMod.addEvent(new AddEventParams.Builder(ScalesOfDominionEvent.EVENT_ID, ScalesOfDominionEvent.class).dungeonID(ID).bonusCondition(new SidedWithSkiesCondition()).create());

        BaseMod.addEvent(new AddEventParams.Builder(AstralBlessingEvent.EVENT_ID, AstralBlessingEvent.class).dungeonID(ID).bonusCondition(new SidedWithStarsCondition()).create());
        BaseMod.addEvent(new AddEventParams.Builder(TwinElementsEvent.EVENT_ID, TwinElementsEvent.class).dungeonID(ID).bonusCondition(new TwinElementsCondition()).create());

//        BaseMod.addEvent(new AddEventParams.Builder(ColossusEvent.EVENT_ID, ColossusEvent.class).dungeonID(this.id).bonusCondition(new EnemyDefeatCondition(Colossus2.MONSTER_ID)).create());
//        BaseMod.addEvent(new AddEventParams.Builder(ColossusEvent.EVENT_ID, ColossusEvent.class).dungeonID(this.id).bonusCondition(new EnemyDefeatCondition(Celeste2.MONSTER_ID)).create());
//        BaseMod.addEvent(new AddEventParams.Builder(ColossusEvent.EVENT_ID, ColossusEvent.class).dungeonID(this.id).bonusCondition(new EnemyDefeatCondition(Leviathan2.MONSTER_ID)).create());
//        BaseMod.addEvent(new AddEventParams.Builder(ColossusEvent.EVENT_ID, ColossusEvent.class).dungeonID(this.id).bonusCondition(new EnemyDefeatCondition(Luminiera2.MONSTER_ID)).create());
//        BaseMod.addEvent(new AddEventParams.Builder(ColossusEvent.EVENT_ID, ColossusEvent.class).dungeonID(this.id).bonusCondition(new EnemyDefeatCondition(Tiamat2.MONSTER_ID)).create());
//        BaseMod.addEvent(new AddEventParams.Builder(ColossusEvent.EVENT_ID, ColossusEvent.class).dungeonID(this.id).bonusCondition(new EnemyDefeatCondition(Yggdrasil2.MONSTER_ID)).create());

        GranblueBosses.logger.info("Generated Normal Events");

//        BaseMod.addEvent(new AddEventParams.Builder(ZooeyEvent.EVENT_ID, ZooeyEvent.class)
//                .dungeonID(this.id)
//                .spawnCondition(() -> !AbstractDungeon.bossKey.equals(GrandOrder.MONSTER_ID)
//                                    && !AbstractDungeon.bossKey.equals(GranblueBosses.makeID("GrandOrder"))
//                                    && !AbstractDungeon.bossKey.equals("Grand Order"))
//                .create());
//        GranblueBosses.logger.info("Generated Boss Change");
    }

    @Override
    protected void initializeShrineList() {}


    @Override
    public AbstractScene DungeonScene() {
        return new Act2ArcarumScene();
    }

    public String getBodyText() {
        return TEXT[5];
    }

    public String getOptionText() {
        return TEXT[3];
    }

    public String getAfterSelectText() {
        return "A warm light envelops you, and you suddenly find yourself floating. Above you is a clear night sky. Bellow you is a sea of clouds.";
    }

    @Override
    protected void initializeLevelSpecificChances() {
        shopRoomChance = 0.05F;
        restRoomChance = 0.12F;
        treasureRoomChance = 0.0F;
        eventRoomChance = 0.22F;
        eliteRoomChance = 0.12F;
        smallChestChance = 50;
        mediumChestChance = 33;
        largeChestChance = 17;
        commonRelicChance = 50;
        uncommonRelicChance = 33;
        rareRelicChance = 17;
        colorlessRareChance = 0.3F;
        if (AbstractDungeon.ascensionLevel >= 12) {
            cardUpgradedChance = 0.25F;
        } else {
            cardUpgradedChance = 0.25F;
        }

    }
}

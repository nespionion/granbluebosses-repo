package granbluebosses.acts;

import actlikeit.dungeons.CustomDungeon;
import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import granbluebosses.GranblueBosses;
import granbluebosses.cards.event.CaOngCall;
import granbluebosses.config.ConfigMenu;
import granbluebosses.events.conditions.SidedWithSkiesCondition;
import granbluebosses.events.conditions.SidedWithStarsCondition;
import granbluebosses.events.conditions.TwinElementsCondition;
import granbluebosses.events.entry.Act1Entry;
import granbluebosses.events.generic.*;
import granbluebosses.events.skyevents.ScalesOfDominionEvent;
import granbluebosses.events.skyevents.act1.BorgerEvent;
import granbluebosses.events.skyevents.act1.SeofonAndNiyon;
import granbluebosses.events.skyevents.act1.ThreoEahtaTrainingEvent;
import granbluebosses.events.skyevents.act2.IlsaEvent;
import granbluebosses.events.starevents.TwinElementsEvent;
import granbluebosses.events.starevents.act1.CaOngEvent;
import granbluebosses.events.starevents.act1.GilgameshEvent;
import granbluebosses.events.starevents.act1.OdinEvent;
import granbluebosses.events.starevents.act1.PrometheusEvent;
import granbluebosses.monsters.act1.bosses.GrandOrder;
import granbluebosses.monsters.act1.bosses.ProtoBaha;
import granbluebosses.monsters.act1.elites.Alexiel;
import granbluebosses.monsters.act1.elites.Europa;
import granbluebosses.monsters.act1.elites.Grimnir;
import granbluebosses.monsters.act1.elites.Shiva;
import granbluebosses.monsters.act1.normal.*;
import granbluebosses.util.Sounds;

import java.util.ArrayList;

public class Act1Skies extends CustomDungeon {
    public static final String ID = GranblueBosses.makeID("Act1Skies"); //From the main mod file for best practices
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;
    public static final String NAME = TEXT[0];

    public Act1Skies() {
        super(NAME, ID, "images/ui/event/panel.png", false, 3, 10,4);

        if (ConfigMenu.enableDMCAMusic){
            this.setMainMusic(Sounds.MUSIC_ACT1_MAIN);
        } else {
            this.setMainMusic(Exordium.id);
        }

//        this.defineMonsters();
//        GranblueBosses.logger.info("Generated Encounters");


//        this.defineBossEnemies();
//        GranblueBosses.logger.info("Generated Boss Encounters");

//        this.generateWeakEnemies(3);
//        this.generateStrongEnemies(6);
//        this.generateElites(4);

        if (ConfigMenu.enableDMCAMusic){
            this.setMainMusic(Sounds.MUSIC_ACT1_MAIN);
        } else {
            this.setMainMusic(Exordium.id);
        }

        if (ConfigMenu.enableStartOfActEvents){
            if (ConfigMenu.modestyFilter){
                this.onEnterEvent(Act1Entry.class);
            } else {
                this.onEnterEvent(Act1Entry.class);
            }
        }

        this.addTempMusic(Sounds.MUSIC_ACT1_BATTLE, Sounds.MUSIC_ACT1_BATTLE);
        this.addTempMusic(Sounds.MUSIC_ACT1_ELITE, Sounds.MUSIC_ACT1_ELITE);
        this.addTempMusic(Sounds.MUSIC_ACT1_ELITE_SHIVA, Sounds.MUSIC_ACT1_ELITE_SHIVA);
        this.addTempMusic(Sounds.MUSIC_ACT1_ELITE_EUROPA, Sounds.MUSIC_ACT1_ELITE_EUROPA);
        this.addTempMusic(Sounds.MUSIC_ACT1_ELITE_GRIMNIR, Sounds.MUSIC_ACT1_ELITE_GRIMNIR);
        this.addTempMusic(Sounds.MUSIC_ACT1_ELITE_ALEX, Sounds.MUSIC_ACT1_ELITE_ALEX);
        this.addTempMusic(Sounds.MUSIC_ACT1_PROTOBAHA1, Sounds.MUSIC_ACT1_PROTOBAHA1);
        this.addTempMusic(Sounds.MUSIC_ACT1_PROTOBAHA2, Sounds.MUSIC_ACT1_PROTOBAHA2);
        this.addTempMusic(Sounds.MUSIC_ACT1_GRANDORDER1, Sounds.MUSIC_ACT1_GRANDORDER1);
        this.addTempMusic(Sounds.MUSIC_ACT1_GRANDORDER2, Sounds.MUSIC_ACT1_GRANDORDER2);
        this.addTempMusic(Sounds.MUSIC_ACT1_GRANDORDER3, Sounds.MUSIC_ACT1_GRANDORDER3);
    }

    public Act1Skies(CustomDungeon cd, AbstractPlayer p, ArrayList<String> emptyList) {
        super(cd, p, emptyList);
    }
    public Act1Skies(CustomDungeon cd, AbstractPlayer p, SaveFile sf) {
        super(cd, p, sf);
    }

    protected static void defineWeakMonster(){
        BaseMod.addMonster(Celeste.MONSTER_ID, () -> new Celeste());
        BaseMod.addMonsterEncounter(ID, new MonsterInfo(Celeste.MONSTER_ID, 10));
        BaseMod.addMonster(Colossus.MONSTER_ID, () -> new Colossus());
        BaseMod.addMonsterEncounter(ID, new MonsterInfo(Colossus.MONSTER_ID, 8));
        BaseMod.addMonster(Leviathan.MONSTER_ID, () -> new Leviathan());
        BaseMod.addMonsterEncounter(ID, new MonsterInfo(Leviathan.MONSTER_ID, 8));
        BaseMod.addMonster(Tiamat.MONSTER_ID, () -> new Tiamat());
        BaseMod.addMonsterEncounter(ID, new MonsterInfo(Tiamat.MONSTER_ID, 8));
        BaseMod.addMonster(Yggdrasil.MONSTER_ID, () -> new Yggdrasil());
        BaseMod.addMonsterEncounter(ID, new MonsterInfo(Yggdrasil.MONSTER_ID, 10));
        BaseMod.addMonster(Luminiera.MONSTER_ID, () -> new Luminiera());
        BaseMod.addMonsterEncounter(ID, new MonsterInfo(Luminiera.MONSTER_ID, 10));

//            addMonster(GranblueBosses.makeID("Colossus"), () -> new Colossus());
//            addMonster(GranblueBosses.makeID("Tiamat"), () -> new Tiamat());
//            addMonster(GranblueBosses.makeID("Luminiera"), () -> new Luminiera());
//            addMonster(GranblueBosses.makeID("Celeste"), () -> new Celeste());
//            addMonster(GranblueBosses.makeID("Leviathan"), () -> new Leviathan());
//            addMonster(GranblueBosses.makeID("Yggdrasil"), () -> new Yggdrasil());
    }

    protected static void defineStrongMonster(){
        BaseMod.addMonster(Celeste2.MONSTER_ID, () -> new Celeste2());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(Celeste2.MONSTER_ID, 10));
        BaseMod.addMonster(Colossus2.MONSTER_ID, () -> new Colossus2());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(Colossus2.MONSTER_ID, 8));
        BaseMod.addMonster(Leviathan2.MONSTER_ID, () -> new Leviathan2());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(Leviathan2.MONSTER_ID, 8));
        BaseMod.addMonster(Tiamat2.MONSTER_ID, () -> new Tiamat2());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(Tiamat2.MONSTER_ID, 8));
        BaseMod.addMonster(Yggdrasil2.MONSTER_ID, () -> new Yggdrasil2());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(Yggdrasil2.MONSTER_ID, 10));
        BaseMod.addMonster(Luminiera2.MONSTER_ID, () -> new Luminiera2());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(Luminiera2.MONSTER_ID, 10));

        BaseMod.addMonster(Athena.MONSTER_ID, () -> new Athena());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(Athena.MONSTER_ID, 10));
        BaseMod.addMonster(Grani.MONSTER_ID, () -> new Grani());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(Grani.MONSTER_ID, 10));
        BaseMod.addMonster(Baal.MONSTER_ID, () -> new Baal());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(Baal.MONSTER_ID, 10));
        BaseMod.addMonster(Garuda.MONSTER_ID, () -> new Garuda());
        BaseMod.addStrongMonsterEncounter(ID, new MonsterInfo(Garuda.MONSTER_ID, 10));
    }

    protected static void defineEliteMonster(){
        BaseMod.addMonster(Alexiel.MONSTER_ID, () -> new Alexiel());
        BaseMod.addEliteEncounter(ID, new MonsterInfo(Alexiel.MONSTER_ID, 10));
        BaseMod.addMonster(Grimnir.MONSTER_ID, () -> new Grimnir());
        BaseMod.addEliteEncounter(ID, new MonsterInfo(Grimnir.MONSTER_ID, 10));
        BaseMod.addMonster(Europa.MONSTER_ID, () -> new Europa());
        BaseMod.addEliteEncounter(ID, new MonsterInfo(Europa.MONSTER_ID, 10));
        BaseMod.addMonster(Shiva.MONSTER_ID, () -> new Shiva());
        BaseMod.addEliteEncounter(ID, new MonsterInfo(Shiva.MONSTER_ID, 10));
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
        BaseMod.addMonster(ProtoBaha.MONSTER_ID, () -> new ProtoBaha());
        BaseMod.addBoss(ID, ProtoBaha.MONSTER_ID, ProtoBaha.MAP_ICON, ProtoBaha.OUTLINE);
        BaseMod.addMonster(GrandOrder.MONSTER_ID, () -> new GrandOrder());
        BaseMod.addBoss(ID, GrandOrder.MONSTER_ID, GrandOrder.MAP_ICON, GrandOrder.OUTLINE);
    }



    public static void addEvents() {

        BaseMod.addEvent(FireEvent.EVENT_ID, FireEvent.class, ID);
        BaseMod.addEvent(LushEvent.EVENT_ID, LushEvent.class, ID);
        BaseMod.addEvent(RiverEvent.EVENT_ID, RiverEvent.class, ID);
        BaseMod.addEvent(RuinsEvent.EVENT_ID, RuinsEvent.class, ID);
        BaseMod.addEvent(Shrine6Event.EVENT_ID, Shrine6Event.class, ID);
        BaseMod.addEvent(Shrine10Event.EVENT_ID, Shrine10Event.class, ID);
        BaseMod.addEvent(YatimaEvent.EVENT_ID, YatimaEvent.class, ID);
        BaseMod.addEvent(RoseQueensEvent.EVENT_ID, RoseQueensEvent.class, ID);

        BaseMod.addEvent(new AddEventParams.Builder(ThreoEahtaTrainingEvent.EVENT_ID, ThreoEahtaTrainingEvent.class).dungeonID(ID).bonusCondition(new SidedWithSkiesCondition()).create());
        BaseMod.addEvent(new AddEventParams.Builder(SeofonAndNiyon.EVENT_ID, SeofonAndNiyon.class).dungeonID(ID).bonusCondition(new SidedWithSkiesCondition()).create());
        BaseMod.addEvent(new AddEventParams.Builder(BorgerEvent.EVENT_ID, BorgerEvent.class).dungeonID(ID).bonusCondition(new SidedWithSkiesCondition()).create());
        BaseMod.addEvent(new AddEventParams.Builder(ScalesOfDominionEvent.EVENT_ID, ScalesOfDominionEvent.class).dungeonID(ID).bonusCondition(new SidedWithSkiesCondition()).create());

        BaseMod.addEvent(new AddEventParams.Builder(CaOngEvent.EVENT_ID, CaOngEvent.class).dungeonID(ID).bonusCondition(new SidedWithStarsCondition()).create());
        BaseMod.addEvent(new AddEventParams.Builder(GilgameshEvent.EVENT_ID, GilgameshEvent.class).dungeonID(ID).bonusCondition(new SidedWithStarsCondition()).create());
        BaseMod.addEvent(new AddEventParams.Builder(OdinEvent.EVENT_ID, OdinEvent.class).dungeonID(ID).bonusCondition(new SidedWithStarsCondition()).create());
        BaseMod.addEvent(new AddEventParams.Builder(PrometheusEvent.EVENT_ID, PrometheusEvent.class).dungeonID(ID).bonusCondition(new SidedWithStarsCondition()).create());
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
        return new Act1SkiesScene();
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
        eliteRoomChance = 0.08F;
        smallChestChance = 50;
        mediumChestChance = 33;
        largeChestChance = 17;
        commonRelicChance = 50;
        uncommonRelicChance = 33;
        rareRelicChance = 17;
        colorlessRareChance = 0.3F;
        if (AbstractDungeon.ascensionLevel >= 12) {
            cardUpgradedChance = 0.0F;
        } else {
            cardUpgradedChance = 0.0F;
        }

    }
}

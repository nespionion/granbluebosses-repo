package granbluebosses.events.entry;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import granbluebosses.GranblueBosses;
import granbluebosses.config.ConfigMenu;
import granbluebosses.relics.ancients.*;
import granbluebosses.relics.events.*;

public class Act1Entry extends PhasedEvent {
    public final static String EVENT_ID = GranblueBosses.makeID("Act1Entry");
    protected static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    public static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private final static String EVENT_IMAGE_URL_1 = GranblueBosses.eventPath("act1/event-act1-entry-0.png");
    private final static String EVENT_IMAGE_URL_1_CEN = GranblueBosses.eventPath("act1/event-act1-entry-0Cen.png");
    private final static String EVENT_IMAGE_URL_2_1 = GranblueBosses.eventPath("act1/event-act1-entry-1.png");
    private final static String EVENT_IMAGE_URL_2_2 = GranblueBosses.eventPath("act1/event-act1-entry-2.png");
    private final static String EVENT_IMAGE_URL_2_2_CEN = GranblueBosses.eventPath("act1/event-act1-entry-2Cen.png");
    private final static String EVENT_IMAGE_URL_3 = GranblueBosses.eventPath("act1/event-act1-entry-3.png");
    private enum SIDE{
        STARS,
        SKIES
    }
    private static SIDE currSide = null;
    private static int startsHPGain = 0;
    private static int skiesHPGain = 0;
    private static int skiesDmg = 0;


    public Act1Entry() {
        super(EVENT_ID, NAME, EVENT_IMAGE_URL_1);

        if (ConfigMenu.modestyFilter){
            this.imageEventText.loadImage(EVENT_IMAGE_URL_1_CEN);
        }

        GranblueBosses.logger.info("Act 1 Entry Event initializing");

        startsHPGain = AbstractDungeon.ascensionLevel >= 15 ? 7 : 6;
        skiesHPGain = AbstractDungeon.ascensionLevel >= 15 ? 12 : 10;
        skiesDmg = AbstractDungeon.ascensionLevel >= 15 ? 10 : 12;

        this.registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[1])
                        .setOptionResult((i)->{

                            currSide = SIDE.STARS;
                            this.imageEventText.loadImage(EVENT_IMAGE_URL_2_1);
                            transitionKey("stars");
                        }))
                .addOption(new TextPhase.OptionInfo(OPTIONS[0])
                        .setOptionResult((i)->{

                            currSide = SIDE.SKIES;
                            if (ConfigMenu.modestyFilter){
                                this.imageEventText.loadImage(EVENT_IMAGE_URL_2_2_CEN);
                            } else {
                                this.imageEventText.loadImage(EVENT_IMAGE_URL_2_2);
                            }
                            transitionKey("sky");
                        }))
        );

        this.registerPhase("stars", new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[4] + startsHPGain + OPTIONS[5], (i)->{
                    this.obtainZerothRewards();
                    this.imageEventText.loadImage(EVENT_IMAGE_URL_3);
                    transitionKey("leave stars");
                })
                .addOption(OPTIONS[6], new ConstellationRelic(), (i)->{
                    this.obtainFirstRewards();
                    this.imageEventText.loadImage(EVENT_IMAGE_URL_3);
                    transitionKey("leave stars");
                })
                .addOption(OPTIONS[8], new ProvidenceGlobe(), (i)->{
                    this.obtainSecondRewards();
                    this.imageEventText.loadImage(EVENT_IMAGE_URL_3);
                    transitionKey("leave stars");
                })
                .addOption(OPTIONS[10], new BlueCrystal(), (i)->{
                    this.obtainThirdRewards();
                    this.imageEventText.loadImage(EVENT_IMAGE_URL_3);
                    transitionKey("leave stars");
                })
        );

        this.registerPhase("sky", new TextPhase(DESCRIPTIONS[2])
                .addOption(OPTIONS[2] + skiesDmg + OPTIONS[3] + skiesHPGain + OPTIONS[5], (i)->{
                    this.obtainZerothRewards();
                    this.imageEventText.loadImage(EVENT_IMAGE_URL_3);
                    transitionKey("leave stars");
                })
                .addOption(OPTIONS[7], new DamascusIngot(), (i)->{

                    this.obtainFirstRewards();
                    this.imageEventText.loadImage(EVENT_IMAGE_URL_3);
                    transitionKey("leave sky");
                })
                .addOption(OPTIONS[9], new LegendaryMerit(), (i)->{

                    this.obtainSecondRewards();
                    this.imageEventText.loadImage(EVENT_IMAGE_URL_3);
                    transitionKey("leave sky");
                })
                .addOption(OPTIONS[11], new ShieldOfTenets(), (i)->{

                    this.obtainThirdRewards();
                    this.imageEventText.loadImage(EVENT_IMAGE_URL_3);
                    transitionKey("leave sky");
                })
        );


        this.registerPhase("leave stars", new TextPhase(DESCRIPTIONS[3] + DESCRIPTIONS[5])
                .addOption(OPTIONS[12], (i)->openMap())
        );

        this.registerPhase("leave sky", new TextPhase(DESCRIPTIONS[4] + DESCRIPTIONS[5])
                .addOption(OPTIONS[12], (i)->openMap())
        );

        transitionKey("start");

    }

    protected void obtainZerothRewards(){
        obtainAct2RewardPrep();

        if (currSide == SIDE.STARS){
            AbstractDungeon.player.increaseMaxHp(startsHPGain, true);
        } else {
            AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, skiesDmg, DamageInfo.DamageType.THORNS));
            AbstractDungeon.player.increaseMaxHp(skiesHPGain, true);
        }
    }

    protected void obtainFirstRewards(){
        obtainAct2RewardPrep();

        if (currSide == SIDE.STARS){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2),new ConstellationRelic());
        } else {
            AbstractDungeon.player.decreaseMaxHealth(12);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2),new DamascusIngot());
        }
    }

    protected void obtainSecondRewards(){
        obtainAct2RewardPrep();

        if (currSide == SIDE.STARS){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2),new ProvidenceGlobe());
        } else {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2),new LegendaryMerit());
        }
    }

    protected void obtainThirdRewards(){
        obtainAct2RewardPrep();

        if (currSide == SIDE.STARS){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2),new BlueCrystal());
        } else {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2),new ShieldOfTenets());
        }
    }

    protected void obtainAct2RewardPrep(){
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new PlusMarkBlue(AbstractDungeon.player.relics.size()));
    }

    /*
    Neow's Blessings:

    First Blessing
        Remove a card.
        Transform a card.
        Upgrade a card.
        Choose a card to obtain. (Choose one of 3 random cards of your character.)
        Choose an Uncommon Colorless Card to obtain.
        Obtain a random Rare Card.

    Second Blessing
        Max HP +8/6/7/7 (Ironclad / Silent / Defect / Watcher)
        Neow's Lament: Enemies in the next three combats will have one health.
        Obtain a random Common Relic.
        Receive 100 Gold.
        Obtain 3 random Potions.

    Third Blessing
        A composite of a disadvantage and a more powerful reward. It can be a combination of any disadvantage and reward below, with any exceptions noted.

        Disadvantages:
            Lose Max HP. (-8 Ironclad / -7 Silent / -7 Watcher / -7 Defect)
            Take X damage.
            X is equal to (Current HP / 10, rounded down) * 3.
            The amount of damage is also affected by AscensionAscension.png 6 and AscensionAscension.png 14.
            Obtain a Curse.
            Lose all Gold.

        Advantages:
            Remove 2 cards.
            Does not pair with Obtain a Curse.
            Transform 2 cards.
            Gain 250 Gold.
            Does not pair with Lose all Gold.
            Choose a Rare Card to obtain.
            Choose a Rare Colorless Card to obtain.
            Obtain a random Rare Relic.
            Gain Max HP. (+16 Ironclad / +12 Silent / +14 Defect / +14 Watcher)
            Does not pair with Lose max health

    * */

}

package granbluebosses.events.entry;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import granbluebosses.GranblueBosses;
import granbluebosses.config.ConfigMenu;
import granbluebosses.relics.ancients.BestLivesTogetherRelic;
import granbluebosses.relics.ancients.MimicChestMino;
import granbluebosses.relics.ancients.OppressusFragorRelic;
import granbluebosses.relics.ancients.TranscendentBlue;
import granbluebosses.relics.events.PlusMarkBlue;

public class Act2Entry extends PhasedEvent {
    public final static String EVENT_ID = GranblueBosses.makeID("Act2Entry");
    protected static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    public static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private final static String EVENT_IMAGE_URL_1_CEN = GranblueBosses.eventPath("act2/act2entry_1_cen.png");
    private final static String EVENT_IMAGE_URL_1_UNCEN = GranblueBosses.eventPath("act2/act2entry_1_uncen.png");
    private final static String EVENT_IMAGE_URL_2_CEN = GranblueBosses.eventPath("act2/act2entry_2_cen.png");
    private final static String EVENT_IMAGE_URL_2_UNCEN = GranblueBosses.eventPath("act2/act2entry_2_uncen.png");
    private final static String EVENT_IMAGE_URL_3_CEN = GranblueBosses.eventPath("act2/act2entry_3_cen.png");
    private final static String EVENT_IMAGE_URL_3_UNCEN = GranblueBosses.eventPath("act2/act2entry_3_uncen.png");
    private final static String EVENT_IMAGE_URL_4_CEN = GranblueBosses.eventPath("act2/act2entry_4_cen.png");
    private final static String EVENT_IMAGE_URL_4_UNCEN = GranblueBosses.eventPath("act2/act2entry_4_uncen.png");
    private final static String EVENT_IMAGE_URL_5 = GranblueBosses.eventPath("act2/act2entry_5.png");
    private static int maxHPGain = 0;


    public Act2Entry(){
        super(EVENT_ID, NAME, ConfigMenu.modestyFilter ? EVENT_IMAGE_URL_1_CEN : EVENT_IMAGE_URL_1_UNCEN);

        GranblueBosses.logger.info("Act 2 Entry Event initializing");

        maxHPGain = AbstractDungeon.ascensionLevel >= 15 ? 15 : 12;

        this.registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[1])
                        .setOptionResult((i)->{

                            this.imageEventText.loadImage(ConfigMenu.modestyFilter ? EVENT_IMAGE_URL_2_CEN : EVENT_IMAGE_URL_2_UNCEN);

                            transitionKey("orologia");
                        }))
        );

        String orologiaDescription = ConfigMenu.modestyFilter ? "man " : "woman ";

        this.registerPhase("orologia", new TextPhase(DESCRIPTIONS[1] + orologiaDescription + DESCRIPTIONS[2])
                .addOption(new TextPhase.OptionInfo(OPTIONS[3])
                        .setOptionResult((i)->{
                            if (checkRelicReward()){
                                this.imageEventText.loadImage(ConfigMenu.modestyFilter ? EVENT_IMAGE_URL_4_CEN : EVENT_IMAGE_URL_4_UNCEN);
                                transitionKey("city");
                            } else {
                                this.imageEventText.loadImage(ConfigMenu.modestyFilter ? EVENT_IMAGE_URL_3_CEN : EVENT_IMAGE_URL_3_UNCEN);
                                transitionKey("cityBad");
                            }
                        }))
                .addOption(new TextPhase.OptionInfo(OPTIONS[4])
                        .setOptionResult((i)->openMap()))
        );

        this.registerPhase("city", new TextPhase(DESCRIPTIONS[3])
                .addOption(new TextPhase.OptionInfo(OPTIONS[7], new OppressusFragorRelic()).enabledCondition(Act2Entry::checkRelicReward, OPTIONS[0]).setOptionResult(
                        (i)->{
                            this.obtainFirstRewards();
                            this.imageEventText.loadImage(EVENT_IMAGE_URL_5);
                            transitionKey("leave");
                        }
                )).addOption(new TextPhase.OptionInfo(OPTIONS[8], new MimicChestMino()).enabledCondition(Act2Entry::checkRelicReward, OPTIONS[0]).setOptionResult(
                        (i)->{
                            this.obtainSecondRewards();
                            this.imageEventText.loadImage(EVENT_IMAGE_URL_5);
                            transitionKey("leave");
                        }
                )).addOption(new TextPhase.OptionInfo(OPTIONS[9], new BestLivesTogetherRelic()).enabledCondition(Act2Entry::checkRelicReward, OPTIONS[0]).setOptionResult(
                        (i)->{
                            this.obtainThirdRewards();
                            this.imageEventText.loadImage(EVENT_IMAGE_URL_5);
                            transitionKey("leave");
                        }
                )).addOption(new TextPhase.OptionInfo(OPTIONS[10], new TranscendentBlue()).enabledCondition(Act2Entry::checkRelicReward, OPTIONS[0]).setOptionResult(
                        (i)->{
                            this.obtainForthRewards();
                            this.imageEventText.loadImage(EVENT_IMAGE_URL_5);
                            transitionKey("leave");
                        }
                ))
        );

        this.registerPhase("cityBad", new TextPhase(DESCRIPTIONS[4])
                .addOption(new TextPhase.OptionInfo(OPTIONS[5] + maxHPGain + OPTIONS[6])
                        .setOptionResult((i)->{

                            this.obtainZerothRewards();
                            this.imageEventText.loadImage(EVENT_IMAGE_URL_5);
                            transitionKey("leave");
                        }))
                .addOption(new TextPhase.OptionInfo(OPTIONS[0]).enabledCondition(() -> false, OPTIONS[0]).setOptionResult(
                        (i)->{
                            this.obtainZerothRewards();
                            transitionKey("leave");
                        }
                ))
        );

        String orologiaPronoun = ConfigMenu.modestyFilter ? "his " : "her ";
        String orologiaPronoun2 = ConfigMenu.modestyFilter ? "He " : "She ";
        String orologiaPronoun3 = ConfigMenu.modestyFilter ? "him" : "her ";

        this.registerPhase("leave", new TextPhase(DESCRIPTIONS[5] + orologiaPronoun + DESCRIPTIONS[6] + orologiaPronoun2 + DESCRIPTIONS[7] + orologiaPronoun3 + DESCRIPTIONS[8])
                .addOption(OPTIONS[11], (i)->openMap())
        );

        transitionKey("start");
    }

    private static boolean checkRelicReward(){
        return AbstractDungeon.player.hasRelic(PlusMarkBlue.RELIC_ID) &&
                ((PlusMarkBlue) AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID)).isRelicActive();
    }

    protected void obtainZerothRewards(){
        if (AbstractDungeon.player.hasRelic(PlusMarkBlue.RELIC_ID)) ((PlusMarkBlue) AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID)).reactivateRelic();

        AbstractDungeon.player.increaseMaxHp(maxHPGain, true);
//        if (checkRelicReward()) ((PlusMarkBlue) AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID)).disableRelic();
    }

    protected void obtainFirstRewards(){
        // TODO : Replace this with Red Mark in preparation for Act 3
//        if (AbstractDungeon.player.hasRelic(PlusMarkBlue.RELIC_ID)) (new PlusMarkBlue(AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID).counter)).instantObtain(AbstractDungeon.player, AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID).counter, true);

        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new OppressusFragorRelic());
//        ((PlusMarkBlue) AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID)).disableRelic();
    }

    protected void obtainSecondRewards(){
        // TODO : Replace this with Red Mark in preparation for Act 3
//        if (AbstractDungeon.player.hasRelic(PlusMarkBlue.RELIC_ID)) (new PlusMarkBlue(AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID).counter)).instantObtain(AbstractDungeon.player, AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID).counter, true);

        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new MimicChestMino());
//        ((PlusMarkBlue) AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID)).disableRelic();
    }

    protected void obtainThirdRewards(){
        // TODO : Replace this with Red Mark in preparation for Act 3
//        if (AbstractDungeon.player.hasRelic(PlusMarkBlue.RELIC_ID)) (new PlusMarkBlue(AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID).counter)).instantObtain(AbstractDungeon.player, AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID).counter, true);

        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new BestLivesTogetherRelic());
//        ((PlusMarkBlue) AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID)).disableRelic();
    }

    protected void obtainForthRewards(){
        // TODO : Replace this with Red Mark in preparation for Act 3
//        if (AbstractDungeon.player.hasRelic(PlusMarkBlue.RELIC_ID)) (new PlusMarkBlue(AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID).counter)).instantObtain(AbstractDungeon.player, AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID).counter, true);

        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new TranscendentBlue());
//        ((PlusMarkBlue) AbstractDungeon.player.getRelic(PlusMarkBlue.RELIC_ID)).disableRelic();
    }
}

package granbluebosses.events.skyevents.act2;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.events.MeteorsLight;
import granbluebosses.relics.events.TiensPortrait;

import static granbluebosses.GranblueBosses.makeID;

public class PortraitTienEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(PortraitTienEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(PortraitTienEvent.class.getSimpleName() + ".png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    public static int GOLD_AMT = 100;

    public PortraitTienEvent() {
        super(EVENT_ID, NAME, IMG);

        GOLD_AMT = AbstractDungeon.ascensionLevel >= 15 ? 50 : 75;

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0] + GOLD_AMT + OPTIONS[1], (i)->{

                    AbstractDungeon.combatRewardScreen.open();
                    AbstractDungeon.combatRewardScreen.clear();
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(GOLD_AMT));
                    AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomPotion()));

                    AbstractDungeon.combatRewardScreen.positionRewards();

                    transitionKey("gold leave");
                })

                .addOption(OPTIONS[2], new TiensPortrait(), (i)->{

                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new TiensPortrait());

                    transitionKey("relic leave");
                })
        );

        registerPhase("gold leave", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[3], (i)->openMap()));
        registerPhase("relic leave", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[3], (i)->openMap()));

        transitionKey("start");
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();

        GOLD_AMT = AbstractDungeon.ascensionLevel >= 15 ? 50 : 75;
    }
}

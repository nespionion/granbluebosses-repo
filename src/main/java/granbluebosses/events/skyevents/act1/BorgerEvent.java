package granbluebosses.events.skyevents.act1;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.events.StarsTreadsRelic;
import granbluebosses.relics.events.TiensPortrait;

import static granbluebosses.GranblueBosses.makeID;

public class BorgerEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(BorgerEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(BorgerEvent.class.getSimpleName() + ".png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg


    public BorgerEvent() {
        super(EVENT_ID, NAME, IMG);


        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0], new StarsTreadsRelic(), (i)->{

                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new StarsTreadsRelic());

                    transitionKey("gold leave");
                })

                .addOption(OPTIONS[1], new TiensPortrait(), (i)->{

                    AbstractRelic r = AbstractDungeon.returnRandomRelic(AbstractDungeon.returnRandomRelicTier());

                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);

                    transitionKey("relic leave");
                })
        );

        registerPhase("gold leave", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[2], (i)->openMap()));
        registerPhase("relic leave", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[2], (i)->openMap()));

        transitionKey("start");
    }
}

package granbluebosses.events.skyevents.act2;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import granbluebosses.GranblueBosses;
import granbluebosses.action.SetHPToSpecificAmountAction;
import granbluebosses.config.ConfigMenu;
import granbluebosses.relics.events.ScalesOfDominionRelic;

import static granbluebosses.GranblueBosses.makeID;

public class JewelResortCasinoEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(JewelResortCasinoEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(JewelResortCasinoEvent.class.getSimpleName() + ".png");
    private static final String IMG_CEN = GranblueBosses.eventPath(JewelResortCasinoEvent.class.getSimpleName() + "Cen.png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    private static int GOLD_AMT = 200;

    public JewelResortCasinoEvent() {
        super(EVENT_ID, NAME, ConfigMenu.modestyFilter ? IMG_CEN : IMG);

        GOLD_AMT = 200;

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0], (i)->{
                    if (AbstractDungeon.eventRng.randomBoolean()){
                        AbstractDungeon.actionManager.addToBottom(new GainGoldAction(GOLD_AMT));
                        GOLD_AMT *= 2;
                        transitionKey("play1");
                    } else {
                        transitionKey("play1 loss");
                    }
                })
                .addOption(OPTIONS[1], (i)->{
                    AbstractDungeon.player.gainGold(AbstractDungeon.eventRng.random(100));
                    transitionKey("play2 leave");
                })
        );

        registerPhase("play1", new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[0], (i)->{
                    if (AbstractDungeon.eventRng.randomBoolean()){
                        AbstractDungeon.actionManager.addToBottom(new GainGoldAction(GOLD_AMT));
                        GOLD_AMT *= 2;
                        transitionKey("play1");
                    } else {
                        AbstractDungeon.player.gainGold(GOLD_AMT);
                        transitionKey("play1 loss");
                    }
                })
                .addOption(OPTIONS[2], (i)->openMap())
        );

        registerPhase("play1 loss", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[2], (i)->openMap()));
        registerPhase("play2 leave", new TextPhase(DESCRIPTIONS[3]).addOption(OPTIONS[2], (i)->openMap()));

        transitionKey("start");
    }

    @Override
    public void onEnterRoom() {
        GOLD_AMT = 200;
        super.onEnterRoom();
    }
}

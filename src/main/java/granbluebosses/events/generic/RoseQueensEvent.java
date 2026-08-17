package granbluebosses.events.generic;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.events.CrystalRose;
import granbluebosses.relics.events.StarsTreadsRelic;
import granbluebosses.relics.events.TiensPortrait;

import static granbluebosses.GranblueBosses.makeID;

public class RoseQueensEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(RoseQueensEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG_1 = GranblueBosses.eventPath(RoseQueensEvent.class.getSimpleName() + "1.png");
    private static final String IMG_2 = GranblueBosses.eventPath(RoseQueensEvent.class.getSimpleName() + "2.png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    public static int HP_LOSS = 12;

    public RoseQueensEvent() {
        super(EVENT_ID, NAME, IMG_1);

        HP_LOSS = AbstractDungeon.ascensionLevel >= 15 ? 12 : 8;

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0] + HP_LOSS + OPTIONS[1], new CrystalRose(), (i)->{



                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, HP_LOSS, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.NONE));

                    this.imageEventText.loadImage(IMG_2);

                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new CrystalRose());

                    transitionKey("gold leave");
                })

                .addOption(OPTIONS[2], (i)->{

                    AbstractRelic r = AbstractDungeon.returnRandomRelic(AbstractDungeon.returnRandomRelicTier());

                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);

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

        HP_LOSS = AbstractDungeon.ascensionLevel >= 15 ? 12 : 8;
    }
}

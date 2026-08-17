package granbluebosses.events.skyevents.act1;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.events.StarsTreadsRelic;
import granbluebosses.relics.events.TiensPortrait;

import static granbluebosses.GranblueBosses.makeID;

public class SeofonAndNiyon extends PhasedEvent {
    public static final String EVENT_ID = makeID(SeofonAndNiyon.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(SeofonAndNiyon.class.getSimpleName() + ".png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    public static int HP_AMT = AbstractDungeon.ascensionLevel >= 15 ? 15 : 20;

    public SeofonAndNiyon() {
        super(EVENT_ID, NAME, IMG);

        HP_AMT = AbstractDungeon.ascensionLevel >= 15 ? 15 : 20;

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0], new StarsTreadsRelic(), (i)->{

                    AbstractDungeon.combatRewardScreen.clear();

                    AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[2], false, false, false, true);


                    transitionKey("gold leave");
                })

                .addOption(OPTIONS[1] + HP_AMT + OPTIONS[2], new TiensPortrait(), (i)->{

                    AbstractDungeon.player.heal(HP_AMT);

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

        HP_AMT = AbstractDungeon.ascensionLevel >= 15 ? 15 : 20;
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            CardCrawlGame.sound.play("CARD_EXHAUST");
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }
}

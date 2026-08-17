package granbluebosses.events.starevents.act1;

import basemod.ReflectionHacks;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AncientPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.cards.event.CaOngCall;
import granbluebosses.cards.event.PrometheusCall;
import granbluebosses.relics.events.StarsTreadsRelic;
import granbluebosses.relics.events.TiensPortrait;

import java.util.List;
import java.util.function.Consumer;

import static granbluebosses.GranblueBosses.makeID;

public class CaOngEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(CaOngEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(CaOngEvent.class.getSimpleName() + ".png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg


    public CaOngEvent() {
        super(EVENT_ID, NAME, IMG);

        TextPhase eventStatPhase = new TextPhase(DESCRIPTIONS[0]);

        AbstractCard c = new CaOngCall();

        this.addTextPhaseOption(eventStatPhase, OPTIONS[0], c, (i)->{

            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));

            transitionKey("accept leave");

        });
        registerPhase("start", eventStatPhase
                .addOption(OPTIONS[1], (i)->{
                    AbstractDungeon.combatRewardScreen.open();
                    AbstractDungeon.combatRewardScreen.clear();
                    AbstractDungeon.combatRewardScreen.rewards.clear();
                    AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(new AncientPotion()));

                    AbstractDungeon.combatRewardScreen.positionRewards();
                    transitionKey("reject leave");
                })
        );

        registerPhase("accept leave", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[2], (i)->openMap()));
        registerPhase("reject leave", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[2], (i)->openMap()));

        transitionKey("start");
    }

    public void addTextPhaseOption(TextPhase eventStatPhase, String optionText, AbstractCard card, Consumer<Integer> onClick) {
        List<TextPhase.OptionInfo> privOptions = ReflectionHacks.getPrivate(eventStatPhase, TextPhase.class, "options");
        privOptions.add((new TextPhase.OptionInfo(optionText, card)).setOptionResult(onClick));
    }
}

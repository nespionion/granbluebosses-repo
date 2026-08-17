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
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.cards.event.PrometheusCall;

import java.util.List;
import java.util.function.Consumer;

import static granbluebosses.GranblueBosses.makeID;

public class PrometheusEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(PrometheusEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(PrometheusEvent.class.getSimpleName() + ".png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    private static int HP_LOSS = 16;
    private static int MAX_HP_UP = 8;

    public PrometheusEvent() {
        super(EVENT_ID, NAME, IMG);

        TextPhase eventStatPhase = new TextPhase(DESCRIPTIONS[0]);

        HP_LOSS = AbstractDungeon.ascensionLevel >= 15 ? 16 : 12 ;
        MAX_HP_UP = AbstractDungeon.ascensionLevel >= 15 ? 6 : 8 ;

        AbstractCard c = new PrometheusCall();

        this.addTextPhaseOption(eventStatPhase, OPTIONS[0] + HP_LOSS + OPTIONS[1], c, (i)->{

            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));

            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, HP_LOSS, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.NONE));

            transitionKey("accept leave");

        });
        registerPhase("start", eventStatPhase
                .addOption(OPTIONS[2] + HP_LOSS + OPTIONS[3] + MAX_HP_UP + OPTIONS[4], (i)->{
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, HP_LOSS, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.NONE));
                    AbstractDungeon.player.increaseMaxHp(MAX_HP_UP, false);

                    transitionKey("reject leave");
                })
        );

        registerPhase("accept leave", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[5], (i)->openMap()));
        registerPhase("reject leave", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[5], (i)->openMap()));

        transitionKey("start");
    }

    public void addTextPhaseOption(TextPhase eventStatPhase, String optionText, AbstractCard card, Consumer<Integer> onClick) {
        List<TextPhase.OptionInfo> privOptions = ReflectionHacks.getPrivate(eventStatPhase, TextPhase.class, "options");
        privOptions.add((new TextPhase.OptionInfo(optionText, card)).setOptionResult(onClick));
    }

}

package granbluebosses.events.skyevents;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.action.SetHPToSpecificAmountAction;
import granbluebosses.relics.ancients.DamascusIngot;
import granbluebosses.relics.ancients.ShieldOfTenets;
import granbluebosses.relics.events.ScalesOfDominionRelic;

import static granbluebosses.GranblueBosses.makeID;

public class ScalesOfDominionEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(ScalesOfDominionEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(ScalesOfDominionEvent.class.getSimpleName() + ".png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    private static final float HEAL_AMT = 0.25f;
    private static final float HEAL_AMT_ASC = 0.15f;

    public ScalesOfDominionEvent() {
        super(EVENT_ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0], new ScalesOfDominionRelic(), (i)->{
                    AbstractDungeon.actionManager.addToBottom(new SetHPToSpecificAmountAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.maxHealth / 2));
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2),new ScalesOfDominionRelic());
                    transitionKey("scales leave");
                })
                .addOption(OPTIONS[1] + (int) (AbstractDungeon.player.maxHealth * (AbstractDungeon.ascensionLevel >= 15 ? HEAL_AMT_ASC : HEAL_AMT)) + OPTIONS[2], (i)->{
                    AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, (int) (AbstractDungeon.player.maxHealth * (AbstractDungeon.ascensionLevel >= 15 ? HEAL_AMT_ASC : HEAL_AMT))));
                    transitionKey("heal leave");
                })
        );

        registerPhase("scales leave", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[3], (i)->openMap()));
        registerPhase("heal leave", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[3], (i)->openMap()));

        transitionKey("start");
    }
}

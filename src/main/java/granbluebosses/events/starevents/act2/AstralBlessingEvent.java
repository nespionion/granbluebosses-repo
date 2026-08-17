package granbluebosses.events.starevents.act2;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.cards.rewards.Magna1.*;
import granbluebosses.cards.rewards.Magna3.*;
import granbluebosses.relics.events.MeteorsLight;

import static granbluebosses.GranblueBosses.makeID;

public class AstralBlessingEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(AstralBlessingEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(AstralBlessingEvent.class.getSimpleName() + ".png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    public AstralBlessingEvent() {
        super(EVENT_ID, NAME, IMG);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0], (i)->{
                    enhanceMagna();

                    transitionKey("leave");
                })
        );

        registerPhase("leave", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[1], (i)->openMap()));

        transitionKey("start");
    }

    public static boolean hasManga1(){
        return (AbstractDungeon.player.masterDeck.findCardById(ColossusOmega.CARD_ID) != null)
                || (AbstractDungeon.player.masterDeck.findCardById(LeviathanOmega.CARD_ID) != null)
                || (AbstractDungeon.player.masterDeck.findCardById(YggdrasilOmega.CARD_ID) != null)
                || (AbstractDungeon.player.masterDeck.findCardById(TiamatOmega.CARD_ID) != null)
                || (AbstractDungeon.player.masterDeck.findCardById(LuminieraOmega.CARD_ID) != null)
                || (AbstractDungeon.player.masterDeck.findCardById(CelesteOmega.CARD_ID) != null)
        ;
    }


    public static void enhanceMagna(){
        AbstractCard c = null;
        AbstractCard newC = null;


        c = AbstractDungeon.player.masterDeck.findCardById(ColossusOmega.CARD_ID);
        while (c != null){
            AbstractDungeon.player.masterDeck.removeCard(c);

            newC = new ColossusIraOmega();

            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(newC.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(newC.target_x, newC.target_y));

            AbstractDungeon.player.masterDeck.addToTop(newC);

            c = AbstractDungeon.player.masterDeck.findCardById(ColossusOmega.CARD_ID);
        }


        c = AbstractDungeon.player.masterDeck.findCardById(LeviathanOmega.CARD_ID);
        while (c != null){
            AbstractDungeon.player.masterDeck.removeCard(c);

            newC = new LeviathanMareOmega();

            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(newC.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(newC.target_x, newC.target_y));

            AbstractDungeon.player.masterDeck.addToTop(newC);

            c = AbstractDungeon.player.masterDeck.findCardById(LeviathanOmega.CARD_ID);
        }


        c = AbstractDungeon.player.masterDeck.findCardById(YggdrasilOmega.CARD_ID);
        while (c != null){
            AbstractDungeon.player.masterDeck.removeCard(c);

            newC = new YggdrasilArbosOmega();

            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(newC.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(newC.target_x, newC.target_y));

            AbstractDungeon.player.masterDeck.addToTop(newC);

            c = AbstractDungeon.player.masterDeck.findCardById(YggdrasilOmega.CARD_ID);
        }


        c = AbstractDungeon.player.masterDeck.findCardById(TiamatOmega.CARD_ID);
        while (c != null){
            AbstractDungeon.player.masterDeck.removeCard(c);

            newC = new TiamatAuraOmega();

            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(newC.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(newC.target_x, newC.target_y));

            AbstractDungeon.player.masterDeck.addToTop(newC);

            c = AbstractDungeon.player.masterDeck.findCardById(TiamatOmega.CARD_ID);
        }


        c = AbstractDungeon.player.masterDeck.findCardById(LuminieraOmega.CARD_ID);
        while (c != null){
            AbstractDungeon.player.masterDeck.removeCard(c);

            newC = new LuminieraCredoOmega();

            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(newC.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(newC.target_x, newC.target_y));

            AbstractDungeon.player.masterDeck.addToTop(newC);

            c = AbstractDungeon.player.masterDeck.findCardById(LuminieraOmega.CARD_ID);
        }


        c = AbstractDungeon.player.masterDeck.findCardById(CelesteOmega.CARD_ID);
        while (c != null){
            AbstractDungeon.player.masterDeck.removeCard(c);

            newC = new CelesteAterOmega();

            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(newC.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(newC.target_x, newC.target_y));

            AbstractDungeon.player.masterDeck.addToTop(newC);

            c = AbstractDungeon.player.masterDeck.findCardById(CelesteOmega.CARD_ID);
        }
    }

}

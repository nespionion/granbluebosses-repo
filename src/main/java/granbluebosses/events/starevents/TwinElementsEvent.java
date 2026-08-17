package granbluebosses.events.starevents;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.config.ConfigMenu;

import java.util.ArrayList;

import static granbluebosses.GranblueBosses.makeID;

public class TwinElementsEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(TwinElementsEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(TwinElementsEvent.class.getSimpleName() + ".png");
    private static final String IMG_CEN = GranblueBosses.eventPath(TwinElementsEvent.class.getSimpleName() + "Cen.png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    public TwinElementsEvent() {
        super(EVENT_ID, NAME, ConfigMenu.modestyFilter ? IMG_CEN : IMG);

        String elementSelf = AbstractDungeon.player.getCardColor() == AbstractCard.CardColor.RED ? "fire" : "water";
        String elementOther = AbstractDungeon.player.getCardColor() == AbstractCard.CardColor.RED ? "water" : "fire";

        int cardAmount = AbstractDungeon.ascensionLevel >= 15 ? 2 : 3;

        registerPhase("start", new TextPhase(DESCRIPTIONS[0] + elementSelf + DESCRIPTIONS[1] + elementOther + DESCRIPTIONS[2])
                .addOption(OPTIONS[0] + cardAmount + OPTIONS[1] + (AbstractDungeon.player.getCardColor() == AbstractCard.CardColor.RED? "#bDefect" : "#rIronclad") + OPTIONS[2], (i)->{

                    AbstractDungeon.combatRewardScreen.open();
                    AbstractDungeon.combatRewardScreen.clear();
                    AbstractDungeon.getCurrRoom().rewards.clear();

                    ArrayList<AbstractCard> tmpPool = new ArrayList<AbstractCard>();

                    if (AbstractDungeon.player.getCardColor() == AbstractCard.CardColor.RED){
                        CardLibrary.addBlueCards(tmpPool);
                    } else {
                        CardLibrary.addRedCards(tmpPool);
                    }

                    for (int j = 0; j < cardAmount; j++) {
                        RewardItem rw;
                        rw = new RewardItem();
                        rw.type = RewardItem.RewardType.CARD;
                        rw.cards.clear();
                        rw.cards.add(tmpPool.get(AbstractDungeon.eventRng.random(tmpPool.size() - 1)));
                        rw.cards.add(tmpPool.get(AbstractDungeon.eventRng.random(tmpPool.size() - 1)));
                        rw.cards.add(tmpPool.get(AbstractDungeon.eventRng.random(tmpPool.size() - 1)));
//                        RewardItem rw = new RewardItem(AbstractDungeon.player.getCardColor() == AbstractCard.CardColor.RED ? AbstractCard.CardColor.BLUE : AbstractCard.CardColor.RED);
                        AbstractDungeon.combatRewardScreen.rewards.add(rw);
                    }

                    AbstractDungeon.combatRewardScreen.positionRewards();


                    if (AbstractDungeon.player.getCardColor() == AbstractCard.CardColor.RED)
                        transitionKey("leave accept iron");
                    else
                        transitionKey("leave accept defect");
                })
                .addOption(new TextPhase.OptionInfo(OPTIONS[3]).enabledCondition(() -> AbstractDungeon.player.masterDeck.hasUpgradableCards(), OPTIONS[4]).setOptionResult(
                        (i)->{
                            AbstractCard c = AbstractDungeon.player.masterDeck.getRandomCard(AbstractDungeon.eventRng);
                            c.upgraded = false;
                            c.upgrade();
                            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), (Settings.WIDTH / 2.0f), Settings.HEIGHT / 2.0f));
                            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));


                            transitionKey("leave");
                        }
                ))
        );

        registerPhase("leave accept iron", new TextPhase(DESCRIPTIONS[3]).addOption(OPTIONS[5], (i)->openMap()));
        registerPhase("leave accept defect", new TextPhase(DESCRIPTIONS[4]).addOption(OPTIONS[5], (i)->openMap()));
        registerPhase("leave", new TextPhase(DESCRIPTIONS[5]).addOption(OPTIONS[5], (i)->openMap()));

        transitionKey("start");
    }
}

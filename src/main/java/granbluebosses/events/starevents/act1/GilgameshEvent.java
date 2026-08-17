package granbluebosses.events.starevents.act1;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.cards.event.GilgameshCall;

import static granbluebosses.GranblueBosses.makeID;

public class GilgameshEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(GilgameshEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private boolean pickCard;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(GilgameshEvent.class.getSimpleName() + ".png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    private static int HEAL_AMT = 16;

    public GilgameshEvent() {
        super(EVENT_ID, NAME, IMG);

        this.pickCard = false;
        HEAL_AMT = AbstractDungeon.ascensionLevel >= 15 ? 16 : 12 ;

        CardGroup attacksInMasterDeck = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (c.type == AbstractCard.CardType.ATTACK) attacksInMasterDeck.addToTop(c);
        }

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0], (i)->{
                    this.pickCard = true;
                    AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 2, OPTIONS[3], false, false, false, false);
                })
        );

        registerPhase("leave other", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[6], (i)->openMap()));
        registerPhase("leave common", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[6], (i)->openMap()));
        registerPhase("leave uncommon", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[6], (i)->openMap()));
        registerPhase("leave rare", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[6], (i)->openMap()));


        transitionKey("start");
    }

    @Override
    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && AbstractDungeon.gridSelectScreen.selectedCards.size() == 2) {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            switch (c.rarity){
                case COMMON:
                    c.upgraded = false;

                    c.upgrade();

                    AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), (Settings.WIDTH / 2.0f), Settings.HEIGHT / 2.0f));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));

                    transitionKey("leave common");
                    break;
                case UNCOMMON:
                    AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c, (Settings.WIDTH / 2.0f), Settings.HEIGHT / 2.0f));

                    AbstractDungeon.effectsQueue.add(new CardPoofEffect(c.target_x, c.target_y));

                    AbstractDungeon.player.masterDeck.removeCard(c);

                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new GilgameshCall(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));

                    transitionKey("leave uncommon");
                    break;
                case RARE:

                    AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c, (Settings.WIDTH / 2.0f), Settings.HEIGHT / 2.0f));

                    AbstractDungeon.effectsQueue.add(new CardPoofEffect(c.target_x, c.target_y));

                    AbstractDungeon.player.masterDeck.removeCard(c);

                    AbstractCard reward = new GilgameshCall();

                    reward.upgrade();

                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(reward, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));

                    CardGroup playerAttacks = AbstractDungeon.player.masterDeck.getUpgradableCards().getAttacks();

                    for (AbstractCard attackCard : playerAttacks.group){
                        attackCard.upgrade();
                        AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(attackCard.makeStatEquivalentCopy()));
                    }

                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));

                    transitionKey("leave rare");
                    break;
                default:
                    AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL_AMT));
                    transitionKey("leave other");
                    break;
            }
        }
    }

}

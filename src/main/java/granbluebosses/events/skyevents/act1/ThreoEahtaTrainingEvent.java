package granbluebosses.events.skyevents.act1;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import granbluebosses.GranblueBosses;

import static granbluebosses.GranblueBosses.makeID;

public class ThreoEahtaTrainingEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(ThreoEahtaTrainingEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    public static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private boolean pickCard;

    //For this example, an image from a basegame event is used.
    private static final String IMG = GranblueBosses.eventPath(ThreoEahtaTrainingEvent.class.getSimpleName() + ".png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    public ThreoEahtaTrainingEvent() {
        super(EVENT_ID, NAME, IMG);

        this.pickCard = false;

        int hpLost = AbstractDungeon.ascensionLevel >= 15 ? 13 : 3;

        int maxHpGain = AbstractDungeon.ascensionLevel >= 15 ? 7 : 13;

        int hpHealRest = AbstractDungeon.player != null ?
                AbstractDungeon.ascensionLevel >= 15 ?
                        (int)(AbstractDungeon.player.maxHealth * 0.3) :
                        (int)(AbstractDungeon.player.maxHealth * 0.5) :
                15;

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0] + hpHealRest + OPTIONS[1]).enabledCondition(() -> true, OPTIONS[0] + hpHealRest + OPTIONS[1])
                        .setOptionResult(
                                (i)->
                                {
                                    AbstractDungeon.player.heal(hpHealRest);
                                    transitionKey("rest and leave");
                                }
                        )
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[2] + hpLost + OPTIONS[3] + maxHpGain + OPTIONS[4]).enabledCondition(() -> AbstractDungeon.player != null && AbstractDungeon.player.currentHealth > hpLost && !AbstractDungeon.player.masterDeck.getUpgradableCards().isEmpty(), OPTIONS[7] + hpLost + OPTIONS[8])
                        .setOptionResult(
                                (i)->
                                {
                                    AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, hpLost, DamageInfo.DamageType.HP_LOSS));
                                    AbstractDungeon.player.increaseMaxHp(maxHpGain, false);
                                    AbstractDungeon.player.masterDeck.getUpgradableCards().getRandomCard(AbstractDungeon.eventRng).upgrade();
                                    transitionKey("threo and leave");
                        }
                        )
                )
                .addOption(new TextPhase.OptionInfo(OPTIONS[5]).enabledCondition(() -> AbstractDungeon.player != null && !AbstractDungeon.player.masterDeck.getUpgradableCards().isEmpty(), OPTIONS[9])
                        .setOptionResult(
                                (i)->
                                {
                                    if (AbstractDungeon.player.masterDeck.getUpgradableCards().size() == 1){
                                        AbstractCard c = AbstractDungeon.player.masterDeck.getUpgradableCards().getTopCard();

                                        c.upgrade();
                                        AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), (Settings.WIDTH / 2.0f), Settings.HEIGHT / 2.0f));
                                        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));



                                        transitionKey("eahta and leave");
                                    } else if (AbstractDungeon.player.masterDeck.getUpgradableCards().size() == 2){
                                        AbstractCard c1 = AbstractDungeon.player.masterDeck.getUpgradableCards().getTopCard();
                                        AbstractCard c2 = AbstractDungeon.player.masterDeck.getUpgradableCards().getBottomCard();

                                        c1.upgrade();
                                        AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c1.makeStatEquivalentCopy(), (200 * Settings.scale) + (Settings.WIDTH / 2.0f), Settings.HEIGHT / 2.0f));
                                        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((200 * Settings.scale) + (Settings.WIDTH / 2.0f), Settings.HEIGHT / 2.0f));

                                        c2.upgrade();
                                        AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c2.makeStatEquivalentCopy(), (Settings.WIDTH / 2.0f) - (200 * Settings.scale), Settings.HEIGHT / 2.0f));
                                        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((Settings.WIDTH / 2.0f) - (200 * Settings.scale), Settings.HEIGHT / 2.0f));


                                        transitionKey("eahta and leave");
                                    } else {
                                        this.pickCard = true;
                                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 2, OPTIONS[3], false, false, false, false);
                                        transitionKey("eahta and leave");
                                    }
                                }
                        )
                )
        );

        registerPhase("rest and leave", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[6], (i)->openMap()));
        registerPhase("threo and leave", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[6], (i)->openMap()));
        registerPhase("eahta and leave", new TextPhase(DESCRIPTIONS[3]).addOption(OPTIONS[6], (i)->openMap()));


        transitionKey("start");
    }

    @Override
    public void renderText(SpriteBatch sb) {
        super.renderText(sb);
    }

    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && AbstractDungeon.gridSelectScreen.selectedCards.size() == 2) {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            c.upgrade();
            logMetricCardUpgrade("ThreoEahtaTrainingEvent", "Eahta", c);
            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), (200 * Settings.scale) + (Settings.WIDTH / 2.0f), Settings.HEIGHT / 2.0f));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((200 * Settings.scale) + (Settings.WIDTH / 2.0f), Settings.HEIGHT / 2.0f));

            c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(1);
            c.upgrade();
            logMetricCardUpgrade("ThreoEahtaTrainingEvent", "Eahta", c);
            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(1));
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), (Settings.WIDTH / 2.0f) - (200 * Settings.scale), Settings.HEIGHT / 2.0f));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((Settings.WIDTH / 2.0f) - (200 * Settings.scale), Settings.HEIGHT / 2.0f));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            this.pickCard = false;
            transitionKey("eahta and leave");
        }
    }

}

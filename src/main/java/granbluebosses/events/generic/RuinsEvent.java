package granbluebosses.events.generic;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import granbluebosses.GranblueBosses;

public class RuinsEvent extends PhasedEvent {
    public final static String EVENT_ID = GranblueBosses.makeID("RuinsEvent");
    protected static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    public static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private final static String EVENT_IMAGE_URL = GranblueBosses.eventPath("act1/ruins.png");
    private final int cardsObtained;

    public RuinsEvent() {
        super(EVENT_ID, NAME, EVENT_IMAGE_URL);
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.cardsObtained = 1;
        } else {
            this.cardsObtained = 2;
        }

        this.registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[0] + this.cardsObtained + OPTIONS[1]).enabledCondition(() -> true, OPTIONS[0] + this.cardsObtained + OPTIONS[1])
                        .setOptionResult((i)->{
                            AbstractDungeon.combatRewardScreen.open();

                            AbstractDungeon.combatRewardScreen.clear();
                            AbstractDungeon.getCurrRoom().rewards.clear();

                            for (int k = 0; k < this.cardsObtained; k++){
                                if (AbstractDungeon.eventRng.randomBoolean()){
                                    AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.player.getCardColor()));
                                } else {
                                    AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomPotion()));
                                }
                            }

                            AbstractDungeon.combatRewardScreen.positionRewards();

                            transitionKey("leave");
                        }))
                .addOption(OPTIONS[2], (i)->openMap())
        );
        this.registerPhase("leave", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[2], (i)->openMap())
        );
        transitionKey("start");
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        transitionKey("start");
    }
}

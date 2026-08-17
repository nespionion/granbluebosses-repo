package granbluebosses.events.skyevents.act2;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.BloodPotion;
import com.megacrit.cardcrawl.rewards.RewardItem;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.ancients.OppressusFragorRelic;
import granbluebosses.relics.events.MeteorsLight;

import static granbluebosses.GranblueBosses.makeID;

public class IlsaEvent extends PhasedEvent {
    public static final String EVENT_ID = makeID(IlsaEvent.class.getSimpleName()); //The event's ID

    //The text that will be displayed in the event, loaded based on the ID. The text will be set up later in this tutorial.
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    //For this example, an image from a basegame event is used.
    private static final String IMG_1 = GranblueBosses.eventPath(IlsaEvent.class.getSimpleName() + "1.png");
    private static final String IMG_2 = GranblueBosses.eventPath(IlsaEvent.class.getSimpleName() + "2.png");
    private static final String IMG_3 = GranblueBosses.eventPath(IlsaEvent.class.getSimpleName() + "3.png");
    //To use your own image, it would look more like
    //private static final String IMG = imagePath("events/ExampleEvent.jpg");
    //This would load yourmod/images/events/ExampleEvent.jpg

    public static int HP_LOSS = 12;
    public static int MAX_HP = 6;

    public IlsaEvent() {
        super(EVENT_ID, NAME, IMG_1);

        HP_LOSS = AbstractDungeon.ascensionLevel >= 15 ? 12 : 6;
        MAX_HP = AbstractDungeon.ascensionLevel >= 15 ? 2 : 6;

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0] + HP_LOSS + OPTIONS[1] + MAX_HP + OPTIONS[2], new MeteorsLight(), (i)->{

                    AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, HP_LOSS, DamageInfo.DamageType.HP_LOSS));

                    AbstractDungeon.player.increaseMaxHp(MAX_HP, false);
                    this.imageEventText.loadImage(IMG_2);

                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new MeteorsLight());

                    transitionKey("accept leave");
                })
                .addOption(OPTIONS[3], (i)->{
                    AbstractDungeon.combatRewardScreen.open();
                    AbstractDungeon.combatRewardScreen.clear();
                    AbstractDungeon.getCurrRoom().rewards.clear();
                    AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(new BloodPotion()));

                    AbstractDungeon.combatRewardScreen.positionRewards();

                    this.imageEventText.loadImage(IMG_3);

                    transitionKey("reject leave");
                })
        );

        registerPhase("accept leave", new TextPhase(DESCRIPTIONS[1]).addOption(OPTIONS[4], (i)->openMap()));
        registerPhase("reject leave", new TextPhase(DESCRIPTIONS[2]).addOption(OPTIONS[4], (i)->openMap()));

        transitionKey("start");
    }

    @Override
    public void onEnterRoom() {
        super.onEnterRoom();
        HP_LOSS = AbstractDungeon.ascensionLevel >= 15 ? 12 : 6;
        MAX_HP = AbstractDungeon.ascensionLevel >= 15 ? 6 : 2;
    }
}

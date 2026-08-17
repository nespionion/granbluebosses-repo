package granbluebosses.cards.protobaha.optionCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import granbluebosses.cards.BaseCard;
import granbluebosses.relics.protobaha.StaffOfBahamutCoda;
import granbluebosses.relics.protobaha.StaffOfBahamutRelic;
import granbluebosses.relics.protobaha.StaffOfBahamutCoda;
import granbluebosses.relics.protobaha.StaffOfBahamutRelic;

public class StaffOfBahamut extends BaseCard {

    public static final String CARD_ID = makeID("StaffOfBahamut");

    public StaffOfBahamut() {
        super(
                CARD_ID,
                0,
                CardType.SKILL,
                CardTarget.SELF,
                CardRarity.SPECIAL,
                CardColor.COLORLESS//,
//                cardImage
        );

        this.purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.upgraded){
            StaffOfBahamutCoda r = new StaffOfBahamutCoda();
            AbstractDungeon.player.masterDeck.removeCard(CARD_ID);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);

            r.flash();
        } else {
            StaffOfBahamutRelic r = new StaffOfBahamutRelic();
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);
            r.flash();
            AbstractDungeon.player.masterDeck.removeCard(CARD_ID);
        }
    }

}

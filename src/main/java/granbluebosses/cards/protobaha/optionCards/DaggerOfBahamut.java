package granbluebosses.cards.protobaha.optionCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import granbluebosses.cards.BaseCard;
import granbluebosses.relics.protobaha.DaggerOfBahamutCoda;
import granbluebosses.relics.protobaha.DaggerOfBahamutRelic;

public class DaggerOfBahamut extends BaseCard {

    public static final String CARD_ID = makeID("DaggerOfBahamut");

    public DaggerOfBahamut() {
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
            DaggerOfBahamutCoda r = new DaggerOfBahamutCoda();
            AbstractDungeon.player.masterDeck.removeCard(CARD_ID);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);

            r.flash();
        } else {
            DaggerOfBahamutRelic r = new DaggerOfBahamutRelic();
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);
            r.flash();
            AbstractDungeon.player.masterDeck.removeCard(CARD_ID);
        }
    }
}

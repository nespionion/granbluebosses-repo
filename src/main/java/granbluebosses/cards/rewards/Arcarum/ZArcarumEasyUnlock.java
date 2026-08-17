package granbluebosses.cards.rewards.Arcarum;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import granbluebosses.cards.faahl.ChaosLegionCard;
import granbluebosses.cards.faahl.ParadiseLostCard;
import granbluebosses.cards.rewards.Magna3.*;
import granbluebosses.cards.rewards.other.InchoateWorldCard;
import granbluebosses.config.ConfigMenu;
import me.antileaf.signature.interfaces.EasyUnlockSubscriber;
import me.antileaf.signature.utils.EasyUnlock;

import java.util.ArrayList;
import java.util.HashSet;

public class ZArcarumEasyUnlock implements EasyUnlockSubscriber {

    public static HashSet<String> ids = new HashSet<>();
    @Override
    public EasyUnlock receiveOnGameOver(GameOverScreen screen) {

        if (ConfigMenu.easyUnlockFullArt){
            ids.add(ArcarumDeathCard.ID);
            ids.add(ArcarumDevilCard.ID);
            ids.add(ArcarumHangedManCard.ID);
            ids.add(ArcarumJudgementCard.ID);
            ids.add(ArcarumJusticeCard.ID);
            ids.add(ArcarumMoonCard.ID);
            ids.add(ArcarumStarsCard.ID);
            ids.add(ArcarumSunCard.ID);
            ids.add(ArcarumTemperanceCard.ID);
            ids.add(ArcarumTowerCard.ID);
            ids.add(InchoateWorldCard.ID);
            ids.add(TiamatAuraOmega.CARD_ID);
            ids.add(ColossusIraOmega.CARD_ID);
            ids.add(LeviathanMareOmega.CARD_ID);
            ids.add(YggdrasilArbosOmega.CARD_ID);
            ids.add(LuminieraCredoOmega.CARD_ID);
            ids.add(CelesteAterOmega.CARD_ID);
        }

        if (AbstractDungeon.player != null && AbstractDungeon.player.masterDeck != null && AbstractDungeon.player.masterDeck.findCardById(ParadiseLostCard.ID) != null){
            UnlockTracker.markCardAsSeen(ParadiseLostCard.ID);
            ids.add(ParadiseLostCard.ID);
        }

        if (AbstractDungeon.player != null && AbstractDungeon.player.masterDeck != null && AbstractDungeon.player.masterDeck.findCardById(ChaosLegionCard.ID) != null){
            UnlockTracker.markCardAsSeen(ChaosLegionCard.ID);
            ids.add(ChaosLegionCard.ID);
        }

        ids.remove(null);

        return new EasyUnlock().IDs(new ArrayList<>(ids)); // cards 为空的 EasyUnlock 会自动被忽略
    }
}

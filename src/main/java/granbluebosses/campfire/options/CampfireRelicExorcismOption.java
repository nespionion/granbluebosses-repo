package granbluebosses.campfire.options;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import granbluebosses.GranblueBosses;
import granbluebosses.campfire.effects.CampfireRelicExorcismEffect;

public class CampfireRelicExorcismOption extends AbstractCampfireOption {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public CampfireRelicExorcismOption(boolean active) {
        this.label = TEXT[0];
        this.usable = active;
        this.description = TEXT[1];
        this.img = ImageMaster.CAMPFIRE_TOKE_BUTTON;
    }

    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new CampfireRelicExorcismEffect());
        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(GranblueBosses.makeID(CampfireRelicExorcismOption.class.getSimpleName()));
        TEXT = uiStrings.TEXT;
    }
}
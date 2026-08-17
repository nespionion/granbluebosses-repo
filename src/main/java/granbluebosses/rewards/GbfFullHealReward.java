package granbluebosses.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class GbfFullHealReward extends CustomReward {
    private static final Texture ICON = ImageMaster.TP_HP;

    public int amount;

    public GbfFullHealReward() {
        // TODO : Test Full Heal works correctly
        super(ICON, "Heal 100% HP.", CustomRewardEnums.GBF_FULL_HEAL_REWARD);
        this.amount = AbstractDungeon.player.maxHealth;
    }

    @Override
    public boolean claimReward() {
        AbstractDungeon.player.heal(this.amount);
        return true;
    }
}

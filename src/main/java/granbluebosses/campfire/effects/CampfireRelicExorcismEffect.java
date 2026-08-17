package granbluebosses.campfire.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import granbluebosses.relics.odious.*;
import granbluebosses.util.CampfireUtils;

public class CampfireRelicExorcismEffect  extends AbstractGameEffect {
    private static final float DUR = 2.0F;
    private boolean hasExorcism = false;
    private Color screenColor;

    public CampfireRelicExorcismEffect() {
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = DUR;
        this.screenColor.a = 0.0F;
        ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.updateBlackScreenColor();
        if (this.duration < 1.0F && !this.hasExorcism && CampfireUtils.isExorcismPossible()) {
            this.hasExorcism = true;
            CardCrawlGame.sound.play("ATTACK_MAGIC_SLOW_2");
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT, false);
            this.odiousRelicExorcism();
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        }

    }

    public void odiousRelicExorcism(){
        if (AbstractDungeon.player.hasRelic(OdiousDemonspear.ID)) {
            AbstractDungeon.player.getRelic(OdiousDemonspear.ID).flash();
            AbstractDungeon.player.getRelic(OdiousDemonspear.ID).counter = 0;
        }

        if (AbstractDungeon.player.hasRelic(OdiousTerrorbow.ID)) {
            AbstractDungeon.player.getRelic(OdiousTerrorbow.ID).flash();
            AbstractDungeon.player.getRelic(OdiousTerrorbow.ID).counter = 0;
        }

        if (AbstractDungeon.player.hasRelic(OdiousDemonedge.ID)) {
            AbstractDungeon.player.getRelic(OdiousDemonedge.ID).flash();
            AbstractDungeon.player.getRelic(OdiousDemonedge.ID).counter = 0;
        }

        if (AbstractDungeon.player.hasRelic(OdiousSealhammer.ID)) {
            AbstractDungeon.player.getRelic(OdiousSealhammer.ID).flash();
            AbstractDungeon.player.getRelic(OdiousSealhammer.ID).counter = 0;
        }

        if (AbstractDungeon.player.hasRelic(OdiousBlightrifle.ID)) {
            AbstractDungeon.player.getRelic(OdiousBlightrifle.ID).flash();
            AbstractDungeon.player.getRelic(OdiousBlightrifle.ID).counter = 0;
        }

        if (AbstractDungeon.player.hasRelic(OdiousCodex.ID)){
            if (AbstractDungeon.player.getRelic(OdiousCodex.ID).counter == 0) {
                AbstractDungeon.player.getRelic(OdiousCodex.ID).flash();
                AbstractDungeon.player.getRelic(OdiousCodex.ID).counter = 1;
            } else {
                AbstractDungeon.player.getRelic(OdiousCodex.ID).flash();
                AbstractDungeon.player.getRelic(OdiousCodex.ID).counter = 0;
            }
        }

    }

    private void updateBlackScreenColor() {
        if (this.duration > 1.5F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.5F) * 2.0F);
        } else if (this.duration < 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
        } else {
            this.screenColor.a = 1.0F;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float)Settings.WIDTH, (float)Settings.HEIGHT);
    }

    public void dispose() {
    }
}


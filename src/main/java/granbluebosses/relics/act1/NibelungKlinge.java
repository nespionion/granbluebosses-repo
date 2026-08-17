package granbluebosses.relics.act1;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import granbluebosses.GranblueBosses;
import granbluebosses.relics.BaseRelic;
import granbluebosses.util.primalcards.PrimalColor;

public class NibelungKlinge extends BaseRelic  {

    public static final String RELIC_ID = GranblueBosses.makeID("NibelungKlinge");
    public static AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.NONE;

    private static int magicNumber = 2;

    public NibelungKlinge() {
        super(
                RELIC_ID,       // ID
                "NibelungKlinge",
                PrimalColor.GBF_PRIMAL_COLOR,
                RelicTier.SPECIAL,                              // Rarity
                LandingSound.HEAVY);                            // SFX

    }

    @Override
    public void onEquip() {
        super.onEquip();
        AbstractDungeon.player.increaseMaxHp(magicNumber, false);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 1 && AbstractDungeon.relicRng.randomBoolean(0.05f)){
            this.flash();

            addToBot(new TextAboveCreatureAction(AbstractDungeon.player, "DODGED!"));
            if (info.owner instanceof AbstractMonster){
                this.playEffect((AbstractMonster) info.owner);
                addToBot(new DamageAction(info.owner, new DamageInfo(AbstractDungeon.player, 6, DamageInfo.DamageType.THORNS), effect));
            }
        }
        return 0;
    }

    private void playEffect(AbstractMonster m){

        switch (AbstractDungeon.player.chosenClass){
            case IRONCLAD:
                effect = AbstractGameAction.AttackEffect.SLASH_VERTICAL;
                break;
            case THE_SILENT:
                effect = AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
                break;
            case DEFECT:
                effect = AbstractGameAction.AttackEffect.NONE;
                addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
                break;
            case WATCHER:
                effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
                break;
            default:
                effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public AbstractRelic makeCopy() {
        return new NibelungKlinge();
    }

}
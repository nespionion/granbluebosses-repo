package granbluebosses.cards.rewards.Magna3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import granbluebosses.cards.BaseCard;
import granbluebosses.cards.BaseSignatureCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.util.GeneralUtils.removePrefix;
import static granbluebosses.util.TextureLoader.getCardTextureString;

public class TiamatAuraOmega extends BaseSignatureCard {

    public static final String CARD_ID = makeID("TiamatAuraOmega");

    private static final int DAMAGE = 6;
    private static final int DAMAGE_UPG = 3;
    private static final int BLOCK = 0;
    private static final int BLOCK_UPG = 0;
    private static final int MAGIC = 1;
    private static final int MAGIC_UPG = 1;

    public TiamatAuraOmega() {
        super(
                CARD_ID,
                1,
                CardType.ATTACK,
                CardTarget.ALL_ENEMY,
                CardRarity.RARE,
                PrimalColor.GBF_PRIMAL_COLOR//,
//                cardImage
        );

        this.setDamage(DAMAGE, DAMAGE_UPG);
        this.setBlock(BLOCK, BLOCK_UPG);
        this.setMagic(MAGIC, MAGIC_UPG);

        tags.add(CustomTags.SUMMON_CALL);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new VFXAction(new WhirlwindEffect()));
        addToBot(new DamageAllEnemiesAction(abstractPlayer, this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (!mo.isDying){
                addToBot(new ApplyPowerAction(mo, abstractPlayer, new WeakPower(mo, 3, false), 3));
                addToBot(new ApplyPowerAction(mo, abstractPlayer, new StrengthPower(mo, this.magicNumber), this.magicNumber));
            }
        }
    }
}

package granbluebosses.cards.rewards.Magna3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import granbluebosses.cards.BaseCard;
import granbluebosses.cards.BaseSignatureCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.util.GeneralUtils.removePrefix;
import static granbluebosses.util.TextureLoader.getCardTextureString;

public class YggdrasilArbosOmega extends BaseSignatureCard {

    public static final String CARD_ID = makeID("YggdrasilArbosOmega");
    private static final int DAMAGE = 6;
    private static final int DAMAGE_UPG = 3;
    private static final int BLOCK = 0;
    private static final int BLOCK_UPG = 0;
    private static final int MAGIC = 3;
    private static final int MAGIC_UPG = 2;

    public YggdrasilArbosOmega() {
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
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new DamageAllEnemiesAction(p, this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        for (int i = 0; i < this.magicNumber; i++){
            if (AbstractDungeon.cardRng.randomBoolean()){
                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
            } else {
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
            }
        }
    }
}

package granbluebosses.cards.rewards.Magna3;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import granbluebosses.cards.BaseCard;
import granbluebosses.cards.BaseSignatureCard;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

public class LeviathanMareOmega extends BaseSignatureCard {

    public static final String CARD_ID = makeID("LeviathanMareOmega");
    private static final int DAMAGE = 6;
    private static final int DAMAGE_UPG = 6;
    private static final int BLOCK = 0;
    private static final int BLOCK_UPG = 0;
    private static final int MAGIC = 3;
    private static final int MAGIC_UPG = 0;

    public LeviathanMareOmega() {
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
    public void use(AbstractPlayer p, AbstractMonster m) {

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (!mo.isDying){
                addToBot(new RemoveAllBlockAction(mo, p));
            }
        }

        addToBot(new DamageAllEnemiesAction(p, this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.POISON));

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (!mo.isDying){
                addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber));
            }
        }

    }
}

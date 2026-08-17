package granbluebosses.cards.rewards.Magna3;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import granbluebosses.cards.BaseCard;
import granbluebosses.cards.BaseSignatureCard;
import granbluebosses.powers.common.PhalanxPower;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

public class ColossusIraOmega extends BaseSignatureCard {

    public static final String CARD_ID = makeID("ColossusIraOmega");
    private static final int DAMAGE = 6;
    private static final int DAMAGE_UPG = 3;
    private static final int BLOCK = 6;
    private static final int BLOCK_UPG = 3;
    private static final int MAGIC = 25;
    private static final int MAGIC_UPG = 0;


    public ColossusIraOmega() {
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
        addToBot(new DamageAllEnemiesAction(p, this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyPowerAction(p, p, new PhalanxPower(p, this.magicNumber), this.magicNumber));
    }
}

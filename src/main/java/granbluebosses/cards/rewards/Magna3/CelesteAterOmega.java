package granbluebosses.cards.rewards.Magna3;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import granbluebosses.cards.BaseCard;
import granbluebosses.cards.BaseSignatureCard;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

public class CelesteAterOmega extends BaseSignatureCard {

    public static final String CARD_ID = makeID("CelesteAterOmega");
    private static final int DAMAGE = 0;
    private static final int DAMAGE_UPG = 0;
    private static final int BLOCK = 0;
    private static final int BLOCK_UPG = 0;
    private static final int MAGIC = 0;
    private static final int MAGIC_UPG = 0;

    public CelesteAterOmega() {
        super(
                CARD_ID,
                1,
                CardType.ATTACK,
                CardTarget.ENEMY,
                CardRarity.RARE,
                PrimalColor.GBF_PRIMAL_COLOR//,
//                cardImage
        );

        this.setDamage(DAMAGE, DAMAGE_UPG);
        this.setBlock(BLOCK, BLOCK_UPG);
        this.setMagic(MAGIC, MAGIC_UPG);
        this.setCostUpgrade(0);

        tags.add(CustomTags.SUMMON_CALL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int poisonAmt = m.currentBlock;
        this.calculateCardDamage(m);
        addToBot(new RemoveAllBlockAction(m, p));

        addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, poisonAmt), poisonAmt));

    }
}

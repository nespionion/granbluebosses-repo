package granbluebosses.cards.rewards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import granbluebosses.cards.BaseCard;

public class GraniCall extends BaseCard {

    public static final String CARD_ID = makeID("GraniCall");
    private static final int DAMAGE = 6;
    private static final int DAMAGE_UPG = 0;
    private static final int BLOCK = 0;
    private static final int BLOCK_UPG = 0;
    private static final int MAGIC = 2;
    private static final int MAGIC_UPG = 0;

    public GraniCall() {
        super(
                CARD_ID,
                1,
                CardType.ATTACK,
                CardTarget.ENEMY,
                CardRarity.SPECIAL,
                CardColor.COLORLESS//,
//                cardImage
        );

        this.setDamage(DAMAGE, DAMAGE_UPG);
        this.setBlock(BLOCK, BLOCK_UPG);
        this.setMagic(MAGIC, MAGIC_UPG);

        this.setExhaust(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new VFXAction(new LightningEffect(abstractMonster.hb.cX, abstractMonster.drawY), 0.2f));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

        addToBot(new ApplyPowerAction(abstractMonster,AbstractDungeon.player, new StrengthPower(abstractMonster, -magicNumber), -magicNumber));
        if (!this.upgraded && !abstractMonster.hasPower("Artifact")){
            addToBot(new ApplyPowerAction(abstractMonster,AbstractDungeon.player, new GainStrengthPower(abstractMonster, magicNumber), magicNumber));
        }
    }
}

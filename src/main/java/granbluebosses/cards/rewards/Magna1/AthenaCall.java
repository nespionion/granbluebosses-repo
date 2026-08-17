package granbluebosses.cards.rewards.Magna1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import granbluebosses.cards.BaseCard;
import granbluebosses.config.ConfigMenu;
import granbluebosses.powers.common.PhalanxPower;
import granbluebosses.util.CustomTags;
import granbluebosses.util.primalcards.PrimalColor;

import static granbluebosses.util.GeneralUtils.removePrefix;
import static granbluebosses.util.TextureLoader.getCardTextureString;

public class AthenaCall extends BaseCard {

    public static final String CARD_ID = makeID("AthenaCall");
    private static final int DAMAGE = 6;
    private static final int DAMAGE_UPG = 0;
    private static final int BLOCK = 0;
    private static final int BLOCK_UPG = 0;
    private static final int MAGIC = 30;
    private static final int MAGIC_UPG = 0;

    public AthenaCall() {
        super(
                CARD_ID,
                1,
                CardType.ATTACK,
                CardTarget.ENEMY,
                CardRarity.UNCOMMON,
                PrimalColor.GBF_PRIMAL_COLOR//,
//                cardImage
        );

        this.setDamage(DAMAGE, DAMAGE_UPG);
        this.setBlock(BLOCK, BLOCK_UPG);
        this.setMagic(MAGIC, MAGIC_UPG);

        this.setExhaust(true, false);

        tags.add(CustomTags.SUMMON_CALL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player, new PhalanxPower(AbstractDungeon.player, magicNumber), magicNumber));
    }

    @Override
    public void render(SpriteBatch sb) {
        if (ConfigMenu.modestyFilter){
            String img = getCardTextureString(removePrefix(makeID("AthenaCallCen")), this.type);
            this.textureImg = img;
            if (img != null) {
                this.loadCardImage(img);
            }
        }
        super.render(sb);
    }
}

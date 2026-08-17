package granbluebosses.powers.relic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import granbluebosses.powers.BasePower;

import static granbluebosses.GranblueBosses.makeID;

public class CelesteEnmity extends BasePower {
    public static final String POWER_ID = makeID("CelesteEnmity");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static PowerStrings getPowerStrings(String ID) {return CardCrawlGame.languagePack.getPowerStrings(ID);}
    private static PowerStrings powerStrings = getPowerStrings(POWER_ID);
    private static int magicNumber = -1;

    public CelesteEnmity(AbstractCreature owner) {
        this(owner, magicNumber);
    }

    public CelesteEnmity(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.type == AbstractCard.CardType.ATTACK){
            if (AbstractDungeon.player.currentHealth * 2 < AbstractDungeon.player.maxHealth){
                addToBot(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            }
            if (AbstractDungeon.player.currentHealth * 5 < AbstractDungeon.player.maxHealth){
                addToBot(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            }
            if (AbstractDungeon.player.currentHealth * 10 < AbstractDungeon.player.maxHealth){
                addToBot(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            }
            if (AbstractDungeon.player.currentHealth == 1){
                addToBot(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.name = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        this.description = DESCRIPTIONS[0];
    }
}

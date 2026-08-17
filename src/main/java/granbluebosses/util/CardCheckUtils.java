package granbluebosses.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;
import granbluebosses.GranblueBosses;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;

public class CardCheckUtils {

    public static boolean checkCard(AbstractCard card, AbstractCard.CardRarity rarity, AbstractCard.CardType cardType, AbstractCard.CardTarget cardTarget, int cardCost){
        if (card == null){
            return false;
        }

        if (rarity != null &&
                !( // The following conditions must match for the card to pass, so return false if the ! for all conditions being false passes
                        rarity == card.rarity ||
                                (rarity == AbstractCard.CardRarity.COMMON && (card.rarity == AbstractCard.CardRarity.BASIC)) ||
                                (rarity == AbstractCard.CardRarity.SPECIAL && (
                                        card.rarity != AbstractCard.CardRarity.COMMON &&
                                                card.rarity != AbstractCard.CardRarity.BASIC &&
                                                card.rarity != AbstractCard.CardRarity.UNCOMMON &&
                                                card.rarity != AbstractCard.CardRarity.RARE &&
                                                card.rarity != AbstractCard.CardRarity.CURSE
                                ))

                )
        ){
            return false;
        }

        if (cardType != null && cardType != card.type){
            return false;
        }

        if (cardTarget != null &&
                !( // The following conditions must match for the card to pass, so return false if the ! for all conditions being false passes
                        cardTarget == card.target ||
                                (card.target == AbstractCard.CardTarget.ALL && cardTarget != AbstractCard.CardTarget.NONE) ||
                                (card.target == AbstractCard.CardTarget.SELF_AND_ENEMY && (cardTarget == AbstractCard.CardTarget.SELF || cardTarget == AbstractCard.CardTarget.ENEMY)) ||
                                (cardTarget == AbstractCard.CardTarget.SELF && card.target == AbstractCard.CardTarget.NONE)

                )
        ){
            return false;
        }
        
        if (cardCost != -1 && card.cost != -1 && cardCost <= card.costForTurn){
            return false;
        }

        if (cardCost != -1 && card.cost == -1 && card.energyOnUse <= cardCost){
            return false;
        }

        return true;
    }

    public static boolean checkCard(AbstractCard card, AbstractCard.CardTags cardTag){
        return cardTag == null || card.hasTag(cardTag);
    }

    public static String generateConditionString(int amount, AbstractCard.CardTags cardTag){
        String retVal = "#b" + amount;

        if (cardTag != null){
            retVal = retVal + " #y" + cardTag.toString().charAt(0) +
                    (cardTag.toString().length() > 1 ?
                    cardTag.toString().substring(1).toLowerCase():
                    "")
            ;
        }
        retVal = retVal + " card" + (amount == 1 ? "." : "s.");

        return retVal;

    }
    
    public static String generateConditionString(int amount, AbstractCard.CardRarity cardRarity, AbstractCard.CardType cardType, AbstractCard.CardTarget cardTarget, int cardCost){
        String retVal = "#b" + amount;

        if (cardRarity != null){
            retVal = retVal + " #y" + cardRarity.toString().charAt(0) +
                    (cardRarity.toString().length() > 1 ?
                            cardRarity.toString().substring(1).toLowerCase():
                            "")
            ;
        }

        GranblueBosses.logger.info("Generating Conditions String for card type " + cardType);
        if (cardType != null){
            String cardTypeString = cardType.toString().length() > 1 ?
                    cardType.toString().charAt(0) + cardType.toString().substring(1).toLowerCase() :
                    cardType.toString();

            if (cardType.toString().charAt(cardType.toString().length() - 1) == 's' || cardType.toString().charAt(cardType.toString().length() - 1) == 'S'){
                retVal = retVal + " #y" + cardTypeString + (amount == 1 ? "" : "es");
            } else {
                retVal = retVal + " #y" + cardTypeString + (amount == 1 ? "" : "s");
            }
        } else {
            retVal = retVal + " card" + (amount == 1 ? "" : "s");
        }

        if (cardCost > 0){
            retVal = retVal + " that cost" + (amount == 1 ? "s" : "") + " " + cardCost + " or more Energy";
        }

        if (cardTarget != null){
            retVal = retVal + (cardCost == -1 ? " that" : " and");

            switch (cardTarget){
                case ENEMY:
                    retVal = retVal + " target" + (amount == 1 ? "s" : "") + " 1 enemy";
                    break;
                case ALL_ENEMY:
                case ALL:
                    retVal = retVal + " target" + (amount == 1 ? "s" : "") + " all enemies";
                    break;
                case SELF:
                case NONE:
                    retVal = retVal + " target" + (amount == 1 ? "s" : "") + " yourself";
                    break;
                default:
                    break;
            }
        }
        
        return retVal;
    }

    public static String generateConditionString(int amount, AbstractPower.PowerType powerType, boolean mustBeDistinct, boolean isArtifactAgnostic){
        String retVal = "#b" + amount;

        if (mustBeDistinct && amount == 1){
            if (powerType == null){
                retVal = retVal + " #yPower";
            } else if (powerType == AbstractPower.PowerType.BUFF) {
                retVal = retVal + " #yBuff";
            } else if (powerType == AbstractPower.PowerType.DEBUFF) {
                retVal = retVal + " #yDebuff";
            } else {
                retVal = retVal + " #y" +
                        getPowerTypeString(powerType)
                        + " #yPower";
            }
            retVal = retVal + " (multiple stacks still count as 1 application)";
        } else if (mustBeDistinct){
            if (powerType == null){
                retVal = retVal + " #yPowers";
            } else if (powerType == AbstractPower.PowerType.BUFF) {
                retVal = retVal + " #yBuffs";
            } else if (powerType == AbstractPower.PowerType.DEBUFF) {
                retVal = retVal + " #yDebuffs";
            } else {
                retVal = retVal + " #y" +
                        getPowerTypeString(powerType)
                        + " #yPowers";
            }
            retVal = retVal + " (multiple stacks still count as 1 application)";
        } else if (amount == 1) {
            retVal = retVal + " stack of any";
            if (powerType == null){
                retVal = retVal + " #yPower";
            } else if (powerType == AbstractPower.PowerType.BUFF) {
                retVal = retVal + " #yBuff";
            } else if (powerType == AbstractPower.PowerType.DEBUFF) {
                retVal = retVal + " #yDebuffs";
            } else {
                retVal = retVal + " #y" +
                        getPowerTypeString(powerType)
                        + " #yPower";
            }
        } else {
            retVal = retVal + " stacks of any";
            if (powerType == null){
                retVal = retVal + " #yPower";
            } else if (powerType == AbstractPower.PowerType.BUFF) {
                retVal = retVal + " #yBuff";
            } else if (powerType == AbstractPower.PowerType.DEBUFF) {
                retVal = retVal + " #yDebuffs";
            } else {
                retVal = retVal + " #y" + powerType + " #yPower";
            }
        }

        return retVal;
    }

    public static String getPowerTypeString(AbstractPower.PowerType powerType){
        return  (powerType.toString().length() > 1 ?
                        powerType.toString().charAt(0) + powerType.toString().substring(1).toLowerCase():
                        powerType.toString());
    }

    public static String generateConditionString(int amountOfHits, int dmgThreshold, DamageInfo.DamageType damageType){
        String retVal = "";

        if (damageType == null || damageType == DamageInfo.DamageType.HP_LOSS){
            retVal = retVal + "loses #b" + dmgThreshold + " HP at once " + amountOfHits + (amountOfHits == 1 ? " more time." : " more times.");
        } else if (damageType == DamageInfo.DamageType.NORMAL){
            retVal = retVal + "takes #b" + amountOfHits + (amountOfHits == 1 ? " hit from an attack that deals " : " hits from attacks that deal") + dmgThreshold + " damage or more.";
        } else if (damageType == DamageInfo.DamageType.THORNS){
            retVal = retVal + "takes #b" + amountOfHits + (amountOfHits == 1 ? " hit of " : " hits of ") + dmgThreshold + " #y${modID}:Magic_Damage or more.";

        } else {
            retVal = retVal + "takes #b" + dmgThreshold + " " + damageType + " damage " + amountOfHits + (amountOfHits == 1 ? " more time." : " more times.");
        }

        return retVal;
    }

    public static String generateConditionString(int amountOfDmg, DamageInfo.DamageType damageType){
        String retVal = "";

        if (damageType == null || damageType == DamageInfo.DamageType.HP_LOSS){
            retVal = retVal + "loses #b" + amountOfDmg + " HP.";
        } else if (damageType == DamageInfo.DamageType.NORMAL){
            retVal = retVal + "takes #b" + amountOfDmg + " damage from attacks.";
        } else if (damageType == DamageInfo.DamageType.THORNS){
            retVal = retVal + "takes #b" + amountOfDmg + " #y${modID}:Magic_Damage.";
        } else {
            retVal = retVal + "takes #b" + amountOfDmg + " " + damageType.toString().toLowerCase() + " damage.";
        }

        return retVal;
    }

    public static String generateConditionStringCN(int amount, AbstractCard.CardTags cardTag) {
        String retVal = "#b" + amount;

        if (cardTag != null) {
            retVal += " #y" + getCardTagStringCN(cardTag);
        }

        retVal += " 张牌。";

        return retVal;
    }


    public static String generateConditionStringCN(
            int amount,
            AbstractCard.CardRarity cardRarity,
            AbstractCard.CardType cardType,
            AbstractCard.CardTarget cardTarget,
            int cardCost) {

        String retVal = "#b" + amount;

        if (cardRarity != null) {
            retVal += " #y" + getCardRarityStringCN(cardRarity);
        }

        GranblueBosses.logger.info(
                "Generating Chinese Conditions String for card type " + cardType
        );

        if (cardType != null) {
            retVal += " #y" + getCardTypeStringCN(cardType);
        }

        retVal += " 牌";

        if (cardCost > 0) {
            retVal += "，费用为 " + cardCost + " 或更高";
        }

        if (cardTarget != null) {
            switch (cardTarget) {
                case ENEMY:
                    retVal += "，以1名敌人为目标";
                    break;

                case ALL_ENEMY:
                case ALL:
                    retVal += "，以所有敌人为目标";
                    break;

                case SELF:
                case NONE:
                    retVal += "，以自身为目标";
                    break;

                default:
                    break;
            }
        }

        return retVal;
    }


    public static String generateConditionStringCN(
            int amount,
            AbstractPower.PowerType powerType,
            boolean mustBeDistinct,
            boolean isArtifactAgnostic) {

        String retVal = "#b" + amount;

        String powerString;

        if (powerType == null) {
            powerString = "#y能力";
        } else if (powerType == AbstractPower.PowerType.BUFF) {
            powerString = "#y增益";
        } else if (powerType == AbstractPower.PowerType.DEBUFF) {
            powerString = "#y减益";
        } else {
            powerString = "#y" + getPowerTypeStringCN(powerType) + " #y能力";
        }

        if (mustBeDistinct) {
            retVal += " 个不同的 " + powerString;
            retVal += "（同一效果的多层叠加仍只算作1次施加）";
        } else {
            retVal += " 层任意 " + powerString;
        }

        return retVal;
    }


    public static String getPowerTypeStringCN(AbstractPower.PowerType powerType) {
        if (powerType == null) {
            return "能力";
        }

        switch (powerType) {
            case BUFF:
                return "增益";

            case DEBUFF:
                return "减益";

            default:
                /*
                 * Fallback for modded/custom PowerTypes.
                 * Add additional mappings here if the mod defines more types.
                 */
                return powerType.toString();
        }
    }


    public static String generateConditionStringCN(
            int amountOfHits,
            int dmgThreshold,
            DamageInfo.DamageType damageType) {

        String retVal = "";

        if (damageType == null || damageType == DamageInfo.DamageType.HP_LOSS) {

            retVal += "再有 #b" + amountOfHits
                    + " 次单次失去至少 #b"
                    + dmgThreshold
                    + " 点生命。";

        } else if (damageType == DamageInfo.DamageType.NORMAL) {

            retVal += "再受到 #b"
                    + amountOfHits
                    + " 次单次造成至少 #b"
                    + dmgThreshold
                    + " 点伤害的攻击。";

        } else if (damageType == DamageInfo.DamageType.THORNS) {

            retVal += "再受到 #b"
                    + amountOfHits
                    + " 次至少 #b"
                    + dmgThreshold
                    + " 点 #y${modID}:Magic_Damage。";

        } else {

            retVal += "再有 #b"
                    + amountOfHits
                    + " 次受到至少 #b"
                    + dmgThreshold
                    + " 点"
                    + getDamageTypeStringCN(damageType)
                    + "伤害。";
        }

        return retVal;
    }


    public static String generateConditionStringCN(
            int amountOfDmg,
            DamageInfo.DamageType damageType) {

        String retVal = "";

        if (damageType == null || damageType == DamageInfo.DamageType.HP_LOSS) {

            retVal += "失去 #b"
                    + amountOfDmg
                    + " 点生命。";

        } else if (damageType == DamageInfo.DamageType.NORMAL) {

            retVal += "受到来自攻击的 #b"
                    + amountOfDmg
                    + " 点伤害。";

        } else if (damageType == DamageInfo.DamageType.THORNS) {

            retVal += "受到 #b"
                    + amountOfDmg
                    + " 点 #y${modID}:Magic_Damage。";

        } else {

            retVal += "受到 #b"
                    + amountOfDmg
                    + " 点"
                    + getDamageTypeStringCN(damageType)
                    + "伤害。";
        }

        return retVal;
    }


    /*
     * ------------------------------------------------------------
     * Chinese translation helpers
     * ------------------------------------------------------------
     */

    public static String getCardRarityStringCN(AbstractCard.CardRarity rarity) {
        if (rarity == null) {
            return "";
        }

        switch (rarity) {
            case BASIC:
                return "基础";

            case COMMON:
                return "普通";

            case UNCOMMON:
                return "罕见";

            case RARE:
                return "稀有";

            case SPECIAL:
                return "特殊";

            case CURSE:
                return "诅咒";

            default:
                return rarity.toString();
        }
    }


    public static String getCardTypeStringCN(AbstractCard.CardType cardType) {
        if (cardType == null) {
            return "";
        }

        switch (cardType) {
            case ATTACK:
                return "攻击";

            case SKILL:
                return "技能";

            case POWER:
                return "能力";

            case STATUS:
                return "状态";

            case CURSE:
                return "诅咒";

            default:
                return cardType.toString();
        }
    }


    public static String getCardTagStringCN(AbstractCard.CardTags cardTag) {
        if (cardTag == null) {
            return "";
        }

        /*
         * CardTags may also contain tags created by mods, so using the
         * string representation here makes it easy to extend this table.
         */
        String tag = cardTag.toString();

        switch (tag) {
            case "STRIKE":
                return "打击";

            case "STARTER_STRIKE":
                return "初始打击";

            case "STARTER_DEFEND":
                return "初始防御";

            case "HEALING":
                return "治疗";

            default:
                /*
                 * Add the Granblue-specific CardTags here if there are any.
                 */
                return tag;
        }
    }


    public static String getDamageTypeStringCN(DamageInfo.DamageType damageType) {
        if (damageType == null) {
            return "";
        }

        switch (damageType) {
            case NORMAL:
                return "攻击";

            case HP_LOSS:
                return "生命流失";

            case THORNS:
                return "魔法";

            default:
                /*
                 * Useful if another mod or this mod adds custom damage types.
                 */
                return damageType.toString();
        }
    }
}

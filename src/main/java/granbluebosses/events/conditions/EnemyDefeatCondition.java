package granbluebosses.events.conditions;

import basemod.eventUtil.util.Condition;

import java.util.HashSet;

public class EnemyDefeatCondition implements Condition {

    public String enemyToDefeat;
    public static HashSet<String> enemiesDefeated = new HashSet<>();

    public EnemyDefeatCondition(String enemyToDefeat){
        this.enemyToDefeat = enemyToDefeat;
    }

    public static void addDefeatedEnemy(String enemyID){
        enemiesDefeated.add(enemyID);
    }

    @Override
    public boolean test() {
        return enemiesDefeated.contains(this.enemyToDefeat);
//        for (String id : enemiesDefeated){
//            if (enemyToDefeat.equals(id)){
//                return true;
//            }
//        }
//        return false;
    }
}

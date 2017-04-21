package main.java.com.questlife.questlife.town;

import main.java.com.questlife.questlife.battle.Battle;
import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by Busch on 20.04.2017.
 */
public class Field {

    private static final int ENEMYAMOUNT = 5;
    private static final int LOOPS = 5;
    private List<Enemy> enemiesInField= new ArrayList<>();
    private Hero hero;
    private int loops;

    public Field(Hero hero) {
        this.hero = hero;
        this.loops = LOOPS;
        initializeField();
    }

    public Field(Hero hero, int loops) {
        this.hero = hero;
        this.loops = loops;
        initializeField();
    }

    private void initializeField() {
        // Creates a field for the hero to wander
        // Will also create ENEMYAMOUNT different types of enemies
        // These will be with fixed stats during one visit for now

        //TODO: Every battle gets same enemies, but different stats on those

        Generator generator = new Generator();
        Enemy[] enemy = new Enemy[ENEMYAMOUNT];
        String enemyName;
        AttackType attackType = AttackType.PHYSICAL;

        // We want the attackType to be physical. But some enemies ought to be magical.
        for(int i=0; i < ENEMYAMOUNT; i++) {
            enemyName = generator.generateEnemyName();
            if(enemyName.contains("Blue")) {
                attackType = AttackType.MAGICAL;
            }
            enemy[i] = new Enemy(enemyName,hero.getLevel(),attackType);
        }

        Collections.addAll(enemiesInField, enemy);

    }

    public void runBattles(Quest activeQuest) {

        Generator generator = new Generator();

        // have hero in field until he dies or completes his quest or was in field long enough
        // Can
        int battleCtr =0;

        while (activeQuest.getIsActive() && battleCtr <= loops) {
            List<Enemy> enemiesInBattle = new ArrayList<>();
            int rndIdx1 = generator.generateNumber()%enemiesInField.size();
            int rndIdx2 = generator.generateNumber()%enemiesInField.size();
            if(rndIdx1<=rndIdx2)
                enemiesInBattle = enemiesInField.subList(rndIdx1,rndIdx2);
            else
                enemiesInBattle = enemiesInField.subList(rndIdx2,rndIdx1);
            Battle battle = new Battle(hero, enemiesInBattle);
            if(battle.runBattle()) {
                battleCtr++;
            } else {
                // end the field trip, hero died
                return;
            }
        }
        // If code gets here:
        // Hero survived, gained gold, completed quest
    }
}

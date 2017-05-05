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
public class Field implements Runnable{

    private static final int ENEMYAMOUNT = 5;
    private static final int LOOPS = 5;
    private List<Enemy> enemiesInField= new ArrayList<>();
    private Hero hero;
    private int loops;
    private Quest activeQuest;

    public Field(Hero hero) {
        this.hero = hero;
        this.loops = LOOPS;
        initializeField();
    }

    public Field(Hero hero, List<Enemy> enemyList) {
        this.hero = hero;
        this.loops = LOOPS;
        initializeField(enemyList);
    }

    public Field(Hero hero, int loops) {
        this.hero = hero;
        this.loops = loops;
        initializeField();
    }


    public Field(Hero hero, int loops, List<Enemy> enemyList) {
        this.hero = hero;
        this.loops = loops;
        initializeField(enemyList);
    }

    private void initializeField(List<Enemy> enemyList) {
        Generator generator = new Generator();
        Enemy[] enemy = new Enemy[ENEMYAMOUNT];
        activeQuest = hero.getActiveQuest();
        boolean questEnemyInField = false;

        int counter=0;

        while(!questEnemyInField || counter<5) {
            // We want to circle around the buffer until we get the enemy we need for our quest!
            int pos = counter%ENEMYAMOUNT;
            // Add a random enemy from the list
            enemy[pos] = enemyList.get(generator.generateNumber()%enemyList.size());

            // Check if the hero has an active quest
            if(activeQuest != null) {
                // Check if we have the quest-enemy
                if (enemy[pos].getName().equals(activeQuest.getEnemyType().getName())) {
                    questEnemyInField = true;
                }
            } else {
                questEnemyInField = true;
            }
            counter++;
        }

        Collections.addAll(enemiesInField, enemy);

    }

    private void initializeField() {
        // Creates a field for the hero to wander
        // Will also create ENEMYAMOUNT different types of enemies
        // These will be with fixed stats during one visit for now

        Generator generator = new Generator();
        Enemy[] enemy = new Enemy[ENEMYAMOUNT];
        String enemyName;
        AttackType attackType = AttackType.PHYSICAL;
        activeQuest = hero.getActiveQuest();

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

        //Running battles in a different thread to not bog down everything between battles

        Thread fieldThread = new Thread(this, "Field Battle Thread");

        if(activeQuest== null) {
            this.activeQuest = null;
        } else if (!this.activeQuest.equals(activeQuest)) {
            this.activeQuest = activeQuest;
        }
        fieldThread.start();


        /*Thread fieldThread = new Thread();
        fieldThread.run();

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
*/

    }

    @Override
    public void run() {

        System.out.println("Sending out hero!");

        Generator generator = new Generator();

        // have hero in field until he dies or completes his quest or was in field long enough
        // Can
        int battleCtr =0;
        boolean questActive = true;

        if(activeQuest == null) {
            questActive = false;
        }

        while (questActive || (battleCtr <= loops)) {
            List<Enemy> enemiesInBattle = new ArrayList<>();

            // Generate a random amount of randomly picked enemies to add to the battle.
            for(int i = 0; i<1+generator.generateNumber()%ENEMYAMOUNT; i++) {
                Enemy enemy = enemiesInField.get(generator.generateNumber()%ENEMYAMOUNT);

                /*
                Set the difficulty of the generated enemy to be a range of 2 around the hero by subtracting and then adding some random number
                subtract 2-add 4 = herolevel+2
                subtract 2-add 2 = herolevel
                subtract 2-add 1 = herolevel-1
                */
                int difficulty = hero.getLevel()-generator.generateNumber()%3;
                difficulty = difficulty+generator.generateNumber()%5;

                if(difficulty == 0) {
                    difficulty = hero.getLevel();
                }

                enemy.setHerolevel(difficulty);

                enemiesInBattle.add(enemy);
            }

            Battle battle = new Battle(hero, enemiesInBattle);
            System.out.println(hero.getName() + " engages "+enemiesInBattle.size()+" enemies!");

            questActive = activeQuest != null && activeQuest.getIsActive();

            StringBuilder enemies= new StringBuilder();
            for(int i = 0; i<enemiesInBattle.size();i++) {
                if(battle.getParticipatingEnemyAt(i).getHealth()<=0) {
                    continue;
                }
                enemies.append(battle.getParticipatingEnemyAt(i).getName());
                if(i<enemiesInBattle.size()-1) {
                    enemies.append(" and a ");
                }
            }
            System.out.println("Hero fighting "+enemies);

            if(battle.runBattle()) {
                battleCtr++;
                try {
                    System.out.println("Hero survived "+battleCtr+" Battles.");
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // end the field trip, hero died
                return;
            }
        }
        // If code gets here:
        // Hero survived, gained gold, completed quest



    }
}

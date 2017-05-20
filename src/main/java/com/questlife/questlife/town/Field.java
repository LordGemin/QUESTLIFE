package main.java.com.questlife.questlife.town;

import javafx.concurrent.Task;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.battle.Battle;
import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.AbstractPotions;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Busch on 20.04.2017.
 */
public class Field extends Task {

    private static final int ENEMYAMOUNT = 5;
    private static final int LOOPS = 5;


    private List<String> enemiesInField= new ArrayList<>();
    private List<Enemy> enemiesInBattle = new ArrayList<>();
    private Hero hero;
    private int goldGained;
    private int loops;
    private int battleCtr;


    public Field() {

    }

    public Field(Hero hero, List<String> enemyList) {
        this.hero = hero;
        this.loops = LOOPS;
        initializeField(enemyList);
    }

/*

    public Field(Hero hero, int loops, List<Enemy> enemyList) {
        this.hero = hero;
        this.loops = loops;
        initializeField(enemyList);
    }*/

    private void initializeField(List<String> enemyList) {
        Generator generator = new Generator();

        int counter=0;

        while(counter<5) {
            // Add a random enemy from the list, where attackType is magical if it has blue in the title;
            String enemyName = enemyList.get(generator.generateNumber()%enemyList.size());
            enemiesInField.add(enemyName);

            counter++;
        }
    }
/*
    private void initializeField() {
        // Creates a field for the hero to wander
        // Will also create ENEMYAMOUNT different types of enemies
        Generator generator = new Generator();
        for(int i=0; i < ENEMYAMOUNT; i++) {
            enemiesInField.add(generator.generateEnemyName());
        }
    }*/

    public void run() {

        System.out.println("Sending out hero!");

        Generator generator = new Generator();

        // have hero in field until he dies or completes his quest or was in field long enough
        // Can
        battleCtr =0;

        while (battleCtr < loops) {


            // Generate a random amount of randomly picked enemies to add to the battle.
            for(int i = 0; i<1+generator.generateNumber()%ENEMYAMOUNT; i++) {

                // A new enemy object for every single enemy in every battle. Eats memory, but garbage collection should handle this fine.
                Enemy enemy = new Enemy(enemiesInField.get(generator.generateNumber()%ENEMYAMOUNT),1);

                /*
                Set the difficulty of the generated enemy to be a range of 2 around the hero by subtracting and then adding some random number
                subtract 2-add 4 = herolevel+2
                subtract 2-add 2 = herolevel
                subtract 2-add 1 = herolevel-1
                TODO: We want difficulty to fluctuate 10% around the heroes level
                */
                int difficulty = hero.getLevel()-generator.generateNumber()%3;
                difficulty = difficulty+generator.generateNumber()%5;

                if(difficulty == 0) {
                    difficulty = hero.getLevel();
                }

                enemy.setHerolevel(difficulty);

                enemiesInBattle.add(enemy);
            }

            System.out.println(hero.getName() + " engages "+enemiesInBattle.size()+" enemies!");

            StringBuilder enemies= new StringBuilder();
            for(int i = 0; i<enemiesInBattle.size();i++) {
                if(getParticipatingEnemyAt(i).getHealth()<=0) {
                    continue;
                }
                enemies.append(getParticipatingEnemyAt(i).getName());
                if(i<enemiesInBattle.size()-1) {
                    enemies.append(" and a ");
                }
            }
            System.out.println("Hero fighting "+enemies);

            if(runBattle()) {
                battleCtr++;
                try {
                    // Clear the scene so that old objects can be deleted peacefully
                    enemiesInBattle.clear();
                    System.out.println("Hero survived "+battleCtr+" Battles.\n");
                    updateMessage(hero.getHealth()+"/"+hero.getMaxHealth());
                    System.out.println("Looking for next enemy...\n");
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                updateMessage(hero.getHealth()+"/"+hero.getMaxHealth());
                // end the field trip, hero died
                return;
            }

        }

        //TODO: Update labels in mainLayout
        // If code gets here:
        // Hero survived, gained gold, completed quest



    }

    @Override
    protected Integer call() throws Exception {
        run();
        return battleCtr;
    }


    private Enemy getParticipatingEnemyAt (int position) {
        return enemiesInBattle.get(position);
    }

    private boolean runBattle() {
        while(hero.getHealth() > 0 && enemiesInBattle.size() > 0) {
            runTurn();
        }
        if (hero.getHealth() > 0 ) {
            int gained = Math.round(goldGained * (1+(hero.getObservation().getLevel()/100.0f)));
            System.out.println(hero.getName() + " gains "+gained+" Gold!");
            hero.gainGold(gained);
            return true;
        }
        //Hero died during battle. Set current time as time of death
        hero.setLastDeath(System.currentTimeMillis());
        return false;

        // return value is answer to the question: is the hero still alive?
    }

    private void runTurn() {
        AttackType heroWeaponAttackType = AttackType.PHYSICAL;
        List<Quest> quests = new ArrayList<>();
        quests.addAll(hero.getQuestList());
        List<Enemy> enemies = enemiesInBattle;
        int target = 0;

        for(int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i).getHealth() <= 0 ) {
                enemiesInBattle.remove(getParticipatingEnemyAt(i));
                //Circle to the next enemy if current one died
                continue;
            }
            //looking for the "left"most enemy alive, skipping all afterwards.
            target = i;
            break;
        }

        if(enemies.size() == 0) {
            System.out.println("No enemies left!");
            return;
        }

        // Always try to survive at least the first dealDamage, approximate total damage to decide if potions should be taken
        int criticalHealth = enemies.get(target).getAttackPower()*enemies.size();
        if(hero.getWeapon() != null) {
            heroWeaponAttackType = hero.getWeapon().getAttackType();
        }
        int criticalMana = (heroWeaponAttackType == AttackType.MAGICAL) ? 10 : 0;
        boolean hasManaPotions = false;
        boolean hasHealthPotions = false;

        // Checking if hero even can fix their hp/mp problem. if there are no potions, there is no luck
        for(AbstractItems a:hero.getInventory()) {
            if(hasHealthPotions && hasManaPotions) {
                break;
            }
            if(a instanceof AbstractPotions) {
                if(((AbstractPotions) a).getStrengthMP() > 0) {
                    hasManaPotions = true;
                    continue;
                }
                if(((AbstractPotions) a).getStrengthHP() > 0) {
                    hasHealthPotions = true;
                    continue;
                }
            }
        }

        // Hero will drink at least something appropriate when they feel like they need to prepare.
        if(hero.getHealth() < criticalHealth && hasHealthPotions) {
            System.out.println(hero.getName()+" taking some potions to prepare.");
            pause();
            hero.takePotion();
        } else if (hero.getMana() < criticalMana && hasManaPotions) {
            System.out.println(hero.getName()+" taking some potions to prepare.");
            pause();
            hero.takePotion();
        } else {
            System.out.println(hero.getName()+" attacks the " + getParticipatingEnemyAt(target).getName()+".");

            // Damage calculation is here.
            int damage = hero.dealDamage();


            int observation = hero.getObservation().getLevel();
            int criticalCheck =  new Generator().generateNumber()%(10+observation);

            // Critical Attacks based on observation stat. Deals double damage
            if(observation >= criticalCheck*5) {
                damage *= 2;
                System.out.println(hero.getName()+" strikes a critical blow.\n");
            }

            getParticipatingEnemyAt(target).takeDamage(damage, heroWeaponAttackType);
            pause(750);

            System.out.println(getParticipatingEnemyAt(target).getName()+" now has "+getParticipatingEnemyAt(target).getHealth()+" health left.");
        }

        if (enemies.get(target).getHealth() <= 0) {
            try {
                for(Quest q:quests) {
                    // countEnemyKilled checks for validity of enemy
                    q.countEnemyKilled(enemies.get(target));
                    if (q.getMobsToHunt()<=0) {
                        hero.completeQuest(q);
                    }
                }
            } catch (NullPointerException e) {
                /*
                TODO: We don't have any quests accepted
                Should we then ignore the obvious error? What is justice even.
                */
            }
            System.out.println(hero.getName() + " has felled the "+getParticipatingEnemyAt(target).getName()+"!");
            hero.gainExperience(getParticipatingEnemyAt(target).getExperieceReward());
            System.out.println(hero.getName() + " gains "+ getParticipatingEnemyAt(target).getExperieceReward()+ " Experience!");
            goldGained += getParticipatingEnemyAt(target).getGoldReward();
            enemiesInBattle.remove(getParticipatingEnemyAt(target));
            pause();
        }

        for (Enemy enemy:enemies) {
            int damage = enemy.getAttackPower();
            hero.takeDamage(damage, enemy.getAttackType());
            System.out.println(enemy.getName() + " strikes "+ hero.getName()+".");
            pause();
            if(hero.getHealth() <= 0) {
                hero.setHealth(0);
                System.out.println(hero.getName()+" succumbed to the pain.\nHe was brought back to the town to recover from his wounds.");
                break;
            }
            System.out.println(hero.getName()+ " now has "+hero.getHealth()+" health left.");
            pause();
        }
        updateMessage(hero.getHealth()+"/"+hero.getMaxHealth());
    }

    private void pause(int pause) {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pause() {
        pause(500);
    }
}

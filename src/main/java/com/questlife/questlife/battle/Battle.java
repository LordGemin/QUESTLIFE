package main.java.com.questlife.questlife.battle;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.AbstractPotions;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.util.AttackType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 11.04.2017.
 */
public class Battle {


    private Hero participatingHero;
    private List<Enemy> participatingEnemies = new ArrayList<>();
    private int goldGained;

    public Battle(Hero participatingHero, List<Enemy> participatingEnemies) {
        this.participatingHero = participatingHero;
        this.participatingEnemies = participatingEnemies;
    }


    Hero getParticipatingHero() {
        return participatingHero;
    }

    public void setParticipatingHero(Hero participatingHero) {
        this.participatingHero = participatingHero;
    }

    List<Enemy> getParticipatingEnemies() {
        return participatingEnemies;
    }

    public Enemy getParticipatingEnemyAt (int position) {
        return participatingEnemies.get(position);
    }

    public void setParticipatingEnemies(List<Enemy> participatingEnemies) {
        this.participatingEnemies = participatingEnemies;
    }

    public void addEnemy(Enemy enemy) {
        participatingEnemies.add(enemy);
    }

    public boolean runBattle() {
        while(getParticipatingHero().getHealth() > 0 && getParticipatingEnemies().size() > 0) {
            runTurn();
        }
        if (getParticipatingHero().getHealth() > 0 ) {
            int gained = Math.round(goldGained * (1+(getParticipatingHero().getObservation().getLevel()/100.0f)));
            System.out.println(getParticipatingHero().getName() + " gains "+gained+" Gold!");
            getParticipatingHero().gainGold(gained);
            return true;
        }
        //Hero died during battle. Set current time as time of death
        getParticipatingHero().setLastDeath(System.currentTimeMillis());
        return false;

        // return value is answer to the question: is the hero still alive?
    }

    public int runTurn() {
        Hero hero = getParticipatingHero();
        AttackType heroWeaponAttackType = AttackType.PHYSICAL;
        List<Quest> quests = new ArrayList<>();
        quests.addAll(hero.getQuestList());
        List<Enemy> enemies = getParticipatingEnemies();
        int target = 0;

        for(int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i).getHealth() <= 0 ) {
                getParticipatingEnemies().remove(getParticipatingEnemyAt(i));
                //Circle to the next enemy if current one died
                continue;
            }
            //looking for the "left"most enemy alive, skipping all afterwards.
            target = i;
            break;
        }

        if(enemies.size() == 0) {
            System.out.println("No enemies left!");
            return hero.getHealth();
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
            if(a instanceof AbstractPotions) {
                if(((AbstractPotions) a).getStrengthMP() > 0) {
                    hasManaPotions = true;
                    break;
                }
                if(((AbstractPotions) a).getStrengthHP() > 0) {
                    hasHealthPotions = true;
                    break;
                }
            }
        }

        // Hero will drink at least something appropriate when they feel like they need to prepare.
        if(hero.getHealth() < criticalHealth && hasHealthPotions) {
            System.out.println(hero.getName()+" taking some potions to prepare.");
            pause(200);
            hero.takePotion();
        } else if (hero.getMana() < criticalMana && hasManaPotions) {
            System.out.println(hero.getName()+" taking some potions to prepare.");
            pause(200);
            hero.takePotion();
        } else {
            System.out.println(hero.getName()+" attacks the " + getParticipatingEnemyAt(target).getName()+".");

            // Damage calculation is here.
            int damage = hero.dealDamage();
            getParticipatingEnemyAt(target).takeDamage(damage, heroWeaponAttackType);
            pause(200);

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
            getParticipatingHero().gainExperience(getParticipatingEnemyAt(target).getExperieceReward());
            System.out.println(hero.getName() + " gains "+ getParticipatingEnemyAt(target).getExperieceReward()+ " Experience!");
            goldGained += getParticipatingEnemyAt(target).getGoldReward();
            getParticipatingEnemies().remove(getParticipatingEnemyAt(target));
            pause(200);
        }

        for (Enemy enemy:enemies) {
            int damage = enemy.getAttackPower();
            hero.takeDamage(damage, enemy.getAttackType());
            System.out.println(enemy.getName() + " strikes "+ hero.getName()+".");
            pause(100);
            if(hero.getHealth() <= 0) {
                hero.setHealth(0);
                System.out.println(hero.getName()+" succumbed to the pain.\nHe was brought back to the town to recover from his wounds.");
                break;
            }
            System.out.println(hero.getName()+ " now has "+hero.getHealth()+" health left.");
            pause(100);
        }
        return hero.getHealth();
    }

    private void pause(int pause) {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

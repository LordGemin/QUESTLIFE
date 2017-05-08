package main.java.com.questlife.questlife.battle;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.AbstractPotions;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.util.AttackType;

import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 *
 * Created by Gemin on 11.04.2017.
 */
public class Battle extends AbstractBattle {


    public Battle(Hero participatingHero) {
        super(participatingHero);
    }

    public Battle(List<Enemy> participatingEnemies) {
        super(participatingEnemies);
    }

    public Battle(Hero participatingHero, List<Enemy> participatingEnemies) {
        super(participatingHero, participatingEnemies);
    }


    @Override
    public boolean runBattle() {
        while(getParticipatingHero().getHealth() > 0 && getParticipatingEnemies().size() > 0) {
            runTurn();
        }
        if (getParticipatingHero().getHealth() > 0 ) {
            int gained = Math.round(goldGained * (1+(getParticipatingHero().getObservation()/100.0f)));
            System.out.println(getParticipatingHero().getName() + " gains "+gained+" Gold!");
            getParticipatingHero().gainGold(gained);
            return true;
        }
        //Hero died during battle. Set current time as time of death
        getParticipatingHero().setLastDeath(System.currentTimeMillis());
        return false;

        // return value is answer to the question: is the hero still alive?
    }

    @Override
    public void runTurn() {
        Hero hero = getParticipatingHero();
        int target = 0;

        for(int i = 0; i < getParticipatingEnemies().size(); i++) {
            if(getParticipatingEnemyAt(i).getHealth() <= 0 ) {
                getParticipatingEnemies().remove(getParticipatingEnemyAt(i));
                //Circle to the next enemy if current one died
            }
            //looking for the "left"most enemy alive, skipping all afterwards.
            target = i;
            i = getParticipatingEnemies().size();
        }

        if(getParticipatingEnemies().size() == 0) {
            System.out.println("No enemies left!");
            return;
        }

        // Always try to survive at least the first dealDamage, approximate total damage to decide if potions should be taken
        int criticalHealth = getParticipatingEnemyAt(target).getAttackPower()*getParticipatingEnemies().size();
        int criticalMana = (hero.getWeapon().getAttackType() == AttackType.MAGICAL) ? 10 : 0;

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
            hero.takePotion();
        } else if (hero.getMana() < criticalMana && hasManaPotions) {
            System.out.println(hero.getName()+" taking some potions to prepare.");
            hero.takePotion();
        } else {
            System.out.println(hero.getName()+" attacks the " + getParticipatingEnemyAt(target).getName()+".");

            // Damage calculation is here.
            int damage = hero.dealDamage();
            getParticipatingEnemyAt(target).takeDamage(damage, hero.getWeapon().getAttackType());

            System.out.println(getParticipatingEnemyAt(target).getName()+" now has "+getParticipatingEnemyAt(target).getHealth()+" health left.");
        }

        if (getParticipatingEnemyAt(target).getHealth() <= 0) {
            try {
                for(Quest q:hero.getQuestList()) {
                    // countEnemyKilled checks for validity of enemy
                    q.countEnemyKilled(getParticipatingEnemyAt(target));
                    if (q.getMobsToHunt()<=0) {
                        hero.completeQuest(q);
                    }
                }
            } catch (ConcurrentModificationException | NullPointerException e) {
                /*
                TODO: We don't have any quests accepted / one of our quests was finished
                Should we then ignore the obvious error? What is justice even.
                */
            }
            System.out.println(hero.getName() + " has felled the "+getParticipatingEnemyAt(target).getName()+"!");
            getParticipatingHero().gainExperience(getParticipatingEnemyAt(target).getExperieceReward());
            System.out.println(hero.getName() + " gains "+ getParticipatingEnemyAt(target).getExperieceReward()+ " Experience!");
            goldGained += getParticipatingEnemyAt(target).getGoldReward();
            getParticipatingEnemies().remove(getParticipatingEnemyAt(target));
        }

        for (Enemy enemy:getParticipatingEnemies()) {
            int damage = enemy.getAttackPower();
            hero.takeDamage(damage, enemy.getAttackType());
            System.out.println(getParticipatingEnemyAt(target).getName() + " strikes "+ hero.getName()+".");
            if(hero.getHealth() <= 0) {
                System.out.println(hero.getName()+" succumbed to the pain.\nHe was brought back to the town to recover from his wounds.");
                break;
            }
            System.out.println(hero.getName()+ " now has "+hero.getHealth()+" health left.");
        }
    }
}

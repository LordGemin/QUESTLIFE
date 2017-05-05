package main.java.com.questlife.questlife.battle;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.util.AttackType;

import java.time.LocalDateTime;
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
        int enemyPosition = 0;

        for(int i = 0; i < getParticipatingEnemies().size(); i++) {
            if(getParticipatingEnemyAt(i).getHealth() <= 0 ) {
                getParticipatingEnemies().remove(getParticipatingEnemyAt(i));
                //Circle to the next enemy if current one died
            }
            //looking for the leftmost enemy alive, skipping all afterwards.
            enemyPosition = i;
            i = getParticipatingEnemies().size();
        }

        if(getParticipatingEnemies().size() == 0) {
            System.out.println("No enemies left!");
            return;
        }

        // Always try to survive at least the first dealDamage, approximate total damage to decide if potions should be taken
        int criticalHealth = getParticipatingEnemyAt(enemyPosition).getAttackPower()*getParticipatingEnemies().size();

        //TODO: Find the amount of mana that should be used on dealDamage.
        int criticalMana = (hero.getWeapon().getAttackType() == AttackType.MAGICAL) ? hero.getAttack()/100 : 0;

        if(hero.getHealth() < criticalHealth || hero.getMana() < criticalMana ) {
            System.out.println(hero.getName()+" taking some potions to prepare.");
            hero.takePotion();
        } else {
            System.out.println(hero.getName()+" attacks the " + getParticipatingEnemyAt(enemyPosition).getName()+".");
            hero.dealDamage(getParticipatingEnemyAt(enemyPosition));
            System.out.println(getParticipatingEnemyAt(enemyPosition).getName()+" now has "+getParticipatingEnemyAt(enemyPosition).getHealth()+" health left.");
        }

        if (getParticipatingEnemyAt(enemyPosition).getHealth() <= 0) {
            System.out.println(hero.getName() + " has felled the "+getParticipatingEnemyAt(enemyPosition).getName()+"!");
            getParticipatingHero().gainExperience(getParticipatingEnemyAt(enemyPosition).getExperieceReward());
            System.out.println(hero.getName() + " gains "+ getParticipatingEnemyAt(enemyPosition).getExperieceReward()+ " Experience!");
            goldGained += getParticipatingEnemyAt(enemyPosition).getGoldReward();
            getParticipatingEnemies().remove(getParticipatingEnemyAt(enemyPosition));
        }

        for (Enemy enemy:getParticipatingEnemies()) {
            enemy.dealDamage(hero);
            System.out.println(getParticipatingEnemyAt(enemyPosition).getName() + " strikes "+ hero.getName()+".");
            System.out.println(hero.getName()+ " now has "+hero.getHealth()+" health left.");
        }
    }
}

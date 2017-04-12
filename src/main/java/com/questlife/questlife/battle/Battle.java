package main.java.com.questlife.questlife.battle;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.util.AttackType;

/**
 * Created by Gemin on 11.04.2017.
 */
public class Battle extends AbstractBattle {


    public Battle(Hero participatingHero) {
        super(participatingHero);
    }

    public Battle(Enemy[] participatingEnemies) {
        super(participatingEnemies);
    }

    public Battle(Hero participatingHero, Enemy[] participatingEnemies) {
        super(participatingHero, participatingEnemies);
    }

    @Override
    void initialiseBattle() {
        turnCounter = 1;

    }

    @Override
    public void runTurn() {
        Hero hero = getParticipatingHero();
        int enemyPosition = -1;

        for(int i = 0; i < getParticipatingEnemies().length; i++) {
            if(getParticipatingEnemyAt(i).getHealth() <= 0 ) {
                //Circle to the next enemy
            } else if (enemyPosition == -1) {
                //looking for the leftmost enemy alive, skipping all afterwards.
                enemyPosition = i;
            }
        }

        // Always try to survive at least the first attack, approximate total damage
        int criticalHealth = getParticipatingEnemyAt(enemyPosition).getAttackPower()*getParticipatingEnemies().length;

        //TODO: Find the amount of mana that should be used on attack.
        int criticalMana = (hero.getWeapon().getAttackType() == AttackType.MAGICAL) ? hero.getAttack()/100 : 0;

        if(hero.getHealth() < criticalHealth || hero.getMana() < criticalMana ) {
            hero.takePotion();
        } else {
            dealDamage(hero, getParticipatingEnemyAt(enemyPosition));
        }
        for(int i = 0; i < getParticipatingEnemies().length; i++) {
            receiveDamage(getParticipatingEnemyAt(i), hero);
        }
    }

    @Override
    public void dealDamage(Hero attacker, Enemy defender) {
        int baseDamage = attacker.getAttack();
        int defense = defender.getDefense();
        int health = defender.getHealth();

        defender.setHealth(health-(baseDamage-defense));
    }

    @Override
    public void receiveDamage(Enemy attacker, Hero defender) {
        int baseDamage = attacker.getAttackPower();
        int defense = defender.getDefense();
        int health = defender.getHealth();

        defender.setHealth(health-(baseDamage-defense));
    }
}

package main.java.com.questlife.questlife.battle;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;

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

    }

    @Override
    public void runTurn() {
        dealDamage(getParticipatingHero(),getParticipatingEnemyAt(0));
        receiveDamage(getParticipatingEnemyAt(0), getParticipatingHero());
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

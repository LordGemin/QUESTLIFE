package main.java.com.questlife.questlife.battle;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 11.04.2017.
 */
public abstract class AbstractBattle {

    private Hero participatingHero;
    private List<Enemy> participatingEnemies = new ArrayList<>();
    int goldGained;
    /* Do we need these variables? We can read all of them from the Hero and Enemy objects... theoretically
    private int damageHero, healthHero, defenseHero, resistanceHero, manaHero;
    private String nameHero, nameEnemy;
    private int damageEnemy, healthEnemy, defenseEnemy, resistanceEnemy, manaEnemy;
    */

    AbstractBattle(Hero participatingHero) {
        this.participatingHero = participatingHero;
    }

    AbstractBattle(List<Enemy> participatingEnemies) {
        this.participatingEnemies = participatingEnemies;
    }

    AbstractBattle(Hero participatingHero, List<Enemy> participatingEnemies) {
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

    /**
     * Defines all parameters and fills the remaining variables.
     */
    abstract boolean runBattle();


    /**
     *
     * A turn is compromised from a Hero dealDamage and an Enemy dealDamage.
     * The hero will take potions if needed.
     */
    abstract void runTurn();

}

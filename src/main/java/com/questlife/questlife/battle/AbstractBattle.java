package main.java.com.questlife.questlife.battle;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;

/**
 * Created by Gemin on 11.04.2017.
 */
public abstract class AbstractBattle {

    private Hero participatingHero;
    private Enemy[] participatingEnemies;
    int turnCounter;
    /* Do we need these variables? We can read all of them from the Hero and Enemy objects... theoretically
    private int damageHero, healthHero, defenseHero, resistanceHero, manaHero;
    private String nameHero, nameEnemy;
    private int damageEnemy, healthEnemy, defenseEnemy, resistanceEnemy, manaEnemy;
    */

    public AbstractBattle(Hero participatingHero) {
        this.participatingHero = participatingHero;
    }

    public AbstractBattle(Enemy[] participatingEnemies) {
        this.participatingEnemies = participatingEnemies;
    }

    public AbstractBattle(Hero participatingHero, Enemy[] participatingEnemies) {
        this.participatingHero = participatingHero;
        this.participatingEnemies = participatingEnemies;
    }

    public Hero getParticipatingHero() {
        return participatingHero;
    }

    public void setParticipatingHero(Hero participatingHero) {
        this.participatingHero = participatingHero;
    }

    public Enemy[] getParticipatingEnemies() {
        return participatingEnemies;
    }

    public Enemy getParticipatingEnemyAt (int position) {
        return participatingEnemies[position];
    }

    public void setParticipatingEnemies(Enemy[] participatingEnemies) {
        this.participatingEnemies = participatingEnemies;
    }

    /**
     * Defines all parameters and fills the remaining variables.
     */
    abstract void initialiseBattle ();


    /**
     * A turn is compromised from a Hero attack and an Enemy attack.
     */
    abstract void runTurn();

    /**
     * This method is used to calculate the damage dealt to an enemy by an attack from the hero.
     * It uses the attack type of the hero, the damage they would deal and the defense stat from the enemy
     * @param attacker receives an Hero object to use in calculations
     * @param defender receives an Enemy object to use in calculations
     */
    abstract void dealDamage(Hero attacker, Enemy defender);

    /**
     * This method is used to calculate the damage dealt to the Hero by an attack from an Enemy.
     * It uses the attack type of the Enemy, the damage it would deal and the appropriate defense stat from the Hero
     * @param attacker receives an Hero object to use in calculations
     * @param defender receives an Enemy object to use in calculations
     */
    abstract void receiveDamage(Enemy attacker, Hero defender);

}

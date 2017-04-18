package main.java.com.questlife.questlife.enemy;

import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.StatCalculator;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Enemy implements Serializable {

    private String name;
    private int health;
    private int attackPower;
    private int defense;
    private int resistance;
    private AttackType attackType;

    Enemy() {

    }

    public Enemy(String name,int health, int attackPower, int defense, int resistance, AttackType attackType) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.resistance = resistance;
        this.attackType = attackType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void dealDamage(Hero attackedHero) {
        int damageDealt= 0;
        if (attackType == AttackType.PHYSICAL)
            damageDealt = this.getAttackPower()-attackedHero.getDefense();
        if (attackType == AttackType.MAGICAL)
            damageDealt = this.getAttackPower()-attackedHero.getResistance();
        attackedHero.takeDamage(damageDealt);
    }

    public void takeDamage(int damageDealt) {
        this.setHealth(this.getHealth()-damageDealt);
    }

    public int getExperieceReward() {
        StatCalculator statCalculator = new StatCalculator();
        return statCalculator.getExperienceFromEnemy(this);
    }

    public int getGoldReward() {
        StatCalculator statCalculator = new StatCalculator();
        return  statCalculator.getGoldFromEnemy(this);
    }
}

package main.java.com.questlife.questlife.enemy;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;
import main.java.com.questlife.questlife.util.StatCalculator;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Enemy implements Serializable {

    private StringProperty name = new SimpleStringProperty();
    private Integer health;
    private Integer maxHealth;
    private Integer attackPower;
    private Integer defense;
    private Integer resistance;
    private Integer level;
    private AttackType attackType;

    public Enemy() {
        this("",1);
    }

    public Enemy(String name, int levelOfHero) {
        this.name.set(name);
        this.level = levelOfHero;
        createEnemy(levelOfHero);
    }

    public Enemy(String name,int health, int attackPower, int defense, int resistance, AttackType attackType) {
        this.name.set(name);
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.resistance = resistance;
        this.attackType = attackType;
    }

    private void createEnemy(int level) {
        Generator generator = new Generator();
        this.level = level;

        this.health = 10+generator.generateNumber()%level*20;
        this.maxHealth = health;
        if(name.toString().contains("Shiny")) {
            this.attackPower = 4 + generator.generateNumber() % level * 15;
        } else {
            this.attackPower = 2 + generator.generateNumber() % level * 10;
        }
        this.defense = 1+generator.generateNumber() % level * 8;
        this.resistance = 1+generator.generateNumber() % level * 6;

        attackType = AttackType.PHYSICAL;
        if(name.toString().contains("Blue")) {
            attackType = AttackType.MAGICAL;
        }

        if(attackType == AttackType.PHYSICAL) {
            this.defense = 1+generator.generateNumber() % level * 10;
        } else {
            // If attackType isn't physical, it is magical
            // Magic enemies are rare, so they should be more challenging
            this.attackPower = 2+generator.generateNumber()%level*12;
            this.resistance = 1+generator.generateNumber() % level * 12;
        }
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Integer getLevel() {
        return level;
    }

    public void takeDamage(int damageDealt, AttackType attackType) {
        int damageTaken = 0;
        if (attackType == AttackType.PHYSICAL)
            damageTaken = damageDealt-getDefense();
        if (attackType == AttackType.MAGICAL)
            damageTaken = damageDealt-getResistance();

        if(damageTaken <= 0) {
            damageTaken = 1;
        }

        setHealth(health-damageTaken);
    }

    public int getExperieceReward() {
        return StatCalculator.getExperienceFromEnemy(this);
    }

    public int getGoldReward() {
        return  StatCalculator.getGoldFromEnemy(this);
    }

    public void setHerolevel(int herolevel) {
        createEnemy(herolevel);
    }
}

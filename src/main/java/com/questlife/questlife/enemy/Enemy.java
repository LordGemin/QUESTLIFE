package main.java.com.questlife.questlife.enemy;

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

    private StringProperty name;
    private Integer health;
    private Integer attackPower;
    private Integer defense;
    private Integer resistance;
    private AttackType attackType;

    public Enemy() {
        this("",1,AttackType.PHYSICAL);
    }

    public Enemy(String name, int levelOfHero, AttackType attackType) {
        this.name.set(name);
        createEnemy(levelOfHero, attackType);
    }

    public Enemy(String name,int health, int attackPower, int defense, int resistance, AttackType attackType) {
        this.name.set(name);
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.resistance = resistance;
        this.attackType = attackType;
    }

    private void createEnemy(int level, AttackType attackType) {
        Generator generator = new Generator();
        this.health = generator.generateNumber()%level*10;
        this.attackPower = generator.generateNumber()%level*5;
        this.defense = generator.generateNumber() % level * 4;
        this.resistance = generator.generateNumber() % level * 3;

        if(attackType == AttackType.PHYSICAL) {
            this.defense = generator.generateNumber() % level * 5;
        } else {
            // If attackType isn't physical, it is magical
            // Magic enemies are rare, so they should be more challenging
            this.attackPower = generator.generateNumber()%level*6;
            this.resistance = generator.generateNumber() % level * 6;
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

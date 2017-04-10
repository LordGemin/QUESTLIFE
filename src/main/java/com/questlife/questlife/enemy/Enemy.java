package main.java.com.questlife.questlife.enemy;

import main.java.com.questlife.questlife.util.AttackType;

/**
 * Created by Gemin on 10.04.2017.
 */
public abstract class Enemy {

    private String name;
    private int attackPower;
    private int defense;
    private int resistance;
    private AttackType attackType;

    public Enemy(String name, int attackPower, int defense, int resistance, AttackType attackType) {
        this.name = name;
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
}

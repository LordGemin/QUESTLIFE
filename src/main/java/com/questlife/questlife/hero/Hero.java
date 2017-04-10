package main.java.com.questlife.questlife.hero;

import main.java.com.questlife.questlife.items.Weapon;
/**
 * Created by Gemin on 10.04.2017.
 */
public abstract class Hero {

    private String name;
    private int health;
    private int mana;
    private int strength;
    private int dexterity;
    private int mind;
    private int charisma;
    private int constitution;
    private int piety;
    private int observation;
    private Weapon weapon;


    Hero (String name, Weapon weapon) {
        this.name = name;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getMind() {
        return mind;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getPiety() {
        return piety;
    }

    public int getObservation() {
        return observation;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setMind(int mind) {
        this.mind = mind;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setPiety(int piety) {
        this.piety = piety;
    }

    public void setObservation(int observation) {
        this.observation = observation;
    }

    public void setWeapon (Weapon weapon) {
        this.weapon = weapon;
    }
}

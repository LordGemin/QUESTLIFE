package main.java.com.questlife.questlife.hero;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.items.Potion;
import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.player.Inventory;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.util.StatCalculator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Hero implements Serializable {

    private String name;
    private Player player;
    private int maxHealth;
    private int health;
    private int maxMana;
    private int mana;
    private int strength;
    private int dexterity;
    private int mind;
    private int charisma;
    private int constitution;
    private int piety;
    private int observation;
    private int level;
    private int experience;
    private int experienceToNextLevel;
    private int gold;
    private Weapon weapon;

    private StatCalculator statCalculator = new StatCalculator();


    Hero() {}

    public Hero (Player player, String name, Weapon weapon) {
        this.player = player;
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

    private void setWeapon (Weapon weapon) {
        this.weapon = weapon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperienceToNextLevel() {
        return experienceToNextLevel;
    }

    public void setExperienceToNextLevel(int experienceToNextLevel) {
        this.experienceToNextLevel = experienceToNextLevel;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDefense(){
        int defense;

        defense = statCalculator.calculateHeroesDefense(this);

        return defense;
    }

    public int getResistance(){
        int resistance;

        resistance = statCalculator.calculateHeroesResistance(this);

        return resistance;
    }

    public int getAttack() {
        int attack;

        attack = statCalculator.calculateHeroesAttack(this);

        return attack;
    }

    public void changeWeapon(Weapon toEquip) {
        player.getInventory().addWeapon(this.weapon);
        this.setWeapon(toEquip);
    }

    private void levelUp() {
        this.level++;
        this.experienceToNextLevel = experienceToNextLevel + 1000+100*Math.round(level/10);
        //TODO: Rethink this formula
    }

    public void gainExperience(int experienceGained) {
        this.experience += experienceGained;
        while (this.experience >= this.experienceToNextLevel) {
            levelUp();
            //TODO: Message to Player. Congrats or something
        }
    }

    public void gainGold(int goldGained) {
        this.gold += goldGained;
    }

    public void takePotion() {
        int needHP = maxHealth-health;
        int needMP = maxMana-mana;
        int healthGotten = 0;
        int manaGotten = 0;

        Inventory inv = player.getInventory();
        List<Potion> potions = inv.getPotionsInInventory();

        for (Potion potion : potions) {
            String identifier = potion.getName();

            //Take all potions until full health
            if (healthGotten < needHP && identifier.endsWith("Potion")) {
                healthGotten += potion.getStrengthHP();
                System.out.println("Taking: " + identifier + " to heal.");
                System.out.println("I have healed: " + healthGotten + " HP so far.");
                inv.getItemsInInventory().remove(potion);
            } else if (identifier.endsWith("Potion")) {
                healthGotten = needHP;
                continue;
            }

            //Take all elixirs until full mana
            if (manaGotten < needMP && identifier.endsWith("Elixir")) {
                manaGotten += potion.getStrengthMP();
                System.out.println("Taking: " + identifier + " to regenerate mana.");
                System.out.println("I have regenerated: " + manaGotten + " MP so far.");
                inv.getItemsInInventory().remove(potion);
            } else if (identifier.endsWith("Elixir")) {
                manaGotten = needMP;
                continue;
            }
            System.out.println();
        }

        this.health += healthGotten;
        this.mana += manaGotten;
        System.out.println("After all these potions, there are "+inv.getPotionsInInventory().size()+" Potions left.");
        player.getInventory().setItemsInInventory(inv.getItemsInInventory());
    }

    public void takePotion (Potion potionToTake) {
        player.getInventory().getItemsInInventory().remove(potionToTake);
        
        int healthNeed = maxHealth - health;
        int manaNeed = maxMana - mana;
        
        if(healthNeed < potionToTake.getStrengthHP()) { //if the potion provides to much we have to power it down
            potionToTake.setStrengthHP(healthNeed);
        }
        if (manaNeed < potionToTake.getStrengthMP()) {
            potionToTake.setStrengthMP(manaNeed);
        }
        
        this.health += potionToTake.getStrengthHP();
        this.mana += potionToTake.getStrengthMP();
        
    }

    public void takeDamage(int damageDealt) {
        this.setHealth(this.getHealth()-damageDealt);
    }

    public void dealDamage(Enemy enemy) {
        int damageDealt = this.getAttack()-enemy.getDefense();
        enemy.takeDamage(damageDealt);
    }

    public boolean spendGold(int cost) {
        if(gold >= cost) {
            gold -= cost;
            return true;
        }
        return false;
    }
}

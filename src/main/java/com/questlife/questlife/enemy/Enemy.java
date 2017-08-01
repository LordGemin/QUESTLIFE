package main.java.com.questlife.questlife.enemy;

import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;
import main.java.com.questlife.questlife.util.StatCalculator;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Enemy implements Serializable {

    private String name;
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
        this.name = name;
        this.level = levelOfHero;
        createEnemy(levelOfHero);
    }

    public Enemy(String name,int health, int attackPower, int defense, int resistance, AttackType attackType) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.resistance = resistance;
        this.attackType = attackType;
    }

    private void createEnemy(int level) {

        Random generator = new Random(System.currentTimeMillis());

        this.level = level;

        // Fluctuate enemy health by 10 percent (10 times level times 0.9-1.1
        this.health = Math.round(level*10* (generator.nextFloat() * 0.2f + 0.9f));
        this.maxHealth = health;

        // Having some fun with the tropes. There can be wounded enemies that have less than their max health!
        if(generator.nextInt(100)<5) {
            name = "Wounded "+name;
            health = Math.round(health*0.8f);
        }

        if(name.contains("Shiny")) {
            this.attackPower = level*3 + generator.nextInt(level*5);
        } else {
            this.attackPower = level*2 + generator.nextInt(level*5);
        }
        this.defense = 4 + generator.nextInt(level*3);
        this.resistance = 2 + generator.nextInt(level*2);

        attackType = AttackType.PHYSICAL;
        if(name.contains("Blue")) {
            attackType = AttackType.MAGICAL;
            // Magical enemies have inverted calculations for defense and resistance
            this.resistance = 4 + generator.nextInt(level*3);
            this.defense = 2 + generator.nextInt(level*2);
        }

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

    public AttackType getAttackType() {
        return attackType;
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
            damageTaken = damageDealt-defense;
        if (attackType == AttackType.MAGICAL)
            damageTaken = damageDealt-resistance;
        if (attackType == AttackType.BOTH) {
            // Attack Type both is valuable for it's diversity. But enemies will resist a little bit better against it.
            damageTaken = Math.round(damageDealt - ((defense + resistance) / 1.5f));
        }

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

package main.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.util.StatCalculator;

/**
 *
 * Created by Busch on 22.04.2017.
 */
public abstract class AbstractPotions extends AbstractItems {

    StatCalculator statCalculator = new StatCalculator();
    Integer strengthHP;
    Integer strengthMP;

    public AbstractPotions() {

    }

    public AbstractPotions(String name, int price, String description) {
        super(name,price,description);
    }

    public AbstractPotions(int strengthHP, int strengthMP) {
        this.strengthHP = strengthHP;
        this.strengthMP = strengthMP;
    }

    public AbstractPotions(String name, int price, String description, int strengthHP, int strengthMP) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.strengthHP = strengthHP;
        this.strengthMP = strengthMP;
    }

    public int getStrengthHP() {
        return strengthHP;
    }

    public int getStrengthMP() {
        return strengthMP;
    }

}

package main.java.com.questlife.questlife.items;

/**
 * Created by Busch on 22.04.2017.
 */
public abstract class AbstractPotions extends AbstractItems {

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


    public abstract void setHeroLevel(int levelOfHero);
}

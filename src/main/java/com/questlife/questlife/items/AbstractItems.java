package main.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.util.StatCalculator;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public abstract class AbstractItems implements Serializable {

    String identifier;

    String name;
    Integer price;
    String description;

    AbstractItems() {}

    AbstractItems(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void setHeroLevel(int levelOfHero);

    public void updatePrice(Hero hero) {
        this.price -= StatCalculator.getRebate(hero, price);
    }
}

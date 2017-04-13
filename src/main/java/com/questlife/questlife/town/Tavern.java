package main.java.com.questlife.questlife.town;

import main.java.com.questlife.questlife.hero.Hero;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Tavern extends AbstractBuilding {

    private Integer cost;

    public Tavern() {

    }

    public Tavern(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void lodgeHero(Hero hero) {
        if (hero.spendGold(cost)) {
            hero.setHealth(hero.getMaxHealth());
            hero.setMana(hero.getMaxMana());
        } else {
            //TODO: Notify player that they did not have enough gold to cover the fees
        }
    }

}

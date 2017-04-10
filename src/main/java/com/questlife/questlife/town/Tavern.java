package main.java.com.questlife.questlife.town;

/**
 * Created by Gemin on 10.04.2017.
 */
public abstract class Tavern {

    private String name;
    private int cost;

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
}

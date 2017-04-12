package main.java.com.questlife.questlife.items;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public abstract class AbstractItems implements Serializable {

    String name;
    int price;
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

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}

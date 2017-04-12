package main.java.com.questlife.questlife.player;

import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.Potion;
import main.java.com.questlife.questlife.items.Weapon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Gemin on 11.04.2017.
 */
public class Inventory {
    private List<AbstractItems> itemsInInventory = new ArrayList<>();

    public Inventory() {

    }

    public List<AbstractItems> getItemsInInventory() {
        return itemsInInventory;
    }

    public List<Potion> getPotionsInInventory() {
        List<Potion> potions = new ArrayList<>();
        for(Iterator<AbstractItems> i = itemsInInventory.iterator(); i.hasNext(); ) {
            AbstractItems potion = i.next();
            if (potion instanceof Potion) potions.add(((Potion) potion));
        }
        return potions;
    }

    public AbstractItems[] getItemsAsArray() {
        int inventorySize = itemsInInventory.size();
        return itemsInInventory.toArray(new AbstractItems[inventorySize]);
    }

    public void setItemsInInventory (List<AbstractItems> itemsInInventory) {
        this.itemsInInventory = itemsInInventory;
    }

    public void addPotion(Potion toAdd) {
        this.itemsInInventory.add(toAdd);
    }

    public void addWeapon (Weapon toAdd) {
        this.itemsInInventory.add(toAdd);
    }

    public AbstractItems getItemAt(int position) {
        return itemsInInventory.get(position);
    }
}

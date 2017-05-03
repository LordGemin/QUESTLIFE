package main.java.com.questlife.questlife.player;

import main.java.com.questlife.questlife.items.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Created by Gemin on 11.04.2017.
 */
public class Inventory {
    private List<AbstractItems> itemsInInventory = new ArrayList<>();

    public Inventory() {

    }

    public List<AbstractItems> getItemsInInventory() {
        return itemsInInventory;
    }

    public List<AbstractPotions> getPotionsInInventory() {
        List<AbstractPotions> potions = new ArrayList<>();
        for(Iterator<AbstractItems> i = itemsInInventory.iterator(); i.hasNext(); ) {
            AbstractItems potion = i.next();
            if (potion instanceof AbstractPotions) potions.add(((AbstractPotions) potion));
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

    public void addPotion(AbstractPotions toAdd) {
        this.itemsInInventory.add(toAdd);
    }

    public void addPotion(Potion toAdd) {
        this.itemsInInventory.add(toAdd);
    }

    public void addWeapon (AbstractWeapons toAdd) {
        this.itemsInInventory.add(toAdd);
    }

    public AbstractItems getItemAt(int position) {
        return itemsInInventory.get(position);
    }
}

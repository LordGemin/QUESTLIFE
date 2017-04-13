package main.java.com.questlife.questlife.town;

import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractItems;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 12.04.2017.
 */
public class Shop extends AbstractBuilding {

    private List<AbstractItems> itemsList = new ArrayList<>();

    public Shop() {

    }

    public Shop(String name) {
        this.name = name;
    }

    public Shop(String name, List<AbstractItems> itemsList) {
        this.name = name;
        this.itemsList = itemsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AbstractItems> getItems(){
        return itemsList;
    }

    public void setItemsList(List<AbstractItems> itemsList) {
        this.itemsList = itemsList;
    }

    public void addItem(AbstractItems item) {
        itemsList.add(item);
    }

    public int getPriceOfItemAt(int position) {
        return itemsList.get(position).getPrice();
    }

    public int getPriceOfItemByName(String name) {
        for (AbstractItems item : itemsList) {
            if (item.getName().equals(name)) {
                return item.getPrice();
            }
        }
        return 0;
    }

    public int getPriceOfItem(AbstractItems item) {
        int positionInList = (itemsList.contains(item)) ? itemsList.indexOf(item) : -1;
        if(positionInList!= -1) {
            return itemsList.get(positionInList).getPrice();
        }
        return 0;
    }

    public AbstractItems sellItem(int position, Hero hero) {
        if(hero.spendGold(getPriceOfItemAt(position))) {
            return itemsList.remove(position);
        }
        return null;
    }

    public AbstractItems sellItem(String name, Hero hero) {
        for (AbstractItems item : itemsList) {
            if (item.getName().equals(name)) {
                return sellItem(itemsList.indexOf(item), hero);
            }
        }
        return null;
    }

}

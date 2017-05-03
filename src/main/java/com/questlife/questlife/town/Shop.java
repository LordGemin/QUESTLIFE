package main.java.com.questlife.questlife.town;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractItems;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 12.04.2017.
 */
public class Shop extends AbstractBuilding {

    private List<AbstractItems> itemList = new ArrayList<>();

    public Shop() {

    }

    public Shop(String name) {
        this.name = name;
    }

    public Shop(String name, List<AbstractItems> itemsList) {
        this.name = name;
        this.itemList.addAll(itemsList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AbstractItems> getItemList() {
        return  itemList;
    }

    public void setItemList(List<AbstractItems> itemsList) {
        this.itemList = itemsList;
    }

    public void addItem(AbstractItems item) {
        itemList.add(item);
    }

    public int getPriceOfItemAt(int position) {
        return itemList.get(position).getPrice();
    }

    public int getPriceOfItemByName(String name) {
        for (AbstractItems item : itemList) {
            if (item.getName().equals(name)) {
                return item.getPrice();
            }
        }
        return 0;
    }

    public int getPriceOfItem(AbstractItems item) {
        int positionInList = (itemList.contains(item)) ? itemList.indexOf(item) : -1;
        if(positionInList!= -1) {
            return itemList.get(positionInList).getPrice();
        }
        return 0;
    }

    public AbstractItems sellItem(int position, Hero hero) {
        if(hero.spendGold(getPriceOfItemAt(position))) {
            return itemList.remove(position);
        }
        return null;
    }

    public boolean sellItem(String name, Hero hero) {
        for (AbstractItems item : itemList) {
            if (item.getName().equals(name)) {
                sellItem(itemList.indexOf(item), hero);
                return true;
            }
        }
        return false;
    }

    public boolean sellItem(AbstractItems item, Hero hero) {
        for (AbstractItems a : itemList) {
            if (a.equals(item)) {
                sellItem(itemList.indexOf(a), hero);
                return true;
            }
        }
        return false;
    }

}

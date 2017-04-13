package main.java.com.questlife.questlife.town;

import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.Potion;
import main.java.com.questlife.questlife.util.Generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Town implements Serializable {

    private List<AbstractBuilding> buildingList = new ArrayList<>();
    private int defaultCost = 50;

    public Town() {
        createTown();
    }

    public Town(List<AbstractBuilding> buildingList) {
        this.buildingList = buildingList;
    }

    public void addBuilding(AbstractBuilding building) {
        buildingList.add(building);
    }

    public List<AbstractBuilding> getBuildingList() {
        return buildingList;
    }

    private void createTown() {
        Generator generator = new Generator();
        Tavern tavern = new Tavern(generator.generateShopNames(), defaultCost);
        addBuilding(tavern);
        Shop shop = new Shop(generator.generateShopNames());
        addBuilding(shop);
    }

    public String getNameOfStoreAt(int position) {
        return buildingList.get(position).getName();
    }
}

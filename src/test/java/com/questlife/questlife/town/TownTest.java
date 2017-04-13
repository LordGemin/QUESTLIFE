package test.java.com.questlife.questlife.town;

import main.java.com.questlife.questlife.town.Town;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * Created by Gemin on 13.04.2017.
 */
public class TownTest {

    @Test
    public void creationTest() {
        //If we create a town without parameters, at least some buildings have to be created.
        Town town = new Town();
        assertTrue(town.getBuildingList().size() > 0);
        System.out.println(town.getNameOfStoreAt(0));
        System.out.println(town.getNameOfStoreAt(1));
    }

}
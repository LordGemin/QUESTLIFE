package test.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.items.Potion;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Gemin on 10.04.2017.
 */
public class PotionTest {
    @Test
    public void testGetTheSameObject() {
        Potion potion = new Potion("Itemname", 300,"This is the rumored potion of old");

        System.out.println(potion.getDescription());
        assertEquals  (potion.getDescription(),
                "This is the rumored potion of old");

    }
}
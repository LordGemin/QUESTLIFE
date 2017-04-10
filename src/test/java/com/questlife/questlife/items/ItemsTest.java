package test.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.items.*;
/**
 * Created by Gemin on 10.04.2017.
 */
public class ItemsTest {
    private Item item;

    public ItemsTest() {
        testConstructor();
    }

    private void testConstructor () {

        try {
            item = new Item("Gunslinger", 200,
                    "This is the most unimportant thing");
            System.out.println("The generated item is called: \" " + item.getName() +
            " \" and would cost around " + item.getPrice() + " Gold if you weren't cheating.");
        } catch (Exception e) {
            System.out.println("WHOA something happend in ItemTest");
        }

        System.out.println("Cool, ItemTest is done");
    }



}

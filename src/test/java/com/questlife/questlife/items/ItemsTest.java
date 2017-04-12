package test.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.items.*;


/**
 * Created by Gemin on 10.04.2017.
 */
public class ItemsTest {
    private Potion potion;

    public ItemsTest() {
        testConstructor();
    }


    private void testConstructor () {

        try {
            potion = new Potion("Gunslinger", 200,
                    "This is the most unimportant thing");
            System.out.println("The generated potion is called: \" " + potion.getName() +
            " \" and would cost around " + potion.getPrice() + " Gold if you weren't cheating.");

        } catch (Exception e) {
            System.out.println("WHOA something happend in PotionTest");
        }

        System.out.println("Cool, PotionTest is done");
    }



}

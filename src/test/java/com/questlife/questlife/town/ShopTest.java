package test.java.com.questlife.questlife.town;

import main.java.com.questlife.questlife.game.Game;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.Potion;
import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.town.Shop;
import main.java.com.questlife.questlife.util.Generator;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * Created by Gemin on 13.04.2017.
 */
public class ShopTest {

    private Game game = new Game(10);
    private Player player=game.getPlayer();

    @Test
    public void sellItem() throws Exception {
        Shop shop = new Shop();
        int itemsToGenerate = 100;
        player.createHero();
        Hero hero = player.getPlayerHero();
        Generator generator = new Generator();

        hero.gainGold(2000);

        for(int i = 0; i < itemsToGenerate/2; i++) {
            Potion potion = new Potion();
            shop.addItem(potion);
            Weapon weapon = new Weapon(10);
            shop.addItem(weapon);
        }

        int itemToBuy = generator.generateNumber()%itemsToGenerate;
        int price = shop.getPriceOfItemAt(itemToBuy);
        AbstractItems item = shop.sellItem(itemToBuy, hero);

        assertEquals(2000-price, hero.getGold());

        assertFalse(shop.getItems().contains(item));

    }

}
package test.java.com.questlife.questlife.game;

import main.java.com.questlife.questlife.game.Game;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.player.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * Created by Busch on 22.04.2017.
 */
public class GameTest {

    private Game game = new Game(10);

    @Test
    public void testItems() throws Exception {
        assertTrue(game.testItems());
        for(AbstractItems p: game.getPotions()) {
            System.out.println("Potion: "+ p.getName());
        }
        for(AbstractItems w: game.getWeapons()) {
            System.out.println("Potion: "+ w.getName());
        }
    }

}
package test.java.com.questlife.questlife.town;

import main.java.com.questlife.questlife.game.Game;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.town.Tavern;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gemin on 12.04.2017.
 */
public class TavernTest {

    private String name = "The toasty Inn";
    private int cost = 100;
    private Tavern tavern = new Tavern(name, cost);
    private Game game = new Game(1);
    private Player player=game.getPlayer();

    @Test
    public void testCreation() throws Exception {
        assertEquals(name, tavern.getName());
        assertEquals(cost, tavern.getCost());
    }

    @Test
    public void setCost() throws Exception {
        int newCost = tavern.getCost()*2;
        tavern.setCost(newCost);
        assertEquals(newCost, tavern.getCost());
    }

    @Test
    public void lodgeHero() throws Exception {
        player.createHero();
        Hero hero = player.getPlayerHero();

        hero.gainGold(2000);
        hero.setConstitution(Math.round(Integer.MAX_VALUE/5));
        hero.setHealth(20);
        hero.setPiety(16);
        hero.setMana(0);

        tavern.lodgeHero(hero);
        assertEquals(hero.getMaxHealth(),hero.getHealth());

        hero.setConstitution(20);
        hero.setHealth(20);
        hero.setPiety(Math.round(Integer.MAX_VALUE/5));
        hero.setMana(0);

        tavern.setCost(1500);
        tavern.lodgeHero(hero);

        assertEquals(hero.getMaxMana(),hero.getMana());
        assertEquals(400, hero.getGold());

        hero.setConstitution(20);
        hero.setHealth(20);
        hero.setPiety(20);
        hero.setMana(0);

        tavern.lodgeHero(hero);
        assertEquals(400,hero.getGold());
        assertEquals(20,hero.getHealth());
    }
}
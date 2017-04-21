package test.java.com.questlife.questlife.player;

import main.java.com.questlife.questlife.game.Game;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.quests.Quest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gemin on 13.04.2017.
 */
public class PlayerTest {

    @Test
    public void sendHeroOnQuest() throws Exception {
        Game game = new Game(10);
        Player player = game.getPlayer();

        Quest quest = new Quest("Test Name", "Test Description", game.getEnemies().get(5), 20, 2000,2000);
        player.createHero();
        player.getPlayerHero().addQuest(quest);
        player.sendHeroOnQuest(quest);

        assertEquals(quest.getName(),player.getPlayerHero().getActiveQuest().getName());
        assertEquals(quest.getMobsToHunt(),player.getPlayerHero().getActiveQuest().getMobsToHunt());
    }

}
package test.java.com.questlife.questlife.battle;

import main.java.com.questlife.questlife.battle.Battle;
import main.java.com.questlife.questlife.game.Game;
import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.util.AttackType;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 *
 * Created by Gemin on 11.04.2017.
 */
public class BattleTest {


    private Game game = new Game(1);
    private Player player=game.getPlayer();


    @Test
    public void runBattle() throws Exception {
        player.createHero();
        player.getPlayerHero().changeWeapon(new Weapon("Wooden Stick",10,10,AttackType.PHYSICAL));
        Battle battle = new Battle(player.getPlayerHero());
        battle.addEnemy(game.getEnemies().get(0));
        player.getPlayerHero().setConstitution(10);
        player.getPlayerHero().setHealth(100);
        player.getPlayerHero().setPiety(10);
        player.getPlayerHero().setMana(100);
        battle.getParticipatingHero().setStrength(5);
        battle.runBattle();

        assertTrue(battle.getParticipatingEnemies().size()==0);

    }

    @Test
    public void runTurn() throws Exception {
        player.createHero();
        player.getPlayerHero().changeWeapon(new Weapon("Wooden Stick",10,10,AttackType.PHYSICAL));
        Battle battle = new Battle(player.getPlayerHero());
        battle.addEnemy(game.getEnemies().get(0));
        player.getPlayerHero().setConstitution(10);
        player.getPlayerHero().setHealth(100);
        player.getPlayerHero().setPiety(10);
        player.getPlayerHero().setMana(100);
        battle.getParticipatingHero().setStrength(5);
        while(battle.getParticipatingEnemies().size() > 0 && battle.getParticipatingHero().getHealth() > 0) {
            battle.runTurn();
        }
        assertTrue(battle.getParticipatingEnemies().size()==0);
    }


}
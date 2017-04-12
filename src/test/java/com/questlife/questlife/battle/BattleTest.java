package test.java.com.questlife.questlife.battle;

import main.java.com.questlife.questlife.battle.Battle;
import main.java.com.questlife.questlife.game.Game;
import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.util.AttackType;
import org.junit.Test;


/**
 *
 * Created by Gemin on 11.04.2017.
 */
public class BattleTest {

    private Game game = new Game(10);
    private Player player=game.getPlayer();


    @Test
    public void fullBattle() throws Exception {
        //TODO: Implement
    }

    @Test
    public void runTurn() throws Exception {
        player.createHero();
        player.getPlayerHero().changeWeapon(new Weapon("Wooden Stick",10,10,AttackType.PHYSICAL));
        Battle battle = new Battle(player.getPlayerHero(), game.getEnemies());
        log("Start comprehensive battle test");
        player.getPlayerHero().setHealth(100);
        battle.getParticipatingHero().setStrength(-5);
        while(battle.getParticipatingEnemyAt(0).getHealth() >= 0 && battle.getParticipatingHero().getHealth() >= 0) {
            battle.runTurn();
            log (battle.getParticipatingEnemyAt(0).getHealth());
            log (battle.getParticipatingHero().getHealth());
        }
    }

    private void log (String out) {
        System.out.println(out);
    }

    private void log (int out) {
        System.out.println(out);
    }

    private void log (String out, int outInt) {
        log(out+outInt);
    }
}
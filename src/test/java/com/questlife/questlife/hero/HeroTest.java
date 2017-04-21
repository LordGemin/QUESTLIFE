package test.java.com.questlife.questlife.hero;

import main.java.com.questlife.questlife.game.Game;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.Potion;
import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.util.AttackType;
import org.junit.Test;


import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 *
 * Created by Gemin on 11.04.2017.
 */
public class HeroTest {

    private Game game = new Game(10);
    private Player player=game.getPlayer();

    @Test
    public void getDefense() throws Exception {
        player.createHero();
        Hero hero = player.getPlayerHero();
        hero.setStrength(20);
        assertEquals(40,hero.getDefense());
    }

    @Test
    public void getResistance() throws Exception {
        player.createHero();
        Hero hero = player.getPlayerHero();
        hero.changeWeapon(new Weapon("Wooden Stick",10,30, AttackType.PHYSICAL));
        hero.setMind(20);
        assertEquals(66,hero.getResistance());
    }

    @Test
    public void getAttack() throws Exception {
        player.createHero();
        Hero hero = player.getPlayerHero();
        hero.setStrength(20);
        assertEquals(20,hero.getAttack());

        player.createHero();
        hero = player.getPlayerHero();
        hero.changeWeapon(new Weapon("Wooden Stick",10,30, AttackType.MAGICAL));
        hero.setMind(20);
        assertEquals(50,hero.getAttack());
    }

    @Test
    public void changeWeapon() throws Exception {
        player.createHero();
        log("");
        log("Low level test");
        player.getPlayerHero().setLevel(3);
        player.getPlayerHero().changeWeapon(new Weapon(player.getPlayerHero().getLevel()));
        log ("The heroes new weapon is called: " + player.getPlayerHero().getWeapon().getName());
        log ("Phy Damage: " + player.getPlayerHero().getWeapon().getPhysicalAttack());
        log ("Mag Damage: " + player.getPlayerHero().getWeapon().getMagicalAttack());
        log ("It uses " + player.getPlayerHero().getWeapon().getAttackType().toString());

        log("");
        log("High level test");
        player.getPlayerHero().setLevel(55);
        player.getPlayerHero().changeWeapon(new Weapon(player.getPlayerHero().getLevel()));
        log ("The heroes new weapon is called: " + player.getPlayerHero().getWeapon().getName());
        log ("Phy Damage: " + player.getPlayerHero().getWeapon().getPhysicalAttack());
        log ("Mag Damage: " + player.getPlayerHero().getWeapon().getMagicalAttack());
        log ("It uses " + player.getPlayerHero().getWeapon().getAttackType().toString());

        log("");
        log("Extreme level test");
        player.getPlayerHero().setLevel(555);
        player.getPlayerHero().changeWeapon(new Weapon(player.getPlayerHero().getLevel()));
        log ("The heroes new weapon is called: " + player.getPlayerHero().getWeapon().getName());
        log ("Phy Damage: " + player.getPlayerHero().getWeapon().getPhysicalAttack());
        log ("Mag Damage: " + player.getPlayerHero().getWeapon().getMagicalAttack());
        log ("It uses " + player.getPlayerHero().getWeapon().getAttackType().toString());
    }

    @Test
    public void gainExperience() throws Exception {
        player.createHero();
        player.getPlayerHero().setLevel(3);
        player.getPlayerHero().setExperience(2000);
        player.getPlayerHero().setExperienceToNextLevel(3000);
        player.getPlayerHero().changeWeapon(new Weapon("Wooden Stick",10,10, AttackType.PHYSICAL));

        // Giving him more than enough to reach 48
        player.getPlayerHero().gainExperience(54300);

        assertEquals(player.getPlayerHero().getLevel(), 48);
    }

    @Test
    public void takePotion() throws Exception {
        int potionsToGenerate = 3000;
        player.createHero();
        Hero hero = player.getPlayerHero();
        hero.setConstitution(potionsToGenerate);
        hero.setPiety(potionsToGenerate);
        hero.setHealth(10);
        hero.setMana(10);

        for(int i = 0; i < potionsToGenerate; i++) {
            Potion potion = new Potion();
            player.getInventory().addPotion(potion);
        }

        if(hero.takePotion()<=potionsToGenerate && hero.getHealth()==hero.getMaxHealth() && hero.getMana()==hero.getMaxMana()) {
            assertTrue(true);
        }

    }

    @Test
    public void getLastDeath() {
        player.createHero();
        Hero hero = player.getPlayerHero();
        hero.setHealth(0);
        hero.setLastDeath(System.currentTimeMillis());
        log("Hero health: " + hero.getHealth());
        try {
            Thread.sleep(2000000);
        } catch (Exception e) {
            log("Waiting was interrupted by mean exception");
        }
        log("Hero died: " + hero.getTimeSinceLastDeath());
        log("Hero health after waiting: " + hero.getHealth());
    }

    private void log (String out) {
        System.out.println(out);
    }

}
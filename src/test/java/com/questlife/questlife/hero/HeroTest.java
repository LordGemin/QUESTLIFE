package test.java.com.questlife.questlife.hero;

import main.java.com.questlife.questlife.game.Game;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.Potion;
import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Gemin on 11.04.2017.
 */
public class HeroTest {

    Game game = new Game(10);
    private Player player=game.getPlayer();

    @Test
    public void getDefense() throws Exception {
    }

    @Test
    public void getResistance() throws Exception {
    }

    @Test
    public void getAttack() throws Exception {
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

        log("Hero Level: ", player.getPlayerHero().getLevel());
        log("Hero Experience: ", player.getPlayerHero().getExperience());
        log("Hero Experience to next level: ", player.getPlayerHero().getExperienceToNextLevel());
        player.getPlayerHero().gainExperience(54300);
        log("Hero Experience to next level: ", player.getPlayerHero().getExperienceToNextLevel());
        log("Hero Level: ", player.getPlayerHero().getLevel());

        assertEquals(player.getPlayerHero().getLevel(), 48);
    }

    @Test
    public void takePotion() throws Exception {
        player.createHero();
        Hero hero = player.getPlayerHero();
        hero.setMaxHealth(20000);
        hero.setMaxMana(10000);
        hero.setHealth(10);
        hero.setMana(10);

        Generator generator = new Generator();

        for(int i = 0; i < 3000; i++) {
            Potion potion = new Potion();
            player.getInventory().addPotion(potion);
            //log("Generated: "+potion.getName());
        }

        log("");
        log("Hero HP: ", hero.getHealth());
        log("Hero MP: ", hero.getMana());
        log("Taking potions....");
        hero.takePotion();
        log("Hero HP: ", hero.getHealth());
        log("Hero MP: ", hero.getMana());

        List<Potion> potions = player.getInventory().getPotionsInInventory();

    }

    void log (String out) {
        System.out.println(out);
    }
    void log (int out) {
        System.out.println(out);
    }

    void log (String out, int outInt) {
        log(out+outInt);
    }

}
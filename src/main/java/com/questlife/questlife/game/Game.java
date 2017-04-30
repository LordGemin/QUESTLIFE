package main.java.com.questlife.questlife.game;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.Potion;
import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.player.Inventory;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;

import java.io.Serializable;
import java.util.*;

import org.reflections.*;
/**
 *
 * Created by Gemin on 11.04.2017.
 */
public class Game implements Serializable{
    private Inventory inventory;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private List<AbstractItems> potions = new ArrayList<>();
    private List<AbstractItems> weapons = new ArrayList<>();

    public Game(int diversity) {
        initializeGame(diversity);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    private void initializeGame(int diversity) {
        createEnemies(diversity);
        createInventory();
        createPlayer();
    }

    private void createInventory() {
        inventory = new Inventory();
    }

    private void createPlayer() {
        player = new Player("", inventory);
    }

    public boolean testItems() {
        boolean flag = false;
        Reflections reflections = new Reflections("main.java");
        Set<Class<? extends AbstractItems>> classes = reflections.getSubTypesOf(AbstractItems.class);
        Iterator i = classes.iterator();
        while(i.hasNext()) {
            Object a = i.next();
            AbstractItems potion;
            try {
                Class b = Class.forName(a.toString().replace("class ", ""));
                potion = (AbstractItems) b.newInstance();
            } catch (Exception e) {
                potion = null;
            }
            if (potion instanceof Potion) {
                potions.add((Potion) potion);
                flag = true;
                continue;
            }
            if(potion instanceof Weapon) {
                weapons.add((Weapon) potion);
                flag = true;
                continue;
            }
            System.out.println("Object was: "+a.toString());
        }
        return flag;

    }

    public List<AbstractItems> getPotions() {
        return potions;
    }

    private void createEnemies(int diversity) {
        Generator generator = new Generator();
        Enemy[] enemy = new Enemy[diversity];
        String enemyName;
        AttackType attackType = AttackType.PHYSICAL;

        for(int i=0; i < diversity; i++) {
            enemyName = generator.generateEnemyName();
            if(enemyName.contains("Blue")) {
                attackType = AttackType.MAGICAL;
            }
            enemy[i] = new Enemy(enemyName,20,2,2,2,attackType);
        }

        Collections.addAll(enemies, enemy);

    }

    public List<AbstractItems> getWeapons() {
        return weapons;
    }
}

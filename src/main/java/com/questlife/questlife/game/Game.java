package main.java.com.questlife.questlife.game;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.player.Inventory;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by Gemin on 11.04.2017.
 */
public class Game implements Serializable{
    private Inventory inventory;
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();

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
}

package main.java.com.questlife.questlife.game;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.player.Inventory;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;

/**
 * Created by Gemin on 11.04.2017.
 */
public class Game {
    private Inventory inventory;
    private Player player;
    private Enemy[] enemies;

    public Game(int diversity) {
        initializeGame(diversity);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public void initializeGame(int diversity) {
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
        enemies = enemy;
    }
}

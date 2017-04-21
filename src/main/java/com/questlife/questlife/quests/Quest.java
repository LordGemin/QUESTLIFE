package main.java.com.questlife.questlife.quests;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Quest implements Serializable{

    private String name;
    private String description;
    private Enemy enemyType;
    private Integer mobsToHunt;
    private Integer rewardExp;
    private Integer rewardGold;
    private Boolean isActive = false;

    public Quest() {

    }

    public Quest(String name, String description, Enemy enemyType, int mobsToHunt, int rewardExp, int rewardGold) {
        this.name = name;
        this.description = description;
        this.enemyType = enemyType;
        this.mobsToHunt = mobsToHunt;
        this.rewardExp = rewardExp;
        this.rewardGold = rewardGold;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMobsToHunt() {
        return mobsToHunt;
    }

    public int getRewardExp() {
        return rewardExp;
    }

    public int getRewardGold() {
        return rewardGold;
    }

    public int countEnemyKilled(Enemy enemy) {
        if(!isActive) {
            return mobsToHunt;
        }
        if(enemy.getName().equals(enemyType.getName())) {
            mobsToHunt -= 1;
        }
        return mobsToHunt;
    }

    public void setAsActive() {
        isActive = true;
    }

    public void setInactive() {
        isActive = false;
    }

    public boolean getIsActive() { return isActive;}
}

package main.java.com.questlife.questlife.quests;

import main.java.com.questlife.questlife.enemy.Enemy;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Quest implements Serializable{

    private String name;
    private String description;
    private String questEnemy;
    private Integer mobsToHunt = 0;
    private Integer rewardExp = 0;
    private Integer rewardGold = 0;
    private Boolean isActive = false;

    public Quest() {

    }

    public Quest(String name, String description, String questEnemy, int mobsToHunt, int rewardExp, int rewardGold) {
        this.name = name;
        this.description = description;
        this.questEnemy = questEnemy;
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

    public Integer getMobsToHunt() {
        return mobsToHunt;
    }

    public Integer getRewardExp() {
        return rewardExp;
    }

    public Integer getRewardGold() {
        return rewardGold;
    }

    public String getQuestEnemy() {
        return questEnemy;
    }

    public int countEnemyKilled(Enemy enemy) {
        if(enemy.getName().equals(questEnemy)) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuestEnemy(String questEnemy) {
        this.questEnemy = questEnemy;
    }

    public void setMobsToHunt(Integer mobsToHunt) {
        this.mobsToHunt = mobsToHunt;
    }

    public void setRewardExp(Integer rewardExp) {
        this.rewardExp = rewardExp;
    }

    public void setRewardGold(Integer rewardGold) {
        this.rewardGold = rewardGold;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}

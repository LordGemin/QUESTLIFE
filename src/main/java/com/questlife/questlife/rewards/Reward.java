package main.java.com.questlife.questlife.rewards;

import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.util.RewardType;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Reward implements Serializable{

    private String name;
    private String description;
    private RewardType rewardType;
    private Skill[] associatedSkills;
    private int cost;
    private int canReceive;
    private int risingCost;

    public Reward() {

    }

    public Reward(String name, RewardType rewardType, Skill[] associatedSkills, int cost, int canReceive, int risingCost) {
        this.name = name;
        this.rewardType = rewardType;
        this.associatedSkills = associatedSkills;
        this.cost = cost;
        this.canReceive = canReceive;
        this.risingCost = risingCost;
    }

    public String getName(){
        return name;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public Skill[] getAssociatedSkills() {
        return associatedSkills;
    }

    public int getCost() {
        return cost;
    }

    public int getCanReceive() {
        return canReceive;
    }

    public int getRisingCost() {
        return risingCost;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public void setAssociatedSkills(Skill[] associatedSkills) {
        this.associatedSkills = associatedSkills;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCanReceive(int canReceive) {
        this.canReceive = canReceive;
    }

    public void setRisingCost(int risingCost) {
        this.risingCost = risingCost;
    }
}

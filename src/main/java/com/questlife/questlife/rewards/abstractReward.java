package main.java.com.questlife.questlife.rewards;

import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.util.RewardType;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public abstract class abstractReward implements Serializable{

    private String name;
    private RewardType rewardType;
    private Skill[] associatedSkills;
    private int cost;
    private int canReceive;
    private int risingCost;

    abstractReward(String name, RewardType rewardType, Skill[] associatedSkills, int cost, int canReceive, int risingCost) {
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
}

package main.java.com.questlife.questlife.rewards;

import main.java.com.questlife.questlife.skills.Skills;
import main.java.com.questlife.questlife.util.RewardType;

/**
 * Created by Gemin on 10.04.2017.
 */
public abstract class abstractRewards {

    private String name;
    private RewardType rewardType;
    private Skills associatedSkills;
    private int cost;
    private int canReceive;
    private int risingCost;

    abstractRewards(String name, RewardType rewardType, Skills associatedSkills, int cost, int canReceive, int risingCost) {
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

    public Skills getAssociatedSkills() {
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

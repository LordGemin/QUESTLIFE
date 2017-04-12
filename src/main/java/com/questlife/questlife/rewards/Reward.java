package main.java.com.questlife.questlife.rewards;

import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.util.RewardType;


/**
 * Created by Gemin on 10.04.2017.
 */
public class Reward extends abstractReward {

    public Reward(String name, RewardType rewardType, Skill[] associatedSkills, int cost, int canReceive, int risingCost) {
        super(name, rewardType,associatedSkills,cost,canReceive,risingCost);
    }
}
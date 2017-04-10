package main.java.com.questlife.questlife.rewards;

import main.java.com.questlife.questlife.skills.Skills;
import main.java.com.questlife.questlife.util.RewardType;

/**
 * Created by Gemin on 10.04.2017.
 */
public class Rewards extends abstractRewards {

    public Rewards(String name, RewardType rewardType, Skills associatedSkills, int cost, int canReceive, int risingCost) {
        super(name, rewardType,associatedSkills,cost,canReceive,risingCost);
    }
}
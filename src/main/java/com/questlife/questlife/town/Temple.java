package main.java.com.questlife.questlife.town;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.util.RewardType;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * Created by Gemin on 02.05.2017.
 */
public class Temple {

    private List<Reward> rewardList = new ArrayList<>();
    private String name;

    public Temple() {
        this("Temple");
    }

    public Temple(String name) {
        this.name = name;
    }

    public Temple(String name, ObservableList<Reward> rewardList) {
        this.name = name;
        this.rewardList = rewardList;
    }

    public void setRewardList(List<Reward> rewardList) {
        this.rewardList = rewardList;
    }

    public List<Reward> getRewardList() {
        return rewardList;
    }

    /**
     * Checks if hero can acquire a given reward.
     * Removes the reward from the reward list to symbolise getting it.
     *
     * @param hero Hero to get the reward
     * @param reward reward to be gotten
     * @return reward that was removed, null if user couldn't pay
     * @throws NoSuchElementException If the given reward was not found!
     */
    public Reward acquireReward(Hero hero, Reward reward) throws NoSuchElementException, NoSuchFieldException {

        // check if the reward exists
        if(!rewardList.contains(reward)) {
            throw new NoSuchElementException("This reward does not exist!");
        }
        // find the position of the reward within the rewardList
        final int pos = rewardList.indexOf(reward);

        // check if hero can BUY reward, remove it and return removed reward for further handling
        if(reward.getRewardType().equals(RewardType.GOLD_BASED)) {
            if(!hero.spendGold(reward.getCost())) {
                return null;
            }
            return rewardList.remove(pos);
        }

        // check if hero is skillfull enough to obtain reward, remove it and return removed reward
        if(reward.getRewardType().equals(RewardType.SKILL_LEVEL_BASED)) {
            if(reward.getAssociatedSkill().getLevel() != reward.getCost()) {
                return null;
            }
            return rewardList.remove(rewardList.indexOf(reward));
        }

        throw new NoSuchFieldException("Could not process reward type!");
    }


}

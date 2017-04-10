package main.java.com.questlife.questlife.quests;

/**
 * Created by Gemin on 10.04.2017.
 */
public abstract class Quests {

    private String name;
    private String description;
    private int mobsToHunt;
    private int rewardExp;
    private int rewardGold;

    public Quests(String name, String description, int mobsToHunt, int rewardExp, int rewardGold) {
        this.name = name;
        this.description = description;
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
}

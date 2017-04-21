package main.java.com.questlife.questlife.player;

import main.java.com.questlife.questlife.goals.Goals;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.skills.Skill;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Player implements Serializable {

    private String name;
    private Inventory inventory;
    private Hero playerHero;
    private List<Skill> associatedSkills = new ArrayList<>();
    private List<Reward> rewards = new ArrayList<>();
    private List<Goals> goalList = new ArrayList<>();
    private List<Goals> competedGoals  = new ArrayList<>();

    public Player() {

    }

    public Player(String name, Inventory inventory) {
        this.name = name;
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Skill[] getSkills() {
        return associatedSkills.toArray(new Skill[associatedSkills.size()]);
    }

    public Hero getPlayerHero() {
        return this.playerHero;
    }

    /**
     * Creates a skill (and adds it to the associated skills of the player. Name, Attributes associated, description are needed.
     * Furthermore the player can decide on the skilltype (goalbased/timebased)
     */
    public void addSkill (Skill newSkill) {
        //TODO: Let the player actually add skills
        associatedSkills.add(newSkill);
    }

    public void deleteSkill (Skill skillToDelete) {
        if(associatedSkills.contains(skillToDelete)) {
            associatedSkills.remove(skillToDelete);
        }
    }

    /**
     * Creates a reward. To create a reward the player has to define a name, a rewardType, associated skills, the cost, the times the reward can be received, as well rising costs
     */
    public void addReward (Reward newReward) {
        //TODO: Let the player actually add rewards
        rewards.add(newReward);
    }

    public void deleteReward (Reward rewardToDelete) {
        if(rewards.contains(rewardToDelete)) {
            rewards.remove(rewardToDelete);
        }
    }

    public void addGoal(Goals newGoal) {
        goalList.add(newGoal);
    }

    public void completeGoal(Goals goal, LocalDateTime newDeadline) {
        if(newDeadline != null)
            goal.completeGoal(newDeadline);
        else
            goalList.remove(goal);
            competedGoals.add(goal);
    }

    /**
     * Creates a hero. To create a hero you need a name and a weapon to start
     */
    public void createHero () {
        //TODO: Let the player actually add his hero, with name and stuff
        Hero hero = new Hero(this,"", null);
        playerHero = hero;
    }

    public void sendHeroOnQuest(Quest quest) {
        if(playerHero.getQuestList().contains(quest)) {
            playerHero.setActiveQuest(quest);
            quest.setAsActive();
        } else {
            //TODO: Warn player that some error occured, and the quest does not exist
        }

        playerHero.sendToField();
    }



}

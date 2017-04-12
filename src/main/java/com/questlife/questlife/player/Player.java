package main.java.com.questlife.questlife.player;

import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.skills.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gemin on 10.04.2017.
 */
public class Player {

    private String name;
    private Inventory inventory;
    private Hero playerHero;
    private List<Skill> associatedSkills = new ArrayList<>();
    private List<Reward> rewards = new ArrayList<>();

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
    public void addSkill () {
        //TODO: Let the player actually add skills
        Skill newSkill = new Skill(null, null,null,null);
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
    public void addReward () {
        //TODO: Let the player actually add rewards
        Reward newReward = new Reward(null,null,null,0,0,0);
        rewards.add(newReward);
    }

    public void deleteReward (Reward rewardToDelete) {
        if(rewards.contains(rewardToDelete)) {
            rewards.remove(rewardToDelete);
        }
    }

    /**
     * Creates a hero. To create a hero you need a name and a weapon to start
     */
    public void createHero () {
        //TODO: Let the player actually add his hero, with name and stuff
        Hero hero = new Hero(this,"", null);
        playerHero = hero;
    }
}

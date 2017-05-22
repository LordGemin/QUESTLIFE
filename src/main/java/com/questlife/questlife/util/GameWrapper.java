package main.java.com.questlife.questlife.util;

import main.java.com.questlife.questlife.goals.Goals;
import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractPotions;
import main.java.com.questlife.questlife.items.AbstractWeapons;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.skills.Skill;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 16.05.2017.
 */
@XmlRootElement(name = "questlife")
public class GameWrapper {

    private Statistics statistics;
    private List<Quest> quests;
    private List<Goals> goals;
    private List<Skill> skills;
    private List<Hero> heroes;
    private List<Reward> rewards;
    private List<AbstractWeapons> inventoryWeapons;
    private List<AbstractPotions> inventoryPotions;

    @XmlElement(name="Stats")
    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    @XmlElement(name="Quest")
    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    @XmlElement(name="Goal")
    public List<Goals> getGoals() {
        return goals;
    }

    public void setGoals(List<Goals> goals) {
        this.goals = goals;
    }

    @XmlElement(name="Skill")
    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @XmlElement(name="Hero")
    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @XmlElement(name="Reward")
    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    @XmlElement(name="InventoryWeapon")
    public List<AbstractWeapons> getInventoryWeapons() {
        return inventoryWeapons;
    }

    public void setInventoryWeapons(List<AbstractWeapons> inventoryWeapons) {
        this.inventoryWeapons = inventoryWeapons;
    }

    @XmlElement(name="InventoryPotion")
    public List<AbstractPotions> getInventoryPotions() {
        return inventoryPotions;
    }

    public void setInventoryPotions(List<AbstractPotions> inventoryPotions) {
        this.inventoryPotions = inventoryPotions;
    }
}

package main.java.com.questlife.questlife.skills;

import main.java.com.questlife.questlife.util.Attributes;
import main.java.com.questlife.questlife.util.SkillType;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Skill implements Serializable{

    private String name;
    private Attributes associatedAttribute;
    private String description;
    private SkillType skilltype;
    private Integer level;
    private Integer experience;
    private Integer experienceToNextLevel;

    public Skill(String name, Attributes associatedAttribute, String description, SkillType skilltype) {
        this.name = name;
        this.associatedAttribute = associatedAttribute;
        this.description = description;
        this.skilltype = skilltype;
    }

    public String getName() {
        return name;
    }

    public Attributes getAssociatedAttribute() {
        return associatedAttribute;
    }

    public String getDescription() {
        return description;
    }

    public SkillType getSkilltype() {
        return skilltype;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getExperienceToNextLevel() {
        return experienceToNextLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssociatedAttribute(Attributes associatedAttribute) {
        this.associatedAttribute = associatedAttribute;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSkilltype(SkillType skilltype) {
        this.skilltype = skilltype;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setExperienceToNextLevel(int experienceToNextLevel) {
        this.experienceToNextLevel = experienceToNextLevel;
    }

    private void levelUp() {
        this.level++;
        this.experienceToNextLevel = experienceToNextLevel + 1000+100*Math.round(level/10);
        //TODO: Rethink this formula
    }

    public void gainExperience(int experienceGained) {
        this.experience += experienceGained;
        while (this.experience >= this.experienceToNextLevel) {
            levelUp();
            //TODO: Message to Player. Congrats or something
        }
    }
}

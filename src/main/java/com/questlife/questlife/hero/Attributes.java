package main.java.com.questlife.questlife.hero;

import main.java.com.questlife.questlife.util.Logger;
import main.java.com.questlife.questlife.util.StatCalculator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
@XmlRootElement (name = "Attributes")
public enum Attributes implements Serializable {

    @XmlElement (name = "Strength")
    STRENGTH("Strength"),

    @XmlElement (name = "Dexterity")
    DEXTERITY("Dexterity"),

    @XmlElement (name = "Mind")
    MIND ("Mind"),

    @XmlElement (name = "Charisma")
    CHARISMA("Charisma"),

    @XmlElement (name = "Constitution")
    CONSTITUTION ("Constitution"),

    @XmlElement (name = "Piety")
    PIETY ("Piety"),

    @XmlElement (name = "Observation")
    OBSERVATION("Observation");

    private final String fieldDescription;
    private Integer experience = 0;
    private Integer experienceToNextLevel = 1000;
    private Integer level = 1;

    Attributes(String value) {
        fieldDescription = value;
    }

    @Override
    public String toString() {
        return fieldDescription;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public static Attributes getField(String description) {
        for(Attributes a: Attributes.values()) {
            if(a.getFieldDescription().equals(description)) {
                return a;
            }
        }
        return null;
    }

    public Integer getLevel() {return level;}

    public void setLevel(int level) {this.level = level;}

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getExperienceToNextLevel() {
        return experienceToNextLevel;
    }

    public void setExperienceToNextLevel(Integer experienceToNextLevel) {
        this.experienceToNextLevel = experienceToNextLevel;
    }

    public void levelUp() {
        level++;
        Logger.log(fieldDescription+" leveled up!");
        this.experienceToNextLevel = StatCalculator.getExpToNextLevel(experienceToNextLevel,level);
    }

    public void gainExperience(int experienceGained) {
        if(experience == null) {
            experience = 0;
        }
        this.experience += experienceGained;
        while (this.experience >= this.experienceToNextLevel) {
            levelUp();
        }
    }
}

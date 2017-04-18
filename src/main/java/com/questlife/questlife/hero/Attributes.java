package main.java.com.questlife.questlife.hero;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public enum Attributes implements Serializable{
    STRENGTH("Strength"), DEXTERITY("Dexterity"), MIND ("Mind"), CHARISMA("Charisma"),
    CONSTITUTION ("Constitution"), PIETY ("Piety"), OBSERVATION("Piety");

    private final String fieldDescription;
    private Integer experience;
    private Integer experienceToNextLevel;
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

    public Integer getLevel() {return level;}

    public void setLevel(int level) {this.level = level;}

    public void levelUp() {
        level++;
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

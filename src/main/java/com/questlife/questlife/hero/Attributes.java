package main.java.com.questlife.questlife.hero;

import javafx.scene.control.Toggle;
import main.java.com.questlife.questlife.util.StatCalculator;

import java.io.Serializable;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public enum Attributes implements Serializable {
    STRENGTH("Strength"), DEXTERITY("Dexterity"), MIND ("Mind"), CHARISMA("Charisma"),
    CONSTITUTION ("Constitution"), PIETY ("Piety"), OBSERVATION("Piety");

    private final String fieldDescription;
    private Integer experience;
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

    public void levelUp() {
        StatCalculator stats = new StatCalculator();
        level++;
        System.out.println(fieldDescription+" leveld up!");
        this.experienceToNextLevel = stats.getExpToNextLevel(experienceToNextLevel,level);
    }

    public void gainExperience(int experienceGained) {
        this.experience += experienceGained;
        while (this.experience >= this.experienceToNextLevel) {
            levelUp();
            //TODO: Message to Player. Congrats or something
        }
    }
}

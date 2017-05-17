package main.java.com.questlife.questlife.skills;

import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.util.AttributesAdapter;
import main.java.com.questlife.questlife.util.SkillType;
import main.java.com.questlife.questlife.util.SkillTypeAdapter;
import main.java.com.questlife.questlife.util.StatCalculator;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;


/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Skill implements Serializable{

    private String name;
    private String associatedAttribute;
    private String description;
    private SkillType skilltype = SkillType.GOALBASED;
    private Integer level =1;
    private Integer experience = 0;
    private Integer experienceToNextLevel = getExperienceToNextLevel();


    public Skill(String name, Attributes associatedAttribute, String description, SkillType skilltype) {
        this.name = name;
        this.associatedAttribute = associatedAttribute.getFieldDescription();
        this.description = description;
        this.skilltype = skilltype;
    }

    public Skill() {
        name = "";
        associatedAttribute = null;
    }

    public Skill(String name, String associatedAttribute, String description, SkillType skilltype, Integer level, Integer experience, Integer experienceToNextLevel) {
        this.name = name;
        this.associatedAttribute = associatedAttribute;
        this.description = description;
        this.skilltype = skilltype;
        this.level = level;
        this.experience = experience;
        this.experienceToNextLevel = experienceToNextLevel;
    }

    public String getName() {
        return name;
    }


    @XmlJavaTypeAdapter(AttributesAdapter.class)
    public Attributes getAssociatedAttribute() {
        return Attributes.getField(associatedAttribute);
    }

    public String getDescription() {
        return description;
    }

    @XmlJavaTypeAdapter(SkillTypeAdapter.class)
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
        if(skilltype.equals(SkillType.TIMEBASED)) {
            experienceToNextLevel=600*level;
        }
        if(experienceToNextLevel==null) {
            experienceToNextLevel = StatCalculator.getExpToNextLevel(0,1);
        }
        return experienceToNextLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssociatedAttribute(Attributes associatedAttribute) {
        this.associatedAttribute = associatedAttribute.getFieldDescription();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSkilltype(SkillType skilltype) {
        if(skilltype == SkillType.TIMEBASED) {
            experienceToNextLevel = 600*level;
        } else if(skilltype == SkillType.GOALBASED) {
            experienceToNextLevel=StatCalculator.getExpToNextLevel(0,1);
            // If the level is greater than 1, we shall iteratively find the appropriate experience to next level
            for(int i = 2; i<level;i++) {
                this.experienceToNextLevel = StatCalculator.getExpToNextLevel(experienceToNextLevel, i);
            }
        }
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
        System.out.println(name+" leveled up!");
        if(skilltype.equals(SkillType.GOALBASED)) {
            this.experienceToNextLevel = StatCalculator.getExpToNextLevel(experienceToNextLevel, level);
        } else if(skilltype.equals(SkillType.TIMEBASED)){
            this.experienceToNextLevel += 600;
        }
        if(!associatedAttribute.equals("")) {
            Attributes.getField(associatedAttribute).levelUp();
        }
    }

    public void gainExperience(int experienceGained) {
        this.experience += experienceGained;
        while (this.experience >= this.experienceToNextLevel) {
            levelUp();
            //TODO: Message to Player. Congrats or something
        }
    }
}

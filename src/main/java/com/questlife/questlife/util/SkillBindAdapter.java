package main.java.com.questlife.questlife.util;

import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.skills.Skill;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * Created by Gemin on 13.05.2017.
 */
public class SkillBindAdapter extends XmlAdapter<String, Skill> {

    @Override
    public Skill unmarshal(String v) throws Exception {

        if(!v.contains(";")) {
            return null;
        }

        Skill skill = new Skill();
        String[] attr = v.split(";");

        skill.setName(attr[0]);
        skill.setDescription(attr[1]);
        skill.setExperience(Integer.parseInt(attr[2]));
        skill.setExperienceToNextLevel(Integer.parseInt(attr[3]));
        skill.setLevel(Integer.parseInt(attr[4]));
        skill.setSkilltype(SkillType.getField(attr[5]));
        skill.setAssociatedAttribute(Attributes.getField(attr[6]));

        return skill;
    }

    @Override
    public String marshal(Skill v) throws Exception {
        String wrappedSkill = "";
        wrappedSkill += v.getName(); // attr 0
        wrappedSkill += ";";
        wrappedSkill += v.getDescription();  // attr 1
        wrappedSkill += ";";
        wrappedSkill += v.getExperience(); // attr 2
        wrappedSkill += ";";
        wrappedSkill += v.getExperienceToNextLevel();  // attr 3
        wrappedSkill += ";";
        wrappedSkill += v.getLevel();  // attr 4
        wrappedSkill += ";";
        wrappedSkill += v.getSkilltype().getFieldDescription();  // attr 5
        wrappedSkill += ";";
        wrappedSkill += v.getAssociatedAttribute().getFieldDescription();  // attr 6

        return wrappedSkill;

    }
}

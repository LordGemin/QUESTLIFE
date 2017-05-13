package main.java.com.questlife.questlife.util;

import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.skills.Skill;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 13.05.2017.
 */
public class SkillListBindAdapter extends XmlAdapter<List<String>, List<Skill>> {

    @Override
    public List<Skill> unmarshal(List<String> values) throws Exception {
        List<Skill> skillList = new ArrayList<>();

        for(String v:values) {

            if (!v.contains(";")) {
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
            skillList.add(skill);
        }

        return skillList;
    }

    @Override
    public List<String> marshal(List<Skill> values) throws Exception {
        List<String> stringList = new ArrayList<>();
        
        for (Skill v: values) {
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
            stringList.add(wrappedSkill);
        }

        return stringList;

    }
}

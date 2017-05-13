package main.java.com.questlife.questlife.skills;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * Created by Gemin on 13.05.2017.
 */
@XmlRootElement(name = "Skills")
public class SkillWrapper {

    private List<Skill> skills;

    @XmlElement(name = "Skill")
    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}

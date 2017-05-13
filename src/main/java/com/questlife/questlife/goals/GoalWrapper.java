package main.java.com.questlife.questlife.goals;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * Created by Gemin on 13.05.2017.
 */
@XmlRootElement(name = "Goals")
public class GoalWrapper {

    private List<Goals> goals;

    @XmlElement(name = "Goal")
    public List<Goals> getGoals() {
        return goals;
    }

    public void setGoals(List<Goals> goals) {
        this.goals = goals;
    }
}

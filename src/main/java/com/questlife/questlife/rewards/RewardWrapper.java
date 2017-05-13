package main.java.com.questlife.questlife.rewards;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *
 * Created by Gemin on 13.05.2017.
 */
@XmlRootElement(name = "Rewards")
public class RewardWrapper {

    private List<Reward> rewards;

    @XmlElement(name = "Reward")
    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }
}

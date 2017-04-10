package test.java.com.questlife.questlife.rewards;

import main.java.com.questlife.questlife.rewards.Rewards;
import main.java.com.questlife.questlife.skills.Skills;
import main.java.com.questlife.questlife.util.RewardType;

/**
 * Created by Gemin on 10.04.2017.
 */
public class RewardTest {

    private Skills skill;
    private Rewards reward;

    public RewardTest() {
        testConstructor();
    }

    private void testConstructor () {

        try {
            reward = new Rewards("Blessing of Kobosuba", RewardType.GOLD_BASED,
                    skill, 2000, 2, 100);
            System.out.println("You created the " + reward.getName()+ " which is a reward that is "+ reward.getRewardType().toString()+". But you have to pay up "+ reward.getCost()+" to receive it.");
        } catch (Exception e) {
            System.out.println("WHOA something happened in RewardTest");
        }

        System.out.println("Cool, RewardTest is done");
    }


}

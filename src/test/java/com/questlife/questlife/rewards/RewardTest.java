package test.java.com.questlife.questlife.rewards;

import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Attributes;
import main.java.com.questlife.questlife.util.RewardType;
import main.java.com.questlife.questlife.util.SkillType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class RewardTest {


    @Test
    public void testCreation () {

        Skill[] skill = new Skill[1];
        skill[0] = new Skill("Situps", Attributes.STRENGTH,"Do Situps", SkillType.TIMEBASED);
        Reward reward;
        reward = new Reward("Blessing of Kobosuba", RewardType.GOLD_BASED, skill, 2000, 2, 100);
        Skill[] test = reward.getAssociatedSkills();

        assertEquals("Blessing of Kobosuba", reward.getName());
        assertEquals(RewardType.GOLD_BASED, reward.getRewardType());
        assertEquals(Attributes.STRENGTH, test[0].getAssociatedAttribute());
    }


}

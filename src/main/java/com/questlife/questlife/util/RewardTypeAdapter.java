package main.java.com.questlife.questlife.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapter for JAXB binding to convert rewardTypes to strings and back
 *
 * Created by Gemin on 13.05.2017.
 */
public class RewardTypeAdapter extends XmlAdapter<String, RewardType> {

    @Override
    public RewardType unmarshal(String v) throws Exception {
        if(v.contains("Gold")) {
            return RewardType.GOLD_BASED;
        }
        if(v.contains("Skill")) {
            return RewardType.SKILL_LEVEL_BASED;
        }
        return null;
    }

    @Override
    public String marshal(RewardType v) throws Exception {
        return v.getFieldDescription();
    }
}

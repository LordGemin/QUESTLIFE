package main.java.com.questlife.questlife.util;

/**
 * Created by Gemin on 10.04.2017.
 */
public enum RewardType {
    GOLD_BASED("Gold based"), SKILL_LEVEL_BASED("Skill level based");

    private final String fieldDescription;

    RewardType(String value) {
        fieldDescription = value;
    }

    public static RewardType getField(String description) {
        for(RewardType r:RewardType.values()) {
            if(r.getFieldDescription().equals(description)) {
                return r;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return fieldDescription;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}

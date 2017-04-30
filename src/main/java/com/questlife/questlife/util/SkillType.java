package main.java.com.questlife.questlife.util;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public enum SkillType {
    TIMEBASED("Time based"), GOALBASED("Goal based");

    private final String fieldDescription;

    SkillType(String value) {
        fieldDescription = value;
    }


    public static SkillType getField(String description) {
        for(SkillType a: SkillType.values()) {
            if(a.getFieldDescription().equals(description)) {
                return a;
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

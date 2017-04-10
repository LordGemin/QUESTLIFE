package main.java.com.questlife.questlife.util;

/**
 * Created by Gemin on 10.04.2017.
 */
public enum SkillType {
    TIMEBASED("Time based"), GOALBASED("Goal based");

    private final String fieldDescription;

    SkillType(String value) {
        fieldDescription = value;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}

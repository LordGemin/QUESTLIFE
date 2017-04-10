package main.java.com.questlife.questlife.util;

/**
 * Created by Gemin on 10.04.2017.
 */
public enum Attributes {
    STRENGTH("Strength"), DEXTERITY("Dexterity"), MIND ("Mind"), CHARISMA("Charisma"),
    CONSTITUTION ("Constitution"), PIETY ("Piety"), OBSERVATION("Piety");

    private final String fieldDescription;

    Attributes(String value) {
        fieldDescription = value;
    }

    @Override
    public String toString() {
        return fieldDescription;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }
}

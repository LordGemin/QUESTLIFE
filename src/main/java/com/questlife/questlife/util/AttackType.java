package main.java.com.questlife.questlife.util;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public enum AttackType {
    PHYSICAL("Physical dealDamage"), MAGICAL("Magical dealDamage");

    private final String fieldDescription;

    AttackType(String value) {
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

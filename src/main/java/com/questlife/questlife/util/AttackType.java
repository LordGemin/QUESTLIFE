package main.java.com.questlife.questlife.util;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public enum AttackType {
    PHYSICAL("Physical"), MAGICAL("Magical"), BOTH("Physical & Magical");

    private final String fieldDescription;

    AttackType(String value) {
        fieldDescription = value;
    }

    public static AttackType getField(String description) {
        for(AttackType a: AttackType.values()) {
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

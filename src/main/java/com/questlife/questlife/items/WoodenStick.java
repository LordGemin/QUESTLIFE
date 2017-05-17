package main.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.StatCalculator;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public final class WoodenStick extends AbstractWeapons {


    static String identifier = "WOST";

    private final Integer BASEPRICE = 75;
    private final Integer BASEMINDAMAGE = 5;
    private final Integer BASEMAXDAMAGE = 7;

    public WoodenStick() {
        this.name = "Wooden Stick";
        this.description = "A dull weapon that can be used for clubbing and casting";
        this.attackType = AttackType.BOTH;
    }

    public WoodenStick(int levelOfHero) {
        this.name = "Wooden Stick";
        this.price = StatCalculator.calculateWeaponPrice(BASEPRICE, levelOfHero);
        this.description = "A dull weapon that can be used for clubbing and casting";
        this.physicalAttack = StatCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.magicalAttack = StatCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.attackType = AttackType.BOTH;
    }

    public void setHeroLevel(int levelOfHero) {
        this.name = "Wooden Stick";
        this.price = StatCalculator.calculateWeaponPrice(BASEPRICE, levelOfHero);
        this.description = "A dull weapon that can be used for clubbing and casting";
        this.physicalAttack = StatCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.magicalAttack = StatCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.attackType = AttackType.BOTH;
    }

}

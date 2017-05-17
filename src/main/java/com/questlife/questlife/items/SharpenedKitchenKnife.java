package main.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.StatCalculator;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public final class SharpenedKitchenKnife extends AbstractWeapons {


    static String identifier = "SHKK";

    private final Integer BASEPRICE = 200;
    private final Integer BASEMINDAMAGE = 10;
    private final Integer BASEMAXDAMAGE = 15;

    public SharpenedKitchenKnife() {
        this.name = "Sharpened Kitchen Knife";
        this.description = "A scary tool in the hands of a clown.";
        this.attackType = AttackType.PHYSICAL;
    }

    public SharpenedKitchenKnife(int levelOfHero) {
        this.name = "Sharpened Kitchen Knife";
        this.price = StatCalculator.calculateWeaponPrice(BASEPRICE, levelOfHero);
        this.description = "A scary tool in the hands of a clown.";
        this.physicalAttack = StatCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.magicalAttack = StatCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.attackType = AttackType.PHYSICAL;
    }

    public void setHeroLevel(int levelOfHero) {
        this.name = "Sharpened Kitchen Knife";
        this.price = StatCalculator.calculateWeaponPrice(BASEPRICE, levelOfHero);
        this.description = "A scary tool in the hands of a clown.";
        this.physicalAttack = StatCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.magicalAttack = StatCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.attackType = AttackType.PHYSICAL;
    }
}

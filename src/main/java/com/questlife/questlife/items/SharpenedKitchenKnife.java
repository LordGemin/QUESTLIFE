package main.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.util.AttackType;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public final class SharpenedKitchenKnife extends AbstractWeapons {

    private final Integer BASEPRICE = 50;
    private final Integer BASEMINDAMAGE = 10;
    private final Integer BASEMAXDAMAGE = 15;

    public SharpenedKitchenKnife() {

    }

    public SharpenedKitchenKnife(int levelOfHero) {
        this.name = "Sharpened Kitchen Knife";
        this.price = statCalculator.calculateWeaponPrice(BASEPRICE, levelOfHero);
        this.description = "A scary tool in the hands of a clown.";
        this.physicalAttack = statCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.magicalAttack = statCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.attackType = AttackType.PHYSICAL;
    }

    public void setHeroLevel(int levelOfHero) {
        this.name = "Sharpened Kitchen Knife";
        this.price = statCalculator.calculateWeaponPrice(BASEPRICE, levelOfHero);
        this.description = "A scary tool in the hands of a clown.";
        this.physicalAttack = statCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.magicalAttack = statCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.attackType = AttackType.PHYSICAL;
    }
}

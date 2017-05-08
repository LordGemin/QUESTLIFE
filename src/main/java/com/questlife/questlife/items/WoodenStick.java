package main.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.util.AttackType;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public final class WoodenStick extends AbstractWeapons {

    private final Integer BASEPRICE = 75;
    private final Integer BASEMINDAMAGE = 5;
    private final Integer BASEMAXDAMAGE = 7;

    public WoodenStick() {

    }

    public WoodenStick(int levelOfHero) {
        this.name = "Wooden Stick";
        this.price = statCalculator.calculateWeaponPrice(BASEPRICE, levelOfHero);
        this.description = "A dull weapon that can be used for clubbing and casting";
        this.physicalAttack = statCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.magicalAttack = statCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.attackType = AttackType.BOTH;
    }

    public void setHeroLevel(int levelOfHero) {
        this.name = "Wooden Stick";
        this.price = statCalculator.calculateWeaponPrice(BASEPRICE, levelOfHero);
        this.description = "A dull weapon that can be used for clubbing and casting";
        this.physicalAttack = statCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.magicalAttack = statCalculator.calculateWeaponDamage(BASEMINDAMAGE,BASEMAXDAMAGE,levelOfHero);
        this.attackType = AttackType.BOTH;
    }

}

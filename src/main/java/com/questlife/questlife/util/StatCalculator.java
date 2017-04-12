package main.java.com.questlife.questlife.util;

import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.Weapon;

/**
 * Created by Gemin on 11.04.2017.
 */
public class StatCalculator {

    public StatCalculator() {

    }

    public int calculateHeroesDefense(Hero hero) {
        int baseValue = hero.getStrength();
        Weapon heroWeapon = hero.getWeapon();

        baseValue += Math.round(heroWeapon.getPhysicalAttack()/10);

        return baseValue*2;
    }

    public int calculateHeroesResistance(Hero hero) {
        int baseValue = hero.getMind();
        Weapon heroWeapon = hero.getWeapon();

        baseValue += Math.round(heroWeapon.getMagicalAttack()/15);

        return  baseValue*3;
    }

    public int calculateHeroesAttack(Hero hero) {
        int attackValue;
        Weapon heroWeapon;
        try {
            heroWeapon = hero.getWeapon();
        } catch (Exception e) {
            heroWeapon = new Weapon("", 0,0,null);
        }

        if(heroWeapon.getAttackType() == null) {
            attackValue = 10;
        } else if (heroWeapon.getAttackType() == AttackType.PHYSICAL) {
            attackValue = hero.getStrength();
            attackValue += heroWeapon.getPhysicalAttack();
        } else if (heroWeapon.getAttackType() == AttackType.MAGICAL) {
            attackValue = hero.getMind();
            attackValue += heroWeapon.getMagicalAttack();
        } else {
            attackValue = 10;
        }

        return attackValue;
    }

}

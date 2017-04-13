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
        //If hero has no weapon equipped, give him nonsense weapon;
        Weapon heroWeapon = (hero.getWeapon() != null) ? hero.getWeapon() : new Weapon("Bare knuckle", 0,0,AttackType.PHYSICAL);

        baseValue += Math.round(heroWeapon.getPhysicalAttack()/10);

        return baseValue*2;
    }

    public int calculateHeroesResistance(Hero hero) {
        int baseValue = hero.getMind();
        //If hero has no weapon equipped, give him nonsense weapon;
        Weapon heroWeapon = (hero.getWeapon() != null) ? hero.getWeapon() : new Weapon("Bare knuckle", 0,0,AttackType.PHYSICAL);

        baseValue += Math.round(heroWeapon.getMagicalAttack()/15);

        return  baseValue*3;
    }

    public int calculateHeroesAttack(Hero hero) {
        int attackValue;
        //If hero has no weapon equipped, give him nonsense weapon;
        Weapon heroWeapon = (hero.getWeapon() != null) ? hero.getWeapon() : new Weapon("Bare knuckle", 0,0,AttackType.PHYSICAL);

        if (heroWeapon.getAttackType() == AttackType.PHYSICAL) {
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

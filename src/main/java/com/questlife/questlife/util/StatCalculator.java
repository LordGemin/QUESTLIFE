package main.java.com.questlife.questlife.util;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.goals.Goals;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.Weapon;

/**
 * 
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

        // Resistance will usually be higher than defense
        return  (int)Math.round(baseValue*2.5);
    }

    public int calculateHeroesAttack(Hero hero) {
        int attackValue;
        //If hero has no weapon equipped, give him nonsense weapon;
        Weapon heroWeapon = (hero.getWeapon() != null) ? hero.getWeapon() : new Weapon("Bare knuckle", 4,4,AttackType.PHYSICAL);

        if (heroWeapon.getAttackType() == AttackType.PHYSICAL) {
            attackValue = hero.getStrength();
            attackValue += heroWeapon.getPhysicalAttack();
        } else if (heroWeapon.getAttackType() == AttackType.MAGICAL) {
            // It is suspected that usually, the Mind attribute will be higher, resulting in higher magical damage
            attackValue = hero.getMind();
            attackValue += heroWeapon.getMagicalAttack();
        } else {
            attackValue = 10;
        }

        return attackValue;
    }

    public int getMaxHealth(Hero hero) {
        return (hero.getConstitution() < 10) ?  50:hero.getConstitution()*5;
    }

    public int getMaxMana(Hero hero) {
        return (hero.getPiety() < 10) ?  50:hero.getConstitution()*5;
    }

    public int getExperienceFromGoal(Goals goal) {
        // We want the gained experience to be split between all associated skills.
        return (int)(Math.round(goal.getComplexity()*0.75*goal.getAmountOfWork())/goal.getAssociatedSkills().size());
    }

    public int getExperienceFromEnemy(Enemy enemy) {
        return Math.round(enemy.getAttackPower()*enemy.getHealth()/2);
    }

    public int getGoldFromEnemy(Enemy enemy) {
        return Math.round(enemy.getAttackPower()*enemy.getHealth()/4);
    }

    public int getExpToNextLevel (int experienceToThisLevel, int level) {
        if(level == 1) {
            return 1000;
        }
        return experienceToThisLevel + 1000+100*Math.round(level/10);
    }

    public int getRebate(Hero hero, int cost) {
        //TODO: Add inverse square function to get slowly rising rebates with asymptote at 0.501 (to reach 0.5)
        return Math.round((hero.getCharisma()/100)*cost);
    }
}

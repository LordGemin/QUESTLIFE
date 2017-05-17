package main.java.com.questlife.questlife.util;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.goals.Goals;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractWeapons;
import main.java.com.questlife.questlife.items.Weapon;

import java.text.DecimalFormat;

/**
 * 
 * Created by Gemin on 11.04.2017.
 */
public final class StatCalculator {

    private static Generator generator = new Generator();

    public StatCalculator() {

    }

    public static int calculateHeroesDefense(Hero hero) {
        int baseValue = hero.getStrength().getLevel();
        //If hero has no weapon equipped, give him nonsense weapon;
        AbstractWeapons heroWeapon = (hero.getWeapon() != null) ? hero.getWeapon() : new Weapon("Bare knuckle", 0,0,AttackType.PHYSICAL);

        baseValue += Math.round(heroWeapon.getPhysicalAttack()/10.0f);

        return baseValue*2;
    }

    public static int calculateHeroesResistance(Hero hero) {
        int baseValue = hero.getMind().getLevel();
        //If hero has no weapon equipped, give him nonsense weapon;
        AbstractWeapons heroWeapon = (hero.getWeapon() != null) ? hero.getWeapon() : new Weapon("Bare knuckle", 0,0,AttackType.PHYSICAL);

        baseValue += Math.round(heroWeapon.getMagicalAttack()/15.0f);

        // Resistance will usually be higher than defense
        return  (int)Math.round(baseValue*2.5);
    }

    public static int calculateHeroesAttack(Hero hero) {
        int attackValue;
        //If hero has no weapon equipped, give him some weapon;
        AbstractWeapons heroWeapon = (hero.getWeapon() != null) ? hero.getWeapon() : new Weapon("Bare knuckle", 4,4,AttackType.PHYSICAL);

        switch (heroWeapon.getAttackType()) {
            case PHYSICAL:
                attackValue = hero.getStrength().getLevel();
                attackValue += heroWeapon.getPhysicalAttack();
                break;
            case MAGICAL:
                // It is suspected that usually, the Mind attribute will be higher, resulting in higher magical damage
                attackValue = hero.getMind().getLevel();
                attackValue += heroWeapon.getMagicalAttack();
                break;
            case BOTH:
                if(hero.getMana()<=0) {
                    attackValue = hero.getStrength().getLevel();
                    attackValue += heroWeapon.getPhysicalAttack();
                } else {
                    attackValue = hero.getMind().getLevel();
                    attackValue += heroWeapon.getMagicalAttack();
                }
                break;
            default:
                attackValue = 10;
                break;
        }

        return attackValue;
    }

    /**
     * Calculates weaponprice based on base and level of hero.
     * Half the baseprice is the minimumprice, and it can go up dramatically from there
     * @param baseprice
     * @param levelOfHero
     * @return weaponprice
     */
    public static int calculateWeaponPrice(int baseprice, int levelOfHero) {
        return (baseprice + generator.generateNumber()%(baseprice+levelOfHero*2));
        //TODO: Rethink this formula
    }

    public static int calculateWeaponDamage(int basedamage, int maxdamage, int levelOfHero) {
        return (basedamage + generator.generateNumber()%(maxdamage+levelOfHero*3));
    }

    public static int getMaxHealth(Hero hero) {
        int consti = hero.getConstitution().getLevel();
        return (consti < 10) ?  100:consti*30;
    }

    public static int getMaxMana(Hero hero) {
        int piety = hero.getPiety().getLevel();
        return (piety < 10) ?  100:piety*20;
    }

    public static int getExperienceFromGoal(Goals goal) {
        // We want the gained experience to be split between all associated skills.

        float div = goal.getAssociatedSkills().size();

        if(div == 0) {
            div=1;
        }

        return (int)(Math.round(goal.getComplexity()*0.75*goal.getAmountOfWork())/div);
    }

    public static int getExperienceFromEnemy(Enemy enemy) {
        return Math.round(enemy.getAttackPower()*enemy.getMaxHealth()/20.0f);
    }

    public static int getGoldFromEnemy(Enemy enemy) {
        return Math.round(enemy.getAttackPower()*enemy.getMaxHealth()/35.0f);
    }

    public static int getExpToNextLevel (int experienceToThisLevel, int level) {
        if(level == 1) {
            return 1000;
        }
        return experienceToThisLevel + 1000+100*Math.round(level/10);
    }

    public static long getExpToNextLevel (int level) {
        if(level == 1) {
            return 500;
        }
        long exp = 10*level*level+1840*level-720;
        // Eliminate last three digits
        long cut = exp/1000;
        cut *= 1000;
        // When subtracting we will be left with a number 0-999. We round according to that.
        exp = (exp-cut<500) ? cut:cut+1000;

        // Result:
        // 1011080 --> 1011000
        // 1022100730 --> 1022101000
        return exp;
    }

    public static int getRebate(Hero hero, int cost) {
        int charisma = hero.getCharisma().getLevel();

        if(charisma < 50) {
            return Math.round(0.0025f*charisma*cost);
        }
        else {
            // We want to slowly increase the correction value from -5.5 to 0
            int fiftyToHundred = charisma-50;
            // For this we need to step by step increase the value, by whatever step/9.09090909090909
            float div = 50/5.5f;
            // Now we have a correction value
            float correction = (-5.5f+fiftyToHundred/div);
            // But we only want it smaller 0. No overcorrection.
            if(correction > 0) {
                correction = 0;
            }
            return (int)(Math.round((-1/(0.4f)*Math.sqrt(charisma+correction))+0.5f)*cost);
        }
    }
}

package main.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.util.AttackType;
import org.reflections.Reflections;

import java.util.*;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Weapon extends AbstractItems {

    private int physicalAttack, magicalAttack;
    private AttackType attackType;

    public Weapon (int heroLevel) {
        generateWeapon(heroLevel);
    }

    /**
     * @param name define name for the weapon
     * @param physicalAttack define physical attack value of the weapon
     * @param magicalAttack define magical attack value of the weapon
     * @param attackType define the attackType of the weapon
     */
    public Weapon (String name, int physicalAttack, int magicalAttack, AttackType attackType) {
        this.name = name;
        this.physicalAttack = physicalAttack;
        this.magicalAttack = magicalAttack;
        this.attackType = attackType;
    }

    /**
     * @return provides physical attack value of the weapon
     */
    public int getPhysicalAttack() {
        return physicalAttack;
    }

    /**
     * @return provides magical attack value of the weapon
     */
    public int getMagicalAttack() {
        return magicalAttack;
    }

    /**
     * @return provides attack type of the weapon
     */
    public AttackType getAttackType() {
        return attackType;
    }

    private AbstractWeapons generateWeapon(int heroLevel) {
        Reflections reflections = new Reflections("main.java");
        Set<Class<? extends AbstractWeapons>> classes = reflections.getSubTypesOf(AbstractWeapons.class);
        List<String> weapons = new ArrayList<>();

        for (Object a : classes) {
            try {
                weapons.add(a.toString().replace("class ", ""));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        AbstractWeapons weapon = null;
        try {
            Class b = Class.forName(weapons.get(new Random().nextInt(weapons.size())));
            weapon = (AbstractWeapons) b.newInstance();
            weapon.setHeroLevel(heroLevel);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (weapon != null) {
            weapon.setHeroLevel(heroLevel);
        }
        return  weapon;
        /*
        Generator generator = new Generator();
        this.physicalAttack = generator.generateNumber()%heroLevel*3;
        this.magicalAttack = generator.generateNumber()%heroLevel*3;
        this.name = generator.generateWeaponName();

        while(physicalAttack == magicalAttack) {
            physicalAttack += generator.generateNumber()%2;
            magicalAttack += generator.generateNumber()%2;
        }

        if (physicalAttack > magicalAttack ) {
            this.attackType = AttackType.PHYSICAL;
        } else {
            this.attackType = AttackType.MAGICAL;
        }
        */
    }

    @Override
    public void setHeroLevel(int levelOfHero) {

    }
}

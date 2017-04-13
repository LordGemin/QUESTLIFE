package main.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;

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


    public Weapon (String name, int price, String description) {
        super(name,price,description);
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

    public void setPhysicalAttack(int physicalAttack) {
        this.physicalAttack = physicalAttack;
    }

    public void setMagicalAttack(int magicalAttack) {
        this.magicalAttack = magicalAttack;
    }

    private void generateWeapon(int heroLevel) {
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
    }
}

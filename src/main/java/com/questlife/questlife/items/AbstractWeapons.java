package main.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;
import main.java.com.questlife.questlife.util.StatCalculator;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public abstract class AbstractWeapons extends AbstractItems{

    StatCalculator statCalculator = new StatCalculator();
    Integer physicalAttack, magicalAttack;
    AttackType attackType;

    public AbstractWeapons() {

    }

    public AbstractWeapons(int heroLevel) {

    }

    public AbstractWeapons(String name, int price, String description) {
        super(name,price,description);
    }

    public AbstractWeapons(int physicalAttack, int magicalAttack, AttackType attackType) {
        this.physicalAttack = physicalAttack;
        this.magicalAttack = magicalAttack;
        this.attackType = attackType;
    }

    public AbstractWeapons(String name, int price, String description, int physicalAttack, int magicalAttack, AttackType attackType) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.physicalAttack = physicalAttack;
        this.magicalAttack = magicalAttack;
        this.attackType = attackType;
    }

    public Integer getPhysicalAttack() {
        return physicalAttack;
    }

    public Integer getMagicalAttack() {
        return magicalAttack;
    }

    public AttackType getAttackType() {
        return attackType;
    }

}

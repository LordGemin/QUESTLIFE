package test.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.util.AttackType;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class WeaponTest {
    private Weapon weapon;

    public WeaponTest() {
        testConstructor();
    }

    private void testConstructor () {

        try {
            weapon = new Weapon("Excalibar", 0, 0, AttackType.PHYSICAL);
            System.out.println("The generated item is called: \" " + weapon.getName() +
                    " \" and would cause around " + weapon.getPhysicalAttack() + " damage if you weren't cheating.");
        } catch (Exception e) {
            System.out.println("WHOA something happend in WeaponTest");
        }

        System.out.println("Cool, WeaponTest is done");
    }



}

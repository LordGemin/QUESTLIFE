package main.java.com.questlife.questlife.util;

import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.AbstractWeapons;
import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.items.WoodenStick;
import org.reflections.Reflections;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Set;

/**
 *
 * Created by Gemin on 13.05.2017.
 */
public class WeaponBindAdapter extends XmlAdapter<String, AbstractWeapons> {

    @Override
    public AbstractWeapons unmarshal(String v) throws Exception {

        if(!v.contains(";")) {
            return null;
        }

        AbstractWeapons weapon = new WoodenStick();
        String[] attr = v.split(";");

        // Set up the reflection API at root directory
        Reflections reflections = new Reflections("main.java");
        // Find all classes that extend on abstract items in any way
        Set<Class<? extends AbstractItems>> classes = reflections.getSubTypesOf(AbstractItems.class);
        // Iterate through all extending classes
        for (Object a : classes) {
            try {
                // We can only take the mostly fully quantified name of a class to create it internally
                Class rawItem = Class.forName(a.toString().replace("class ", ""));
                if(rawItem.toString().contains(attr[0])) {
                    weapon = (AbstractWeapons) rawItem.newInstance();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        weapon.setPrice(Integer.parseInt(attr[1]));
        weapon.setPhysicalAttack(Integer.parseInt(attr[2]));
        weapon.setMagicalAttack(Integer.parseInt(attr[3]));

        return weapon;
    }

    @Override
    public String marshal(AbstractWeapons v) throws Exception {
        String wrappedWeapon = "";
        wrappedWeapon += v.getName(); // attr 0
        wrappedWeapon += ";";
        wrappedWeapon += v.getPrice();  // attr 1
        wrappedWeapon += ";";
        wrappedWeapon += v.getPhysicalAttack(); // attr 2
        wrappedWeapon += ";";
        wrappedWeapon += v.getMagicalAttack();  // attr 3

        return wrappedWeapon;

    }
}

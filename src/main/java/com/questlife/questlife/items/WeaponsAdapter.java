package main.java.com.questlife.questlife.items;

import org.reflections.Reflections;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Set;

/**
 *
 * Created by Gemin on 17.05.2017.
 */
public class WeaponsAdapter extends XmlAdapter<WeaponsAdapter.AdaptedWeapons, AbstractWeapons> {

    @Override
    public AbstractWeapons unmarshal(AdaptedWeapons v) throws Exception {

        if(v == null) {
            return null;
        }

        // Set up the reflection API at root directory
        Reflections reflections = new Reflections("main.java");
        // Find all classes that extend on abstract items in any way
        Set<Class<? extends AbstractWeapons>> classes = reflections.getSubTypesOf(AbstractWeapons.class);
        // Iterate through all extending classes
        for (Object a : classes) {
            AbstractWeapons item;
            try {
                // We can only take the mostly fully quantified name of a class to create it internally
                Class rawItem = Class.forName(a.toString().replace("class ", ""));
                // We add the class to our raw items data to randomly generate different versions of the same class
                // We also add all items as some one time generated form into the itemsData list
                item = (AbstractWeapons) rawItem.newInstance();
                if(v.name.equals(item.getName())) {
                    item.setMagicalAttack(v.magicalAttack);
                    item.setPhysicalAttack(v.physicalAttack);
                    item.setPrice(v.price);
                    System.out.println("Reflection unmarshalling successful!");
                    System.out.println("Created: "+item.getName());
                    return item;
                }
            } catch (Exception e) {
                item = null;
            }
        }

        System.out.println("Reflection unmarshalling unsuccessful!");

        if (v.name.equals("Sharpened Kitchen Knife")) {
            SharpenedKitchenKnife weap = new SharpenedKitchenKnife();
            weap.setPrice(v.price);
            weap.setPhysicalAttack(v.physicalAttack);
            weap.setMagicalAttack(v.magicalAttack);
            return weap;
        }
        if (v.name.equals("Wooden Stick")) {
            WoodenStick weap = new WoodenStick();
            weap.setPrice(v.price);
            weap.setPhysicalAttack(v.physicalAttack);
            weap.setMagicalAttack(v.magicalAttack);
            return weap;

        }
        return null;
    }

    @Override
    public AdaptedWeapons marshal(AbstractWeapons v) throws Exception {
        if(v == null) {
            return null;
        }
        AdaptedWeapons adaptedWeapons = new AdaptedWeapons();
        adaptedWeapons.name = v.getName();
        adaptedWeapons.price = v.getPrice();
        adaptedWeapons.physicalAttack = v.getPhysicalAttack();
        adaptedWeapons.magicalAttack = v.getMagicalAttack();

        return adaptedWeapons;
    }

    public static class AdaptedWeapons {

        @XmlAttribute
        Integer price;

        @XmlAttribute
        int magicalAttack;

        @XmlAttribute
        int physicalAttack;

        @XmlAttribute
        String name;
    }
}

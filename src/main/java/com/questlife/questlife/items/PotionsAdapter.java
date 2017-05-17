package main.java.com.questlife.questlife.items;

import javafx.fxml.FXML;
import org.reflections.Reflections;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Set;

/**
 *
 * Created by Gemin on 17.05.2017.
 */
public class PotionsAdapter extends XmlAdapter<PotionsAdapter.AdaptedPotions, AbstractPotions> {

    @Override
    public AbstractPotions unmarshal(AdaptedPotions v) throws Exception {

        if(v == null) {
            return null;
        }

        // Set up the reflection API at root directory
        Reflections reflections = new Reflections("main.java");
        // Find all classes that extend on abstract items in any way
        Set<Class<? extends AbstractPotions>> classes = reflections.getSubTypesOf(AbstractPotions.class);
        // Iterate through all extending classes
        for (Object a : classes) {
            AbstractPotions item;
            try {
                // We can only take the mostly fully quantified name of a class to create it internally
                Class rawItem = Class.forName(a.toString().replace("class ", ""));
                // We add the class to our raw items data to randomly generate different versions of the same class
                // We also add all items as some one time generated form into the itemsData list
                item = (AbstractPotions) rawItem.newInstance();
                if(v.name.equals(item.getName())) {
                    item.setStrengthHP(v.strengthHP);
                    item.setStrengthMP(v.strengthMP);
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
/*
        if (v.identifier.equals(BarbariansShot.identifier)) {
            BarbariansShot pot = new BarbariansShot();
            pot.setPrice(v.price);
            pot.setStrengthHP(v.strengthHP);
            pot.setStrengthMP(v.strengthMP);
            return pot;
        }
        if (v.identifier.equals(Beer.identifier)) {
            Beer pot = new Beer();
            pot.setPrice(v.price);
            pot.setStrengthHP(v.strengthHP);
            pot.setStrengthMP(v.strengthMP);
            return pot;
        }
        if (v.identifier.equals(GreatElixir.identifier)) {
            GreatElixir pot = new GreatElixir();
            pot.setPrice(v.price);
            pot.setStrengthHP(v.strengthHP);
            pot.setStrengthMP(v.strengthMP);
            return pot;
        }
        if (v.identifier.equals(GreatPotion.identifier)) {
            GreatPotion pot = new GreatPotion();
            pot.setPrice(v.price);
            pot.setStrengthHP(v.strengthHP);
            pot.setStrengthMP(v.strengthMP);
            return pot;
        }
        if (v.identifier.equals(MinorPotion.identifier)) {
            MinorPotion pot = new MinorPotion();
            pot.setPrice(v.price);
            pot.setStrengthHP(v.strengthHP);
            pot.setStrengthMP(v.strengthMP);
            return pot;
        }
        if (v.identifier.equals(MinorElixir.identifier)) {
            MinorElixir pot = new MinorElixir();
            pot.setPrice(v.price);
            pot.setStrengthHP(v.strengthHP);
            pot.setStrengthMP(v.strengthMP);
            return pot;
        }
        if (v.identifier.equals(UselessPotion.identifier)) {
            UselessPotion pot = new UselessPotion();
            pot.setPrice(v.price);
            pot.setStrengthHP(v.strengthHP);
            pot.setStrengthMP(v.strengthMP);
            return pot;
        }
        if (v.identifier.equals(Wine.identifier)) {
            Wine pot = new Wine();
            pot.setPrice(v.price);
            pot.setStrengthHP(v.strengthHP);
            pot.setStrengthMP(v.strengthMP);
            return pot;
        }*/
        return null;
    }

    @Override
    public AdaptedPotions marshal(AbstractPotions v) throws Exception {
        if(v == null) {
            return null;
        }
        AdaptedPotions adaptedPotions = new AdaptedPotions();
        adaptedPotions.name = v.getName();
        adaptedPotions.price = v.getPrice();
        adaptedPotions.strengthHP = v.strengthHP;
        adaptedPotions.strengthMP = v.strengthMP;
        return adaptedPotions;
    }

    public static class AdaptedPotions {

        @XmlAttribute
        public Integer price;

        @XmlAttribute
        public Integer strengthHP;

        @XmlAttribute
        public Integer strengthMP;

        @XmlAttribute
        public String name;
    }
}

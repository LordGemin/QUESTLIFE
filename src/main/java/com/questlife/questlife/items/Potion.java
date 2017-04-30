package main.java.com.questlife.questlife.items;

import main.java.com.questlife.questlife.util.Generator;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class provides for potions and elixirs.
 * Healing values are hard coded. Should be changed in the future.
 * TODO: Add localization.
 *
 * Created by Gemin on 10.04.2017.
 */
public class Potion extends AbstractItems {

    private Integer strengthHP;
    private Integer strengthMP;

    public Potion() {
        generatePotion();
    }

    public int getStrengthHP() {
        return strengthHP;
    }

    public void setStrengthHP(int strengthHP) {
        this.strengthHP = strengthHP;
    }

    public int getStrengthMP() {
        return strengthMP;
    }

    public void setStrengthMP(int strengthMP) {
        this.strengthMP = strengthMP;
    }

    public Potion(String name, int price, String description) {
        super(name, price, description);
    }

    private AbstractPotions generatePotion(int heroLevel) {

        Reflections reflections = new Reflections("main.java");
        Set<Class<? extends AbstractPotions>> classes = reflections.getSubTypesOf(AbstractPotions.class);
        List<String> potions = new ArrayList<>();

        for (Object a : classes) {
            try {
                potions.add(a.toString().replace("class ", ""));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        AbstractPotions potion = null;
        try {
            Class b = Class.forName(potions.get(new Random().nextInt(potions.size()) - 1));
            potion = (AbstractPotions) b.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (potion != null) {
            potion.setHeroLevel(heroLevel);
        }
        return potion;
    }


        private void generatePotion() {

        Generator generator = new Generator();
        String potionName = generator.generatePotionName();
        boolean isPotion = true;

        if(potionName.endsWith("Elixir")) {
            isPotion=false;
        }

        //TODO: Make this and the generation more dynamic
        if(potionName.startsWith("Useless")) {
            this.strengthHP = (isPotion) ? 5 : 0;
            this.strengthMP = (isPotion) ? 0 : 5;
            price = 5;
        } else if (potionName.startsWith("Minor")) {
            this.strengthHP = (isPotion) ? 20 : 0;
            this.strengthMP = (isPotion) ? 0 : 20;
            price = 15;
        } else if (potionName.startsWith("Great")) {
            this.strengthHP = (isPotion) ? 100 : 0;
            this.strengthMP = (isPotion) ? 0 : 100;
            price = 50;
        }

        this.name = potionName;

        if(isPotion)
            this.description = potionName + " will heal " + strengthHP + " health points";
        else
            this.description = potionName + " will regenerate " + strengthMP + " mana points";
    }
}

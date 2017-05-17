package main.java.com.questlife.questlife.items;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public class MinorPotion extends AbstractPotions{

    static String identifier = "MIPO";

    public MinorPotion() {
        this.name = "Minor Potion";
        this.price = 100;
        this.description = "This Potion will regenerate your health by 50 points";
        this.strengthHP = 50;
        this.strengthMP = 0;
    }

    @Override
    public void setHeroLevel(int levelOfHero) {

    }
}

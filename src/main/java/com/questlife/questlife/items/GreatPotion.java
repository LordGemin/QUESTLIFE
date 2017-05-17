package main.java.com.questlife.questlife.items;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public class GreatPotion extends AbstractPotions{

    static String identifier = "GRPO";

    public GreatPotion() {
        this.name = "Great Potion";
        this.price = 200;
        this.description = "This Potion will regenerate your health by 100 points";
        this.strengthHP = 100;
        this.strengthMP = 0;
    }

    @Override
    public void setHeroLevel(int levelOfHero) {

    }
}

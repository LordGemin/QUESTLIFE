package main.java.com.questlife.questlife.items;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public class UselessPotion extends AbstractPotions {
    public UselessPotion() {
        this.name = "Useless Potion";
        this.price = 10;
        this.description = "This really doesn't do much.";
        this.strengthHP = 5;
        this.strengthMP = 5;
    }

    @Override
    public void setHeroLevel(int levelOfHero) {

    }
}

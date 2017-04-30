package main.java.com.questlife.questlife.items;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public class Beer extends AbstractPotions{
    public Beer() {
        this.name = "Beer";
        this.price = 75;
        this.description = "This traditional drink will fill some of your mana but shorten your lifespan.";
        this.strengthHP = -10;
        this.strengthMP = 20;
    }

    @Override
    public void setHeroLevel(int levelOfHero) {

    }
}

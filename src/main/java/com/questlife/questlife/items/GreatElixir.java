package main.java.com.questlife.questlife.items;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public class GreatElixir extends AbstractPotions{
    public GreatElixir() {
        this.name = "Great Elixir";
        this.price = 200;
        this.description = "This Elixir will regenerate your mana by 100 points";
        this.strengthHP = 0;
        this.strengthMP = 100;
    }

    @Override
    public void setHeroLevel(int levelOfHero) {

    }
}

package main.java.com.questlife.questlife.items;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public class MinorElixir extends AbstractPotions{
    public MinorElixir() {
        this.name = "Minor Elixir";
        this.price = 100;
        this.description = "This Elixir will regenerate your mana by 50 points";
        this.strengthHP = 0;
        this.strengthMP = 50;
    }

    @Override
    public void setHeroLevel(int levelOfHero) {

    }
}

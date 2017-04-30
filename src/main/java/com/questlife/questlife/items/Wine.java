package main.java.com.questlife.questlife.items;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public class Wine extends AbstractPotions{
    public Wine() {
        this.name = "Wine";
        this.price = 150;
        this.description = "This exquisite drink is more potent than beer but less destructive to your liver.";
        this.strengthHP = -5;
        this.strengthMP = 40;
    }

    @Override
    public void setHeroLevel(int levelOfHero) {

    }
}

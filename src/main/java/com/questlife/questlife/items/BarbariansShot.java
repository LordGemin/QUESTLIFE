package main.java.com.questlife.questlife.items;

/**
 *
 * Created by Gemin on 22.04.2017.
 */
public class BarbariansShot extends AbstractPotions{

    static String identifier = "BARB";

    public BarbariansShot() {
        this.name = "Barbarians Shot";
        this.price = 400;
        this.description = "This tropical drink from the northern spheres will reduce your ability to focus, but greatly help you stand for battle.";
        this.strengthHP = 200;
        this.strengthMP = -20;
    }

    @Override
    public void setHeroLevel(int levelOfHero) {

    }
}

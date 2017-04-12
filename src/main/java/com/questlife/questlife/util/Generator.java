package main.java.com.questlife.questlife.util;

import java.util.Random;

/**
 * Utility class to randomly generate names for items, enemies, quests.
 * TODO: Make generation more dynamical. Read Prefixes etc from files?
 * Created by Gemin on 11.04.2017.
 */
public class Generator {
    private Random randomGenerator = new Random();

    public Generator() {
    }

    public int generateNumber() {
        return randomGenerator.nextInt(512);
    }

    public String generateWeaponName() {
        String generatedName;

        String[] prefixes = {"Ex","Gun","Power","Karma","Mana","Staff of "};
        String[] middles = {"cali","sli","ham","bla","sta","fury"};
        String[] suffixes = {"bur","nger","mer","de","ff","ness"};

        generatedName = "".concat(prefixes[generateNumber()%prefixes.length]);
        generatedName = generatedName.concat(middles[generateNumber()%middles.length]);
        generatedName = generatedName.concat(suffixes[generateNumber()%suffixes.length]);

        return generatedName;
    }

    public String generateEnemyName() {
        String generatedName;

        String[] prefixes = {"Blue", "Yellow", "Power", "Shiny", "Red", "Green", "Black"};
        String[] suffixes = {"Ranger", "Slime", "Skeleton", "Balloon", "Gargoyle", "Dragon"};

        generatedName = prefixes[generateNumber() % prefixes.length];
        generatedName += " ".concat(suffixes[generateNumber() % suffixes.length]);

        return generatedName;
    }

    public String generatePotionName() {
        String generatedName;

        String[] itemStrength= {"Great","Minor","Useless"};
        String[] itemType = {"Potion","Elixir"};

        generatedName = itemStrength[generateNumber()%itemStrength.length];
        generatedName += " "+itemType[generateNumber()%itemType.length];

        return generatedName;
    }
}

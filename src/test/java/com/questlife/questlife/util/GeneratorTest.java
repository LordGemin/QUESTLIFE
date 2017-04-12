package test.java.com.questlife.questlife.util;

import main.java.com.questlife.questlife.util.Generator;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 *
 * Created by Gemin on 11.04.2017.
 */
public class GeneratorTest {

    @Test
    public void generateRandomWeaponName() {
        Generator nameGenerator = new Generator();
        boolean excaliburGenerated = false;
        boolean staffFuryGenerated = false;
        boolean wasGenerated = false;

        for(int i = 0; i<= 1000; i++) {
            String weaponName = nameGenerator.generateWeaponName();
            if(weaponName.contains("Excalibur")) {
                excaliburGenerated = true;
            }
            if(weaponName.contains("Staff of furyness")) {
                staffFuryGenerated = true;
            }
            if(excaliburGenerated&&staffFuryGenerated) {
                log("Steps in weapon generation: " + i);
                wasGenerated = true;
                i = 1000;
            }
        }
        assertTrue(wasGenerated);

    }

    @Test
    public void generateRandomEnemyName() {
        Generator nameGenerator = new Generator();
        boolean powerRangerGenerated = false;
        boolean blackDragonGenerated = false;
        boolean wasGenerated = false;

        for(int i = 0; i<= 1000; i++) {
            String enemyName = nameGenerator.generateEnemyName();
            if(enemyName.contains("Power Ranger")) {
                powerRangerGenerated = true;
            }
            if(enemyName.contains("Black Dragon")) {
                blackDragonGenerated = true;
            }
            if(powerRangerGenerated&&blackDragonGenerated) {
                log("Steps in enemy generation: " + i);
                wasGenerated = true;
                i = 1000;
            }
        }
        assertTrue(wasGenerated);

    }

    @Test
    public void generateRandomPotionName() {
        Generator nameGenerator = new Generator();
        boolean uselessPotionGenerated = false;
        boolean greatElixirGenerated = false;
        boolean wasGenerated = false;

        for(int i = 0; i<= 1000; i++) {
            String potionName = nameGenerator.generatePotionName();
            if(potionName.contains("Useless Potion")) {
                uselessPotionGenerated = true;
            }
            if(potionName.contains("Great Elixir")) {
                greatElixirGenerated = true;
            }
            if(uselessPotionGenerated&&greatElixirGenerated) {
                log("Steps in potion generation: " + i);
                wasGenerated = true;
                i = 1000;
            }
        }
        assertTrue(wasGenerated);


    }

    void log (String out) {
        System.out.println(out);
    }
}
package main.java.com.questlife.questlife.battle;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.util.AttackType;

import java.util.List;

/**
 *
 * Created by Gemin on 11.04.2017.
 */
public class Battle extends AbstractBattle {


    public Battle(Hero participatingHero) {
        super(participatingHero);
    }

    public Battle(List<Enemy> participatingEnemies) {
        super(participatingEnemies);
    }

    public Battle(Hero participatingHero, List<Enemy> participatingEnemies) {
        super(participatingHero, participatingEnemies);
    }


    @Override
    public void runBattle() {
        while(getParticipatingHero().getHealth() > 0 && getParticipatingEnemies().size() > 0) {
            runTurn();
        }
    }

    @Override
    public void runTurn() {
        Hero hero = getParticipatingHero();
        int enemyPosition = -1;

        for(int i = 0; i < getParticipatingEnemies().size(); i++) {
            if(getParticipatingEnemyAt(i).getHealth() <= 0 ) {
                //Circle to the next enemy
            } else if (enemyPosition == -1) {
                //looking for the leftmost enemy alive, skipping all afterwards.
                enemyPosition = i;
            }
        }

        // Always try to survive at least the first dealDamage, approximate total damage
        int criticalHealth = getParticipatingEnemyAt(enemyPosition).getAttackPower()*getParticipatingEnemies().size();

        //TODO: Find the amount of mana that should be used on dealDamage.
        int criticalMana = (hero.getWeapon().getAttackType() == AttackType.MAGICAL) ? hero.getAttack()/100 : 0;

        if(hero.getHealth() < criticalHealth || hero.getMana() < criticalMana ) {
            hero.takePotion();
        } else {
            hero.dealDamage(getParticipatingEnemyAt(enemyPosition));
        }
        for (Enemy enemy:getParticipatingEnemies()) {
            enemy.dealDamage(hero);
        }
        if (getParticipatingEnemyAt(enemyPosition).getHealth() <= 0) {
            getParticipatingEnemies().remove(getParticipatingEnemyAt(enemyPosition));
        }
    }

}

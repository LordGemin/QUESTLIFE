package main.java.com.questlife.questlife.hero;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.items.Potion;
import main.java.com.questlife.questlife.items.Weapon;
import main.java.com.questlife.questlife.player.Inventory;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.town.Field;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;
import main.java.com.questlife.questlife.util.StatCalculator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Hero implements Serializable {

    private String name;
    private Player player;
    private Integer health = 0;
    private Integer mana = 0;
    private Integer level = 1;
    private Integer experience = 0;
    private Integer experienceToNextLevel;
    private Integer gold = 0;
    private Weapon weapon;
    private Quest activeQuest;
    private Long lastDeath;

    private StatCalculator statCalculator = new StatCalculator();
    private List<Quest> questList = new ArrayList<>();


    public Hero() {

    }

    public Hero (Player player, String name, Weapon weapon) {
        this.player = player;
        this.name = name;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        if(health == getMaxHealth()) {
            lastDeath = null;
            return health;
        }
        if(lastDeath == null)
            return health;
        else {
            long timeSinceDeath = System.currentTimeMillis() - lastDeath;
            //refill complete health within 6 hours of last death
            //If user wants to go out adventuring before then: pay gold for tavern. It should be cheap enough
            health = (int) Math.ceil((getMaxHealth()/24)*(Math.floor(timeSinceDeath/600000)));
            if (health > getMaxHealth()) {
                health = getMaxHealth();
            }
            return health;
        }
    }

    public int getMana() {
        return mana;
    }

    public int getStrength() {
        return Attributes.STRENGTH.getLevel();
    }

    public int getDexterity() {
        return Attributes.DEXTERITY.getLevel();
    }

    public int getMind() {
        return Attributes.MIND.getLevel();
    }

    public int getCharisma() {
        return Attributes.CHARISMA.getLevel();
    }

    public int getConstitution() {
        return Attributes.CONSTITUTION.getLevel();
    }

    public int getPiety() {
        return Attributes.PIETY.getLevel();
    }

    public int getObservation() {
        return Attributes.OBSERVATION.getLevel();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setStrength(int strength) {
        Attributes.STRENGTH.setLevel(strength);
    }

    public void setDexterity(int dexterity) {
        Attributes.DEXTERITY.setLevel(dexterity);
    }

    public void setMind(int mind) {
        Attributes.MIND.setLevel(mind);
    }

    public void setCharisma(int charisma) {
        Attributes.CHARISMA.setLevel(charisma);
    }

    public void setConstitution(int constitution) {
        Attributes.CONSTITUTION.setLevel(constitution);
    }

    public void setPiety(int piety) {
        Attributes.PIETY.setLevel(piety);
    }

    public void setObservation(int observation) {
        Attributes.OBSERVATION.setLevel(observation);
    }

    private void setWeapon (Weapon weapon) {
        this.weapon = weapon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperienceToNextLevel() {
        if(experienceToNextLevel==null) {
            experienceToNextLevel = statCalculator.getExpToNextLevel(0,1);
        }
        return experienceToNextLevel;
    }

    public void setExperienceToNextLevel(int experienceToNextLevel) {
        this.experienceToNextLevel = experienceToNextLevel;
    }

    public int getMaxHealth() {
        return statCalculator.getMaxHealth(this);
    }

    public int getMaxMana() {
        return statCalculator.getMaxMana(this);
    }


    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public long getLastDeath() {
        return lastDeath;
    }

    public void setLastDeath(long lastDeath) {
        this.lastDeath = lastDeath;
    }

    public long getTimeSinceLastDeath() {
        return System.currentTimeMillis()-lastDeath;
    }

    public Quest getActiveQuest() {
        return activeQuest;
    }

    public void setActiveQuest(Quest activeQuest) {
        this.activeQuest = activeQuest;
    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public void setQuestList(List<Quest> questList) {
        this.questList = questList;
    }

    public void addQuest(Quest quest) {
        questList.add(quest);
    }

    private void completeQuest() {
        //TODO: Notify player.
        gainGold(activeQuest.getRewardGold());
        gainExperience(activeQuest.getRewardExp());
        getQuestList().remove(activeQuest);
        activeQuest.setInactive();
        activeQuest = null;
    }

    public int getDefense(){
        return statCalculator.calculateHeroesDefense(this);
    }

    public int getResistance() {
        return statCalculator.calculateHeroesResistance(this);
    }

    public int getAttack() {
        return statCalculator.calculateHeroesAttack(this);
    }

    public void changeWeapon(Weapon toEquip) {
        if (player.getInventory().getItemsInInventory().contains(toEquip))
            player.getInventory().getItemsInInventory().remove(toEquip);
        player.getInventory().addWeapon(this.weapon);
        this.setWeapon(toEquip);
    }

    private void levelUp() {
        this.level++;
        this.experienceToNextLevel = statCalculator.getExpToNextLevel(experienceToNextLevel,level);
        //TODO: Rethink this formula
    }

    public void gainExperience(int experienceGained) {
        try {
            this.experience = experienceGained + experience;
        } catch (NullPointerException e) {
            this.experience = experienceGained;
        }
        while (this.experience >= this.experienceToNextLevel) {
            levelUp();
            //TODO: Message to Player. Congrats or something
        }
    }

    public void gainGold(int goldGained) {
        try {
            this.gold = gold + goldGained;
        } catch (NullPointerException e) {
            this.gold = goldGained;
        }
    }

    public int takePotion() {
        int needHP = getMaxHealth()-health;
        int needMP = getMaxMana()-mana;
        int healthGotten = 0;
        int manaGotten = 0;
        int potionCounter = 0;

        Inventory inv = player.getInventory();
        List<Potion> potions = inv.getPotionsInInventory();

        for (Potion potion : potions) {
            String identifier = potion.getName();

            //Take all potions until full health
            if (healthGotten < needHP && identifier.endsWith("Potion")) {
                healthGotten += potion.getStrengthHP();
                takePotion(potion);
                potionCounter++;
            } else if (identifier.endsWith("Potion")) {
                healthGotten = needHP;
                continue;
            }

            //Take all elixirs until full mana
            if (manaGotten < needMP && identifier.endsWith("Elixir")) {
                manaGotten += potion.getStrengthMP();
                takePotion(potion);
                potionCounter++;
            } else if (identifier.endsWith("Elixir")) {
                manaGotten = needMP;
            }
        }

        return potionCounter;
    }

    public void takePotion (Potion potionToTake) {
        player.getInventory().getItemsInInventory().remove(potionToTake);
        
        int healthNeed = getMaxHealth() - health;
        int manaNeed = getMaxMana() - mana;
        
        if(healthNeed < potionToTake.getStrengthHP()) { //if the potion provides to much we have to power it down
            potionToTake.setStrengthHP(healthNeed);
        }
        if (manaNeed < potionToTake.getStrengthMP()) {
            potionToTake.setStrengthMP(manaNeed);
        }
        
        this.health += potionToTake.getStrengthHP();
        this.mana += potionToTake.getStrengthMP();
    }

    public void takeDamage(int damageDealt) {
        this.setHealth(this.getHealth()-damageDealt);
    }

    public void dealDamage(Enemy enemy) {
        int damageDealt = 0;

        // Physical Damage is easier to deal, but should in general deal slightly less damage
        if (weapon.getAttackType() == AttackType.PHYSICAL)
            damageDealt = this.getAttack()-enemy.getDefense();

        // Magical Damage can be more destructive, but uses mana. If Hero has not enough mana, it will deal less damage
        if (weapon.getAttackType() == AttackType.MAGICAL) {
            this.mana -= 10;
            if(mana <= 0) { //If Hero has not enough Mana to perform full attack, we refresh some mana for next round and deal substantially less damage this round
                mana = 10;
                damageDealt = Math.round((this.getAttack() - enemy.getResistance())/2);
            } else {
                damageDealt = this.getAttack() - enemy.getResistance();
            }
        }

        int criticalCheck =  new Generator().generateNumber();

        // Critical Attacks based on observation stat. Deals double damage
        if(getObservation() >= criticalCheck*5)
            damageDealt *= 2;
            //TODO: Message to player

        enemy.takeDamage(damageDealt);

        if (enemy.getHealth() <= 0) {
            try {
                if (activeQuest.countEnemyKilled(enemy) <= 0) {
                    completeQuest();
                }
            } catch (NullPointerException e) {
                /*
                TODO: There is no assigned quest anymore, or ever was.
                Should we then ignore the obvious error? What is justice even.
                */
            }
        }
    }

    public boolean spendGold(int cost) {
        cost -= statCalculator.getRebate(this, cost);
        if(gold >= cost) {
            gold -= cost;
            return true;
        }
        return false;
    }

    public void sendToField() {
        if(getHealth() > 0) {
            Field field = new Field(this);
            field.runBattles(getActiveQuest());
        } else {
            System.out.println(name+" can barely stand. he is not suitable for questing. Heal him first");
            //TODO: Message to player that hero is barely able to walk.
        }

    }

    public void sendToField(int loops) {
        if(getHealth() > 0) {
            Field field = new Field(this,loops);
            field.runBattles(getActiveQuest());
        } else {
            System.out.println(name+" can barely stand. he is not suitable for questing. Heal him first");
            //TODO: Message to player that hero is barely able to walk.
        }

    }
}

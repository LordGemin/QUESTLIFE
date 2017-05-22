package main.java.com.questlife.questlife.hero;

import main.java.com.questlife.questlife.items.*;
import main.java.com.questlife.questlife.player.Inventory;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.util.*;

import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 10.04.2017.
 */
public class Hero implements Serializable {

    private Statistics statistics;

    @XmlIDREF
    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    private String name;
    private Integer health = 0;
    private Integer mana = 0;
    private Integer level = 1;
    private Long experience = (long) 0;
    private Long experienceToNextLevel;
    private Long experienceToLastLevel = (long) 0;
    private Integer gold = 0;
    private AbstractWeapons weapon;
    private Long lastDeath;

    @XmlTransient
    private List<Quest> questList = new ArrayList<>();
    @XmlTransient
    private List<AbstractItems> inventory = new ArrayList<>();

    public Hero() {

    }

    public Hero(String name) {
        this.name = name;
    }

    public Hero (String name, AbstractWeapons weapon) {
        this.name = name;
        this.weapon = weapon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        if(health == getMaxHealth()) {
            lastDeath = null;
            return health;
        }
        if(lastDeath == null)
            return health;
        else {
            //refill complete health within 4 hours of last death
            //If user wants to go out adventuring before then: pay gold for tavern. It should be cheap enough
            health = (int) Math.ceil((getMaxHealth()/24)*(Math.floor(getTimeSinceLastDeath()/600000)));
            if (health > getMaxHealth()) {
                health = getMaxHealth();
            }
            return health;
        }
    }

    public int getMana() {
        return mana;
    }

    @XmlJavaTypeAdapter(AttributesAdapter.class)
    public Attributes getStrength() {
        return Attributes.STRENGTH;
    }

    @XmlJavaTypeAdapter(AttributesAdapter.class)
    public Attributes getDexterity() {
        return Attributes.DEXTERITY;
    }

    @XmlJavaTypeAdapter(AttributesAdapter.class)
    public Attributes getMind() {
        return Attributes.MIND;
    }

    @XmlJavaTypeAdapter(AttributesAdapter.class)
    public Attributes getCharisma() {
        return Attributes.CHARISMA;
    }

    @XmlJavaTypeAdapter(AttributesAdapter.class)
    public Attributes getConstitution() {
        return Attributes.CONSTITUTION;
    }

    @XmlJavaTypeAdapter(AttributesAdapter.class)
    public Attributes getPiety() {
        return Attributes.PIETY;
    }

    @XmlJavaTypeAdapter(AttributesAdapter.class)
    public Attributes getObservation() {
        return Attributes.OBSERVATION;
    }

    @XmlJavaTypeAdapter(WeaponBindAdapter.class)
    public AbstractWeapons getWeapon() {
        return weapon;
    }

    public void setWeapon (AbstractWeapons weapon) {
        this.weapon = weapon;
    }

    @XmlTransient
    public List<AbstractItems> getInventory() {
        return inventory;
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

    public void setInventory(List<AbstractItems> inventory) {
        this.inventory = inventory;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public long getExperienceToNextLevel() {
        if(experienceToNextLevel==null) {
            experienceToNextLevel = StatCalculator.getExpToNextLevel(1);
        }
        return experienceToNextLevel;
    }

    public long getExperienceToLastLevel() {
        return experienceToLastLevel;
    }

    public void setExperienceToNextLevel(long experienceToNextLevel) {
        this.experienceToNextLevel = experienceToNextLevel;
    }

    public int getMaxHealth() {
        return StatCalculator.getMaxHealth(this);
    }

    public int getMaxMana() {
        return StatCalculator.getMaxMana(this);
    }


    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public long getLastDeath() {
        if (lastDeath == null) {
            return 0;
        }
        return lastDeath;
    }

    public void setLastDeath(long lastDeath) {
        this.lastDeath = lastDeath;
    }

    public long getTimeSinceLastDeath() {
        return System.currentTimeMillis()-lastDeath;
    }
/*
    public Quest getActiveQuest() {
        if(activeQuest == null && questList.size() > 0) {
            activeQuest = questList.get(0);
            questList.get(0).setAsActive();
        }

        return activeQuest;
    }

    public boolean setActiveQuest(Quest activeQuest) {
        Quest oldQuest = this.activeQuest;
        if (this.activeQuest != null) {
            this.activeQuest.setInactive();
        }

        if(activeQuest == null) {
            for(Quest q:questList) {
                if (!q.equals(oldQuest)) {
                    q.setAsActive();
                    this.activeQuest = q;
                }
            }
            return true;
        }

        if(questList.contains(activeQuest)) {
            activeQuest.setAsActive();
            this.activeQuest = activeQuest;
            return true;
        }
        return false;
    }*/

    @XmlTransient
    public List<Quest> getQuestList() {
        return questList;
    }

    public void setQuestList(List<Quest> questList) {
        this.questList = questList;
    }

    public void addQuest(Quest quest) {
        questList.add(quest);
    }

    public void completeQuest(Quest quest) {
        //TODO: Notify player.
        gainGold(quest.getRewardGold());
        gainExperience(quest.getRewardExp());
        questList.remove(quest);
        quest.setInactive();
        statistics.countQuest();
    }

    public int getDefense(){
        return StatCalculator.calculateHeroesDefense(this);
    }

    public int getResistance() {
        return StatCalculator.calculateHeroesResistance(this);
    }

    public int getAttack() {
        return StatCalculator.calculateHeroesAttack(this);
    }

    public AbstractWeapons changeWeapon(AbstractWeapons toEquip) {
        AbstractWeapons oldWeapon = null;
        if (inventory.contains(toEquip))
            inventory.remove(toEquip);
        else
            return null;
        if (this.weapon != null) {
            oldWeapon = this.weapon;
            inventory.add(oldWeapon);
        }
        this.weapon = toEquip;

        return oldWeapon;
    }

    private void levelUp() {
        this.level++;
        this.experienceToLastLevel = experienceToNextLevel;
        this.experienceToNextLevel = StatCalculator.getExpToNextLevel(level);
    }

    public void gainExperience(int experienceGained) {
        experience += experienceGained;
        while (this.experience >= this.experienceToNextLevel) {
            levelUp();
        }
        statistics.countExperience(experienceGained);
    }

    public void gainGold(int goldGained) {
        try {
            this.gold += goldGained;
        } catch (NullPointerException e) {
            this.gold = goldGained;
        }
        statistics.countGold(goldGained);
    }

    public int takePotion() {
        int needHP = getMaxHealth()-health;
        int needMP = getMaxMana()-mana;
        int healthGotten = 0;
        int manaGotten = 0;
        int potionCounter = 0;

        Inventory inv = new Inventory();
        inv.setItemsInInventory(inventory);
        List<AbstractPotions> potions = inv.getPotionsInInventory();

        for (AbstractPotions potion : potions) {
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

    public void takePotion (AbstractPotions potionToTake) {
        inventory.remove(potionToTake);

        health += potionToTake.getStrengthHP();
        mana += potionToTake.getStrengthMP();

        health = (health < getMaxHealth()) ? health:getMaxHealth();
        mana = (mana < getMaxMana()) ? mana:getMaxMana();
    }

    public void takeDamage(int damageDealt, AttackType attackType) {
        int damageTaken = 0;
        if (attackType == AttackType.PHYSICAL)
            damageTaken = damageDealt-getDefense();
        if (attackType == AttackType.MAGICAL)
            damageTaken = damageDealt-getResistance();

        if(damageTaken <= 0) {
            damageTaken =1;
        }

        this.setHealth(this.getHealth()-damageTaken);
    }

    public int dealDamage() {
        int damageDealt = getAttack();
        AttackType weaponAttackType = null;

        // Temporary weapon for this attack, gets deleted later
        boolean deleteWeapon = false;
        if(weapon == null) {
            weapon = new Weapon("Bare knuckle", 4, 4,AttackType.BOTH);
            deleteWeapon = true;
        }

        if(weapon.getAttackType() != null) {
            weaponAttackType = weapon.getAttackType();
        }

        if (weapon.getAttackType() == AttackType.PHYSICAL) {
            damageDealt = getAttack();
        }

        if (weapon.getAttackType() == AttackType.BOTH) {
            if(mana <= 0) { //If Hero has not enough Mana to perform full attack, he attack with the weapon physically
                damageDealt = getAttack();
                mana = 10;
            } else {
                damageDealt = getAttack();
            }
        }

        // Magical Damage can be more destructive, but uses mana. If Hero has not enough mana, it will deal less damage
        if (weapon.getAttackType() == AttackType.MAGICAL) {
            if(mana < 10) { //If Hero has not enough Mana to perform full attack, we refresh some mana for next round and deal substantially less damage this round
                mana = 10;
                damageDealt = Math.round(damageDealt/2.0f);
            } else {
                this.mana -= 10;
                damageDealt = getAttack();
            }
        }

        if(deleteWeapon) {
            weapon = null;
        }

        return damageDealt;
    }

    public boolean spendGold(int cost) {
        cost -= StatCalculator.getRebate(this, cost);
        if(gold >= cost) {
            gold -= cost;
            return true;
        }
        return false;
    }

    /**
     * When hero buys rewards, he gets no rebate.
     *
     * @param cost Cost of the reward that is to be bought
     * @return true if hero had enough gold
     */
    public boolean spendGoldForReward(int cost) {
        if(gold >= cost) {
            gold -= cost;
            return true;
        }
        return false;
    }

}

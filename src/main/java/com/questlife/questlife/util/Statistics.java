package main.java.com.questlife.questlife.util;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 *
 * Created by Gemin on 21.05.2017.
 */
public class Statistics {

    @XmlID
    private String id;

    private int enemiesDefeated;
    private int goalsCompleted;
    private int questsSucceeded;
    private List<AbstractMap.SimpleEntry<String, Integer>> experienceMap = new ArrayList<>();
    private List<AbstractMap.SimpleEntry<String, Integer>> goldMap = new ArrayList<>();

    public Statistics() {
        this.id = UUID.randomUUID().toString();
    }


    public int getEnemiesDefeated() {
        return enemiesDefeated;
    }

    public void setEnemiesDefeated(int enemiesDefeated) {
        this.enemiesDefeated = enemiesDefeated;
    }

    public int getGoalsCompleted() {
        return goalsCompleted;
    }

    public void setGoalsCompleted(int goalsCompleted) {
        this.goalsCompleted = goalsCompleted;
    }

    public int getQuestsSucceeded() {
        return questsSucceeded;
    }

    public void setQuestsSucceeded(int questsSucceeded) {
        this.questsSucceeded = questsSucceeded;
    }

    @XmlJavaTypeAdapter(MapListAdapter.class)
    public List<AbstractMap.SimpleEntry<String, Integer>> getExperienceMap() {
        return experienceMap;
    }

    public void setExperienceMap(List<AbstractMap.SimpleEntry<String, Integer>> experienceMap) {
        this.experienceMap = experienceMap;
    }

    @XmlJavaTypeAdapter(MapListAdapter.class)
    public List<AbstractMap.SimpleEntry<String, Integer>> getGoldMap() {
        return goldMap;
    }

    public void setGoldMap(List<AbstractMap.SimpleEntry<String, Integer>> goldMap) {
        this.goldMap = goldMap;
    }


    public void countEnemy() {
        enemiesDefeated++;
    }

    public void countGoal() {
        goalsCompleted++;
    }

    public void countQuest() {
        questsSucceeded++;
    }

    public void countExperience(int expGained) {
        long time = System.currentTimeMillis();
        Date comp = new Date();
        comp.setTime(time);
        LocalDateTime ldt = LocalDateTime.ofInstant(comp.toInstant(), ZoneId.systemDefault());
        String newDate = ldt.getYear()+ "-" + ldt.getMonthValue()+ "-" + ldt.getDayOfMonth() + "-" + ldt.getHour();

        for(AbstractMap.SimpleEntry<String, Integer> b:experienceMap) {
            if(b.getKey().equals(newDate)) {
                b.setValue(b.getValue()+expGained);
                return;
            }
        }

        AbstractMap.SimpleEntry<String, Integer> a = new AbstractMap.SimpleEntry<>(newDate, expGained);
        experienceMap.add(a);
    }

    public void countGold(int goldGained) {
        long time = System.currentTimeMillis();
        Date comp = new Date();
        comp.setTime(time);
        LocalDateTime ldt = LocalDateTime.ofInstant(comp.toInstant(), ZoneId.systemDefault());
        String newDate = ldt.getYear()+ "-" + ldt.getMonthValue()+ "-" + ldt.getDayOfMonth() + "-" + ldt.getHour();

        for(AbstractMap.SimpleEntry<String, Integer> b:goldMap) {
            if(b.getKey().equals(newDate)) {
                b.setValue(b.getValue()+goldGained);
                return;
            }
        }

        AbstractMap.SimpleEntry<String, Integer> a = new AbstractMap.SimpleEntry<>(newDate, goldGained);
        goldMap.add(a);
    }
}

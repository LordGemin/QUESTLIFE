package main.java.com.questlife.questlife.town;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.util.Generator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gemin on 03.05.2017.
 */
public class Marketboard {

    private List<Quest> questList = new ArrayList<>();

    public Marketboard() {

    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public void setQuestList(List<Quest> questList) {
        this.questList = questList;
    }

    public Quest generateQuest(List<String> enemyList, int level) {
        if(enemyList.size() == 0) {
            return null;
        }
        Quest generatedQuest = new Quest();
        Generator generator = new Generator();
        int pick = generator.generateNumber()%enemyList.size();

        Enemy enemy = new Enemy(enemyList.get(pick), level+1);

        // Pick an enemy from the list, set it as enemy
        generatedQuest.setQuestEnemy(enemy.getName());

        // Set mobs to hunt between 5 and 10
        generatedQuest.setMobsToHunt(5 + generator.generateNumber()%6);

        // We want to "Defeat 5 Blue SlimeS"
        generatedQuest.setName("Defeat "+generatedQuest.getMobsToHunt()+" "+enemy.getName()+"s");

        // The reward should be in accordance with the expected difficulty
        generatedQuest.setRewardExp(Math.round((enemy.getExperieceReward())*(generatedQuest.getMobsToHunt())*2.5f));

        // We want to receive lower gold rewards than experience rewards, because progress feels nice, but gold should feel rewarding
        generatedQuest.setRewardGold(Math.round((enemy.getGoldReward())*(generatedQuest.getMobsToHunt())*1.5f));

        // The description will be a bit silly in some cases. But it's never shown unless the player selects detailed view
        generatedQuest.setDescription(generator.generateQuestDescription(enemy));

        questList.add(generatedQuest);

        return generatedQuest;
    }

    public Quest acceptQuest(Quest quest) {
        if(questList.contains(quest)) {
            questList.remove(quest);
            return quest; // <-- we return the quest we where given? Yes! Because we need to confirm that it is available and can be used!
        }
        return null;
    }
}

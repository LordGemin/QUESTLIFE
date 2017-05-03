package main.java.com.questlife.questlife.town;

import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.util.Generator;

import java.util.List;

/**
 *
 * Created by Gemin on 03.05.2017.
 */
public class Marketboard {

    private List<Quest> questList;

    public Marketboard() {

    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public void setQuestList(List<Quest> questList) {
        this.questList = questList;
    }

    public Quest generateQuest(List<Enemy> enemyList) {
        if(enemyList.size() == 0) {
            return null;
        }
        Quest generatedQuest = new Quest();
        Generator generator = new Generator();
        int pick = generator.generateNumber()%enemyList.size();

        // Pick an enemy from the list, set it as enemytype
        generatedQuest.setEnemyType(enemyList.get(pick));

        // Set mobs to hunt between 1 and 10
        generatedQuest.setMobsToHunt(1 + generator.generateNumber()%10);

        // We want to "Defeat 1 blue slime" but "3 blue slimeS"
        String enemyName = (generatedQuest.getMobsToHunt()== 1) ? generatedQuest.getEnemyType().getName():generatedQuest.getEnemyType().getName()+"s";
        generatedQuest.setName("Defeat "+generatedQuest.getMobsToHunt()+" "+enemyName);

        // The reward should be in accordance with the expected difficulty
        generatedQuest.setRewardExp(Math.round((generatedQuest.getEnemyType().getExperieceReward())*(generatedQuest.getMobsToHunt())/3.5f));

        // We want to receive lower gold rewards than experience rewards, because progress feels nice, but gold should feel rewarding
        generatedQuest.setRewardGold(Math.round((generatedQuest.getEnemyType().getGoldReward())*(generatedQuest.getMobsToHunt())/4.5f));

        // The description will be a bit silly in some cases. But it's never shown unless the player selects detailed view
        generatedQuest.setDescription(generator.generateQuestDescription(generatedQuest.getEnemyType()));

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

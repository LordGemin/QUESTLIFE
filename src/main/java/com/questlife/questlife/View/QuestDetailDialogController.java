package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.quests.Quest;


/**
 *
 * Created by Gemin on 29.04.2017.
 */
public class QuestDetailDialogController {


    private Stage dialogStage;

    @FXML
    private Label questName;

    @FXML
    private Text questDescription;

    @FXML
    private Label questEnemyCount;

    @FXML
    private Label enemyName;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }


    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    /**
     * Sets the quest to be edited in the dialog.
     *
     * @param quest
     */
    public void setQuest(Quest quest) {
        questName.setText(quest.getName());
        questDescription.setText(quest.getDescription());
        enemyName.setText(quest.getQuestEnemy());
        questEnemyCount.setText(""+quest.getMobsToHunt());
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}

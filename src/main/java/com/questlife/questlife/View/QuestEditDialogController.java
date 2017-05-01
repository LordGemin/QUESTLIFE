package main.java.com.questlife.questlife.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.quests.Quest;


/**
 *
 * Created by Gemin on 29.04.2017.
 */
public class QuestEditDialogController {


    private Stage dialogStage;
    private Quest quest;
    private boolean okClicked = false;

    @FXML
    private TextField questName;

    @FXML
    private TextArea questDescription;

    @FXML
    private TextField questEnemyCount;

    @FXML
    private TableView<Enemy> enemyTable;

    @FXML
    private TableColumn<Enemy, String> enemyName;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        enemyName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
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
     * Sets the goal to be edited in the dialog.
     *
     * @param quest
     */
    public void setQuest(Quest quest, MainApp mainApp) {
        this.quest = quest;

        enemyTable.setItems(mainApp.getEnemyData());
        questName.setText(quest.getName());
        questDescription.setText(quest.getDescription());
        questEnemyCount.setText(""+quest.getMobsToHunt());
        enemyTable.getSelectionModel().select(quest.getEnemyType());
    }


    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }


    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            quest.setName(questName.getText());
            quest.setDescription(questDescription.getText());
            quest.setMobsToHunt(Integer.parseInt(questEnemyCount.getText()));
            quest.setEnemyType(enemyTable.getSelectionModel().getSelectedItem());

            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (questName.getText() == null || questName.getText().length() == 0) {
            errorMessage += "No valid goal name!\n";
        }

        if (questDescription.getText() == null || questName.getText().length() == 0) {
            errorMessage += "Enter some description.";
        }

        if (questEnemyCount.getText() == null || questEnemyCount.getText().length() == 0) {
            errorMessage += "Non valid cost!\n";
        } else {
            // try to parse the cost into an int.
            try {
                Integer.parseInt(questEnemyCount.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid cost (must be a whole number)!\n";
            }
        }

        if (enemyTable.getSelectionModel().getSelectedItems().size() == 0 || enemyTable.getSelectionModel().getSelectedItems().size() > 1) {
            errorMessage += "Select exactly one enemy type for this quest";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}

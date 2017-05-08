package main.java.com.questlife.questlife.View;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.town.Marketboard;

/**
 *
 * Created by Gemin on 03.05.2017.
 */
public class MarketboardViewController {

    private Stage dialogStage;
    private MainApp mainApp;
    private Marketboard marketboard = new Marketboard();
    ObservableList<Quest> questObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Quest> questTable;

    @FXML
    private TableColumn<Quest, String> enemyName;
    @FXML
    private TableColumn<Quest, Integer> enemyAmount;
    @FXML
    private TableColumn<Quest, Integer> questReward;

    public void initialize() {
        enemyName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnemyType().getName()));
        enemyAmount.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMobsToHunt()));
        questReward.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRewardGold()));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;


        if(marketboard.getQuestList() == null) {
            questObservableList.add(marketboard.generateQuest(mainApp.getEnemyData(), mainApp.getHeroData().get(0).getLevel()));
        }
        while(marketboard.getQuestList().size() < 10) {
            questObservableList.add(marketboard.generateQuest(mainApp.getEnemyData(), mainApp.getHeroData().get(0).getLevel()));
        }

        questTable.setItems(questObservableList);
    }

    @FXML
    private void handleLeave() {
        dialogStage.close();
    }

    @FXML
    private void handleAccept() {
        Quest quest;
        // We shall allow the player to accept 3 quests at level 1. Should he increase in level, he can take on more quests
        if(mainApp.getHeroData().get(0).getQuestList().size() > mainApp.getHeroData().get(0).getLevel()+2) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Quest can't be accepted");
            alert.setHeaderText("Too many quests");
            alert.setContentText("The townsfolk won't trust someone who is all talks!\nProve your worth and complete some quests!");
            alert.show();
            return;
        }
        if(isOneSelected()) {
            quest = questTable.getSelectionModel().getSelectedItem();
            // Accept the quest and check if successful by removing from the observable list
            if(!questObservableList.remove(marketboard.acceptQuest(quest))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Quest error");
                alert.setHeaderText("Quest was not found");
                alert.setContentText("There was an internal error accepting the quest!\nPlease close the Board and try again!");
                alert.show();
                return;
            }
            mainApp.getHeroData().get(0).getQuestList().add(quest);
            mainApp.getQuestData().add(quest);
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quest Accepted");
        alert.setHeaderText("Quest successfully accepted");
        alert.setContentText("The townsfolk are greatful for your help!\nThey await your heroic deeds.");
        alert.show();
    }

    @FXML
    private void handleDetails() {
        Quest quest;
        if(isOneSelected()) {
            quest = questTable.getSelectionModel().getSelectedItem();
            mainApp.showQuestDetailDialog(quest);
        }
    }

    private boolean isOneSelected() {
        if(questTable.getSelectionModel().getSelectedItems().size() > 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Quest selection error");
            alert.setHeaderText("Only one quest may be selected");
            alert.setContentText("Please select only one quest!\n");
            alert.show();
            return false;
        }
        if(questTable.getSelectionModel().getSelectedItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Quest selection error");
            alert.setHeaderText("One quest must be selected");
            alert.setContentText("Please select a quest!\n");
            alert.show();
            return false;
        }

        return true;
    }

}

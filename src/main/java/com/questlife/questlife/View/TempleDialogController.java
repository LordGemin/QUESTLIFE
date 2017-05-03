package main.java.com.questlife.questlife.View;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.town.Temple;
import main.java.com.questlife.questlife.util.RewardType;

import java.util.NoSuchElementException;


/**
 *
 * Created by Gemin on 03.05.2017.
 */
public class TempleDialogController {

    private Stage dialogStage;
    private MainApp mainApp;
    private Temple temple;

    @FXML
    private TableView<Reward> rewardTableView;
    @FXML
    private TableColumn<Reward, String> rewardName;
    @FXML
    private TableColumn<Reward, String> rewardType;
    @FXML
    private TableColumn<Reward, String> rewardAssociatedSkill;
    @FXML
    private TableColumn<Reward, Integer> rewardCost;


    public void initialize() {
        rewardName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        rewardType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRewardType().toString()));
        rewardAssociatedSkill.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssociatedSkill().getName()));
        rewardCost.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCost()));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.temple = mainApp.getTemple();

        rewardTableView.setItems(mainApp.getRewardData());
    }


    /**
     * Called when user wants to acquire some reward.
     * Checks for the selected reward, shouts at user, then tries to purchase the reward.
     * Throws alerts if Hero does not fulfill requirements.
     */
    @FXML
    private void handleAcquisition() {
        if(rewardTableView.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Could not get reward");
            alert.setHeaderText("Problem");
            alert.setContentText("Please select one reward.");
            alert.show();
            // Shouldn't run the rest of the code if user didn't select ONE item
            return;
        }
        Reward selection = rewardTableView.getSelectionModel().getSelectedItem();

        try {
            Reward removed = temple.acquireReward(mainApp.getHeroData().get(0), selection);
            if(removed == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Could not get reward");
                alert.setHeaderText("Problem");
                alert.setContentText((selection.getRewardType().equals(RewardType.GOLD_BASED)) ? "Hero does not have enough gold" : "Hero didn't train enough to receive blessing");
                alert.show();
            }
            mainApp.getRewardData().remove(removed);
        } catch (NoSuchFieldException | NoSuchElementException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Could not get reward");
            alert.setHeaderText("Problem");
            alert.setContentText(e.getMessage());
            alert.show();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEdit() {
        if(rewardTableView.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Could not get reward");
            alert.setHeaderText("Problem");
            alert.setContentText("Please select one reward.");
            alert.show();
            // Shouldn't run the rest of the code if user didn't select ONE item
            return;
        }
        mainApp.showRewardEditDialog(rewardTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}

package main.java.com.questlife.questlife.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.util.RewardType;

import static java.lang.Integer.parseInt;

/**
 *
 * Created by Gemin on 29.04.2017.
 */
public class RewardEditDialogController {

    @FXML
    private TextField rewardName;
    @FXML
    private TextArea rewardDescription;
    @FXML
    private TextField rewardCost;
    @FXML
    private TextField rewardRisingCost;
    @FXML
    private TextField rewardAmount;

    @FXML
    private ToggleGroup rewardType = new ToggleGroup();
    @FXML
    private RadioButton skillBased;
    @FXML
    private RadioButton goldBased;

    @FXML
    private TableView<Skill> rewardAssociatedSkillTable;
    @FXML
    private TableColumn<Skill, String> rewardAssociatedSkillName;


    private Reward reward;

    private Stage dialogStage;
    private boolean okClicked = false;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        rewardAssociatedSkillName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
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
     * Sets the skill to be edited in the dialog.
     *
     * @param reward
     */
    public void setReward(Reward reward, MainApp mainApp) {
        this.reward = reward;

        rewardAssociatedSkillTable.setItems(mainApp.getSkillData());

        skillBased.setToggleGroup(rewardType);
        skillBased.setUserData(RewardType.SKILL_LEVEL_BASED.getFieldDescription());
        goldBased.setToggleGroup(rewardType);
        goldBased.setUserData(RewardType.GOLD_BASED.getFieldDescription());


        if (reward.getRewardType() != null) {
            switch (reward.getRewardType()) {
                case SKILL_LEVEL_BASED:
                    rewardType.selectToggle(skillBased);
                    rewardAmount.setText(""+1);
                    rewardAmount.setDisable(true);
                    break;
                case GOLD_BASED:
                    rewardType.selectToggle(goldBased);
                    break;
            }
        }
        rewardName.setText(reward.getName());
        rewardDescription.setText(reward.getDescription());
        rewardCost.setText(""+reward.getCost());
        rewardRisingCost.setText(""+reward.getRisingCost());
        rewardAmount.setText(""+reward.getCanReceive());

        rewardAssociatedSkillTable.getSelectionModel().select(reward.getAssociatedSkill());
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
     * Called when the user toggles a radio button.
     * For skill based rewards, there are no multiple rewards and therefore no rising costs
     */
    @FXML
    private void handleToggleSkillBased() {
        if (rewardType.getSelectedToggle() == skillBased) {
            rewardAmount.setDisable(true);
            rewardRisingCost.setDisable(true);
        } else {
            rewardAmount.setDisable(false);
            rewardRisingCost.setDisable(false);
        }
    }


    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            reward.setName(rewardName.getText());
            reward.setDescription(rewardDescription.getText());
            reward.setCost(parseInt(rewardCost.getText()));
            if(rewardType.getSelectedToggle() == skillBased) {
                reward.setCanReceive(1);
                reward.setRisingCost(0);
            } else {
                reward.setCanReceive(parseInt(rewardAmount.getText()));
                reward.setRisingCost(parseInt(rewardRisingCost.getText()));
            }

            reward.setRewardType(RewardType.getField(rewardType.getSelectedToggle().getUserData().toString()));

            // We ignore the user input for associated skill if he wants gold based rewards!
            if (reward.getRewardType() == RewardType.SKILL_LEVEL_BASED) {
                reward.setAssociatedSkill(rewardAssociatedSkillTable.getSelectionModel().getSelectedItem());
            } else {
                reward.setAssociatedSkill(null);
            }

            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (rewardName.getText() == null || rewardName.getText().length() == 0) {
            errorMessage += "No valid goal name!\n";
        }

        if (rewardDescription.getText() == null || rewardDescription.getText().length() == 0) {
            errorMessage += "Enter some description.\n";
        }

        /*
         * If no reward type was selected, remind user!
         */
        if (rewardType.getSelectedToggle() == null) {
            errorMessage += "Must select the reward type!\n";
        }

        if (rewardCost.getText() == null || rewardCost.getText().length() == 0) {
            errorMessage += "No valid cost!\n";
        } else {
            // try to parse the cost into an int.
            try {
                Integer.parseInt(rewardCost.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Cost must be a whole number!\n";
            }
        }

        if (rewardRisingCost.getText() == null || rewardRisingCost.getText().length() == 0) {
            rewardRisingCost.setText("0");
        }
            // try to parse the cost into an int.
        try {
            Integer.parseInt(rewardRisingCost.getText());
        } catch (NumberFormatException e) {
            errorMessage += "Rising cost must be a whole number!\n";
        }


        if (rewardAmount.getText() == null || rewardAmount.getText().length() == 0) {
            errorMessage += "No valid amount!\n";
        } else {
            // try to parse the cost into an int.
            try {
                Integer.parseInt(rewardAmount.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Amount must be a whole number!\n";
            }
        }

        if(rewardType.getSelectedToggle() == skillBased && Integer.parseInt(rewardAmount.getText()) > 1) {
            errorMessage += "You can only receive skill based rewards once";
        }

        /*
         * For reward type = skill based the user has to select one skill
         */
        if (rewardType.getSelectedToggle() == skillBased && rewardAssociatedSkillTable.getSelectionModel().getSelectedItems() == null) {
            errorMessage += "You have to specify one associated skill!\n";
        }

        /*
         * For reward type = skill based the user has to select only one skill
         */
        if (rewardType.getSelectedToggle() == skillBased && rewardAssociatedSkillTable.getSelectionModel().getSelectedItems().size() > 1) {
            errorMessage += "You can specify only one associated skill!\n";
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

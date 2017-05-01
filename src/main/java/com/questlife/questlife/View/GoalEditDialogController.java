package main.java.com.questlife.questlife.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.goals.Goals;
import main.java.com.questlife.questlife.skills.Skill;

/**
 *
 * Created by Gemin on 28.04.2017.
 */
public class GoalEditDialogController {

    @FXML
    private TextField goalName;
    @FXML
    private Slider amountOfWordSlider;
    @FXML
    private Slider complexitySlider;
    @FXML
    private DatePicker deadlinePicker;
    @FXML
    private Label experienceLabel;
    @FXML
    private TableView<Skill> associatedSkill;
    @FXML
    private TableColumn<Skill, String> skillName;
    @FXML
    private TableView<Goals> overarchingGoalTable;
    @FXML
    private TableColumn<Goals, String> overarchingGoalName;

    private Stage dialogStage;
    private Goals goal;
    private boolean okClicked = false;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        overarchingGoalName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOverarchingGoal().getName()));
        skillName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
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
     * @param goal
     */
    public void setGoal(Goals goal, MainApp mainApp) {
        this.goal = goal;

        overarchingGoalTable.setItems(mainApp.getGoalData());
        associatedSkill.setItems(mainApp.getSkillData());
        goalName.setText(goal.getName());
        amountOfWordSlider.setValue(goal.getAmountOfWork());
        complexitySlider.setValue(goal.getComplexity());
        for(Skill s:goal.getAssociatedSkills()) {
            associatedSkill.getSelectionModel().select(s);
        }
        overarchingGoalTable.getSelectionModel().select(goal.getOverarchingGoal());
        deadlinePicker.setPromptText("dd.mm.yyyy");
        experienceLabel.setText(""+goal.getExperienceReward());
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
            goal.setName(goalName.getText());
            goal.setAmountOfWork((int)amountOfWordSlider.getValue());
            goal.setComplexity((int)complexitySlider.getValue());
            goal.setDeadline(deadlinePicker.getValue().atTime(12,0));
            goal.setAssociatedSkills(associatedSkill.getSelectionModel().getSelectedItems());
            goal.setOverarchingGoal(overarchingGoalTable.getSelectionModel().getSelectedItem());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleSliderDrag() {
        goal.setAmountOfWork((int)amountOfWordSlider.getValue());
        goal.setComplexity((int)complexitySlider.getValue());
        experienceLabel.setText(""+goal.getExperienceReward());
    }

    /**
     * Checks if the entered input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (goalName.getText() == null || goalName.getText().length() == 0) {
            errorMessage += "No valid goal name!\n";
        }
        if (deadlinePicker.getValue() == null) {
            errorMessage += "Set a deadline!\n";
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

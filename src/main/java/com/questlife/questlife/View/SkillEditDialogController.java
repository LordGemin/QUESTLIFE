package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.util.SkillType;

import java.util.Objects;


/**
 *
 * Created by Gemin on 29.04.2017.
 */
public class SkillEditDialogController {
    @FXML
    private TextField skillName;
    @FXML
    private TextArea skillDescription;
    @FXML
    private ToggleGroup attributes = new ToggleGroup();
    @FXML
    private RadioButton rb_strength;
    @FXML
    private RadioButton rb_dexterity;
    @FXML
    private RadioButton rb_mind;
    @FXML
    private RadioButton rb_charisma;
    @FXML
    private RadioButton rb_observation;
    @FXML
    private RadioButton rb_constitution;
    @FXML
    private ToggleGroup skillType = new ToggleGroup();
    @FXML
    private RadioButton rb_timeBased;
    @FXML
    private RadioButton rb_goalBased;
    @FXML
    private Button addTime;



    private Skill skill;

    private Stage dialogStage;
    private boolean okClicked = false;
    private MainApp mainApp;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        addTime.setVisible(false);
        addTime.setDisable(true);
    }


    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Sets the skill to be edited in the dialog.
     *
     * @param skill
     */
    public void setSkill(Skill skill) {
        this.skill = skill;

        rb_strength.setToggleGroup(attributes);
        rb_strength.setUserData(Attributes.STRENGTH.getFieldDescription());
        rb_dexterity.setToggleGroup(attributes);
        rb_dexterity.setUserData(Attributes.DEXTERITY.getFieldDescription());
        rb_mind.setToggleGroup(attributes);
        rb_mind.setUserData(Attributes.MIND.getFieldDescription());
        rb_charisma.setToggleGroup(attributes);
        rb_charisma.setUserData(Attributes.CHARISMA.getFieldDescription());
        rb_observation.setToggleGroup(attributes);
        rb_observation.setUserData(Attributes.OBSERVATION.getFieldDescription());
        rb_constitution.setToggleGroup(attributes);
        rb_constitution.setUserData(Attributes.CONSTITUTION.getFieldDescription());

        rb_timeBased.setToggleGroup(skillType);
        rb_timeBased.setUserData(SkillType.TIMEBASED.getFieldDescription());
        rb_goalBased.setToggleGroup(skillType);
        rb_goalBased.setUserData(SkillType.GOALBASED.getFieldDescription());

        if(skill.getAssociatedAttribute() != null) {
            switch (skill.getAssociatedAttribute()) {
                case STRENGTH:
                    attributes.selectToggle(rb_strength);
                    break;
                case DEXTERITY:
                    attributes.selectToggle(rb_dexterity);
                    break;
                case MIND:
                    attributes.selectToggle(rb_mind);
                    break;
                case CHARISMA:
                    attributes.selectToggle(rb_charisma);
                    break;
                case OBSERVATION:
                    attributes.selectToggle(rb_observation);
                    break;
                case CONSTITUTION:
                    attributes.selectToggle(rb_constitution);
                    break;
                default:
                    break;
            }
        }

        if (skill.getSkilltype() != null) {
            switch (skill.getSkilltype()) {
                case GOALBASED:
                    skillType.selectToggle(rb_goalBased);
                    addTime.setVisible(false);
                    addTime.setDisable(true);
                    break;
                case TIMEBASED:
                    skillType.selectToggle(rb_timeBased);
                    addTime.setVisible(true);
                    addTime.setDisable(false);
                    break;
            }
        }
        skillName.setText(skill.getName());
        skillDescription.setText(skill.getDescription());

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
            skill.setName(skillName.getText());
            skill.setDescription(skillDescription.getText());
            skill.setAssociatedAttribute(Attributes.getField(attributes.getSelectedToggle().getUserData().toString()));
            skill.setSkilltype(SkillType.getField(skillType.getSelectedToggle().getUserData().toString()));

            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (skillName.getText() == null || skillName.getText().length() == 0) {
            errorMessage += "No valid goal name!\n";
        }

        if (skillDescription.getText() == null || skillDescription.getText().length() == 0) {
            errorMessage += "Enter some description.\n";
        }
        if (skillDescription.getText().equals(skill.getDescription())) {
            errorMessage += "To edit a skill, change the description\n";
        }

        /*
         * If no attribute was selected, remind user!
         */
        if (attributes.getSelectedToggle() == null) {
            errorMessage += "Must select the associated attribute!\n";
        }
        /*
         * If no skill type was selected, remind user!
         */
        if (skillType.getSelectedToggle() == null) {
            errorMessage += "Must select a skill tyoe!\n";
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

    @FXML
    private void handleAddTime(){
        this.skill = mainApp.showAddTimeDialog(skill);
        System.out.println("Time to next level: "+skill.getExperienceToNextLevel());
    }

}

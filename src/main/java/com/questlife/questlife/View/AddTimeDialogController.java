package main.java.com.questlife.questlife.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.skills.Skill;

/**
 *
 * Created by Gemin on 04.05.2017.
 */
public class AddTimeDialogController {
    private Skill skill;
    private Stage dialogStage;

    @FXML
    private Label timeToAdd;
    @FXML
    private Slider sliderTime;
    @FXML
    private ComboBox<String> comboTime;

    @FXML
    public void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Minutes");
        items.add("Hours");
        comboTime.setItems(items);
        comboTime.getSelectionModel().select(0);
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleSlider() {
        timeToAdd.setText(""+(int) Math.round(sliderTime.getValue()));
    }

    @FXML
    private void handleComboSelect() {
        if(comboTime.getSelectionModel().isEmpty()) {
            return;
        }
        if(comboTime.getSelectionModel().getSelectedItem().equals("Minutes")) {
            sliderTime.setMax(60);
            sliderTime.setMajorTickUnit(20);
            sliderTime.setMinorTickCount(20);
            sliderTime.setShowTickMarks(false);
        } else if(comboTime.getSelectionModel().getSelectedItem().equals("Hours")) {
            sliderTime.setMax(24);
            sliderTime.setMajorTickUnit(4);
            sliderTime.setMinorTickCount(4);
            sliderTime.setShowTickMarks(false);
        }
    }

    @FXML
    private void handleOk() {
        int timeGain = Integer.parseInt(timeToAdd.getText());
        timeGain = (comboTime.getSelectionModel().getSelectedItem().equals("Minutes")) ? timeGain:timeGain*60;
        skill.gainExperience(timeGain);
        dialogStage.close();
    }
}

package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.goals.Goals;

import java.time.LocalDateTime;

/**
 *
 * Created by Gemin on 05.05.2017.
 */
public class DefineNewGoalDeadlineDialogController {

    private Goals goal;
    private Stage dialogStage;
    private LocalDateTime ldt;

    @FXML
    private DatePicker deadliner;
    @FXML
    private TextField hours;
    @FXML
    private TextField minutes;

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
    public void setGoal(Goals goal) {
        this.goal = goal;
        deadliner.setValue(goal.getDeadline().toLocalDate());
        hours.setText(""+goal.getDeadline().getHour());
        minutes.setText(""+goal.getDeadline().getMinute());
    }
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if(inputOk()) {
            ldt = deadliner.getValue().atTime(Integer.parseInt(hours.getText()), Integer.parseInt(minutes.getText()));
            goal.setDeadline(ldt);
        }
    }

    public LocalDateTime getLDT() {
        return ldt;
    }

    private boolean inputOk() {
        String errorMessage = "";

        // try to parse the cost into an int.
        try {
            if(Integer.parseInt(hours.getText())>24 || Integer.parseInt(hours.getText())<0) {
                errorMessage+="Hour must be between 0-24";
                hours.setText("12");
            }
        } catch (NumberFormatException e) {
            errorMessage += "Hour must be a whole number!\n";
            hours.setText("12");
        }
        // try to parse the cost into an int.
        try {
            if(Integer.parseInt(minutes.getText())>60 || Integer.parseInt(minutes.getText())<0) {
                errorMessage+="Hour must be between 0-60";
                minutes.setText("0");
            }
        } catch (NumberFormatException e) {
            errorMessage += "Minute must be a whole number!\n";
            minutes.setText("0");
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

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}

package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 *
 * Created by Gemin on 01.05.2017.
 */
public class TownViewController {

    private Stage dialogStage;

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

    @FXML
    private void handleLeaveTown() {
        dialogStage.close();
    }

    @FXML
    private void handleShop() {

    }

    @FXML
    private void handleTemple() {

    }

    @FXML
    private void handleMarket() {

    }

    @FXML
    private void handleTavern() {

    }
}

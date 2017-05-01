package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;

/**
 *
 * Created by Gemin on 01.05.2017.
 */
public class TownViewController {

    private Stage dialogStage;
    private MainApp mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp reference
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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

    /**
     * Called when user clicks invisible button for shop
     */
    @FXML
    private void handleShop() {
        mainApp.showShopDialog();
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

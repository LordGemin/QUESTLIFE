package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import main.java.com.questlife.questlife.MainApp;

import java.io.File;

/**
 *
 * Created by Gemin on 16.05.2017.
 */
public class RootLayoutController {


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
     * Called when the user selects town from menu "views"
     */
    @FXML
    private void handleShowTown() {
        mainApp.showTownView();
    }

    /**
     * Called when user selects Tavern from submenu town from "views"
     */
    @FXML
    private void handleShowTavern() {
        mainApp.showTavernDialog();
    }

    /**
     * Called when user selects Temple from submenu town from "views"
     */
    @FXML
    private void handleShowTemple() {
        mainApp.showTempleDialog();
    }

    /**
     * Called when user selects Marketboard from submenu town from "views"
     */
    @FXML
    private void handleShowMarket() {
        mainApp.showMarketBoardView();
    }

    /**
     * Called when user selects Shop from submenu town from "views"
     */
    @FXML
    private void handleShowShops() {
        mainApp.showShopDialog();
    }


    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadDataFromFile(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getFilePath();
        if (personFile != null) {
            mainApp.saveDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveDataToFile(file);
        }
    }


    /**
     * Called when the user clicks Exit.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Questlife");
        alert.setHeaderText("About");
        alert.setContentText("Author: Robert Buschmann\n");

        alert.showAndWait();
    }

}

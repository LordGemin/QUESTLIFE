package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.town.Tavern;

/**
 *
 * Created by Gemin on 02.05.2017.
 */
public class TavernDialogController {

    @FXML
    private Label tavernName;

    private MainApp mainApp;
    private Stage dialogStage;
    private Tavern tavern;

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    /**
     * provides reference to the main app
     *
     * initializes shop front with all available items
     * also adjusts all items displayed for hero level
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.tavern = new Tavern();
        tavernName.setText(tavern.getName());
    }

    /**
     * Called when user decides to lodge Hero in tavern.
     * Will display an alert if hero has not enough money (and throw the user our)
     */
    @FXML
    private void handleLodge() {
        if(!tavern.lodgeHero(mainApp.getHeroData().get(0))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thrown out");
            alert.setHeaderText("Lodging denied");
            alert.setContentText(mainApp.getHeroData().get(0).getName()+ " has not enough money!\n"+mainApp.getHeroData().get(0).getName()+" was thrown out of the tavern!\n");
            alert.showAndWait();
            dialogStage.close();
        }
    }

    /**
     * Called when user decides to cancel the transaction
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}

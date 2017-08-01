package main.java.com.questlife.questlife.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.hero.Hero;

/**
 *
 * Created by Gemin on 01.08.2017.
 */
public class HeroCreationDialogController {

    Stage dialogStage;
    Hero hero = new Hero();

    @FXML
    RadioButton rb_war;
    @FXML
    RadioButton rb_mage;
    @FXML
    RadioButton rb_thief;

    @FXML
    ToggleGroup Class;

    @FXML
    Label strength;
    @FXML
    Label dexterity;
    @FXML
    Label charisma;
    @FXML
    Label observation;
    @FXML
    Label mind;
    @FXML
    Label constitution;


    // Base values for our classes. Values must always add up to 18
    private final static int STR = 3;
    private final static int DEX = 3;
    private final static int MND = 3;
    private final static int CON = 3;
    private final static int OBS = 3;
    private final static int CHA = 3;

    @FXML
    TextField heroName;

    @FXML
    Button okButton;



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
     * @param dialogStage given to class by main
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }




    /**
     * Sets the goal to be edited in the dialog.
     *
     * @param hero given to the class by main
     */
    public void setHero(Hero hero) {
        this.hero = hero;
        hero.setStrength(STR);
        hero.setDexterity(DEX);
        hero.setMind(MND);
        hero.setCharisma(CHA);
        hero.setObservation(OBS);
        hero.setConstitution(CON);

        updateLabels();
    }
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if(inputOk()) {
            hero.setName(heroName.getText());
            dialogStage.close();
        }
    }

    /**
     * Called when user toggles warrior class
     */
    @FXML
    private void handleWar() {
        hero.setStrength(STR+2);
        hero.setConstitution(CON+2);
        hero.setCharisma(CHA-2);
        hero.setMind(MND-2);
        hero.setObservation(OBS);
        hero.setDexterity(DEX);

        updateLabels();
    }


    /**
     * Called when user toggles mage class
     */
    @FXML
    private void handleMage() {
        hero.setStrength(STR-2);
        hero.setConstitution(CON-1);
        hero.setCharisma(CHA);
        hero.setMind(MND+3);
        hero.setObservation(OBS+1);
        hero.setDexterity(DEX-1);

        updateLabels();
    }

    /**
     * Called when user toggles thief class
     */
    @FXML
    private void handleThief() {
        hero.setStrength(STR-1);
        hero.setConstitution(CON-1);
        hero.setCharisma(CHA+1);
        hero.setMind(MND-1);
        hero.setObservation(OBS+1);
        hero.setDexterity(DEX+1);

        updateLabels();
    }

    private boolean inputOk() {
        String errorMessage = "";

        if(heroName.getText().length() < 1) {
            errorMessage += "No valid hero name!\n";
        }
        if(Class.getSelectedToggle() == null) {
            errorMessage += "Select a class!\n";
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

    private void updateLabels() {
        strength.setText(""+hero.getStrength().getLevel());
        dexterity.setText(""+hero.getDexterity().getLevel());
        mind.setText(""+hero.getMind().getLevel());
        charisma.setText(""+hero.getCharisma().getLevel());
        observation.setText(""+hero.getObservation().getLevel());
        constitution.setText(""+hero.getConstitution().getLevel());
    }

}

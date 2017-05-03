package main.java.com.questlife.questlife.View;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.goals.Goals;
import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.util.RewardType;

import java.util.List;

/**
 *
 * Created by Gemin on 28.04.2017.
 */
public class mainLayoutController {

    private Hero hero;


    /**
     * All the labels to be seen. Attributes, Level, Name
     *
     */
    @FXML
    private Label heroName;
    @FXML
    private Label strength;
    @FXML
    private Label dexterity;
    @FXML
    private Label mind;
    @FXML
    private Label charisma;
    @FXML
    private Label observation;
    @FXML
    private Label constitution;
    @FXML
    private Label piety;
    @FXML
    private Label gold;


    /**
     * Progressbar to show progress towards next heroLevel
     */
    @FXML
    private ProgressBar experienceToNextLevel;

    /**
     * Table that contains all goals
     */
    @FXML
    private TableView<Goals> goalsTable;
    @FXML
    private TableColumn<Goals, String> goalsName;
    @FXML
    private TableColumn<Goals, List<Skill>> goalsAssociatedSkills;
    @FXML
    private TableColumn<Goals, String> goalsDeadline;
    @FXML
    private TableColumn<Goals, String> goalsExperience;

    /**
     * Table that contains all quests
     */
    @FXML
    private TableView<Quest> questsTable;
    @FXML
    private TableColumn<Quest, String> questEnemy;
    @FXML
    private TableColumn<Quest, Integer> questEnemyCount;
    @FXML
    private TableColumn<Quest, Integer> questExpReward;
    @FXML
    private TableColumn<Quest, Integer> questGoldReward;

    /**
     * Table that contains all rewards
     */
    @FXML
    private TableView<Reward> rewardTable;
    @FXML
    private TableColumn<Reward, String> rewardName;
    @FXML
    private TableColumn<Reward, String> rewardDescription;
    @FXML
    private TableColumn<Reward, RewardType> rewardAquirationBase;
    @FXML
    private TableColumn<Reward, Integer> rewardCost;
    @FXML
    private TableColumn<Reward, Integer> rewardAmount;

    /**
     * Table that contains all skills
     */
    @FXML
    private TableView<Skill> skillsTable;
    @FXML
    private TableColumn<Skill, String> skillName;
    @FXML
    private TableColumn<Skill, Integer> skillLevel;
    @FXML
    private TableColumn<Skill, Attributes> skillAssociatedAttribute;
    @FXML
    private TableColumn<Skill, Integer> skillExperience;

    /**
     * Table that contains all Items the hero has (his inventory)
     */
    @FXML
    private TableView<AbstractItems> inventoryTable;
    @FXML
    private TableColumn<AbstractItems, String> inventoryName;
    @FXML
    private TableColumn<AbstractItems, String> inventoryDescription;


    private MainApp mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the goal table with the four columns.
        goalsName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        goalsAssociatedSkills.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAssociatedSkills()));
        goalsDeadline.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeadLineAsString()));
        goalsExperience.setCellValueFactory(cellData -> new SimpleStringProperty(""+cellData.getValue().getExperienceReward()));


        // Initialize the quest table with the four columns.
        questEnemy.setCellValueFactory(cellData -> cellData.getValue().getEnemyType().nameProperty());
        questEnemyCount.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMobsToHunt()));
        questExpReward.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRewardGold()));
        questGoldReward.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRewardExp()));

        // Initialize the reward table with the five columns
        rewardName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        rewardDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        rewardAquirationBase.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRewardType()));
        rewardCost.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCost()));
        rewardAmount.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCanReceive()));

        // Initialize the skill table with four columns
        skillName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        skillAssociatedAttribute.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAssociatedAttribute()));
        skillLevel.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLevel()));
        skillExperience.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExperienceToNextLevel()));

        // Initialize the inventory with two columns
        inventoryName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        inventoryDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));


     /*   // Listen for selection changes and show the person details accordingly
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
*/
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp reference
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.hero = mainApp.getHeroData().get(0);

        goalsTable.setItems(mainApp.getGoalData());
        questsTable.setItems(mainApp.getQuestData());
        rewardTable.setItems(mainApp.getRewardData());
        skillsTable.setItems(mainApp.getSkillData());
        inventoryTable.setItems(mainApp.getInventory());

        heroName.setText(hero.getName());
        experienceToNextLevel.setProgress(hero.getExperience()/hero.getExperienceToNextLevel());
        strength.setText(""+hero.getStrength());
        dexterity.setText(""+hero.getDexterity());
        mind.setText(""+hero.getMind());
        charisma.setText(""+hero.getCharisma());
        observation.setText(""+hero.getObservation());
        constitution.setText(""+hero.getConstitution());
        piety.setText(""+hero.getPiety());
        gold.setText(""+ hero.getGold());
    }

    @FXML
    private void handleNewGoal(){
        Goals tempGoal = new Goals();
        boolean okClicked = mainApp.showGoalEditDialog(tempGoal);
        if (okClicked) {
            mainApp.getGoalData().add(tempGoal);
        }
    }

    @FXML
    private void handleNewSkill(){
        Skill tempSkill = new Skill();
        boolean okClicked = mainApp.showSkillEditDialog(tempSkill);
        if (okClicked) {
            mainApp.getSkillData().add(tempSkill);
        }
    }

    @FXML
    private void handleNewReward(){
        Reward tempReward = new Reward();
        boolean okClicked = mainApp.showRewardEditDialog(tempReward);
        if (okClicked) {
            mainApp.getRewardData().add(tempReward);
        }
    }

    @FXML
    private void handleNewQuest(){
        Quest tempQuest = new Quest();
        boolean okClicked = mainApp.showQuestEditDialog(tempQuest);
        if (okClicked) {
            mainApp.getQuestData().add(tempQuest);
        }
    }

    /**
     * Called when the user selects town from menu "views"
     */
    @FXML
    private void handleShowTown() {
        mainApp.showTownView();
        updateLabels();
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

    private void updateLabels() {
        heroName.setText(hero.getName());
        experienceToNextLevel.setProgress(hero.getExperience()/hero.getExperienceToNextLevel());
        strength.setText(""+hero.getStrength());
        dexterity.setText(""+hero.getDexterity());
        mind.setText(""+hero.getMind());
        charisma.setText(""+hero.getCharisma());
        observation.setText(""+hero.getObservation());
        constitution.setText(""+hero.getConstitution());
        piety.setText(""+hero.getPiety());
        gold.setText(""+hero.getGold());
    }

}

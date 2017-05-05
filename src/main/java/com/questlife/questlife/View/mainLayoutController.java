package main.java.com.questlife.questlife.View;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.goals.Goals;
import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.AbstractPotions;
import main.java.com.questlife.questlife.items.AbstractWeapons;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.town.Field;
import main.java.com.questlife.questlife.util.RewardType;
import main.java.com.questlife.questlife.util.SkillType;

import java.util.ArrayList;
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
    private Label level;
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

    @FXML
    private Button inventoryPotionUse;
    @FXML
    private Button inventoryWeaponEquip;


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
        skillExperience.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExperienceToNextLevel()-cellData.getValue().getExperience()));

        // Initialize the inventory with two columns
        inventoryName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        inventoryDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        inventoryPotionUse.setDisable(true);
        inventoryPotionUse.setVisible(false);
        inventoryWeaponEquip.setDisable(true);
        inventoryWeaponEquip.setVisible(false);

        // Listen for selection changes and show the person details accordingly
        inventoryTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleInvSelection(newValue));

    }

    private void handleInvSelection(AbstractItems newValue) {
        if(newValue instanceof AbstractPotions) {
            inventoryPotionUse.setDisable(false);
            inventoryPotionUse.setVisible(true);
            inventoryWeaponEquip.setDisable(true);
            inventoryWeaponEquip.setVisible(false);
            return;
        }
        if(newValue instanceof AbstractWeapons) {
            inventoryPotionUse.setDisable(true);
            inventoryPotionUse.setVisible(false);

            inventoryWeaponEquip.setDisable(false);
            inventoryWeaponEquip.setVisible(true);
        }
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
        inventoryTable.setItems(mainApp.getInventory());
        skillsTable.setItems(mainApp.getSkillData());

        updateLabels();
    }

    @FXML
    private void handleNewGoal(){
        Goals tempGoal = new Goals();
        boolean okClicked = mainApp.showGoalEditDialog(tempGoal);
        if (okClicked) {
            mainApp.getGoalData().add(tempGoal);
        }
        updateLabels();
    }

    @FXML
    private void handleDeleteGoal() {
        if (goalsTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can only delete one element");
            alert.setHeaderText("Select goal");
            alert.setContentText("Please select one goal from the table above\n");

            alert.showAndWait();
            return;
        }
        mainApp.getGoalData().remove(goalsTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleEditGoal() {
        if (goalsTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can only edit one element");
            alert.setHeaderText("Select goal");
            alert.setContentText("Please select one goal from the table above\n");

            alert.showAndWait();
            return;
        }
        Goals goal = goalsTable.getSelectionModel().getSelectedItem();
        mainApp.showGoalEditDialog(goal);
        updateLabels();
    }

    @FXML
    private void handleNewSkill(){
        Skill tempSkill = new Skill();
        boolean okClicked = mainApp.showSkillEditDialog(tempSkill);
        if (okClicked) {
            mainApp.getSkillData().add(tempSkill);
        }
        updateLabels();
    }

    /**
     * Called when user wants to edit a reward.
     */
    @FXML
    private void handleEditSkill() {
        if (skillsTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can only edit one element");
            alert.setHeaderText("Select skill");
            alert.setContentText("Please select one skill from the table above\n");

            alert.showAndWait();
            return;
        }
        Skill skill = skillsTable.getSelectionModel().getSelectedItem();
        mainApp.showSkillEditDialog(skill);
        updateLabels();
    }

    /**
     * Called when user wants to delete a skill
     */
    @FXML
    private void handleDeleteSkill() {
        if (skillsTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can only delete one element");
            alert.setHeaderText("Select skill");
            alert.setContentText("Please select one skill from the table above\n");

            alert.showAndWait();
            return;
        }
        Skill skill = skillsTable.getSelectionModel().getSelectedItem();

        for(Reward r:mainApp.getRewardData()) {
            if(r.getAssociatedSkill() == skill) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Can't delete");
                alert.setHeaderText("Reward");
                alert.setContentText(r.getName() + " is based on this skill.\nPlease delete the reward first!");

                alert.showAndWait();
                return;
            }
        }
        for(Goals r:mainApp.getGoalData()) {
            if(r.getAssociatedSkills().contains(skill)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Can't delete");
                alert.setHeaderText("Goal");
                alert.setContentText(r.getName() + " is based on this skill.\nPlease remove it from the goal first!");

                alert.showAndWait();
                return;
            }
        }

        mainApp.getSkillData().remove(skill);
        updateLabels();
    }

    @FXML
    private void handleAddTimeToSkill() {
        if (skillsTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can not add time");
            alert.setHeaderText("Select skill");
            alert.setContentText("Please select one skill from the table above\n");

            alert.showAndWait();
            return;
        }

        Skill skill = skillsTable.getSelectionModel().getSelectedItem();

        if(skill.getSkilltype() != SkillType.TIMEBASED) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can not add time");
            alert.setHeaderText("Goalbased skill");
            alert.setContentText("Please select a time based skill!\nOr edit this skill to be time based.");

            alert.showAndWait();
            return;
        }
        mainApp.showAddTimeDialog(skill);
        System.out.println("Time to next level: "+skill.getExperienceToNextLevel());

        updateLabels();

    }

    @FXML
    private void handleNewReward(){
        Reward tempReward = new Reward();
        boolean okClicked = mainApp.showRewardEditDialog(tempReward);
        if (okClicked) {
            mainApp.getRewardData().add(tempReward);
        }
        updateLabels();
    }

    /**
     * Called when user wants to edit a reward.
     */
    @FXML
    private void handleEditReward() {
        if (rewardTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can only edit one element");
            alert.setHeaderText("Select reward");
            alert.setContentText("Please select one reward from the table above\n");

            alert.showAndWait();
            return;
        }
        Reward reward = rewardTable.getSelectionModel().getSelectedItem();
        mainApp.showRewardEditDialog(reward);
        updateLabels();
    }

    /**
     * Called when user wants to delete a reward
     */
    @FXML
    private void handleDeleteReward() {
        if (rewardTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't delete");
            alert.setHeaderText("Select reward");
            alert.setContentText("Please select one reward from the table above\n");

            alert.showAndWait();
            return;
        }

        Reward reward = rewardTable.getSelectionModel().getSelectedItem();
        mainApp.getRewardData().remove(reward);
        updateLabels();

    }

    /**
     * Called when user wants to see the details of a quest
     */
    @FXML
    private void handleQuestDetail(){
        if (questsTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Could not display detail");
            alert.setHeaderText("Select Quest");
            alert.setContentText("Please select one quest from the table above\n");

            alert.showAndWait();
            return;
        }
        Quest detail = questsTable.getSelectionModel().getSelectedItem();
        mainApp.showQuestDetailDialog(detail);
        updateLabels();

    }

    /**
     * Called when user abandons a quest
     */
    @FXML
    private void handleAbandonQuest() {
        if (questsTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't abandon");
            alert.setHeaderText("Select Quest");
            alert.setContentText("Please select one quest from the table above\n");

            alert.showAndWait();
            return;
        }
        Quest deletion = questsTable.getSelectionModel().getSelectedItem();
        if(hero.getActiveQuest() == deletion) {
            hero.setActiveQuest(null);
        }
        hero.getQuestList().remove(deletion);
        mainApp.getQuestData().remove(deletion);
        updateLabels();
    }

    /**
     * Called when user clicks the set active button on a quest
     */
    @FXML
    private void handleSetActiveQuest() {
        if (questsTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Could not set active");
            alert.setHeaderText("Select one quest");
            alert.setContentText("Please select one quest from the table above\n");

            alert.showAndWait();
            return;
        }
        hero.setActiveQuest(questsTable.getSelectionModel().getSelectedItem());
        updateLabels();
    }

    /**
     * Called when user clicks the use potion button, which is only enabled when user has selected a potion
     */
    @FXML
    private void handleUsePotion() {
        hero.setInventory(inventoryTable.getItems());
        if (inventoryTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Could not use");
            alert.setHeaderText("Select potion");
            alert.setContentText("Please select one potion from the table above\n");

            alert.showAndWait();
            return;
        }
        hero.takePotion((AbstractPotions) inventoryTable.getSelectionModel().getSelectedItem());
        updateLabels();
    }

    /**
     * Called when user clicks the equip weapon button, which is only enabled when user has selected a weapon
     */
    @FXML
    private void handleEquipWeapon() {
        hero.setInventory(inventoryTable.getItems());
        if (inventoryTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Could equip");
            alert.setHeaderText("Select weapon");
            alert.setContentText("Please select one weapon from the table above\n");

            alert.showAndWait();
            return;
        }
        hero.changeWeapon((AbstractWeapons) inventoryTable.getSelectionModel().getSelectedItem());
        updateLabels();
    }

    /**
     * Called when user clicks to sell some items
     */
    @FXML
    private void handleSellItem() {
        if (inventoryTable.getSelectionModel().getSelectedItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Couldn't sell");
            alert.setHeaderText("Select item");
            alert.setContentText("Please select at least one item from the table above\n");

            alert.showAndWait();
            return;
        }
        int value = 0;
        for(AbstractItems a:inventoryTable.getSelectionModel().getSelectedItems()){
            value += a.getPrice();
            mainApp.getInventory().remove(a);
        }
        // Resell value is much lower than new item value
        hero.gainGold(Math.round(value/3.0f));
        gold.setText(""+hero.getGold());
        updateLabels();
    }

    /**
     * Called when user clicks to sell some items
     */
    @FXML
    private void handleItemDetail() {
        if (inventoryTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Detail error");
            alert.setHeaderText("Select item");
            alert.setContentText("Please select one item from the table above\n");

            alert.showAndWait();
            return;
        }
        AbstractItems selected = inventoryTable.getSelectionModel().getSelectedItem();
        if(selected instanceof AbstractPotions) {
            mainApp.showPotionDetailDialog(selected);
        } else if(selected instanceof AbstractWeapons) {
            mainApp.showWeaponDetailDialog(selected);
        }
        updateLabels();
    }

    @FXML
    private void sendHeroToField() {
        System.out.println("Hero health: "+hero.getHealth());
        if(hero.getHealth() > 0) {
            System.out.println("Sending "+hero.getName()+" to the field.");
            Field field = new Field(hero, mainApp.getEnemyData());
            field.runBattles(hero.getActiveQuest());
        }
        updateLabels();
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
        level.setText(""+hero.getLevel());
        experienceToNextLevel.setProgress(((float)hero.getExperience())/hero.getExperienceToNextLevel());
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

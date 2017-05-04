package main.java.com.questlife.questlife;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.View.*;
import main.java.com.questlife.questlife.enemy.Enemy;
import main.java.com.questlife.questlife.goals.Goals;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.*;
import main.java.com.questlife.questlife.player.Player;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.town.Shop;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.Generator;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 *
 * Created by Gemin on 28.04.2017.
 */
public class MainApp extends Application {

    private static final int TAVERNCOST = 50;
    private Stage primaryStage;

    private AnchorPane mainLayout;

    /**
     * The data as an observable list of Goals.
     */
    private ObservableList<Goals> goalData = FXCollections.observableArrayList();
    public ObservableList<Goals> getGoalData() {
        return goalData;
    }

    /**
     * The data as an observable list of Quests.
     */
    private ObservableList<Quest> questData = FXCollections.observableArrayList();

    public ObservableList<Quest> getQuestData() {
        return questData;
    }


    /**
     * The data as an observable list of rewards.
     */
    private ObservableList<Reward> rewardData = FXCollections.observableArrayList();

    public ObservableList<Reward> getRewardData() {
        return rewardData;
    }

    /**
     * The data as an observable list of skills.
     */
    private ObservableList<Skill> skillData = FXCollections.observableArrayList();

    public ObservableList<Skill> getSkillData() {
        return skillData;
    }

    /**
     * The inventory data as obersvable list of items
     */
    private ObservableList<AbstractItems> inventory = FXCollections.observableArrayList();

    public ObservableList<AbstractItems> getInventory() {
        return inventory;
    }

    /**
     * The data as an observable list of items.
     */
    private ObservableList<AbstractItems> itemsData = FXCollections.observableArrayList();

    public ObservableList<AbstractItems> getItemsData() {
        return itemsData;
    }

    private ObservableList<Class> itemsRaw = FXCollections.observableArrayList();

    public ObservableList<Class> getItemsRaw() {
        return itemsRaw;
    }

    /**
     * Initializes all items using reflections.
     * This is will continue to add all future items.
     *
     * @return true if initializing via reflection worked.
     */
    private boolean initializeItems() {
        boolean flag = false;
        // Set up the reflection API at root directory
        Reflections reflections = new Reflections("main.java");
        // Find all classes that extend on abstract items in any way
        Set<Class<? extends AbstractItems>> classes = reflections.getSubTypesOf(AbstractItems.class);
        // Iterate through all extending classes
        for (Object a : classes) {
            AbstractItems item;
            try {
                // We can only take the mostly fully quantified name of a class to create it internally
                Class rawItem = Class.forName(a.toString().replace("class ", ""));
                // We add the class to our raw items data to randomly generate different versions of the same class
                // We also add all items as some one time generated form into the itemsData list
                item = (AbstractItems) rawItem.newInstance();
                itemsRaw.add(rawItem);
            } catch (Exception e) {
                item = null;
            }
            if (item instanceof AbstractPotions) {
                itemsData.add((AbstractPotions) item);
                flag = true;
                continue;
            }
            if (item instanceof AbstractWeapons) {
                itemsData.add((AbstractWeapons) item);
                flag = true;
                continue;
            }
            System.out.println("Object was: " + a.toString());
        }
        return flag;

    }


    /**
     * The data as an observable list of enemies.
     */
    private ObservableList<Enemy> enemyData = FXCollections.observableArrayList();
    public ObservableList<Enemy> getEnemyData() {
        return enemyData;
    }

    /**
     * Creates Enemies for the game to use.
     *
     * TODO: Have abstract enemy main class and specific enemies!
     */
    private void initializeEnemies() {
        Generator generator = new Generator();

        for(int i=0; i < 20; i++) {
            AttackType attackType = AttackType.PHYSICAL;
            String enemyName = generator.generateEnemyName();
            if(enemyName.contains("Blue")) {
                attackType = AttackType.MAGICAL;
            }
            enemyData.add(new Enemy(enemyName, 1, attackType));
        }
    }

    /**
     * The data as an observable list of hero(s). Saving just one hero, but List to be observable
     */
    private ObservableList<Hero> heroData = FXCollections.observableArrayList();

    public ObservableList<Hero> getHeroData() {
        return heroData;
    }


    /**
     * A shop to have the game work. Doesn't need to be observable as of now.
     */
    private Shop shop = new Shop(new Generator().generateShopNames());

    public Shop getShop() {
        return shop;
    }

    /**
     * Constructor
     */
    public MainApp() {
        initializeItems();
        initializeEnemies();

        heroData.add(new Hero(new Player(), "Bolgerig", new Weapon(1)));
        heroData.get(0).setCharisma(20);
        heroData.get(0).setConstitution(50);
        heroData.get(0).setGold(5000);
        heroData.get(0).setHealth(heroData.get(0).getMaxHealth());

        //TODO: SampleData?
        goalData.add(new Goals());
        goalData.add(new Goals());
        goalData.add(new Goals());
    }


    /**
     * Returns the main stage.
     * @return Stage - primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Questlife");
        
        initMainLayout();

    }

    private void initMainLayout() {
        try {
            // Load main layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/mainLayout.fxml"));
            mainLayout = (AnchorPane) loader.load();

            // Show the scene containing the main layout.
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            mainLayoutController controller = loader.getController();
            controller.setMainApp(this);

            // As the design is barebones, don't let the window be resized
            primaryStage.setResizable(false);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        /*File file = getSaveDataFilePath();
        if (file != null) {
            loadSaveDataFromFile(file);
        }*/
    }


    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return File at filepath
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
        } else {
            prefs.remove("filePath");
        }
    }



    public static void main(String[] args) {
        launch(args);
    }

    public boolean showGoalEditDialog(Goals tempGoal) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/goalEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Goal");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            GoalEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGoal(tempGoal, this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean showSkillEditDialog(Skill tempSkill) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/skillEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Skill");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            SkillEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSkill(tempSkill);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showRewardEditDialog(Reward tempReward) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/rewardEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Reward");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            RewardEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReward(tempReward, this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showQuestDetailDialog(Quest tempQuest) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/questDetailDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Quest");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            QuestDetailDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setQuest(tempQuest);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showPotionDetailDialog(AbstractItems item) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/potionDetailDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(item.getName()+" Details");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            PotionDetailDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setItem(item);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showWeaponDetailDialog(AbstractItems item) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/weaponDetailDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(item.getName()+" Details");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            WeaponDetailDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setItem(item);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showTownView() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/townView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Town View");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            TownViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showShopDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/shopDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Shop");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            ShopDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showTavernDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/tavernDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tavern");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Disable resizing of the window
            // TODO: Enable by smart design
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            TavernDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showTempleDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/templeDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Temple");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Disable resizing of the window
            // TODO: Enable by smart design
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            TempleDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showMarketBoardView() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/marketboardView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Marketboard");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Disable resizing of the window
            // TODO: Enable by smart design
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            MarketboardViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Skill showAddTimeDialog(Skill skill) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/addTimeDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add time");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Disable resizing of the window
            // TODO: Enable by smart design
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            AddTimeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSkill(skill);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();


            return controller.getSkill();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

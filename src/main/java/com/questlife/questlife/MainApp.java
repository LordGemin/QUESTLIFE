package main.java.com.questlife.questlife;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.View.*;
import main.java.com.questlife.questlife.goals.Goals;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.*;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.town.Shop;
import main.java.com.questlife.questlife.util.GameWrapper;
import main.java.com.questlife.questlife.util.Generator;
import main.java.com.questlife.questlife.util.Statistics;
import org.reflections.Reflections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 *
 * Created by Gemin on 28.04.2017.
 */
public class MainApp extends Application {

    private static final String PREFERRED_PATH = "src/main/resources/questlife_save.xml";

    private static final int TAVERN_COST = 50;
    private long shopCounter = System.currentTimeMillis();
    private Stage primaryStage;
    private MainApp mainApp = this;

    private BorderPane rootLayout;

    private mainLayoutController mainController;

    public boolean isRunning() {
        return primaryStage.isShowing();
    }

    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * Class to regularly save game data.
     *
     */
    class gameLoop extends Task {
        @Override
        protected Object call() throws Exception {
            int ctr = 0;
            final int SLEEP = 3000;
            while(mainApp.isRunning()) {
                try {
                    Thread.sleep(SLEEP);
                    ctr += SLEEP;
                    // mainController.updateLayout();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!mainApp.isRunning()) {
                    return null;
                }
                if(ctr >= 300000) {
                    ctr = 0;
                    mainApp.saveDataToFile(mainApp.getFilePath());
                }
            }
            return null;
        }
    }

    /**
     * Getter & Setters for various variables
     */
    public static int getTAVERNCOST() {
        return TAVERN_COST;
    }


    public void setShopCounter(long shopCounter) {
        this.shopCounter = shopCounter;
    }

    public long getSinceLastShopCounterUpdate() {
        return shopCounter - System.currentTimeMillis();
    }

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

    /*
    public ObservableList<AbstractItems> getItemsData() {
        return itemsData;
    }*/

    private ObservableList<Class> itemsRaw = FXCollections.observableArrayList();

    public ObservableList<Class> getItemsRaw() {
        return itemsRaw;
    }

    /**
     * Initializes all items using reflections.
     * This is will continue to add all future items.
     *
     */
    private void initializeItems() {
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
                itemsData.add(item);
                continue;
            }
            if (item instanceof AbstractWeapons) {
                itemsData.add(item);
                continue;
            }
            System.out.println("Object was: " + a.toString());
        }

    }


    /**
     * The data as an observable list of enemies.
     */
    private ObservableList<String> enemyData = FXCollections.observableArrayList();
    public ObservableList<String> getEnemyData() {
        return enemyData;
    }

    /**
     * Creates Enemies for the game to use.
     *
     */
    private void initializeEnemies() {
        Generator generator = new Generator();

        for(int i=0; i < 20; i++) {
            String enemyName = generator.generateEnemyName();
            enemyData.add(enemyName);
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
     * Statistics to check progress
     */
    private Statistics statistics = new Statistics();

    /**
     * Constructor
     */
    public MainApp() {
        initializeItems();
        initializeEnemies();

        heroData.add(new Hero("Bolderig"));
        heroData.get(0).setStatistics(statistics);
        heroData.get(0).setCharisma(1);
        heroData.get(0).setConstitution(1);
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

        initRoot();
        initMainLayout();

        // Implement some simple gameLoop to keep everything updated
        new Thread(new gameLoop()).start();

    }

    private void initRoot() {
        try {
            // Load main layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the main layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            // As the design is barebones, don't let the window be resized
            primaryStage.setResizable(false);

            primaryStage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getFilePath();
        if (file != null) {
            loadDataFromFile(file);
        } else {
            // If there is no last file, force user to pick new file
            newSaveFile();
        }
    }

    private void newSaveFile() {
        boolean created = false;
        File file = new File(PREFERRED_PATH);

        if(!file.exists()) {
            try {
                created =  file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        if(created) {
            fileChooser.setInitialDirectory(file.getParentFile().getAbsoluteFile());
            fileChooser.setInitialFileName(file.getName());
        } else {
            fileChooser.setInitialFileName(file.getName());
        }
        // Show save file dialog
        File newFile = fileChooser.showSaveDialog(primaryStage);

        if (newFile != null) {
            // Make sure it has the correct extension
            if (!newFile.getPath().endsWith(".xml")) {
                newFile = new File(newFile.getPath() + ".xml");
            }
            saveDataToFile(newFile);
        }
    }

    private void initMainLayout() {
        try {
            // Load main layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/mainLayout.fxml"));
            AnchorPane mainLayout = loader.load();

            rootLayout.setCenter(mainLayout);

            // Give the controller access to the main app.
            mainController = loader.getController();
            mainController.setMainApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateMainLayout() {
        mainController.updateLayout();
    }

    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return File at filepath
     */
    public File getFilePath() {
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
    private void setFilePath(File file) {
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
            AnchorPane page = loader.load();


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
            controller.setGoalAndMainApp(tempGoal, this);

            //rootLayout.setCenter(page);

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
            AnchorPane page = loader.load();

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
            AnchorPane page = loader.load();

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

    public void showQuestDetailDialog(Quest tempQuest) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/questDetailDialog.fxml"));
            AnchorPane page = loader.load();

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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPotionDetailDialog(AbstractItems item) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/potionDetailDialog.fxml"));
            AnchorPane page = loader.load();

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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWeaponDetailDialog(AbstractItems item) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/weaponDetailDialog.fxml"));
            AnchorPane page = loader.load();

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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTownView() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/townView.fxml"));
            AnchorPane page = loader.load();

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

            updateMainLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showShopDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/shopDialog.fxml"));
            AnchorPane page = loader.load();

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

            updateMainLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTavernDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/tavernDialog.fxml"));
            AnchorPane page = loader.load();

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

            updateMainLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTempleDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/templeDialog.fxml"));
            AnchorPane page = loader.load();

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

            updateMainLayout();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMarketBoardView() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/marketboardView.fxml"));
            AnchorPane page = loader.load();

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

            updateMainLayout();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showStatisticsDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/statisticsView.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Statistics");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Disable resizing of the window
            // TODO: Enable by smart design
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            StatisticsViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            updateMainLayout();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Skill showAddTimeDialog(Skill skill) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/addTimeDialog.fxml"));
            AnchorPane page = loader.load();

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

    /**
     * Called when user completes a recurring goal
     *
     * @param goal Goal that needs a new deadline
     * @return The deadline that was set
     */
    public LocalDateTime showDefineNewDeadlineDialog(Goals goal) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/defineNewGoalDeadlineDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Set new deadline");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Disable resizing of the window
            // TODO: Enable by smart design
            dialogStage.setResizable(false);

            // Set the goal into the controller.
            DefineNewGoalDeadlineDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGoal(goal);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();


            return controller.getLDT();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Called when user clicks Log in views in root layout
     */
    public void showLogView() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/logView.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Logs");
            dialogStage.getIcons().add(new Image("file:resources/images/Address_Book.png"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Disable resizing of the window
            // TODO: Enable by smart design
            dialogStage.setResizable(false);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file file to be loaded from
     */
    public void loadDataFromFile(File file) {
        try {
            if (!file.exists()) {
                newSaveFile();
                return;
            }
            JAXBContext gameContext = JAXBContext.newInstance(GameWrapper.class);
            Unmarshaller umGame = gameContext.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            GameWrapper gameWrapper = (GameWrapper) umGame.unmarshal(file);

            // Load stats
            if(gameWrapper.getStatistics() != null)
                statistics = gameWrapper.getStatistics();

            // Load quests
            questData.clear();
            if(gameWrapper.getQuests() != null)
                questData.addAll(gameWrapper.getQuests());

            // Load inventory
            inventory.clear();
            if(gameWrapper.getInventoryWeapons() != null)
                inventory.addAll(gameWrapper.getInventoryWeapons());
            if(gameWrapper.getInventoryPotions() != null)
                inventory.addAll(gameWrapper.getInventoryPotions());

            // Load heroes
            heroData.clear();
            if(gameWrapper.getHeroes() != null)
                heroData.addAll(gameWrapper.getHeroes());
            // Give the poor hero/ine their inventory & quests
            for (Hero h:heroData) {
                h.setWeapon(h.getWeapon());
                h.setInventory(inventory);
                h.setQuestList(questData);
                h.setStatistics(statistics);
            }

            // Load skills
            skillData.clear();
            if(gameWrapper.getSkills() != null)
                skillData.addAll(gameWrapper.getSkills());

            // Load rewards
            rewardData.clear();
            if(gameWrapper.getRewards() != null)
                rewardData.addAll(gameWrapper.getRewards());

            // Load goals
            goalData.clear();
            if(gameWrapper.getGoals() != null)
                goalData.addAll(gameWrapper.getGoals());


            // Save the file path to the registry.
            setFilePath(file);
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    private class MySchemaOutputResolver extends SchemaOutputResolver {

        public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
            File file = new File(suggestedFileName);
            StreamResult result = new StreamResult(file);
            result.setSystemId(file.toURI().toURL().toString());
            return result;
        }

    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param file file to be safed to
     */
    public void saveDataToFile(File file) {
        try {

            JAXBContext gameContext = JAXBContext.newInstance(GameWrapper.class);
            Marshaller mGame = gameContext.createMarshaller();
            mGame.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            SchemaOutputResolver sor = new MySchemaOutputResolver();
            gameContext.generateSchema(sor);

            GameWrapper gameWrapper = new GameWrapper();
            gameWrapper.setQuests(questData);

            List<AbstractWeapons> weaponsList = new ArrayList<>();
            List<AbstractPotions> potionsList = new ArrayList<>();
            for(AbstractItems a:inventory) {
                if(a instanceof AbstractWeapons)
                    weaponsList.add((AbstractWeapons) a);
                if(a instanceof AbstractPotions)
                    potionsList.add((AbstractPotions) a);
            }
            gameWrapper.setInventoryWeapons(weaponsList);
            gameWrapper.setInventoryPotions(potionsList);
            gameWrapper.setHeroes(heroData);
            gameWrapper.setSkills(skillData);
            gameWrapper.setRewards(rewardData);
            gameWrapper.setGoals(goalData);
            gameWrapper.setStatistics(statistics);
            mGame.marshal(gameWrapper,file);

            // Save the file path to the registry.
            setFilePath(file);
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
}

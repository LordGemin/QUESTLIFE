package main.java.com.questlife.questlife;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.View.*;
import main.java.com.questlife.questlife.goals.GoalWrapper;
import main.java.com.questlife.questlife.goals.Goals;
import main.java.com.questlife.questlife.hero.Attributes;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.hero.HeroWrapper;
import main.java.com.questlife.questlife.items.*;
import main.java.com.questlife.questlife.quests.Quest;
import main.java.com.questlife.questlife.quests.QuestListWrapper;
import main.java.com.questlife.questlife.rewards.Reward;
import main.java.com.questlife.questlife.rewards.RewardWrapper;
import main.java.com.questlife.questlife.skills.Skill;
import main.java.com.questlife.questlife.town.Shop;
import main.java.com.questlife.questlife.util.AttackType;
import main.java.com.questlife.questlife.util.GameWrapper;
import main.java.com.questlife.questlife.util.Generator;
import main.java.com.questlife.questlife.skills.SkillWrapper;
import org.reflections.Reflections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 *
 * Created by Gemin on 28.04.2017.
 */
public class MainApp extends Application {

    private static final int TAVERNCOST = 50;
    private long shopCounter = System.currentTimeMillis();
    private Stage primaryStage;
    private Stage parentStage;

    private BorderPane rootLayout;

    private AnchorPane mainLayout;

    /**
     * Getter & Setters for various variables
     */
    public static int getTAVERNCOST() {
        return TAVERNCOST;
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
    private ObservableList<String> enemyData = FXCollections.observableArrayList();
    public ObservableList<String> getEnemyData() {
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
     * Constructor
     */
    public MainApp() {
        initializeItems();
        initializeEnemies();

        heroData.add(new Hero("Bolderig"));
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

    }

    private void initRoot() {
        try {
            // Load main layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

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
    }

    private void initMainLayout() {
        try {
            // Load main layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/mainLayout.fxml"));
            mainLayout = (AnchorPane) loader.load();

            rootLayout.setCenter(mainLayout);

            // Give the controller access to the main app.
            mainLayoutController controller = loader.getController();
            controller.setMainApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getFilePath();
        if (file != null) {
            loadDataFromFile(file);
        }
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
    public void setFilePath(File file) {
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
            AnchorPane page = (AnchorPane) loader.load();

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
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file
     */
    public void loadDataFromFile(File file) {
        try {
            JAXBContext gameContext = JAXBContext.newInstance(GameWrapper.class);
            Unmarshaller umGame = gameContext.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            GameWrapper gameWrapper = (GameWrapper) umGame.unmarshal(file);

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
                h.setInventory(inventory);
                h.setQuestList(questData);
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

/*

            //
            // LOADING QUESTS
            //
            JAXBContext questContext = JAXBContext
                    .newInstance(QuestListWrapper.class);
            Unmarshaller umQuest = questContext.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            QuestListWrapper questWrapper = (QuestListWrapper) umQuest.unmarshal(file);

            // Setting up the List
            questData.clear();
            questData.addAll(questWrapper.getQuests());

            //
            // LOADING INVENTORY (WEAPON & POTION SEPARATE
            //
            JAXBContext absWeaponContext = JAXBContext
                    .newInstance(InvAbstractWeaponsWrapper.class);
            JAXBContext absPotionContext = JAXBContext
                    .newInstance(InvAbstractPotionsWrapper.class);
            Unmarshaller umWeapon = absWeaponContext.createUnmarshaller();
            Unmarshaller umPotion = absPotionContext.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            InvAbstractWeaponsWrapper weaponWrapper = (InvAbstractWeaponsWrapper) umWeapon.unmarshal(file);
            InvAbstractPotionsWrapper potionsWrapper = (InvAbstractPotionsWrapper) umPotion.unmarshal(file);

            // Setting up the List
            inventory.clear();
            inventory.addAll(weaponWrapper.getWeapons());
            inventory.addAll(potionsWrapper.getPotions());

            //
            // LOADING ATTRIBUTE
            //
            JAXBContext attrContext = JAXBContext.newInstance(Attributes.class);
            Unmarshaller umAttr = attrContext.createUnmarshaller();
            umAttr.unmarshal(file);

            //
            // LOADING HEROES
            //
            JAXBContext heroContext = JAXBContext
                    .newInstance(HeroWrapper.class);
            Unmarshaller umHero = heroContext.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            HeroWrapper heroWrapper = (HeroWrapper) umHero.unmarshal(file);

            // Setting up the List
            heroData.clear();
            heroData.addAll(heroWrapper.getHeroes());
            // Give the poor hero/ine their inventory & quests
            for (Hero h:heroData) {
                h.setInventory(inventory);
                h.setQuestList(questData);
            }

            //
            // LOADING SKILLS
            //
            JAXBContext skillContext = JAXBContext.newInstance(SkillWrapper.class);
            Unmarshaller umSkill = skillContext.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            SkillWrapper skillWrapper = (SkillWrapper) umSkill.unmarshal(file);

            // Setting up the List
            skillData.clear();
            skillData.addAll(skillWrapper.getSkills());

            //
            // LOADING REWARDS
            //
            JAXBContext rewardContext = JAXBContext.newInstance(RewardWrapper.class);
            Unmarshaller umReward = rewardContext.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            RewardWrapper rewardWrapper = (RewardWrapper) umReward.unmarshal(file);

            // Setting up the List
            rewardData.clear();
            rewardData.addAll(rewardWrapper.getRewards());

            //
            // LOADING GOALS
            //
            JAXBContext goalContext = JAXBContext.newInstance(Goals.class);
            Unmarshaller umGoal = goalContext.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            GoalWrapper goalWrapper = (GoalWrapper) umGoal.unmarshal(file);

            // Setting up the List
            goalData.clear();
            goalData.addAll(goalWrapper.getGoals());


            // Save the file path to the registry.
            setFilePath(file);
*/

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
     * @param file
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
            mGame.marshal(gameWrapper,file);


            /*
            FileChannel fc = FileChannel.open(file.toPath());
            XMLOutputFactory xof = XMLOutputFactory.newFactory();
            XMLStreamWriter xsw = xof.createXMLStreamWriter(Channels.newOutputStream(fc), "UTF-8");

            xsw.writeStartDocument("UTF-8", "1");


            //
            // SAVING QUESTS
            //
            JAXBContext questContext = JAXBContext
                    .newInstance(QuestListWrapper.class);
            Marshaller mQuest = questContext.createMarshaller();
            mQuest.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our quest data.
            QuestListWrapper questWrapper = new QuestListWrapper();
            questWrapper.setQuests(questData);

            // Marshalling and saving XML to the file.
            mQuest.marshal(questWrapper, xsw);

            //
            // SAVING INVENTORY
            //
            JAXBContext absWeaponContext = JAXBContext.newInstance(InvAbstractWeaponsWrapper.class);
            JAXBContext absPotionContext = JAXBContext.newInstance(InvAbstractPotionsWrapper.class);
            Marshaller mWeapon = absWeaponContext.createMarshaller();
            mWeapon.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Marshaller mPotion = absPotionContext.createMarshaller();
            mPotion.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping the inventory data.
            InvAbstractWeaponsWrapper weaponsWrapper = new InvAbstractWeaponsWrapper();
            InvAbstractPotionsWrapper potionsWrapper = new InvAbstractPotionsWrapper();
            for(AbstractItems a:inventory) {
                if(a instanceof AbstractWeapons)
                    weaponsWrapper.getWeapons().add((AbstractWeapons) a);
                if(a instanceof AbstractPotions)
                    potionsWrapper.getPotions().add((AbstractPotions) a);
            }
            mWeapon.marshal(weaponsWrapper, xsw);
            mPotion.marshal(potionsWrapper, xsw);

            //
            // SAVING ATTRIBUTES
            //
            JAXBContext attrContext = JAXBContext.newInstance(Attributes.class);
            Marshaller mAttr = attrContext.createMarshaller();
            mAttr.marshal(Attributes.STRENGTH, xsw);
            mAttr.marshal(Attributes.DEXTERITY, xsw);
            mAttr.marshal(Attributes.MIND, xsw);
            mAttr.marshal(Attributes.CHARISMA, xsw);
            mAttr.marshal(Attributes.CONSTITUTION, xsw);
            mAttr.marshal(Attributes.OBSERVATION, xsw);
            mAttr.marshal(Attributes.PIETY, xsw);

            //
            // SAVING HEROES
            //
            JAXBContext heroContext = JAXBContext
                    .newInstance(HeroWrapper.class);
            Marshaller mHero = heroContext.createMarshaller();

            // wrapping hero data
            HeroWrapper heroWrapper = new HeroWrapper();
            heroWrapper.setHeroes(heroData);

            mHero.marshal(heroWrapper, xsw);

            //
            // SAVING SKILLS
            //
            JAXBContext skillContext = JAXBContext.newInstance(SkillWrapper.class);
            Marshaller mSkill = skillContext.createMarshaller();

            // wrapping our skills
            SkillWrapper skillWrapper = new SkillWrapper();
            skillWrapper.setSkills(skillData);

            mSkill.marshal(skillWrapper, xsw);


            //
            // SAVING REWARDS
            //
            JAXBContext rewardContext = JAXBContext.newInstance(RewardWrapper.class);
            Marshaller mReward = rewardContext.createMarshaller();

            // wrapping reward Data
            RewardWrapper rewardWrapper = new RewardWrapper();
            rewardWrapper.setRewards(rewardData);

            mReward.marshal(rewardWrapper, xsw);

            //
            // SAVING GOALS
            //
            JAXBContext goalContext = JAXBContext.newInstance(GoalWrapper.class);
            Marshaller mGoal = goalContext.createMarshaller();

            // wrapping goal data
            GoalWrapper goalWrapper = new GoalWrapper();
            goalWrapper.setGoals(goalData);

            mGoal.marshal(goalWrapper, xsw);

            xsw.close();
            fc.close();
*/
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

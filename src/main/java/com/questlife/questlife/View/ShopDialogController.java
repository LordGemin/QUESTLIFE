package main.java.com.questlife.questlife.View;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.*;
import main.java.com.questlife.questlife.town.Shop;
import main.java.com.questlife.questlife.util.Generator;

/**
 *
 * Created by Gemin on 01.05.2017.
 */
public class ShopDialogController {

    private Stage dialogStage;
    private MainApp mainApp;
    private Shop shop;
    private ObservableList<AbstractItems> shopFront = FXCollections.observableArrayList();

    @FXML
    private Label shopname;

    @FXML
    private TableView<AbstractItems> itemsTable;
    @FXML
    private TableColumn<AbstractItems, String> itemsName;
    @FXML
    private TableColumn<AbstractItems, String> itemsDescription;
    @FXML
    private TableColumn<AbstractItems, Integer> itemPrice;


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

        shop = mainApp.getShop();

        if(shop.getItemList().size() == 0) {
            // We want a restocking if we have no items in the shop
            shopRestocking();
        } else if (mainApp.getSinceLastShopCounterUpdate() >= 14400000) {
            // We also want a restocking every four hours
            // We clear the item list, and start a new
            shop.getItemList().clear();
            shopRestocking();
            // Reset our clock
            mainApp.setShopCounter(System.currentTimeMillis());
        } else {
            for(AbstractItems a: shop.getItemList()) {
                a.updatePrice(mainApp.getHeroData().get(0));
            }
        }

        shopFront.addAll(shop.getItemList());

        itemsTable.setItems(shopFront);
        itemsTable.sort();

        shopname.setText(shop.getName());
    }

    private void shopRestocking() {
        Hero hero = mainApp.getHeroData().get(0);

        // Our shop needs restocking
        System.out.println("Shop restocked");
        AbstractItems item;
        Generator generator = new Generator();
        while(shop.getItemList().size() <= 10) {
            int pick = generator.generateNumber()%(mainApp.getItemsRaw().size());

            // Should we picked anything but 0, decrease pick by one to avoid out of bounds problems.
            // TODO: Even distribution. Like this we get too many 0
            if(pick != 0)
                pick--;

            try {
                item = (AbstractItems) mainApp.getItemsRaw().get(pick).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                item = null;
                e.printStackTrace();
            }

            if (item instanceof AbstractPotions) {
                shop.addItem((AbstractPotions) item);
                item.setHeroLevel(hero.getLevel());
                item.updatePrice(hero);
            }
            if (item instanceof AbstractWeapons) {
                shop.addItem((AbstractWeapons) item);
                item.setHeroLevel(hero.getLevel());
                item.updatePrice(hero);
            }

        }
    }

    @FXML
    private void initialize() {
        itemsName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        itemsDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        itemPrice.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
    }


    @FXML
    private void handleDetails() {
        if(itemsTable.getSelectionModel().getSelectedItems().size()>1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Item sale failed");
            alert.setHeaderText("Problem");
            alert.setContentText("Too many items selected!\nPlease select only one item.");
            alert.show();
            return;
        }
        if (itemsTable.getSelectionModel().getSelectedItems() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Item sale failed");
            alert.setHeaderText("Problem");
            alert.setContentText("No item selected!\nPlease select an item.");
            alert.show();
        }

        AbstractItems item = itemsTable.getSelectionModel().getSelectedItem();
        if (item instanceof AbstractPotions) {
            mainApp.showPotionDetailDialog(item);
        } else if (item instanceof AbstractWeapons){
            mainApp.showWeaponDetailDialog(item);
        }
    }

    /**
     * Called when the user wants to buy an item. Checks for multiple selection/no selection, then tries to sell
     *
     * TODO: Fix alerts!!!
     */
    @FXML
    private void handleBuy() {
        if(itemsTable.getSelectionModel().getSelectedItems().size()>1) {
            //TODO: Allow wholesale?
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Item sale failed");
            alert.setHeaderText("Problem");
            alert.setContentText("Too many items selected!\nPlease select only one item.");
            alert.show();
            return;
        }
        if(itemsTable.getSelectionModel().getSelectedItems().size()<1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Item sale failed");
            alert.setHeaderText("Problem");
            alert.setContentText("No item selected!\nPlease select an item.");
            alert.show();
            return;
        }
        if(mainApp.getHeroData().size() >= 1) {
            Hero hero = mainApp.getHeroData().get(0);

            AbstractItems item = itemsTable.getSelectionModel().getSelectedItem();
            if (!shop.sellItem(item, hero)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Item sale failed");
                alert.setHeaderText("Problem");
                alert.setContentText("Not enough money!\n");
                alert.show();
                return;
            }
            mainApp.getInventory().add(item);
            shopFront.remove(item);
            System.out.println("Hero has: "+hero.getGold()+" Gold left");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Item sale failed");
            alert.setHeaderText("Problem");
            alert.setContentText("Create a hero first!\n");
            alert.show();
        }
    }

    @FXML
    private void handleLeave() {
        dialogStage.close();
    }
}

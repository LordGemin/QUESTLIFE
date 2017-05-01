package main.java.com.questlife.questlife.View;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.MainApp;
import main.java.com.questlife.questlife.hero.Hero;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.AbstractPotions;
import main.java.com.questlife.questlife.items.AbstractWeapons;
import main.java.com.questlife.questlife.town.Shop;

/**
 *
 * Created by Gemin on 01.05.2017.
 */
public class ShopDialogController {

    private Stage dialogStage;
    private MainApp mainApp;
    private Shop shop;

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
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
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
            return;
        }
        if (itemsTable.getSelectionModel().getSelectedItems() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Item sale failed");
            alert.setHeaderText("Problem");
            alert.setContentText("No item selected!\nPlease select an item.");
        }

        AbstractItems item = itemsTable.getSelectionModel().getSelectedItem();
        if (item instanceof AbstractPotions) {
            mainApp.showPotionDetailDialog(item);
        } else if (item instanceof AbstractWeapons){
            mainApp.showWeaponDetailDialog(item);
        }
    }

    @FXML
    private void handleBuy() {
        if(itemsTable.getSelectionModel().getSelectedItems().size()>1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Item sale failed");
            alert.setHeaderText("Problem");
            alert.setContentText("Too many items selected!\nPlease select only one item.");
            return;
        }
        if(mainApp.getHeroData().size() >= 1) {
            Hero hero = mainApp.getHeroData().get(0);

            AbstractItems item = itemsTable.getSelectionModel().getSelectedItem();
            if (shop.sellItem(item.getName(), hero) == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Item sale failed");
                alert.setHeaderText("Problem");
                alert.setContentText("No item selected!\nPlease select an item.");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Item sale failed");
            alert.setHeaderText("Problem");
            alert.setContentText("Create a hero first!\n");
        }
    }

    @FXML
    private void handleLeave() {
        dialogStage.close();
    }
}

package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.AbstractWeapons;

/**
 *
 * Created by Gemin on 01.05.2017.
 */
public class WeaponDetailDialogController {


    private Stage dialogStage;

    @FXML
    private Label weaponName;
    @FXML
    private Label weaponDescription;
    @FXML
    private Label weaponPhysicalAttack;
    @FXML
    private Label weaponMagicalAttack;
    @FXML
    private Label weaponAttackType;
    @FXML
    private Label weaponPrice;


    @FXML
    private void initialize() {

    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setItem(AbstractItems item) {
        weaponName.setText(item.getName());
        weaponDescription.setText(item.getDescription());
        weaponPhysicalAttack.setText(""+((AbstractWeapons)item).getPhysicalAttack());
        weaponMagicalAttack.setText(""+((AbstractWeapons)item).getMagicalAttack());
        weaponAttackType.setText(""+((AbstractWeapons)item).getAttackType().getFieldDescription());
        weaponPrice.setText(""+item.getPrice());

    }

    @FXML
    private void handleExit() {
        dialogStage.close();
    }

}

package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.com.questlife.questlife.items.AbstractItems;
import main.java.com.questlife.questlife.items.AbstractPotions;

/**
 *
 * Created by Gemin on 01.05.2017.
 */
public class PotionDetailDialogController {

    private Stage dialogStage;

    @FXML
    private Label potionName;
    @FXML
    private Text potionDescription;
    @FXML
    private Label potionStrengthHP;
    @FXML
    private Label potionStrengthMP;
    @FXML
    private Label potionPrice;


    @FXML
    private void initialize() {

    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setItem(AbstractItems item) {
        potionName.setText(item.getName());
        potionDescription.setText(item.getDescription());
        potionStrengthHP.setText(""+((AbstractPotions)item).getStrengthHP());
        potionStrengthMP.setText(""+((AbstractPotions)item).getStrengthMP());
        potionPrice.setText(""+item.getPrice());

    }

    @FXML
    private void handleExit() {
        dialogStage.close();
    }
}

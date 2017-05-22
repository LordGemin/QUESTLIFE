package main.java.com.questlife.questlife.View;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.com.questlife.questlife.util.Logger;

import java.io.*;

/**
 *
 * Created by Gemin on 22.05.2017.
 */
public class LogViewController {
    @FXML
    private TextFlow textFlow;
    @FXML
    private ScrollPane scrollPane;

    private File file = Logger.getFile();

    @FXML
    public void initialize() {

        //TODO: COMMENT THIS CODE
        StringWriter sw = new StringWriter();

        try(FileReader fileReader =  new FileReader(file)){

            int data = fileReader.read();
            while(data != -1) {
                sw.append((char) data);
                data = fileReader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] strings = sw.toString().split("\n");

        for(String s:strings) {
            String[] a = s.split("] ");
            Text text = new Text();
            if(a.length < 2) {
                text.setText("\n");
            } else {
                text.setText(a[1] + "\n");
                text.setFill(Logger.getColor(a[0].replace("[", "")));
            }
            textFlow.getChildren().add(text);
        }

    }
}

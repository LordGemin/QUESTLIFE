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
        // Create a variable to contain all current log
        StringWriter sw = new StringWriter();

        // We receive the default logfile and read its contents bytewise into the writer
        try(FileReader fileReader =  new FileReader(file)){

            // First character is read from the file
            int data = fileReader.read();
            while(data != -1) {
                // We add the received character to our string
                sw.append((char) data);
                // And receive the next character
                data = fileReader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Split our now massive stringwriter by lines and save each line as own string in array
        String[] strings = sw.toString().split("\n");

        for(String s:strings) {
            // As we have decided to use colours in our log, we get seperate the first part [COLOUR] from the actual text
            String[] a = s.split("] ");

            // And create a new text object to be added into the TextFlow shown to the player
            // We use a text object as it lets us use colours
            Text text = new Text();
            if(a.length < 2) {
                // We don't have any text this line, just newline
                text.setText("\n");
            } else {
                // First we set the text of our textfield, taken from the second arrayfield
                text.setText(a[1] + "\n");
                // Then we set the colour by getting rid of the last [ before COLOUR and passing it to a parser
                text.setFill(Logger.getColor(a[0].replace("[", "")));
            }
            // We add the thus formed text field and take the next string/line from our log
            textFlow.getChildren().add(text);
        }

    }
}

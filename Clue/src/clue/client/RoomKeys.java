/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Displays the corresponding room colours to the name of the room names.
 * @author Hung Bui Quang
 * @author Jose Colaco
 */
public class RoomKeys {
    
    private Stage roomKeysStage;
    private Font avenirLarge;
    private Font avenirText;
    
    /**
     * Initialises the RoomKeys stage and all its content.
     * @param stage the parent stage
     */
    public RoomKeys(Stage stage) {
        roomKeysStage = new Stage();
        roomKeysStage.initOwner(stage);
        roomKeysStage.initStyle(StageStyle.UNDECORATED);
        
        initFont();
        
        GridPane roomKeys = new GridPane();
        roomKeys.setBackground(new Background(new BackgroundFill(Color.rgb(226, 226, 226), CornerRadii.EMPTY, Insets.EMPTY)));
        roomKeys.setAlignment(Pos.CENTER);
        roomKeys.setPadding(new Insets(10));
        
        Label roomLabel = getLabel("Room", avenirLarge);
        Label keyLabel = getLabel("Key", avenirLarge);
        
        roomKeys.add(roomLabel, 0, 0);
        roomKeys.add(keyLabel, 1, 0);
        
        for (int i=1; i < 10; i++) {
            Label colour = new Label();
            colour.setMinSize(30, 30);
            colour.setMaxSize(30, 30);
            switch(i-1) {
                case 0:
                    colour.setStyle("-fx-background-color: #696969;");
                    break;
                case 1:
                    colour.setStyle("-fx-background-color: #42d4f4;");
                    break;
                case 2:
                    colour.setStyle("-fx-background-color: #000075");
                    break;
                case 3:
                    colour.setStyle("-fx-background-color: #f58231;");
                    break;
                case 4:
                    colour.setStyle("-fx-background-color: #911eb4;");
                    break;
                case 5:
                    colour.setStyle("-fx-background-color: #800000");
                    break;
                case 6:
                    colour.setStyle("-fx-background-color: #808000");
                    break;
                case 7:
                    colour.setStyle("-fx-background-color: #fffac8;");
                    break;
                case 8:
                    colour.setStyle("-fx-background-color: #fabebe");
                    break;
                default:
                    break;
            }
            roomKeys.add(colour, 1, i);
        }
        
        String[] rooms = new String[]{"Study", "Hall", "Lounge", "Library", "Billard Room", "Dining Room", "Conservatory", "Ballroom", "Kitchen"};
        for (int i=0; i < 9; i++) {
            Label roomName = getLabel(rooms[i], avenirText);
            roomKeys.add(roomName, 0, i+1);
            GridPane.setMargin(roomName, new Insets(0, 10, 0, 0));
        }
        
        Scene scene = new Scene(roomKeys);
        roomKeysStage.setScene(scene);
        roomKeysStage.setResizable(false);
    }
    
    /**
     * calls show on the roomKeysStage to display the window.
     */
    public void show() {
        roomKeysStage.show();
    }
    
    /**
     * calls hide on the roomKeysStage to hide the window.
     */
    public void hide() {
        roomKeysStage.hide();
    }
    
    /**
     * sets the stage location at the given X and Y.
     * @param x the X coordinate of the desired location
     * @param y the Y coordinate of the desired location
     */
    public void setLocation(double x, double y) {
        roomKeysStage.setX(x);
        roomKeysStage.setY(y);
    }
    
    /**
     * Initialises the fonts and tries to load the ttf file.
     */
    private void initFont() {
        avenirLarge = new Font(20);
        avenirText = new Font(15);
        try {
            avenirLarge = Font.loadFont(new FileInputStream(new File("resources/fonts/Avenir-Book.ttf")), 20);
            avenirText = Font.loadFont(new FileInputStream(new File("resources/fonts/Avenir-Book.ttf")), 15);
            
        } catch(FileNotFoundException ex) {
            
        }
    }
    
    /**
     * creates a label of the given text in the given font.
     * @param text the content of the label
     * @param font the desired font
     * @return Label of text in the given font
     */
    private Label getLabel(String text, Font font) {
        Label label = new Label(text);
        label.setFont(font);
        return label;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author hungb
 */
public class boardEditor {
    
    private Font avenirLarge;
    private EditorTile[][] board;
    
    private VBox createPalette() {
        VBox paletteLayout = new VBox();
        paletteLayout.setAlignment(Pos.CENTER);

        MenuItem roomButton = new MenuItem("Room", avenirLarge);
        roomButton.setBackgroundColor(Color.rgb(87, 73, 60));
        roomButton.setPrefWidth(120);
        
        MenuItem emptyButton = new MenuItem("Empty", avenirLarge);
        emptyButton.setBackgroundColor(Color.rgb(6, 96, 38));
        emptyButton.setPrefWidth(120);
        
        MenuItem hallButton = new MenuItem("Hall", avenirLarge);
        hallButton.setBackgroundColor(Color.rgb(225, 147, 31));
        hallButton.setPrefWidth(120);
        
        MenuItem spawnButton = new MenuItem("Spawn", avenirLarge);
        spawnButton.setBackgroundColor(Color.rgb(60, 134, 1));
        spawnButton.setPrefWidth(120);
        
        MenuItem intrigueButton = new MenuItem("Intrigue", avenirLarge);
        intrigueButton.setBackgroundColor(Color.GOLD);
        intrigueButton.setPrefWidth(120);
        
        MenuItem doorUpButton = new MenuItem("Door Up", avenirLarge);
        doorUpButton.setBackgroundColor(Color.rgb(161, 97, 0));
        doorUpButton.setPrefWidth(120);
        
        MenuItem doorDownButton = new MenuItem("Door Down", avenirLarge);
        doorDownButton.setBackgroundColor(Color.rgb(161, 97, 0));
        doorDownButton.setPrefWidth(120);
        
        MenuItem doorLeftButton = new MenuItem("Door Left", avenirLarge);
        doorLeftButton.setBackgroundColor(Color.rgb(161, 97, 0));
        doorLeftButton.setPrefWidth(120);
        
        MenuItem doorRightButton = new MenuItem("Door Right", avenirLarge);
        doorRightButton.setBackgroundColor(Color.rgb(161, 97, 0));
        doorRightButton.setPrefWidth(120);
        
        paletteLayout.getChildren().addAll(roomButton, emptyButton, hallButton,
                spawnButton, intrigueButton, doorUpButton, doorDownButton,
                doorLeftButton, doorRightButton);
        return paletteLayout;
    }
    
    private void generateBoard(int x, int y, GridPane pane) {
        board = new EditorTile[y][x];
        
        for (int i=0; i < x; i++) {
            for (int j=0; i < y; j++) {
                Label l = new Label();
                l.prefHeight(38);
                l.prefWidth(38);
                
                l.setOnMouseClicked(e -> {

                });
            }
        }
    }
    
    private void initFonts() {
        avenirLarge = new Font(20);
        try {
            avenirLarge = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 20);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(boardEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void paintTile(Label l) throws CreationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void startEditor() {
        Stage editorStage = new Stage();
        editorStage.initModality(Modality.APPLICATION_MODAL);
        editorStage.setTitle("Board Editor");
        editorStage.setResizable(false);
        
        initFonts();
        
        BorderPane editorLayout = new BorderPane();
        editorLayout.setLeft(createPalette());
        
        Scene scene = new Scene(editorLayout);
        editorStage.setScene(scene);
        editorStage.show();
    }


}

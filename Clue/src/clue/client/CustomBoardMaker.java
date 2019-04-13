/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 *
 * @author zemig
 */
public class CustomBoardMaker extends Application{
    
    int width;
    int height;
    boolean mouseDown;
    TileType lastSelected;
    Stage mainStage;
    BorderPane root;
    VBox pallete;
    GridPane gridPane;
    Scene mainScene;
    
    enum TileType{
        ROOM,
        DOOR,
        WALKABLE,
        INTRIGUE,
        EMPTY,
        START
        
    }

    public CustomBoardMaker(){
        mainStage = new Stage();   
        root = new BorderPane();
        gridPane = new GridPane();
        pallete = new VBox();
        lastSelected = TileType.DOOR;
    }
    
    public static void main(String[] args) throws Exception{
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage.setTitle("Board Customiser");
        mainStage.setResizable(false);
        mainStage.initModality(Modality.APPLICATION_MODAL);
        
        addPallete();
        root.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        makeGrid(24, 25, gridPane);
 
        width = 1280;
        height = 720;
        
        mainScene = new Scene(root, width, height);
        mainStage.setScene(mainScene);
        mainStage.show();
    }
    
    public void makeGrid(int x, int y, GridPane pane){
        for(int i = 0; i < x; i++){
            for(int u = 0; u < y; u++){
                Label l = new Label();
                l.setMinSize(50, 50);
                l.setMaxSize(100, 100);
                l.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY))); 
                l.setOnMouseClicked(e -> paintTile(l));
                pane.setConstraints(l, i , u);
                pane.getChildren().add(l);
            }
        }
    }
    
    public void paintTile(Label l){
        switch(lastSelected){
            case ROOM:
                l.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.RED, CornerRadii.EMPTY, Insets.EMPTY))); 
                break;
            case DOOR:
                l.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY))); 
                break;
            case EMPTY:
                l.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case WALKABLE:
                l.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY))); 
                break;
            case INTRIGUE:
                l.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY))); 
                break;
            case START:
                l.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.PINK, CornerRadii.EMPTY, Insets.EMPTY))); 
                break;
        }
    }
    
 
    public void addPallete(){
        
        //Different "Brushes" Available
        CustomBoardPallete room = new CustomBoardPallete("Room", TileType.ROOM);
        CustomBoardPallete door = new CustomBoardPallete("Door", TileType.DOOR);
        CustomBoardPallete empty = new CustomBoardPallete("Empty", TileType.EMPTY);
        CustomBoardPallete walkable = new CustomBoardPallete("Walkable", TileType.WALKABLE);
        CustomBoardPallete start = new CustomBoardPallete("Start", TileType.START);
        CustomBoardPallete intrigue = new CustomBoardPallete("Intregue", TileType.INTRIGUE);
        
        //Button Functionality
        room.setOnMouseClicked(e -> lastSelected = room.getTileType());
        door.setOnMouseClicked(e -> lastSelected = door.getTileType());
        empty.setOnMouseClicked(e -> lastSelected = empty.getTileType());
        walkable.setOnMouseClicked(e -> lastSelected = walkable.getTileType());
        start.setOnMouseClicked(e -> lastSelected = start.getTileType());
        intrigue.setOnMouseClicked(e -> lastSelected = intrigue.getTileType());
        
        
        pallete.setAlignment(Pos.BASELINE_CENTER);
        pallete.getChildren().addAll(room, door, empty, walkable, start, intrigue);
        
        root.setLeft(pallete);
        
    }
    
    public void setTile(TileType t){
        lastSelected = t;
    }
    
    
   
}

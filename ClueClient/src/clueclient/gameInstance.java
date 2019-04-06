/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clueclient;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author hungb
 */
public class gameInstance {
    
    private static final int TILE_SIZE = 38;
    private int width;
    private int height;
    private int counter;
   
    private Tile[][] board = new Tile[25][24];
    
    private GridPane createBoard() {
        GridPane boardPane = new GridPane();
        
        Image boardImage = new Image(getClass().getResource("assets/boardImage.png").toExternalForm());
        
        boardPane.setBackground(new Background(new BackgroundImage(boardImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        boardPane.setPrefSize(width, height);
        
        // create base Tiles
        for (int y=0; y < 24; y++) {
            for (int x=0; x < 25; x++) {
                Tile tile = new Tile(TILE_SIZE);
                tile.setOnMouseClicked((MouseEvent e) -> {
                    System.out.println("HELLO "+counter);
                    counter++;
                });
                        
                        
                board[x][y] = tile;
                boardPane.add(tile, y, x);
                
            }
        }
        
        // TODO: spawn players
        
        return boardPane;
    }
    
    public VBox createNotepad() {
        VBox notepadLayout = new VBox();
        
        Label notepadLabel = new Label("Notepad");
        
        TextArea notepad = new TextArea();
        notepad.setPrefRowCount(20);
        notepad.setPrefColumnCount(15);
        
        notepadLayout.getChildren().addAll(notepadLabel, notepad);
        
        return notepadLayout;
    }
    
    public GridPane createCardsDisplay() {
        GridPane cardsLayout = new GridPane();
        
        Label playerCardsLabel = new Label("Cards");
        
        cardsLayout.add(playerCardsLabel, 0, 0);
        
        return cardsLayout;
    }
    
    private GridPane createPlayerControls() {
        GridPane playerControlsLayout = new GridPane();
        
        Button suggestButton = new Button("Suggestion");
        suggestButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        suggestButton.setOnAction(e -> {
            System.out.println("Suggest");
        });
        
        Button accuseButton = new Button("Accusation");
        accuseButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        accuseButton.setOnAction(e -> {
            System.out.println("Accuse");
        });
        
        Button rollDiceButton = new Button("Roll");
        
        Button endTurnButton = new Button("End Turn");
        
        playerControlsLayout.add(suggestButton, 0, 0, 2, 1);
        playerControlsLayout.add(accuseButton, 0, 1, 2, 1);
        playerControlsLayout.add(rollDiceButton, 0, 2);
        playerControlsLayout.add(endTurnButton, 1, 2);
        
        return playerControlsLayout;        
    }
    
    private BorderPane createUI() {
        BorderPane main = new BorderPane();
        main.setLeft(createNotepad());
        
        main.setCenter(createBoard());
        
        VBox rightPanel = new VBox();
        rightPanel.getChildren().addAll(createCardsDisplay(), createPlayerControls());
        
        main.setRight(rightPanel);
        
        return main;
    }
    
    public void startGame(int numberOfPlayers, boolean AIPlayers, int width, int height) {
        Stage gameStage = new Stage();
        
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setTitle("Clue");
        gameStage.setResizable(false);

        // Temp return button
        Button returnButton = new Button("Back");
        returnButton.setOnAction(e -> {
            gameStage.close();
        });
        
        Scene scene = new Scene(createUI());
        gameStage.setScene(scene);
        gameStage.show();
    }
}

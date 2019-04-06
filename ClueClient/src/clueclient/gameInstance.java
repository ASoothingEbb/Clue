/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clueclient;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author hungb
 */
public class gameInstance {
    
    public static void startGame(int numberOfPlayers, boolean AIPlayers, int width, int height) {
        Stage gameStage = new Stage();
        
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setTitle("Clue");
        gameStage.setResizable(false);
        
        // mainLayout
        BorderPane main = new BorderPane();
        
        // Notepad
        VBox notepadLayout = new VBox();
        
        Label notepadLabel = new Label("Notes");
        TextArea notepad = new TextArea();
        notepad.setPrefRowCount(10);
        
        notepadLayout.getChildren().addAll(notepadLabel, notepad);
        
        // RightPane
        VBox rightPane = new VBox();
        
        // Player Cards
        GridPane playerCardsLayout = new GridPane();
        
        Label playerCardsLabel = new Label("Cards");
        
        playerCardsLayout.add(playerCardsLabel, 0, 0);
        
        // Actions
        GridPane actionButtonsLayout = new GridPane();
        
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
        
        actionButtonsLayout.add(suggestButton, 0, 0, 2, 1);
        actionButtonsLayout.add(accuseButton, 0, 1, 2, 1);
        actionButtonsLayout.add(rollDiceButton, 0, 2);
        actionButtonsLayout.add(endTurnButton, 1, 2);
        
        rightPane.getChildren().addAll(playerCardsLayout, actionButtonsLayout);
        
        // Temp return button
        Button returnButton = new Button("Back");
        returnButton.setOnAction(e -> {
            gameStage.close();
            
        });
        
        main.setLeft(notepadLayout);
        main.setRight(rightPane);
        Scene scene = new Scene(main);
        gameStage.setScene(scene);
        gameStage.show();
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clueclient;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author hungb
 */
public class ClueClient extends Application {
    
    private Stage stage;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Clue");
        
        stage = primaryStage;
        
        VBox menuOptions = new VBox();
        menuOptions.setPadding(new Insets(10));
        menuOptions.setSpacing(8);
        menuOptions.setAlignment(Pos.CENTER);

        addUIControls(menuOptions);

        Scene scene = new Scene(menuOptions, 1280, 720);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void addUIControls(VBox menuOptions) {
        // Game Title
        Label gameTitle = new Label("Clue");

        // Create Game Button
        Button createGame = new Button("Create Game");

        // Join Game Button
        Button joinGame = new Button("Join Game");
        joinGame.setOnAction(e -> joinGameScene(stage));

        // Settings Button
        Button settings = new Button("Settings");

         menuOptions.getChildren().addAll(gameTitle, createGame, joinGame, settings);
    }
    
    private void joinGameScene(Stage stage) {
        GridPane joinGameOptions = new GridPane();
        joinGameOptions.setAlignment(Pos.CENTER);

        // Scene Title
        Label joinGameTitle = new Label("Join Game");

        // IP Address
        Label ipAddressTitle = new Label("IP Address");
        TextField ipAddress = new TextField();

        // Port
        Label portTitle = new Label("Port");
        TextField port = new TextField();
        
        // join Game Button
        Button joinGameButton = new Button("Join Game");

        joinGameOptions.add(joinGameTitle, 0,0);
        joinGameOptions.add(ipAddressTitle, 0, 1);
        joinGameOptions.add(ipAddress, 1, 1);
        joinGameOptions.add(portTitle, 0, 2);
        joinGameOptions.add(port, 1, 2);
        joinGameOptions.add(joinGameButton, 0, 3);

        Scene scene = new Scene(joinGameOptions, 1280, 720);
        stage.setScene(scene);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

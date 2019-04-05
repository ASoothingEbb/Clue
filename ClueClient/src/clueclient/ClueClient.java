/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clueclient;

import java.util.HashMap;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
    
    private Scene prevScene;
    private Stage stage;
    
    private int width;
    private int height;
    private String currentWindowMode;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Clue");
        
        width = 1280;
        height = 720;
        
        stage = primaryStage;
        
        VBox menuOptions = new VBox();
        menuOptions.setPadding(new Insets(10));
        menuOptions.setSpacing(8);
        menuOptions.setAlignment(Pos.CENTER);

        addUIControls(menuOptions);

        Scene scene = new Scene(menuOptions, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void addUIControls(VBox menuOptions) {
        // Game Title
        Label gameTitle = new Label("Clue");

        // Create Game Button
        Button createGame = new Button("Create Game");
        createGame.setOnAction(e -> createGameScene(stage));

        // Join Game Button
        Button joinGame = new Button("Join Game");
        joinGame.setOnAction(e -> joinGameScene(stage));

        // Settings Button
        Button settings = new Button("Settings");
        settings.setOnAction(e -> settingScene(stage));

         menuOptions.getChildren().addAll(gameTitle, createGame, joinGame, settings);
    }
    
    private void createGameScene(Stage stage) {
        GridPane createGameOptions = new GridPane();
        
        // Scene Title
        Label createGameTitle = new Label("Create Game");

        // Number of Players
        final int numberOfPlayers = 6;
        
        Label numberOfPlayersLabel = new Label("Number of Players");
        
        Slider playersNum = new Slider(1,numberOfPlayers,6);
        
        playersNum.setMajorTickUnit(1);
        playersNum.setMinorTickCount(0);
        playersNum.setShowTickMarks(true);
        playersNum.setShowTickLabels(true);
        playersNum.setSnapToTicks(true);

        // Number of AI Players
        Label numberOfAIPlayersLabel = new Label("AI Players");
        CheckBox aiPlayersNum = new CheckBox();

        // Return to menu
        Button returnButton = new Button("Back");
        returnButton.setOnAction(e -> stage.setScene(prevScene));
        
        createGameOptions.add(createGameTitle, 0, 0);
        createGameOptions.add(numberOfPlayersLabel, 0, 1);
        createGameOptions.add(playersNum, 1, 1);
        createGameOptions.add(numberOfAIPlayersLabel, 0, 2);
        createGameOptions.add(aiPlayersNum, 1, 2);
        createGameOptions.add(returnButton, 0, 3);
        
        Scene scene = new Scene(createGameOptions, width, height);
        prevScene = stage.getScene();
        stage.setScene(scene);
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

        Scene scene = new Scene(joinGameOptions, width, height);
        prevScene = stage.getScene();
        stage.setScene(scene);
    }
    
    private void settingScene(Stage stage) {
        GridPane settingsOptions = new GridPane();
        
        // Display Settings
        Label displaySettingsLabel = new Label("Display");
        
        // Resolutions
        Label gameResolutionLabel = new Label("Resolution");
        
        ChoiceBox gameResolution = new ChoiceBox();
        gameResolution.setValue(width+"x"+height);
        gameResolution.getItems().addAll("1280x720", "1920x1080", "2560x1440");

        // TODO: make it refresh changes
        gameResolution.getSelectionModel().selectedItemProperty().addListener((v, name, value) -> {
            String[] resolution = value.toString().split("x");
            width =Integer.parseInt(resolution[0]);
            height = Integer.parseInt(resolution[1]);
        });
        
        // Window Mode
        Label windowModeLabel = new Label("Window Mode");
        
        ChoiceBox windowMode = new ChoiceBox();
        windowMode.setValue("Windowed");
        windowMode.getItems().addAll("Windowed", "Borderless", "Fullscreen");
        
        windowMode.getSelectionModel().selectedItemProperty().addListener((v, name, value) -> {
            currentWindowMode = value.toString();
        }); 
        
        // Audio Settings
        Label AudioSettingsLabel = new Label("Audio");
        
        // Return to menu
        Button returnButton = new Button("Back");
        returnButton.setOnAction(e -> stage.setScene(prevScene));   
        
        settingsOptions.add(displaySettingsLabel, 0, 0);
        settingsOptions.add(gameResolutionLabel, 0, 1);
        settingsOptions.add(gameResolution, 1, 1);
        settingsOptions.add(windowModeLabel, 0, 2);
        settingsOptions.add(windowMode, 1, 2);
        settingsOptions.add(returnButton, 0, 3);
                
        Scene scene = new Scene(settingsOptions, width, height);
        prevScene = stage.getScene();
        stage.setScene(scene);
   }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

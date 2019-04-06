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
        Button createGame = new Button("Play");
        createGame.setOnAction(e -> startGameScene(stage));

        // Join Game Button
        Button joinGame = new Button("How to play");
        joinGame.setOnAction(e -> howToPlayScene(stage));

        // Settings Button
        Button settings = new Button("Settings");
        settings.setOnAction(e -> settingScene(stage));

         menuOptions.getChildren().addAll(gameTitle, createGame, joinGame, settings);
    }
    
    private void startGameScene(Stage stage) {
        GridPane startGameOptions = new GridPane();
        
        // Scene Title
        Label startGameTitle = new Label("Create Game");

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
        Label aiPlayersLabel = new Label("AI Players");
        CheckBox aiPlayers = new CheckBox();
        
        // Create Game Instance
        
        gameInstance game = new gameInstance();
        
        Button newGameButton = new Button("Start Game");
        newGameButton.setOnAction((ActionEvent e) -> {
            stage.hide();
            game.startGame((int) playersNum.getValue(), aiPlayers.isSelected(), width, height);
        });

        // Return to menu
        Button returnButton = new Button("Back");
        returnButton.setOnAction(e -> stage.setScene(prevScene));
        
        startGameOptions.add(startGameTitle, 0, 0);
        startGameOptions.add(numberOfPlayersLabel, 0, 1);
        startGameOptions.add(playersNum, 1, 1);
        startGameOptions.add(aiPlayersLabel, 0, 2);
        startGameOptions.add(aiPlayers, 1, 2);
        startGameOptions.add(newGameButton, 0, 3);
        startGameOptions.add(returnButton, 0, 4);
        
        Scene scene = new Scene(startGameOptions, width, height);
        prevScene = stage.getScene();
        stage.setScene(scene);
    }
    
    private void howToPlayScene(Stage stage) {
        GridPane joinGameOptions = new GridPane();
        joinGameOptions.setAlignment(Pos.CENTER);

        // Scene Title
        Label howToPlayTitle = new Label("How to Play");

        joinGameOptions.add(howToPlayTitle, 0,0);

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
        Label audioSettingsLabel = new Label("Audio");
        
        Label masterVolumeLabel = new Label("Master Volume");
        
        Label musicVolumeLabel = new Label("Music Volume");
        
        Label effectVoumeLabel = new Label("Sound Effect Volume");
        
        
        // Gameplay Settings
        Label gameplaySettingsLabel = new Label("Gameplay");
        
        
        
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

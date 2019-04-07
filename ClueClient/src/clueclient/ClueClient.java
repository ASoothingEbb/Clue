/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clueclient;

import java.util.HashMap;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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

    // Fonts
    private Font avenirTitle = Font.loadFont(getClass().getResourceAsStream("assets/fonts/Avenir-Book.ttf"), 20);
    private Font avenirNormal = Font.loadFont(getClass().getResourceAsStream("assets/fonts/Avenir-Book.ttf"), 12);   
    
    // BackgroundFill
    private Background blackFill = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    
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
        
        Image bg = new Image(getClass().getResource("assets/cluedoMansion.jpg").toExternalForm());
        
        //BackgroundImage background = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        menuOptions.setBackground(blackFill);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void addUIControls(VBox menuOptions) {
        // Game Title
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("assets/fonts/ringbearer.ttf"), 80);
        Label gameTitle = new Label("cluE");
        gameTitle.setFont(titleFont);
        gameTitle.setTextFill(Color.WHITE);
        
        // Test new Button design
        MenuItem createGameButton = new MenuItem("Play", avenirTitle);
        createGameButton.setOnMouseClicked(e -> startGameScene(stage));
        
        MenuItem howToPlayButton = new MenuItem("How To Play", avenirTitle);
        howToPlayButton.setOnMouseClicked(e -> howToPlayScene(stage));
        
        MenuItem settingsButton = new MenuItem("Settings", avenirTitle);
        settingsButton.setOnMouseClicked(e -> settingScene(stage));

         menuOptions.getChildren().addAll(gameTitle, createGameButton, howToPlayButton, settingsButton);
    }
    
    private void startGameScene(Stage stage) {
        BorderPane alignmentPane = new BorderPane();
        alignmentPane.setBackground(blackFill);

        GridPane startGameOptions = new GridPane();
        startGameOptions.setAlignment(Pos.CENTER);
        startGameOptions.setBackground(blackFill);
        
        alignmentPane.setCenter(startGameOptions);
        
        // Scene Title
        Label startGameTitle = new Label("Create Game");
        startGameTitle.setFont(avenirTitle);
        startGameTitle.setTextFill(Color.WHITE);

        // Number of Players
        final int numberOfPlayers = 6;
        
        Label numberOfPlayersLabel = new Label("Number of Players");
        numberOfPlayersLabel.setFont(avenirNormal);
        numberOfPlayersLabel.setTextFill(Color.WHITE);
        
        Slider playersNum = new Slider(1,numberOfPlayers,6);
        
        playersNum.setMajorTickUnit(1);
        playersNum.setMinorTickCount(0);
        playersNum.setShowTickMarks(true);
        playersNum.setShowTickLabels(true);
        playersNum.setSnapToTicks(true);

        // Number of AI Players
        Label aiPlayersLabel = new Label("AI Players");
        aiPlayersLabel.setFont(avenirNormal);
        aiPlayersLabel.setTextFill(Color.WHITE);
        
        CheckBox aiPlayers = new CheckBox();
        
        // Create Game Instance
        gameInstance game = new gameInstance();
        
        MenuItem startGameButton = new MenuItem("Start Game", avenirTitle);
        startGameButton.setOnMouseClicked(e -> {
            stage.hide();
            game.startGame((int) playersNum.getValue(), aiPlayers.isSelected(), width, height);
        });

        // Return to menu
        MenuItem returnButton = new MenuItem("Back", avenirTitle);
        returnButton.setOnMouseClicked(e -> stage.setScene(prevScene));
        
        startGameOptions.add(startGameTitle, 0, 0, 2, 1);
        GridPane.setHalignment(startGameTitle, HPos.CENTER);
        
        startGameOptions.add(numberOfPlayersLabel, 0, 1);
        GridPane.setHalignment(numberOfPlayersLabel, HPos.CENTER);
        
        startGameOptions.add(playersNum, 1, 1);
        
        startGameOptions.add(aiPlayersLabel, 0, 2);
        GridPane.setHalignment(aiPlayersLabel, HPos.CENTER);
        
        startGameOptions.add(aiPlayers, 1, 2);
        GridPane.setHalignment(aiPlayers, HPos.CENTER);
        
        startGameOptions.add(startGameButton, 0, 3, 2, 1);
        GridPane.setHalignment(startGameButton, HPos.CENTER);
        
        alignmentPane.setBottom(returnButton);
        BorderPane.setMargin(returnButton, new Insets(0,0,10,20));
        
        Scene scene = new Scene(alignmentPane, width, height);
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

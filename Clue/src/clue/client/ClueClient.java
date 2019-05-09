/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import clue.GameController;
import clue.GameController.TooManyPlayersException;
import clue.MissingRoomDuringCreationException;
import clue.NotEnoughPlayersException;
import clue.action.AccuseAction;
import clue.action.Action;
import clue.action.ShowCardAction;
import clue.action.SuggestAction;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Hung Bui Quang
 */
public class ClueClient extends Application {
    
    private Scene prevScene;
    private Stage stage;
    
    Sound backgroundMusic;
    
    private Image volumeOn;
    private Image volumeOff;
    
    private int width;
    private int height;
    
    private HashMap<String, String> textureMap;
    
    private int numberOfPlayers;
    private int numberOfAIs;
    
    // Fonts
    private Font avenirLarge;
    private Font avenirTitle;
    private Font avenirNormal;
    
    // BackgroundFill
    private final Background greenFill = new Background(new BackgroundFill(Color.rgb(7, 80, 2), CornerRadii.EMPTY, Insets.EMPTY));
    
    
    /**
     * Starts the client
     * 
     * @param primaryStage the root stage that is passed by launch called in main
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Clue");
        
        width = 1280;
        height = 720;
        
        stage = primaryStage;
        
        try {
            volumeOn = new Image(new FileInputStream(new File("resources/Sprites/volumeOn.png")), 50, 50, false, false);
            volumeOff = new Image(new FileInputStream(new File("resources/Sprites/volumeOff.png")), 50, 50, false, false);
        } catch(FileNotFoundException ex) {
            
        }

        backgroundMusic = new Sound("resources/Music/backgroundMusic.wav");
        backgroundMusic.loop();
        backgroundMusic.setVolume(0.6f);
        
        VBox menuOptions = new VBox();
        menuOptions.setPadding(new Insets(10));
        menuOptions.setSpacing(8);
        menuOptions.setAlignment(Pos.CENTER);

        addUIControls(menuOptions);
       
        
        Scene scene = new Scene(menuOptions, width, height);
        
        //BackgroundImage background = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        menuOptions.setBackground(greenFill);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Creates the main scene and adds the nodes to the given VBox.
     * @param menuOptions 
     */
    private void addUIControls(VBox menuOptions) {
        // Game Title
        Font titleFont = new Font(80);
        avenirLarge = new Font(30);
        avenirTitle = new Font(20);
        avenirNormal = new Font(12);
        try {
            titleFont = Font.loadFont(new FileInputStream(new File("./resources/fonts/ringbearer.ttf")), 80);
            avenirLarge = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 30);
            avenirTitle = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 20);
            avenirNormal = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 15);
        } catch(FileNotFoundException e) {
            
        }
        
        Label gameTitle = new Label("cluE");
        gameTitle.setFont(titleFont);
        gameTitle.setTextFill(Color.WHITE);
        menuOptions.getChildren().add(gameTitle);
        
        // Test new Button design
        MenuItem createGameButton = new MenuItem("Play", avenirTitle);
        createGameButton.setOnMouseClicked(e -> startGameScene(stage));
        
        MenuItem AiVsAiButton = new MenuItem("AI vs AI", avenirTitle);
        AiVsAiButton.setOnMouseClicked(e -> startAIGameScene(stage));
        
        MenuItem howToPlayButton = new MenuItem("How To Play", avenirTitle);
        howToPlayButton.setOnMouseClicked(e -> howToPlayScene(stage));
        
        MenuItem boardCreator = new MenuItem("Board Editor", avenirTitle);
        boardCreator.setOnMouseClicked(e -> boardCreator(stage));
        
        MenuItem settingsButton = new MenuItem("Settings", avenirTitle);
        settingsButton.setOnMouseClicked(e -> settingScene(stage));

         menuOptions.getChildren().addAll(createGameButton, AiVsAiButton, howToPlayButton, boardCreator, settingsButton);
    }
    
    /**
     * Creates the game creation scene where the number of human and AI
     * players specified, and can be started.
     * @param stage 
     */
    
    private void boardCreator(Stage stage) {
        boardEditor editor = new boardEditor();
        stage.hide();
        editor.startEditor(stage);
    }
    
    private void startAIGameScene(Stage stage) {
        numberOfAIs = 2;
        BorderPane alignmentPane = new BorderPane();
        alignmentPane.setBackground(greenFill);
        
        GridPane AIStartGameOptions = new GridPane();
        AIStartGameOptions.setAlignment(Pos.CENTER);
        AIStartGameOptions.setBackground(greenFill);
        
        alignmentPane.setCenter(AIStartGameOptions);
        
        // Scene title
        Label startGameTitle = getLabel("Create AI vs AI game", avenirTitle);
        
        Label numberOfAILabel = getLabel("Number of AIs", avenirNormal);
        
        HBox AIs = new HBox();
        AIs.setAlignment(Pos.CENTER);
        
        MenuItem AIsNumber = new MenuItem("2", avenirTitle);
        AIsNumber.setActiveColor(Color.GREY);
        
        MenuItem minusAI = new MenuItem("-", avenirTitle);
        minusAI.setPadding(new Insets(0, 5, 0, 0));
        minusAI.setBackgroundColor(Color.rgb(7, 80, 2));
        minusAI.setMinSize(15, 15);
        minusAI.setOnMouseClicked(e -> {
            if (numberOfAIs > 2) {
                updateNumberOfAIs(false, AIsNumber);
            }
        });
        
        MenuItem addAI = new MenuItem("+", avenirTitle);
        addAI.setOnMouseClicked(e -> {
            if (numberOfAIs < 6) {
                updateNumberOfAIs(true, AIsNumber);
            }
        });
        
        AIs.getChildren().addAll(minusAI, AIsNumber, addAI);
        
        GameInstance game = new GameInstance();
        
        MenuItem startGameButton = new MenuItem("Start Game", avenirTitle);
        startGameButton.setOnMouseClicked(e -> {
            String doorFile = "resources/archersAvenueDoors.csv";
            String tileFile = "resources/archersAvenueTiles.csv";
            try {
                GameController gameController = new GameController(0, numberOfAIs, tileFile, doorFile);
                stage.hide();
                stage.setScene(prevScene);
                DisplayAIGameLog(stage, gameController);
            } catch(TooManyPlayersException | MissingRoomDuringCreationException | NoSuchRoomException | NoSuchTileException ex) {
                System.out.println("Ice Cream Machine BROKE");
            } catch(NotEnoughPlayersException ex) {
                Prompt playerPrompt = new Prompt("Not Enough Players");
                playerPrompt.setLabelTitle("Start Game Error");
                playerPrompt.showAndWait();    
            }
            
        });

        // Return to menu
        MenuItem returnButton = new MenuItem("Back", avenirTitle);
        returnButton.setOnMouseClicked(e -> {
            stage.setScene(prevScene);
            numberOfAIs = 0;
         });
        
        AIStartGameOptions.add(startGameTitle, 0, 0, 2, 1);
        GridPane.setHalignment(startGameTitle, HPos.CENTER);
        
        AIStartGameOptions.add(numberOfAILabel, 0, 1);
        GridPane.setHalignment(numberOfAILabel, HPos.CENTER);
        
        AIStartGameOptions.add(AIs, 1, 1);
        GridPane.setMargin(AIs, new Insets(0, 0, 0, 10));
        
        AIStartGameOptions.add(startGameButton, 0, 2, 2, 1);
        GridPane.setHalignment(startGameButton, HPos.CENTER);
        
        alignmentPane.setBottom(returnButton);
        BorderPane.setMargin(returnButton, new Insets(0,0,10,20));
        
        Scene scene = new Scene(alignmentPane, width, height);
        prevScene = stage.getScene();
        stage.setScene(scene);
    }
    
    private void DisplayAIGameLog(Stage stage, GameController gameController) {
        HashMap<String, String> CardNameMap = new HashMap<>();
        
        CardNameMap.put("character0", "Miss Scarlet");
        CardNameMap.put("character1", "Colonel Mustard");
        CardNameMap.put("character2", "Mrs White");
        CardNameMap.put("character3", "Reverend Green");
        CardNameMap.put("character4", "Mrs Peacock");
        CardNameMap.put("character5", "Professor Plum");
        
        CardNameMap.put("weapon0", "Candlestick");
        CardNameMap.put("weapon1", "Dagger");
        CardNameMap.put("weapon2", "Lead Pipe");
        CardNameMap.put("weapon3", "Revolver");
        CardNameMap.put("weapon4", "Rope");
        CardNameMap.put("weapon5", "Spanner");
        
        CardNameMap.put("room0", "Study");
        CardNameMap.put("room1", "Hall");
        CardNameMap.put("room2", "Lounge");
        CardNameMap.put("room3", "Library");
        CardNameMap.put("room4", "Billard Room");
        CardNameMap.put("room5", "Dining Room");
        CardNameMap.put("room6", "Conservatory");
        CardNameMap.put("room7", "Ball Room");
        CardNameMap.put("room8", "Kitchen");
        
        VBox gameLog = new VBox();
        gameLog.setPadding(new Insets(5, 10, 0, 10));
        gameLog.setBackground(greenFill);
        gameLog.setAlignment(Pos.CENTER);
        
        Label historyLabel = getLabel("History", avenirTitle); 
        
        TextArea history = new TextArea();
        history.setPrefRowCount(30);
        history.setPrefColumnCount(30);
        history.setWrapText(true);
        history.setFont(avenirNormal);
        history.setStyle("-fx-control-inner-background: #fff2ab;");
        history.setEditable(false);
        
        List<Action> log = gameController.getActionLog();

        for (Action action: log) {
            StringBuilder message = new StringBuilder();     
            switch (action.getActionType()) {
                case ACCUSATION:
                    int accuser = ((AccuseAction) action).getPlayer().getId();
                    final int[] cards = ((AccuseAction) action).getAccusationCards();
                    message.append(CardNameMap.get("character" + accuser));
                    message.append(" accused ");
                    message.append(CardNameMap.get("character" + cards[0]));
                    message.append(" of murder in the ");
                    message.append(CardNameMap.get("room" + cards[2]));
                    message.append(" using the ");
                    message.append(CardNameMap.get("weapon" + cards[1]));
                    message.append("\n");
                    history.appendText("--------------------\n");
                    history.appendText(message.toString());
                    break;
                case SHOWCARD:
                    int suggestee = ((ShowCardAction) action).getWhoShowedTheCard().getId();
                    int suggester = ((ShowCardAction) action).getPlayer().getId();
                    message.append(CardNameMap.get("character" + suggestee));
                    message.append(" showed a card to ");
                    message.append(CardNameMap.get("character" + suggester));
                    message.append("\n");
                    history.appendText("--------------------\n");
                    history.appendText(message.toString());
                    break;
                case SUGGEST:
                    SuggestAction suggestedAction = ((SuggestAction) action);
                    message.append(CardNameMap.get("character" + suggestedAction.getPlayer().getId()));
                    message.append(" suggested ");
                    message.append(CardNameMap.get("character" + suggestedAction.getPersonCard().getId()));
                    message.append(" of the murder in the ");
                    message.append(CardNameMap.get("room" + suggestedAction.getRoomCard().getId()));
                    message.append(" using the ");
                    message.append(CardNameMap.get("weapon" + suggestedAction.getWeaponCard().getId()));
                    message.append("\n");
                    history.appendText("--------------------\n");
                    history.appendText(message.toString());
                    break;
                case SHOWCARDS:
                    
                    break;
                default:
                    break;
            }
        }
        
        StringBuilder winner = new StringBuilder();
        int winningPlayer = gameController.getWinner();
        if (winningPlayer == -1) {
            winner.append("No one won");
            winner.append("\n");
        } else {
            winner.append(CardNameMap.get("Character" + winningPlayer));
            winner.append("accused correctly and won!!!");
            winner.append("\n");
        }
        history.appendText("--------------------\n");
        history.appendText(winner.toString());
        
        MenuItem backButton = new MenuItem("Back", avenirTitle);
        backButton.setOnMouseClicked(e -> stage.setScene(prevScene));
        
        gameLog.getChildren().addAll(historyLabel, history, backButton);
        
        Scene scene = new Scene(gameLog, width, height);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Creates the GameInstance and passes the necessary parameters.
     * @param stage parent stage
     */
    private void startGameScene(Stage stage) {
        numberOfPlayers = 1;
        
        BorderPane alignmentPane = new BorderPane();
        alignmentPane.setBackground(greenFill);

        GridPane startGameOptions = new GridPane();
        startGameOptions.setAlignment(Pos.CENTER);
        startGameOptions.setBackground(greenFill);
        
        alignmentPane.setCenter(startGameOptions);
        
        // Scene Title
        Label startGameTitle = getLabel("Create Game", avenirTitle);
        
        // Number of Players        
        Label numberOfPlayersLabel = getLabel("Number of Players", avenirNormal);
        
        HBox players = new HBox();
        players.setAlignment(Pos.CENTER);
        
        MenuItem playersNumber = new MenuItem(String.valueOf(numberOfPlayers), avenirTitle);
        playersNumber.setActiveColor(Color.GREY);
        
        //TextField playersNumber = getTextField(1, false);
        
        MenuItem minusPlayer = new MenuItem("-", avenirTitle);
        minusPlayer.setPadding(new Insets(0, 5, 0, 0));
        minusPlayer.setBackgroundColor(Color.rgb(7, 80, 2));
        minusPlayer.setMinSize(15, 15);
        minusPlayer.setOnMouseClicked(e -> {
            if (numberOfPlayers > 1) {
            updateNumberOfPlayers(false, playersNumber);
            }
        });
        
        MenuItem addPlayer = new MenuItem("+", avenirTitle);
        addPlayer.setOnMouseClicked(e -> {
            if (numberOfPlayers < 6) {
            updateNumberOfPlayers(true, playersNumber);
            }
        });
        
        players.getChildren().addAll(minusPlayer, playersNumber, addPlayer);
        
        Label aiPlayersLabel = getLabel("Number of AIs", avenirNormal);
        
        HBox AIs = new HBox();
        AIs.setAlignment(Pos.CENTER);
        
        MenuItem AIsNumber = new MenuItem("0", avenirTitle);
        AIsNumber.setActiveColor(Color.GREY);
        
        MenuItem minusAI = new MenuItem("-", avenirTitle);
        minusAI.setPadding(new Insets(0, 5, 0, 0));
        minusAI.setBackgroundColor(Color.rgb(7, 80, 2));
        minusAI.setMinSize(15, 15);
        minusAI.setOnMouseClicked(e -> {
            if (numberOfAIs > 0) {
                updateNumberOfAIs(false, AIsNumber);
            }
        });
        
        MenuItem addAI = new MenuItem("+", avenirTitle);
        addAI.setOnMouseClicked(e -> {
            if (numberOfAIs < (6 - numberOfPlayers)) {
                updateNumberOfAIs(true, AIsNumber);
            }
        });
        
        AIs.getChildren().addAll(minusAI, AIsNumber, addAI);
        
        Label mapLabel = getLabel("Map", avenirNormal);
        
        File directory = new File("./Maps");
        String[] directories = directory.list((File current, String name) -> new File(current, name).isDirectory());
        ComboBox maps = new ComboBox();
        maps.setItems(FXCollections.observableArrayList(directories));
        maps.setVisibleRowCount(8);
        maps.setValue("archersAvenue");
            
        GameInstance game = new GameInstance();
        
        MenuItem startGameButton = new MenuItem("Start Game", avenirTitle);
        startGameButton.setOnMouseClicked(e -> {
            String mapDirPath = "Maps/" + maps.getValue();
            File mapDirectory = new File(mapDirPath);
            String[] mapFiles = mapDirectory.list();
            String doorFile;
            String tileFile;
            Prompt fileErrorPrompt = new Prompt("");
            if (mapFiles.length == 2) {
                if (mapFiles[0].endsWith("Doors.csv") && mapFiles[1].endsWith("Tiles.csv")) {
                    doorFile = mapDirPath + "/" + mapFiles[0];
                    tileFile = mapDirPath + "/" + mapFiles[1];
                } else if (mapFiles[0].endsWith("Tiles.csv") && mapFiles[1].endsWith("Doors.csv")) {
                    doorFile = mapDirPath + "/" + mapFiles[1];
                    tileFile = mapDirPath + "/" + mapFiles[0];
                } else {
                    fileErrorPrompt.setMessage("Door and Tile csv files not found. Loading default map.");
                    fileErrorPrompt.showAndWait();
                    doorFile = "resources/archersAvenueDoors.csv";
                    tileFile = "resources/archersAvenueTiles.csv";
                }
            } else {
                fileErrorPrompt.setMessage("Error loading map too many file found. Loading default map.");
                fileErrorPrompt.showAndWait();
                doorFile = "resources/archersAvenueDoors.csv";
                tileFile = "resources/archersAvenueTiles.csv";
            }

            try {
                GameController gameController = new GameController(numberOfPlayers, numberOfAIs, tileFile, doorFile);
                game.startGame(gameController, stage, tileFile);
                stage.hide();
                stage.setScene(prevScene);
            } catch(TooManyPlayersException | MissingRoomDuringCreationException | NoSuchRoomException | NoSuchTileException ex) {
                System.out.println("Ice Cream Machine BROKE");
            } catch(NotEnoughPlayersException ex) {
                Prompt playerPrompt = new Prompt("Not Enough Players");
                playerPrompt.setLabelTitle("Start Game Error");
                playerPrompt.showAndWait();    
            }
            
        });

        // Return to menu
        MenuItem returnButton = new MenuItem("Back", avenirTitle);
        returnButton.setOnMouseClicked(e -> stage.setScene(prevScene));
        
        startGameOptions.add(startGameTitle, 0, 0, 2, 1);
        GridPane.setHalignment(startGameTitle, HPos.CENTER);
        
        startGameOptions.add(numberOfPlayersLabel, 0, 1);
        GridPane.setHalignment(numberOfPlayersLabel, HPos.CENTER);
        
        startGameOptions.add(players, 1, 1);
        GridPane.setMargin(players, new Insets(0, 0, 0, 10));
        
        startGameOptions.add(aiPlayersLabel, 0, 2);
        GridPane.setHalignment(aiPlayersLabel, HPos.CENTER);
        
        startGameOptions.add(AIs, 1, 2);
        GridPane.setMargin(AIs, new Insets(0, 0, 0, 10));
        
        startGameOptions.add(mapLabel, 0, 3);
        GridPane.setHalignment(mapLabel, HPos.CENTER);
        
        startGameOptions.add(maps, 1, 3);
        GridPane.setMargin(maps, new Insets(0, 0, 0, 10));
        
        startGameOptions.add(startGameButton, 0, 4, 2, 1);
        GridPane.setHalignment(startGameButton, HPos.CENTER);
        
        alignmentPane.setBottom(returnButton);
        BorderPane.setMargin(returnButton, new Insets(0,0,10,20));
        
        Scene scene = new Scene(alignmentPane, width, height);
        prevScene = stage.getScene();
        stage.setScene(scene);
    }
    
    /**
     * Updates the number of AIs counter
     * @param increase Increase or decrease counter
     * @param label Label to update
     */
    private void updateNumberOfAIs(boolean increase, MenuItem label) {
        if (increase) {
            numberOfAIs++;
        } else {
            numberOfAIs--;
        }
        label.setText(String.valueOf(numberOfAIs));
    }
    
    /**
     * Updates the number of Players counter
     * @param increase Increase or decrease counter
     * @param label Label to update
     */
    private void updateNumberOfPlayers(boolean increase, MenuItem label) {
        if (increase) {
            numberOfPlayers++;
        } else {
            numberOfPlayers--;
        }
        label.setText(String.valueOf(numberOfPlayers));
    }
    
    /**
     * Creates the HowToPlay scene. Before setting the stage to the HowToPlay
     * scene, the prevScene has to be set to the currentScene which is used to
     * return to home Scene.
     * @param stage parent stage
     */
    private void howToPlayScene(Stage stage) {
        VBox howToPlayLayout = new VBox();
        howToPlayLayout.setPadding(new Insets(50));
        howToPlayLayout.setAlignment(Pos.CENTER);
        howToPlayLayout.setBackground(greenFill);
        
        Label howToPlayTitle = getLabel("How To Play", avenirTitle);
        
        StringBuilder HowToPlayText = new StringBuilder();
        HowToPlayText.append("Rules of Clue:\n");
        HowToPlayText.append("The character Miss Scarlet (Player 1) takes the first turn.\n");
        HowToPlayText.append("Players can move only horizontally or vertically, never diagonally.\n");
        HowToPlayText.append("Players can't move through a yellow tile occupied by another player, but multiple players can be in the same room.\n");
        HowToPlayText.append("Players can't enter a space or doorway they have already entered on the same turn.\n");
        HowToPlayText.append("Players can move through a doorway to enter a room, but this ends their movement (can still suggest/accuse).\n");
        HowToPlayText.append("Players who start their turn in a room with a secret passage can use the secret passage instead of moving. This will put their character in another room across the board, ending their movement (can still suggest/accuse).\n");
        HowToPlayText.append("Players can only suggest/accuse inside a room.\n");
        HowToPlayText.append("Players can suggest their own cards.\n");
        HowToPlayText.append("Players can suggest and then accuse on the same turn.\n");
        HowToPlayText.append("Wrong accusations means the player who accused is out of the game.\n");
        HowToPlayText.append("If your character is suggested by another player, you will be moved the room suggested.\n");
        HowToPlayText.append("If your character lands on an intrigue tile, the special bonus must be used on that turn.\n");
        
        Label HowToPlay = getLabel(HowToPlayText.toString(), avenirNormal);
        HowToPlay.setWrapText(true);
        
        MenuItem returnButton = new MenuItem("Back", avenirTitle);
        returnButton.setOnMouseClicked(e -> stage.setScene(prevScene));
        
        howToPlayLayout.getChildren().addAll(howToPlayTitle, HowToPlay, returnButton);
        
        Scene scene = new Scene(howToPlayLayout, width, height);
        prevScene = stage.getScene();
        stage.setScene(scene);
    }
    
    /**
     * Creates the settings 
     * @param stage parent stage
     */
    private void settingScene(Stage stage) {        
        BorderPane settingsLayout = new BorderPane();
        settingsLayout.setBackground(greenFill);
        
        BorderPane leftLayout = new BorderPane();
        
        VBox settingsOptions = new VBox();
        
        GridPane textureSettings = new GridPane();
        textureSettings.setBackground(greenFill);
        //textureSettings.setBackground(new Background(new BackgroundFill(Color.rgb(7, 80, 2), CornerRadii.EMPTY, Insets.EMPTY)));
        textureSettings.setPadding(new Insets(20,30,20,30));
        GridPane audioSettings = new GridPane();
        audioSettings.setBackground(greenFill);
        //audioSettings.setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        audioSettings.setPadding(new Insets(20,30,20,30));
        GridPane creditsPane = new GridPane();
        creditsPane.setBackground(greenFill);
        //creditsPane.setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        creditsPane.setPadding(new Insets(20,30,20,30));
        
        
        Label settingsTitle = getLabel("Settings", avenirLarge);
        
        MenuItem texturesButton = new MenuItem("Textures", avenirTitle);
        texturesButton.setOnMouseClicked(e -> {
            settingsLayout.setCenter(textureSettings);
            BorderPane.setMargin(textureSettings, new Insets(0,50,50,0));
        });
        
        MenuItem audioButton = new MenuItem("Audio", avenirTitle);
        audioButton.setOnMouseClicked(e -> {
            settingsLayout.setCenter(audioSettings);
            BorderPane.setMargin(audioSettings, new Insets(0,50,50,0));
        });
        
        MenuItem creditsButton = new MenuItem("Credits", avenirTitle);
        creditsButton.setOnMouseClicked(e -> {
            settingsLayout.setCenter(creditsPane);
            BorderPane.setMargin(creditsPane, new Insets(0,50,50,0));
        });
        
        MenuItem returnButton = new MenuItem("Back", avenirTitle);
        returnButton.setOnMouseClicked(e -> stage.setScene(prevScene));
        
        // Textures
        
        textureSettingsScene(textureSettings);
        
        // Audio
        
        audioSettingsScene(audioSettings);
        
        // Credits
        
        creditsScene(creditsPane);
        
        // adding nodes to panes
        
        settingsOptions.getChildren().addAll(texturesButton, audioButton, creditsButton);
        settingsOptions.setPadding(new Insets(0,10,0,20));
        
        settingsLayout.setLeft(leftLayout);
        
        settingsLayout.setTop(settingsTitle);
        BorderPane.setAlignment(settingsTitle, Pos.CENTER_LEFT);
        BorderPane.setMargin(settingsTitle, new Insets(10,0,10,20));
        
        leftLayout.setTop(settingsOptions);
        leftLayout.setBottom(returnButton);
        BorderPane.setMargin(returnButton, new Insets(0,0,10,20));
        
        Scene scene = new Scene(settingsLayout, width, height);
        prevScene = stage.getScene();
        stage.setScene(scene);
    }
    
    /**
     * Creates the JavaFX component which will be used to display the credit information in a scene
     * 
     * @param layout
     * @return 
     */
    private GridPane creditsScene(GridPane layout) {      
        LinkedHashMap <String, String> credits = new LinkedHashMap<>();
        credits.put("Produced By", "Big Sage Productions");
        credits.put("Project Manager", "Jak");
        credits.put("AI", "Jose");
        credits.put("Programmer", "Steve");
        credits.put(" ", "Steve");
        credits.put("  ", "Jak");
        credits.put("   ", "Jose");
        credits.put("Artist", "Hung");
        credits.put("UX Design","Hung");
        credits.put("Background Music", "Investigations by Kevin Macleod");
        credits.put("Board & Card Assets", "Hasbro Cluedo Board Game(2000)");
        credits.put("Special Thanks to", "Big Sage");
        
        int index = 1;
        for(Map.Entry<String, String> entry: credits.entrySet()) {
            Label title = getLabel(entry.getKey(), avenirTitle);
            Label credit = getLabel(entry.getValue(), avenirTitle);
            
            layout.add(title, 0, index);
            layout.add(credit, 1, index);
            GridPane.setMargin(credit, new Insets(0, 0, 0, 50));
            index++;
        }
        
        layout.setAlignment(Pos.CENTER);
        
        return layout;
    }
    
    /**
     * 
     * @param layout
     * @return 
     */
    private GridPane audioSettingsScene(GridPane layout) {
        Label backgroundMusicLabel = getLabel("Background Music Volume", avenirTitle);
        Label toggleVolume = new Label("",new ImageView(volumeOn));
       
        
        toggleVolume.setOnMouseClicked(e -> backgroundMusic.toggleSound());
        
        Slider backgroundMusicVolume = new Slider(0, 100, 100);
        backgroundMusicVolume.setMaxWidth(1000);
        backgroundMusicVolume.setBlockIncrement(10);
        
        Label backgroundMusicVolumeShow = getLabel("", avenirTitle);
        backgroundMusicVolumeShow.setText("60");
        backgroundMusicVolume.setValue(60);
                
        backgroundMusicVolume.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                backgroundMusicVolumeShow.setText(String.valueOf((int) backgroundMusicVolume.getValue()));
                backgroundMusic.setVolume((float) ((float)backgroundMusicVolume.getValue()*0.01));
            }
        });
        
        layout.add(backgroundMusicLabel, 0, 0);
        GridPane.setMargin(backgroundMusicLabel, new Insets(0, 10, 0, 0));
        layout.add(backgroundMusicVolume, 1, 0);
        layout.add(backgroundMusicVolumeShow, 2, 0);
        layout.add(toggleVolume, 3, 0);
        GridPane.setMargin(backgroundMusicVolumeShow, new Insets(0, 0, 0, 10));
        
        return layout;
    }
    
    /**
     * Creates the settings scene and elements
     * @param layout layout to add the JavaFX nodes to
     * @return layout with the texture settings JavaFX nodes
     */
    private GridPane textureSettingsScene(GridPane layout) {
        textureMap = new HashMap<>();
        
        ExtensionFilter imageFormats = new ExtensionFilter("Image files", "*.jpg", "*.png", "*.jpeg");
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFormats);
        fileChooser.setTitle("Select");

        for (int i=1; i < 7; i++) {
            Label character = getLabel("Character " + i, avenirTitle);
            TextField characterName = getTextField(10, true);
            characterName.setPromptText("Name");
            
            Label characterFile = getLabel("File", avenirTitle);
            TextField characterFilePath = getTextField(40, false);
            
            final String propertiesCharacterName = "character"+(i-1)+"Name";
            final String propertiesCharacter = "character"+(i-1)+"Texture";
            
            MenuItem selectCharacterFile = new MenuItem("Choose", avenirTitle);
            selectCharacterFile.setOnMouseClicked(e -> {
                File characterTexture = fileChooser.showOpenDialog(stage);
                characterFilePath.setText(characterTexture.getAbsolutePath());
                textureMap.put(propertiesCharacter,characterTexture.getAbsolutePath());
                textureMap.put(propertiesCharacterName, characterName.getText());
            });
            layout.add(character, 0, i);
            GridPane.setMargin(character, new Insets(0, 10, 0, 0));
            layout.add(characterName, 1, i);
            layout.add(characterFile, 2, i);
            GridPane.setMargin(characterFile, new Insets(0, 10, 0, 10));
            layout.add(characterFilePath, 3, i);
            layout.add(selectCharacterFile, 4, i);
            GridPane.setMargin(selectCharacterFile, new Insets(0, 0, 0, 10));
        }
        
        for (int i=1; i < 7; i++) {
            Label weapon = getLabel("Weapon " + i, avenirTitle);
            TextField weaponName = getTextField(10, true);
            weaponName.setPromptText("Name");
            
            Label weaponFile = getLabel("File", avenirTitle);
            TextField weaponFilePath = getTextField(40, false);
            
            final String propertiesWeapon = "weapon"+(i-1)+"Texture";
            final String propertiesWeaponName = "weapon"+(i-1)+"Name";
            
            MenuItem selectWeaponFile = new MenuItem("Choose", avenirTitle);
            selectWeaponFile.setOnMouseClicked(e -> {
                File weaponTexture = fileChooser.showOpenDialog(stage);
                weaponFilePath.setText(weaponTexture.getAbsolutePath());
                textureMap.put(propertiesWeapon,weaponTexture.getAbsolutePath());
                textureMap.put(propertiesWeaponName, weaponName.getText());
            });
            
            layout.add(weapon, 0, i + 6);
            GridPane.setMargin(weapon, new Insets(0, 10, 0, 0));
            layout.add(weaponName, 1, i + 6);
            layout.add(weaponFile, 2, i + 6);
            GridPane.setMargin(weaponFile, new Insets(0, 10, 0, 10));
            layout.add(weaponFilePath, 3, i + 6);
            layout.add(selectWeaponFile, 4, i + 6);
            GridPane.setMargin(selectWeaponFile, new Insets(0, 0, 0, 10));
        }
        
        Label savedStatus = new Label("changes not saved");
        savedStatus.setTextFill(Color.LIGHTGREY);
        savedStatus.setFont(avenirNormal);
        MenuItem applyChanges = new MenuItem("Apply", avenirTitle);
        applyChanges.setOnMouseClicked(e -> {
            layout.add(savedStatus, 3, 14);
            if (saveProperties()) {
                savedStatus.setText("changes Saved");
            } else {
                savedStatus.setText("Unable to save changes");
            }
        });
        
        GridPane.setHalignment(savedStatus, HPos.RIGHT);
        layout.add(applyChanges, 4, 14);
        GridPane.setMargin(applyChanges, new Insets(0, 0, 0, 15));
        
        return layout;
    }
    
    /**
     * Creates and saves the settings to a properties file
     * @return if the file was saved successfully or not
     */
    private boolean saveProperties() {
        try (OutputStream output = new FileOutputStream("./resources/config.properties")) {
            Properties prop = new Properties();
            
            textureMap.entrySet().forEach((entry) -> {
                prop.setProperty(entry.getKey(), entry.getValue());
            });
            
            prop.store(output, null);
            System.out.println(prop);
            return true;
        } catch(IOException io) {
            return false;
        }
    }
    
    /**
     * Returns a TextField with specified settings, the column size and can the
     * content be edited.
     * @param columnCount
     * @param editable
     * @return TextField with the specified columnCount and editable state set.
     */
    private TextField getTextField(int columnCount, boolean editable) {
        TextField filePath = new TextField();
        filePath.setPrefColumnCount(columnCount);
        filePath.setEditable(editable);
        return filePath;        
    }
    
    /**
     * Returns a Label with the given text and font.
     * @param text
     * @param font
     * @return Label of the given text in the given font
     */
    private Label getLabel(String text, Font font) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(font);
        return label;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import clue.GameController;
import clue.action.Action;
import clue.action.UnknownActionException;
import clue.player.Player;
import clue.tile.NoSuchRoomException;
import clue.tile.Room;
import clue.tile.TileOccupiedException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author hungb
 */
public class gameInstance {
    
    private static final int TILE_SIZE = 38;
   
    private StackPane[][] board;
    private String notes;
    
    private PlayerSprite currentPlayer;
    private IntegerProperty remainingMoves;
    private int currentRoom;
    private boolean rolled;
    
    private GameController gameInterface;
    private Stage gameStage;
    private Scene uiScene;
    private Scene curtainScene;
   
    private FadeTransition uiToCurtain;
    
    private PlayerSprite[] playerSprites;
    
    private HashMap<String, String> ImagePathMap = new HashMap<>();
    private HashMap<String, String> CardNameMap = new HashMap<>();
    
    private String boardTilePath;
    
    private GridPane cardsDisplay;
            
    private Font avenirLarge;
    private Font avenirTitle;
    private Font avenirText;
    private final Background blackFill = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background greenFill = new Background(new BackgroundFill(Color.rgb(7, 80, 2), CornerRadii.EMPTY, Insets.EMPTY));
    
    private StackPane createBoard() {
        StackPane root = new StackPane();
        root.setPadding(new Insets(10, 5, 5, 0));
                
        GridPane boardPane = new GridPane();
        boardPane.setGridLinesVisible(true);
        
        //try {
        //    Image boardImage = new Image(new FileInputStream(ImagePathMap.get("board")));
        //    boardPane.setBackground(new Background(new BackgroundImage(boardImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        //} catch(IOException ex) {
        //    System.out.println("Failed to load board texture");
        //}
        
        int boardHeight = gameInterface.getBoardHeight();
        int boardWidth = gameInterface.getBoardWidth();
        board = new StackPane[boardHeight][boardWidth];
        
        
        String line = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(boardTilePath))) {
            int y = 0;
            while ((line = br.readLine()) != null && y < boardHeight) {
                String[] tiles = line.split(",");
                int x = 0;
                for (int i=0; i < boardWidth; i++) {
                    String tile = tiles[i];
                    String cell = tile.replaceAll("[^0-9A-Z-]+", "");
                    StackPane tilePane = new StackPane();
                    Tile tileSprite = new Tile(TILE_SIZE);
                    tileSprite.setStyle("-fx-border-width: 1px 1px 1px 1px; -fx-border-style: solid; -fx-border-color: black;");
                    final int coordX = x;
                    final int coordY = y;
                    
                    tilePane.setOnMouseClicked(e -> {
                        try {
                            if (gameInterface.move(coordX, coordY)) {
                                currentPlayer.move(gameInterface.getPlayer().getDrawX(), gameInterface.getPlayer().getDrawY(), board, currentPlayer);
                                remainingMoves.set(gameInterface.getPlayer().getMoves());
                            } else {
                                Prompt moveError = new Prompt("Invalid Move");
                                moveError.show();
                            }
                        } catch (NoSuchRoomException | UnknownActionException | InterruptedException | GameController.MovementException | TileOccupiedException ex) {
                            Logger.getLogger(gameInstance.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NullPointerException ex) {
                            Prompt rollError = new Prompt("Roll First");
                            rollError.show();
                        }
                    });
                    if (cell.equals("-1") || cell.equals("")) {
                        tileSprite.setColor(Color.rgb(7, 80, 2));
                    } else if (cell.equals("0")) {
                        tileSprite.setColor(Color.rgb(222, 151,  29));
                    } else if (cell.contains("S")) {
                        tileSprite.setColor(Color.rgb(55, 136, 4));
                    } else if (Integer.valueOf(cell) > 0) {
                        tileSprite.setColor(Color.rgb(90, 76, 65));
                    }
                    
                    tilePane.getChildren().add(tileSprite);
                    board[y][x] = tilePane;
                    boardPane.add(tilePane, x, y);
                    x++;
                }
                y++;
                
            }
        } catch(IOException ex) {
            Prompt boardMapError = new Prompt("Board File not found");
            boardMapError.show();
        }
        
        ArrayList<int[]> doorLocations = gameInterface.getDoorLocations();
        System.out.println(doorLocations.size());
        doorLocations.forEach((coords) -> {
            Tile doorSprite = new Tile(TILE_SIZE);
            switch (coords[2]) {
                case 1:
                    System.out.println("UP");
                    doorSprite.setStyle("-fx-border-width: 5px 0px 0px 0px; -fx-border-style: solid; -fx-border-color: #A36200;");
                    break;
                case 2:
                    System.out.println("RIGHT");
                    doorSprite.setStyle("-fx-border-width: 0px 5px 0px 0px; -fx-border-style: solid; -fx-border-color: #A36200;");
                    break;
                case 3:
                    System.out.println("DOWN");
                    doorSprite.setStyle("-fx-border-width: 0px 0px 5px 0px; -fx-border-style: solid; -fx-border-color: #A36200;");
                    break;
                case 4:
                    System.out.println("LEFT");
                    doorSprite.setStyle("-fx-border-width: 0px 0px 0px 5px; -fx-border-style: solid; -fx-border-color: #A36200;");
                    break;
            }
            
            board[coords[1]][coords[0]].getChildren().add(doorSprite);
        });

        spawnPlayers(board);
        
        rolled = false;
        
        root.getChildren().add(boardPane);
        
        return root;
    }

    private void spawnPlayers(StackPane[][] board) {
        List<Player> players = gameInterface.getPlayers();
        playerSprites = new PlayerSprite[players.size()];
        for(int i = players.size()-1; i>= 0; i--) {
            Player player = players.get(i);
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();
            PlayerSprite playerSprite = new PlayerSprite(x, y, "PP"+player.getId());
            playerSprites[i] = playerSprite;
            currentPlayer = playerSprite;
            board[y][x].getChildren().add(playerSprite);
        }
    }
    
    private VBox createLeftPanel() {
        VBox leftPanelLayout = new VBox();
        leftPanelLayout.setPadding(new Insets(0, 10, 10, 10));
        
        // Notepad
        Label notepadLabel = getLabel("Notepad", avenirTitle);
        
        TextArea notepad = new TextArea();
        notepad.setPrefRowCount(20);
        notepad.setPrefColumnCount(20);
        notepad.setWrapText(true);
        notepad.setFont(avenirText);
        notepad.setStyle("-fx-control-inner-background: #fff2ab;");
        notepad.textProperty().addListener((observable, oldValue, newValue) -> {
            notes = newValue;
        });
        
        // Test
        MenuItem print = new MenuItem("Print", avenirTitle);
        print.setOnMouseClicked(e -> {
            System.out.println(notepad.getText());
        });
        
        // suggestion accusation history
        Label historyLabel = getLabel("History", avenirTitle); 

        StackPane history = new StackPane();
                
        ScrollPane historyPane = new ScrollPane();
        //TODO make transparent or notepad yellow
        //historyPane.setStyle("-fx-control-inner-background: #fff2ab;");
        //historyPane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        historyPane.setPannable(false);
        historyPane.setPrefHeight(400);
        historyPane.setContent(history);
        
        leftPanelLayout.getChildren().addAll(notepadLabel, notepad, print, historyLabel, historyPane);
        
        return leftPanelLayout;
    }
    
    private Label formatHistoryItem() {
        // TODO String processing
        // waiting for definite format from backend
        Label historyItem = new Label();
        return historyItem;
    }
    
    private void createCardsDisplay(GridPane cardsLayout) {
        Label playerCardsLabel = getLabel("Cards", avenirTitle);
        int x = 1;
        int y = 0;
        for (clue.card.Card card: gameInterface.getPlayer().getCards()) {
            Image cardImage = null;
            try {
                switch (card.cardType) {
                    case PERSON:
                        cardImage = new Image(new FileInputStream(new File(ImagePathMap.get("character"+card.getid()))));
                        break;
                    case WEAPON:
                        cardImage = new Image(new FileInputStream(new File(ImagePathMap.get("weapon"+card.getid()))));
                        break;
                    case ROOM:
                        cardImage = new Image(new FileInputStream(new File(ImagePathMap.get("room"+card.getid()))));
                        break;
                    default:
                        cardImage = new Image(new FileInputStream(new File(ImagePathMap.get("character1"))));
                        break;
                }
            } catch(FileNotFoundException ex) {
                System.out.println("Not Found");
            }

            ImageView view = new ImageView(cardImage);
            cardsLayout.add(view, y, x);
            GridPane.setMargin(view, new Insets(0, 10, 10, 0));
            if (y == 2) {
                x++;
                y = 0;
            } else {
                y++;
            }
        }
        cardsLayout.add(playerCardsLabel, 0, 0, 3, 1);
        GridPane.setHalignment(playerCardsLabel,HPos.CENTER);
    }
    
    private VBox createPlayerControls() {        
        VBox playerControlsLayout = new VBox();
        playerControlsLayout.setAlignment(Pos.CENTER);
        playerControlsLayout.setPadding(new Insets(0, 0, 5, 0));
        
        Label remainingMovesLabel = getLabel("Roll Available", avenirTitle);

        MenuItem suggestionButton = new MenuItem("Suggestion", avenirLarge);
        suggestionButton.setActiveColor(Color.ORANGE);
        suggestionButton.setInactiveColor(Color.DARKORANGE);
        suggestionButton.setActive(false); //refresh Colour
        suggestionButton.setOnMouseClicked(e -> {
            if (gameInterface.getPlayer().getPosition().isRoom()) {
                currentRoom = ((Room) gameInterface.getPlayer().getPosition()).getId();
                createCardsWindow("Suggestion", Color.ORANGE);
            } else {
                Prompt suggestError = new Prompt("You are not in a room");
                suggestError.show();
            }
        });
        
        MenuItem accusationButton = new MenuItem("Accusation", avenirLarge);
        accusationButton.setActiveColor(Color.RED);
        accusationButton.setInactiveColor(Color.DARKRED);
        accusationButton.setActive(false);
        accusationButton.setOnMouseClicked(e -> {
            if (gameInterface.getPlayer().getPosition().isRoom()) {
                currentRoom = ((Room) gameInterface.getPlayer().getPosition()).getId();
                createCardsWindow("Accusation", Color.RED);
            } else {
                Prompt suggestError = new Prompt("You are not in a room");
                suggestError.show();
            }
        });

        MenuItem rollButton = new MenuItem("Roll", avenirLarge);
        
        rollButton.setOnMouseClicked(e -> {      
            if (!rolled) {
                int roll = gameInterface.roll();
                remainingMoves = new SimpleIntegerProperty();
                remainingMoves.set(roll);
                remainingMovesLabel.setText("Remaining Moves: " + remainingMoves.get());
                remainingMoves.addListener((observable, oldValue, newValue) -> remainingMovesLabel.setText("Remaining Moves: " + newValue));
                rolled = true;
            } else {
                Prompt alreadyRolled = new Prompt("You cannot roll");
                alreadyRolled.show();
            }
        });
        
        MenuItem endButton = new MenuItem("End Turn", avenirLarge);
        endButton.setOnMouseClicked(e -> {
            try {
                gameInterface.endTurn();
                gameInterface.getPlayer().setNotes(notes);
                rolled = false;
                currentPlayer = playerSprites[gameInterface.getPlayer().getId()];
                remainingMovesLabel.setText("Roll Available");
                switchToCurtain();
                createCardsDisplay(cardsDisplay);
            } catch (UnknownActionException | InterruptedException | GameController.MovementException | TileOccupiedException ex) {
                Logger.getLogger(gameInstance.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        playerControlsLayout.getChildren().addAll(remainingMovesLabel, suggestionButton, accusationButton, rollButton, endButton);
        
        return playerControlsLayout;
    }
    
    private void createCardsWindow(String title, Color color) {
        selectCards cardsWindow = new selectCards();
        cardsWindow.show(title, color, currentRoom, ImagePathMap, CardNameMap, gameInterface);
    }
    
    private BorderPane createUI() {
        curtainScene =  new Scene(createCurtain());
        BorderPane main = new BorderPane();
        main.setBackground(greenFill);
        
        main.setLeft(createLeftPanel());
        
        main.setCenter(createBoard());
        
        BorderPane rightPanel = new BorderPane();
        
        cardsDisplay = new GridPane();
        createCardsDisplay(cardsDisplay);
        rightPanel.setTop(cardsDisplay);
        rightPanel.setBottom(createPlayerControls());
        
        main.setRight(rightPanel);
        
        
        //Fade Transition
        uiToCurtain = new FadeTransition(Duration.millis(2500), main);
        uiToCurtain.setNode(main);
        uiToCurtain.setFromValue(0);
        uiToCurtain.setToValue(1);
        
        return main;
    }
    
    public void actionResponse(Action action) {
        switch (action.actionType) {
            case SHOWCARDS:
                
                break;
            case MOVE:
                
                break;
            case AVOIDSUGGESTIONCARD:
                
                break;
            case THROWAGAIN:
                
                break;
            case STARTTURN:
                
                break;
            case ACCUSATION:
                
                break;
        }
    }

    private Label getLabel(String text, Font font) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(font);
        return label;
    }
    
    private void initDefaultGraphics() {
        ImagePathMap.put("board", "./resources/board.png");
        
        ImagePathMap.put("character0", "./resources/Character/MissScarlet.png");
        ImagePathMap.put("character1", "./resources/Character/ColonelMustard.png");
        ImagePathMap.put("character2", "./resources/Character/MrsWhite.png");
        ImagePathMap.put("character3", "./resources/Character/MrGreen.png");
        ImagePathMap.put("character4", "./resources/Character/MrsPeacock.png");
        ImagePathMap.put("character5", "./resources/Character/ProfessorPlum.png");
        
        ImagePathMap.put("weapon0","./resources/Weapon/Candlestick.png");
        ImagePathMap.put("weapon1","./resources/Weapon/Dagger.png");
        ImagePathMap.put("weapon2","./resources/Weapon/LeadPipe.png");
        ImagePathMap.put("weapon3","./resources/Weapon/Revolver.png");
        ImagePathMap.put("weapon4","./resources/Weapon/Rope.png");
        ImagePathMap.put("weapon5","./resources/Weapon/Wrench.png");
        
        ImagePathMap.put("room0","./resources/Room/Ballroom.png");
        ImagePathMap.put("room1","./resources/Room/BillardRoom.png");
        ImagePathMap.put("room2","./resources/Room/Conservatory.png");
        ImagePathMap.put("room3","./resources/Room/DiningRoom.png");
        ImagePathMap.put("room4","./resources/Room/Hall.png");
        ImagePathMap.put("room5","./resources/Room/Kitchen.png");
        ImagePathMap.put("room6","./resources/Room/Library.png");
        ImagePathMap.put("room7","./resources/Room/Lounge.png");
        ImagePathMap.put("room8","./resources/Room/Study.png");
    }
    
    private void initDefaultNames() {
        CardNameMap.put("character0", "Miss Scarlet");
        CardNameMap.put("character1", "Colonel Mustard");
        CardNameMap.put("character2", "Mrs White");
        CardNameMap.put("character3", "Mr Green");
        CardNameMap.put("character4", "Mrs Peacock");
        CardNameMap.put("character5", "Professor Plum");
        
        CardNameMap.put("weapon0", "Candlestick");
        CardNameMap.put("weapon1", "Dagger");
        CardNameMap.put("weapon2", "Lead Pipe");
        CardNameMap.put("weapon3", "Revolver");
        CardNameMap.put("weapon4", "Rope");
        CardNameMap.put("weapon5", "Wrench");
        
        CardNameMap.put("room0", "Ballroom");
        CardNameMap.put("room1", "Billard Room");
        CardNameMap.put("room2", "Conservatory");
        CardNameMap.put("room3", "Dining Room");
        CardNameMap.put("room4", "Hall");
        CardNameMap.put("room5", "Kitchen");
        CardNameMap.put("room6", "Library");
        CardNameMap.put("room7", "Lounge");
        CardNameMap.put("room8", "Study");
    }
    
    private void initFonts() {
        avenirLarge = new Font(30);
        avenirTitle = new Font(20);
        avenirText = new Font(15);
        try {
            avenirLarge = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 30);
            avenirTitle = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 20);
            avenirText = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 15);
        } catch(FileNotFoundException e) {
            
        }
    }
    
    private void initGraphics() {
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            Properties prop = new Properties();
            
            prop.load(input);
            System.out.println("here");
            input.close();
        } catch (IOException ex) {
            System.out.println("there");
        }
    }
        
    public void startGame(GameController gameController, String tilePath) {
        gameStage = new Stage();
        
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setTitle("Clue");
        gameStage.setResizable(false);
        
        this.boardTilePath = tilePath;

        // Temp return button
        Button returnButton = new Button("Back");
        returnButton.setOnAction(e -> {
            gameStage.close();
        });
        
        gameInterface = gameController;
        
        initFonts();
        initDefaultGraphics();
        initDefaultNames();
        initGraphics();
        
        uiScene = new Scene(createUI(), Color.BLACK);
        gameStage.setScene(uiScene);
        gameStage.show();
    }
    
    public VBox createCurtain(){
        VBox curtain = new VBox();
        
        curtain.setAlignment(Pos.CENTER);
        curtain.setBackground(blackFill);
        
        curtain.setMinSize(1736, 960);
        Button fadeSwitch = new Button("Unfade");
        fadeSwitch.setOnAction(e -> switchToUi());
        
        curtain.getChildren().add(fadeSwitch);
        return curtain;
    }
    
    public void switchToCurtain(){
        gameStage.setScene(curtainScene);
        System.out.println(gameStage.getWidth() + "" + gameStage.getHeight());
    }
    
    public void switchToUi(){
        uiToCurtain.play();
        gameStage.setScene(uiScene);
        System.out.println(gameStage.getWidth() + "" + gameStage.getHeight());
    }

}

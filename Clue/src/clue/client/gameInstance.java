/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import clue.GameController;
import clue.GameController.TooManyPlayersException;
import clue.MissingRoomDuringCreationException;
import clue.action.Action;
import clue.action.UnknownActionException;
import clue.card.CardType;
import clue.player.Player;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.Room;
import clue.tile.TileOccupiedException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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

/**
 *
 * @author hungb
 */
public class gameInstance {
    
    private static final int TILE_SIZE = 38;
    private int counter;
   
    private StackPane[][] board;
    private String notes;
    
    private HashMap<Integer, Integer> spawnlocations = new HashMap<>();
    private PlayerSprite currentPlayer;
    private int remainingMoves;
    private int currentRoom;
    private boolean rolled;
    
    private GameController gameInterface;
    
    private HashMap<String, String> ImagePathMap = new HashMap<>();
    private HashMap<String, String> CardNameMap = new HashMap<>();
            
    private Font avenirLarge;
    private Font avenirTitle;
    private Font avenirText;
    private final Background blackFill = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    private final Background greenFill = new Background(new BackgroundFill(Color.rgb(7, 80, 2), CornerRadii.EMPTY, Insets.EMPTY));
    
    private StackPane createBoard() {
        StackPane root = new StackPane();
        root.setPadding(new Insets(10, 0, 0, 0));
                
        GridPane boardPane = new GridPane();
        
        try {
            Image boardImage = new Image(new FileInputStream(ImagePathMap.get("board")));
            boardPane.setBackground(new Background(new BackgroundImage(boardImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        } catch(IOException ex) {
            System.out.println("Failed to load board texture");
        }
        
        int boardHeight = gameInterface.getBoardHeight();
        int boardWidth = gameInterface.getBoardWidth();
        board = new StackPane[boardHeight][boardWidth];
        
        // create base Tiles
        for (int y=0; y < boardHeight; y++) {
            for (int x=0; x < boardWidth; x++) {
                StackPane tilePane = new StackPane();
                Tile tile = new Tile(TILE_SIZE);
                final int coordX = x;
                final int coordY = y;
                tile.setOnMouseClicked((MouseEvent e) -> {
                    try {
                        System.out.println(coordX + " " + coordY);
                        System.out.println(gameInterface.move(coordX, coordY));
                    } catch (NoSuchRoomException | UnknownActionException | InterruptedException | GameController.MovementException | TileOccupiedException ex) {
                        Logger.getLogger(gameInstance.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    currentPlayer.move(coordX, coordY, board, currentPlayer);
                    System.out.println("HELLO " + counter);
                    counter++;
                });
                
                tilePane.getChildren().add(tile);
                board[y][x] = tilePane;
                boardPane.add(tilePane, x, y);
            }
        }
        
        // TODO: spawn players
        spawnPlayers(board);
        
        rolled = false;
        
        root.getChildren().add(boardPane);
        
        return root;
    }

    private void spawnPlayers(StackPane[][] board) {
        List<Player> players = gameInterface.getPlayers();
        Collections.reverse(players);
        players.forEach((player) -> {
            System.out.println(player.getId());
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();
            PlayerSprite playerSprite = new PlayerSprite(x, y, "PP"+player.getId());
            currentPlayer = playerSprite;
            board[y][x].getChildren().add(playerSprite);
        });
        
        
    }
    
    private VBox createLeftPanel() {
        VBox leftPanelLayout = new VBox();
        leftPanelLayout.setPadding(new Insets(0, 10, 10, 10));
        
        // Notepad
        Label notepadLabel = getLabel("Notepad", avenirTitle);
        
        TextArea notepad = new TextArea();
        notepad.setPrefRowCount(20);
        notepad.setPrefColumnCount(20);
        
        // Test
        MenuItem print = new MenuItem("Print", avenirTitle);
        print.setOnMouseClicked(e -> {
            System.out.println(notepad.getText());
        });
        
        // suggestion accusation history
        Label historyLabel = getLabel("History", avenirTitle); 

        StackPane history = new StackPane();
        
        ScrollPane historyPane = new ScrollPane();
        historyPane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        historyPane.setPannable(false);
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
    
    private GridPane createCardsDisplay() {
        GridPane cardsLayout = new GridPane();
        Label playerCardsLabel = getLabel("Cards", avenirTitle);
        int x = 1;
        int y = 0;
        //System.out.println(gameInterface.getPlayer().getCards());
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
        
        return cardsLayout;
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
            //gameInterface.getPlayer().getPosition().isRoom()
            if (true) {
                //currentRoom = ((Room) gameInterface.getPlayer().getPosition()).getId();
                currentRoom = 1;
                createCardsWindow("Suggetsion", Color.ORANGE);
            }
        });
        
        MenuItem accusationButton = new MenuItem("Accusation", avenirLarge);
        accusationButton.setActiveColor(Color.RED);
        accusationButton.setInactiveColor(Color.DARKRED);
        accusationButton.setActive(false);
        accusationButton.setOnMouseClicked(e -> {
            createCardsWindow("Accusation", Color.RED);
        });

        MenuItem rollButton = new MenuItem("Roll", avenirLarge);
        
        rollButton.setOnMouseClicked(e -> {
            if (!rolled) {
                // waiting for gamecontroller to be finalised.
                // remainingMovesLabel.setText("Remaining Moves: " + gameInterface.roll());
                remainingMoves = gameInterface.roll();
                remainingMovesLabel.setText("Remaining Moves: " + remainingMoves);
                rolled = true;
            } else {
                Prompt alreadyRolled = new Prompt("You cannot roll");
                alreadyRolled.showAndWait();
            }
        });
        
        MenuItem endButton = new MenuItem("End Turn", avenirLarge);
        endButton.setOnMouseClicked(e -> {
            System.out.println("End Turn");
        });
        
        Button endTurnButton = new Button("End Turn");
        // Insets(top, right, bottom, left);
        playerControlsLayout.getChildren().addAll(remainingMovesLabel, suggestionButton, accusationButton, rollButton, endButton);
        
        return playerControlsLayout;        
    }
    
    private void createCardsWindow(String title, Color color) {
        selectCards cardsWindow = new selectCards();
        cardsWindow.show(title, color, currentRoom, ImagePathMap, CardNameMap);
    }
    
    private BorderPane createUI() {
        BorderPane main = new BorderPane();
        main.setBackground(greenFill);
        
        main.setLeft(createLeftPanel());
        
        main.setCenter(createBoard());
        
        BorderPane rightPanel = new BorderPane();
        rightPanel.setTop(createCardsDisplay());
        rightPanel.setBottom(createPlayerControls());
        
        main.setRight(rightPanel);
        
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
        //String configPath = getClass().getResource("assets/config.properties").toExternalForm();
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            Properties prop = new Properties();
            
            prop.load(input);
            System.out.println("here");
            input.close();
        } catch (IOException ex) {
            System.out.println("there");
        }
    }
        
    public void startGame(GameController gameController) {
        Stage gameStage = new Stage();
        
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setTitle("Clue");
        gameStage.setResizable(false);

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
        
        Scene scene = new Scene(createUI());
        gameStage.setScene(scene);
        gameStage.show();
    }
}

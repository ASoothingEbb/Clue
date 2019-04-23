/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import clue.GameController;
import clue.action.AccuseAction;
import clue.action.Action;
import clue.action.ShowCardAction;
import clue.action.ShowCardsAction;
import clue.action.SuggestAction;
import clue.card.Card;
import clue.card.CardType;
import clue.player.AiAdvanced;
import clue.card.WeaponCard;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Hung Bui Quang
 */
public class gameInstance {
    
    // Stages
    private Stage gameStage;
    private Stage client;
    
    // Data for constructing the game
    private static final int TILE_SIZE = 38;
    private String boardTilePath;
    
    private final HashMap<String, String> ImagePathMap = new HashMap<>();
    private final HashMap<String, String> CardNameMap = new HashMap<>();
    private final HashMap<String, String> TokenPathMap = new HashMap<>();
    
    // Game board
    private StackPane[][] board;
    
    // Backend Interface
    private GameController gameInterface;
    
    // Game Sprites
    private PlayerSprite currentPlayer;
    private PlayerSprite[] playerSprites;
    private WeaponSprite[] weaponSprites;
    
    // Player Data
    private int currentRoom;
    private boolean rolled;
    private String notes;
    private IntegerProperty remainingMoves;
    private boolean suggested = false;
    private boolean accused = false;
    
    // Player Data JavaFX Nodes
    Label playerCardsLabel;
    private TextArea history;
    private GridPane cardsDisplay;
    private TextArea notepad;
    private Label remainingMovesLabel;
    private Prompt showCardPrompt = null;
    
    // Sounds
    private Sound endTurnSound;
    
    //JavaFX
    private Scene prevScene;
    
    // Data for suggestion scene
    private CardType selectedCardType;
    private int selectedCardId;
    private ImageView selectedView = null;

    // Transition
    private Scene uiScene;
    private Scene curtainScene;
    private FadeTransition uiToCurtain;
    
    //Fonts
    private Font avenirLarge;
    private Font avenirTitle;
    private Font avenirText;
    
    //Backgrounds
    private final Background greenFill = new Background(new BackgroundFill(Color.rgb(7, 80, 2), CornerRadii.EMPTY, Insets.EMPTY));
    private final Background blackFill = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    
    /**
     * Creates the Tile board in the form of a GridPane and adds it to a StackPane.
     * 
     * @return StackPane object
     */
    private StackPane createBoard() {
        StackPane root = new StackPane();
        root.setPadding(new Insets(10, 5, 5, 0));
        root.setAlignment(Pos.CENTER);
                
        GridPane boardPane = new GridPane();

        int boardHeight = gameInterface.getBoardHeight();
        int boardWidth = gameInterface.getBoardWidth();
        board = new StackPane[boardHeight][boardWidth];

        Image image = null;
        try {
            image = new Image(new FileInputStream(new File(ImagePathMap.get("board"))));
        } catch(FileNotFoundException ex) {
        }
        
        ImageView boardview = new ImageView(image);
        boardview.setFitWidth(912);
        boardview.setFitHeight(950);
        
        Pane alignment = new Pane();
        alignment.getChildren().addAll(boardview, boardPane);
        
        root.getChildren().add(alignment);
        String line;
        
        try (BufferedReader br = new BufferedReader(new FileReader(boardTilePath))) {
            int y = 0;
            while ((line = br.readLine()) != null && y < boardHeight) {
                String[] tiles = line.split(",");
                int x = 0;
                for (int i=0; i < boardWidth; i++) {
                    String tile = tiles[i];
                    String cell = tile.replaceAll("[^0-9A-Z-]+", "");
                    StackPane tilePane = new StackPane();
                    tilePane.setAlignment(Pos.CENTER);
                    Tile tileSprite = new Tile(TILE_SIZE);
                    if (!boardTilePath.contains("archersAvenue")) {
                        tileSprite.setStyle("-fx-border-width: 1px 1px 1px 1px; -fx-border-style: solid; -fx-border-color: black;");
                    }
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
                        } catch (NoSuchRoomException | TileOccupiedException ex) {
                            Logger.getLogger(gameInstance.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NullPointerException ex) {
                            Prompt rollError = new Prompt("Roll First");
                            rollError.show();
                        }
                    });
                    if (!boardTilePath.contains("archersAvenue")) {
                        if (cell.equals("-1") || cell.equals("")) {
                            tileSprite.setColor(Color.rgb(7, 80, 2));
                        } else if (cell.equals("0")) {
                            tileSprite.setColor(Color.rgb(222, 151,  29));
                        } else if (cell.contains("S")) {
                            tileSprite.setColor(Color.rgb(55, 136, 4));
                        }else if (cell.contains("I")){
                            tileSprite.setColor(Color.ALICEBLUE);
                        } else if (Integer.valueOf(cell) > 0) {
                            paintRoom(tileSprite, Integer.valueOf(cell));
                        } 
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
        
        if (!boardTilePath.contains("archersAvenue")) {
            ArrayList<int[]> doorLocations = gameInterface.getDoorLocations();
            doorLocations.forEach((coords) -> {
                Tile doorSprite = new Tile(TILE_SIZE);
                switch (coords[2]) {
                    case 1:
                        doorSprite.setStyle("-fx-border-width: 5px 0px 0px 0px; -fx-border-style: solid; -fx-border-color: #A36200;");
                        break;
                    case 2:
                        doorSprite.setStyle("-fx-border-width: 0px 5px 0px 0px; -fx-border-style: solid; -fx-border-color: #A36200;");
                        break;
                    case 3:
                        doorSprite.setStyle("-fx-border-width: 0px 0px 5px 0px; -fx-border-style: solid; -fx-border-color: #A36200;");
                        break;
                    case 4:
                        doorSprite.setStyle("-fx-border-width: 0px 0px 0px 5px; -fx-border-style: solid; -fx-border-color: #A36200;");
                        break;
                }
                board[coords[1]][coords[0]].getChildren().add(doorSprite);
            });
        }
        
        spawnPlayers(board);
        spawnWeapons(board);
        
        rolled = false;
        return root;
    }
    
    /**
     * Changes each rooms appearance to be different by changing their colour on the board(custom maps only).
     * 
     * @param tile  the Tile(Label) to change appearance
     * @param id the id of the room 1 to 9.
     */
    private void paintRoom(Tile tile, int id){
        Random rand = new Random(Calendar.getInstance().getTimeInMillis());
        switch(id){
            case 1://Study
                tile.setStyle("-fx-background-color: #696969;");
                break;
            case 2://Hall
                tile.setStyle("-fx-background-color: #42d4f4;");
                break;
            case 3://Lounge
                tile.setStyle("-fx-background-color: #000075;");
                break;
            case 4://Library
                tile.setStyle("-fx-background-color: #f58231;");
                break;
            case 5://Billiard Room
                tile.setStyle("-fx-background-color: #911eb4;");
                break;
            case 6://Dining room
                tile.setStyle("-fx-background-color: #800000;");
                break;
            case 7://Convervatory
                tile.setStyle("-fx-background-color: #808000;");
                break;
            case 8://Ballroom
                tile.setStyle("-fx-background-color: #fffac8;");
                break;
            case 9://Kitchen
                tile.setStyle("-fx-background-color: #fabebe;");
                break;
            default:
                int r = rand.nextInt(255)+1;
                int g = rand.nextInt(255)+1;
                int b = rand.nextInt(255)+1;
                tile.setColor(Color.rgb(r,g,b));
                break;
        }
    }

    /**
     * Called at the beginning of a game. Renders the players in a spawn tile.
     * 
     * @param board the GUI representation of the board
     */
    private void spawnPlayers(StackPane[][] board) {
        List<Player> players = gameInterface.getPlayers();
        playerSprites = new PlayerSprite[players.size()];
        for(int i = players.size()-1; i>= 0; i--) {
            int x = players.get(i).getDrawX();
            int y = players.get(i).getDrawY();

            playerSprites[i] = new PlayerSprite(x, y, TokenPathMap.get("character" + players.get(i).getId()));
            board[y][x].getChildren().add(playerSprites[i]);
        }
        currentPlayer = playerSprites[0];
    }
    
    /**
     * Called at the beginning of a game. Renders the weapons in a separate room.
     * 
     * @param board the GUI representation of the board
     */
    private void spawnWeapons(StackPane[][] board) {
        List<WeaponCard> weapons = gameInterface.getWeaponCards();
        weaponSprites = new WeaponSprite[weapons.size()];
        for (int i=0; i < weapons.size(); i++) {
            int x = weapons.get(i).getDrawX();
            int y = weapons.get(i).getDrawY();
            weaponSprites[i] = new WeaponSprite(x, y, TokenPathMap.get("weapon" + weapons.get(i).getId()));
            board[y][x].getChildren().add(weaponSprites[i]);
        }
    }
    
    /**
     * Re-renders where the player tokens are on the board
     */
    private void redrawPlayers() {
        gameInterface.getPlayers().forEach((player) -> {
            PlayerSprite sprite = playerSprites[player.getId()];
            sprite.move(player.getDrawX(), player.getDrawY(), board, sprite);
        });
    }
    /**
     * Re-renders where the weapon tokens are on the board
     */
    private void redrawWeapons() {
        gameInterface.getWeaponCards().forEach((weapon) -> {
            WeaponSprite sprite = weaponSprites[weapon.getId()];
            sprite.move(weapon.getDrawX(), weapon.getDrawY(), board, sprite);
        });
    }
    
   /**
    * Creates the left panel where the player notes and the history for each respective player are shown.
    * 
    * @return Notes and History boxes
    */
    private VBox createLeftPanel() {
        VBox leftPanelLayout = new VBox();
        leftPanelLayout.setPadding(new Insets(0, 10, 10, 10));
        
        // Notepad
        Label notepadLabel = getLabel("Notepad", avenirTitle);
        
        notepad = new TextArea();
        notepad.setPrefRowCount(20);
        notepad.setPrefColumnCount(20);
        notepad.setWrapText(true);
        notepad.setFont(avenirText);
        notepad.setStyle("-fx-control-inner-background: #fff2ab;");
        notepad.textProperty().addListener((observable, oldValue, newValue) -> {
            notes = newValue;
        });
        
        // suggestion accusation history
        
        BorderPane hiddenLayout = new BorderPane();
        
        Label historyLabel = getLabel("History", avenirTitle); 
        hiddenLayout.setLeft(historyLabel);
        
        history = new TextArea();
        history.setPrefRowCount(18);
        history.setPrefColumnCount(20);
        history.setWrapText(true);
        history.setFont(avenirText);
        history.setStyle("-fx-control-inner-background: #fff2ab;");
        history.setEditable(false);
        
        leftPanelLayout.getChildren().addAll(notepadLabel, notepad, hiddenLayout, history);
        
        if (!boardTilePath.contains("archersAvenue")) {
            MenuItem showRoomKeys = new MenuItem("Room Keys", avenirTitle);
            showRoomKeys.setAlignment(Pos.CENTER);
            RoomKeys roomKeys = new RoomKeys(gameStage);
            showRoomKeys.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    Bounds coordinates = showRoomKeys.localToScene(showRoomKeys.getBoundsInLocal());
                    roomKeys.setLocation(coordinates.getMaxX() - 20, coordinates.getMaxY() + coordinates.getHeight() + 6);
                    roomKeys.show();
                } else {
                    roomKeys.hide();
                }
            });
            hiddenLayout.setRight(showRoomKeys);
        }
        
        return leftPanelLayout;
    }
    
    /**
     * Renders the cards the player has.
     * 
     * @param cardsLayout 
     */
    private void createCardsDisplay(GridPane cardsLayout) {
        cardsLayout.getChildren().remove(playerCardsLabel);
        playerCardsLabel = getLabel(CardNameMap.get("character"+gameInterface.getPlayer().getId()) + "'s Turn", avenirLarge);
        switch(gameInterface.getPlayer().getId()){
            case 0://Scarlet
                playerCardsLabel.setTextFill(Color.rgb(232, 53, 53));
                break;
            case 1://Mustard
                playerCardsLabel.setTextFill(Color.rgb(253,188,0));
                break;
            case 2://Mrs White
                playerCardsLabel.setTextFill(Color.WHITE);
                break; 
            case 3://Reverend green
                playerCardsLabel.setTextFill(Color.rgb(38, 242, 99));
                break;
            case 4://Mrs Peacock
                playerCardsLabel.setTextFill(Color.rgb(66, 190, 244));
                break;
            case 5://Professor Plum
                playerCardsLabel.setTextFill(Color.rgb(216, 64, 237));
                break;
            default:
                break;
        }
        int x = 1;
        int y = 0;
        cardsDisplay.getChildren().clear();
        for (Card card: gameInterface.getPlayer().getCards()) {
            ImageView view = new ImageView(getImage(card.getId(), card.getCardType()));
            view.setFitHeight(230);
            view.setFitWidth(150);
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
    
    /**
     * Creates the Roll, suggest, accuse, end turn buttons and a label displaying roll count/status.
     * 
     * @return VBox containing all the buttons. 
     */
    private VBox createPlayerControls() {    
        //Sounds
        endTurnSound = new Sound("resources/Sounds/endTurnSound.wav");
        endTurnSound.setVolume(0.75f);
        
        
        VBox playerControlsLayout = new VBox();
        playerControlsLayout.setAlignment(Pos.CENTER);
        playerControlsLayout.setPadding(new Insets(0, 0, 5, 0));
        
        remainingMovesLabel = getLabel("Roll Available", avenirTitle);

        MenuItem suggestionButton = new MenuItem("Suggestion", avenirLarge);
        suggestionButton.setActiveColor(Color.ORANGE);
        suggestionButton.setInactiveColor(Color.DARKORANGE);
        suggestionButton.setActive(false); //refresh Colour
        suggestionButton.setOnMouseClicked(e -> {
            if (!accused) {
                if (suggested) {
                    Prompt errorPrompt = new Prompt("You have already suggsted");
                    errorPrompt.setLabelTitle("Invalid Game Move");
                    errorPrompt.showAndWait();
                } else {
                    if (gameInterface.getPlayer().getPosition().isRoom()) {
                        currentRoom = ((Room) gameInterface.getPlayer().getPosition()).getId();
                        createCardsWindow("Suggestion", Color.ORANGE);
                    } else {
                        Prompt suggestError = new Prompt("You are not in a room");
                        suggestError.showAndWait();
                    }
                }
            }
        });
        
        MenuItem accusationButton = new MenuItem("Accusation", avenirLarge);
        accusationButton.setActiveColor(Color.rgb(200, 0, 0));
        accusationButton.setInactiveColor(Color.rgb(239, 43, 43));
        accusationButton.setActive(false);
        accusationButton.setOnMouseClicked(e -> {
            if (accused) {
                Prompt errorPrompt = new Prompt("You have already accused");
                errorPrompt.setLabelTitle("Invalid Game Move");
                errorPrompt.showAndWait();
            } else {
                if (gameInterface.getPlayer().getPosition().isRoom()) {
                    currentRoom = ((Room) gameInterface.getPlayer().getPosition()).getId();
                    createCardsWindow("Accusation", Color.RED);
                } else {
                    Prompt accuseError = new Prompt("You are not in a roomn");
                    accuseError.showAndWait();
                }
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
                alreadyRolled.showAndWait();
            }
        });

        MenuItem endButton = new MenuItem("End Turn", avenirLarge);
        endButton.setOnMouseClicked(e -> {
            gameInterface.getPlayer().setNotes(notes);
            gameInterface.endTurn();
            endTurnSound.play();
        });
        
        playerControlsLayout.getChildren().addAll(remainingMovesLabel, suggestionButton, accusationButton, rollButton, endButton);
        
        return playerControlsLayout;
    }
    
    /**
     * Initialises the cardsWindow for accusation or suggestion.
     * 
     * @param title title of the window, either suggest or accuse
     * @param color colour corresponding to suggest or accuse
     */
    private void createCardsWindow(String title, Color color) {
        selectCards cardsWindow = new selectCards();
        cardsWindow.show(title, color, currentRoom, ImagePathMap, CardNameMap, gameInterface);
    }
    
    /**
     * Creates The main component to be used in the Scene that is shown when people are playing the game.
     * This will include board, player controls, history/notes, etc.
     * 
     * @return BorderPane containing the UI elements
     */
    private BorderPane createUI() {
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
        uiToCurtain = new FadeTransition(Duration.millis(500), main);
        uiToCurtain.setNode(main);
        uiToCurtain.setFromValue(0);
        uiToCurtain.setToValue(1);
        
        return main;
    }
    
    /**
     * Used by the backend to communicate with the GUI.
     * 
     * @param action a type of action
     */
    public void actionResponse(Action action) {
        switch (action.getActionType()) {
            case SHOWCARDS:
                System.out.println("[gameInstance.actionResponse] case SHOWCARDS ----");
                endTurnSound.play();
                showCards(action);
                break;
            case SHOWCARD:
                System.out.println("[gameInstance.actionResponse] case SHOWCARD ----");
                endTurnSound.play();
                showCard(action);
                redrawPlayers();
                redrawWeapons();
                if (((ShowCardAction) action).getWhoShowedTheCard() instanceof AiAdvanced) {
                    showCardPrompt.show();
                }
                suggested = true;
                break;
            case AVOIDSUGGESTIONCARD:
                System.out.println("[gameInstance.actionResponse] case AVOIDSUGGESTIONCARD ----");
                break;
            case THROWAGAIN:
                System.out.println("[gameInstance.actionResponse] case THROWAGAIN ----");
                resetRoll();
                break;
            case STARTTURN:
                System.out.println("[gameInstance.actionResponse] case STARTTURN ----");
                break;
            case ACCUSATION:
                System.out.println("[gameInstance.actionResponse] case ACCUSATION ----");
                showAccusationResult(action);
                break;
            case TELEPORT:
                System.out.println("[gameInstance.actionResponse] case TELEPORT ----");
                remainingMovesLabel.setText("Teleport");
                break;
        }
        System.out.println("return");
    }
    /**
     * Gets the image for the corresponding card.
     * @param cardId the ID of the card
     * @param cardType the type of card (Character, Weapon, or Room)
     * @return an Image object of the card
     */
    private Image getImage(int cardId, CardType cardType) {
        Image cardImage = null;
        try {
            switch (cardType) {
                case PERSON:
                    cardImage = new Image(new FileInputStream(new File(ImagePathMap.get("character"+cardId))));
                    break;
                case WEAPON:
                    cardImage = new Image(new FileInputStream(new File(ImagePathMap.get("weapon"+cardId))));
                    break;
                case ROOM:
                    cardImage = new Image(new FileInputStream(new File(ImagePathMap.get("room"+cardId))));
                    break;
                default:
                    cardImage = new Image(new FileInputStream(new File(ImagePathMap.get("character1"))));
                    break;
            }
        } catch(FileNotFoundException ex) {

        }
        
        return cardImage;
    }
    
    /**
     * Alerts the player of whether their accusation was correct or incorrect.
     * 
     * @param action a type of action
     */
    public void showAccusationResult(Action action) {
        Prompt accusationResultPrompt = new Prompt("");
        if (((AccuseAction) action).wasCorrect()) { //accusation correct
            accusationResultPrompt.setMessage("CONGRATULATIONS");
            accusationResultPrompt.setLabelTitle("YOU WON");
        } else {
            accusationResultPrompt.setMessage("BETTER LUCK NEXT TIME!");
            accusationResultPrompt.setLabelTitle("YOU LOSE");
        }
        
        List<Card> murderCards = ((AccuseAction) action).getMurderCards();
        ImageView[] cards = new ImageView[3];
        for (int i=0; i < murderCards.size(); i++) {
            cards[i] = new ImageView(getImage(murderCards.get(i).getId(), murderCards.get(i).getCardType()));
        }
        accusationResultPrompt.setImage(cards);
        accusationResultPrompt.setOnCloseRequest(e -> {
            if (((AccuseAction) action).wasCorrect()) {
                client.show();
                gameStage.close();
            }
            accused = true;
        });
        accusationResultPrompt.showAndWait();
    }
    
    /**
     * Prompts the player with a Card which he suggested IF any of the players have said card. Prompts nothing otherwise.
     * 
     * @param action the showCardAction
     */
    private void showCard(Action action) {
        ShowCardAction response = ((ShowCardAction) action);
        String suggestee = CardNameMap.get("character" + ((ShowCardAction) action).getWhoShowedTheCard().getId());
        showCardPrompt = new Prompt(suggestee + " showed");
        showCardPrompt.setLabelTitle("Suggestion Response");
        ImageView cardViewer = new ImageView(getImage(response.getCardToShow().getId(), response.getCardToShow().getCardType()));
        showCardPrompt.setImage(cardViewer);
        showCardPrompt.setOnCloseRequest(e -> {
            showCardPrompt = null;
        });
    }
    /**
     * Changes the scene to a waiting scene which notifies the current player whose attention is required
     * @param playerId ID of the player whose attention is required
     * @param next the scene to change after the players have switched
     */
    private void switchPlayerScene(int playerId, Scene next) {
        VBox switchPlayer = new VBox();
        switchPlayer.setBackground(blackFill);
        switchPlayer.setAlignment(Pos.CENTER);
        Label switchToLabel = getLabel("Switch to " + CardNameMap.get("character"+playerId), avenirTitle);
        MenuItem showButton = new MenuItem("Play", avenirTitle);
        showButton.setOnMouseClicked(e -> {
            gameStage.setScene(next);
            if (showCardPrompt != null) {
                showCardPrompt.show();
            }
        });
        
        switchPlayer.getChildren().addAll(switchToLabel, showButton);
        
        Scene scene = new Scene(switchPlayer, 1736, 960);
        gameStage.setScene(scene);
    }
    /**
     * Brings up a prompt(window) which displays a message to the player.
     * 
     * @param message the message to show the user
     */
    public void notifyUser(String message) {
        Prompt notifyPrompt = new Prompt(message);
        notifyPrompt.setLabelTitle("Notice");
        notifyPrompt.show();
    }
    
    /**
     * This is prompted if the player has 1 or more cards that were suggested.
     * The user will be asked to pick one of those cards.
     * 
     * @param action the showCardsAction
     */
    private void showCards(Action action) {
        VBox showCardsDisplay = new VBox();
        showCardsDisplay.setBackground(greenFill);
        showCardsDisplay.setAlignment(Pos.CENTER);
        
        List<Card> cards = ((ShowCardsAction) action).getCardList();
        
        Label showCardsLabel = getLabel("Select a card to show", avenirTitle);
        
        HBox cardDisplay = new HBox();
        cardDisplay.setPadding(new Insets(10));
        cardDisplay.setSpacing(10);
        cardDisplay.setAlignment(Pos.CENTER);
                
        cards.stream().map((card) -> {
            final int cardId = card.getId();
            final CardType cardType = card.getCardType();
            ImageView view = new ImageView(getImage(card.getId(), card.getCardType()));
            view.setOnMouseClicked(e -> {
                setSelectedCard(cardId, cardType, view);
            });
            return view;
        }).forEachOrdered((view) -> {
            cardDisplay.getChildren().add(view);
        });
        
        MenuItem confirmCardButton = new MenuItem("Confirm Selection", avenirTitle);
        confirmCardButton.setOnMouseClicked(e -> {
            ((ShowCardsAction) action).setCardToShow(selectedCardId, selectedCardType);
            switchPlayerScene(((ShowCardsAction) action).getSuggester().getId(), prevScene);
            gameInterface.replyToShowCards((ShowCardsAction) action);
        });
        
        showCardsDisplay.getChildren().addAll(showCardsLabel, cardDisplay, confirmCardButton);
        Scene scene = new Scene(showCardsDisplay, 1736, 960);
        prevScene = gameStage.getScene();
        switchPlayerScene(((ShowCardsAction) action).getPlayer().getId(), scene);
    }
    
    /**
     * Makes a card glow in the "showCards" prompt. This will mark it as selected and will be shown to the person who suggested.
     * 
     * @param id the id of the card  that is selected
     * @param type the type of card that is selected
     * @param view  the render of the card 
     */
    private void setSelectedCard(int id, CardType type, ImageView view) {
        this.selectedCardId = id;
        this.selectedCardType = type;
        
        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.rgb(255,215,0));
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        
        if (selectedView != null && !selectedView.equals(view)) {
            view.setEffect(borderGlow);
            this.selectedView.setEffect(null);
        } else {
            view.setEffect(borderGlow);
            this.selectedView = view;
        }
        this.selectedView = view;
    }

    /**
     * Creates a label with text and font of choosing.
     * 
     * @param text the text to be displayed on the label
     * @param font the type of font to be used for the text
     * @return a label object with message "text" and font "font".
     */
    private Label getLabel(String text, Font font) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(font);
        return label;
    }
    
    /**
     * Initialises the default graphics. The board and the cards.
     */
    private void initDefaultGraphics() {
        ImagePathMap.put("board", "resources/boardFinal.png");
        
        ImagePathMap.put("character0", "resources/Character/MissScarlet.png");
        ImagePathMap.put("character1", "resources/Character/ColonelMustard.png");
        ImagePathMap.put("character2", "resources/Character/MrsWhite.png");
        ImagePathMap.put("character3", "resources/Character/ReverendGreen.png");
        ImagePathMap.put("character4", "resources/Character/MrsPeacock.png");
        ImagePathMap.put("character5", "resources/Character/ProfessorPlum.png");
        
        ImagePathMap.put("weapon0","resources/Weapon/Candlestick.png");
        ImagePathMap.put("weapon1","resources/Weapon/Dagger.png");
        ImagePathMap.put("weapon2","resources/Weapon/LeadPipe.png");
        ImagePathMap.put("weapon3","resources/Weapon/Revolver.png");
        ImagePathMap.put("weapon4","resources/Weapon/Rope.png");
        ImagePathMap.put("weapon5","resources/Weapon/Spanner.png");
        
        ImagePathMap.put("room0","resources/Room/Study.png");
        ImagePathMap.put("room1","resources/Room/Hall.png");
        ImagePathMap.put("room2","resources/Room/Lounge.png");
        ImagePathMap.put("room3","resources/Room/Library.png");
        ImagePathMap.put("room4","resources/Room/BillardRoom.png");
        ImagePathMap.put("room5","resources/Room/DiningRoom.png");
        ImagePathMap.put("room6","resources/Room/Conservatory.png");
        ImagePathMap.put("room7","resources/Room/BallRoom.png");
        ImagePathMap.put("room8","resources/Room/Kitchen.png");
    }
    
    /**
     * Initialises the default graphical assets for the characters and weapons.
     * These are the player tokens ie:the images rendered ON the board.
     */
    private void initDefaultTokens() {
        TokenPathMap.put("character0", "resources/characterToken/MissScarlet.png");
        TokenPathMap.put("character1", "resources/characterToken/ColonelMustard.png");
        TokenPathMap.put("character2", "resources/characterToken/MrsWhite.png");
        TokenPathMap.put("character3", "resources/characterToken/ReverendGreen.png");
        TokenPathMap.put("character4", "resources/characterToken/MrsPeacock.png");
        TokenPathMap.put("character5", "resources/characterToken/ProfessorPlum.png");
        
        TokenPathMap.put("weapon0", "resources/weaponToken/Candlestick.png");
        TokenPathMap.put("weapon1", "resources/weaponToken/Dagger.png");
        TokenPathMap.put("weapon2", "resources/weaponToken/LeadPipe.png");
        TokenPathMap.put("weapon3", "resources/weaponToken/Revolver.png");
        TokenPathMap.put("weapon4", "resources/weaponToken/Rope.png");
        TokenPathMap.put("weapon5", "resources/weaponToken/Spanner.png");
    }
    
    /**
     * Initialises the default names. This is used as a HashMap.
     */
    private void initDefaultNames() {
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
    }
    
    /**
     * Initialises the Fonts.
     */
    private void initFonts() {
        avenirLarge = new Font(30);
        avenirTitle = new Font(20);
        avenirText = new Font(15);
        try {
            avenirLarge = Font.loadFont(new FileInputStream(new File("resources/fonts/Avenir-Book.ttf")), 30);
            avenirTitle = Font.loadFont(new FileInputStream(new File("resources/fonts/Avenir-Book.ttf")), 20);
            avenirText = Font.loadFont(new FileInputStream(new File("resources/fonts/Avenir-Book.ttf")), 15);
        } catch(FileNotFoundException e) {
            
        }
    }
    
    /**
     * Initialises the custom graphics.
     */
    private void initCustomSettings() {
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            
            prop.keySet();
            
            prop.entrySet().forEach((entry) -> {
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                if (key.contains("Name")) {
                    CardNameMap.put(key.substring(0, key.length() - 4), value);
                } else if (key.contains("Texture")) {
                    ImagePathMap.put(key.substring(0, key.length() - 5), value);
                } else if (key.contains("Token")) {
                    TokenPathMap.put(key.substring(0, key.length() - 5), value);
                }
            });
            input.close();
        } catch (IOException ex) {
        }
    }
       
    /**
     * Starts the game instance, initialises the UI elements.
     * 
     * @param gameController a reference to the gameController class (backend)
     * @param client the main window which started gameInstance
     * @param tilePath CSV representation of the board
     */
    public void startGame(GameController gameController, Stage client,String tilePath) {
        gameStage = new Stage();
        
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setTitle("Clue");
        gameStage.setResizable(false);
        
        this.client = client;
        this.boardTilePath = tilePath;

        // Temp return button
        Button returnButton = new Button("Back");
        returnButton.setOnAction(e -> {
            gameStage.close();
        });
        
        gameInterface = gameController;
        gameInterface.setGameInstance(this);
        
        initFonts();
        initDefaultTokens();
        initDefaultGraphics();
        initDefaultNames();
        initCustomSettings();
        
        uiScene = new Scene(createUI(), Color.BLACK);
        gameStage.setScene(uiScene);
        gameStage.show();
    }
    
    /**
     * This creates the black Scene for when human players are meant to switch who's looking at the screen.
     * 
     * @return the switch turn scene
     */
    public Scene createCurtainScene(){
        VBox curtain = new VBox();
        
        curtain.setAlignment(Pos.CENTER);
        curtain.setBackground(blackFill);
        curtain.setMinSize(1736, 960);
        
        
        //TODO fix transition to update
        Label switchPlayerLabel = getLabel(CardNameMap.get("character"+gameInterface.getPlayer().getId()) + "'s Turn", avenirLarge);
        
         switch(gameInterface.getPlayer().getId()){
            case 0://Scarlet
                switchPlayerLabel.setTextFill(Color.RED);
                break;
            case 1://Mustard
                switchPlayerLabel.setStyle("-fx-text-fill: #ffdb58");
                break;
            case 2://Mrs White
                switchPlayerLabel.setTextFill(Color.WHITE);
                break; 
            case 3://Reverend green
                switchPlayerLabel.setStyle("-fx-text-fill: #00FF00;");
                break;
            case 4://Mrs PeaCOCK
                switchPlayerLabel.setTextFill(Color.BLUE);
                break;
            case 5://Professor Plum
                switchPlayerLabel.setStyle("-fx-text-fill: #DDA0DD;");
                break;
            default:
                break;
        }
        
        MenuItem fadeSwitch = new MenuItem("Start Turn", avenirTitle);
        fadeSwitch.setOnMouseClicked(e -> {
            switchToUi();
            endTurnSound.reset();
        });
        
        curtain.getChildren().addAll(switchPlayerLabel, fadeSwitch);
        curtainScene =  new Scene(curtain);
        return curtainScene;
    }
    
    /**
     * Switches the current scene to the Curtain scene.
     */
    public void switchToCurtain(){
        gameStage.setScene(createCurtainScene());
    }
    
    /**
     * Switches the current scene to the uiScene and fade animation plays.
     */
    public void switchToUi(){
        uiToCurtain.play();
        gameStage.setScene(uiScene);
    }
    
    
    
    /**
     * Creates the "history" to be displayed in the GUI in the form of strings. These Strings are descriptions of the actions.
     * 
     * @param actionsToNotify the list of actions to be turned into human readable strings.
     */
    public void showActionLog(LinkedList<Action> actionsToNotify) {
        for (Action action: actionsToNotify) {
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
                    final int[] mathew = new int[1];
                    System.out.println(mathew[-1]);
                    break;
            }
        }
        
    }

    /**
     * Starts a new human turn in the GUI This includes rendering new cards(for the next player) an switches it to a black(curtain) scene.
     * 
     * @param actionsToNotify the actions that have happened previously.
     */
    public void newHumanPlayerTurn(LinkedList<Action> actionsToNotify) {
        resetRoll();
        suggested = false;
        accused = false;
        currentPlayer = playerSprites[gameInterface.getPlayer().getId()];
        notepad.setText(gameInterface.getPlayer().getNotes());
        history.clear();
        switchToCurtain();
        redrawPlayers();
        redrawWeapons();
        createCardsDisplay(cardsDisplay);
        showActionLog(actionsToNotify);
        //TODO
        //call showAction(actionsToNotify) after player turn has begun (after they click start turn and they fade in)
    }
    
  /**
    * Resets the Roll label which displays how many moves you have.
    */
    private void resetRoll() {
        remainingMovesLabel.setText("Roll Available");
        rolled = false;
    }

    /**
     * Called by GameController when the game has finished, player is the winning player, player is null if there is no winner.
     */
    public void gameOver() {
        Prompt gameOverPrompt = new Prompt("No one was able to guess the murder cards");
        gameOverPrompt.setTitle("GAME OVER");
        gameOverPrompt.setOnCloseRequest(e -> {
            client.show();
            gameStage.close();
        });
        gameOverPrompt.showAndWait();
    }
}
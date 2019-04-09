/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    private int width;
    private int height;
    private int counter;
   
    private StackPane[][] board = new StackPane[25][24];
    private String notes;
    
    private HashMap<Integer, Integer> spawnlocations = new HashMap<>();
    private Player currentPlayer;

    private final Font avenirButtonLarge = Font.loadFont(getClass().getResourceAsStream("assets/fonts/Avenir-Book.ttf"), 30);
    private final Font avenirTitle = Font.loadFont(getClass().getResourceAsStream("assets/fonts/Avenir-Book.ttf"), 20);
    private final Font avenirText = Font.loadFont(getClass().getResourceAsStream("assets/fonts/Avenir-Book.ttf"), 15);
    private final Background blackFill = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    
    private StackPane createBoard() {
        StackPane root = new StackPane();
        root.setPadding(new Insets(10, 0, 0, 0));
                
        GridPane boardPane = new GridPane();
                
        Image boardImage = new Image(getClass().getResource("assets/boardImage.png").toExternalForm());
        
        boardPane.setBackground(new Background(new BackgroundImage(boardImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        boardPane.setPrefSize(width, height);
        
        // create base Tiles
        for (int y=0; y < 24; y++) {
            for (int x=0; x < 25; x++) {
                StackPane tilePane = new StackPane();
                Tile tile = new Tile(TILE_SIZE);
                final int coordX = x;
                final int coordY = y;
                tile.setOnMouseClicked((MouseEvent e) -> {
                    boolean moved = currentPlayer.move(coordX, coordY, board, currentPlayer);
                    System.out.println("HELLO "+counter);
                    counter++;
                });
                
                tilePane.getChildren().add(tile);
                        
                board[x][y] = tilePane;
                boardPane.add(tilePane, y, x);
                
            }
        }
        
        // TODO: spawn players
        
        Player player1 = new Player(0, 4, "PP");
        board[5][0].getChildren().add(player1);
        currentPlayer = player1;
        
        root.getChildren().add(boardPane);
        
        return root;
    }
    
    public VBox createLeftPanel() {
        VBox leftPanelLayout = new VBox();
        leftPanelLayout.setPadding(new Insets(0, 10, 10, 10));
        
        // Notepad
        Label notepadLabel = new Label("Notepad");
        notepadLabel.setTextFill(Color.WHITE);
        notepadLabel.setFont(avenirTitle);
        
        TextArea notepad = new TextArea();
        notepad.setPrefRowCount(20);
        notepad.setPrefColumnCount(20);
        
        // Test
        MenuItem print = new MenuItem("Print", avenirTitle);
        print.setOnMouseClicked(e -> {
            System.out.println(notepad.getText());
        });
        
        // suggestion accusation history
        Label historyLabel = new Label("History");
        historyLabel.setTextFill(Color.WHITE);
        historyLabel.setFont(avenirTitle);
        
        StackPane history = new StackPane();
        
        ScrollPane historyPane = new ScrollPane();
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
    
    public GridPane createCardsDisplay() {
        GridPane cardsLayout = new GridPane();
        
        Label playerCardsLabel = new Label("Cards");
        playerCardsLabel.setTextFill(Color.WHITE);
        playerCardsLabel.setFont(avenirTitle);

        Card weaponCard = new Card(new Image(getClass().getResourceAsStream("assets/card.png")), "weapon", 1);
        ImageView View1 = new ImageView(weaponCard.getImage());
        ImageView View2 = new ImageView(weaponCard.getImage());
        ImageView View3 = new ImageView(weaponCard.getImage());
        
        // Insets(top, right, bottom, left);
        cardsLayout.add(View1, 0, 1);
        GridPane.setMargin(View1, new Insets(0,10,10,0));
        cardsLayout.add(View2, 1, 1);
        GridPane.setMargin(View2, new Insets(0,10,10,0));
        cardsLayout.add(View3, 0, 2);
        GridPane.setMargin(View3, new Insets(0,10,10,0));
        cardsLayout.add(playerCardsLabel, 0, 0, 2, 1);
        GridPane.setHalignment(playerCardsLabel,HPos.CENTER);
        
        return cardsLayout;
    }
    
    private GridPane createPlayerControls() {
        GridPane playerControlsLayout = new GridPane();
        playerControlsLayout.setAlignment(Pos.CENTER);
        
        MenuItem suggestionButton = new MenuItem("Suggestion", avenirButtonLarge);
        suggestionButton.setActiveColor(Color.ORANGE);
        suggestionButton.setInactiveColor(Color.DARKORANGE);
        suggestionButton.setActive(false); //refresh Colour
        suggestionButton.setOnMouseClicked(e -> {
            createCardsWindow("Suggetsion", Color.ORANGE);
        });
        
        Button suggestButton = new Button("Suggestion");
        suggestButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        suggestButton.setOnAction(e -> {
            createCardsWindow("Suggestion", Color.ORANGE);
        });
        
        MenuItem accusationButton = new MenuItem("Accusation", avenirButtonLarge);
        accusationButton.setActiveColor(Color.RED);
        accusationButton.setInactiveColor(Color.DARKRED);
        accusationButton.setActive(false);
        accusationButton.setOnMouseClicked(e -> {
            createCardsWindow("Accusation", Color.RED);
        });
        
        Button accuseButton = new Button("Accusation");
        accuseButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        accuseButton.setOnAction(e -> {
            createCardsWindow("Accusation", Color.RED);
        });
        
        MenuItem rollButton = new MenuItem("Roll", avenirButtonLarge);
        rollButton.setOnMouseClicked(e -> {
            System.out.println("Roll Dices");
        });
        
        Button rollDiceButton = new Button("Roll");
        
        MenuItem endButton = new MenuItem("End Turn", avenirButtonLarge);
        endButton.setOnMouseClicked(e -> {
            System.out.println("End Turn");
        });
        
        Button endTurnButton = new Button("End Turn");
        // Insets(top, right, bottom, left);
        playerControlsLayout.add(suggestionButton, 0, 0, 2, 1);
        GridPane.setHalignment(suggestionButton, HPos.CENTER);
        playerControlsLayout.add(accusationButton, 0, 1, 2, 1);
        GridPane.setHalignment(accusationButton, HPos.CENTER);
        playerControlsLayout.add(rollButton, 0, 2);
        GridPane.setMargin(rollButton, new Insets(0, 0, 10, 10));
        playerControlsLayout.add(endButton, 1, 2);
        GridPane.setMargin(endButton, new Insets(0, 10, 10, 10));
        
        return playerControlsLayout;        
    }
    
    private void createCardsWindow(String title, Color color) {
        selectCards cardsWindow = new selectCards();
        cardsWindow.show(title, color);
    }
    
    private BorderPane createUI() {
        BorderPane main = new BorderPane();
        main.setBackground(blackFill);
        
        main.setLeft(createLeftPanel());
        
        main.setCenter(createBoard());
        
        BorderPane rightPanel = new BorderPane();
        rightPanel.setTop(createCardsDisplay());
        rightPanel.setBottom(createPlayerControls());
        
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

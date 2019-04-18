/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author hungb
 */
public class boardEditor {
    
    private Stage clientStage;
    private Stage editorStage;
    
    private Font avenirLarge;
    private Font avenirNormal;
    private EditorTile[][] board;
    private EditorTileType lastSelected;
    private String mapName;
    private TextField mapNameField;
    
    
    /**
     * Creates buttons which sets the user's current "brush". This will allow them to "paint" their baord and make a custom map.
     * This will also have a text field which lets the user pick the name of their map and save it. 
     *
     * @return All the tile types which people can use to paint the board in a VBox.
     */
    private VBox createPalette() {
        VBox paletteLayout = new VBox();
        paletteLayout.setPadding(new Insets(0, 0, 0, 10));
        paletteLayout.setAlignment(Pos.CENTER);
        
        Label selectedLabel = new Label("Selected");
        selectedLabel.setFont(avenirLarge);
        selectedLabel.setTextFill(Color.WHITE);
        
        Label currentlySelected = new Label("HALL");
        currentlySelected.setFont(avenirNormal);
        currentlySelected.setTextFill(Color.WHITE);

        MenuItem roomButton = new MenuItem("Room", avenirLarge);
        roomButton.setBackgroundColor(Color.rgb(87, 73, 60));
        roomButton.setPrefWidth(120);
        roomButton.setColorScheme(Color.WHITE, Color.BLACK);
        roomButton.setOnMouseClicked(e -> {
            lastSelected = EditorTileType.ROOM;
            currentlySelected.setText(lastSelected.toString());
        });
        
        MenuItem emptyButton = new MenuItem("Empty", avenirLarge);
        emptyButton.setBackgroundColor(Color.rgb(7, 80, 2));
        emptyButton.setPrefWidth(120);
        emptyButton.setColorScheme(Color.WHITE, Color.BLACK);
        emptyButton.setOnMouseClicked(e -> {
            lastSelected = EditorTileType.EMPTY;
            currentlySelected.setText(lastSelected.toString());
        });
        
        MenuItem hallButton = new MenuItem("Hall", avenirLarge);
        hallButton.setBackgroundColor(Color.rgb(225, 147, 31));
        hallButton.setPrefWidth(120);
        hallButton.setColorScheme(Color.WHITE, Color.BLACK);
        hallButton.setOnMouseClicked(e -> {
            lastSelected = EditorTileType.HALL;
            currentlySelected.setText(lastSelected.toString());
        });
        
        MenuItem spawnButton = new MenuItem("Spawn", avenirLarge);
        spawnButton.setBackgroundColor(Color.rgb(60, 134, 1));
        spawnButton.setPrefWidth(120);
        spawnButton.setColorScheme(Color.WHITE, Color.BLACK);
        spawnButton.setOnMouseClicked(e -> {
            lastSelected = EditorTileType.START;
            currentlySelected.setText(lastSelected.toString());
        });
        
        MenuItem intrigueButton = new MenuItem("Intrigue", avenirLarge);
        intrigueButton.setBackgroundColor(Color.GOLD);
        intrigueButton.setPrefWidth(120);
        intrigueButton.setColorScheme(Color.WHITE, Color.BLACK);
        intrigueButton.setOnMouseClicked(e -> {
            lastSelected = EditorTileType.INTRIGUE;
            currentlySelected.setText(lastSelected.toString());
        });
        
        MenuItem doorUpButton = new MenuItem("Door Up", avenirLarge);
        doorUpButton.setBackgroundColor(Color.rgb(161, 97, 0));
        doorUpButton.setPrefWidth(120);
        doorUpButton.setColorScheme(Color.WHITE, Color.BLACK);
        doorUpButton.setOnMouseClicked(e -> {
            lastSelected = EditorTileType.DOOR_UP;
            currentlySelected.setText(lastSelected.toString().replace("_", " "));
        });
        
        MenuItem doorDownButton = new MenuItem("Door Down", avenirLarge);
        doorDownButton.setBackgroundColor(Color.rgb(161, 97, 0));
        doorDownButton.setPrefWidth(120);
        doorDownButton.setColorScheme(Color.WHITE, Color.BLACK);
        doorDownButton.setOnMouseClicked(e -> {
            lastSelected = EditorTileType.DOOR_DOWN;
            currentlySelected.setText(lastSelected.toString().replace("_", " "));
        });
        
        MenuItem doorLeftButton = new MenuItem("Door Left", avenirLarge);
        doorLeftButton.setBackgroundColor(Color.rgb(161, 97, 0));
        doorLeftButton.setPrefWidth(120);
        doorLeftButton.setColorScheme(Color.WHITE, Color.BLACK);
        doorLeftButton.setOnMouseClicked(e -> {
            lastSelected = EditorTileType.DOOR_LEFT;
            currentlySelected.setText(lastSelected.toString().replace("_", " "));
        });
        
        MenuItem doorRightButton = new MenuItem("Door Right", avenirLarge);
        doorRightButton.setBackgroundColor(Color.rgb(161, 97, 0));
        doorRightButton.setPrefWidth(120);
        doorRightButton.setColorScheme(Color.WHITE, Color.BLACK);
        doorRightButton.setOnMouseClicked(e -> {
            lastSelected = EditorTileType.DOOR_RIGHT;
            currentlySelected.setText(lastSelected.toString().replace("_", " "));
        });
        
        mapNameField = new TextField();
        mapNameField.setText("Custom Map 1");
        mapNameField.setPrefColumnCount(5);
        
        MenuItem createCSV = new MenuItem("Save", avenirLarge);
        createCSV.setOnMouseClicked(e-> {
            try {
                createCSV();
            } catch(CreationException ex) {
                Prompt saveError = new Prompt(ex.message);
                saveError.show();
            } catch(FileNotFoundException ex) {
                
            }
        });
        
        MenuItem backButton = new MenuItem("Back", avenirLarge);
        backButton.setOnMouseClicked(e -> {
            clientStage.show();
            editorStage.close();
        });
        
        paletteLayout.getChildren().addAll(selectedLabel, currentlySelected,
                roomButton, emptyButton, hallButton, spawnButton, 
                intrigueButton, doorUpButton, doorDownButton,
                doorLeftButton, doorRightButton, mapNameField, createCSV,
                backButton);
        return paletteLayout;
    }
    
    /**
     * Makes the tiles and renders them in the boardEditor.
     * 
     * @param x the width of the board
     * @param y the height of the board
     * @param pane where the tiles will be placed 
     */
    private void generateBoard(int x, int y, GridPane pane) {
        board = new EditorTile[y][x];
        
        for (int i=0; i < x; i++) {
            for (int j=0; j < y; j++) {
                Label l = new Label();
                l.setMinSize(38, 38);
                
                l.setOnMouseClicked(e -> {
                    try {
                        paintTile(l);
                    } catch (CreationException ex) {
                        Prompt error = new Prompt(ex.message);
                        error.show();
                    }
                });
                
                l.setStyle("-fx-border-color: black; -fx-background-color: #de971d;");
                board[j][i] = new EditorTile(l, "0", i, j);
                pane.add(l, i, j);
            }
        }
    }
    
    /**
     * Sets it so there is no door in Tile which corresponds to l
     * 
     * @param l label(tile) to have their door direction reverted.
     */
    public void resetDoorDirection(Label l) {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if(board[y][x].getLabel() == l){
                    board[y][x].setDoorDirection("-1");
                    return;
                }
            }
        }
    }
    
    /**
     * This method finds room tiles and groups them together into one room/"building".
     * It will do this by updating their roomId to the corresponding id.
     * This method runs recursively until it finishes.
     * 
     * @param x the x coordinate of a tile.
     * @param y the y coordinate of a tile.
     * @param roomId the highest of the current found room/s.
     */
    private void grabRoom(int x, int y, int roomId) {
        if(x<board[0].length-1){//If thereis a RIGHT tile
            if ("r".equals(board[y][x+1].getS())){
                board[y][x+1].setS("" + roomId);
                grabRoom(x+1, y, roomId);
            }
        }
        
        if(x>0){//If there is a LEFT tile
            if ("r".equals(board[y][x-1].getS())){
                board[y][x-1].setS("" + roomId);
                grabRoom(x-1, y, roomId);
            }
        }
        
        if(y<board.length-1){//If there is a tile BELOW
            if ("r".equals(board[y+1][x].getS())){
                board[y+1][x].setS("" + roomId);
                grabRoom(x, y+1, roomId);
            }
        }
            
        if(y>0){//If there is a tile ABOVE
            if ("r".equals(board[y-1][x].getS())){
                board[y-1][x].setS("" + roomId);
                grabRoom(x, y-1, roomId);
            }
        }
    }
    
    /**
     * Resets the rooms with roomsIds greater than 0 and sets them back to r.
     */
    private void ungrabRooms(){
        for(int y=0; y < board.length; y++){
            for(int x=0; x< board[0].length; x++){
                
                if(!board[y][x].getS().equals("0") && !board[y][x].getS().equals("-1")){
                    try{
                        Integer.parseInt(board[y][x].getS());
                        board[y][x].setS("r");
                    } catch(NumberFormatException e){

                    }        
                } 
            }
        }
    }
 
    /**
     * Changes the representation of the tile into something else.
     * 
     * @param l the tile to change string of.
     * @param s the string to change it to.
     */
    private void updateBoard(Label l, String s){
        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board[0].length; x++){
                if(board[y][x].getLabel() == l){
                    board[y][x].setS(s);
                    return;
                }
            }
        }
    }
    
    /**
     * This is called when a tile is clicked. It will "paint" the depending on their last selected "brush" from the palette.
     * This will change the tiles String representation to correspond their "brush" and changes the style of the Label(tile) accordingly.
     * 
     * @param l the tile to be "painted".
     * @throws CreationException 
     */
    private void paintTile(Label l) throws CreationException {
        int x = -1;
        int y = -1;
        
        for(int i =0; i<board.length; i++){
            for(int u =0; u<board[0].length; u++){
                if(board[i][u].getLabel() == l){
                    x = u;
                    y = i;
                }
            }
        }
        
        switch(lastSelected){
            case ROOM:
                updateBoard(l, "r");
                resetDoorDirection(l);
                l.setStyle("-fx-background-color: #5a4c41;");
                break;
                
            case EMPTY:
                updateBoard(l, "-1");
                resetDoorDirection(l);
                l.setStyle("-fx-border-color: black; -fx-background-color: #075002;");
                break;
                
            case HALL:
                updateBoard(l, "0");
                resetDoorDirection(l);
                l.setStyle("-fx-border-color: black; -fx-background-color: #de971d;");
                break;
                
            case INTRIGUE:
                updateBoard(l, "I");
                resetDoorDirection(l);
                l.setStyle("-fx-border-color: black; -fx-background-color: gold;");
                break;
                
            case START:
                updateBoard(l, "S");
                resetDoorDirection(l);
                l.setStyle("-fx-border-color: black; -fx-background-color: #378804;");
                break;
                
            case DOOR_UP:
                if (board[y][x].getS().equals("r")) {
                    if (y==0) {
                        throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                    }
                    if (y>0) {
                        if (board[y-1][x].getS().equals("S") || board[y-1][x].getS().equals("0")) {
                            board[y][x].setDoorDirection("up");
                            l.setStyle("-fx-background-color: #5a4c41; -fx-border-width: 5px 0px 0px 0px; -fx-border-style: solid; -fx-border-color: #A36200;");
                        } else {
                            throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                        }
                    }
                } else {
                    throw new CreationException("Doors must be placed in room tiles.");
                }
                break;
                
            case DOOR_DOWN:
                if (board[y][x].getS().equals("r")) {
                    if (y==board.length-1) {
                        throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                    }
                    if (y<board.length) {
                        if (board[y+1][x].getS().equals("S") || board[y+1][x].getS().equals("0")) {
                            board[y][x].setDoorDirection("down");
                            l.setStyle("-fx-background-color: #5a4c41; -fx-border-width: 0px 0px 5px 0px; -fx-border-style: solid; -fx-border-color: #A36200;");
                        } else {
                            throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                        }
                    }
                } else {
                    throw new CreationException("Doors must be placed in room tiles.");
                }
                break;
                
            case DOOR_LEFT:
                if (board[y][x].getS().equals("r")) {
                    if (x==0) {
                        throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                    } else if (x>0) {
                        if (board[y][x-1].getS().equals("S") || board[y][x-1].getS().equals("0")){
                            board[y][x].setDoorDirection("left");
                            l.setStyle("-fx-background-color: #5a4c41; -fx-border-width: 0px 0px 0px 5px; -fx-border-style: solid; -fx-border-color: #A36200;");
                        } else {
                            throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                        }
                    }
                } else {
                    throw new CreationException("Doors must be placed in room tiles.");
                }
                break;
                
            case DOOR_RIGHT:
                if (board[y][x].getS().equals("r")) {
                    if (x==board[0].length-1) {
                        throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                    }
                    if (x<board[0].length-1) {
                        if (board[y][x+1].getS().equals("S") || board[y][x+1].getS().equals("0")) {
                            board[y][x].setDoorDirection("right");
                            l.setStyle("-fx-background-color: #5a4c41; -fx-border-width: 0px 5px 0px 0px; -fx-border-style: solid; -fx-border-color: #A36200;");
                        } else {
                            throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                        }
                    }
                } else {
                    throw new CreationException("Doors must be placed in room tiles.");
                }
                break;
        }
    }
    
    /**
     * This is run when the "Save" button is pressed.
     * Creates 2 CSV files. These 2 files are representations of the custom board.
     * these 2 files are to be interpreted by the boardMappings class which can then load a map.
     * 
     * This will save the CSV files into a folder named after what the player wants to name their map.
     * 
     * @throws CreationException
     * @throws FileNotFoundException 
     */
    private void createCSV() throws CreationException, FileNotFoundException {
        int currentRoomInt = 1;
        int startTileCounter= 0;
        
        //reading textField
        mapName = mapNameField.getText();
        
        ungrabRooms();
        for(int y=0; y < board.length; y++){
            for(int x=0; x< board[0].length; x++){
                if(board[y][x].getS().equals("S")){
                    startTileCounter++;
                }
                
                
                else if((board[y][x].getS().equals("r"))){
                    board[y][x].setS("" + currentRoomInt);
                    grabRoom(x, y, currentRoomInt);
                    currentRoomInt++;
  
                }
                    
                
            }
        }
        
        if(currentRoomInt != 10){
            ungrabRooms();
            throw new CreationException("You must have 9 Rooms Exactly.");
        }
        
        if(startTileCounter != 6){
            ungrabRooms();
            throw new CreationException("There must be 6 starting Tiles");
        }
        
        String newName = mapName.replaceAll(" ", "");
        File tempFile = new File("./Maps/" +newName);
        if(!tempFile.exists()){
            new File("./Maps/" + newName).mkdirs();
            makeDoorCsv(newName);
            makeTileCsv(newName);
        } else {
            throw new CreationException("A custom Map with this name already exists. \nPick a new name, or delete the already existing one.");
        }
    }

    /**
     * Called by createCSV()
     * 
     * Creates the CSV file for the Doors.
     * 
     * @param name the name of the CSV file.
     * @throws FileNotFoundException 
     */
    private void makeDoorCsv(String name) throws FileNotFoundException{    
        List<String[]> csv = new ArrayList<>();
                
        for(int y=0; y < board.length; y++){
            for(int x=0; x< board[0].length; x++){
                if(!board[y][x].getDoorDirection().equals("-1")){
                     String[] temp = new String[4]; 
                     temp[0] = board[y][x].getS();
                    switch(board[y][x].getDoorDirection()){
                        case "left":
                            temp[1] = Integer.toString(x-1);
                            temp[2] = Integer.toString(y);
                            temp[3] = "L";
                            break;
                        case "right":
                            temp[1] = Integer.toString(x+1);
                            temp[2] = Integer.toString(y);
                            temp[3] = "R";
                            break;
                        case "down":
                            temp[1] = Integer.toString(x);
                            temp[2] = Integer.toString(y+1);
                            temp[3] = "D";
                            break;
                        case "up":
                            temp[1] = Integer.toString(x);
                            temp[2] = Integer.toString(y-1);
                            temp[3] = "U";
                            break;
                    }
                    
                    csv.add(temp);
                }
            }  
        }
        
        File csvFile = new File("./Maps/"+mapName+"/"+mapName+"Doors.csv");
        
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            csv.stream().map(this::convertToCSV).forEach(pw::println);
        }
    }    
    
    /**
     * Called by createCSV()
     * 
     * Creates the CSV file for the Tiles(everything but the doors).
     * 
     * @param name the name of the CSV file.
     * @throws FileNotFoundException 
     */
    private void makeTileCsv(String name) throws FileNotFoundException{
        List<String[]> csv = new ArrayList<>();
                
        for(int y=0; y < board.length; y++){
            String[] temp = new String[24];
            for(int x=0; x< board[0].length; x++){
                temp[x] = board[y][x].getS();
            }
            csv.add(temp);
        }
        
        File csvFile = new File("./Maps/" + mapName +"/" + mapName + "Tiles.csv");
        
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            csv.stream().map(this::convertToCSV).forEach(pw::println);
        }
    }
    
    /**
     * Separates an Array of Strings into COmma separated values.
     * 
     * @param data a row of the CSV file.
     * @return a String (CSV).
     */
    public String convertToCSV(String[] data) {
        return Stream.of(data).collect(Collectors.joining(","));
    }
    
    /**
     * Initialises the fonts.
     */
    private void initFonts() {
        avenirLarge = new Font(20);
        avenirNormal = new Font(15);
        try {
            avenirLarge = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 20);
            avenirNormal = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 15);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(boardEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This is called by the Game Client in order to open a new window with the board editor.
     * 
     * @param clientStage the stage to set the boardEditor scene to. 
     */
    public void startEditor(Stage clientStage) {
        editorStage = new Stage();
        editorStage.initModality(Modality.APPLICATION_MODAL);
        editorStage.setTitle("Board Editor");
        editorStage.setResizable(false);
        
        this.clientStage = clientStage;
        this.lastSelected = EditorTileType.HALL;
        
        initFonts();
        
        BorderPane editorLayout = new BorderPane();
        editorLayout.setBackground(new Background(new BackgroundFill(Color.rgb(7, 80, 2), CornerRadii.EMPTY, Insets.EMPTY)));
        editorLayout.setLeft(createPalette());
        
        GridPane boardPane = new GridPane();
        boardPane.setPadding(new Insets(10, 0, 0, 10));
        editorLayout.setCenter(boardPane);
        
        generateBoard(24, 25, boardPane);
        
        Scene scene = new Scene(editorLayout);
        editorStage.setScene(scene);
        editorStage.show();
    }
}
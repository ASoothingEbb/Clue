/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 *
 * @author zemig
 */
public class CustomBoardMaker extends Application{
    
    int width;
    int height;

    //Logic
    LabelStringPair[][] board;
    TileType lastSelected;
    
    
    //Components
    BorderPane root;
    VBox pallete;
    GridPane gridPane;
    TextField mapName;
    String mapNameString;
    
    
    Scene mainScene;
    Stage mainStage;

    private static class CreationException extends Exception {
        public String s;
        public CreationException(String message) {
            super(message);
            this.s = message;
            
        }
    }
    
    enum TileType{
        ROOM,
        DOOR_UP,
        DOOR_DOWN,
        DOOR_LEFT,
        DOOR_RIGHT,
        WALKABLE,
        INTRIGUE,
        EMPTY,
        START
        
    }

    public CustomBoardMaker(){
        mainStage = new Stage();   
        root = new BorderPane();
        gridPane = new GridPane();
        pallete = new VBox();
        lastSelected = TileType.WALKABLE;
        
       
        
        width = 1100;
        height = 980;
    }
    
    public static void main(String[] args) throws Exception{
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage.setTitle("Board Customiser");
        mainStage.setResizable(false);
        mainStage.initModality(Modality.APPLICATION_MODAL);
        
        addPallete();
        root.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        
        makeGrid(24, 25, gridPane);
 
        
        
        mainScene = new Scene(root, width, height);
        mainStage.setScene(mainScene);
        mainStage.show();
    }
    
    /**
     * 
     * 
     * @param x width of the board
     * @param y height of the board
     * @param pane GridPane Object
     */
    public void makeGrid(int x, int y, GridPane pane){
        board = new LabelStringPair[y][x];
        for(int i = 0; i < x; i++){
            for(int u = 0; u < y; u++){
                Label l = new Label();
                l.setMinSize(35, 35);
                l.setMaxSize(35, 35);          
               //Label mouse bheaviour
                EventHandler<MouseEvent> mouseHandler = (MouseEvent mouseEvent) -> {
                
                    try {
                        paintTile(l);
                    } catch (CreationException ex) {
                        createAlert(ex.s);
                    }
                };
                
                l.setOnMouseClicked(mouseHandler);
                
                
                l.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");
                board[u][i] = new LabelStringPair(l, "0", i, u);
                pane.setConstraints(l, i , u);
                pane.getChildren().add(l);
            }
        }
    }
    /**
     * This method changes the appearance of a label depending on the current "brush" that is being used.
     * @param l A Label object
     */
    public void paintTile(Label l) throws CreationException{
        int x = -1;
        int y = -1;
        
        for(int i =0; i<board.length; i++){
            for(int u =0; u<board[0].length; u++){
                if(board[i][u].l == l){
                    x = u;
                    y = i;
                }
            }
        }
        
        switch(lastSelected){
            case ROOM:
                updateBoard(l, "r");
                resetDoorDirection(l);
                l.setStyle("-fx-background-color: #EA3E3E;");
                break;
                
            case EMPTY:
                updateBoard(l, "-1");
                resetDoorDirection(l);
                l.setStyle("-fx-border-color: black; -fx-background-color: lime;");
                break;
                
            case WALKABLE:
                updateBoard(l, "0");
                resetDoorDirection(l);
                l.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");
                break;
                
            case INTRIGUE:
                updateBoard(l, "I");
                resetDoorDirection(l);
                l.setStyle("-fx-border-color: black; -fx-background-color: orange;");
                break;
                
            case START:
                updateBoard(l, "S");
                resetDoorDirection(l);
                l.setStyle("-fx-border-color: black; -fx-background-color: pink;");
                break;
                
            case DOOR_UP:
                if(board[y][x].s.equals("r")){
                    if(y==0){
                        throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                    }
                    if(y>0){
                        if(board[y-1][x].s.equals("S") || board[y-1][x].s.equals("0")){
                            board[y][x].doorDirection = "up";
                            l.setStyle("-fx-background-color: #EA3E3E; -fx-border-width: 10px 0px 0px 0px; -fx-border-style: solid; -fx-border-color: purple;");
                        }else {
                            throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                        }
                    }
                } else {
                    throw new CreationException("Doors must be placed in room tiles.");
                }
                break;
                
            case DOOR_DOWN:
                if(board[y][x].s.equals("r")){
                    if(y==board.length-1){
                        throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                    }
                    if(y<board.length){
                        if(board[y+1][x].s.equals("S") || board[y+1][x].s.equals("0")){
                            board[y][x].doorDirection = "down";
                            l.setStyle("-fx-background-color: #EA3E3E; -fx-border-width: 0px 0px 10px 0px; -fx-border-style: solid; -fx-border-color: purple;");
                        }else {
                            throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                        }
                    }
                } else {
                    throw new CreationException("Doors must be placed in room tiles.");
                }
                break;
                
            case DOOR_LEFT:
                if(board[y][x].s.equals("r")){
                    if(x==0){
                        throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                    }
                    if(x>0){
                        if(board[y][x-1].s.equals("S") || board[y][x-1].s.equals("0")){
                            board[y][x].doorDirection = "left";
                            l.setStyle("-fx-background-color: #EA3E3E; -fx-border-width: 0px 0px 0px 10px; -fx-border-style: solid; -fx-border-color: purple;");
                        }else {
                            throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                        }
                    }
                } else {
                    throw new CreationException("Doors must be placed in room tiles.");
                }
                break;
                
            case DOOR_RIGHT:
                if(board[y][x].s.equals("r")){
                    if(x==board[0].length-1){
                        throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                    }
                    if(x<board[0].length-1){
                        if(board[y][x+1].s.equals("S") || board[y][x+1].s.equals("0")){
                            board[y][x].doorDirection = "right";
                            l.setStyle("-fx-background-color: #EA3E3E; -fx-border-width: 0px 10px 0px 0px; -fx-border-style: solid; -fx-border-color: purple;");
                        }else {
                            throw new CreationException("Doors must be placed facing either a Walkable tile or a Starting Tile");
                        }
                    }
                } else {
                    throw new CreationException("Doors must be placed in room tiles.");
                }
                break;
        }
    }
    
    public void resetDoorDirection(Label l){
        for(int y = 0; y < board.length; y++){
                for(int x = 0; x < board[0].length; x++){
                    if(board[y][x].l == l){
                        board[y][x].doorDirection = "-1";
                        return;
                    }
                }
            }
    }
    public void addPallete(){
        
        //Different "Brushes" Available
        CustomBoardPallete room = new CustomBoardPallete("Room", TileType.ROOM);
        CustomBoardPallete empty = new CustomBoardPallete("Empty", TileType.EMPTY);
        CustomBoardPallete walkable = new CustomBoardPallete("Walkable", TileType.WALKABLE);
        CustomBoardPallete start = new CustomBoardPallete("Start", TileType.START);
        CustomBoardPallete intrigue = new CustomBoardPallete("Intregue", TileType.INTRIGUE);
        
        CustomBoardPallete doorUp = new CustomBoardPallete("Door Up", TileType.DOOR_UP);
        CustomBoardPallete doorDown = new CustomBoardPallete("Door Down", TileType.DOOR_DOWN);
        CustomBoardPallete doorLeft = new CustomBoardPallete("Door Left", TileType.DOOR_LEFT);
        CustomBoardPallete doorRight = new CustomBoardPallete("Door Right", TileType.DOOR_RIGHT);
        mapName = new TextField("Custom Map 1");
        
        Button createCSV = new Button("Confirm");
        
        
        //Alerts
        
                
        
        //Button Functionality
        room.setOnMouseClicked(e -> lastSelected = room.getTileType());
        empty.setOnMouseClicked(e -> lastSelected = empty.getTileType());
        walkable.setOnMouseClicked(e -> lastSelected = walkable.getTileType());
        start.setOnMouseClicked(e -> lastSelected = start.getTileType());
        intrigue.setOnMouseClicked(e -> lastSelected = intrigue.getTileType());
        doorUp.setOnMouseClicked(e -> lastSelected = doorUp.getTileType());
        doorDown.setOnMouseClicked(e -> lastSelected = doorDown.getTileType());
        doorLeft.setOnMouseClicked(e -> lastSelected = doorLeft.getTileType());
        doorRight.setOnMouseClicked(e -> lastSelected = doorRight.getTileType());
        createCSV.setOnAction(e -> {
            try {
                createCSV();
            } catch (CreationException ex) {
                createAlert(ex.s);
            } catch (FileNotFoundException ex) {
            }
        });
        
        //Button Style
        room.setStyle("-fx-background-color: #EA3E3E;"
                    + "-fx-border-color: black;"
                    + "-fx-font-weight: 700;");
           
        empty.setStyle("-fx-background-color: lime ;"
                    + "-fx-border-color: black;"
                    + "-fx-font-weight: 700;");
        
        walkable.setStyle("-fx-background-color: lightgray ;"
                    + "-fx-border-color: black;"
                    + "-fx-font-weight: 700;");
        
        start.setStyle("-fx-background-color: pink ;"
                    + "-fx-border-color: black;"
                    + "-fx-font-weight: 700;");
        
        intrigue.setStyle("-fx-background-color: orange;"
                    + "-fx-border-color: black;"
                    + "-fx-font-weight: 700;");
        
        doorUp.setStyle("-fx-background-color: #EA3E3E;"
                    + "-fx-border-color: black;"
                    + "-fx-font-weight: 700;");
        
        doorDown.setStyle("-fx-background-color: #EA3E3E;"
                    + "-fx-border-color: black;"
                    + "-fx-font-weight: 700;");
        
        doorLeft.setStyle("-fx-background-color: #EA3E3E;"
                    + "-fx-border-color: black;"
                    + "-fx-font-weight: 700;");
        
        doorRight.setStyle("-fx-background-color: #EA3E3E;"
                    + "-fx-border-color: black;"
                    + "-fx-font-weight: 700;");
      
        
        
        pallete.setAlignment(Pos.BASELINE_CENTER);
        pallete.getChildren().addAll(room, empty, walkable, start, intrigue, doorUp, doorDown, doorLeft, doorRight, createCSV, mapName);
        
        root.setLeft(pallete);
        
    }
    
    public void setTile(TileType t){
        lastSelected = t;
    }
    
    public void updateBoard(Label l, String s){
    
            for(int y = 0; y < board.length; y++){
                for(int x = 0; x < board[0].length; x++){
                    if(board[y][x].l == l){
                        board[y][x].s = s;
                        return;
                    }
                }
            }
    }
    
    public void createCSV() throws CreationException, FileNotFoundException{
        int currentRoomInt = 1;
        int startTileCounter= 0;
        
        //reading textField
        mapNameString = mapName.getText();
        
        ungrabRooms();
        for(int y=0; y < board.length; y++){
            for(int x=0; x< board[0].length; x++){
                if(board[y][x].s.equals("S")){
                    startTileCounter++;
                }
                
                
                else if((board[y][x].s.equals("r"))){
                    board[y][x].s = "" + currentRoomInt;
                    grabRooms(x, y, currentRoomInt);
                    currentRoomInt++;
  
                }
                    
                
                System.out.print(board[y][x] + " ");
            }
            System.out.println("");
        }
        
        if(currentRoomInt > 10){
            ungrabRooms();
            throw new CreationException("You can't have more than 9 rooms.");
        }
        if(currentRoomInt == 1){
            ungrabRooms();
            throw new CreationException("You must have at least 1 Room.");
        }
        
        if(startTileCounter != 6){
            ungrabRooms();
            throw new CreationException("There must be 6 starting Tiles");
        }
        
        String newName = mapNameString.replaceAll(" ", "");
        File tempFile = new File("./Maps/" +newName);
        if(!tempFile.exists()){
            new File("./Maps/" + newName).mkdirs();
            makeDoorCsv(newName);
            makeTileCsv(newName);
        } else {
            throw new CreationException("A custom Map with this name already exists. \nPick a new name, or delete the already existing one.");
        }
        
    }
    
    public void grabRooms(int x, int y, int roomId){
        
        if(x<board[0].length-1){//If thereis a RIGHT tile
            if ("r".equals(board[y][x+1].s)){
                board[y][x+1].s = "" + roomId;
                grabRooms(x+1, y, roomId);
            }
        }
        
        if(x>0){//If there is a LEFT tile
            if ("r".equals(board[y][x-1].s)){
                board[y][x-1].s = "" + roomId;
                grabRooms(x-1, y, roomId);
            }
        }
        
        if(y<board.length-1){//If there is a tile BELOW
            if ("r".equals(board[y+1][x].s)){
                board[y+1][x].s = "" + roomId;
                grabRooms(x, y+1, roomId);
            }
        }
            
        if(y>0){//If there is a tile ABOVE
            if ("r".equals(board[y-1][x].s)){
                board[y-1][x].s = "" + roomId;
                grabRooms(x, y-1, roomId);
            }
        }
    }
    
    public void createAlert(String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Clue Board Creation Alert");
        alert.setContentText(message);
        alert.setHeaderText(null);
        
        alert.showAndWait();
    }
    
    public void ungrabRooms(){
        for(int y=0; y < board.length; y++){
            for(int x=0; x< board[0].length; x++){
                
                if(!board[y][x].s.equals("0") && !board[y][x].s.equals("-1")){
                    try{
                        Integer.parseInt(board[y][x].s);
                        board[y][x].s = "r";
                    } catch(NumberFormatException e){

                    }        
                } 
            }
        }
    }
    
    public class LabelStringPair{

        public Label l;
        public String s;
        public int x;
        public int y;
        public int roomId;
        public String doorDirection;
        
        public LabelStringPair(Label l, String s, int x, int y){
            this.l = l;
            this.s = s;
            this.x = x;
            this.y = y;
            this.roomId = -1;
            this.doorDirection = "-1";
        }
        
        @Override
        public String toString(){
            if(s.equals("0")){
                return " ";
            }
            
            return s;// s + "(" + x + "," + y+ ")" ;
        }
        

    }
    
    public void makeDoorCsv(String name) throws FileNotFoundException{    
        List<String[]> csv = new ArrayList<>();
                
        for(int y=0; y < board.length; y++){
            for(int x=0; x< board[0].length; x++){
                if(!board[y][x].doorDirection.equals("-1")){
                     String[] temp = new String[3]; 
                     temp[0] = board[y][x].s;
                    switch(board[y][x].doorDirection){
                        case "left":
                            temp[1] = Integer.toString(x-1);
                            temp[2] = Integer.toString(y);
                            break;
                        case "right":
                            temp[1] = Integer.toString(x+1);
                            temp[2] = Integer.toString(y);
                            break;
                        case "down":
                            temp[1] = Integer.toString(x);
                            temp[2] = Integer.toString(y+1);
                            break;
                        case "up":
                            temp[1] = Integer.toString(x);
                            temp[2] = Integer.toString(y-1);
                            break;
                    }
                    
                    csv.add(temp);
                }
            }  
        }
        
        File csvFile = new File("./Maps/"+mapNameString+"/"+mapNameString+"Door"+".csv");
        
        try (PrintWriter pw = new PrintWriter(csvFile)) {
        csv.stream().map(this::convertToCSV).forEach(pw::println);
        }
    }    
    public void makeTileCsv(String name) throws FileNotFoundException{
        List<String[]> csv = new ArrayList<>();
                
        for(int y=0; y < board.length; y++){
            String[] temp = new String[24];
            for(int x=0; x< board[0].length; x++){
                temp[x] = board[y][x].s;
            }
            csv.add(temp);
        }
        
        File csvFile = new File("./Maps/"+mapNameString+"/" + mapNameString + ".csv");
        
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            csv.stream().map(this::convertToCSV).forEach(pw::println);
        }
    }
    
    public String convertToCSV(String[] data) {
        return Stream.of(data).collect(Collectors.joining(","));
    }
   
}

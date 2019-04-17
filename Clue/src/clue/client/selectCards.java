/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import clue.GameController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author hungb
 */
public class selectCards {
    
    private Stage stage;
    
    private String name;
    private Font avenir;
    private Font avenirText;
    private String actionType;
    private Color color;
    private HashMap<String, String> ImagePathMap;
    private HashMap<String, String> CardNameMap;
    private int currentRoom;
    private GameController gameInterface;
    
    private final Background greenFill = new Background(new BackgroundFill(Color.rgb(7, 80, 2), CornerRadii.EMPTY, Insets.EMPTY));
    
    private GridPane createCardsUI() {
        GridPane cards = new GridPane();
        cards.setBackground(greenFill);
        
        ArrayList<String> characters = new ArrayList<>();
        ArrayList<String> weapons = new ArrayList<>();
        ArrayList<String> rooms = new ArrayList<>();
        
        CardNameMap.entrySet().forEach((entry) -> {
            if (entry.getKey().contains("character")) {
                characters.add(entry.getValue());
            } else if (entry.getKey().contains("weapon")) {
                weapons.add(entry.getValue());
            } else if (entry.getKey().contains("room")) {
                rooms.add(entry.getValue());
            }
        });
         
        Image character = null;
        try {
            character = new Image(new FileInputStream(new File(ImagePathMap.get("character0"))));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        ImageView characterView = new ImageView(character);
        
        ComboBox characterOptions = new ComboBox();
        characterOptions.getItems().addAll(characters);
        characterOptions.setValue(CardNameMap.get("character0"));
        characterOptions.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            characterView.setImage(getImage(newValue.toString()));
        });
        
        Image weapon = null;
        try {
            weapon = new Image(new FileInputStream(new File(ImagePathMap.get("weapon0"))));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        ImageView weaponView = new ImageView(weapon);
        
        ComboBox weaponOptions = new ComboBox();
        weaponOptions.getItems().addAll(weapons);
        weaponOptions.setValue(CardNameMap.get("weapon0"));
        weaponOptions.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            weaponView.setImage(getImage(newValue.toString()));
        });
        
        Image room = null;
        ImageView roomView;
        ComboBox roomOptions = new ComboBox();
        
        if (name.equals("suggestion")) {
            try {
                room = new Image(new FileInputStream(new File(ImagePathMap.get("room"+currentRoom))));
            } catch(FileNotFoundException ex) {
                System.out.println("File not found");
            }
            roomOptions.getItems().add(CardNameMap.get("room"+currentRoom));
            roomOptions.setValue(CardNameMap.get("room"+currentRoom));
            roomView = new ImageView(room);
        } else {
            try {
                room = new Image(new FileInputStream(new File(ImagePathMap.get("room0"))));
            } catch(FileNotFoundException ex) {
                System.out.println("File not found");
            }
            roomOptions.getItems().addAll(rooms);
            roomOptions.setValue(CardNameMap.get("room0"));
            roomView = new ImageView(room);
            roomOptions.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            roomView.setImage(getImage(newValue.toString()));
        });
        }
        
        MenuItem sendCards = new MenuItem(actionType, avenir);
        sendCards.setActiveColor(color);
        sendCards.setOnMouseClicked(e -> {
            if (characterOptions.getValue().toString().contains("Select")) {
                Prompt selectCard = new Prompt("Select the suspect");
                selectCard.showAndWait();
            } else if (weaponOptions.getValue().toString().contains("Select")) {
                Prompt selectCard = new Prompt("Select the murder weapon");
                selectCard.showAndWait();
            } else {
                String personKey = getKey(CardNameMap, characterOptions.getValue().toString());
                int personCard = Integer.valueOf(personKey.substring(personKey.length() - 1));
                String weaponKey = getKey(CardNameMap, weaponOptions.getValue().toString());
                int weaponCard = Integer.valueOf(weaponKey.substring(weaponKey.length() - 1));
                String roomKey = getKey(CardNameMap, roomOptions.getValue().toString());
                int roomCard = Integer.valueOf(roomKey.substring(roomKey.length() - 1));
                if (actionType.equals("Suggestion")) {
                    gameInterface.suggest(personCard, weaponCard);
                } else if (actionType.equals("Accusation")) {
                    gameInterface.accuse(personCard, weaponCard, roomCard);
                }
                stage.close();
            }
        });
        
        cards.add(characterView, 0, 0);
        GridPane.setMargin(characterView, new Insets(10));
        cards.add(characterOptions, 0, 1);
        GridPane.setHalignment(characterOptions, HPos.CENTER);
        
        cards.add(weaponView, 1, 0);
        GridPane.setMargin(weaponView, new Insets(10));
        cards.add(weaponOptions, 1, 1);
        GridPane.setHalignment(weaponOptions, HPos.CENTER);
        
        cards.add(roomView, 2, 0);
        GridPane.setMargin(roomView, new Insets(10, 0, 10, 10));
        cards.add(roomOptions, 2, 1);
        GridPane.setHalignment(roomOptions, HPos.CENTER);
        
        cards.add(sendCards, 0, 2, 3, 1);
        GridPane.setHalignment(sendCards, HPos.CENTER);
        
        return cards;
    }
    
    private Image getImage(String key) {
        try {
            Image image = new Image(new FileInputStream(new File(ImagePathMap.get(getKey(CardNameMap, key)))));
            return image;
        } catch(FileNotFoundException ex) {
            System.out.println("File not found");
        }
        return null;
    }
    
    private String getKey(HashMap<String, String> map, String value) {
        for (HashMap.Entry entry: map.entrySet()) {
            if (entry.getValue().toString().equals(value)) {
                return entry.getKey().toString();
            }
        }
        return "";
    }
    
    private void initFonts() {
        avenir = new Font(30);
        avenirText = new Font(20);
        try {
            avenir = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 30);
            avenirText = Font.loadFont(new FileInputStream(new File("./resources/fonts/avenir-Book.ttf")), 20);
        } catch(FileNotFoundException ex) {
            System.out.println("Font not found");
        }
        
    }
    
    public void show(String name, Color color, int room, HashMap<String,String> ImagePathMap, HashMap<String, String> CardNameMap, GameController gameController) {
        stage = new Stage();
        
        this.name = name;        
        this.gameInterface = gameController;
        this.actionType = name;
        this.color = color;
        this.ImagePathMap = ImagePathMap;
        this.CardNameMap = CardNameMap;
        this.currentRoom = room;
        
        initFonts();
        
        stage.setTitle(name);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        //stage.initStyle(StageStyle.UNDECORATED);
        
        Scene scene = new Scene(createCardsUI());
        stage.setScene(scene);
        stage.show();
    }
}

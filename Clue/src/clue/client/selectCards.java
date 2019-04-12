/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author hungb
 */
public class selectCards {
    
    private Stage stage;
    
    private Font avenir = Font.loadFont(getClass().getResourceAsStream("assets/fonts/Avenir-Book.ttf"), 30);
    private Font avenirText = Font.loadFont(getClass().getResourceAsStream("assets/fonts/Avenir-Book.ttf"), 20);
    private String actionType;
    private Color color;
    private Background darkGreyFill = new Background(new BackgroundFill(Color.rgb(42, 42, 42), CornerRadii.EMPTY, Insets.EMPTY));
    
    private GridPane createCardsUI() {
        GridPane cards = new GridPane();
        cards.setBackground(darkGreyFill);
        
        Card characterCard = new Card(new Image(getClass().getResourceAsStream("assets/card.png")), "character", 1);
        ImageView characterView = new ImageView(characterCard.getImage());
        
        ComboBox character = new ComboBox();
        character.getItems().addAll("Miss Scarlet", "Professor Plum", "Mrs. Peacock", "Reverend Green", "Colonel Mustard", "Mrs. White");
        character.setValue("Select Character");
        
        Card weaponCard = new Card(new Image(getClass().getResourceAsStream("assets/card.png")), "weapon", 1);
        ImageView weaponView = new ImageView(weaponCard.getImage());
        
        ComboBox weapon = new ComboBox();
        weapon.getItems().addAll("Candlestick", "Dagger", "Lead pipe", "Revolver", "Rope", "Spanner");
        weapon.setValue("Select Weapon");
        
        Card roomCard = new Card(new Image(getClass().getResourceAsStream("assets/card.png")), "room", 1);
        ImageView roomView = new ImageView(roomCard.getImage());
        
        ComboBox room = new ComboBox();
        room.getItems().addAll("Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billard Room", "Library", "Lounge", "Hall", "Study");
        room.setValue("Select Room");
        
        MenuItem sendCards = new MenuItem(actionType, avenir);
        sendCards.setActiveColor(color);
        sendCards.setOnMouseClicked(e -> {
            if (character.getValue().toString().contains("Select")) {
                Prompt selectCard = new Prompt("Select the suspect");
                selectCard.showAndWait();
            } else if (weapon.getValue().toString().contains("Select")) {
                Prompt selectCard = new Prompt("Select the murder weapon");
                selectCard.showAndWait();
            } else if (room.getValue().toString().contains("Select")) {
                Prompt selectCard = new Prompt("Select the murder room");
                selectCard.showAndWait();
            } else {
                System.out.println(actionType);
                System.out.println("Character: " + character.getValue());
                System.out.println("Weapon: " + weapon.getValue());
                System.out.println("Room: " + room.getValue());
                stage.close();
            }
        });
        
        cards.add(characterView, 0, 0);
        GridPane.setMargin(characterView, new Insets(10));
        cards.add(character, 0, 1);
        GridPane.setHalignment(character, HPos.CENTER);
        
        cards.add(weaponView, 1, 0);
        GridPane.setMargin(weaponView, new Insets(10));
        cards.add(weapon, 1, 1);
        GridPane.setHalignment(weapon, HPos.CENTER);
        
        cards.add(roomView, 2, 0);
        GridPane.setMargin(roomView, new Insets(10, 0, 10, 10));
        cards.add(room, 2, 1);
        GridPane.setHalignment(room, HPos.CENTER);
        
        cards.add(sendCards, 0, 2, 3, 1);
        GridPane.setHalignment(sendCards, HPos.CENTER);
        
        return cards;
    }
    
    public void show(String name, Color color) {
        stage = new Stage();
        
        this.actionType = name;
        this.color = color;
        
        stage.setTitle(name);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        //stage.initStyle(StageStyle.UNDECORATED);
        
        Scene scene = new Scene(createCardsUI());
        stage.setScene(scene);
        stage.show();
    }
}

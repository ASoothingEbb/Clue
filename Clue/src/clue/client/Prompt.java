/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

/**
 *
 * @author hungb
 */
public class Prompt extends Dialog {
    
    private final DialogPane dialogPane;
    private Font avenirLarge;
    private Font avenirMedium;
    private VBox layout;
    private Label title;
    private Label message;
    
    /**
     * Makes and Styles the dialog box(prompt).
     * 
     * @param text the message displayed in the prompt.
     */
    public Prompt(String text) {
        initStyle(StageStyle.UNDECORATED);
        dialogPane = getDialogPane();
        dialogPane.setBackground(new Background(new BackgroundFill(Color.rgb(56, 45, 40), CornerRadii.EMPTY, Insets.EMPTY)));
        
        initFonts();
        
        layout = new VBox();
        
        title = new Label("Error:");
        title.setTextFill(Color.WHITE);
        title.setFont(avenirLarge);
        
        message = new Label(text);
        message.setTextFill(Color.WHITE);
        message.setFont(avenirMedium);
        
        MenuItem okButton = new MenuItem("OK", avenirLarge);
        okButton.setOnMouseClicked(e -> {
            setResult(true);
            close();
        });
        
        layout.getChildren().addAll(title, message, okButton);
        layout.setAlignment(Pos.CENTER);
        
        dialogPane.setContent(layout);
    }
    
    
    /**
     * Initialises the Fonts.
     */
    private void initFonts() {
        avenirLarge = new Font(20);
        avenirMedium = new Font(15);
        try {
            avenirLarge = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 20);
            avenirMedium = Font.loadFont(new FileInputStream(new File("./resources/fonts/Avenir-Book.ttf")), 15);
            
        } catch(FileNotFoundException ex) {
            
        }
    }
    
    /**
     * Sets the text message in this prompt
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message.setText(message);
    }
    
    /**
     * Sets the title of this prompt
     * @param title the title of the prompt
     */
    public void setLabelTitle(String title) {
        this.title.setText(title);
    }
    
    /**
     * Sets the image of a card to be displayed
     * @param card the image of the card
     */
    public void setImage(ImageView  card) {
        layout.getChildren().add(2, card);
    }
    
    /**
     * Displays one or more Cards in the prompt
     * @param cards list of card images to be shown
     */
    public void setImage(ImageView[] cards) {
        HBox cardsLayout = new HBox();
        cardsLayout.setAlignment(Pos.CENTER);
        cardsLayout.setSpacing(10);
        cardsLayout.getChildren().addAll(cards[0], cards[1], cards[2]);
        layout.getChildren().add(2, cardsLayout);
    }
}

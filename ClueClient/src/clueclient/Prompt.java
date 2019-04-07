/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clueclient;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
    
    public Prompt(String text) {
        setTitle("Error");
        initStyle(StageStyle.UNDECORATED);
        dialogPane = getDialogPane();
        dialogPane.setBackground(new Background(new BackgroundFill(Color.rgb(160, 160, 160), CornerRadii.EMPTY, Insets.EMPTY)));
        
        VBox layout = new VBox();
        
        Label title = new Label("Error:");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.loadFont(getClass().getResourceAsStream("assets/fonts/Avenir-Book.ttf"),20));
        
        Label message = new Label(text);
        message.setTextFill(Color.WHITE);
        message.setFont(Font.loadFont(getClass().getResourceAsStream("assets/fonts/Avenir-Book.ttf"), 15));
        
        MenuItem okButton = new MenuItem("OK", Font.loadFont(getClass().getResourceAsStream("assets/fonts/Avenir-Book.ttf"),20));
        okButton.setOnMouseClicked(e -> {
            setResult(true);
            close();
        });
        
        layout.getChildren().addAll(title, message, okButton);
        layout.setAlignment(Pos.CENTER);
        
        dialogPane.setContent(layout);
    }
}

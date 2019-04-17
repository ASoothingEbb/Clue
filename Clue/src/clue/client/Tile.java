/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author hungb
 */
public class Tile extends Label {
    
    private PlayerSprite player;
    
    public Tile(int size) {
        setPrefWidth(size);
        setPrefHeight(size);
        
        
        
        //setStroke(Color.BLACK);
        //setStrokeType(StrokeType.INSIDE);
        //setFill(Color.BROWN);
        
        //setFill(Color.rgb(200, 200, 200, 0.0));   
    }
    
    public void setPlayer(PlayerSprite player) {
        this.player = player;
    }
    
    public void removePlayer(PlayerSprite player) {
        this.player = null;
    }
    
    public void setColor(Color color) {
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}

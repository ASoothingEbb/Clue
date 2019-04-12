/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author hungb
 */
public class Tile extends Rectangle {
    
    private Player player;
    
    public Tile(int size) {
        setWidth(size);
        setHeight(size);
        
        setStroke(Color.BLACK);
        setStrokeType(StrokeType.INSIDE);
        //setFill(Color.BROWN);
        
        setFill(Color.rgb(200, 200, 200, 0.0));   
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void removePlayer(Player player) {
        this.player = null;
    }
}

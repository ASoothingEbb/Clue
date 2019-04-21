/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 *
 * @author hungb
 */
public class WeaponSprite extends Label {
    private int positionX;
    private int positionY;    
    
    /**
     * Creates a WeaponSprite
     * @param x the x coordinate where the weapon sprite is
     * @param y the y coordinate where the weapon sprite is
     * @param sprite the path to the image of the weapon sprite
     */
    public WeaponSprite(int x, int y, String sprite) {
        this.positionX = x;
        this.positionY = y;
        
        Image image = null;
        try {
            image = new Image(new FileInputStream(new File(sprite)));
        } catch(FileNotFoundException ex) {
            
        }
        
        ImageView weapon = new ImageView(image);
        weapon.setFitHeight(32);
        weapon.setFitWidth(32);
        setGraphic(weapon);
    }
    
    /**
     * Changes where the weapon sprite is being rendered.
     * @param x the new x coordinate of where to render the weapon sprite
     * @param y the new x coordinate of where to render the weapon sprite
     * @param board the board where the weapon sprite is 
     * @param weapon the weapon sprite to be moved
     */
    public void move(int x, int y, StackPane[][] board, WeaponSprite weapon) {
        board[positionY][positionX].getChildren().remove(weapon);
        board[y][x].getChildren().add(weapon);
        positionX = x;
        positionY = y;
    }
}

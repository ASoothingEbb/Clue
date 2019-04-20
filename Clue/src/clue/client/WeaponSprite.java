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
    
    public void move(int x, int y, StackPane[][] board, WeaponSprite weapon) {
        board[positionY][positionX].getChildren().remove(weapon);
        board[y][x].getChildren().add(weapon);
        positionX = x;
        positionY = y;
    }
}

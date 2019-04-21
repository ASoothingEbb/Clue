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
public class PlayerSprite extends Label {
    private int positionX;
    private int positionY;
    
    /**
     * Creates a PlayerSprite with specific position x, y and image.
     * 
     * @param positionX the x coordinate of where it is in the board StackPane[][] from gameInstance.
     * @param positionY the y coordinate of where it is in the board StackPane[][] from gameInstance.
     * @param sprite the path to a PNG.
     */
    public PlayerSprite(int positionX, int positionY, String sprite) {
        this.positionX = positionX;
        this.positionY = positionY;
        Image image = null;
        try {
            image = new Image(new FileInputStream(new File(sprite)));
        } catch(FileNotFoundException ex) {
            
        }
        ImageView characterView = new ImageView(image);
        characterView.setFitHeight(32);
        characterView.setFitWidth(32);
        setGraphic(characterView);
    }
    
    /**
     * Re-renders the player into a new position on the board.
     * 
     * @param x the x coordinate of where to move the sprite to.
     * @param y the y coordinate of where to move the sprite to.
     * @param board the map which the player is in.
     * @param player the sprite of the player.
     */
    public void move(int x, int y, StackPane[][] board, PlayerSprite player) {
        board[positionY][positionX].getChildren().remove(player);
        board[y][x].getChildren().add(player);
        positionX = x;
        positionY = y;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 *
 * @author hungb
 */
public class PlayerSprite extends Label {
    private int positionX;
    private int positionY;
    private String character;
    
    public PlayerSprite(int positionX, int positionY, String character) {
        setText(character);
        setTextFill(Color.WHITE);
        this.positionX = positionX;
        this.positionY = positionY;
        this.character = character;
    }
    
    public void move(int x, int y, StackPane[][] board, PlayerSprite player) {
        board[positionY][positionX].getChildren().remove(player);
        board[y][x].getChildren().add(player);
        positionX = x;
        positionY = y;
    }
}

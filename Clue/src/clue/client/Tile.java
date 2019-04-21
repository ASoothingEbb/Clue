/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 *
 * @author hungb
 */
public class Tile extends Label {
    
    /**
     * Creates a Tile as a label
     * @param size the size of the tile in pixels
     */
    public Tile(int size) {
        setPrefWidth(size);
        setPrefHeight(size);

    }

    /**
     * Sets the colour of the tile
     * @param color the colour to change the tile to
     */
    public void setColor(Color color) {
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}

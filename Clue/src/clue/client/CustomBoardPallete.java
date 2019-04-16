/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import clue.client.CustomBoardMaker.TileType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;

/**
 *
 * @author zemig
 */
public class CustomBoardPallete extends Button{
    
    TileType type;
    Background background;
    Button tile;
    
    public CustomBoardPallete(String name, TileType t){
        super(name);
        type = t;
        tile = this;
        
        tile.setMinSize(50, 50);
        tile.setMaxSize(100, 100);
    }
    
    public TileType getTileType(){
        return type;
    }
    
}

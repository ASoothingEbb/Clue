/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.tile.Room;
import clue.tile.Tile;

/**
 * Door object -- only used to construct doors for BoardMappings
 * @author Malter
 */
public class Door {
    
    private int xRoom, yRoom, xTile, yTile;
    /**
     * constructor for making a door to be used by BoardMappings to link a tile to a room
     * 
     * 
     * @param yRoom the y location of the room of the door
     * @param xTile the x location of the outside tile
     * @param yTile the y location of the outside tile
     */
    public Door(int yRoom, int xTile, int yTile){
        this.yRoom = yRoom;
        this.xTile = xTile;
        this.yTile = yTile;
        if (xTile < 0 || yTile < 0 || yRoom < 0){
            throw new ArrayIndexOutOfBoundsException("xTile, yTile and yRoom must be > 0");
        }
    }
      
    /**
     * gets the room for the door
     * @return the y identifier of the room
     */
    public int getRoomY(){
        return yRoom;
    }
        
    /**
     * gets the x coordinate of the outside non room tile
     * @return the x coordinate
     */
    public int getTileX(){
        return xTile;
    }
    
    /**
     * gets the y coordinate of the outside non room tile
     * @return the y coordinate
     */
    public int getTileY(){
        return yTile;
    }
}

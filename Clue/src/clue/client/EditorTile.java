/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.scene.control.Label;

/**
 *
 * @author hungb
 */
public class EditorTile {
    
    private final Label label;
    private String s;
    private final int x;
    private final int y;
    private int roomId;
    private String doorDirection;
    /**
     * Constructor for the Editor tile that is used in the boardEditor.
     * 
     * @param l Label object (How tiles are shown in the boardEditor).
     * @param s string representation of the tile.
     * @param x coordinate of tile in the GridPane of the boardEditor.
     * @param y coordinate of tile in the GridPane of the boardEditor.
     */
    public EditorTile(Label l, String s, int x, int y) {
        this.label = l;
        this.s = s;
        this.x = x;
        this.y = y;
        this.roomId = -1;
        this.doorDirection = "-1";
    }
    /**
     * Sets the String representation of the Tile.
     * 
     * @param s representation of the tile(Whether it's a  starting tile, room, etc).
     */
    public void setS(String s) {
        this.s = s;
    }

    /**
     * Sets the room Id.
     * 
     * @param roomId Id of the room that this tile belongs to.
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Sets the direction of where the door points to from this tile.
     * 
     * @param doorDirection "left", "right", "up" or "down".
     */
    public void setDoorDirection(String doorDirection) {
        this.doorDirection = doorDirection;
    }

    /**
     * Returns label object of this Tile.
     * 
     * @return label
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Returns the String representation of the Tile.
     * 
     * @return s
     */
    public String getS() {
        return s;
    }
    /**
     * Returns x coordinate of this tile.
     * 
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Returns y coordinate of this tile.
     * 
     * @return y
     */
    public int getY() {
        return y;
    }
    /**
     * Returns the Room Id of this Tile.
     * 
     * @return roomId
     */
    public int getRoomId() {
        return roomId;
    }
    
    /**
     * Returns the direction in which the door is facing 
     * 
     * @return doorDirection - 1 of :"left", "right", "up" or "down".
     */
    public String getDoorDirection() {
        return doorDirection;
    }
    
   
}

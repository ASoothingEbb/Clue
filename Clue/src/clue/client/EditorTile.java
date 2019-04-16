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
    private final String s;
    private final int x;
    private final int y;
    private int roomId;
    private String doorDirection;
    
    public EditorTile(Label l, String s, int x, int y) {
        this.label = l;
        this.s = s;
        this.x = x;
        this.y = y;
        this.roomId = -1;
        this.doorDirection = "-1";
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setDoorDirection(String doorDirection) {
        this.doorDirection = doorDirection;
    }

    public Label getLabel() {
        return label;
    }

    public String getS() {
        return s;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getDoorDirection() {
        return doorDirection;
    }
    
    
    
}

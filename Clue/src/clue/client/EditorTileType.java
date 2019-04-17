/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

/**
 *  Denotes what kind of tile it is in the boardEditor.
 * 
 * @author hungb
 */


public enum EditorTileType {
    ROOM,
    DOOR_UP,
    DOOR_DOWN,
    DOOR_LEFT,
    DOOR_RIGHT,
    HALL,
    INTRIGUE,
    EMPTY,
    START;
}

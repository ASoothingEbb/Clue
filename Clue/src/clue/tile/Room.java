/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

import clue.card.RoomCard;

/**
 *Represents a room on the board. Each room should have an associated RoomCard.
 * @author slb35
 */
public class Room extends Tile{
    public RoomCard card;

    /**
     * Creates a new Room
     * @param card the RoomCard associated with this room.
     */
    public Room(RoomCard card) {
        super();
        this.card = card;
    }
    
}

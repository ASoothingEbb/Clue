/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

import clue.NoSuchRoomException;
import clue.card.RoomCard;

/**
 *Represents a room on the board. Each room should have an associated RoomCard.
 * @author slb35
 */
public class Room extends Tile{
    private RoomCard card;

    /**
     * Creates a new Room
     * @param card the RoomCard associated with this room.
     */
    public Room(RoomCard card) {
        super(-1,card.getid());
        this.card = card;
        room = true;
    }
    
    /**
     * Creates a new Room
     * @param x
     * @param y
     */
    public Room(){
        super(-1,-1);
        this.card = card;
        room = true;
    }
    
    /**
     * sets the RoomCard of the Room
     * @param card the RoomCard to set
     */
    public void setCard(RoomCard card){
        System.err.println("[Room.setCard] //DO NOT IMPLEMENT, IF YOU MUST SPEAK TO MW434");        
    }
    
    public RoomCard getCard() throws NoSuchRoomException{
        if (getY() == -1){
            throw new NoSuchRoomException("room does not contain a valid roomCard");
        }
        return card;
    }
}

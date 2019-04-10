/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

import clue.card.RoomCard;
import java.util.ArrayList;
import java.util.List;

/**
 *Represents a room on the board. Each room should have an associated RoomCard.
 * @author slb35
 */
public class Room extends Tile{
    private RoomCard card;
    private ArrayList<int[]> locations;
    /**
     * Creates a new Room
     * @param card the RoomCard associated with this room.
     */
    public Room(RoomCard card) {
        super(-1,card.getid());
        this.card = card;
        room = true;
        locations = new ArrayList<>();
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
        locations = new ArrayList<>();
    }
    
    /**
     * adds a x y location to the room location
     * @param x the x coordinate of the location to be added
     * @param y the y coordinate of the location to be added
     */
    public void addLocation(int x, int y){
        int[] loc = new int[2];
        loc[0] = x;
        loc[1] = y;
        locations.add(loc);
    }
    
    public List<int[]> getLocations(){
        return locations;
    }
    
    
    
    /**
     * gets the room id of the room
     * @return the room id
     */
    public int getId(){
        return getY();
    
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

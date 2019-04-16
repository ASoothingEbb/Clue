/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

import clue.card.RoomCard;
import java.util.ArrayList;

/**
 * Represents a room on the board. Each room should have an associated RoomCard.
 * @author slb35
 */
public class Room extends Tile{
    private RoomCard card;
    private ArrayList<int[]> locations;
    private ArrayList<int[]> nonOccupiedLocations;
    /**
     * Creates a new Room
     * @param card the RoomCard associated with this room.
     */
    public Room(RoomCard card) {
        super(-1,card.getid());
        this.card = card;
        room = true;
        locations = new ArrayList<>();
        nonOccupiedLocations = new ArrayList<>();
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
     * Removes door locations from unOccupiedLocations
     * 
     */
    public void removeDoorLocationsFromDrawingLocations(){
    
        boolean isDoor = false;
        ArrayList<int[]> toRemove = new ArrayList<>();
        for (int[] loc : nonOccupiedLocations){//for each drawing location
            isDoor = false;
            for (Tile adjacent : getAdjacent()){//for each adjacent tile to the room
                
                //check if the adjacent tile is next to the drawn location, if it is, queue it to be removed from nonOccupiedLocations
                if (loc[0] == adjacent.getX()+1 && loc[1] == adjacent.getY()){//to left               
                    isDoor = true;
                }
                else if (loc[0] == adjacent.getX()-1 && loc[1] == adjacent.getY()){//to right                    
                    isDoor = true;
                }
                else if (loc[0] == adjacent.getX() && loc[1] == adjacent.getY()+1){//below
                    isDoor = true;;
                }
                else if (loc[0] == adjacent.getX() && loc[1] == adjacent.getY()-1){//above
                    isDoor = true;
                }
            }
            if (isDoor){
                toRemove.add(loc);
            }
        }   
        for (int[] removing : toRemove){
            nonOccupiedLocations.remove(removing);
        }
    }
    
    /**
     * Adds a x y location to the room location
     * @param x the x coordinate of the location to be added
     * @param y the y coordinate of the location to be added
     */
    public void addLocation(int x, int y){
        int[] loc = new int[2];
        loc[0] = x;
        loc[1] = y;
        locations.add(loc);
        nonOccupiedLocations.add(loc);
    }
    
    /**
     * Returns the physical locations (x,y coordinates) of the room
     * @return 
     */
    public ArrayList<int[]> getLocations(){
        return locations;
    }
    
    
    
    /**
     * Gets the room id of the room
     * @return the room id
     */
    public int getId(){
        return getY();
    
    }

    
    /**
     * Placeholder method from legacy version, currently still here so it is not re implemented without notice to mw434
     * 
     */
    final public void setCard(RoomCard card){
        System.err.println("[Room.setCard] //DO NOT IMPLEMENT, IF YOU MUST SPEAK TO MW434"); 
    }
    
    /**
     * Gets the RoomCard associated with this room
     * @return the RoomCard associated with this room
     * @throws NoSuchRoomException thrown when room has no associated card
     */
    public RoomCard getCard() throws NoSuchRoomException{
        if (getY() == -1){
            throw new NoSuchRoomException("room was not created correctly, does not contain a valid roomCard");
        }
        return card;
    }
    
    /**
     * Gives a location resource to the caller
     * @return 
     */
    public int[] assignLocation(){
        int [] location = locations.get(0);
        if (nonOccupiedLocations.size() > 0){
            int selected = nonOccupiedLocations.size()/2;
            location = nonOccupiedLocations.get(selected);
            nonOccupiedLocations.remove(selected);
        }
        
        return location;

    
    }
    /**
     * Adds a location resource back to the room to be given to future players
     * @param location x,y coordinate of the location resource
     */
    public void unassignLocation(int[] location){
        nonOccupiedLocations.add(location);
    }
}

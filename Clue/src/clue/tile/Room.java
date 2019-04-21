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
    private ArrayList<int[]> doorLocations;
    /**
     * Creates a new Room
     * @param card the RoomCard associated with this room.
     */
    public Room(RoomCard card) {
        super(-1,card.getId());
        this.card = card;
        room = true;
        locations = new ArrayList<>();
        nonOccupiedLocations = new ArrayList<>();
        doorLocations = new ArrayList<>();
    }
    /**
     * Creates a new Room
     */
    public Room(){
        super(-1,-1);
        this.card = card;
        room = true;
        locations = new ArrayList<>();
    }
    
    /**
     * Adds a door location to a room
     * @param loc x,y,key of direction key: (up = 1, right = 2, down = 3, left = 4)
     */
    public void addDoorLocation(int[] loc){
        int[] toAdd = new int[3];
        toAdd[0] = loc[0];
        toAdd[1] = loc[1];
        toAdd[2] = loc[2];
        doorLocations.add(toAdd);
    }
    /**
     * Removes door locations from unOccupiedLocations
     * 
     */
    public void removeDoorLocationsFromDrawingLocations(){
    
        for (int[] loc : doorLocations){
            for (int i = 0; i < nonOccupiedLocations.size(); i++){
                if (loc[0] == nonOccupiedLocations.get(i)[0] && loc[1] == nonOccupiedLocations.get(i)[1]){//if door location == nonOccupied location
                    nonOccupiedLocations.remove(i);
                    break;
                }
            }
        
        }
    }
    
    /**
     * Gets the door locations in the room
     * @return the list of door locations, where a door location is x,y and the direction of the door (up = 1, right = 2, down = 3, left = 4)
     */
    public ArrayList<int[]> getDoorLocations(){
        return doorLocations;
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
     * @return returns the list of (x,y) coordinate locations
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
     * @param card the room card associated with the room
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
     * @return a (x,y) location for the token to be drawn at
     */
    public int[] assignLocation(){
        int [] location = locations.get(0);
        boolean assignedToMiddle = false;
        if (nonOccupiedLocations.size() >= 9){
            int xLow = Integer.MAX_VALUE;
            int xHigh = 0;
            int yLow = Integer.MAX_VALUE;
            int yHigh = 0;
            
            //calculate the lowest and highest 
            for (int[] loc : nonOccupiedLocations){
                if (loc[0] > xHigh){
                    xHigh = loc[0];
                }
                if (loc[0] < xLow){
                    xLow = loc[0];
                }
                
                if (loc[1] > yHigh){
                    yHigh = loc[1];
                }
                if (loc[1] < yLow){
                    yLow = loc[1];
                }  
            }
            
            
            //select the first location that can fit within the 1 tile margin
            for (int[] loc : nonOccupiedLocations){
                if (loc[0] < xHigh && loc[0] > xLow && loc[1] > yLow && loc[1] < yHigh){
                    location = loc;
                    assignedToMiddle = true;
                    break;
                }
            }
            
            
            
            nonOccupiedLocations.remove(location);    
        }
        if (!nonOccupiedLocations.isEmpty() && !assignedToMiddle){
            location = nonOccupiedLocations.get(0);
            nonOccupiedLocations.remove(location);   
        }
        
        int[] result = new int[2];
        result[0] = location[0];
        result[1] = location[1];
        return result;

    
    }
    /**
     * Adds a location resource back to the room to be given to future players
     * @param location x,y coordinate of the location resource
     */
    public void unassignLocation(int[] location){
        for (int loc[] : locations){
            if (loc[0] == location[0] && loc[1] == location[1]){//only accept the drawn location if room has this location
                nonOccupiedLocations.add(location);
                System.out.println("[Room.unassignLocation]"+location[0] +","+location[1]+" being assigned to room: "+getId());
                break;
            }
        }
        
    }
}

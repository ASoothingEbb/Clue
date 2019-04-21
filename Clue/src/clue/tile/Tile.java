/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

import java.util.ArrayList;
import java.util.List;

/**
 *Represents a tile on the game board.
 * @author slb35
 */
public class Tile {
    protected boolean special;
    private List<Tile> adjacentTiles;
    protected boolean room;
    private boolean occupied;
    private int x;
    private int y;
    
    
    /**
     * Creates a new Tile.
     * @param x the x coordinate of the tile
     * @param y the y coordinate of the tile
     */
    public Tile(int x, int y) {
        special = false;
        room = false;
        adjacentTiles = new ArrayList<>();
        this.x = x;
        this.y = y;
        occupied = false;
    }
    
    /**
     * Creates a new Tile that does not have valid x y coordinates
     */
    public Tile() {
        special = false;
        room = false;
        adjacentTiles = new ArrayList<>();
        this.x = -1;
        this.y = -1;
        occupied = true;//tile is not traverseable
    }
    
    
    /**
     * gets if the tile is a valid tile, non valid tiles are effectively null tiles
     * @return false if the tile is part of the board, true if the tile is a empty placeholder tile
     */
    public boolean isPlaceholder(){
        return x == -1 && y == -1;
    }
    
    
   

    /**
     * Gets whether or not a Tile is adjacent to this one.
     * @param tile Tile to check
     * @return adjacency
     */
    public boolean isAdjacent(Tile tile){
        return adjacentTiles.contains(tile);
    }
    
    /**
     * Returns true if the tile is room, false otherwise.
     * @return room
     */
    public boolean isRoom(){
        return room;
    }
    
    
    /**
     * @return false if the tile is a non room tile and someone is already in the tile;
     * 
     */
    public boolean isFull(){
        return occupied && !isRoom();
    }
    
    /**
     * sets the occupied state of the tile
     * @param occupied the new occupied state of the square
     * 
     */
    public void setOccupied(boolean occupied){
        this.occupied = occupied;
    }
    

    /**
     * Adds Tile to the list of adjacent tiles for the current tile, the tile being added will have this tile added to its adjacency list
     * @param adjacentTile tile to add
     */
    public void addAdjacentBoth(Tile adjacentTile) {
        this.addAdjacent(adjacentTile);
        if (adjacentTile.isAdjacent(this)){
            System.out.println("[Tile.addAdjacentBoth]target tile allready knowns about this adjacency, dont add both");                    
        }
        else{
                adjacentTile.addAdjacent(this);//non recursive add to prevent them keep on adding and calling each other
        }  
    }
   
    /**
     * Adds Tile to the list of adjacent tiles for the current tile 
     * @param adjacentTile tile to add
     */
    public void addAdjacent(Tile adjacentTile) {
        if (isAdjacent(adjacentTile)){
            System.out.println("[Tile.addAdjacent]this tile allready knows about this adjacency!");
        }
        else{
            this.adjacentTiles.add(adjacentTile);
        }
    }
    
    /**
     * gets the x coordinate of the tile, -1 if the tile is a room
     * @return the x coordinate / -1 if room
     */
    @Override
    public String toString(){
        String res = "";
        res+= "spec:"+isSpecial()+", room:"+isRoom() + ", xy:" + getX() + "," + getY()+ " \n";
        
        for(Tile adjacent : getAdjacent()){
            res += "    "+adjacent.getX()+ ", "+adjacent.getY()+ " \n";
        }
        
        return res;
    }
    
    /**
     * Gets the x coordinate of this tile
     * @return the x coordinate
     */
    public int getX(){
        return x;
    }
    
    /**
     * gets the y coordinate of the tile, returns the room id if the tile is a room
     * @return the y coordinate / room id
     */
    public int getY(){
        return y;
    }
    /**
     * gets the adjacent tiles
     * @return the adjacent tiles
     */
    public List<Tile> getAdjacent(){
        return adjacentTiles;
    }
    
    /**
     * gets the special flag from the tile
     * @return true if the tile is special, false otherwise
     */
    public boolean isSpecial(){
        return special;
    }  
}

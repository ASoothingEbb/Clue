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
    public boolean special;
    private List<Tile> adjacentTiles;
    private boolean room;
    
    /**
     * Creates a new Tile.
     */
    public Tile() {
        special = false;
        room = false;
        adjacentTiles = new ArrayList<>();
    }
    
    /**
     * Adds Tiles to the list of adjacent tiles
     * @param adjacentTiles list of tiles to add
     */
    public void setAdjacent(List<Tile> adjacentTiles){
        this.adjacentTiles.addAll(adjacentTiles);
    }

    /**
     * Gets whether or not a Tile is adjacent to this one.
     * @param tile Tile to check
     * @return adjacency
     */
    public boolean isAdjacent(Tile tile){
        if(adjacentTiles.contains(tile)){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Returns true if the tile is room, false otherwise.
     * @return room
     */
    public boolean isRoom(){
        return room;
    }
    
}

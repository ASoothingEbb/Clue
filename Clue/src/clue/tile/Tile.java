/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

import java.util.List;

/**
 *
 * @author slb35
 */
public class Tile {
    private List<Tile> adjacentTiles;
    public Tile(List<Tile> tiles) {
        adjacentTiles = tiles;
    }
    
    public boolean isAdjacent(Tile tile){
        if(adjacentTiles.contains(tile)){
            return true;
        }
        else{
            return false;
        }
    }
}

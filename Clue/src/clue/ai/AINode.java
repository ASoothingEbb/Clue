/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.ai;

import clue.tile.Tile;

/**
 *
 * @author zemig
 */
public class AINode {
    
    
    //Ignore
    
    private int x;
    private int y;
    private Tile tile;
    private boolean traversable;
    private boolean visited;
    
    public AINode(Tile tile){
        this.x = tile.getX();
        this.y = tile.getY();
        traversable = tile.isFull();
        visited = false;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setVisited(){
        visited = true;
    }
   
    
}

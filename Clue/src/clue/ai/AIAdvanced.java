/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.ai;

/**
 *
 * @author zemig
 */  

import clue.BoardMappings;
import clue.GameController;
import clue.action.StartAction;
import clue.card.Card;
import clue.player.AIPlayer;
import clue.player.Player;
import clue.tile.Tile;
import java.util.ArrayList;
import java.util.List;

public class AIAdvanced extends AIPlayer{
    
    private int id;
    private GameController gameController;
    private int targetY;//Y value of the closest room tile.
    private int targetX;////Y value of the closest room tile.
    private List<Player> players;
    private List<List<Card>> cardLists;//List of each cards players have previously suggested
    
    public AIAdvanced(int id){
        super(id);
        
        this.id = id;
        gameController = getGameController();
        players = gameController.getPlayers();
        
        
    }
    
    @Override
    public void onUpdate(){
    
        if(gameController.getLastAction() instanceof StartAction){
            makeLists();
        }
    }
    
    public void findNewRoom(){
        List<Tile> path = BFS(tileMap);
        
        targetX = path.get(path.size()-1).getX();//Values of the room tile(last tile).
        targetY = path.get(path.size()-1).getY();
    }
   /** 
    * This method finds the closest Tile to the player that is a room.
    *    
    *@param map of all the tiles.
    *@return The path to the closest Room.
    */
    private List<Tile> BFS(BoardMappings map){
     
        //TODO
        List<Tile> path = new ArrayList<Tile>();
        
        return path;
    }
    
    /**
     * Creates the list of lists for cards of every player(includingitlsef)
     */
    private void makeLists(){
        for(int i=0; i<players.size(); i++){
            
        }
    }
}

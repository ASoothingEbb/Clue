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
import clue.tile.NoSuchRoomException;
import clue.action.*;
import clue.card.Card;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;
import clue.tile.Tile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AiAdvanced extends Player{
    
    private int id;
    private GameController gameController;
    private int targetY;//Y value of the closest room tile.
    private int targetX;////Y value of the closest room tile.
    private int boardWidth;
    private int boardHeight;
    private List<Player> players;
    private ArrayList<ArrayList<Card>> cardLists;//List of each cards players have previously suggested
    private Random rand;
    
    //Width of the board, height of the board.
    public AiAdvanced(int id, int width, int height){
        super(id);
        
        this.boardWidth = width;
        this.boardHeight = height;
        this.id = id;
        gameController = getGameController();
        rand = new Random();
    }
    
    @Override
    public void onUpdate(){
    
        ///Check if any tile in the solution path is occupied & if player position changed. if not, keep same path. Else make a new Solution path.
        if(gameController.getLastAction() instanceof StartAction){
            players = gameController.getPlayers();
            makeLists();///TODO
        }
        
        //Generating Random Cards (ids).
        PersonCard randPersonCard = new PersonCard(rand.nextInt(6) + 1);
        RoomCard randRoomCard = new RoomCard(rand.nextInt(6) + 1);
        WeaponCard randWeaponCard = new WeaponCard(rand.nextInt(9)+ 1);
        
        
        if(gameController.getPlayer().getId() == getId()){//If I need to respond.
            if(gameController.getLastAction() instanceof EndTurnAction){//If my turn, last action was end turn.
                if(this.getPosition().isRoom()){//If I'm in a room
                    //try {           
                    //    sendAction(Accuse(randPersonCard, randRoomCard, randWeaponCard));
                    //} catch (InterruptedException ex) {
                    //    Logger.getLogger(AIAdvanced.class.getName()).log(Level.SEVERE, null, ex);
                    //}
                }
                
            } else if (gameController.getLastAction() instanceof ShowCardsAction){//If I need to show a card 

                ShowCardsAction action = (ShowCardsAction) gameController.getLastAction();
                Card card = action.getCardList().get(0);
                ShowCardAction newAction = new ShowCardAction(action.getSuggester(), card);//Shows one card to person who requested cards to be shown(suggested).

                //try {
                //    sendAction(newAction);//Show Card.
                //} catch (InterruptedException ex) {
                //    Logger.getLogger(AIAdvanced.class.getName()).log(Level.SEVERE, null, ex);
                //}
            }
        }
    }
    
    public void findNewRoom(BoardMappings tileMap) throws NoSuchRoomException{
        List<Tile> path = BFS();
        
        targetX = path.get(path.size()-1).getX();//Values of the room tile(last tile).
        targetY = path.get(path.size()-1).getY();
    }
    
   /** 
    * calculates the closest room from the players current location
    *    
    *
    *@return The path to the closest Room from the player's current position. 
    */
    public LinkedList<Tile> BFS(){   
        
        LinkedList<LinkedList<Tile>> pathList = new LinkedList<>();
        
        boolean visited[][] = new boolean[boardWidth][boardHeight];
        
        for(boolean []a : visited){
            Arrays.fill(a,false);
        }
        
        LinkedList<Tile> solutionPath = new LinkedList<>();        
        LinkedList<Tile> newPath = new LinkedList<>();
        LinkedList<Tile> currentPath;
        
        newPath.add(getPosition());
        pathList.add(newPath);
        visited[getPosition().getX()][getPosition().getY()] = true;
        
        
        boolean foundRoom = false;        
        while(!foundRoom){
            
            if (pathList.isEmpty()){
                //System.out.println("no valid path found");
                return new LinkedList<>();
            }
            
            currentPath = pathList.get(0);
            //System.out.println("expanding a path to have one extra distance from source");
            
            for (Tile t : currentPath.getLast().getAdjacent()){//try to explore all the tiles adjacent to the last tile in the path
                if (t.isRoom()){//shortest path found
                    currentPath.add(t);
                    return currentPath;
                }
                else if (t.isFull() || visited[t.getX()][t.getY()]){//if the tile is full or is allready visited, do not explore it
                    //System.out.println("allready explored this tile");
                    continue;
                }
                
                newPath = new LinkedList<>();
                for (Tile ti : currentPath){
                    newPath.add(ti);
                }
                
                newPath.add(t);//add new path to pathList (current path + newly found tile)
                
                //System.out.println("adding new path with distance: "+newPath.size());
                pathList.add(newPath);
                
                System.out.println("current paths:");
                for (LinkedList<Tile> storedPath : pathList){
                    System.out.println("path:");
                    for (Tile ti: storedPath){
                        System.out.println("   "+ti.getX() +","+ ti.getY());
                    }
                }
                
                visited[t.getX()][t.getY()] = true;//tile is now marked as visited    
            }
            pathList.remove(currentPath);
  
        }
        return solutionPath;  
    }
    
    /**
     * Creates the list of lists of cards of every player(including itself)
     */
    public void makeLists(){
        for(int i=0; i<players.size(); i++){
            cardLists.add(new ArrayList<>());//create a list for every player.
            cardLists.set(id-1, (ArrayList<Card>) getCards());      
            ///TODO
        }
    }
    
    public ArrayList<ArrayList<Card>> getLists(){
        return cardLists;
    }
}

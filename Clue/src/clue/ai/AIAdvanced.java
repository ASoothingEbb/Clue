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
import clue.NoSuchRoomException;
import clue.action.EndTurnAction;
import clue.action.ShowCardAction;
import clue.action.ShowCardsAction;
import clue.action.StartAction;
import clue.card.Card;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;
import clue.tile.Tile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AIAdvanced extends Player{
    
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
    public AIAdvanced(int id, int width, int height){
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
                    try {           
                        sendAction(Accuse(randPersonCard, randRoomCard, randWeaponCard));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AIAdvanced.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            } else if (gameController.getLastAction() instanceof ShowCardsAction){//If I need to show a card 

                ShowCardsAction action = (ShowCardsAction) gameController.getLastAction();
                Card card = action.getCardList().get(0);
                ShowCardAction newAction = new ShowCardAction(action.getSuggester(), card);//Shows one card to person who requested cards to be shown(suggested).

                try {
                    sendAction(newAction);//Show Card.
                } catch (InterruptedException ex) {
                    Logger.getLogger(AIAdvanced.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void findNewRoom(BoardMappings tileMap) throws NoSuchRoomException{
        List<Tile> path = BFS();
        
        targetX = path.get(path.size()-1).getX();//Values of the room tile(last tile).
        targetY = path.get(path.size()-1).getY();
    }
   /** 
    * This method finds the closest Tile to the player that is a room.
    *    
    *@param instance of BoardMappings
    *@return The path to the closest Room from the player's current position. 
    */
    public ArrayList<Tile> BFS(){
        int height = boardHeight;
        int width = boardWidth;   
        
        ArrayList<ArrayList<Tile>> pathList = new ArrayList<>();
        boolean foundRoom = false;
        
        boolean visited[][] = new boolean[height][width];
        
        for(boolean []a : visited){
            Arrays.fill(a,false);
        }
        visited[getPosition().getX()][getPosition().getY()] = true;
        
        ArrayList<Tile> temp = new ArrayList<>();
        temp.add(getPosition());
        pathList.add(temp);
        
        ArrayList<ArrayList<Tile>> toRemove = new ArrayList<>();
        ArrayList<Tile> solutionPath = new ArrayList<>();
        ArrayList<ArrayList<Tile>> newPath = new ArrayList<>();
        ArrayList<Tile> path;
        
        int i = 0;
        
        int oldPathListSize = 0;
        while(!foundRoom){
            i = 0;
            oldPathListSize = pathList.size();
            System.out.println("Increase in distance");
            while(i < oldPathListSize){
                System.out.println("i: " + i + "oldPathistSize: "+ oldPathListSize);
                path = pathList.get(i);
                Tile tempTile = path.get(path.size() - 1);//last Tile of path.
                
                for(Tile adjacent : tempTile.getAdjacent()){
                    ArrayList<Tile> tempPath = path;
                    System.out.println("Num of paths: " + pathList.size());
                    System.out.println("New path: ");
                    for(Tile t : tempPath){
                        System.out.print(t);
                    }
                    System.out.println("");
                    
                    if(!adjacent.isFull()){
                        if(!visited[adjacent.getX()][adjacent.getY()]){//If not yet visited.
                            tempPath.add(adjacent);
                            visited[adjacent.getX()][adjacent.getY()] = true;//set as visited.
                            System.out.println(adjacent.getX() + ", "+ adjacent.getY()+ ":" + visited[adjacent.getX()][adjacent.getY()]+ "Set  asdasdasdasdasdasdasdasdadasd ");
                            if (adjacent.isRoom()){
                                foundRoom = true;
                                solutionPath = tempPath;
                                break;
                            }  
                        }
                        
                        System.out.println("MEHAMMMAMMAMSMAMMM");
                        pathList.add(tempPath);
                        toRemove.add(path);
                    }
                }
                 i++;
            }
            for(ArrayList<Tile> pathToRemove : toRemove){
                pathList.remove(pathToRemove);
                System.out.println("TRAPS ARE GAY");
            }
            toRemove = new ArrayList<ArrayList<Tile>>();
            
           
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

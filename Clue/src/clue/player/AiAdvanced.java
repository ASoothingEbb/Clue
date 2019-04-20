/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.player;

/**
 *
 * @author zemig
 */  

import clue.GameController;
import clue.action.*;
import clue.card.Card;
import clue.player.Player;
import clue.tile.Tile;
import clue.tile.TileOccupiedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AiAdvanced extends Player{
    
    private int id;
    private GameController gameController;
    private int boardWidth;
    private int boardHeight;
    private LinkedList<Tile> pathToRoom;
    private Random rand;
    private int suggestionsLeft;
    ArrayList<ArrayList<Integer>> knownCards;
    
    private ArrayList<ArrayList<Card>> cardLists;//List of each cards players have previously suggested
    private List<Card> shownCards;
    private boolean myTurn;
    
    
    /** 
    * Constructor for AiAdvanced.
    * @param id of the Player.
    * @param gc GameController object.
    * @param width of the game board.
    * @param height of the game board.
    */
    
    public AiAdvanced(int id, GameController gc ,int width, int height){
        super(id, gc);
        this.boardWidth = width;
        this.boardHeight = height;
        this.id = id;
        gameController = gc;
        rand = new Random(Calendar.getInstance().getTimeInMillis());
        shownCards = new ArrayList<>();
        suggestionsLeft = rand.nextInt(15)+15;
        myTurn = false;
        
        knownCards = new ArrayList<>();
        
        ArrayList<Integer> c0 = new ArrayList<>();
        ArrayList<Integer> c1 = new ArrayList<>();
        ArrayList<Integer> c2 = new ArrayList<>();
        
        knownCards.add(c0);
        knownCards.add(c1);
        knownCards.add(c2);
    }
    
    public Card respondToShowCards(List<Card> cards){
        System.out.println("[AiAdvanced.respondToShowCards] id: "+id);
        return cards.get(0);
    }
    
   
    
    /**
     * Sends an action to the game controller that will move the player towards/to a room.
     */
    public void moveToRoom(){
        
        gameController.roll();
        System.out.println("[AiAdvanced.moveToRoom] id: "+id + "moves: "+getMoves());
        LinkedList<Tile> path = BFS();
        Tile target;
        if (!path.isEmpty()){
            if (getMoves() < path.size()){
                target = path.get(getMoves()-1);
            }   
            else{
                target = path.getLast();
            }
                

            try {
                gameController.move(target);
            } catch (TileOccupiedException ex) {
                Logger.getLogger(AiAdvanced.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
    }

    /**
     * Called by GameConstructor when the ai player turns begins
     */
    public void respondToStartTurn() {
        System.out.println("[AiAdvanced.respondToStartTurn] id: "+id);
        myTurn = true;
        
        if (knownCards.isEmpty()){//then it is the first turn
            for (Card card : getCards()){
                addCardToKnownCards(card);
            }
        }
        if(getPosition().isRoom()){//if in room
            suggestAccuse();
        } 
        else {
            moveToRoom();
            if (getPosition().isRoom()){
                suggestAccuse();
            }
            
        }
        if (myTurn){
            endTurn();
        }   
    }

    /**
     * Called by GameConstructor when the ai players is allowed to roll the dice again, 
     */
    public void respondToThrowAgain() {
        System.out.println("[AiAdvanced.respondToThrowAgain] id: "+id);
        if (!getPosition().isRoom()){
            moveToRoom();
        }
        endTurn();
    }
    
    /**
     * Called by GameConstructor when the ai player is shown a card (from there suggestion)
     * @param card the card being shown to ai player
     * @param whoShowedTheCard the person who showed the card
     */
    public void revealCard(Card card, Player whoShowedTheCard) {//called when a player is showing a card to AI
        System.out.println("[AiAdvanced.revealCard] id: "+id);
        addCardToKnownCards(card);  
    }

    /**
     * Adds a card to the know deck, these cards are cards which are seen by the ai player and are thus not part of the murder cards
     * @param card the card shown to the ai player
     */
    private void addCardToKnownCards(Card card){
        System.out.println("[AiAdvanced.addCardToKnownCards] id: "+id);
        if (card != null){
            if (null != card.getCardType())switch (card.getCardType()) {
                case PERSON:
                    knownCards.get(0).add(card.getId());
                    break;

                case ROOM:
                    knownCards.get(1).add(card.getId());
                    break;

                case WEAPON:
                    knownCards.get(2).add(card.getId());
                    break;

                default:
                    break;
            }
        }
        
    }

    /**
     * Submits suggestions until it reaches a suggestion limit, then it submits an accusation
     */
    private void suggestAccuse() {
        System.out.println("[AiAdvanced.suggestAccuse] id: "+id);
        int[] unknownIds;
        if (suggestionsLeft > 0){
            unknownIds = getNextUnknown();
            suggestionsLeft--;
            gameController.suggest(unknownIds[0], unknownIds[2]);
        }
        else {
            unknownIds = getNextUnknown();
            gameController.accuse(unknownIds[0], unknownIds[2]);
        }
    }
 
    /**
     * Selects 3 card ids to be used in suggestion/accusation that it knows is not part of the murder cards
     * @return The three ids to be used in a suggestion/accusation ,index 0 = personid, index 1 = roomid, index 2 = weaponid
     */
    private int[] getNextUnknown(){

        System.out.println("[AiAdvanced.getNextUnknown] id: "+id);
        int[] result = new int[3]; 
        result[0] = 0;
        result[1] = 0;
        result[2] = 0;

        int randomInt;
        boolean found = false;
        int c = 0;
        while (!found && c < 49){//pick a random int until the id is not known, stop after 50 random ints
            randomInt = rand.nextInt(6);
            if (!knownCards.get(0).contains(randomInt)){
                result[0] = randomInt;
                found = true;
            }
            c++;

        }
        
        for (int i = 0; i < knownCards.get(1).size(); i++){//get the lowest unknown room id
            if (!knownCards.get(1).contains(i)){
                result[1] = i;
                break;
            }
        }
        
        found = false;
        c = 0;
        while (!found && c < 49){// pick a random int until the id is not known
            randomInt = rand.nextInt(6);
            if (!knownCards.get(2).contains(randomInt)){
                result[2] = randomInt;
                found = true;
            }
            c++;
        }

        return result;        
        
        
    }

    /**
     * Submits an endTurn action to the GameController
     */
    private void endTurn() {
        System.out.println("[AiAdvanced.endTurn] id: "+id);
        myTurn = false;
        gameController.endTurn();

    }

    /**
     * Called by the GameConstructor when the Ai player needs to respond to a teleport action
     * @param action the teleport action the Ai player needs to respond to
     */
    public void respondToTeleport(TeleportAction action) {
        //TODO change type casting and parameters
        System.out.println("[AiAdvanced.respondToTeleport] id: "+id);
        LinkedList<Tile> path = BFS();
        ((TeleportAction)action).setTarget(path.getLast());
        suggestAccuse();
        endTurn();
    }
    
  /** 
    * Returns the path to the closest room from the players current location
    * @return The path to the closest Room from the player's current position.
    * @deprecated only used so that it can be tested
    */
    public LinkedList<Tile> BFStesting(){
        return BFS();
    }
    
    /** 
    * Returns the path to the closest room from the players current location
    *@return The path to the closest Room from the player's current position. 
    */
    public LinkedList<Tile> BFS(){   
        System.out.println("[AiAdvanced.BFS] id: "+id);
        boolean visited[][] = new boolean[boardWidth][boardHeight];
        
        for(boolean []a : visited){
            Arrays.fill(a,false);
        }
        visited[getPosition().getX()][getPosition().getY()] = true;
        
        LinkedList<LinkedList<Tile>> pathList = new LinkedList<>();
        LinkedList<Tile> solutionPath = new LinkedList<>();        
        LinkedList<Tile> newPath = new LinkedList<>();
        LinkedList<Tile> currentPath;
        
        newPath.add(getPosition());
        pathList.add(newPath);

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
                else if (t.isFull() || visited[t.getX()][t.getY()]){//if the tile is full or is already visited, do not explore it
                    continue;
                }
                
                newPath = new LinkedList<>();
                for (Tile ti : currentPath){
                    newPath.add(ti);
                }
                
                newPath.add(t);//add new path to pathList (current path + newly found tile)
                
                pathList.add(newPath);
           
                visited[t.getX()][t.getY()] = true;//tile is now marked as visited    
            }
            pathList.remove(currentPath);
  
        }
        pathToRoom = solutionPath;
        pathToRoom.remove(getPosition());
        return solutionPath;  
    }
    
}

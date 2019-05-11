/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.player;

import clue.GameController;
import clue.card.Card;
import clue.card.CardType;
import clue.card.IntrigueCard;
import clue.tile.Room;
import clue.tile.Tile;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 *
 * @author slb35
 */
public class Player {

    private boolean active;
    private List<Card> cards;
    private List<IntrigueCard> intrigues;
    private Tile position;
    protected int movements;
    private int id;
    private int lastSeen = 0;
    private String notes;
    private int[] drawnLocation;
    private boolean isAi;
    private boolean canReceiveIntrigue;

    public GameController game;

    /**
     * Creates a new player.
     * @param id the id of the player instance
     * @param gc the GameController of the running game
     */
    public Player(int id, GameController gc) {
        this.game = gc;
        this.id = id;
        notes = "";
        active = true;
        cards = new ArrayList();
        intrigues = new ArrayList();
        drawnLocation = new int[2];
        isAi = false;
        canReceiveIntrigue = true;
    }

    /**
     * Creates a new Player
     *
     * @deprecated for test use only - please instantiate a GameController
     * @param id the id of the player instance
     */
    public Player(int id) {
        this.id = id;
        notes = "";
        active = true;
        cards = new ArrayList();
        intrigues = new ArrayList();
    }

    public void onUpdate() {
    }

    /**
     * Gets the id of this Player.
     *
     * @return player id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * gets how many tiles this player can move
     * @return number of moves 
     */
    public int getMoves() {
        return movements;
    }

    /**
     * Sets the amount of tiles this player can move
     * @param moves the number of moves
     */
    public void setMoves(int moves) {
        movements = moves;
    }

    /**
     * Gets whether or not this player is still in the game.
     *
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Removes this player from the game, preventing it from taking any further
     * turns.
     */
    public void removeFromPlay() {
        active = false;
    }

    /**
     * gets the current position of the Player on the board
     *
     * @return Player location
     */
    public Tile getPosition() {
        return position;
    }

    /**
     * Moves the player to the specified location on the board
     *
     * @param t destination
     */
    public void setPosition(Tile t) {

        t.setOccupied(true);
        if (position != null) {
            position.setOccupied(false);
            if (position.isRoom()) {
                ((Room) position).unassignLocation(drawnLocation);//allow room to re assign location 
            }
        }
        drawnLocation = new int[2];
        if (t.isRoom()) {
            drawnLocation = ((Room) t).assignLocation();//get a location from the room

        } else {

            drawnLocation[0] = t.getX();
            drawnLocation[1] = t.getY();
        }

        position = t;
    }

    /**
     * Gets the x coordinate that the player should be drawn at
     *
     * @return the x coordinate
     */
    public int getDrawX() {
        return drawnLocation[0];
    }

    /**
     * Gets the y coordinate that the player should be drawn at
     *
     * @return the y coordinate
     */
    public int getDrawY() {
        return drawnLocation[1];
    }

    /**
     * Adds the card to this player.
     *
     * @param card the card to add
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Adds an intrigue card (drawn from the GameController) to the players intrigue card list
     * @return the selected intrigue card
     */
    public IntrigueCard addIntrigue() {
        IntrigueCard card = (IntrigueCard) game.drawCard();
        intrigues.add(card);
        return card;
    }

    /**
     * Adds a given intrigue card to the players intrigue card list
     * @return the intrigue card added to players intrigue card list
     * @deprecated this is a test method
     * @param card the intrigue card to be added
     */
    public IntrigueCard addIntrigue(IntrigueCard card) {
        intrigues.add(card);
        return card;
    }

    /**
     * Removes a given intrigue card from the players intrigue card list
     * @param card the intrigue card to be removed
     */
    public void removeIntrigue(IntrigueCard card){
        System.out.println("[Player.removeIntrigue] : "+card.getCardType());
        intrigues.remove(card);
    }

    /**
     * Gets whether or not the player has the card
     *
     * @param card the card to check
     * @return true if they have the card, false otherwise
     */
    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    /**
     * Gets whether or not the player has the intrigue card
     * @param card the intrigue card to search for
     * @return true if player had card, false otherwise
     */
    public boolean hasIntrigue(IntrigueCard card){
        return intrigues.contains(card);
    }

    /**
     * Gets the list of intrigue cards held by the player
     * @return the list of intrigue cards
     */
    public List<IntrigueCard> getIntrigues() {
        return intrigues;
    }

    /**
     * Gets whether or not the player has a given intrigue card type
     *
     * @param type the intrigue card type to search for
     * @return true if they have a card of that type, false otherwise
     */
    public boolean hasIntrigue(CardType type) {
        System.out.println("[Player.hasIntrigue] intrigue count: "+intrigues.size());
        for (IntrigueCard intrigue : intrigues) {
            System.out.println("owned intrigue: "+intrigue.getCardType());
            if (intrigue.getCardType() == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes one intrigue of the given card type from the player
     *
     * @param type the intrigue card type to remove
     */
    public void removeIntrigueOnce(CardType type) {
        System.out.println();
        IntrigueCard toRemove = null;

        for (IntrigueCard intrigue : intrigues) {
            if (intrigue.getCardType() == type) {
                toRemove = intrigue;
                break;
            }
        }

        if (toRemove != null) {
            intrigues.remove(toRemove);
        }

    }
    
    /**
     * Gets all the cards held by the player
     * @return the list of held cards
     */
    public List<Card> getCards(){
        return cards;
    }

    /**
     * Gets the index of the last entry it saw in the action log
     * @return the index of the last seen index
     */
    public int getLogPointer() {
        return lastSeen;
    }

    /**
     * Sets the index of the last entry it saw in the action log
     * @param pointer the index to set the pointer to
     */
    public void setLogPointer(int pointer) {
        lastSeen = pointer;
    }
    
    /**
     * Gets the players detective notes
     * @return the players notes
     */
    public String getNotes(){
        return notes;
    }
    
    /**
     * Sets the players detective notes
     * @param notes the new detective notes of the player
     */
    public void setNotes(String notes){
        this.notes = notes;
    }
    

    
    /**
     * Converts player object to string form for debugging
     * @return string format of player instance
     */
    @Override
    public String toString(){
        String result = "";
        result += "Player id: "+id;
        result += " position: "+getPosition();
        
        
        return result;
    }
    
    
    public void setCanReceiveIntrigue(boolean setTo){
        canReceiveIntrigue = setTo;
        System.out.println("[Player.setCanReceiveIntrigue] :"+setTo);
    
    }
    public boolean getCanReceiveIntrigue(){
        System.out.println("[Player.getCanReceiveIntrigue] :"+canReceiveIntrigue);
        return canReceiveIntrigue;
    }
    
    public IntrigueCard getIntrigue(CardType type){
        for (IntrigueCard card: intrigues){
            if (card.getCardType() == type){
                return card;
            }
        }
        return null;
    }
}

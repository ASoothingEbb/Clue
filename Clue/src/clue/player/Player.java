/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.player;

import clue.GameController;
import clue.action.Action;
import clue.card.Card;
import clue.Observer;
import clue.action.AccuseAction;
import clue.action.MoveAction;
import clue.action.SuggestAction;
import clue.action.UnknownActionException;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.tile.Tile;
import clue.card.WeaponCard;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Represents a player in the game.
 * @author slb35
 */
public abstract class Player implements Observer{
public class MovementException extends Exception{
}
    private boolean active;
    private List<Card> cards;
    private Tile position;
    private int movements;
    private int id;
    private GameController game;
    
    /**
     * Creates a new player.
     */
    public Player(int id){
        this.id = id;
        active = true;
    }
    @Override
    public void onUpdate() {
    }
    /**
     * Gets the id of this Player.
     * @return player id.
     */
    public int getId(){
        return this.id;
    }
    
    private void doMove(Queue<Tile> tiles) throws MovementException{
        if(tiles.size()<= movements){
            sendAction(move(tiles));
        } else{
            throw new MovementException();
        }
    }
    /**
     * Attempts to move from the current position to a new tile.
     * @param t the destination tile.
     * @return new MoveAction
     */
    private Action move(Queue<Tile> t){
        return new MoveAction(t,this);
    }

    /**
     * Suggests a set of cards as the murder details
     * @param person suspect
     * @param room crime scene
     * @param weapon murder weapon
     * @return new SuggestAction
     */
    private void suggest(PersonCard person,RoomCard room, WeaponCard weapon){
        sendAction(game.suggest(person,room,weapon,this));
    }
    /**
     * Accuses a set of cards, resulting in this player becoming removed from 
     * the game. If the accusation is correct, the game ends and this Player is 
     * the winner.
     * @param person suspect
     * @param room crime scene
     * @param weapon murder weapon
     * @return new AccuseAction
     */
    private Action Accuse(PersonCard person, RoomCard room, WeaponCard weapon){
        return new AccuseAction(this,person,room,weapon);
    }
    /**
     * sends an action to the GameController to be executed.
     * @param action the action to be executed
     */
    public void sendAction(Action action){
        if(active){
            try {
                game.performAction(action);
            } catch (UnknownActionException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Gets whether or not this player is still in the game.
     * @return active
     */
    public boolean isActive(){
        return active;
    }
    /**
     * Removes this player from the game, preventing it from taking any further
     * turns.
     */
    public void removeFromPlay(){
        active = false;
    }
    /**
     * gets the current position of the Player on the board
     * @return Player location
     */
    public Tile getPosition(){
        return position;
    }
    /**
     * Moves the player to the specified location on the board
     * @param t destination
     */
    public void setPosition(Tile t) {
        position = t;
    }
    public void addCard(Card card){
        cards.add(card);
    }
    public void removeCard(Card card) throws NullPointerException{
        cards.remove(card);
    }
    public boolean hasCard(Card card){
        return cards.contains(card);
    }
    
    /**
     * Returns the game controller object.
     * @return game
     */
    public GameController getGameController(){
        return game;
    }
    
}


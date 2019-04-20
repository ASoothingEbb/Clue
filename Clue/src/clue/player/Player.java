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

    public GameController game;

    /**
     * Creates a new player.
     * @param id
     * @param gc
     */
    public Player(int id) {
        this.id = id;
        notes = "";
        active = true;
        cards = new ArrayList();
        intrigues = new ArrayList();
        drawnLocation = new int[2];
        isAi = false;
    }

//    /**
//     * Creates a new Player
//     * @deprecated for test use only - please instantiate a GameController
//     * @param id 
//     */
//    public Player(int id){
//        this.id = id;
//        notes = "";
//        active = true;
//        cards = new ArrayList();
//        intrigues = new ArrayList();
//    }

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

    public int getMoves() {
        return movements;
    }

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
        if (position!=null){
            position.setOccupied(false);   
            if (position.isRoom()){
                ((Room) position).unassignLocation(drawnLocation);//allow room to re assign location 
            }
        }
        drawnLocation = new int[2];
        if (t.isRoom()){
            drawnLocation = ((Room)t).assignLocation();//get a location from the room
            
        } 
        else{
            
            drawnLocation[0] = t.getX();
            drawnLocation[1] = t.getY();    
        }   
        
        position = t;
    }

    /**
     * Gets the x coordinate that the player should be drawn at
     * @return the x coordinate
     */
    public int getDrawX(){
        return drawnLocation[0];
    }
    
    /**
     * Gets the y coordinate that the player should be drawn at
     * @return the y coordinate
     */
    public int getDrawY(){
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
     * 
     * @return 
     */
    public IntrigueCard addIntrigue() {
        IntrigueCard card = (IntrigueCard) game.drawCard();
        intrigues.add(card);
        return card;
    }
    
    /**
     * @deprecated this is a test method
     * @param card 
     */
    public IntrigueCard addIntrigue(IntrigueCard card){
        intrigues.add(card);
        return card;
    }

    public void removeIntrigue(IntrigueCard card){
        intrigues.remove(card);
    }
    /**
     * Removes a card from this player.
     *
     * @param card the card to remove
     * @throws NullPointerException
     */
    public Card removeCard(Card card) throws NullPointerException {
        cards.remove(card);
        return card;
    }

    /**
     * Gets whether or not the player has the card
     *
     * @param card
     * @return
     */
    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    public boolean hasIntrigue(IntrigueCard card){
        return intrigues.contains(card);
    }

    public List<IntrigueCard> getIntrigue() {
        return intrigues;
    }


    /**
     * Gets whether or not the player has a given intrigue card type
     *
     * @param type the intrigue card type to search for
     * @return true if they have a card of that type, false otherwise
     */
    public boolean hasIntrigue(CardType type) {
        for (IntrigueCard intrigue: intrigues){
            if (intrigue.getCardType() == type){
                return true;
            }
        }
        return false;
    }

    /**
     * Removes one intrigue of the given card type from the player
     * @param type the intrigue card type to remove
     */
    public void removeIntrigueOnce(CardType type){
        IntrigueCard toRemove = null;
        
        for (IntrigueCard intrigue: intrigues){
            if (intrigue.getCardType() == type){
                toRemove = intrigue;
                break;
            }
        }
        
        if (toRemove != null){
            intrigues.remove(toRemove);    
        }
        
    }
    
    
    
    /**
     * Returns the game controller object.
     *
     * @return game
     */
    public GameController getGameController() {
        return game;
    }
    
    public List<Card> getCards(){
        return cards;
    }

    public int getLogPointer() {
        return lastSeen;
    }

    public void setLogPointer(int pointer) {
        lastSeen = pointer;
    }
    
    public String getNotes(){
        return notes;
    }
    
    public void setNotes(String notes){
        this.notes = notes;
    }
    
    //TODO move to AiAdvanced constructor
    public void setAi(){
        isAi = true;
    }
    
    public boolean isAi(){
        return isAi;
    }
    
    public String toString(){
        String result = "";
        result += "Player id: "+id;
        result += " isAi: "+isAi();
        result += " position: "+getPosition();
        
        
        return result;
    }
    
//    /**
//     * Executes a sequence of moves
//     *
//     * @param tiles a queue of destination tiles
//     * @throws clue.player.Player.MovementException when the player makes an
//     * invalid move
//     */
//    private void doMove(Queue<Tile> tiles) throws MovementException, InterruptedException {
//        if (tiles.size() <= movements) {
//            sendAction(move(tiles));
//        } else {
//            throw new MovementException();
//        }
//    }
//    /**
//     * Attempts to move from the current position to a new tile.
//     *
//     * @param t the destination tile.
//     * @return new MoveAction
//     */
//    private Action move(Queue<Tile> t) {
//        return new MoveAction(t, this);
//    }
//    /**
//     * Suggests a set of cards as the murder details
//     *
//     * @param person suspect
//     * @param room crime scene
//     * @param weapon murder weapon
//     * @return new SuggestAction
//     */
//    private void suggest(PersonCard person, RoomCard room, WeaponCard weapon) throws InterruptedException {
//        sendAction(game.suggest(person, room, weapon, this));
//    }
//    /**
//     * Accuses a set of cards, resulting in this player becoming removed from
//     * the game. If the accusation is correct, the game ends and this Player is
//     * the winner.
//     *
//     * @param person suspect
//     * @param room crime scene
//     * @param weapon murder weapon
//     * @return new AccuseAction
//     */
//    public Action Accuse(PersonCard person, RoomCard room, WeaponCard weapon) {
//        return new AccuseAction(this, person, room, weapon, game.CheckAccuse(person, room, weapon));
//    }
//    /**
//     * sends an action to the GameController to be executed.
//     *
//     * @param action the action to be executed
//     */
//    public void sendAction(Action action) throws InterruptedException {
//        if (active) {
//            try {
//                game.performAction(action);
//            } catch (UnknownActionException ex) {
//                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}

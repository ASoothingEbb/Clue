/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.player.Player;
import java.util.List;

/**
 * Represents the state of an instance of a Clue game.
 *
 * @author slb35
 */
public class GameState {

    /**
     * the number of players in the game.
     */
    public int playersNumber;
    /**
     * All players in the game including inactive players.
     */
    private List<Player> players;
    /**
     * The Player on the previous turn.
     */
    private Player previousPlayer;
    /**
     * The Player whose turn it is.
     */
    private Player currentPlayer;
    /**
     * The last Action object that updated the state.
     */
    private Action lastAction;
    /**
     * The id of the current turn.
     */
    private int turn;
    /**
     * Indicates whether or not the game is currently playing.
     */
    private boolean running;

    /**
     * Creates a new GameState object to keep track of the game logic.
     *
     * @param players A list of Players present in the game
     */
    public GameState(List<Player> players) {
        this.players = players;
        previousPlayer = getStartingPlayer();// TODO should be null?
        currentPlayer = getStartingPlayer(); 
        running = true;
        playersNumber = players.size();
        System.out.println("[GameState.constructor]");
    }




    /**
     * Returns the id of the next player in the player list.
     * @return the id of the next player
     */
    public int nextPlayer() {
        System.out.println("[GameState.nextPlayer]");
        int i = -1;
        Player next = currentPlayer;
        if (running && hasActive()) {
            i = turn;
            
            i++;
            if (i >= players.size()){
                i = 0;
            }
            System.out.println("[GameState.nextPlayer] i = "+i);
            next = players.get(i);
            System.out.println("[GameState.nextPlayer] player id = " + next.getId() + ", active: "  + next.isActive());
            while (!next.isActive()){
                i++;
                if (i >= players.size()){
                    i = 0;
                }
                next = players.get(i);
            }
            
        } 
        else{
            System.out.println("[GameState.nextPlayer] running: "+running+" hasActive: "+hasActive()+"----------------");
        }
        System.out.println("[GameState.nextPlayer] player index: "+i);
        return i;
        
    }

    /**
     * Gets the starting player in the list
     * @return the starting player
     */
    public final Player getStartingPlayer(){
        for (Player p : players){
            if (p.isActive()){
                return p;
            }    
        }
        return null;
    }
    
    /**
     * Gets the next pointer in the player list using wraparound
     * @param i the current pointer
     * @return the next pointer
     */
    public int getNextPointer(int i) {
        System.out.println("[GameState.getNextPointer]");
        if (i + 1 == players.size()) {
            i = 0;
        } else {
            i++;
        }
        return i;
    }

    /**
     * Sets the current turn pointer to the player with the specified id
     *
     * @param player id of next player
     */
    public void nextTurn(int player) {
        
        previousPlayer = currentPlayer;
        currentPlayer = players.get(player);
        turn = currentPlayer.getId();
        System.out.println("[GameState.nextTurn] new current player: "+turn) ;
    }

    /**
     * Returns the id of the current player
     *
     * @return player id of the current player
     */
    public int getPlayerTurn() {
        System.out.println("[GameState.getPlayerTurn]turn:"+turn+" playerId: "+currentPlayer.getId());
        return currentPlayer.getId();
    }



    /**
     * gets the last Action performed
     *
     * @return last Action update
     */
    public Action getAction() {
        return this.lastAction;
    }

    /**
     * Gets a player object with the specified id
     * @param id the id of the player object to fetch
     * @return the fetched player
     */
    public Player getPlayer(int id) {
        return players.get(id);
    }

    /**
     * gets whether or not the game is currently playing.
     *
     * @return active (true or false)
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Ends the current game instance.
     *
     * @return the final player of the game
     */
    public Player endGame() {
        running = false;
        return currentPlayer;
    }

    /**
     * Updates the last performed Action.
     *
     * @param action Action that was performed.
     */
    public void setAction(Action action) {
        this.lastAction = action;
    }

    /**
     * Gets the current player who's turn it is
     * @return the current player who's turn it is
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * Gets whether or not the game state has an active player in it
     * @return true if game state contains an active player, false otherwise
     */
    public boolean hasActive(){
        System.out.println("[GameState.hasActive]");
        boolean active = false;
        for (Player p : players){
            if (p.isActive()){
                active = true;
            }
        }
        System.out.println("[GameState.hasActive] has active: "+active);
        return active;
    }    
        
}

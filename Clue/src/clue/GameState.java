/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.player.Player;
import java.util.Iterator;
import java.util.List;

/**
 *Represents the state of an instance of a Clue game.
 * @author slb35
 */
public class GameState implements Subject{

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
     * @param players A list of Players present in the game
     */
    public GameState(List<Player> players){
        this.players = players;
        running = true;
        playersNumber = players.size();
    }

    /**
     *Adds a Player to the list of Players.
     * @param observer The player to add
     */
    @Override
    public void register(Observer observer) {
        players.add((Player) observer);
    }

    /**
     *Removes a player from the game.
     *Note that players are not actually removed from the list of players, but instead they are marked as inactive.
     * @param observer The player to remove
     */
    @Override
    public void unregister(Observer observer) {
        ((Player)observer).removeFromPlay();
    }

    /**
     *Issues a game state update to all players in the game.
     */
    @Override
    public void notifyAllObservers() {
        for(Player p: players){
            p.onUpdate();
        }
    }

    /**
     *Returns the id of the next player in the player list
     */
    public int nextPlayer(){
        int next = 0;
        if(running){
        while(!currentPlayer.isActive()){
        turn++;
        next = players.get(turn).getId();
        }
        }
        return next;
    }

    /**
     * Sets the current turn pointer to the player with the specified id
     * @param player id of next player
     */
        public void nextTurn(int player){
        previousPlayer = currentPlayer;
        currentPlayer = players.get(player);
        turn = currentPlayer.getId();
    }
    
    /**
     *Returns the id of the current player
     * @return player id
     */
    public int getPlayerTurn(){
        return this.currentPlayer.getId();
    }
    


    /**
     *Sets the previous player to be the current player
     */
    public void previousPlayer(){
        currentPlayer = previousPlayer;
    }

    /**
     *gets the last Action performed on this GameState.
     * @return last Action update
     */
    public Action getAction(){
        return this.lastAction;
    }
    /**
     * gets whether or not the game is currently playing.
     * @return active (true or false)
     */
    public boolean isRunning(){
        return running;
    }
    

    /**
     *Ends the current game instance.
     * @return
     */
    public Player endGame(){
        running = false;
        return currentPlayer;
    }

    /**
     *Updates the last performed Action.
     * @param action Action that was performed.
     */
    public void setAction(Action action){
        this.lastAction = action;
    }
}

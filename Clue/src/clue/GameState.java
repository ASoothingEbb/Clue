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
 *
 * @author slb35
 */
public class GameState implements Subject{

    public int playersNumber;
    private List<Player> players;
    private Player previousPlayer;
    private Player currentPlayer;
    private Action lastAction;
    private int turn;
    private boolean running;

    /**
     *
     * @param players
     */
    public GameState(List<Player> players){
        this.players = players;
        running = true;
        playersNumber = players.size();
    }

    /**
     *
     * @param observer
     */
    @Override
    public void register(Observer observer) {
        players.add((Player) observer);
    }

    /**
     *
     * @param observer
     */
    @Override
    public void unregister(Observer observer) {
        ((Player)observer).removeFromPlay();
    }

    /**
     *
     */
    @Override
    public void notifyAllObservers() {
        for(Player p: players){
            p.onUpdate();
        }
    }

    /**
     *
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

        public void nextTurn(int player){
        previousPlayer = currentPlayer;
        currentPlayer = players.get(player);
    }
    
    /**
     *
     * @return
     */
    public int getPlayerTurn(){
        return this.currentPlayer.getId();
    }
    


    /**
     *
     */
    public void previousPlayer(){
        currentPlayer = previousPlayer;
    }

    /**
     *
     * @return
     */
    public Action getAction(){
        return this.lastAction;
    }
    /**
     * 
     * @return 
     */
    public boolean isRunning(){
        return running;
    }
    

    /**
     *
     * @return
     */
    public Player endGame(){
        running = false;
        return currentPlayer;
    }

    /**
     *
     * @param action
     */
    public void setAction(Action action){
        this.lastAction = action;
    }
}

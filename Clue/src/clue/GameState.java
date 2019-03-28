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

    private List<Player> players;
    private Player previousPlayer;
    private Player currentPlayer;
    private Action lastAction;
    private int turn;
    public GameState(List<Player> players){
        this.players = players;
    }
    @Override
    public void register(Observer observer) {
        players.add((Player) observer);
    }

    @Override
    public void unregister(Observer observer) {
        ((Player)observer).removeFromPlay();
    }

    @Override
    public void notifyAllObservers() {
        for(Player p: players){
            p.onUpdate();
        }
    }
    public void nextTurn(){
        previousPlayer = currentPlayer;
        while(!currentPlayer.active){
        turn++;
        currentPlayer = players.get(turn);
        }
    }
    public int getPlayerTurn(){
        return this.currentPlayer.getId();
    }
    public void previousPlayer(){
        currentPlayer = previousPlayer;
    }
    public Action getAction(){
        return this.lastAction;
    }
    public void setAction(Action action){
        this.lastAction = action;
    }
}

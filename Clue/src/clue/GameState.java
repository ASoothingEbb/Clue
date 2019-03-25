/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.player.Player;
import java.util.List;

/**
 *
 * @author slb35
 */
public class GameState implements Subject{

    private List<Player> players;
    private Player currentPlayerTurn;
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
    
    
}

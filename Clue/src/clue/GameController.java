/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;

/**
 *
 * @author slb35
 */
public class GameController {
    private GameState state;
    public GameController(){
        
    }
    public void makeAction(Action action){
        
        state.notifyAllObservers();
    }
}

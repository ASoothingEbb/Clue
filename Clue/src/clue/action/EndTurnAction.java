/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;

/**
 *Represents the end of the game turn.
 * @author steve
 */
public class EndTurnAction extends Action{

    /**
     * Creates a new End Turn Action .
     * @param player
     */
    public EndTurnAction(Player player) {
        super(player);
        this.actionType = ActionType.ENDTURN;
    }

    /**
     * Run by the gameController when this action is meant to be executed.
     */
    @Override
    public void execute() {
        super.execute(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

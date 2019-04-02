/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;

/**
 *Represents the start of the next turn.
 * @author steve
 */
public class StartTurnAction extends Action{

    public ActionType actionType = ActionType.STARTTURN;
    /**
     * Creates a new StartTurnAction.
     * @param player the Player whose turn it is.
     */
    public StartTurnAction(Player player) {
        super(player);
    }
  
}

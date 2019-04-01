/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;

/**
 *
 * @author steve
 */
public class StartTurnAction extends Action{

    public ActionType actionType = ActionType.STARTTURN;
    public StartTurnAction(Player player) {
        super(player);
    }
  
}

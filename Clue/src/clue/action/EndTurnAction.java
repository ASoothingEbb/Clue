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
     *
     * @param player
     */
    public EndTurnAction(Player player) {
        super(player);
        this.actionType = ActionType.ENDTURN;
    }
}

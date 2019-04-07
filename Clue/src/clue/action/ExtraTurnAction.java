/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;

/**
 *Represents a player taking an extra turn through the use of an intrigue card.
 * @author slb35
 */
public class ExtraTurnAction extends Action{

        public ActionType actionType = ActionType.EXTRATURN;
        
        /**
         * Creates a new ExtraTurnAction
         * @param player the Player to roll again
         */
        
        
        public ExtraTurnAction(Player player){
            super(player);
        }
}

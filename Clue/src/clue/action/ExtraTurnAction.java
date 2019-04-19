/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.ExtraTurnIntrigue;
import clue.player.Player;

/**
 * Represents a player taking an extra turn through the use of an intrigue card.
 *
 * @author slb35
 */
public class ExtraTurnAction extends Action {

    /**
     * Creates a new ExtraTurnAction
     *
     * @param player the Player to roll again
     * @param card
     */
    public ExtraTurnAction(Player player, ExtraTurnIntrigue card) {
        super(player, card);
        this.actionType = ActionType.EXTRATURN;
    }

    /**
     * Executes StartAction. Allows player to roll again.
     */
    @Override
    public void execute() {
        player.removeIntrigue((ExtraTurnIntrigue)card);
        super.execute();
    }
    
}

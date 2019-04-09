/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.ThrowAgainIntrigue;
import clue.player.Player;

/**
 *Represents a Player being able to roll again due to a roll again intrigue card
 * being used.
 * @author slb35
 */
public class ThrowAgainAction extends Action{
        /**
         * Creates a new ThrowAgainAction
         * @param player the Player to roll again
         */
        public ThrowAgainAction(Player player,ThrowAgainIntrigue card){
            super(player,card);
        this.actionType = ActionType.THROWAGAIN;
        }

    @Override
    public void execute() {
        player.removeCard(card);
    }
        
}

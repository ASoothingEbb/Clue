/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.AiAdvanced;
import clue.card.ThrowAgainIntrigue;
import clue.client.gameInstance;
import clue.player.Player;

/**
 * Represents a Player being able to roll again due to a roll again intrigue
 * card being used.
 *
 * @author slb35
 */
public class ThrowAgainAction extends Action {

    /**
     * Creates a new ThrowAgainAction
     *
     * @param player the Player to roll again
     * @param card the intrigue card
     */
    public ThrowAgainAction(Player player, ThrowAgainIntrigue card) {
        super(player, card);
        this.actionType = ActionType.THROWAGAIN;
    }

    /**
     * Executes the ThrowAgainAction. Allows user to roll again.
     */
    @Override
    public void execute() {
        player.removeIntrigue((ThrowAgainIntrigue)card);
        if (player instanceof AiAdvanced) {
            ((AiAdvanced) player).respondToThrowAgain();
        }
    }

}

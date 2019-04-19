/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.ai.AiAdvanced;
import clue.card.ThrowAgainIntrigue;
import clue.client.gameInstance;
import clue.player.Player;

/**
 *Represents a Player being able to roll again due to a roll again intrigue card
 * being used.
 * @author slb35
 */
public class ThrowAgainAction extends Action{
    gameInstance gui;
     /**
     * Creates a new ThrowAgainAction
     * @param player the Player to roll again
     * @param card
     * @param gui
     */
    public ThrowAgainAction(Player player,ThrowAgainIntrigue card, gameInstance gui){
        super(player,card);
        this.actionType = ActionType.THROWAGAIN;
        this.gui = gui;
    }
    /**
     * Executes the ThrowAgainAction.
     * Allows user to roll again.
     */
    @Override
    public void execute() {
        player.removeCard(card);
        if (player.isAi()){
            ((AiAdvanced) player).respondToThrowAgain();

        }
        else if (gui != null){
            gui.actionResponse(this);
        }
        else{
            System.out.println("[ThrowAgainAction.execute] no gui found");
        }
    }
        
}

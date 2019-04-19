/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.ai.AiAdvanced;
import clue.card.AvoidSuggestionIntrigue;
import clue.client.gameInstance;
import clue.player.Player;

/**
 * Represents a player avoiding showing cards during the ShowCardAction through
 * use of an intrigue card.
 *
 * @author slb35
 */
public class AvoidSuggestionAction extends Action {

    public AvoidSuggestionIntrigue card;
    private gameInstance gui;

    /**
     * Creates a new AvoidSuggestionAction
     *
     * @param player the Player to roll again
     * @param card the AvoidSuggestionIntrigue associated with this action
     * @deprecated legacy version used for testing only
     */
    public AvoidSuggestionAction(Player player, AvoidSuggestionIntrigue card) {
        super(player,card);
        this.actionType = ActionType.AVOIDSUGGESTIONCARD;
        gui = null;
        
    }

    /**
     * Creates a new AvoidSuggestionAction
     *
     * @param player the Player to roll again
     * @param card the AvoidSuggestionIntrigue associated with this action
     * 
     */
    public AvoidSuggestionAction(Player player, AvoidSuggestionIntrigue card, gameInstance gui) {
        super(player,card);
        this.actionType = ActionType.AVOIDSUGGESTIONCARD;
        this.gui = gui;
    }
    
    /**
     * Run by the gameController when it is executed.
     */
    @Override
    public void execute() {
        player.removeCard(card);
        if (player.isAi()){
            //((AiAdvanced) player).notifyAvoidSuggestion();

        }
        else if (gui != null){
            gui.actionResponse(this);
        }
        else{
            System.out.println("[ThrowAgainAction.execute] no gui found");
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.AvoidSuggestionIntrigue;
import clue.client.GameInstance;
import clue.player.AiAdvanced;
import clue.player.Player;

/**
 * Represents a player avoiding showing cards during the ShowCardAction through
 * use of an intrigue card.
 *
 * @author slb35
 */
public class AvoidSuggestionAction extends Action {

    public AvoidSuggestionIntrigue card;
    private GameInstance gui;

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
     * @param gui the GameInstance GUI that may need to be prompted
     * 
     */
    public AvoidSuggestionAction(Player player, AvoidSuggestionIntrigue card, GameInstance gui) {
        super(player,card);
        this.actionType = ActionType.AVOIDSUGGESTIONCARD;
        this.gui = gui;
    }
    
    /**
     * Executes the AvoidSuggestionAction. Means that the player had no cards to show.
     */
    @Override
    public void execute() {
        player.removeIntrigue((AvoidSuggestionIntrigue)card);
        if (player instanceof AiAdvanced){

        }
        else if (gui != null){
            gui.actionResponse(this);
        }
        else{
            System.out.println("[ThrowAgainAction.execute] no gui found");
        }
    }
}

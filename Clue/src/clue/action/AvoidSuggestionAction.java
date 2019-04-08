/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.AvoidSuggestionIntrigue;
import clue.player.Player;

/**
 * Represents a player avoiding showing cards during the ShowCardAction through
 * use of an intrigue card.
 *
 * @author slb35
 */
public class AvoidSuggestionAction extends Action {

    public AvoidSuggestionIntrigue card;

    /**
     * Creates a new AvoidSuggestionAction
     *
     * @param player the Player to roll again
     * @param card the AvoidSuggestionIntrigue associated with this action
     */
    public AvoidSuggestionAction(Player player, AvoidSuggestionIntrigue card) {
        super(player,card);
        this.actionType = ActionType.AVOIDSUGGESTIONCARD;
    }

    @Override
    public void execute() {
        player.removeCard(card);
    }
}

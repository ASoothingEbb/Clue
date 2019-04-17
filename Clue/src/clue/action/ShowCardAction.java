/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.Card;
import clue.player.Player;
import java.util.List;

/**
 *Represents one player showing a card to another player.
 * @author slb35
 */
public class ShowCardAction extends Action {
    private final Card card;
/**
 * Creates a new ShowCardAction
 * @param player the Player to show the Card to
 * @param card the Card to be shown.
 */
    public ShowCardAction(Player player,Card card) {
        super(player);
        this.actionType = ActionType.SHOWCARD;
        this.card = card;
    }
/**
 * Executes the ShowCardAction.
 */
    @Override
    public void execute() {
        super.execute();
    }

}

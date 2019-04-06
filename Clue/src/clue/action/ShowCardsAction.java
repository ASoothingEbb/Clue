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
 *Represents a player being prompted to show a card.
 * @author steve
 */
public class ShowCardsAction extends Action{

    ActionType actionType = ActionType.SHOWCARDS;
    private List<Card> cards;
    private Player suggester;

    /**
     * Creates a new ShowCardsAction
     * @param player the Player to be prompted
     * @param cards the cards to prompt
     */

    public ShowCardsAction(Player player, Player suggester, List<Card> cards) {
        super(player);
        this.cards = cards;
        this.suggester = suggester;

    }

    /**
     * Executes the ShowCardsAction. Stores result in result.
     */
    @Override
    public void execute() {
        super.execute(); //To change body of generated methods, choose Tools | Templates.
    }
    

    /**
     * Returns the Card list for this action.
     * @return cards
     */
    public List<Card> getCardList(){
        return cards;
    }
    
    /**
     * Returns the player object of the player who requested the cards(suggested).
     * @return suggester
     */
    public Player getSuggester() {
        return suggester;
    }
}

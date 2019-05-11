/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.GameController;
import clue.player.AiAdvanced;
import clue.card.Card;
import clue.card.CardType;
import clue.client.GameInstance;
import clue.player.Player;
import java.util.List;

/**
 *Represents a player being prompted to show a card.
 * @author steve
 */
public class ShowCardsAction extends Action{

    private final List<Card> cards;
    private final Player suggester;
    private int idOfCardToShow;
    private CardType typeOfCardToShow;

    /**
     * Creates a new ShowCardsAction
     * @param player the Player to be prompted
     * @param suggester the Player who initiated the suggestion
     * @param cards the cards to prompt
     */

    public ShowCardsAction(Player player, Player suggester, List<Card> cards) {
        super(player);
        this.actionType = ActionType.SHOWCARDS;
        this.cards = cards;
        this.suggester = suggester;
        idOfCardToShow = -1;
        typeOfCardToShow = null;

    }

    /**
     * Executes the ShowCardsAction. Stores result in result.
     */
    @Override
    public void execute() {
        if (player instanceof AiAdvanced){
            Card responseCard = ((AiAdvanced) player).respondToShowCards(cards);
            idOfCardToShow = responseCard.getId();
            typeOfCardToShow = responseCard.getCardType();
        }
    }
    
    /**
     * Sets the card to be shown 
     * 
     * @param i the id of the card.
     * @param type the type of the card to show  
     */
    public void setCardToShow(int i, CardType type){
        idOfCardToShow = i;
        typeOfCardToShow = type;
    }
    
    /**
     * @return the ID of the card to be shown. 
     */
    public int getIdOfCardToShow(){
        return idOfCardToShow;
    }
    
    /**
     * @return the TYPE of card the be shown.
     */
    public CardType getCardTypeOfCardToShow(){
        return typeOfCardToShow;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.ai.AiAdvanced;
import clue.card.Card;
import clue.card.CardType;
import clue.client.gameInstance;
import clue.player.Player;
import java.util.List;

/**
 *Represents a player being prompted to show a card.
 * @author steve
 */
public class ShowCardsAction extends Action{

    private final List<Card> cards;
    private final Player suggester;
    private gameInstance gui;
    private int idOfCardToShow;
    private CardType typeOfCardToShow;

    /**
     * Creates a new ShowCardsAction
     * @param player the Player to be prompted
     * @param suggester the Player who initiated the suggestion
     * @param cards the cards to prompt
     */

    public ShowCardsAction(Player player, Player suggester, List<Card> cards, gameInstance gui) {
        super(player);
        this.actionType = ActionType.SHOWCARDS;
        this.cards = cards;
        this.suggester = suggester;
        this.gui = gui;
        idOfCardToShow = -1;
        typeOfCardToShow = null;

    }

    /**
     * 
     * @deprecated Test purposes only
     * @param player
     * @param suggester
     * @param cards 
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
        if (player.isAi()){
            Card responseCard = ((AiAdvanced) player).respondToShowCards(cards);
            idOfCardToShow = responseCard.getId();
            typeOfCardToShow = responseCard.cardType;
        }
        else{
            gui.actionResponse(this);
        }
    }
    
    public void setCardToShow(int i, CardType type){
        idOfCardToShow = i;
        typeOfCardToShow = type;
    }
    
    public int getIdOfCardToShow(){
        return idOfCardToShow;
    }
    
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

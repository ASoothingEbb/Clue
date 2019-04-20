/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

/**
 * Represents an intrigue card.
 *
 * @author slb35
 */
public abstract class IntrigueCard extends Card {

    /**
     * Creates an IntregueCard
     * @param id the id of the card
     */
    public IntrigueCard(int id) {
        super(id);
    }

    /**
     * Creates an Action update for the intrigue action.
     *
     * @return intrigue Action.
     */
    
    
    /**
     * Gets the card enum type.
     *
     * @return Card Type
     */
    public CardType getCardType() {
        return cardType;
    }
    
    /**
     * Checks if two card types are the same
     * @param c the card to compare to
     * @return true if same enum type false otherwise
     */
    public boolean equals(Card c){
        return (cardType == c.cardType);
    }
}

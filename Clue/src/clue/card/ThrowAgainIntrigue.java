/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

import clue.action.Action;

/**
 * Represents an IntrigueCard that allows Players to roll their movement again
 * for this turn.
 *
 * @author slb35
 */
public class ThrowAgainIntrigue extends IntrigueCard {

    /**
     * Creates a ThrowAgainIntrigue Card
     * @param id the id of the card
     */
    public ThrowAgainIntrigue(int id) {
        super(id);
        cardType = CardType.THROWAGAIN;
    }

    /**
     * Gets the type of the card
     * @return the enum type of the card
     */
    @Override
    public CardType getCardType() {
        return cardType;
    }

}

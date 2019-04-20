/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

/**
 * Represents an IntrigueCard that allows players to take an extra turn.
 *
 * @author slb35
 */
public class ExtraTurnIntrigue extends IntrigueCard {

    /**
     * Creates an ExtraTurnIntrigue Card.
     * @param id the id of the card
     */
    public ExtraTurnIntrigue(int id) {
        super(id);
        cardType = CardType.EXTRATURN;
    }

    /**
     * Gets the card enum type.
     *
     * @return Card Type
     */
    @Override
    public CardType getCardType() {
        return cardType;
    }

}

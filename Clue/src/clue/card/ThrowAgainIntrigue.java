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

    public ThrowAgainIntrigue(int id) {
        super(id);
        cardType = CardType.THROWAGAIN;
    }

    @Override
    public CardType getCardType() {
        return cardType;
    }

}

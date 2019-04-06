/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

import clue.action.Action;

/**
 * Represents an IntrigueCard that allows players to avoid responding to a
 * suggestion.
 *
 * @author slb35
 */
public class AvoidSuggestionIntrigue extends IntrigueCard {

    public AvoidSuggestionIntrigue(int id) {
        super(id);
        cardType = CardType.AVOIDSUGGESTION;
    }

    @Override
    public CardType getCardType() {
        return cardType;
    }
}

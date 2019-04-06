/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

import clue.action.Action;

/**
 * Represents an intrigue card.
 *
 * @author slb35
 */
public abstract class IntrigueCard extends Card {

    public IntrigueCard(int id) {
        super(id);
    }

    /**
     * Creates an Action update for the intrigue action.
     *
     * @return intrigue Action.
     */
    public CardType getCardType() {
        return cardType;
    }
}

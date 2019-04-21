/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

import clue.action.Action;

/**
 * Represents an intrigue card that allows players to teleport to a new Tile or
 * room.
 *
 * @author slb35
 */
public class TeleportIntrigue extends IntrigueCard {

    /**
     * Creates a TeleportIntrigue Card
     * @param id the id of the card
     */
    public TeleportIntrigue(int id) {
        super(id);
        cardType = CardType.TELEPORT;
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

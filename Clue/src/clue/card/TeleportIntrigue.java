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

    public TeleportIntrigue(int id) {
        super(id);
        cardType = CardType.TELEPORT;
    }

    @Override
    public CardType getCardType() {
        return cardType;
    }

}

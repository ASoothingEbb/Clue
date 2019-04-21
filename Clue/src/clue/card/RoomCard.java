/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

/**
 *Represents a card for a room.
 * @author slb35
 */
public class RoomCard extends Card {

    /**
     * Creates a RoomCard
     * @param id ID of the room
     */
    public RoomCard(int id) {
        super(id);
        cardType = CardType.ROOM;
    }
}
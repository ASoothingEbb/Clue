/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

/**
 *Represents a game card. This may be a person, room, weapon or intrigue card.
 * @author slb35
 */
public abstract class Card {

    private int id;

    public Card(int id) {
        this.id = id;
    }
    
    /**
     * Gets the card id.
     * @return id
     */
    public int getid(){
        return id;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

/**
 *Represents a card for a person.
 * @author slb35
 */
public class PersonCard extends Card{

    /**
     * Creates a PersonCard
     * @param id the id of the card
     */
    public PersonCard(int id) {
        super(id);
        cardType = CardType.PERSON;
    }
    
    
}

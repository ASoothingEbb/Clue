/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

import clue.tile.Room;

/**
 *Represents a card for a weapon.
 * @author slb35
 */
public class WeaponCard extends Card{

    private Room position;
    private int[] drawnLocation;
    
    /**
     * Creates a Weapon Card
     * @param id the id of the weapon card
     */
    public WeaponCard(int id) {
        super(id);
        cardType = CardType.WEAPON;
        drawnLocation = new int[2];
        drawnLocation[0] = -1;
        drawnLocation[1] = -1;
        
    }

    /**
     * Moves the token of the card to the specified room on the board
     * @param t destination room
     */
    public void setPosition(Room t) {
        
        if (position!= null){
            position.unassignLocation(drawnLocation);//allow room to re assign location
        }
        
        position = t;
        drawnLocation = t.assignLocation();//get a location from the room
  
    }
    
    /**
     * Gets the x coordinate that the token should be drawn at
     * @return the x coordinate
     */
    public int getDrawX(){
        return drawnLocation[0];
    }
    
    /**
     * Gets the y coordinate that the token should be drawn at
     * @return the y coordinate
     */
    public int getDrawY(){
        return drawnLocation[1];
    }
}

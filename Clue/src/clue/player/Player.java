/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.player;

import clue.action.Action;
import clue.card.Card;
import clue.Observer;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.tile.Tile;
import clue.card.WeaponCard;
import java.util.List;

/**
 *
 * @author slb35
 */
public abstract class Player implements Observer{

    public boolean active;
    private List<Card> cards;
    private Tile position;
    private int movements;
    private int id;
    
    @Override
    public void onUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private Action move(Tile s, Tile t){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private Action suggest(RoomCard room, PersonCard person, WeaponCard weapon){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private Action Accuse(RoomCard room, PersonCard person, WeaponCard weapon){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private void sendAction(Action action){} 
}

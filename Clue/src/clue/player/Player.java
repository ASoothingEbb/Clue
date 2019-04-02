/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.player;

import clue.GameController;
import clue.action.Action;
import clue.card.Card;
import clue.Observer;
import clue.action.AccusationAction;
import clue.action.MoveAction;
import clue.action.SuggestAction;
import clue.action.UnknownActionException;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.tile.Tile;
import clue.card.WeaponCard;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author slb35
 */
public abstract class Player implements Observer{

    private boolean active;
    private List<Card> cards;
    private Tile position;
    private int movements;
    private int id;
    private GameController game;
    
    public Player(){
        active = true;
    }
    
    @Override
    public void onUpdate() {
    }
    public int getId(){
        return this.id;
    }
    private Action move(Tile t){
        return new MoveAction(position,t,this);
    }
    private Action suggest(RoomCard room, PersonCard person, WeaponCard weapon){
        return new SuggestAction(person, room, weapon, this);
    }
    private Action Accuse(PersonCard person, RoomCard room, WeaponCard weapon){
        return new AccusationAction(this,person,room,weapon);
    }
    private void sendAction(Action action){
        if(active){
            try {
                game.performAction(action);
            } catch (UnknownActionException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public boolean isActive(){
        return active;
    }
    public void removeFromPlay(){
        active = false;
    }
    public Tile getPosition(){
        return position;
    }
    public void setPosition(Tile t) {
        position = t;
    }
}

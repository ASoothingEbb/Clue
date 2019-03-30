/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;

/**
 *
 * @author slb35
 */
public class AccusationAction implements Action {

    private PersonCard person;
    private RoomCard room;
    private WeaponCard weapon;
    private Player player;

    ActionType actionType = ActionType.ACCUSATION;

    public AccusationAction(Player player, PersonCard person, RoomCard room, WeaponCard weapon) {
        super();
        this.person = person;
        this.room = room;
        this.weapon = weapon;
        this.player = player;
    }

    public boolean authenticate(PersonCard person, RoomCard room, WeaponCard weapon) {
        player.removeFromPlay();
        if (this.person.getid() == person.getid() && this.room.getid() == room.getid() && this.weapon.getid() == weapon.getid()) {
            return true;
        } else {
            return false;
        }
    }
}

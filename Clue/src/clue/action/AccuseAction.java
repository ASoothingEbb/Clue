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
 *Represents a player making an accusation
 * @author slb35
 */
public class AccuseAction extends Action {

    private final PersonCard person;
    public RoomCard room;
    public WeaponCard weapon;

    /**
     * Creates a new AccusationAction.
     * @param player the accusing Player
     * @param person the person card to accuse
     * @param room the room card to accuse
     * @param weapon the weapon card to accuse
     * @param result true if the players accusation was correct, false otherwise
     */
    public AccuseAction(Player player, PersonCard person, RoomCard room, WeaponCard weapon,boolean result) {
        super(player);
        this.actionType = ActionType.ACCUSATION;
        this.person = person;
        this.room = room;
        this.weapon = weapon;
        this.player = player;
        this.result = result;
    }

    /**
     * executes the AccusationAction, removing the player from play.
     */
    @Override
    public void execute() {
        player.removeFromPlay();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.GameState;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;

/**
 *Represents a Player making a suggestion.
 * @author slb35
 */
public class SuggestAction extends Action{
        public ActionType actionType = ActionType.SUGGEST;
        public Player shower;
        private PersonCard person;
        private RoomCard room;
        private WeaponCard weapon;
        private GameState state;

        /**
         * Creates a new SuggestAction.
         * @param person the person to suggest
         * @param room the room to suggest
         * @param weapon the weapon to suggest
         * @param player the player making the suggestion
         */
    public SuggestAction(PersonCard person, RoomCard room, WeaponCard weapon,Player player) {
        super(player);
        this.person = person;
        this.room = room;
        this.weapon = weapon;
        this.state = state;
    }

    /**
     * Executes the SuggestAction. Result stores if another player has any of 
     * the suggested cards.
     */
    @Override
    public void execute() {
        int j = player.getId();
        boolean found = false;
        for(int i = 0;i< state.playersNumber;i++){
            if(j == player.getId()){
                
            }
        }
    }
        
    
}

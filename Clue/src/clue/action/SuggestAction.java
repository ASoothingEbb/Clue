/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.GameState;
import clue.card.Card;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Player making a suggestion.
 *
 * @author slb35
 */
public class SuggestAction extends Action {

    public ActionType actionType = ActionType.SUGGEST;
    public Player show;
    public List<Card> foundCards;
    private Card[] cards;
    private GameState state;

    /**
     * Creates a new SuggestAction.
     *
     * @param person the person to suggest
     * @param room the room to suggest
     * @param weapon the weapon to suggest
     * @param player the player making the suggestion
     */
    public SuggestAction(PersonCard person, RoomCard room, WeaponCard weapon, Player player,GameState state) {
        super(player);
        this.cards = new Card[]{person, room, weapon};
        this.state = state;
        this.foundCards = new ArrayList();
    }

    /**
     * Executes the SuggestAction. Result stores if another player has any of
     * the suggested cards. show stores the 
     */
    @Override
    public void execute() {
        Player check;
        int j = player.getId();
        boolean found = false;
        for (int i = 0; i < state.playersNumber; i++) {
            check = state.getPlayer(j);
            if (j != player.getId() && check.getActiveSuggestionBlock() == false) {
                for (Card c : cards) {
                    if (check.hasCard(c)) {
                        show = state.getPlayer(j);
                        foundCards.add(c);
                        found = true;
                    }
                }
                if(found){
                    break;
                }
            }
            j = state.getNextPointer(j);
        }
        result = found;
    }

}

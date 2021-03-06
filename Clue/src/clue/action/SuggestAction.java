/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.GameState;
import clue.card.Card;
import clue.card.CardType;
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

    private Player show;
    private List<Card> foundCards;
    private final Card[] cards;
    private List<Player> players;

    /**
     * Creates a new SuggestAction.
     *
     * @param person the person to suggest
     * @param room the room to suggest
     * @param weapon the weapon to suggest
     * @param player the player making the suggestion
     * @param players the list of players in the game
     * 
     */
    public SuggestAction(PersonCard person, RoomCard room, WeaponCard weapon, Player player, List<Player> players) {
        super(player);
        this.actionType = ActionType.SUGGEST;
        this.cards = new Card[]{person, room, weapon};
        this.foundCards = new ArrayList();
        this.players = players;
        player.setMoves(0);
    }

    /**
     * Gets the room card used in the suggestion
     * @return the room card used in the suggestion
     */
    public RoomCard getRoomCard(){
        return (RoomCard)cards[1];
    }
    
    /**
     * Gets the person card used in the suggestion
     * @return the person card used in the suggestion
     */
    public PersonCard getPersonCard(){
        return (PersonCard)cards[0];
    }
    
    /**
     * Gets the weapon card used in the suggestion
     * @return the weapon card used in the suggestion
     */
    public WeaponCard getWeaponCard(){
        return (WeaponCard)cards[2];
    }
    
    
    
    /**
     * Executes the SuggestAction. Result stores if another player has any of
     * the suggested cards. show stores the 
     */
    @Override
    public void execute() {
        Player check;
        int i = player.getId();
        boolean found = false;
        int playersLeftToCheck = players.size()-1;
        System.out.println("[SuggestAction.execute] player is making a suggestion: "+i);
        while (playersLeftToCheck > 0) {
            i++;
            if (i >= players.size()){
                i = 0;
            }
            check = players.get(i);
            playersLeftToCheck--;
            if (player.getId() != i) {
                if (!check.hasIntrigue(CardType.AVOIDSUGGESTION)){
                    for (Card c : cards) {
                        if (check.hasCard(c)) {
                            show = players.get(i);
                            foundCards.add(c);
                            playersLeftToCheck = 0;
                            System.out.println("[SuggestAction.execute] player : "+check.getId()+" was found to have card: "+c);
                            found = true;
                        }
                    }
                }
                else{
                    for (Card c : cards) {
                        if (check.hasCard(c)) {
                            //TODO
                            //notify player with id i that thier avoid suggestion prevented them from revleaing card c
                            System.out.println("[SuggestAction.execute] player has avoided showing cards due to suggestion: "+i);
                               
                        }
                    }
                }
            }
            
            
        }
        result = found;
    }

    @Override
    public String toString() {
        String cards = "";
        for(Card c : this.cards){
            cards += c.getCardType()+ ":" + c.getId() + " ";
        }
        return super.toString() + cards;
    }

    /**
     * Gets the person who needs to show their card.
     * @return player who needs to show card
     */
    public Player getShower(){
        return show;
    }
    
    /**
     * Gets the cards which the player has which match the cards that were suggested.
     * @return list of cards of the player that match the suggested 
     */
    public List<Card> getFoundCards(){
        return foundCards;
    }
}

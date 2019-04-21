/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.Card;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.client.gameInstance;
import clue.player.AiAdvanced;
import clue.player.Player;
import java.util.ArrayList;

/**
 *Represents a player making an accusation
 * @author slb35
 */
public class AccuseAction extends Action {

    private final PersonCard person;
    public RoomCard room;
    public WeaponCard weapon;
    private gameInstance gui;
    private ArrayList<Card> murderCards;
    
     /**
     * Creates a new AccusationAction.
     * @param player the accusing Player
     * @param person the person card to accuse
     * @param room the room card to accuse
     * @param weapon the weapon card to accuse
     * @param murderPerson the person who conducted the murder
     * @param murderRoom the room which the murder took place
     * @param murderWeapon the weapon used in the murder
     * @param gui the gameInstance (GUI) that may need to be prompted
     * 
     */
    public AccuseAction(Player player, PersonCard person, RoomCard room, WeaponCard weapon,
                                       PersonCard murderPerson, RoomCard murderRoom, WeaponCard murderWeapon, gameInstance gui) {
        
        super(player);
        this.actionType = ActionType.ACCUSATION;
        this.person = person;
        this.room = room;
        this.weapon = weapon;
        this.player = player;
        
        murderCards = new ArrayList<>();
        murderCards.add(murderPerson);
        murderCards.add(murderRoom);
        murderCards.add(murderWeapon);
        this.gui = gui;
        
        result = murderPerson == person && murderRoom == room && murderWeapon == weapon;
    }
    
    
    /**
     * Creates a new AccusationAction.
     * @param player the accusing Player
     * @param person the person card to accuse
     * @param room the room card to accuse
     * @param weapon the weapon card to accuse
     * @param result true if the players accusation was correct, false otherwise
     * @deprecated this is a test / legacy method
     */
    public AccuseAction(Player player, PersonCard person, RoomCard room, WeaponCard weapon,boolean result) {
        super(player);
        this.actionType = ActionType.ACCUSATION;
        this.person = person;
        this.room = room;
        this.weapon = weapon;
        this.player = player;
        this.result = result;
        this.gui = null;
    }
    
    

    /**
     * executes the AccusationAction, removing the player from play.
     */
    @Override
    public void execute() {
        player.removeFromPlay();
        if (!(player instanceof AiAdvanced)){
            gui.actionResponse(this);
        }
    }
    
    
    /**
     * Gets the correct murder cards
     * @return the murder cards
     */
    public ArrayList<Card> getMurderCards(){
        return murderCards;
    }
    
    /**
     * Gets the result of the accusation
     * @return true if the player won the game, false if they lost and have thus been removed from the game
     */
    public boolean wasCorrect(){
        return result;
    }
    
    /**
     * Gets the accused card IDs
     * @return the accused card IDs
     */
    public int[] getAccusationCards() {
        int[] cards = new int[3];
        cards[0] = person.getId();
        cards[1] = weapon.getId();
        cards[2] = room.getId();
        return cards;
    }
}

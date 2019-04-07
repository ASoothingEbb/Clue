/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.ai;

import clue.GameController;
import clue.action.AccuseAction;
import clue.action.Action;
import clue.action.EndTurnAction;
import clue.action.ShowCardAction;
import clue.action.ShowCardsAction;
import clue.card.Card;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.AIPlayer;
import clue.player.Player;
import clue.tile.Tile;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zemig
 */
public class AiBasic extends AIPlayer{
    
//    private boolean active;
//    private List<Card> cards;
//    private Tile position;
//    private int movements;
//    private int id;
//    private GameController game;
    
    private GameController gameController;
    Random rand;
    
    
    public AiBasic(int id) {
        super(id);
        
        gameController = getGameController();
        rand = new Random();
    }
    
    @Override
    public void onUpdate() {//Runs after every event.
       
        //Generating Random Cards (ids).
        PersonCard randPersonCard = new PersonCard(rand.nextInt(6) + 1);
        RoomCard randRoomCard = new RoomCard(rand.nextInt(6) + 1);
        WeaponCard randWeaponCard = new WeaponCard(rand.nextInt(9)+ 1);
        
        
        if(gameController.getPlayer().getId() == getId()){//If I need to respond.
            if(gameController.getLastAction() instanceof EndTurnAction){//If my turn, last action was end turn.
                if(this.getPosition().isRoom()){//If I'm in a room
                    try {           
                        sendAction(Accuse(randPersonCard, randRoomCard, randWeaponCard));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AiBasic.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            } else if (gameController.getLastAction() instanceof ShowCardsAction){//If I need to show a card 

                ShowCardsAction action = (ShowCardsAction) gameController.getLastAction();
                Card card = action.getCardList().get(0);
                ShowCardAction newAction = new ShowCardAction(action.getSuggester(), card);//Shows one card to person who requested cards to be shown(suggested).

                try {
                    sendAction(newAction);//Show Card.
                } catch (InterruptedException ex) {
                    Logger.getLogger(AiBasic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    
}

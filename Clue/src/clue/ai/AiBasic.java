/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.ai;

import clue.GameController;
import clue.player.Player;
import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author zemig
 */
public class AiBasic extends Player{
    
    private int targetY;//Y value of the closest room tile.
    private int targetX;////Y value of the closest room tile
    private int x;
    private int y;
    
    private GameController gameController;
    Random rand;
    
    
    public AiBasic(int id, GameController gc) {
        super(id, gc);
        
        gameController = gc;
        rand = new Random(Calendar.getInstance().getTimeInMillis());
    }
    
    @Override
    public void onUpdate() {//Runs after every event.
       
        //Generating Random Cards (ids).
        //PersonCard randPersonCard = new PersonCard(rand.nextInt(6) + 1);
        //RoomCard randRoomCard = new RoomCard(rand.nextInt(6) + 1);
        //WeaponCard randWeaponCard = new WeaponCard(rand.nextInt(9)+ 1);
        
        
        //if(gameController.getPlayer().getId() == getId()){//If I need to respond.
        //    if(gameController.getLastAction() instanceof EndTurnAction){//If my turn, last action was end turn.
        //        if(this.getPosition().isRoom()){//If I'm in a room
        //            try {           
        //                game.accuse(randPersonCard, randRoomCard, randWeaponCard);
        //            } catch (InterruptedException | UnknownActionException | TileOccupiedException ex) {
        //                Logger.getLogger(AiBasic.class.getName()).log(Level.SEVERE, null, ex);
        //            }
        //        }
        //        
        //    } else if (gameController.getLastAction() instanceof ShowCardsAction){//If I need to show a card 

        //        ShowCardsAction action = (ShowCardsAction) gameController.getLastAction();
        //        Card card = action.getCardList().get(0);

        //        try {
        //            gameController.showCard(card);//Show Card.
        //        }catch (UnknownActionException | InterruptedException | TileOccupiedException ex) {
        //            Logger.getLogger(AiBasic.class.getName()).log(Level.SEVERE, null, ex);
        //        }
        //    }
        //}
    } 
}

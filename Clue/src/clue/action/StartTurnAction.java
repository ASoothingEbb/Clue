/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.ai.AiAdvanced;
import clue.card.Card;
import clue.player.Player;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Represents the start of the next turn.
 * @author steve
 */
public class StartTurnAction extends Action{

    /**
     * Creates a new StartTurnAction.
     * @param player the Player whose turn it is.
     */
    public StartTurnAction(Player player) {
        super(player);
        this.actionType = ActionType.STARTTURN;
    }

    @Override
    public void execute() {
        super.execute(); //To change body of generated methods, choose Tools | Templates.
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AiAdvanced.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (player.isAi()){
            ((AiAdvanced) player).respondToStartTurn();
        }
        //TODO prompt GUI
    }
}

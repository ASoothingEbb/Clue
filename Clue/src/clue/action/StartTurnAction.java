/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.ai.AiAdvanced;
import clue.player.Player;

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
        if (player.isAi()){
            ((AiAdvanced) player).respondToStartTurn();
        }
        //TODO prompt GUI
    }
}

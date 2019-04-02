/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;

/**
 *Represents a Player on the network being removed from the game.
 * @author steve
 */
public class KickAction extends Action{
        public ActionType actionType = ActionType.KICK;

    public KickAction(Player player) {
        super(player);
    }
        
}

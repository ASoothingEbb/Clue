/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;

/**
 *Represents a Player being able to teleport to any tile of the board, cannot teleport to occupied non-room tiles - intrigue card
 * being used.
 * @author mw434
 */
public class TeleportAction extends Action{
    public ActionType actionType = ActionType.TELEPORT;
    
    public TeleportAction(Player p){
        super(p);
    
    
    }
    
}

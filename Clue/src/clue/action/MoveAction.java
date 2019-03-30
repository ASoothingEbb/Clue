/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;
import clue.tile.Tile;

/**
 *
 * @author slb35
 */
public class MoveAction implements Action{
    private Tile s;
    private Tile t;
    public MoveAction(Tile s,Tile t) {
    }
        
        public ActionType actionType = ActionType.MOVE;
        public boolean authenticate(){
            if(s.isAdjacent(t)){
                return true;
            }
            else{
                return false;
            }
        }
}

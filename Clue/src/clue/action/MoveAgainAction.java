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
 * @author Malter
 */
public class MoveAgainAction extends Action{
    MoveAction moveAction;
    public MoveAgainAction(MoveAction moveAction) {
        super(moveAction.player);
        this.actionType = ActionType.MOVEAGAIN;
        this.moveAction = moveAction;
    }
    
    
    /**
     * Executes the MoveAgainAction. result stores whether or not MoveAction is valid
     * destination from Tile s.
     */
    @Override
    public void execute() {
        moveAction.execute();
        
    }
    
    /**
     * gets the destination Tile
     *
     * @return tile tiles
     */
    public Tile getTile() {
        return moveAction.getTile();
    }
    
}

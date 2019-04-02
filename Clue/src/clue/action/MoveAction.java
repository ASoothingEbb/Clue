/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;
import clue.tile.Tile;

/**
 *Represents a player moving from Tile s to Tile t
 * @author slb35
 */
public class MoveAction extends Action {

    private Tile s;
    private Tile t;

    /**
     * Creates a new MoveAction
     * @param s source Tile
     * @param t destination Tile
     * @param player Player to move
     */
    public MoveAction(Tile s, Tile t, Player player) {
        super(player);
    }

    public ActionType actionType = ActionType.MOVE;

    /**
     * Executes the MoveAction. result stores whether or not Tile t is a valid 
     * destination from Tile s.
     */
    @Override
    public void execute() {
        if (s.isAdjacent(t)) {
            result = true;
        } else {
            result = false;
        }
    }

    /**
     * gets the destination Tile
     * @return tile t
     */
    public Tile getTile() {
        return t;
    }
}

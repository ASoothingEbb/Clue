/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;
import clue.tile.Tile;
import java.util.List;
import java.util.Queue;

/**
 * Represents a player moving from Tile s to Tile tiles
 *
 * @author slb35
 */
public class MoveAction extends Action {

    private Tile s;
    private Queue<Tile> tiles;
    private Tile last;

    /**
     * Creates a new MoveAction
     *
     * @param s source Tile
     * @param t destination Tile
     * @param player Player to move
     */
    public MoveAction(Queue<Tile> t, Player player) {
        super(player);
        this.s = player.getPosition();
        this.tiles = t;
    }

    public ActionType actionType = ActionType.MOVE;

    /**
     * Executes the MoveAction. result stores whether or not Tile t is a valid
     * destination from Tile s.
     */
    @Override
    public void execute() {
        Tile t;
        while (!tiles.isEmpty()) {
            t = tiles.poll();
            if (!s.isAdjacent(t)) {
                result = false;
                break;
            } else if (t.room) {
                last = t;
                result = tiles.isEmpty();
                break;
            } else {
                last = t;
            }
        }
    }

    /**
     * gets the destination Tile
     *
     * @return tile tiles
     */
    public Tile getTile() {
        return last;
    }
}

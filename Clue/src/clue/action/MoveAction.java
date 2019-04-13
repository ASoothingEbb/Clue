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

    private final Tile s;
    private final Queue<Tile> tiles;
    private Tile last;

    /**
     * Creates a new MoveAction
     *
     * @param s source Tile
     * @param t destination Tile
     * @param player Player to move
     */
    public MoveAction(Player player, Queue<Tile> t) {
        super(player);
        this.actionType = ActionType.MOVE;
        this.s = player.getPosition();
        this.tiles = t;
    }

    /**
     * Executes the MoveAction. result stores whether or not Tile t is a valid
     * destination from Tile s.
     */
    @Override
    public void execute() {
        //Number of moves is validated in GameController.Move()
        Tile t = null;
        while (!tiles.isEmpty()) {
            t = tiles.poll();
            if (!s.isAdjacent(t)) {
                result = false;
                break;
            } else if (t.isRoom()) {
                last = t;
                result = tiles.isEmpty();
                player.setMoves(0);
                break;
            } else {
                last = t;
                player.setMoves(player.getMoves() - 1);
            }
        }
        if (player.getMoves() == 0) {
            if (t.isFull()) {
                result = false;
            } else {
                result = false;
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

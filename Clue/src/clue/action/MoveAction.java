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
public class MoveAction extends Action {

    private Tile s;
    private Tile t;

    public MoveAction(Tile s, Tile t, Player player) {
        super(player);
    }

    public ActionType actionType = ActionType.MOVE;

    @Override
    public void execute() {
        if (s.isAdjacent(t)) {
            result = true;
        } else {
            result = false;
        }
    }

    public Tile getTile() {
        return t;
    }
}

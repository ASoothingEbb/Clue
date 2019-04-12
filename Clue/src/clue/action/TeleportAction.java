/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;
import clue.card.TeleportIntrigue;
import clue.tile.Tile;
import clue.tile.TileOccupiedException;

/**
 * Represents a Player being able to teleport to any tile of the board, cannot
 * teleport to occupied non-room tiles - intrigue card being used.
 *
 * @author mw434
 */
public class TeleportAction extends Action {

    private final Tile t;

    public TeleportAction(Player p, TeleportIntrigue card, Tile t) {
        super(p, card);
        this.actionType = ActionType.TELEPORT;
        this.t = t;
    }

    @Override
    public void execute() {
        if(!t.isFull()){
        player.setPosition(t);
        player.removeCard(card);
        result = true;
        }
        else{
            result = false;
        }
    }
}

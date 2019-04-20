/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.AiAdvanced;
import clue.player.Player;
import clue.card.TeleportIntrigue;
import clue.client.gameInstance;
import clue.tile.Tile;
import clue.tile.TileOccupiedException;

/**
 * Represents a Player being able to teleport to any tile of the board, cannot
 * teleport to occupied non-room tiles - intrigue card being used.
 *
 * @author mw434
 */
public class TeleportAction extends Action {

    private Tile target;

    /**
     * Creates a TeleportAction
     * 
     * @param p the player to be teleported.
     * @param card  the intrigue card.
     * @param gui a instance of the game.
     */
    public TeleportAction(Player p, TeleportIntrigue card) {
        super(p, card);
        this.actionType = ActionType.TELEPORT;
    }

    /**
     * Executes the TeleportAction.
     */
    @Override
    public void execute() {
        player.removeIntrigue((TeleportIntrigue)card);
        if (player.isAi()){
            ((AiAdvanced) player).respondToTeleport(this);

        }
    }
    
    /**
     * Sets the tile for the player to teleport to.
     * @param t the tile to teleport to
     */
    public void setTarget(Tile t){
        target = t;
    }
    
    /**
     * @return the tile where the player will teleport to.
     */
    public Tile getTarget(){
        return target;
    }
}

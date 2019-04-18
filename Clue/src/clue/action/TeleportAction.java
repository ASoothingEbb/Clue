/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.ai.AiAdvanced;
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

    private gameInstance gui;
    private Tile target;

    public TeleportAction(Player p, TeleportIntrigue card, gameInstance gui) {
        super(p, card);
        this.actionType = ActionType.TELEPORT;
    }

    @Override
    public void execute() {
        player.removeCard(card);
        if (player.isAi()){
            ((AiAdvanced) player).respondToTeleport(this);

        }
        else if (gui != null){
            gui.actionResponse(this);
        }
        else{
            System.out.println("[TeleportAction.execute] no gui found");
        }
    }
    
    public void setTarget(Tile t){
        target = t;
    }
    
    public Tile getTarget(){
        return target;
    }
}

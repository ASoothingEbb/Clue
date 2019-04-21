/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

import clue.action.Action;
import clue.action.ActionType;
import clue.action.AvoidSuggestionAction;
import clue.action.ExtraTurnAction;
import clue.action.ThrowAgainAction;
import clue.action.UnknownActionException;
import clue.card.AvoidSuggestionIntrigue;
import clue.card.IntrigueCard;
import clue.player.Player;
import java.util.List;

/**
 * Represents a Tile with an intrigue card associated with it.
 *
 * @author slb35
 */
public class SpecialTile extends Tile {

    private ActionType action;

    /**
     * Creates a Special Tile 
     * @param x the x coordinate of the tile 
     * @param y the y coordinate of the tiles
     */
    public SpecialTile(int x, int y) {
        super(x,y);
        special = true;
    }

    /**
     * Gets an IntrigueCard from the Game pool.
     *
     * @param player The player to give the intrigue card to
     * @return the card that was added
     */
    public IntrigueCard getIntrigue(Player player) {
        return player.addIntrigue();
    }
    
}

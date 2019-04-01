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
import clue.player.Player;
import java.util.List;

/**
 *
 * @author slb35
 */
public class SpecialTile extends Tile {

    private ActionType action;

    public SpecialTile(ActionType t) {
        action = t;
        special = true;
    }
    

    public Action getSpecial(Player player) throws UnknownActionException {
        switch (action) {
            case AVOIDSUGGESTIONCARD:
                return new AvoidSuggestionAction(player);
            case EXTRATURN:
                return new ExtraTurnAction(player);
            case THROWAGAIN:
                return new ThrowAgainAction(player);
            default:
                throw new UnknownActionException();
        }
    }
}

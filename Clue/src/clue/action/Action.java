/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;

/**
 *
 * @author slb35
 */
public abstract class Action {

    public Player player;
    public ActionType actionType = ActionType.DEFAULT;
    public boolean result;

    public Action(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void execute() {
    }
}

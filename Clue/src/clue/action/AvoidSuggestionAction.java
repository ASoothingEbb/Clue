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
public class AvoidSuggestionAction extends Action{
        public ActionType actionType = ActionType.AVOIDSUGGESTIONCARD;
        public AvoidSuggestionAction(Player player){
            super(player);
        }
}

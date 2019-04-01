/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.Card;
import clue.player.Player;
import java.util.List;

/**
 *
 * @author slb35
 */
public class ShowCardAction extends Action {

    public ActionType actionType = ActionType.SHOWCARD;

    public ShowCardAction(Player player,List<Card> cards) {
        super(player);
    }

    @Override
    public void execute() {
        
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

/**
 *Represents the end of the game turn.
 * @author steve
 */
public class EndTurnAction extends Action{
    ActionType actionType = ActionType.ENDTURN;

    public EndTurnAction() {
        super(null);
    }
}

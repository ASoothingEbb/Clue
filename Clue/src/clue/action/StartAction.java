/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

/**
 *Represents a game instance being started.
 * @author steve
 */
public class StartAction extends Action{
/**
 * Creates a new StartAction.
 */
    public StartAction() {
        super(null);
        this.actionType = ActionType.START;
    }

    /**
     * Executes this action.
     */
    @Override
    public void execute() {
        super.execute(); //To change body of generated methods, choose Tools | Templates.
    }
}

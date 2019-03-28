/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;

/**
 *
 * @author slb35
 */
public class GameController {
    private GameState state;
    public GameController(){
        
    }
    public void performAction(Action action){
        switch(action.actionType){
            default:
                break;
            case DEFAULT:
                break;
            case ACCUSATION:
                break;
            case AVOIDSUGGESTIONCARD:
                break;
            case ENDTURN:
                break;
            case EXTRATURN:
                break;
            case KICK:
                break;
            case MOVE:
                break;
            case SHOWCARD:
                break;
            case START:
                break;
            case SUGGEST:
                break;
            case THROWAGAIN:
                break;
        }
        state.notifyAllObservers();
    }
}

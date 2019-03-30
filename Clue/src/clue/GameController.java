/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.AccusationAction;
import clue.action.Action;
import clue.action.MoveAction;
import clue.card.Card;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;
import java.util.List;

/**
 * action = (AccusationAction) action;
 *
 * @author slb35
 */
public class GameController {

    private GameState state;
    private List<Card> cards;
    private PersonCard person;
    private RoomCard room;
    private WeaponCard weapon;
    private List<Player> players;
    private Player winner;

    public GameController() {

    }

    public void performAction(Action action) {
        switch (action.actionType) {
            default:
                break;
            case DEFAULT:
                break;
            case ACCUSATION:
                if (((AccusationAction) action).authenticate(person, room, weapon)) {
                    winner = state.endGame();
                } else {
                }
                endGame();
                break;
            case AVOIDSUGGESTIONCARD:
                break;
            case ENDTURN:
                state.nextTurn();
                break;
            case EXTRATURN:
                break;
            case KICK:
                break;
            case MOVE:
                if(((MoveAction)action).authenticate()){
                    
                }
                break;
            case SHOWCARD:
                break;
            case START:
                state = new GameState(players);
                break;
            case SUGGEST:
                break;
            case THROWAGAIN:
                break;
        }
        state.setAction(action);
        state.notifyAllObservers();
    }

    private void endGame() {
        if (winner == null) {
        } else {

        }
    }
}

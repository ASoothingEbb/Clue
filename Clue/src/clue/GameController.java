/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.AccusationAction;
import clue.action.Action;
import clue.action.ExtraTurnAction;
import clue.action.MoveAction;
import clue.action.ShowCardAction;
import clue.action.StartAction;
import clue.action.StartTurnAction;
import clue.action.UnknownActionException;
import clue.card.Card;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;
import clue.tile.SpecialTile;
import clue.tile.Tile;
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
    private Player player;
    private boolean working = false;

    public GameController() {

    }

    public synchronized void performAction(Action action) throws UnknownActionException, InterruptedException {
        player = players.get(state.getPlayerTurn());
        working = true;
        action.execute();
        switch (action.actionType) {
            default:
                throw new UnknownActionException();
            case DEFAULT:
                throw new UnknownActionException();
            case ACCUSATION:
                if (action.result) {
                    winner = state.endGame();
                } else {
                }
                endGame();
                break;
            case AVOIDSUGGESTIONCARD:
                
                break;
            case ENDTURN:
                state.nextTurn();
                while (working) {
                    wait();
                }
                performAction(new StartTurnAction(player));
                break;
            case EXTRATURN:
                while (working) {
                    wait();
                }
                performAction(new StartTurnAction(player));
                break;
            case KICK:
                break;
            case MOVE:
                if (action.result) {
                    Tile loc = ((MoveAction) action).getTile();
                    player.setPosition(loc);
                    if (loc.special) {
                        ((SpecialTile) loc).getSpecial(player);
                    }
                }
                break;
            case SHOWCARD:

                break;
            case START:
                state = new GameState(players);
                break;
            case STARTTURN:
                state.nextTurn(player.getId());
                break;
            case SUGGEST:

                break;
            case THROWAGAIN:
                while(working){
                wait();}
                performAction(new StartTurnAction(player));
                break;
        }
        state.setAction(action);
        state.notifyAllObservers();
        working = false;
        notify();
    }

    private void endGame() {
        if (winner == null) {
        } else {

        }
    }
}

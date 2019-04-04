/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.AccuseAction;
import clue.action.Action;
import clue.action.ExtraTurnAction;
import clue.action.MoveAction;
import clue.action.ShowCardAction;
import clue.action.ShowCardsAction;
import clue.action.StartAction;
import clue.action.StartTurnAction;
import clue.action.SuggestAction;
import clue.action.UnknownActionException;
import clue.card.Card;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;
import clue.tile.SpecialTile;
import clue.tile.Tile;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * Keeps track of an instance of a Clue game state.
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
    private SynchronousQueue queue;

    /**
     * Creates a new GameController.
     */
    public GameController() {
        queue = new SynchronousQueue();
    }

    /**
     * Takes an Action from a Player and adds it to the queue of Actions.
     *
     * @param action the Action to be performed
     * @throws UnknownActionException Action type could not be resolved
     * @throws InterruptedException Action was not performed at the correct
     * time.
     */
    public void performAction(Action action) throws UnknownActionException {
        queue.add(action);
        execute();
    }

    /**
     * Executes an action from the queue. Waits for the current action to 
     * complete before executing.
     *
     * @throws UnknownActionException
     * @throws InterruptedException
     */
    private void execute() throws UnknownActionException {
        player = players.get(state.getPlayerTurn());
        Action action = (Action) queue.poll();
        action.execute();
        //Action specific logic
        switch (action.actionType) {
            default:
                throw new UnknownActionException();
            case DEFAULT:
                throw new UnknownActionException();
            case ACCUSATION:
                if (action.result) {
                    winner = state.endGame();
                    endGame();
                } else if(state.playersNumber == 0){
                    state.endGame();
                    endGame();
                }else {
                }
                break;
            case AVOIDSUGGESTIONCARD:

                break;
            case ENDTURN:
                state.nextTurn(state.nextPlayer());
                performAction(new StartTurnAction(player));
                break;
            case EXTRATURN:
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
            case SHOWCARDS:
                
                break;
            case START:
                state = new GameState(players);
                break;
            case STARTTURN:
                state.nextTurn(player.getId());
                break;
            case SUGGEST:
                if (action.result) {
                    performAction(new ShowCardsAction(((SuggestAction) action).show, ((SuggestAction) action).foundCards));
                }
                break;
            case THROWAGAIN:
                performAction(new StartTurnAction(player));
                break;
        }
        //update game state
        state.setAction(action);
        state.notifyAllObservers();
    }
    
    /**
     * Returns the last action executed on the state
     * @return Action
     */
    public Action getLastAction(){
        return state.getAction();
    }

    public HashMap getLocations(){
        HashMap loc = new HashMap();
        for(Player p: players){
            loc.put(p.getId(), p.getPosition());
        }
        return loc;
    }
            
    /**
     * Returns the player whose turn it is
     * @return Current player
     */
    public Player getPlayer(){
        return player;
    }
    /**
     * Terminates the game instance and declares a winner.
     */
    private void endGame() {
        if (winner == null) {
        } else {

        }
    }

    /**
     * Creates a new SuggestAction for a player
     * 
     * @param person the person to be suggested
     * @param room the room to be suggested
     * @param weapon the weapon to be suggested
     * @param player the suggesting player
     * @return new SuggestAction
     */
    public Action suggest(PersonCard person, RoomCard room, WeaponCard weapon, Player player) {
        return new SuggestAction(person, room, weapon, player, state);
    }
}

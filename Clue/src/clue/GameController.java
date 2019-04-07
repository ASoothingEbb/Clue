/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.action.ActionType;
import clue.action.AvoidSuggestionAction;
import clue.action.ExtraTurnAction;
import clue.action.MoveAction;
import clue.action.ShowCardsAction;
import clue.action.StartAction;
import clue.action.StartTurnAction;
import clue.action.SuggestAction;
import clue.action.TeleportAction;
import clue.action.ThrowAgainAction;
import clue.action.UnknownActionException;
import clue.card.AvoidSuggestionIntrigue;
import clue.card.Card;
import clue.card.IntrigueCard;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;
import clue.tile.SpecialTile;
import clue.tile.Tile;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Keeps track of an instance of a Clue game state.
 *
 * @author slb35
 */
public class GameController {

    private GameState state;
    private List<IntrigueCard> cards;
    private PersonCard person;
    private RoomCard room;
    private WeaponCard weapon;
    private List<Player> players;
    private Player winner;
    private Player player;
    private boolean working = false;
    private SynchronousQueue queue;
    private Random random;

    /**
     * Creates a new GameController.
     */
    public GameController(List<Player> players) throws InterruptedException, UnknownActionException {
        queue = new SynchronousQueue(true);
        this.players = players;
        random = new Random(Calendar.getInstance().getTimeInMillis());
        performAction(new StartAction());
    }

    /**
     * Takes an Action from a Player and adds it to the queue of Actions.
     *
     * @param action the Action to be performed
     * @throws UnknownActionException Action type could not be resolved
     * @throws InterruptedException Action was not performed at the correct
     * time.
     */
    public void performAction(Action action) throws UnknownActionException, InterruptedException {
        execute((Action) queue.poll());
        queue.offer(action);
    }

    /**
     * Executes an action from the queue. Waits for the current action to
     * complete before executing.
     *
     * @throws UnknownActionException
     * @throws InterruptedException
     */
    private void execute(Action action) throws UnknownActionException, InterruptedException {
        player = players.get(state.getPlayerTurn());
        action.execute();
        System.out.println(action.actionType + "executing");
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
                } else if (state.playersNumber == 0) {
                    state.endGame();
                    endGame();
                } else {
                }
                break;
            case AVOIDSUGGESTIONCARD:
                action.getPlayer().setActiveSuggestionBlock(true);//player will not be checked in next turns suggestion check
                //TODO: notify player they have a suggestion block
                returnCard((IntrigueCard)action.getPlayer().removeCard(((AvoidSuggestionAction)action).card));
                break;
            case ENDTURN:
                state.nextTurn(state.nextPlayer());
                
                int j = player.getId();
                
                for (int i = 0; i < state.playersNumber; i++){
                    
                    if (j != player.getId()){
                        state.getPlayer(j).setActiveSuggestionBlock(false);//remove any suggestion blocks players may have 
                    }  
                    
                    j = state.getNextPointer(j);
                }
                
                performAction(new StartTurnAction(player));
                break;
            case EXTRATURN:
                performAction(new StartTurnAction(action.getPlayer()));
                break;
            case KICK:
                break;
            case MOVE:
                if (action.result && (state.getAction().actionType == ActionType.STARTTURN || state.getAction().actionType == ActionType.THROWAGAIN)) {
                    Tile loc = ((MoveAction) action).getTile();
                    player.setPosition(loc);
                    if (loc.special) {
                        IntrigueCard card = ((SpecialTile) loc).getIntrigue(player);
                        switch(card.cardType){
                            case AVOIDSUGGESTION:
                                performAction(new AvoidSuggestionAction(player, (AvoidSuggestionIntrigue) card));
                                break;
                            case EXTRATURN:
                                //TODOplayer
                                performAction(new ExtraTurnAction(player));
                                break;
                            case TELEPORT:
                                //TODO
                                performAction(new TeleportAction(player));
                                break;
                            case THROWAGAIN:
                                //TODO
                                performAction(new ThrowAgainAction(player));
                                break;
                        }
                    }
                }
                break;
            case SHOWCARD:
                if (state.getAction().actionType == ActionType.SHOWCARDS) {

                }
                break;
            case SHOWCARDS:
                if (state.getAction().actionType == ActionType.SUGGEST) {

                }
                break;
            case START:
                state = new GameState(players);
                break;
            case STARTTURN:
                if (state.getAction().actionType == ActionType.ENDTURN || state.getAction().actionType == ActionType.EXTRATURN) {
                    state.nextTurn(player.getId());
                }
                break;
            case SUGGEST:
                if (action.result && state.getAction().actionType == ActionType.STARTTURN | state.getAction().actionType == ActionType.MOVE) {
                    performAction(new ShowCardsAction(((SuggestAction) action).show, ((SuggestAction)action).player, ((SuggestAction) action).foundCards));
                }
                break;
            case THROWAGAIN:
                //TODO: tell gui to roll again
                //TODO: allow players to roll again
                break;
            
        }
        //update game state
        state.setAction(action);
        state.notifyAllObservers();
    }

    /**
     * Returns the last action executed on the state
     *
     * @return Action
     */
    public Action getLastAction() {
        return state.getAction();
    }

    public HashMap getLocations() {
        HashMap loc = new HashMap();
        for (Player p : players) {
            loc.put(p.getId(), p.getPosition());
        }
        return loc;
    }

    /**
     * Returns the player whose turn it is
     *
     * @return Current player
     */
    public Player getPlayer() {
        return player;
    }
    
     /**
     * Returns the List of every Player in the game.
     *
     * @return List of all Players.
     */
    public List<Player> getPlayers(){
        return players;
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
     * @param person the person card to be suggested
     * @param room the room card to be suggested
     * @param weapon the weapon card to be suggested
     * @param player the suggesting Player
     * @return new SuggestAction
     */
    public Action suggest(PersonCard person, RoomCard room, WeaponCard weapon, Player player) {
        return new SuggestAction(person, room, weapon, player, state);
    }
    
    public boolean CheckAccuse(PersonCard person,RoomCard room, WeaponCard weapon){
        return person == this.person && room == this.room && weapon == this.weapon;
    }
    
    public IntrigueCard drawCard(){
        int nextCard = random.nextInt(cards.size());
                return cards.remove(nextCard);
    }
    
    private void returnCard(IntrigueCard card){
        cards.add(card);
    }
}

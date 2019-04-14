/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.*;
import clue.ai.AiBasic;
import clue.card.*;
import clue.player.Player;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.SpecialTile;
import clue.tile.Tile;
import clue.tile.Room;
import clue.tile.TileOccupiedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Performs internal game logic for a Clue game instance
 *
 * @author slb35
 */
public final class GameController {

    public class MovementException extends Exception {
    }

    public class TooManyPlayersException extends Exception {
    }

    private GameState state;
    private final BoardMappings bm;
    private List<IntrigueCard> cards;
    private PersonCard person;
    private RoomCard room;
    private WeaponCard weapon;
    private List<Player> players;
    private Player winner;
    private Player player;
    private final boolean working = false;
    private final Random random;
    private List<Action> actionLog;
    private Queue<Action> actions;
    private int turns = 0;

    /**
     * Creates a new GameController.
     *
     * @param human
     * @param ai
     * @param tilePath
     * @param boardHeight
     * @throws java.lang.InterruptedException
     * @throws clue.action.UnknownActionException
     * @throws clue.tile.NoSuchRoomException
     * @throws clue.tile.NoSuchTileException
     * @throws clue.MissingRoomDuringCreationException
     * @throws clue.GameController.TooManyPlayersException thrown when player count exceeds 6 or the number of starting locations
     * @throws clue.tile.TileOccupiedException
     */
    public GameController(int human, int ai, String tilePath, String doorPath) throws InterruptedException, UnknownActionException, NoSuchRoomException, NoSuchTileException, MissingRoomDuringCreationException, TooManyPlayersException, TileOccupiedException {
        //TODO
        this.bm = new BoardMappings(tilePath, doorPath);
        LinkedList<Tile> startingTiles = bm.getStartingTiles();
        List<Player> players = new ArrayList();
        for (int i = 0; i < human; i++) {
            players.add(new Player(i, this));
        }
        for (int i = human; i < human + ai; i++) {
            players.add(new AiBasic(i, this));
        }
        if (players.size() > 6 || players.size() > startingTiles.size()) {
            throw new TooManyPlayersException();
        }
        this.players = players;
        random = new Random(Calendar.getInstance().getTimeInMillis());
        actionLog = new ArrayList();
        state = new GameState(players);
        if (human + ai >= 2) {
            for (Player p : players) {
                if (p.isActive()) {
                    //System.out.println(p+" :"+startingTiles.peek());
                    p.setPosition(startingTiles.poll());
                }
            }
            performAction(new StartAction());
        } else {
            state.endGame();
            endGame();
        }
    }

    /**
     * Executes an action from the queue. Waits for the current action to
     * complete before executing.
     *
     * @param action
     * @throws UnknownActionException
     * @throws InterruptedException
     * @throws clue.tile.TileOccupiedException
     */
    public void performAction(Action action) throws UnknownActionException, InterruptedException, TileOccupiedException {
        Action nextAction = null;
        player = players.get(state.getPlayerTurn());
        action.execute();
        System.out.println(action.actionType + " executing");
        //Action specific lplayersogic
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
                    nextAction = new EndTurnAction(state.getCurrentPlayer());
                }
                actionLog.add(turns, action);
                break;
            case AVOIDSUGGESTIONCARD:
                action.getPlayer().setActiveSuggestionBlock(true);//player will not be checked in next turns suggestion check
                //TODO: notify player they have a suggestion block
                returnCard(((AvoidSuggestionAction) action).card);
                break;
            case ENDTURN:
                player.setMoves(0);
                state.nextTurn(state.nextPlayer());

                int j = player.getId();

                for (int i = 0; i < state.playersNumber; i++) {

                    if (j != player.getId()) {
                        state.getPlayer(j).setActiveSuggestionBlock(false);//remove any suggestion blocks players may have 
                    }

                    j = state.getNextPointer(j);
                }
                moveActionLog();
                turns++;
                nextAction = new StartTurnAction(state.getCurrentPlayer());
                break;
            case EXTRATURN:
                returnCard((IntrigueCard) action.card);
                nextAction = new StartTurnAction(action.getPlayer());
                break;
            case MOVE:
                if (action.result && (state.getAction().actionType == ActionType.STARTTURN || state.getAction().actionType == ActionType.THROWAGAIN || state.getAction().actionType == ActionType.MOVE)) {
                    Tile loc = ((MoveAction) action).getTile();
                    player.getPosition().setOccupied(false);
                    player.setPosition(loc);
                    loc.setOccupied(true);
                    if (loc.special) {
                        getSpecial(loc);
                    }
                }
                break;
            case SHOWCARD:
                if (state.getAction().actionType == ActionType.SHOWCARDS) {
                    //TODO
                }
                break;
            case SHOWCARDS:
                if (state.getAction().actionType == ActionType.SUGGEST) {
                    //TODO
                }
                actionLog.add(turns, action);
                break;
            case START:
                nextAction = new StartTurnAction(player);
                
                //GIVE PLAYERS CARDS
                handOutCards();

                break;
            case STARTTURN:
                if (state.getAction().actionType == ActionType.ENDTURN || state.getAction().actionType == ActionType.EXTRATURN) {
                    state.nextTurn(player.getId());
                }
                break;
            case SUGGEST:
                if (action.result && state.getAction().actionType == ActionType.STARTTURN | state.getAction().actionType == ActionType.MOVE) {
                    nextAction = new ShowCardsAction(((SuggestAction) action).show, ((SuggestAction) action).player, ((SuggestAction) action).foundCards);
                }
                actionLog.add(turns, action);
                break;
            case TELEPORT:
                if (!action.result) {
                    throw new TileOccupiedException();
                }
                
                //TODO update occupied status of tiles
                break;
            case THROWAGAIN:
                //TODO: tell gui to roll again
                //TODO: allow players to roll again
                roll();
                break;
        }
        //update game state
        state.setAction(action);
        state.notifyAllPlayers();
        if (nextAction != null) {
            performAction(nextAction);
        }
    }

    /**
     * Returns the last action executed on the state
     *
     * @return Action
     */
    public Action getLastAction() {
        return state.getAction();
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
     * Returns the player object with the given id.
     *
     * @param id of the player.
     * @return Player with id id. null if invalid ID.
     */
    public Player getPlayer(int id) {
        for (int i = 0; i < players.size(); i++) {
            if (id == players.get(i).getId()) {
                return players.get(i);
            }
        }
        return null;
    }

    /**
     * Returns the List of every Player in the game.
     *
     * @return List of all Players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Terminates the game instance and declares a winner.
     */
    private void endGame() {
        //TODO notify GUI
        if (winner == null) {
        } else {

        }
    }
    
    /**
     * Ends the turn of the current player
     */
    public void endTurn() throws UnknownActionException, InterruptedException, MovementException, TileOccupiedException{
        performAction(new EndTurnAction(player));
    }

    /**
     * rolls the current player's moves
     *
     * @return new movement limit
     */
    public int roll() {
        player.setMoves(random.nextInt(10) + 2);
        return player.getMoves();
    }

    /**
     * Distributes the cards between the active players in the game
     */
    private void handOutCards(){
        int numberOfWeapons = 6;
        int numberOfPersons = 6;
                
        ArrayList<Card> cards = new ArrayList<>();
              
        Room[] rooms = bm.getRooms();
        try {
            for (int i = 0; i < rooms.length; i++){
                cards.add(rooms[i].getCard());
            }
        }
        catch ( NoSuchRoomException ex){
            System.out.println(ex);
        }
        
                
        for (int i = 0; i < numberOfWeapons; i++){
            cards.add(new WeaponCard(i));
        }
        for (int i = 0; i < numberOfPersons; i++){
            cards.add(new PersonCard(i));
        }
                
        Random rand = new Random();
        int randInt = -1;
        int playerIndex = 0;

        while (!cards.isEmpty()){
            if (playerIndex >= players.size()){
                playerIndex = 0;
            }
            else if (players.get(playerIndex).isActive()){//only give cards to active players
                randInt = rand.nextInt(cards.size());//select random index
                players.get(playerIndex).addCard(cards.get(randInt));//give card from cards list at the random index
                cards.remove(randInt);//remove the already given card from cards list
                playerIndex++;
            }
        }
    }
    
    /**
     * Moves the current player in a sequence of moves.
     *
     * @param tiles tiles to move to
     * @throws UnknownActionException
     * @throws InterruptedException
     * @throws clue.GameController.MovementException the movements were invalid
     * @throws clue.tile.TileOccupiedException
     */
    public void move(Queue<Tile> tiles) throws UnknownActionException, InterruptedException, MovementException, TileOccupiedException {
        if (tiles.size() <= player.getMoves()) {
            performAction(new MoveAction(player, tiles));
        } else {
            throw new MovementException();
        }
    }

    public void move(Tile tile) throws UnknownActionException, InterruptedException, MovementException, TileOccupiedException {
        Queue<Tile> list = new LinkedList();
        list.add(tile);
        performAction(new MoveAction(player, list));
    }

    /**
     * Creates a new SuggestAction for a player
     *
     * @param person the person card to be suggested
     * @param room the room card to be suggested
     * @param weapon the weapon card to be suggested
     * @param player the suggesting Player
     * @throws clue.action.UnknownActionException
     * @throws java.lang.InterruptedException
     */
    public void suggest(PersonCard person, RoomCard room, WeaponCard weapon, Player player) throws UnknownActionException, InterruptedException, TileOccupiedException {
        performAction(new SuggestAction(person, room, weapon, player, state));
    }

    /**
     * Shows a card to a suggesting player
     *
     * @param card the card to be shown
     * @throws UnknownActionException
     * @throws InterruptedException
     * @throws clue.tile.TileOccupiedException
     */
    public void showCard(Card card) throws UnknownActionException, InterruptedException, TileOccupiedException {
        performAction(new ShowCardAction(player, card));
    }

    /**
     * makes an accusation. The player is immediately removed from the
     * GameState's active player list.
     *
     * @param person the character to accuse
     * @param room the crime scene to accuse
     * @param weapon the murder weapon to accuse
     * @throws UnknownActionException
     * @throws InterruptedException
     * @throws clue.tile.TileOccupiedException
     */
    public void accuse(PersonCard person, RoomCard room, WeaponCard weapon) throws UnknownActionException, InterruptedException, TileOccupiedException {
        performAction(new AccuseAction(player, person, room, weapon, person == this.person && room == this.room && weapon == this.weapon));
    }

    /**
     * Gets a new intrigue card from the GameController's deck.
     *
     * @return a random intrigue card
     */
    public IntrigueCard drawCard() {
        int nextCard = random.nextInt(cards.size());
        return cards.remove(nextCard);
    }

    /**
     * gets a log of all actions that have happened since the previous player
     * turn and updates the player's pointer
     */
    private void moveActionLog() {
        //TODO
        int pointer = player.getLogPointer();
        while (pointer != turns) {
            actions.offer(actionLog.get(pointer));
            pointer++;
        }
        player.setLogPointer(pointer);
    }

    /**
     * gets the current list of actions that the player should be notified about
     *
     * @return action sublist
     */
    public Queue<Action> getActions() {
        return actions;
    }

    /**
     * Puts an intrigue card back in the game's deck.
     *
     * @param card the card to be returned to the deck.
     */
    private void returnCard(IntrigueCard card) {
        cards.add(card);
    }

    /**
     * Resolves special tile functionality
     *
     * @param loc special tile
     * @throws UnknownActionException
     * @throws InterruptedException
     */
    private void getSpecial(Tile loc) throws UnknownActionException, InterruptedException, TileOccupiedException {
        IntrigueCard card = ((SpecialTile) loc).getIntrigue(player);
        switch (card.cardType) {
            case AVOIDSUGGESTION:
                performAction(new AvoidSuggestionAction(player, (AvoidSuggestionIntrigue) card));
                break;
            case EXTRATURN:
                //TODOplayer
                performAction(new ExtraTurnAction(player, (ExtraTurnIntrigue) card));
                break;
            case TELEPORT:
                //TODO
                //Prompt GUI to choose tile
                //Construct action from result
                //performAction(new TeleportAction(player));
                break;
            case THROWAGAIN:
                //TODO
                performAction(new ThrowAgainAction(player, (ThrowAgainIntrigue) card));
                break;
        }
    }
}

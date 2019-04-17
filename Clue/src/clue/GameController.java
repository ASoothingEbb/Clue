/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.*;
import clue.ai.AiAdvanced;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import clue.client.gameInstance;

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
    private PersonCard murderPerson;
    private RoomCard murderRoom;
    private WeaponCard murderWeapon;
    private List<Player> players;
    private Player winner;
    private Player player;
    private final boolean working = false;
    private final Random random;
    private List<Action> actionLog;
    private Queue<Action> actions;
    private int turns = 0;
    private List<WeaponCard> weaponCards;
    private List<PersonCard> personCards;
    private List<RoomCard> roomCards;
    private gameInstance gui;

    /**
     * Creates a new GameController.
     *
     * @param human
     * @param ai
     * @param tilePath
     * @param doorPath
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
        
        weaponCards = new ArrayList<>();
        personCards = new ArrayList<>();
        roomCards = new ArrayList<>();
        players = new ArrayList();

        for (int i = 0; i < human; i++) {
            players.add(new Player(i, this));
        }
        for (int i = human; i < human + ai; i++) {
            players.add(new AiAdvanced(i, this, getBoardWidth(), getBoardHeight()));
        }
        if (players.size() > 6 || players.size() > startingTiles.size()) {
            throw new TooManyPlayersException();
        }
        if (players.size() < 3){
            
        }
        
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
     * Add gameInstance reference to backend so that it can make GUI calls
     * @param gs 
     */
    public void setGameInstance(gameInstance gui){
        this.gui = gui;
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
        if (player != null){
            System.out.println("[GameController.performAction] player turn before: "+player.getId());
        }
        
        for (Player p : players){
            System.out.println("[GameController.performAction] player order: "+p.getId());
        }
        player = players.get(state.getPlayerTurn());
        System.out.println("[GameController.performAction] player turn after: "+player.getId());
        action.execute();
        System.out.println("[GameController.performAction] "+action.actionType + " executing");
        //Action specific lplayersogic
        switch (action.actionType) {
            default:
                throw new UnknownActionException();
            case DEFAULT:
                throw new UnknownActionException();
            case ACCUSATION:
                System.out.println("    CASE ACCUSATION");
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
                System.out.println("    CASE AVOIDSUGGESTIONCARD");
                //TODO: maybe notify player they have a suggestion block
                returnCard(((AvoidSuggestionAction) action).card);
                break;
            case ENDTURN:
                System.out.println("    CASE ENDTURN");
                player.setMoves(0);
                state.nextTurn(state.nextPlayer());

                int j = player.getId();
                
                for (Player p : players){
                    if (p.isActive() && p.getId() !=j){
                        state.getPlayer(j).removeIntrigueOnce(CardType.AVOIDSUGGESTION);//remove any suggestion blocks players may have 
                        
                    }    
                }
                
                moveActionLog();
                turns++;
                nextAction = new StartTurnAction(state.getCurrentPlayer());
                break;
            case EXTRATURN:
                System.out.println("    CASE EXTRATURN");
                returnCard((IntrigueCard) action.card);
                //does it also need to be physicalled removed from the player?
                nextAction = new StartTurnAction(action.getPlayer());
                break;
            case MOVE:
                System.out.println("    CASE MOVE "+player.getId() + "FROM: "+state.getAction().actionType);
                if (action.result && (state.getAction().actionType == ActionType.STARTTURN || state.getAction().actionType == ActionType.MOVE || state.getAction().actionType == ActionType.THROWAGAIN)) {
                    Tile loc = ((MoveAction) action).getTile();    
                    player.getPosition().setOccupied(false);  
                    player.setPosition(loc); 
                    loc.setOccupied(true);                    
                    if (loc.special) {
                        getSpecial(loc);
                    }
                    System.out.println("playerId: "+player.getId()+", move attempt result: "+action.result);
                }
                
                
                break;
            case SHOWCARD:
                System.out.println("    CASE SHOWCARD");
                if (state.getAction().actionType == ActionType.SHOWCARDS) {
                    
                }
                break;
            case SHOWCARDS:
                System.out.println("    CASE SHOWCARDS");
                if (state.getAction().actionType != ActionType.ACCUSATION) {
                    
                    int id = ((ShowCardsAction)action).getIdOfCardToShow();
                    CardType type = ((ShowCardsAction)action).getCardTypeOfCardToShow();
                    Player personToShow = ((ShowCardsAction)action).getSuggester();
                    Card cardToShow = getCard(id, type);
                    
                   
                    nextAction = new ShowCardAction(personToShow, cardToShow, gui, ((ShowCardsAction)action).getPlayer());
                }
                actionLog.add(action);
                break;
            case START:
                System.out.println("    CASE START "+player.getId());
                nextAction = new StartTurnAction(player);
                
                //GIVE PLAYERS CARDS
                handOutCards();

                break;
            case STARTTURN:
                System.out.println("    CASE STARTTURN "+player.getId() + " FROM: "+state.getAction().actionType);
                if (state.getAction().actionType == ActionType.ENDTURN || state.getAction().actionType == ActionType.EXTRATURN || state.getAction().actionType == ActionType.START) {
                    //System.out.println("b"+player.getId());
                    state.nextTurn(player.getId());
                    //System.out.println("a"+player.getId());
                }
                break;
            case SUGGEST:
                System.out.println("    CASE SUGGEST "+player.getId() + " FROM: "+state.getAction().actionType);
                if (state.getAction().actionType == ActionType.STARTTURN || state.getAction().actionType == ActionType.MOVE || state.getAction().actionType == ActionType.TELEPORT) {
                    if (action.result){
                        nextAction = new ShowCardsAction(((SuggestAction) action).show, ((SuggestAction) action).player, ((SuggestAction) action).foundCards, gui);
                    }
                    else{
                        //TODO
                        //tell GUI that no one had one of those cards
                    }
                }
                actionLog.add(action);
                break;
            case TELEPORT:
                System.out.println("    CASE TELEPORT");

                Tile target = ((TeleportAction) action).getTarget();
                boolean result = false;
                if (!target.isFull()){
                    result = true;
                    player.getPosition().setOccupied(false);  
                    player.setPosition(target); 
                    target.setOccupied(true);                    
                    if (target.special) {
                        getSpecial(target);
                    }
                        
                }
                System.out.println("playerId: "+player.getId()+", [teleport] move attempt result: "+result);
                
                break;
            case THROWAGAIN:
                System.out.println("    CASE THROWAGAIN");
                //TODO: tell gui to roll again
                //TODO: allow players to roll again
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
     * @throws clue.action.UnknownActionException
     * @throws java.lang.InterruptedException
     * @throws clue.GameController.MovementException
     * @throws clue.tile.TileOccupiedException
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
     * Distributes the cards between the active players in the game and sets the murder cards
     */
    private void handOutCards(){
        int numberOfWeapons = 6;
        int numberOfPersons = 6;
                
        ArrayList<Card> cardDeck = new ArrayList<>();
        Random rand = new Random();
        int randInt = -1;
              
        Room[] rooms = bm.getRooms();
        randInt = rand.nextInt(rooms.length);
        try {
            for (int i = 0; i < rooms.length; i++){
                roomCards.add(rooms[i].getCard());
                if (i == randInt){
                    murderRoom = rooms[i].getCard();
                }
                else{
                    cardDeck.add(rooms[i].getCard());
                }
                
            }
        }
        catch ( NoSuchRoomException ex){
            System.out.println(ex);
        }
        
        randInt = rand.nextInt(numberOfWeapons);       
        WeaponCard weaponCard;
        for (int i = 0; i < numberOfWeapons; i++){
            weaponCard = new WeaponCard(i);
            weaponCards.add(weaponCard);
            if (i == randInt){
                murderWeapon = weaponCard;
            }
            else{
                cardDeck.add(weaponCard);
            }
            
        }
        randInt = rand.nextInt(numberOfWeapons);   
        
        PersonCard personCard;
        for (int i = 0; i < numberOfPersons; i++){
            personCard = new PersonCard(i);
            personCards.add(personCard);
            if (i == randInt){
                murderPerson = personCard;
            }
            else{
                cardDeck.add(personCard);
            }
            
        }
                
        
        int playerIndex = 0;
        while (!cardDeck.isEmpty()){
            if (playerIndex >= players.size()){
                playerIndex = 0;
            }
            else if (players.get(playerIndex).isActive()){//only give cards to active players
                randInt = rand.nextInt(cardDeck.size());//select random index
                players.get(playerIndex).addCard(cardDeck.get(randInt));//give card from cards list at the random index
                cardDeck.remove(randInt);//remove the already given card from cards list
                playerIndex++;
            }
        }
    }
    
    /**
     * Attempts to move the player to the target tile
     *
     * @param tile tile to move to
     * @return 
     * @throws UnknownActionException
     * @throws InterruptedException
     * @throws clue.GameController.MovementException the movements were invalid
     * @throws clue.tile.TileOccupiedException
     */
    public boolean move(Tile tile) throws UnknownActionException, InterruptedException, MovementException, TileOccupiedException {
        
        MoveAction moveAction = new MoveAction(player, tile, bm.getBoardWidth(), bm.getBoardHeight());
        
        System.out.println("[GameControler.move] before playerId = "+player.getId());
        performAction(moveAction);
        System.out.println("[GameControler.move] after playerId = "+player.getId());
        return moveAction.result;
        
    }
        /**
     * Try to move current player to tile at x,y
     * @param x
     * @param y
     * @return true if move was successful, false otherwise
     * @throws NoSuchRoomException
     * @throws UnknownActionException
     * @throws InterruptedException
     * @throws clue.GameController.MovementException
     * @throws TileOccupiedException 
     */
    public boolean move(int x, int y) throws NoSuchRoomException, UnknownActionException, InterruptedException, MovementException, TileOccupiedException{
        Tile target = getTile(x,y);
        System.out.println("playerId = "+player.getId()+" move was called to go to tile: "+target);
        return move(getTile(x,y));
    
    }

    /**
     * Creates and performs a new SuggestAction for a player
     *
     * @param person the murderPerson card to be suggested
     * @param room the murderRoom card to be suggested
     * @param weapon the murderWeapon card to be suggested
     * @param suggestee the player who is making the suggestion
     * @return the constructed suggestion action
     * @throws clue.action.UnknownActionException
     * @throws java.lang.InterruptedException
     * @throws clue.tile.TileOccupiedException
     */
    public SuggestAction suggest(PersonCard person, RoomCard room, WeaponCard weapon, Player suggestee) throws UnknownActionException, InterruptedException, TileOccupiedException {
        SuggestAction suggestAction = new SuggestAction(person, room, weapon, suggestee, players);
        performAction(suggestAction);
        return suggestAction;
    }

    
    
    /**
     * Creates and performs a new SuggestAction for a player
     * @param personId the id of the person being suggested
     * @param weaponId the id of the weapon being suggested
     * 
     */
    public void suggest(int personId, int weaponId){
        System.out.println("[GameController.suggest]");
        PersonCard person = getPersonCard(personId);
        RoomCard room = null;
        WeaponCard weapon = getWeaponCard(weaponId);
        
        System.out.println("[GameController.suggest] person: "+person);        
        System.out.println("[GameController.suggest] weapon: "+weapon);
        if (player.getPosition().isRoom()){
            try {
                room = ((Room)player.getPosition()).getCard();
            } catch (NoSuchRoomException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("[GameController.suggest] room: "+room);
        
        if (person == null || room == null || weapon == null){
            System.err.println("unable to find 3 cards");
            
        }
        try {
            suggest(person, room, weapon, player);
        } catch (UnknownActionException | InterruptedException | TileOccupiedException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    
    
    /**
     * Shows a card to a suggesting player
     *
     * @param card the card to be shown
     * @throws UnknownActionException
     * @throws InterruptedException
     * @throws clue.tile.TileOccupiedException
     */
    //public void showCard(Card card) throws UnknownActionException, InterruptedException, TileOccupiedException {
        //performAction(new ShowCardAction(player, card));
    //}

    /**
     * Makes an accusation. The player is immediately removed from the
     * GameState's active player list.
     *
     * @param person the character to accuse
     * @param room the crime scene to accuse
     * @param weapon the murder murderWeapon to accuse
     * @return nothing if accusation was incorrect, if correct : list of Integers representing the ids of: the winner of the game, the murderPerson, murderRoom, murderWeapon
     * @throws UnknownActionException
     * @throws InterruptedException
     * @throws clue.tile.TileOccupiedException
     * 
     */
    public AccuseAction accuse(PersonCard person, RoomCard room, WeaponCard weapon) throws UnknownActionException, InterruptedException, TileOccupiedException {
        
        AccuseAction accuseAction = new AccuseAction(player, person, room, weapon, person == this.murderPerson && room == this.murderRoom && weapon == this.murderWeapon);
        
        performAction(accuseAction);
        return accuseAction;
            
    }
    
    /**
     * Makes an accusation. The player is immediately removed from the
     * GameState's active player list.
     * 
     * @param personId the character to accuse
     * @param weaponId the murder murderWeapon to accuse
     * 
    
     */
    public void accuse(int personId, int weaponId){
        PersonCard person = getPersonCard(personId);
        RoomCard room = null;
        WeaponCard weapon = getWeaponCard(weaponId);
        
        
        if (player.getPosition().isRoom()){
            try {
                room = ((Room)player.getPosition()).getCard();
            } catch (NoSuchRoomException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (person == null || room == null || weapon == null){
            System.err.println("unable to find 3 cards");
        }
        try {
            accuse(person, room, weapon);
        } catch (UnknownActionException | InterruptedException | TileOccupiedException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return null;
       
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
        while (pointer != actionLog.size()) {
            System.out.println("[GameController.moveActionLog] pointer: "+pointer +" turns: "+turns);
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
                //handled by suggestion
                break;
            case EXTRATURN:
                //TODOplayer
                performAction(new ExtraTurnAction(player, (ExtraTurnIntrigue) card));
                break;
            case TELEPORT:
                performAction(new TeleportAction(player, (TeleportIntrigue) card, gui));
                break;
            case THROWAGAIN:
                performAction(new ThrowAgainAction(player, (ThrowAgainIntrigue) card, gui));
                break;
        }
    }
    
    /**
     * Gets the 3 murder cards
     * @return the 3 murder cards
     */
    public ArrayList<Card> getMurderCards(){
        ArrayList<Card> result = new ArrayList<>();
        result.add(murderPerson);
        result.add(murderRoom);
        result.add(murderWeapon);
       return result;
    }
    
    /**
     * Gets the tile given x,y coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the tile at x y
     * @throws clue.tile.NoSuchRoomException
     */
    public Tile getTile(int x, int y) throws NoSuchRoomException{
        return bm.getTile(x,y);
    
    }
    
    public int getBoardWidth() {
        return bm.getBoardWidth();
    }
    
    public int getBoardHeight() {
        return bm.getBoardHeight();
    }
    
    /**
     * Gets all the (x,y) coordinates of room tiles which the GUI should drawn doors at
     * @return the list of (x,y) coordinates
     */
    public ArrayList<int[]> getDoorLocations(){
        ArrayList<int[]> doorLocations = new ArrayList<>();
        for (Room r : bm.getRooms()){
            doorLocations.addAll(r.getDoorLocations());
        }
        return doorLocations;
        
    }
    
    public PersonCard getPersonCard(int i){
        PersonCard result = null;
        for (PersonCard c : personCards){
            if (c.getId() == i){
                result = c;
                break;
            }
        }
        return result;
    }
    
    public WeaponCard getWeaponCard(int i){
        WeaponCard result = null;
        for (WeaponCard c : weaponCards){
            if (c.getId() == i){
                result = c;
                break;
            }
        }
        return result;
    }
    
    public RoomCard getRoomCard(int i){
        RoomCard result = null;
        for (RoomCard c : roomCards){
            if (c.getId() == i){
                result = c;
                break;
            }
        }
        return result;
    }
    
    public Card getCard(int i, CardType type){
        if (null != type)switch (type) {
            case ROOM:
                return getRoomCard(i);
                
            case PERSON:
                return getPersonCard(i);
                
            case WEAPON:
                return getWeaponCard(i);
                
            default:
                break;
        }
        return null;
    }
    
    
}

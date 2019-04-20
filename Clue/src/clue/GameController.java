/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.*;
import clue.player.AiAdvanced;
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
    private Player winner;//says it isnt used but it is
    private Player player;
    private final Random random;
    private List<Action> actionLog;
    private Queue<Action> actions;
    private List<WeaponCard> weaponCards;
    private List<PersonCard> personCards;
    private List<RoomCard> roomCards;
    private gameInstance gui;
    //private LinkedList<ShowCardsAction> queuedGuiActions;

    /**
     * Creates a new GameController which provides the backend logic and calls
     *
     * @param human the number of human players
     * @param ai the number of ai players
     * @param tilePath the directory of the csv file used to make the tiles
     * @param doorPath the directory of the csv file used to make the doors
     * @throws clue.tile.NoSuchRoomException thrown when a door in doorPath csv points to a room that was not found
     * @throws clue.tile.NoSuchTileException thrown when a door in doorPath csv points to a tile that was not found
     * @throws clue.MissingRoomDuringCreationException thrown when the tilePath csv is missing a room, if the max room id is N, you must have 1-N in the csv
     * @throws clue.GameController.TooManyPlayersException thrown when player count exceeds 6 or the number of starting locations
     * @throws clue.NotEnoughPlayersException thrown when game is created with less then 2 players
     */
    public GameController(int human, int ai, String tilePath, String doorPath) throws NoSuchRoomException, NoSuchTileException, MissingRoomDuringCreationException, TooManyPlayersException, NotEnoughPlayersException {
        //TODO
        bm = new BoardMappings(tilePath, doorPath);
        LinkedList<Tile> startingTiles = bm.getStartingTiles();
        
        gui = null;
        winner = null;
        
        weaponCards = new ArrayList<>();
        personCards = new ArrayList<>();
        roomCards = new ArrayList<>();
        players = new ArrayList();
        actions = new LinkedList<>();
        //queuedGuiActions = new LinkedList<>();

        //initalise all the players
        for (int i = 0; i < human; i++) {
            players.add(new Player(i, this));
        }
        for (int i = human; i < human + ai; i++) {
            players.add(new AiAdvanced(i, this, getBoardWidth(), getBoardHeight()));
        }
        if (players.size() > 6 || players.size() > startingTiles.size()) {
            throw new TooManyPlayersException();
        }
        
        
        random = new Random(Calendar.getInstance().getTimeInMillis());
        actionLog = new ArrayList();
        state = new GameState(players);
        if (human+ai > startingTiles.size()){
            throw new NoSuchTileException("bad tiles map, not enough starting location for given players");
        }
        //assign players starting positions
        if (human + ai >= 2) {
            for (Player p : players) {
                if (p.isActive()) {
                    p.setPosition(startingTiles.poll());
                }
                System.out.println(p);
            }
            try {
                performAction(new StartAction());
            } catch (UnknownActionException | TileOccupiedException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            endGame();
            throw new NotEnoughPlayersException();
        }
        Player nonActive;
        
        while (players.size() < 6){
            nonActive = new Player(players.size(), this);
            nonActive.removeFromPlay();
            nonActive.setPosition(startingTiles.poll());
            players.add(nonActive);
            
            
            
        }
        
        //assign weapon tokens starting locations
        int roomIdToBePlacedIn = 0;
        int numRooms = bm.getRooms().length;
        for (WeaponCard c : weaponCards){
            c.setPosition(bm.getRoom(roomIdToBePlacedIn));
            roomIdToBePlacedIn++;
            if (roomIdToBePlacedIn >= numRooms){
                roomIdToBePlacedIn = 0;
            }
        
        }
    }

    /** 
     * Add gameInstance reference to backend so that it can make GUI calls
     * @param gui the GUI to make the call to
     */
    public void setGameInstance(gameInstance gui){
        this.gui = gui;
    }
    
    /**
     * Executes a given action.
     *
     * @param action the action to be executed
     * @throws UnknownActionException thrown when it was given an action that it didn't know how to handle
     * @throws clue.tile.TileOccupiedException thrown when a move attempt was unsuccessful because the target tile was full
     */
    public void performAction(Action action) throws UnknownActionException, TileOccupiedException {
        
        if (state.isRunning()){//only execute the action if game is running
            Action nextAction = null;

            player = players.get(state.getPlayerTurn());

            System.out.println("[GameController.performAction] ----"+action.getActionType() + " executing---- player turn: "+player.getId());
            action.execute();//action.execute() handles a lot of the logic behind execution of an action
            //Action specific lplayersogic
            switch (action.getActionType()) {
                default:
                    throw new UnknownActionException();
                case DEFAULT:
                    throw new UnknownActionException();
                case ACCUSATION:
                    System.out.println("    CASE ACCUSATION");
                    if (action.result) {
                        //endGame(player);
                    } else if (!state.hasActive()) {
                        endGame();
                    } else {
                        //nextAction = new EndTurnAction(state.getCurrentPlayer());//players now manually call end turn
                    }
                    actionLog.add(action);
                    break;
                case AVOIDSUGGESTIONCARD:
                    System.out.println("    CASE AVOIDSUGGESTIONCARD");
                    returnCard(((AvoidSuggestionAction) action).card);
                    break;
                case ENDTURN:
                    System.out.println("    CASE ENDTURN");
                    player.setMoves(0);


                    int j = player.getId();

                    for (Player p : players){
                        if (p.isActive() && p.getId() !=j){
                            state.getPlayer(j).removeIntrigueOnce(CardType.AVOIDSUGGESTION);//remove any suggestion blocks players may have 

                        }    
                    }

                    moveActionLog();


                    if (state.hasActive()){
                        int old = player.getId();
                        state.nextTurn(state.nextPlayer());
                        System.out.println("[GameController.performAction] case end turn transitioning to next player turn: "+old+"->"+player.getId());
                        nextAction = new StartTurnAction(state.getCurrentPlayer());    
                    }
                    else{
                        System.out.println("[GameController.performAction] case end turn no more active players");
                        endGame();
                    }

                    break;
                case MOVE:
                    System.out.println("    CASE MOVE "+player.getId() + "FROM: "+state.getAction().getActionType());
                    if (action.result && (state.getAction().getActionType() == ActionType.STARTTURN || state.getAction().getActionType() == ActionType.MOVE || state.getAction().getActionType() == ActionType.THROWAGAIN || state.getAction().getActionType() == ActionType.ENDTURN || state.getAction().getActionType() == ActionType.START)) {
                        Tile loc = ((MoveAction) action).getTile();    
                        //player.getPosition().setOccupied(false);  
                        player.setPosition(loc); 
                        //loc.setOccupied(true);                    
                        if (loc.special && player.getMoves() == 0) {
                            getSpecial(loc);
                        }
                        System.out.println("playerId: "+player.getId()+", move attempt result: "+action.result);    
                    }
                    else{
                        System.out.println("bad move call------------");
                    }



                    break;
                case SHOWCARD:
                    System.out.println("    CASE SHOWCARD");
                    if(gui != null && !(player instanceof AiAdvanced)){
                        gui.actionResponse(action);
                    }
                    if (state.getAction().getActionType() == ActionType.SHOWCARDS) {

                    }
                    actionLog.add(action);
                    break;
                case SHOWCARDS:
                    System.out.println("    CASE SHOWCARDS");
                    if(gui != null && !(player instanceof AiAdvanced)){
                        gui.actionResponse(action);
                    }
                    else{
                    replyToShowCards((ShowCardsAction)action);}
                    if (state.getAction().getActionType() != ActionType.ACCUSATION) {

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
                    System.out.println("    CASE STARTTURN "+player.getId() + " FROM: "+state.getAction().getActionType());
                    if (state.getAction().getActionType() == ActionType.ENDTURN || state.getAction().getActionType() == ActionType.EXTRATURN || state.getAction().getActionType() == ActionType.START&&state.isRunning()) {
                        //System.out.println("b"+player.getId());
                        //state.nextTurn(player.getId());
                        //System.out.println("a"+player.getId());
                        moveActionLog();
                        LinkedList<Action> actionsToNotify = getActions();


                        if (gui != null && !(player instanceof AiAdvanced)){
                            System.out.println("--------------------------prompting gui for player+"+player.getId());
                            gui.newHumanPlayerTurn(actionsToNotify);

                        }
                        else{
                            System.out.println("[GameController.performAction] null gui -> gui.newHumanPlayerTurn(player, actionsToNotify)");
                        }
                    }
                    break;
                case SUGGEST:
                    System.out.println("    CASE SUGGEST "+player.getId() + " FROM: "+state.getAction().getActionType());
                    if (!(state.getAction().getActionType() == ActionType.SUGGEST || state.getAction().getActionType() == ActionType.ACCUSATION)) {
                        if (action.result){

                            try {
                                System.out.println("[GameController.performAction] pulling player original position: "+players.get((((SuggestAction) action).getPersonCard().getId())).getPosition());
                                players.get((((SuggestAction) action).getPersonCard().getId())).setPosition(bm.getRoom(((SuggestAction) action).getRoomCard().getId()));//move the person being suggested into the room of the suggestion
                                System.out.println("[GameController.performAction] pulling player new position: "+players.get((((SuggestAction) action).getPersonCard().getId())).getPosition());
  
                                ((SuggestAction) action).getWeaponCard().setPosition(bm.getRoom(((SuggestAction) action).getRoomCard().getId()));//move the weapon token for the weaponCard to the room of the suggestion

                                
                            } catch (NoSuchRoomException ex) {
                                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            nextAction = new ShowCardsAction(((SuggestAction) action).getShower(), ((SuggestAction) action).getPlayer(), ((SuggestAction) action).getFoundCards());

                            
                            if (player instanceof AiAdvanced){
                                //queuedGuiActions.add((ShowCardsAction)nextAction);
                                //gui.switchToUi();
                                gui.aiShowCardsRequests();
                            
                                
                                //nextAction = null;
                            }
                            
                        }
                        else {
                            if (gui != null && (player instanceof AiAdvanced)){
                                gui.notifyUser("No other player had to show a card due to your suggestion.");
                            }
                            else{
                                System.out.println("[GameController.performAction] null gui -> gui.notifyUser(No other player had to show a card due to your suggestion.");
                            }

                        }
                    }
                    actionLog.add(action);
                    break;
                case TELEPORT:
                    System.out.println("    CASE TELEPORT");
                    if(gui != null && !(player instanceof AiAdvanced)){
                        gui.actionResponse(action);
                    }
                    returnCard((IntrigueCard)((TeleportAction) action).getCard());

                    Tile target = ((TeleportAction) action).getTarget();
                    boolean result = false;
                    if (!target.isFull()){
                        result = true;
                        //player.getPosition().setOccupied(false);  
                        player.setPosition(target); 
                        //target.setOccupied(true);                    
                        if (target.special) {
                            getSpecial(target);
                        }

                    }
                    System.out.println("playerId: "+player.getId()+", [teleport] move attempt result: "+result);

                    break;
                case THROWAGAIN:
                    System.out.println("    CASE THROWAGAIN");
                    if(gui != null && !(player instanceof AiAdvanced)){
                        gui.actionResponse(action);
                    }
                    returnCard((IntrigueCard)((ThrowAgainAction) action).getCard());

                    break;
                case EXTRATURN:
                    System.out.println("    CASE EXTRATURN");
                    returnCard((IntrigueCard) action.getCard());
                    nextAction = new StartTurnAction(action.getPlayer());
                    break;
            }
            //update game state
            state.setAction(action);
            //state.notifyAllPlayers();
            if (nextAction != null) {
                performAction(nextAction);
            }
        }
        else{
            endGame();//tell the gui that the game is over
        }   
        
    }

    /**
     * Returns the last action executed on the state
     *
     * @return the last action
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
     * Called by GUI when the player is on an intrigue tile and they wish to end there movement turn to receive an intrigue card.
     */
    public void endMovementTurn(){
        if (player.getPosition().isSpecial()){
            CardType type = getSpecial(player.getPosition()).getCardType();
            gui.notifyUser("You received an intrigue card: "+type.toString());
        }    
    }
    

    /**
     * Returns the player object with the given id.
     *
     * @param id of the player.
     * @return Player with the same id as the parameter id. null if invalid id
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
     * Terminates the game instance , tells the gui the game is over, without a winner
     */
    private void endGame() {
        state.endGame();
        if (gui!=null){
            gui.gameOver();
        } 
    }
    
    /**
     * Ends the turn of the current player
     */
    public void endTurn() {
        try {
            performAction(new EndTurnAction(player));
        } catch (UnknownActionException | TileOccupiedException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * rolls the current player's moves
     *
     * @return new movement limit
     */
    public int roll() {
        int roll = random.nextInt(10) + 2;
        player.setMoves(roll);
        System.out.println("Player id:" + player.getId() + " rolls a "+ roll);
        return player.getMoves();
    }

    /**
     * Distributes the cards between the active players in the game and sets the murder cards
     */
    private void handOutCards(){
        int numberOfWeapons = 6;
        int numberOfPersons = 6;
                
        ArrayList<Card> cardDeck = new ArrayList<>();
        Random rand = new Random(Calendar.getInstance().getTimeInMillis());
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
        randInt = rand.nextInt(numberOfPersons);   
        
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
     * @param target the target tile to go to
     * @return true if move was successful, false otherwise
     * @throws clue.tile.TileOccupiedException thrown when the target tile of a move is occupied
     */
    public boolean move(Tile target) throws TileOccupiedException {
        
        MoveAction moveAction = new MoveAction(player, target, bm.getBoardWidth(), bm.getBoardHeight());
        
        System.out.println("[GameControler.move] before playerId = "+player.getId());
        try {
            performAction(moveAction);
        } catch (UnknownActionException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("[GameControler.move] after playerId = "+player.getId());
        return moveAction.result;
        
    }
    
    /**
     * Try to move current player to tile at x,y
     * @param x the x coordinate of the target tile
     * @param y the y coordinate of the target tile
     * @return true if move was successful, false otherwise
     * @throws NoSuchRoomException thrown when target x = -1 and y = room id but room with that room id wasnet found
     * @throws TileOccupiedException when the target tile of a move is occupied
     */
    public boolean move(int x, int y) throws NoSuchRoomException, TileOccupiedException{
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
     */
    public SuggestAction suggest(PersonCard person, RoomCard room, WeaponCard weapon, Player suggestee) {
        SuggestAction suggestAction = new SuggestAction(person, room, weapon, suggestee, players);
        try {
            performAction(suggestAction);
        } catch (TileOccupiedException | UnknownActionException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        suggest(person, room, weapon, player);

    }    
    
    


    /**
     * Makes an accusation. The player is immediately removed from the
     * GameState's active player list.
     *
     * @param person the character to accuse
     * @param room the crime scene to accuse
     * @param weapon the murder murderWeapon to accuse
     * @return the constructed (and executed) AccuseAction
     * 
     */
    public AccuseAction accuse(PersonCard person, RoomCard room, WeaponCard weapon) {
        
        AccuseAction accuseAction = new AccuseAction(player, person, room, weapon, murderPerson, murderRoom, murderWeapon, gui);
        
        try {
            performAction(accuseAction);
        } catch (UnknownActionException | TileOccupiedException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            room = ((Room)player.getPosition()).getCard();
        } catch (NoSuchRoomException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        WeaponCard weapon = getWeaponCard(weaponId);
  
        
        if (person == null || room == null || weapon == null){
            System.err.println("unable to find 3 cards");
        }

        accuse(person, room, weapon);           
       
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
     * Gets a log of all actions that have happened since the previous player
     * turn and updates the player's pointer
     */
    private void moveActionLog() {
        //TODO //TODO is the previous todo allready implemented?
        int pointer = player.getLogPointer();
        actions = new LinkedList<>();
        while (pointer != actionLog.size()) {
            System.out.println("[GameController.moveActionLog] pointer: "+pointer);
            actions.offer(actionLog.get(pointer));
            pointer++;
        }
        player.setLogPointer(pointer);
    }

    /**
     * Gets the current list of actions that the player should be notified about
     *
     * @return action sublist
     */
    public LinkedList<Action> getActions() {
        return (LinkedList<Action>) actions;
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
     * @return returns the picked Intrigue card
     * 
     */
    private Card getSpecial(Tile loc) {
        IntrigueCard card = ((SpecialTile) loc).getIntrigue(player);
        try{
            switch (card.getCardType()) {
                case AVOIDSUGGESTION:
                    //handled by suggestion
                    break;
                case EXTRATURN:
                    //TODOplayer
                    performAction(new ExtraTurnAction(player, (ExtraTurnIntrigue) card));
                    break;
                case TELEPORT:
                    performAction(new TeleportAction(player, (TeleportIntrigue) card));
                    break;
                case THROWAGAIN:
                    performAction(new ThrowAgainAction(player, (ThrowAgainIntrigue) card));
                    break;
            }
        } catch (UnknownActionException | TileOccupiedException ex){
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return card;
        
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
     * @throws clue.tile.NoSuchRoomException thrown when x=-1 and y=roomid but the roomid was not paired to a valid room
     */
    public Tile getTile(int x, int y) throws NoSuchRoomException{
        return bm.getTile(x,y);
    
    }
    
    /**
     * Gets the board width
     * @return the board width
     */
    public int getBoardWidth() {
        return bm.getBoardWidth();
    }
    
    /**
     * Gets the board height
     * @return the board height
     */
    public int getBoardHeight() {
        return bm.getBoardHeight();
    }
    
    /**
     * Gets all the (x,y) coordinates of room tiles which the GUI should draw doors at
     * @return the list of (x,y) coordinates
     */
    public ArrayList<int[]> getDoorLocations(){
        ArrayList<int[]> doorLocations = new ArrayList<>();
        for (Room r : bm.getRooms()){
            doorLocations.addAll(r.getDoorLocations());
        }
        return doorLocations;
        
    }
    
    /**
     * Gets a person card with a specific id
     * @param id the id of the person card to get
     * @return the person card found
     */
    public PersonCard getPersonCard(int id){
        PersonCard result = null;
        for (PersonCard c : personCards){
            if (c.getId() == id){
                result = c;
                break;
            }
        }
        return result;
    }
    
    /**
     * Gets a weapon card with a specific id
     * @param id the id of the weapon card to get
     * @return the weapon card found
     */
    public WeaponCard getWeaponCard(int id){
        WeaponCard result = null;
        for (WeaponCard c : weaponCards){
            if (c.getId() == id){
                result = c;
                break;
            }
        }
        return result;
    }
    
    /**
     * Gets a room card with a specific id
     * @param id the id of the room card to get
     * @return the room card found
     */
    public RoomCard getRoomCard(int id){
        RoomCard result = null;
        for (RoomCard c : roomCards){
            if (c.getId() == id){
                result = c;
                break;
            }
        }
        return result;
    }
    
    /**
     * Gets a card with the specified id and card type
     * @param id the id of the card to fetch
     * @param type the card type of the card to fetch
     * @return the fetched card
     */
    public Card getCard(int id, CardType type){
        if (null != type)switch (type) {
            case ROOM:
                return getRoomCard(id);
                
            case PERSON:
                return getPersonCard(id);
                
            case WEAPON:
                return getWeaponCard(id);
                
            default:
                break;
        }
        return null;
    }
    
    
    /**
     * Is called by gui/ai player when they respond to a show cards action
     * @param action the action the gui is responding to, the action also contains the gui response (formulated from the player decision)
     */
    public void replyToShowCards (ShowCardsAction action){
        System.out.println("[GameController.showCard]");
        int id = action.getIdOfCardToShow();
        CardType type = action.getCardTypeOfCardToShow();
        Player personToShow = action.getSuggester();
        Card cardToShow = getCard(id, type);
                  
                   
        try {
            performAction(new ShowCardAction(personToShow, cardToShow, ((ShowCardsAction)action).getPlayer()));
        } catch (UnknownActionException  | TileOccupiedException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets the list of weapon cards
     * @return List of WeaponCard
     */
    public List<WeaponCard> getWeaponCards() {
        return weaponCards;
    }
    
}

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
 * 
 */
public final class GameController {


    public class TooManyPlayersException extends Exception {
    }

    private GameState state;
    private final BoardMappings bm;
    private List<IntrigueCard> intrigueCards;
    private PersonCard murderPerson;
    private RoomCard murderRoom;
    private WeaponCard murderWeapon;
    private List<Player> players;
    private int winner;
    private Player player;
    private final Random random;
    private List<Action> actionLog;
    private Queue<Action> actions;
    private List<WeaponCard> weaponCards;
    private List<PersonCard> personCards;
    private List<RoomCard> roomCards;
    private gameInstance gui;

    /**
     * Creates a new GameController which provides the backend logic and calls used by players to participate in the game
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
        winner = -1;
        
        weaponCards = new ArrayList<>();
        personCards = new ArrayList<>();
        roomCards = new ArrayList<>();
        players = new ArrayList();
        actions = new LinkedList<>();

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
        
        Player nonActive;
        while (players.size() < 6){
            System.out.println(players.size());
            nonActive = new Player(players.size(), this);
            nonActive.removeFromPlay();
            players.add(nonActive); 
        }
        
        //assign players starting positions
        if (human + ai >= 2) {
            for (Player p : players) {
                p.setPosition(startingTiles.poll());

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
     * @param gui the GUI to make the calls to for human players
     */
    public void setGameInstance(gameInstance gui){
        this.gui = gui;
    }
    
    /**
     * Performs a given action, constructs and performs the next action if the given action produces another action
     *
     * @param action the action to be executed
     * @throws UnknownActionException thrown when it was given an action that it didn't know how to handle
     * @throws clue.tile.TileOccupiedException thrown when a move attempt was unsuccessful because the target tile was full
     */
    public void performAction(Action action) throws UnknownActionException, TileOccupiedException {
        
        if (state.isRunning()){//only execute the action if game is running
            Action nextAction = null;

            player = players.get(state.getPlayerTurn());//get the current player whose turn it is

            System.out.println("[GameController.performAction] ----"+action.getActionType() + " executing------"
                    + "---------------------------------------------------------- player turn: "+player.getId());
            action.execute();//action.execute() handles a lot of the logic behind execution of an action
            switch (action.getActionType()) {
                default:
                    throw new UnknownActionException();
                case DEFAULT:
                    throw new UnknownActionException();
                case ACCUSATION:
                    System.out.println("    CASE ACCUSATION");
                    if(!(player instanceof AiAdvanced)){
                        gui.actionResponse(action);
                    }
                    if (action.result) {
                        //endGame(player);
                        if (winner == -1){
                            winner = action.getPlayer().getId();
                            System.out.println(action.getPlayer().getId());
                        }
                        
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
                    nextAction = performEndTurnAction((EndTurnAction)action);  
                    break;
                case MOVE:
                    System.out.println("    CASE MOVE "+player.getId() + "FROM: "+state.getLastAction().getActionType());
                    
                    if (player.hasIntrigue(CardType.TELEPORT)){
                        if (attemptToTeleport(((MoveAction) action).getTile())){//if teleport was successful
                            System.out.println("Teleport successfull");
                            action.result = true;
                            IntrigueCard card = player.getIntrigue(CardType.TELEPORT);
                            player.removeIntrigue(card);//remove intrigue from player
                            returnCard(card);//return teleport intrigue to intrigue deck
                        }
                        else{
                            System.out.println("Teleport unsucessful");
                        }
                        
                    }
                    else if (action.result) {//standard move
                        Tile loc = ((MoveAction) action).getTile();    
                        player.setPosition(loc);               
                        System.out.println("playerId: "+player.getId()+", move attempt result: "+action.result);    
                    }
                    else{
                        System.out.println("bad move call------------");
                    }



                    break;
                case SHOWCARD:
                    System.out.println("    CASE SHOWCARD");
                    if(gui != null && !(action.getPlayer() instanceof AiAdvanced)){
                        gui.actionResponse(action);
                    }
                    actionLog.add(action);
                    break;
                case SHOWCARDS:
                     System.out.println("    CASE SHOWCARDS");
                    if(gui != null){//if there is a gui
                        if (((ShowCardsAction)action).getSuggester() instanceof AiAdvanced ){
                        }
                        
                        if (action.getPlayer() instanceof AiAdvanced){//if player who needs to respond is Ai, extract the reply set by the ai player in the show cards action execute
                            replyToShowCards((ShowCardsAction)action);
                        }
                        else{//else wait for the gui to set the reply before processing it
                            
                            gui.actionResponse(action);
                        }
                          
                    }
                    else{
                        replyToShowCards((ShowCardsAction)action);
                    }
                    actionLog.add(action);
                    break;                 
                case START:
                    System.out.println("    CASE START "+player.getId());
                    nextAction = new StartTurnAction(player);

                    //GIVE PLAYERS CARDS
                    handOutCards();
                    
                    //setup the intrigue card deck
                    intrigueCards = new ArrayList<>();
                    for (int i = 0; i < 4; i++){
                        intrigueCards.add(new TeleportIntrigue(i));
                    }
                    for (int i = 0; i < 4; i++){
                        intrigueCards.add(new ThrowAgainIntrigue(i));
                    }
                    for (int i = 0; i < 4; i++){
                        intrigueCards.add(new ExtraTurnIntrigue(i));
                    }
                    for (int i = 0; i < 4; i++){
                        intrigueCards.add(new AvoidSuggestionIntrigue(i));
                    }

                    break;
                case STARTTURN:
                    System.out.println("    CASE STARTTURN " + action.getPlayer().getId() + " FROM: " + state.getLastAction().getActionType());

                    moveActionLog();
                    LinkedList<Action> actionsToNotify = getActions();

                    player.setCanReceiveIntrigue(true);//allow player to receive an intrigue

                    if (gui != null && !(action.getPlayer() instanceof AiAdvanced)) {
                        System.out.println("--------------------------prompting gui for player+" + action.getPlayer());
                        gui.newHumanPlayerTurn(actionsToNotify);

                    } else if (player == action.getPlayer() && player instanceof AiAdvanced){//end turn if player is ai and they didnt allready end their turn
                        System.out.println(action.getPlayer().getId());
                        System.out.println(player.getId()+" endTurnAi: after starturn executed");
                        endTurnAi();
                        
                    }
                                            
                    break;
                case SUGGEST:
                    System.out.println("    CASE SUGGEST "+action.getPlayer().getId() + " FROM: "+state.getLastAction().getActionType());
                    if (!(state.getLastAction().getActionType() == ActionType.SUGGEST || state.getLastAction().getActionType() == ActionType.ACCUSATION)) {
                        if (action.result){

                            try {
                                System.out.println("size"+players.size());
                                System.out.println("[GameController.performAction] pulling player original position: "+players.get((((SuggestAction) action).getPersonCard().getId())).getPosition());
                                players.get((((SuggestAction) action).getPersonCard().getId())).setPosition(bm.getRoom(((SuggestAction) action).getRoomCard().getId()));//move the person being suggested into the room of the suggestion
                                System.out.println("[GameController.performAction] pulling player new position: "+players.get((((SuggestAction) action).getPersonCard().getId())).getPosition());
  
                                ((SuggestAction) action).getWeaponCard().setPosition(bm.getRoom(((SuggestAction) action).getRoomCard().getId()));//move the weapon token for the weaponCard to the room of the suggestion

                                
                            } catch (NoSuchRoomException ex) {
                                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            nextAction = new ShowCardsAction(((SuggestAction) action).getShower(), ((SuggestAction) action).getPlayer(), ((SuggestAction) action).getFoundCards());

                            
                            
                        }
                        else {
                            if (gui != null && !(action.getPlayer() instanceof AiAdvanced)){
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
                    

                    
                    break;
                case THROWAGAIN:
                    System.out.println("    CASE THROWAGAIN");
                    returnCard((IntrigueCard)((ThrowAgainAction) action).getCard());
                    if(gui != null && !(player instanceof AiAdvanced)){
                        gui.actionResponse(action);
                    }
                    else if (player == action.getPlayer() && player instanceof AiAdvanced){//end turn if player is ai and they didnt allready end their turn
                        endTurnAi();
                    
                    }
                    

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
        return state.getLastAction();
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
     * Gets the winning Player id
     * @return the player id of who won
     */
    public int getWinner() {
        return this.winner;
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
     * Ends the turn of the current player, called by GUI
     */
    public void endTurn() {
        try {
            performAction(new EndTurnAction(player));

            
        } catch (UnknownActionException | TileOccupiedException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   /**
     * Ends the turn of the current player, called by Ai
     */
    public void endTurnAi() {
        boolean waiting = false;
        //TODO remove the line below
        ((AiAdvanced)player).isWaiting();
        if (player instanceof AiAdvanced){
            if(!((AiAdvanced)player).isWaiting()){//if ai player is not waiting for reply from thier suggestion
                try {
                    performAction(new EndTurnAction(player));
                } catch (UnknownActionException | TileOccupiedException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }     
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
            System.out.println(playerIndex);
            if (playerIndex >= players.size()){
                playerIndex = 0;
            }
            else if (players.get(playerIndex).isActive()){//only give cards to active players
                randInt = rand.nextInt(cardDeck.size());//select random index
                players.get(playerIndex).addCard(cardDeck.get(randInt));//give card from cards list at the random index
                cardDeck.remove(randInt);//remove the already given card from cards list
                playerIndex++;
            }
            else{//non active player
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
     * @return the created suggest action
     * 
     */
    public SuggestAction suggest(int personId, int weaponId){
        System.out.println("[GameController.suggest]");
        PersonCard person = getPersonCard(personId);
        RoomCard room = null;
        WeaponCard weapon = getWeaponCard(weaponId);
        
        System.out.println("[GameController.suggest] person: "+person);        
        System.out.println("[GameController.suggest] weapon: "+weapon);
        if (player.getPosition().isRoom()){
            try {
                room = ((Room)player.getPosition()).getCard();
                System.out.println("---"+player.getPosition());
            } catch (NoSuchRoomException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("[GameController.suggest] room: "+room);
        
        if (person == null || room == null || weapon == null){
            System.err.println("unable to find 3 cards");
            return null;
            
        }
        else{
            SuggestAction suggestAction = suggest(person, room, weapon, player);
            return suggestAction;
            
            //if (suggestAction.getPlayer() instanceof AiAdvanced){//if current player is ai
            //    if (!suggestAction.result){//if no one needs to show a card
            //        System.out.println("NO ONE NEEDS TO SHOW CARD TO AI - ENDING TURN");
            //        endTurn();
            //    }
            //    else if (suggestAction.getShower() instanceof AiAdvanced){//if player who needs to show is ai
            //        System.out.println("AI SHOWED AI CARDS - ENDING TURN");
            //
            //        endTurn();
            //    }
            //    else{//human player needs to respond
            //        //do nothing
            //        System.out.println("HUMAN NEEDS TO SHOW CARD TO AI - NOT ENDING TURN");

            //    }
            //}
            
        }
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
        
        AccuseAction accuseAction = new AccuseAction(player, person, room, weapon, murderPerson, murderRoom, murderWeapon);
        
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
        int nextCard = random.nextInt(intrigueCards.size());
        return intrigueCards.remove(nextCard);
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
        intrigueCards.add(card);
    }

    /**
     * Picks an intrigue, gives it to the player, then performs the intrigue
     *
     * @param loc special tile
     * @return returns the picked Intrigue card
     * 
     */
    private Card pickAndPerformIntrigue(Tile loc) {
        IntrigueCard card = ((SpecialTile) loc).getIntrigue(player);//selects an intrigue and gives it to the current player
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

            if (player instanceof AiAdvanced) {//if person who made suggestion is ai
                System.out.println(player.getId()+" endTurnAi: after replyToShowCards");
                endTurnAi();
                
                //endTurn();//end the turn of that ai player

            }  
            
        } catch (UnknownActionException  | TileOccupiedException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean attemptToTeleport(Tile target){
        boolean result = false;
        if (!target.isFull()){
            result = true;
            player.setPosition(target);                    

        }
       
        System.out.println("playerId: "+player.getId()+", [teleport] move attempt result: "+result);
        return result;
    
    }
    
    /**
     * Gets the list of weapon cards
     * @return List of WeaponCard
     */
    public List<WeaponCard> getWeaponCards() {
        return weaponCards;
    }
 
    /**
     * Called to perform an end turn action
     *
     * @param action the end turn action to be performed
     * @return the next action (to be performed) produced by the perform end
     * turn action
     */
    private Action performEndTurnAction(EndTurnAction action) {
        System.out.println("[GameController.performEndTurnAction]");
        IntrigueCard intrigue = null;
        if (!(player instanceof AiAdvanced)){//TODO fix ai-intrigue interaction
            intrigue = endTurnIntrigueTileCheck();
        }
        
        player.setMoves(0);
        if (intrigue != null) {//do not automatically accept the end turn if player recived intrigue
            //performAction(intrigueAction) is handled during the endTurnIntrigueTileCheck() call
            if (intrigue.getCardType() == CardType.EXTRATURN){
                player.setCanReceiveIntrigue(true);//allow player to get another intrigue
            }
            else if (player instanceof AiAdvanced && intrigue.getCardType() == CardType.AVOIDSUGGESTION){
                return new EndTurnAction(player);
            }
        } else {//end the players turn
            
            player.setMoves(0);
            int currentPlayerId = player.getId();

            for (Player p : players) {//remove any avoid suggestion intriue that players may have, do not remove from the current player
                if (p.isActive() && p.getId() != currentPlayerId) {
                    for (IntrigueCard c : p.getIntrigues()){
                        if (c.getCardType() == CardType.AVOIDSUGGESTION){
                            p.removeIntrigue(c);
                            break;
                        }
                    }
                }
            }

            moveActionLog();

            if (state.hasActive()) {//if there are still active players in the game, continue to next turn
                int old = player.getId();
                state.nextTurn(state.nextPlayer());
                System.out.println("[GameController.performAction] end turn transitioning to next player turn: " + old + "->" + players.get(state.getPlayerTurn()));
                return new StartTurnAction(state.getCurrentPlayer());
            } else {//end the game instead of transitioning to next players turn
                System.out.println("[GameController.performAction] end turn no more active players");
                endGame();
            }
        }
        return null;
    } 
    
    /**
     * Called when player ends there turn, check if they should be given an intrigue card
     * @return true if player received an intrigue card, false otherwise
     */
    public IntrigueCard endTurnIntrigueTileCheck(){
        System.out.println("[GameController.endTurnIntrigueTileCheck] :"+player.getCanReceiveIntrigue());
        if (player.getPosition().isSpecial() && player.getCanReceiveIntrigue()){// && !(player instanceof AiAdvanced)){
            
            IntrigueCard card = (IntrigueCard)pickAndPerformIntrigue(player.getPosition());
            CardType type = card.getCardType();
            if (!(player instanceof AiAdvanced)){
                gui.notifyUser("You received an intrigue card: "+type.toString());
            }
            
            System.out.println("[GameController.endTurnIntrigueTileCheck] player is being given an intrigue card"+type.toString());
            player.setCanReceiveIntrigue(false);//player cannot receive another intrigue this turn
            return card;
        }    
        return null;
    }
    
    /**
     * Gets the whole action log
     * @return the action log
     */
    public List<Action> getActionLog(){
        return actionLog;
    }
}

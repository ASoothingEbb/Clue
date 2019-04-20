/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.action.ActionType;
import clue.action.MoveAction;
import clue.action.StartAction;
import clue.action.StartTurnAction;
import clue.action.UnknownActionException;
import clue.card.Card;
import clue.card.CardTest;
import clue.card.IntrigueCard;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.AIPlayer;
import clue.player.Player;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.Tile;
import clue.tile.TileOccupiedException;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steve
 */
public class GameControllerTest {
    private static GameController gc;
    
    public GameControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws UnknownActionException, NoSuchRoomException, NoSuchTileException, MissingRoomDuringCreationException, GameController.TooManyPlayersException, TileOccupiedException, NotEnoughPlayersException {

        gc = new GameController(2,0,"resources/archersAvenueTiles.csv", "resources/archersAvenueDoors.csv");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetsFirstPlayerTurn() throws Exception{
        System.out.println("setsFirstPlayerTurn");
        
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv");
        
        Player p0 = gc.getPlayer(0);
        Player p1 = gc.getPlayer(1);
        
        assertTrue(gc.getLastAction() instanceof StartTurnAction);
        
        assertEquals(p0.getId(),gc.getPlayer().getId());
    }
    
    @Test
    public void testPlayersAtCorrectStartingLocations() throws Exception{
        System.out.println("playersAtCorrectStartingLocations");
        
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv");
        
        Player p0 = gc.getPlayer(0);
        Player p1 = gc.getPlayer(1);
        
        assertEquals(5, p0.getPosition().getX());
        assertEquals(6, p0.getPosition().getY());
        
        
        assertEquals(1, p1.getPosition().getX());
        assertEquals(7, p1.getPosition().getY());
    
    
    }
    
    
    
    
    /**
     * Test of performAction method, of class GameController.
     */
    @Test
    public void testPerformAction() throws Exception {
        System.out.println("performAction");
       
        
        
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of suggest method, of class GameController.
     */
    @Test
    public void testSuggest() {
        System.out.println("suggest");
        fail("The test case is a prototype.");
    }

    /**        
     * Test of getLastAction method, of class GameController.
     */
    @Test
    public void testGetLastAction() throws NoSuchRoomException, TileOccupiedException {
        System.out.println("getLastAction");
        Card card = new CardImpl();
        gc.move(0, 0);
        assertEquals(ActionType.MOVE,gc.getLastAction().getActionType());
    }

    /**
     * Test of getPlayer method, of class GameController.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        gc.getPlayer();
    }

    /**
     * Test of roll method, of class GameController.
     */
    @Test
    public void testRoll() throws Exception{
        System.out.println("roll");
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv");
        Player p0 = gc.getPlayer();
        int r = 0;
        for (int i = 0; i < 500; i++){
            r = gc.roll();
            assertTrue(r > 1 && r < 13);
            assertEquals(r,p0.getMoves());
        }

    }

    /**
     * Test of move method, of class GameController.
     */
    @Test
    public void testMove() throws Exception {
        System.out.println("move");
        
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv");
        
        Player p0 = gc.getPlayer(0);
        Player p1 = gc.getPlayer(1);
        
        int r = gc.roll();
        
        List<Tile> adjacent = p0.getPosition().getAdjacent();
        Tile oldTile = p0.getPosition();
        
        assertTrue(oldTile.isFull());//p0 starting tile should be full
        Tile target = null;

        for (Tile t : adjacent){
            if (t.getX() == 4 && t.getY() == 6){
                target = t;
            }
        }
        assertTrue(target != null);//tile x == 4 and y ==6 not found
        
        assertFalse(target.isFull());
        gc.move(target);
        assertTrue(target.isFull());
        
        
        assertEquals(4, p0.getPosition().getX());//check if player was moved
        assertEquals(6, p0.getPosition().getY());
        
        assertFalse(oldTile.isFull());//p0 starting tile should no longer be full
        assertTrue(gc.getLastAction() instanceof MoveAction);
        
        gc.endTurn();//ends turn of player 1 

    }
    
    
    
    
    
    
    @Test
    public void testMoveEndMove2Players() throws Exception{
    System.out.println("moveEndMove2Players");
    
            
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv");
        
        Player p0 = gc.getPlayer(0);
        Player p1 = gc.getPlayer(1);
        
        int r = gc.roll();
        
        List<Tile> adjacent = p0.getPosition().getAdjacent();
        Tile oldTile = p0.getPosition();
        
        assertTrue(oldTile.isFull());//p0 starting tile should be full
        Tile target = null;

        for (Tile t : adjacent){
            if (t.getX() == 4 && t.getY() == 6){
                target = t;
            }
        }
        assertTrue(target != null);//tile x == 4 and y ==6 not found
        
        assertFalse(target.isFull());
        gc.move(target);
        assertTrue(target.isFull());
        
        
        assertEquals(4, p0.getPosition().getX());//check if player was moved
        assertEquals(6, p0.getPosition().getY());
        
        assertFalse(oldTile.isFull());//p0 starting tile should no longer be full
        assertTrue(gc.getLastAction() instanceof MoveAction);
        
        gc.endTurn();//ends turn of player 1
        
        r = gc.roll();
        
        adjacent = p1.getPosition().getAdjacent();
        oldTile = p1.getPosition();
        
        assertTrue(oldTile.isFull());//p1 starting tile should be full
        
        target = null;

        for (Tile t : adjacent){
            if (t.getX() == 2 && t.getY() == 7){
                target = t;
            }
        }
        assertTrue(target != null);//tile x == 2 and y == 7 not found
        assertFalse(target.isFull());
        gc.move(target);
        assertTrue(target.isFull());
        
        assertEquals(2, p1.getPosition().getX());//check if player was moved
        assertEquals(7, p1.getPosition().getY());
        
        assertFalse(oldTile.isFull());//p1 starting tile should no longer be full
        assertTrue(gc.getLastAction() instanceof MoveAction);

    }
    
    @Test
    public void testMoveTwiceOneTurn() throws Exception{
        System.out.println("moveTwiceOneTurn");
        
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv");
        
        Player p0 = gc.getPlayer(0);
        Player p1 = gc.getPlayer(1);
        
        int r = gc.roll();
        
        List<Tile> adjacent = p0.getPosition().getAdjacent();
        Tile oldTile = p0.getPosition();
        
        assertTrue(oldTile.isFull());//p0 starting tile should be full
        Tile target = null;

        for (Tile t : adjacent){
            if (t.getX() == 4 && t.getY() == 6){
                target = t;
            }
        }
        assertTrue(target != null);//tile x == 4 and y ==6 not found
        
        assertFalse(target.isFull());
        gc.move(target);
        assertTrue(target.isFull());
        
        
        assertEquals(4, p0.getPosition().getX());//check if player was moved
        assertEquals(6, p0.getPosition().getY());
        
        assertFalse(oldTile.isFull());//p0 starting tile should no longer be full
        assertTrue(gc.getLastAction() instanceof MoveAction);
        
        adjacent = p0.getPosition().getAdjacent();
        target = null;

        for (Tile t : adjacent){
            if (t.getX() == 4 && t.getY() == 5){
                target = t;
            }
        }
        assertTrue(target != null);//tile x == 4 and y ==5 not found
        
        assertFalse(target.isFull());
        gc.move(target);//attempt to perform a second, single tile move in a single turn
        assertTrue(target.isFull());
        
        assertEquals(4, p0.getPosition().getX());//check if player was moved
        assertEquals(5, p0.getPosition().getY());
        
    }     
        
    
    @Test
    public void testHandOutCards() throws Exception{
        System.out.println("handOutCards");
        
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv");
        
        
        ArrayList<Card> cards = new ArrayList<>();
        
        for (Player p : gc.getPlayers()){
        
            for (Card c : p.getCards()){
                assertFalse(cards.contains(c));
                cards.add(c);
            }
        }
        assertEquals(11,cards.size());//numberOfPersons + numberOfRooms + numberOfWeapons - 3 (for murder cards)
                
        for (Card murderCard : gc.getMurderCards()){//check that no players have the murder cards
            assertFalse(cards.contains(murderCard));
        }

    }

//    /**
//     * Test of showCard method, of class GameController.
//     */
//    @Test
//    public void testShowCard() throws Exception {
//        System.out.println("showCard");
//        fail("The test case is a prototype.");
//
//    }
    /**
     * Test of accuse method, of class GameController.
     */
    @Test
    public void testAccuse() throws Exception {
        //System.out.println("accuse");
        fail("The test case is a prototype.");

    }

    /**
     * Test of drawCard method, of class GameController.
     */
    @Test
    public void testDrawCard() {
        //System.out.println("drawCard");
        fail("The test case is a prototype.");

    }

    /**
     * Test of getActions method, of class GameController.
     */
    @Test
    public void testGetActions() throws Exception {
        System.out.println("getActions");
        gc = new GameController(2,0,"resources/archersAvenueTiles.csv", "resources/archersAvenueDoors.csv");
        Queue<Action> expResult = new LinkedList();
        gc.endTurn();
        gc.move(3,22);
        gc.suggest(0, 0);
        expResult.add(gc.getLastAction());
        gc.endTurn();
        Queue<Action> result = gc.getActions();
        assertEquals(expResult.element().getActionType(), result.element().getActionType());
    }

    /**
     * Test of getPlayer method, of class GameController.
     */
    @Test
    public void testGetPlayer_0args() {
        //System.out.println("getPlayer");
        fail("The test case is a prototype.");

    }

    /**
     * Test of getPlayer method, of class GameController.
     */
    @Test
    public void testGetPlayer_int() throws Exception{
        System.out.println("getPlayer");
        gc = new GameController(2,0,"resources/archersAvenueTiles.csv", "resources/archersAvenueDoors.csv");
        Player expResult = new Player(1);
        Player result = gc.getPlayer(1);
        assertEquals(expResult.getId(),result.getId());
    }

    /**
     * Test of getPlayers method, of class GameController.
     */
    @Test
    public void testGetPlayers() {
        System.out.println("getPlayers");
        List<Player> expResult = new ArrayList();
        expResult.add(new Player(0));
        expResult.add(new Player(1));
        List<Player> result = gc.getPlayers();
        for(Player p: expResult){
            assertTrue(gc.getPlayer(p.getId()).isActive());
        }
    }

    /**
     * Test of move method, of class GameController.
     */
    @Test
    public void testMove_Queue() throws Exception {
        System.out.println("move");
        //Queue<Tile> tiles = new LinkedList();
        Tile tile = new Tile(0,0);
        Tile tile2 = new Tile(0,1);
        tile.addAdjacent(tile2);
        gc.getPlayer().setPosition(tile);
        gc.getPlayer().setMoves(1);
        //tiles.add(tile2);
        gc.move(tile2);
        assertTrue(tile2 == gc.getPlayer().getPosition());
    }

    /**
     * Test of move method, of class GameController.
     */
    @Test
    public void testMove_Tile() throws Exception {
        System.out.println("move");
        Tile tile = new Tile(0,0);
        Tile tile2 = new Tile(0,1);
        tile.addAdjacent(tile2);
        gc.getPlayer().setPosition(tile);
        gc.getPlayer().setMoves(1);
        gc.move(tile2);
        assertEquals(tile2,gc.getPlayer().getPosition());
    }

    /**
     * Test of endTurn method, of class GameController.
     */
    @Test
    public void testEndTurn() throws Exception {
        System.out.println("endTurn");
        gc = new GameController(2,0,"resources/archersAvenueTiles.csv", "resources/archersAvenueDoors.csv");
        System.out.println(gc);
        Player expResult = gc.getPlayer();
        gc.endTurn();
        assertNotEquals(expResult, gc.getPlayer());
    }
    
    public class CardImpl extends Card{
        
        public CardImpl() {
            super(0);
        }
        
    }
    
    
    @Test
    public void testAivsAi() throws Exception{
        System.out.println("AivsAi");
        gc = new GameController(0,2,"resources/archersAvenueTiles.csv", "resources/archersAvenueDoors.csv");
    }
}

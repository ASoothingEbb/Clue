/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.GameController.MovementException;
import clue.action.Action;
import clue.action.MoveAction;
import clue.action.StartAction;
import clue.action.StartTurnAction;
import clue.action.UnknownActionException;
import clue.card.Card;
import clue.card.IntrigueCard;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.Tile;
import clue.tile.TileOccupiedException;
import java.util.ArrayList;
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
    public static void setUpClass() throws InterruptedException, UnknownActionException, NoSuchRoomException, NoSuchTileException, MissingRoomDuringCreationException, GameController.TooManyPlayersException, TileOccupiedException {

        gc = new GameController(1,1,"testCsv/tiles1.csv", "testCsv/doors1.csv",6,8);
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

    //@Test
    public void testSetsFirstPlayerTurn() throws Exception{
        System.out.println("setsFirstPlayerTurn");
        
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv",6,8);
        
        Player p0 = gc.getPlayer(0);
        Player p1 = gc.getPlayer(1);
        
        assertTrue(gc.getLastAction() instanceof StartTurnAction);
        
        assertEquals(p0.getId(),gc.getPlayer().getId());
    }
    
    //@Test
    public void testPlayersAtCorrectStartingLocations() throws Exception{
        System.out.println("playersAtCorrectStartingLocations");
        
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv",6,8);
        
        Player p0 = gc.getPlayer(0);
        Player p1 = gc.getPlayer(1);
        
        assertTrue(p0.getPosition().getX() == 5 && p0.getPosition().getY() == 6);
        assertTrue(p1.getPosition().getX() == 1 && p1.getPosition().getY() == 7);
    
    
    }
    
    
    
    
    /**
     * Test of performAction method, of class GameController.
     */
    //@Test
    public void testPerformAction() throws Exception {
        System.out.println("performAction");
       
        
        
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of suggest method, of class GameController.
     */
    //@Test
    public void testSuggest() throws InterruptedException, UnknownActionException, TileOccupiedException {
        System.out.println("suggest");
        fail("The test case is a prototype.");
    }

    /**        instance = new GameController(new ArrayList<Player>());
     * Test of getLastAction method, of class GameController.
     */
    //@Test
    public void testGetLastAction() {
        System.out.println("getLastAction");
        fail("The test case is a prototype.");

    }

    /**
     * Test of getPlayer method, of class GameController.
     */
    //@Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        fail("The test case is a prototype.");

    }

    /**
     * Test of roll method, of class GameController.
     */
    //@Test
    public void testRoll() throws Exception{
        System.out.println("roll");
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv",6,8);
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
    //@Test
    public void testMove() throws Exception {
        System.out.println("move");
        
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv",6,8);
        
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
    
    
    
    
    
    
    //@Test
    public void testMoveEndMove2Players() throws Exception{
    System.out.println("moveEndMove2Players");
    
            
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv",6,8);
        
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
    
    //@Test
    public void testMoveTwiceOneTurn() throws Exception{
        System.out.println("moveTwiceOneTurn");
        
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv",6,8);
        
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
        
    
    //@Test
    public void testHandOutCards() throws Exception{
        System.out.println("handOutCards");
        
        gc = new GameController(1,1,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv",6,8);
        
        
        ArrayList<Card> cards = new ArrayList<>();
        
        for (Player p : gc.getPlayers()){
        
            for (Card c : p.getCards()){
                assertFalse(cards.contains(c));
                cards.add(c);
            }
        }
        assertEquals(18,cards.size());
                
                
                
               
    
    
    }

    /**
     * Test of showCard method, of class GameController.
     */
    //@Test
    public void testShowCard() throws Exception {
        System.out.println("showCard");
        fail("The test case is a prototype.");

    }
    /**
     * Test of accuse method, of class GameController.
     */
    //@Test
    public void testAccuse() throws Exception {
        //System.out.println("accuse");
        fail("The test case is a prototype.");

    }

    /**
     * Test of drawCard method, of class GameController.
     */
    //@Test
    public void testDrawCard() {
        //System.out.println("drawCard");
        fail("The test case is a prototype.");

    }

    /**
     * Test of getActions method, of class GameController.
     */
    //@Test
    public void testGetActions() {
        //System.out.println("getActions");
        fail("The test case is a prototype.");

    }

    /**
     * Test of getPlayer method, of class GameController.
     */
    //@Test
    public void testGetPlayer_0args() {
        //System.out.println("getPlayer");
        fail("The test case is a prototype.");

    }

    /**
     * Test of getPlayer method, of class GameController.
     */
    //@Test
    public void testGetPlayer_int() {
        //System.out.println("getPlayer");
        fail("The test case is a prototype.");

    }

    /**
     * Test of getPlayers method, of class GameController.
     */
    //@Test
    public void testGetPlayers() {
        //System.out.println("getPlayers");
        fail("The test case is a prototype.");
 
    }

    /**
     * Test of move method, of class GameController.
     */
    //@Test
    public void testMove_Queue() throws Exception {
        //System.out.println("move");
        fail("The test case is a prototype.");

    }

    /**
     * Test of move method, of class GameController.
     */
    //@Test
    public void testMove_Tile() throws Exception {
        //System.out.println("move");
        fail("The test case is a prototype.");

    }
    
    @Test
    public void testArchersAvenue() throws Exception{
        System.out.println("archersAvenue");
        gc = new GameController(1,1,"resources/archersAvenueTiles.csv", "resources/archersAvenueDoors.csv",24,25);
        System.out.println("archersAvenue end");
    
    }
    
}

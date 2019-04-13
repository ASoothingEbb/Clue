/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.action.StartAction;
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

        gc = new GameController(1,1,"testCsv/tiles1.csv", "testCsv/doors1.csv");
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

    /**
     * Test of performAction method, of class GameController.
     */
    @Test
    public void testPerformAction() throws Exception {
        System.out.println("performAction");
        gc = new GameController(2,0,"testCsv/tiles1WithIds.csv", "testCsv/doors1.csv");
        
        
        Player p1 = gc.getPlayer(1);
        Player p2 = gc.getPlayer(2);
        
        assertTrue(gc.getLastAction() instanceof StartAction);
        assertTrue(gc.getPlayer() == p1);
        System.out.println(p1.getPosition().getX() +","+ p1.getPosition().getY());
        System.out.println(p2.getPosition().getX() +","+ p2.getPosition().getY());
        
        assertTrue(p1.getPosition().getX() == 5 && p1.getPosition().getY() == 6);
        assertTrue(p2.getPosition().getX() == 1 && p2.getPosition().getY() == 7);
        int r = gc.roll();
        
        
        //gc.performAction(action);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of suggest method, of class GameController.
     */
    @Test
    public void testSuggest() throws InterruptedException, UnknownActionException, TileOccupiedException {
        //System.out.println("suggest");
    }

    /**        instance = new GameController(new ArrayList<Player>());
     * Test of getLastAction method, of class GameController.
     */
    @Test
    public void testGetLastAction() {
        //System.out.println("getLastAction");

    }

    /**
     * Test of getPlayer method, of class GameController.
     */
    @Test
    public void testGetPlayer() {
        //System.out.println("getPlayer");

    }

    /**
     * Test of roll method, of class GameController.
     */
    @Test
    public void testRoll() {
        //System.out.println("roll");

    }

    /**
     * Test of move method, of class GameController.
     */
    @Test
    public void testMove() throws Exception {
        //System.out.println("move");

    }

    /**
     * Test of showCard method, of class GameController.
     */
    @Test
    public void testShowCard() throws Exception {
        //System.out.println("showCard");

    }
    /**
     * Test of accuse method, of class GameController.
     */
    @Test
    public void testAccuse() throws Exception {
        //System.out.println("accuse");

    }

    /**
     * Test of drawCard method, of class GameController.
     */
    @Test
    public void testDrawCard() {
        //System.out.println("drawCard");

    }

    /**
     * Test of getActions method, of class GameController.
     */
    @Test
    public void testGetActions() {
        //System.out.println("getActions");

    }

    /**
     * Test of getPlayer method, of class GameController.
     */
    @Test
    public void testGetPlayer_0args() {
        //System.out.println("getPlayer");

    }

    /**
     * Test of getPlayer method, of class GameController.
     */
    @Test
    public void testGetPlayer_int() {
        //System.out.println("getPlayer");

    }

    /**
     * Test of getPlayers method, of class GameController.
     */
    @Test
    public void testGetPlayers() {
        //System.out.println("getPlayers");
 
    }

    /**
     * Test of move method, of class GameController.
     */
    @Test
    public void testMove_Queue() throws Exception {
        //System.out.println("move");

    }

    /**
     * Test of move method, of class GameController.
     */
    @Test
    public void testMove_Tile() throws Exception {
        //System.out.println("move");

    }
    
}

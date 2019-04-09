/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.action.UnknownActionException;
import clue.card.Card;
import clue.card.IntrigueCard;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;
import clue.tile.Tile;
import java.util.ArrayList;
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
    
    public GameControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
        Action action = null;
        GameController instance = new GameController(new ArrayList<Player>());
        instance.performAction(action);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of suggest method, of class GameController.
     */
    @Test
    public void testSuggest() throws InterruptedException, UnknownActionException {
        System.out.println("suggest");
        PersonCard person = null;
        RoomCard room = null;
        WeaponCard weapon = null;
        Player player = null;
        GameController instance = new GameController(new ArrayList<Player>());
        instance.suggest(person, room, weapon, player);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastAction method, of class GameController.
     */
    @Test
    public void testGetLastAction() {
        System.out.println("getLastAction");
        GameController instance = null;
        Action expResult = null;
        Action result = instance.getLastAction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayer method, of class GameController.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        GameController instance = null;
        Player expResult = null;
        Player result = instance.getPlayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of roll method, of class GameController.
     */
    @Test
    public void testRoll() {
        System.out.println("roll");
        GameController instance = null;
        int expResult = 0;
        int result = instance.roll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of move method, of class GameController.
     */
    @Test
    public void testMove() throws Exception {
        System.out.println("move");
        Queue<Tile> tiles = null;
        GameController instance = null;
        instance.move(tiles);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showCard method, of class GameController.
     */
    @Test
    public void testShowCard() throws Exception {
        System.out.println("showCard");
        Card card = null;
        GameController instance = null;
        instance.showCard(card);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of accuse method, of class GameController.
     */
    @Test
    public void testAccuse() throws Exception {
        System.out.println("accuse");
        PersonCard person = null;
        RoomCard room = null;
        WeaponCard weapon = null;
        GameController instance = null;
        instance.accuse(person, room, weapon);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawCard method, of class GameController.
     */
    @Test
    public void testDrawCard() {
        System.out.println("drawCard");
        GameController instance = null;
        IntrigueCard expResult = null;
        IntrigueCard result = instance.drawCard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActions method, of class GameController.
     */
    @Test
    public void testGetActions() {
        System.out.println("getActions");
        GameController instance = null;
        Queue<Action> expResult = null;
        Queue<Action> result = instance.getActions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

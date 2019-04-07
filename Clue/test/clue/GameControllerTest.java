/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.action.UnknownActionException;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.Player;
import java.util.ArrayList;
import java.util.HashMap;
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
        Action expResult = null;
        Action result = instance.suggest(person, room, weapon, player);
        assertEquals(expResult, result);
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
     * Test of getLocations method, of class GameController.
     */
    @Test
    public void testGetLocations() {
        System.out.println("getLocations");
        GameController instance = null;
        HashMap expResult = null;
        HashMap result = instance.getLocations();
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
    
}

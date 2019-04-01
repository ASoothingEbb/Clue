/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.player.Player;
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
public class GameStateTest {
    
    public GameStateTest() {
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
     * Test of register method, of class GameState.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        Observer observer = null;
        GameState instance = null;
        instance.register(observer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unregister method, of class GameState.
     */
    @Test
    public void testUnregister() {
        System.out.println("unregister");
        Observer observer = null;
        GameState instance = null;
        instance.unregister(observer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of notifyAllObservers method, of class GameState.
     */
    @Test
    public void testNotifyAllObservers() {
        System.out.println("notifyAllObservers");
        GameState instance = null;
        instance.notifyAllObservers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextTurn method, of class GameState.
     */
    @Test
    public void testNextTurn() {
        System.out.println("nextTurn");
        GameState instance = null;
        int expResult = 0;
        int result = instance.nextTurn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayerTurn method, of class GameState.
     */
    @Test
    public void testGetPlayerTurn() {
        System.out.println("getPlayerTurn");
        GameState instance = null;
        int expResult = 0;
        int result = instance.getPlayerTurn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNextTurn method, of class GameState.
     */
    @Test
    public void testSetNextTurn() {
        System.out.println("setNextTurn");
        int player = 0;
        GameState instance = null;
        instance.setNextTurn(player);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of previousPlayer method, of class GameState.
     */
    @Test
    public void testPreviousPlayer() {
        System.out.println("previousPlayer");
        GameState instance = null;
        instance.previousPlayer();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAction method, of class GameState.
     */
    @Test
    public void testGetAction() {
        System.out.println("getAction");
        GameState instance = null;
        Action expResult = null;
        Action result = instance.getAction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isRunning method, of class GameState.
     */
    @Test
    public void testIsRunning() {
        System.out.println("isRunning");
        GameState instance = null;
        boolean expResult = false;
        boolean result = instance.isRunning();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of endGame method, of class GameState.
     */
    @Test
    public void testEndGame() {
        System.out.println("endGame");
        GameState instance = null;
        Player expResult = null;
        Player result = instance.endGame();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAction method, of class GameState.
     */
    @Test
    public void testSetAction() {
        System.out.println("setAction");
        Action action = null;
        GameState instance = null;
        instance.setAction(action);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

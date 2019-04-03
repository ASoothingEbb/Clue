/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.player.AIPlayer;
import clue.player.Player;
import java.util.ArrayList;
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
        Observer observer = new AIPlayer(0);
        GameState instance = new GameState(new ArrayList());
        instance.register(observer);
        assertEquals(1,instance.playersNumber);
    }

    /**
     * Test of unregister method, of class GameState.
     */
    @Test
    public void testUnregister() {
        System.out.println("unregister");
        Observer observer = new AIPlayer(0);
        ArrayList<Player> players = new ArrayList();
        players.add((Player)observer);
        GameState instance = new GameState(players);
        instance.unregister(observer);
        assertEquals(0,instance.playersNumber);
    }

    /**
     * Test of notifyAllObservers method, of class GameState.
     */
    @Test
    public void testNotifyAllObservers() {
        System.out.println("notifyAllObservers");
        Player player0 = new AIPlayer(0);
        Player player1 = new AIPlayer(1);
        Player player2 = new AIPlayer(2);
        ArrayList<Player> players = new ArrayList();
        players.add(player0);
        players.add(player1);
        players.add(player2);
        GameState instance = new GameState(players);
        instance.notifyAllObservers();
    }

    /**
     * Test of nextTurn method, of class GameState.
     */
    @Test
    public void testNextTurn() {
        System.out.println("nextTurn");
        ArrayList<Player> players = new ArrayList();
        players.add(new AIPlayer(0));
        GameState instance = new GameState(players);
        int expResult = 0;
        int result = instance.nextPlayer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPlayerTurn method, of class GameState.
     */
    @Test
    public void testGetPlayerTurn() {
        System.out.println("getPlayerTurn");
        ArrayList<Player> players = new ArrayList();
        players.add(new AIPlayer(0));
        GameState instance = new GameState(players);
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
        GameState instance = new GameState(new ArrayList<Player>());
        instance.nextTurn(player);
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(0,instance.getPlayerTurn());
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
        GameState instance = new GameState(new ArrayList<Player>());
        boolean expResult = false;
        instance.endGame();
        boolean result = instance.isRunning();
        assertEquals(expResult, result);
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

    /**
     * Test of nextPlayer method, of class GameState.
     */
    @Test
    public void testNextPlayer() {
        System.out.println("nextPlayer");
        GameState instance = null;
        int expResult = 0;
        int result = instance.nextPlayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextPointer method, of class GameState.
     */
    @Test
    public void testGetNextPointer() {
        System.out.println("getNextPointer");
        int i = 0;
        GameState instance = null;
        int expResult = 0;
        int result = instance.getNextPointer(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayer method, of class GameState.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        int id = 0;
        GameState instance = null;
        Player expResult = null;
        Player result = instance.getPlayer(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

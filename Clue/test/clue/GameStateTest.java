/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.action.EndTurnAction;
import clue.action.StartAction;
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

    GameState instance;

    public GameStateTest() {
        Player player0 = new AIPlayer(0);
        Player player1 = new AIPlayer(1);
        Player player2 = new AIPlayer(2);
        ArrayList<Player> players = new ArrayList();
        players.add(player0);
        players.add(player1);
        players.add(player2);
        instance = new GameState(players);
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
        instance.register(observer);
        assertEquals(1, instance.playersNumber);
    }

    /**
     * Test of unregister method, of class GameState.
     */
    @Test
    public void testUnregister() {
        System.out.println("unregister");
        Observer observer = new AIPlayer(0);
        instance.register(observer);
        instance.unregister(observer);
        assertEquals(0, instance.playersNumber);
    }

    /**
     * Test of notifyAllObservers method, of class GameState.
     */
    @Test
    public void testNotifyAllObservers() {
        System.out.println("notifyAllObservers");
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
        instance = new GameState(players);
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
        instance = new GameState(players);
        int expResult = 0;
        int result = instance.getPlayerTurn();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNextTurn method, of class GameState.
     */
    @Test
    public void testSetNextTurn() {
        System.out.println("setNextTurn");
        int player = 0;
        instance.nextTurn(player);
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(0, instance.getPlayerTurn());
    }

    /**
     * Test of previousPlayer method, of class GameState.
     */
    @Test
    public void testPreviousPlayer() {
        System.out.println("previousPlayer");
        instance.register(new AIPlayer(1));
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
        Action action = new EndTurnAction();
        instance.setAction(action);
        Action expResult = action;
        Action result = instance.getAction();
        assertEquals(expResult, result);
    }

    /**
     * Test of isRunning method, of class GameState.
     */
    @Test
    public void testIsRunning() {
        System.out.println("isRunning");
        boolean expResult = true;
        boolean result = instance.isRunning();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAction method, of class GameState.
     */
    @Test
    public void testSetAction() {
        System.out.println("setAction");
        Action action = new StartAction();
        instance.setAction(action);
        assertEquals(action, instance.getAction());
    }

    /**
     * Test of nextPlayer method, of class GameState.
     */
    @Test
    public void testNextPlayer() {
        System.out.println("nextPlayer");
        ArrayList<Player> players = new ArrayList();
        players.add(new AIPlayer(0));
        players.add(new AIPlayer(1));
        GameState instance = new GameState(players);
        int expResult = 1;
        int result = instance.nextPlayer();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNextPointer method, of class GameState.
     */
    @Test
    public void testGetNextPointer() {
        System.out.println("getNextPointer");
        ArrayList<Player> players = new ArrayList();
        players.add(new AIPlayer(0));
        players.add(new AIPlayer(1));
        players.get(1).removeFromPlay();
        players.add(new AIPlayer(2));
        int i = 0;
        int expResult = 2;
        int result = instance.getNextPointer(i);
        assertEquals(expResult, result);
        expResult = 0;
        result = instance.getNextPointer(result);
        assertEquals(expResult,result);
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

    /**
     * Test of endGame method, of class GameState.
     */
    @Test
    public void testEndGame() {
        System.out.println("endGame");
        boolean expResult = false;
        instance.endGame();
        boolean result = instance.isRunning();
        assertEquals(expResult, result);
    }
}

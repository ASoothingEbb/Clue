/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.action.EndTurnAction;
import clue.action.StartAction;
import clue.action.UnknownActionException;
import clue.player.Player;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.TileOccupiedException;
import java.util.ArrayList;
import java.util.List;
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

    private static GameController game;

    private static GameState instance;
    private static ArrayList<Player> players;

    public GameStateTest() {
        //Player player0 = new Player(0);
        //Player player1 = new Player(1);
        //Player player2 = new Player(2);
        //players = new ArrayList();
        //players.add(player0);
        //players.add(player1);
        //players.add(player2);
        //instance = new GameState(players);
    }

    @BeforeClass
    public static void setUpClass() throws UnknownActionException, NoSuchRoomException, NoSuchTileException, MissingRoomDuringCreationException, GameController.TooManyPlayersException, TileOccupiedException, NotEnoughPlayersException {
        game = new GameController(2, 0, "Maps/archersAvenue/archersAvenueTiles.csv", "Maps/archersAvenue/archersAvenueDoor.csv");
        players = (ArrayList<Player>) game.getPlayers();
        instance = new GameState(players);
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
     * Test of nextTurn method, of class GameState.
     */
    @Test
    public void testNextTurn() {
        System.out.println("nextTurn");
        ArrayList<Player> players = new ArrayList();
        players.add(new Player(0));
        players.add(new Player(1));
        instance = new GameState(players);
        int expResult = 1;
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
        players.add(new Player(0));
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
        assertEquals(0, instance.getPlayerTurn());
    }

   

    /**
     * Test of getLastAction method, of class GameState.
     */
    @Test
    public void testGetAction() {
        System.out.println("getAction");
        Action action = new EndTurnAction(new Player(0));
        instance.setAction(action);
        Action expResult = action;
        Action result = instance.getLastAction();
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
        assertEquals(action, instance.getLastAction());
    }

    /**
     * Test of nextPlayer method, of class GameState.
     */
    @Test
    public void testNextPlayer() {
        System.out.println("nextPlayer");
        ArrayList<Player> players = new ArrayList();
        players.add(new Player(0));
        players.add(new Player(1));
        GameState instance = new GameState(players);
        instance.nextTurn(0);

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
        players.add(new Player(0));
        players.add(new Player(1));
        players.add(new Player(2));
        GameState instance = new GameState(players);

        assertEquals(1, instance.getNextPointer(0));


        assertEquals(2, instance.getNextPointer(1));
        assertEquals(0, instance.getNextPointer(2));



    }

    /**
     * Test of getPlayer method, of class GameState.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        int id = 0;

        ArrayList<Player> players = new ArrayList();
        players.add(new Player(0));
        players.add(new Player(1));
        GameState instance = new GameState(players);

        Player expResult = players.get(0);
        Player result = instance.getPlayer(id);
        assertEquals(expResult, result);

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

    /**
     * Test of getCurrentPlayer method, of class GameState.
     */
    @Test
    public void testGetCurrentPlayer() {
        System.out.println("getCurrentPlayer");
        int expResult = 0;
        instance.nextTurn(0);
        int result = instance.getCurrentPlayer().getId();
        assertEquals(expResult, result);
    }




}

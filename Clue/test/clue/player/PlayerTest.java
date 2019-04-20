/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.player;

import clue.GameController;
import clue.MissingRoomDuringCreationException;
import clue.NotEnoughPlayersException;
import clue.action.Action;
import clue.action.UnknownActionException;
import clue.card.Card;
import clue.card.IntrigueCard;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.Tile;
import clue.tile.TileOccupiedException;
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
public class PlayerTest {
    
    public PlayerTest() {
    }
    private static GameController gc;


    
    @BeforeClass
    public static void setUpClass() throws UnknownActionException, NoSuchRoomException, NoSuchTileException, MissingRoomDuringCreationException, GameController.TooManyPlayersException, TileOccupiedException, NotEnoughPlayersException {

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
     * Test of onUpdate method, of class Player.
     */
    @Test
    public void testOnUpdate() {
        System.out.println("onUpdate");
        Player instance = new PlayerImpl(0, gc);
        instance.onUpdate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Player.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Player instance = new PlayerImpl(0, gc);
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isActive method, of class Player.
     */
    @Test
    public void testIsActive() {
        System.out.println("isActive");
        Player instance = new PlayerImpl(0, gc);
        boolean expResult = false;
        boolean result = instance.isActive();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeFromPlay method, of class Player.
     */
    @Test
    public void testRemoveFromPlay() {
        System.out.println("removeFromPlay");
        Player instance = new PlayerImpl(0, gc);
        instance.removeFromPlay();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPosition method, of class Player.
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");
        Player instance = new PlayerImpl(0, gc);
        Tile expResult = null;
        Tile result = instance.getPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPosition method, of class Player.
     */
    @Test
    public void testSetPosition() {
        System.out.println("setPosition");
        Tile t = null;
        Player instance = new PlayerImpl(0, gc);
        instance.setPosition(t);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class PlayerImpl extends Player {

        public PlayerImpl(int id, GameController gc) {
            super(id, gc);
        }
        
    }

    /**
     * Test of addCard method, of class Player.
     */
    @Test
    public void testAddCard() {
        System.out.println("addCard");
        Card card = null;
        Player instance = new PlayerImpl(0, gc);
        instance.addCard(card);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeCard method, of class Player.
     */
    @Test
    public void testRemoveCard() {
        System.out.println("removeCard");
        Card card = null;
        Player instance = new PlayerImpl(0, gc);
        instance.removeCard(card);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasCard method, of class Player.
     */
    @Test
    public void testHasCard() {
        System.out.println("hasCard");
        Card card = null;
        Player instance = new PlayerImpl(0, gc);
        boolean expResult = false;
        boolean result = instance.hasCard(card);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGameController method, of class Player.
     */
    @Test
    public void testGetGameController() {
        System.out.println("getGameController");
        Player instance = null;
        GameController expResult = null;
        GameController result = instance.getGameController();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMoves method, of class Player.
     */
    @Test
    public void testGetMoves() {
        System.out.println("getMoves");
        Player instance = null;
        int expResult = 0;
        int result = instance.getMoves();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMoves method, of class Player.
     */
    @Test
    public void testSetMoves() {
        System.out.println("setMoves");
        int moves = 0;
        Player instance = null;
        instance.setMoves(moves);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addIntrigue method, of class Player.
     */
    @Test
    public void testAddIntrigue() {
        System.out.println("addIntrigue");
        Player instance = null;
        IntrigueCard expResult = null;
        IntrigueCard result = instance.addIntrigue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntrigue method, of class Player.
     */
    @Test
    public void testGetIntrigue() {
        System.out.println("getIntrigue");
        Player instance = null;
        List<IntrigueCard> expResult = null;
        List<IntrigueCard> result = instance.getIntrigue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCards method, of class Player.
     */
    @Test
    public void testGetCards() {
        System.out.println("getCards");
        Player instance = null;
        List<Card> expResult = null;
        List<Card> result = instance.getCards();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLogPointer method, of class Player.
     */
    @Test
    public void testGetLogPointer() {
        System.out.println("getLogPointer");
        Player instance = null;
        int expResult = 0;
        int result = instance.getLogPointer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLogPointer method, of class Player.
     */
    @Test
    public void testSetLogPointer() {
        System.out.println("setLogPointer");
        int pointer = 0;
        Player instance = null;
        instance.setLogPointer(pointer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNotes method, of class Player.
     */
    @Test
    public void testGetNotes() {
        System.out.println("getNotes");
        Player instance = null;
        String expResult = "";
        String result = instance.getNotes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNotes method, of class Player.
     */
    @Test
    public void testSetNotes() {
        System.out.println("setNotes");
        String notes = "";
        Player instance = null;
        instance.setNotes(notes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

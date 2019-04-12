/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.player;

import clue.GameController;
import clue.MissingRoomDuringCreationException;
import clue.action.Action;
import clue.action.UnknownActionException;
import clue.card.Card;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.Tile;
import clue.tile.TileOccupiedException;
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
     * Test of setActiveSuggestionBlock method, of class Player.
     */
    @Test
    public void testSetActiveSuggestionBlock() {
        System.out.println("setActiveSuggestionBlock");
        boolean newActiveSuggestionBlockValue = false;
        Player instance = null;
        instance.setActiveSuggestionBlock(newActiveSuggestionBlockValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActiveSuggestionBlock method, of class Player.
     */
    @Test
    public void testGetActiveSuggestionBlock() {
        System.out.println("getActiveSuggestionBlock");
        Player instance = null;
        boolean expResult = false;
        boolean result = instance.getActiveSuggestionBlock();
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

}

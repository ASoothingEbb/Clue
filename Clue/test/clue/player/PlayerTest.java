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
import clue.action.StartTurnAction;
import clue.action.UnknownActionException;
import clue.card.AvoidSuggestionIntrigue;
import clue.card.Card;
import clue.card.IntrigueCard;
import clue.card.PersonCard;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.Tile;
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
public class PlayerTest {
    
    public PlayerTest() {
    }
    private static GameController gc;


    
    @BeforeClass
    public static void setUpClass() throws UnknownActionException, NoSuchRoomException, NoSuchTileException, MissingRoomDuringCreationException, GameController.TooManyPlayersException, TileOccupiedException, NotEnoughPlayersException {

        gc = new GameController(1,1,"resources/archersAvenueTiles.csv", "resources/archersAvenueDoors.csv");
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
     * Test of getId method, of class Player.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Player instance = new PlayerImpl(0,gc);
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of isActive and removeFromPlay method, of class Player.
     */
    @Test
    public void testActive() {
        System.out.println("isActive");
        Player instance = new PlayerImpl(0,gc);
        assertTrue(instance.isActive());
        instance.removeFromPlay();
        assertFalse(instance.isActive());
    }

    /**
     * Test of getPosition and setPosition method, of class Player.
     */
    @Test
    public void testPosition() {
        System.out.println("getPosition");
        Player instance = new PlayerImpl(0,gc);
        Tile expResult = new Tile();
        instance.setPosition(expResult);
        Tile result = instance.getPosition();
        assertEquals(expResult, result);;
    }


    public class PlayerImpl extends Player {

        public PlayerImpl(int id, GameController gc) {
            super(id, gc);
        }
        
    }

    /**
     * Test of addCard, removeCard and hasCard method, of class Player.
     */
    @Test
    public void testAddCard() {
        System.out.println("addCard");
        Card card = new PersonCard(0);
        Player instance = new PlayerImpl(0,gc);
        assertFalse(instance.hasCard(card));
        instance.addCard(card);
        assertTrue(instance.hasCard(card));
        instance.removeCard(card);
        assertFalse(instance.hasCard(card));
    }

    /**
     * Test of getMoves method, of class Player.
     */
    @Test
    public void testGetMoves() {
        System.out.println("getMoves");
        Player instance = new PlayerImpl(0, gc);
        int expResult = 0;
        int result = instance.getMoves();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMoves method, of class Player.
     */
    @Test
    public void testSetMoves() {
        System.out.println("setMoves");
        int moves = 10;
        Player instance = new PlayerImpl(moves, gc);
        instance.setMoves(moves);
        assertEquals(moves,instance.getMoves());
    }

    /**
     * Test of addIntrigue method, of class Player.
     */
    @Test
    public void testAddIntrigue() {
        System.out.println("addIntrigue");
        Player instance = new PlayerImpl(0, gc);
        IntrigueCard expResult = new AvoidSuggestionIntrigue(0);
        assertFalse(instance.hasIntrigue(expResult));
        instance.addIntrigue(expResult);
        assertTrue(instance.hasIntrigue(expResult));
        
    }

    /**
     * Test of getIntrigue method, of class Player.
     */
    @Test
    public void testGetIntrigue() {
        System.out.println("getIntrigue");
        Player instance = new Player(0,gc);
        List<IntrigueCard> expResult = new ArrayList<IntrigueCard>();
        List<IntrigueCard> result = instance.getIntrigue();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCards method, of class Player.
     */
    @Test
    public void testGetCards() {
        System.out.println("getCards");
        Player instance = new Player(0,gc);
        List<Card> expResult = new ArrayList<Card>();
        List<Card> result = instance.getCards();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLogPointer method, of class Player.
     */
    @Test
    public void testGetLogPointer() {
        System.out.println("getLogPointer");
        Player instance = new Player(0,gc);
        int expResult = 0;
        int result = instance.getLogPointer();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLogPointer method, of class Player.
     */
    @Test
    public void testSetLogPointer() {
        System.out.println("setLogPointer");
        int pointer = 10;
        Player instance = new Player(0,gc);
        instance.setLogPointer(pointer);
        assertEquals(pointer,instance.getLogPointer());
    }

    /**
     * Test of getNotes and setNotes method, of class Player.
     */
    @Test
    public void testNotes() {
        System.out.println("getNotes");
        Player instance = new Player(0,gc);
        assertEquals("",instance.getNotes());
        String expResult = "abcd";
        instance.setNotes(expResult);
        String result = instance.getNotes();
        assertEquals(expResult, result);
    }

}

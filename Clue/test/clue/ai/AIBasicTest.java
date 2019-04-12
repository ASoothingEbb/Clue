/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.ai;

import clue.GameController;
import clue.MissingRoomDuringCreationException;
import clue.action.UnknownActionException;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.TileOccupiedException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zemig
 */
public class AIBasicTest {
    GameController dummyGame;
    
    public AIBasicTest() throws InterruptedException, UnknownActionException, TileOccupiedException, MissingRoomDuringCreationException, NoSuchRoomException, NoSuchTileException, GameController.TooManyPlayersException {
        dummyGame = new GameController(0, 6, "", "");
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
     * Test of onUpdate method, of class AiBasic.
     */
    @Test
    public void testOnUpdate() {
        System.out.println("onUpdate");
        AiBasic instance = new AiBasic(1, dummyGame);
        instance.onUpdate();
    }
    
    @Test
    public void testConstructor(){
        AiBasic aItest = new AiBasic(1, dummyGame);
        int expInt = 1;
        int id = aItest.getId();
        assertEquals(id, expInt);
    }
    
}

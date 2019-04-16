/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.player.Player;
import clue.tile.Tile;
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
public class MoveActionTest {
    
    public MoveActionTest() {
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
     * Test of execute method, of class MoveAction.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        Player player = new Player(0);
        Tile s = new Tile(0,0);
        player.setPosition(s);
        Tile t = new Tile(0,1);
        player.setMoves(1);
        s.addAdjacent(t);
        MoveAction instance = new MoveAction(player,t,10,10);
        instance.execute();
        assertTrue(instance.result);
    }

    /**
     * Test of getTile method, of class MoveAction.
     */
    @Test
    public void testGetTile() {
        System.out.println("getTile");
        Tile expResult = new Tile(0,1);
        MoveAction instance = new MoveAction(new Player(0),expResult,10,10);
        Tile result = instance.getTile();
        assertEquals(expResult, result);
    }
    
}

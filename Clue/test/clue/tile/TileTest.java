/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

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
public class TileTest {
    
    public TileTest() {
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
     * Test of isAdjacent method, of class Tile.
     */
    @Test
    public void testIsAdjacent() {
        System.out.println("isAdjacent");
        Tile tileA = new Tile(0,0);
        Tile tileB = new Tile(0,1);
        Tile tileC = new Tile(1,0);
        
        ArrayList<Tile> adjacencyListForTileA = new ArrayList<>();
        adjacencyListForTileA.add(tileB);
        tileA.setAdjacent(adjacencyListForTileA);
        
        assertEquals(true, tileA.isAdjacent(tileB));
        assertEquals(false, tileA.isAdjacent(tileC));
        
    }

    /**
     * Test of setAdjacent method, of class Tile.
     */
    @Test
    public void testSetAdjacent() {
        System.out.println("setAdjacent");
        List<Tile> adjacentTiles = new ArrayList();
        Tile instance = new Tile(0,0);
        Tile tileA = new Tile(0,1);
        adjacentTiles.add(tileA);
        instance.setAdjacent(adjacentTiles);
        assertEquals(true,instance.isAdjacent(tileA));
    }

    /**
     * Test of isRoom method, of class Tile.
     */
    @Test
    public void testIsRoom() {
        System.out.println("isRoom");
        Tile instance = new Tile(0,0);
        boolean expResult = false;
        boolean result = instance.isRoom();
        assertEquals(expResult, result);
    }
    
}

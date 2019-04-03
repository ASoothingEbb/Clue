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
        Tile tileA = new Tile();
        Tile tileB = new Tile();
        Tile tileC = new Tile();
        
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
        List<Tile> adjacentTiles = null;
        Tile instance = new Tile();
        instance.setAdjacent(adjacentTiles);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

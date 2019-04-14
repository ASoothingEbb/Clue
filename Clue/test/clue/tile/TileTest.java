/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

import clue.card.RoomCard;
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
     * Test of isRoom method, of class Tile.
     */
    @Test
    public void testIsRoom() {
        System.out.println("isRoom");
        
        Tile t = new Tile(0,0);
        assertFalse(t.isRoom());
        
        Room r = new Room(new RoomCard(1));
        assertTrue(r.isRoom());
    }

    /**
     * Test of isPlaceholder method, of class Tile.
     */
    @Test
    public void testIsPlaceholder() {
        System.out.println("isPlaceholder");
        Tile t = new Tile();
        assertTrue(t.isPlaceholder());
    }

    /**
     * Test of isAdjacent and addAdjacent methods, of class Tile.
     */
    @Test
    public void testIsAddAdjacent() {
        System.out.println("isAdjacent/addAdjacent");
        Tile t1 = new Tile();
        Tile t2 = new Tile();
        Tile t3 = new Tile();
        
        t1.addAdjacent(t2);
        
        assertTrue(t1.isAdjacent(t2));
        assertFalse(t2.isAdjacent(t1));//addAdjacent only adds the adjacency one way
        
        //t3 should be in no adjacencies
        assertFalse(t3.isAdjacent(t1) || t1.isAdjacent(t3));
        assertFalse(t3.isAdjacent(t2) || t2.isAdjacent(t3));
        
    }
    /**
     * Test of testGetAdjacent method, of class Tile.
     */   
    @Test
    public void testGetAdjacent() {
        System.out.println("isAdjacent/addAdjacent/GetAdjacent");
        Tile t1 = new Tile();
        Tile t2 = new Tile();
        Tile t3 = new Tile();
        
        t1.addAdjacent(t2);
        
        assertTrue(t1.getAdjacent().contains(t2));
        assertFalse(t2.getAdjacent().contains(t1));
        assertFalse(t3.getAdjacent().contains(t1) || t3.getAdjacent().contains(t2));
        
        assertEquals(1,t1.getAdjacent().size());
        assertEquals(0,t2.getAdjacent().size());
        assertEquals(0,t3.getAdjacent().size());
        
    }  
    


    /**
     * Test of isFull method, of class Tile.
     */
    @Test
    public void testIsFull() {
        System.out.println("isFull");
        
        
        Room r = new Room(new RoomCard(1));
        Tile emptyTile = new Tile(0,0);
        Tile occupiedTile = new Tile(0,0);
        occupiedTile.setOccupied(true);
        r.setOccupied(true);
        
        assertTrue(occupiedTile.isFull());
        assertFalse(r.isFull());
        assertFalse(emptyTile.isFull());
    }

    /**
     * Test of setOccupied method, of class Tile.
     */
    @Test
    public void testSetOccupied() {
        System.out.println("setOccupied");
        Tile t1 = new Tile(0,0);
        t1.setOccupied(true);
        assertTrue(t1.isFull());
        t1.setOccupied(false);
        assertFalse(t1.isFull());
          
    }

    /**
     * Test of addAdjacentBoth method, of class Tile.
     */
    @Test
    public void testAddAdjacentBoth() {
        System.out.println("addAdjacentBoth");

        Tile t1 = new Tile();
        Tile t2 = new Tile();
        Tile t3 = new Tile();
        
        t1.addAdjacentBoth(t2);
        
        assertTrue(t1.getAdjacent().contains(t2));
        assertTrue(t2.getAdjacent().contains(t1));
        assertFalse(t3.getAdjacent().contains(t1) || t3.getAdjacent().contains(t2));
        
        assertEquals(1,t1.getAdjacent().size());
        assertEquals(1,t2.getAdjacent().size());
        assertEquals(0,t3.getAdjacent().size());
        
        t1.addAdjacentBoth(t3);
        assertEquals(2,t1.getAdjacent().size());
        assertTrue(t3.getAdjacent().contains(t1));
    }

    /**
     * Test of getX method, of class Tile.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Tile t1 = new Tile(0,0);
        assertEquals(0, t1.getX());
        
        Tile t2 = new Tile(3,7);
        assertEquals(3, t2.getX());
        
        Room r1 = new Room(new RoomCard(5));
        assertEquals(-1, r1.getX());
    }

    /**
     * Test of getY method, of class Tile.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Tile t1 = new Tile(0,0);
        assertEquals(0, t1.getY());
        
        Tile t2 = new Tile(3,7);
        assertEquals(7, t2.getY());
        
        Room r1 = new Room(new RoomCard(5));
        assertEquals(5, r1.getY());
    }


    /**
     * Test of isSpecial method, of class Tile.
     */
    @Test
    public void testIsSpecial() {
        System.out.println("isSpecial");
        
        Tile t = new Tile(0,0);
        assertFalse(t.isSpecial());
        
        Room r = new Room(new RoomCard(5));
        assertFalse(r.isSpecial());
        
        SpecialTile st = new SpecialTile(0,0);
        assertTrue(st.isSpecial());

    }

    /**
     * Test of isAdjacent method, of class Tile.
     */
    @Test
    public void testIsAdjacent() {
        System.out.println("isAdjacent");
        Tile tile = null;
        Tile instance = new Tile();
        boolean expResult = false;
        boolean result = instance.isAdjacent(tile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAdjacent method, of class Tile.
     */
    @Test
    public void testAddAdjacent() {
        System.out.println("addAdjacent");
        Tile adjacentTile = null;
        Tile instance = new Tile();
        instance.addAdjacent(adjacentTile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Tile.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Tile instance = new Tile();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

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
public class DoorTest {
    
    public DoorTest() {
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
     * Test of getRoomY method, of class Door.
     */
    @Test
    public void testGetRoomY() {
        System.out.println("getRoomY");
        Door instance = null;
        int expResult = 0;
        int result = instance.getRoomY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTileX method, of class Door.
     */
    @Test
    public void testGetTileX() {
        System.out.println("getTileX");
        Door instance = null;
        int expResult = 0;
        int result = instance.getTileX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTileY method, of class Door.
     */
    @Test
    public void testGetTileY() {
        System.out.println("getTileY");
        Door instance = null;
        int expResult = 0;
        int result = instance.getTileY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

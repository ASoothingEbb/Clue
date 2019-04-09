/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

import clue.NoSuchRoomException;
import clue.card.RoomCard;
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
public class RoomTest {
    
    public RoomTest() {
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

    @Test
    public void testIsRoom() {
        Room room = new Room(new RoomCard(1));
        assertEquals(true, room.isRoom());
        
    }
    
    @Test
    public void testGetRoom(){
        RoomCard roomCard1 = new RoomCard(1);
        assertEquals(roomCard1, new Room(roomCard1));
    
    }

    /**
     * Test of getCard method, of class Room.
     */
    @Test
    public void testGetCard() throws NoSuchRoomException {
        System.out.println("getCard");
        Room instance = null;
        RoomCard expResult = null;
        RoomCard result = instance.getCard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

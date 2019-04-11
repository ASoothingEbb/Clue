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
    /**
     * Test of isRoom method, of class Room.
     */
    @Test
    public void testIsRoom() {
        System.out.println("isRoom");
        Room room = new Room(new RoomCard(1));
        assertEquals(true, room.isRoom());
        
    }
    
 

    /**
     * Test of getCard method, of class Room.
     */
    @Test
    public void testGetCard() throws NoSuchRoomException {
        System.out.println("getCard");        
        RoomCard roomCard1 = new RoomCard(1);
        Room r = new Room(roomCard1);
        assertEquals(roomCard1, r.getCard());
    }

    /**
     * Test of addLocation method, of class Room.
     */
    @Test
    public void testAddGetLocation() {
        System.out.println("add/getLocation");
        int x = 0;
        int y = 0;
        
        int x2 = 5;
        int y2 = 7;
        
        RoomCard rc = new RoomCard(1);
        Room r = new Room(rc);
        r.addLocation(x, y);
        r.addLocation(x2,y2);
        
        ArrayList<int[]> locations = r.getLocations();
        assertEquals(2,locations.size());
        int[] loc1 = locations.get(0);
        int[] loc2 = locations.get(1);
        
        
        assertTrue(
            loc1[0] == x && loc1[1] == y
            &&
            loc2[0] == x2 && loc2[1] == y2
            ||//locations is not specifically ordered, so loc1 may not be at index 0, the or checks if the ordering is swapped
            loc2[0] == x && loc2[1] == y
            &&
            loc1[0] == x2 && loc1[1] == y2      
        );
        
    }

    /**
     * Test of getId method, of class Room.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        
        Room r = new Room();
        assertEquals(-1, r.getId());
        
        Room r2 = new Room(new RoomCard(5));
        assertEquals(5, r2.getId());
        
        Room r3 = new Room(new RoomCard(7));
        assertEquals(7, r3.getId());
    }
    
}

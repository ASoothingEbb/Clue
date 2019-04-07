/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.tile.Room;
import clue.tile.Tile;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Malter
 */
public class BoardMappingsTest {
    
    public BoardMappingsTest() {
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
    public void testCorrectStartingLocations() {
        try {
            BoardMappings boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv");
            
            //"testCsv/tiles1.csv" contains two starting locations at 5,6 and 1,7
            assertEquals(5,boardMappings.getStartingTiles().get(0).getX());
            assertEquals(6,boardMappings.getStartingTiles().get(0).getY());
            
            assertEquals(1,boardMappings.getStartingTiles().get(1).getX());
            assertEquals(7,boardMappings.getStartingTiles().get(1).getY());
            
            
        } catch (Exception e){
            System.out.println(e);
            fail();     
        }
    }
    
    @Test
    public void testAddDoorsToTileAdjacencies(){
        try {
            BoardMappings boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv");
            
            //"testCsv/tiles1.csv", "testCsv/doors1.csv" files contains a board with 2 rooms and 2 doorways,
            //doorway one goes from coord 3,1 to room 1
            //doorway two goes from coord 4,4 to room 2
            Room r1 = (Room) boardMappings.getTile(-1, 1);
            Room r2 = (Room) boardMappings.getTile(-1, 2);
            
            Tile t1 = boardMappings.getTile(3,1);
            Tile t2 = boardMappings.getTile(4,4);
            
            assertTrue(r1.isAdjacent(t1));
            assertTrue(t1.isAdjacent(r1));
            
            assertTrue(r2.isAdjacent(t2));
            assertTrue(t2.isAdjacent(r2));
        } catch (Exception ex) {
            System.out.println(ex);
            fail(); 
        }

    }
    @Test
    public void testAddShortcut(){
        try {
            BoardMappings boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv");
            
            Room r1 = (Room) boardMappings.getTile(-1, 1);
            Room r2 = (Room) boardMappings.getTile(-1, 2);
            
            assertFalse(r1.isAdjacent(r2));
            assertFalse(r2.isAdjacent(r1));
            
            boardMappings.addShortcut(1,2);
            
            assertTrue(r1.isAdjacent(r2));
            assertTrue(r2.isAdjacent(r1));
            
        } catch (Exception ex) {
            System.out.println(ex);
            fail(); 
        }
    
    }
}

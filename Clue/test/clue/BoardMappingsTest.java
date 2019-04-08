/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.tile.Room;
import clue.tile.Tile;
import java.util.List;
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
 * @author mw434
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
        System.out.println("testCorrectStartingLocations");
        try {
            BoardMappings boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv");
  
            //"testCsv/tiles1.csv" contains two starting locations at 5,6 and 1,7
            List<Tile> startingLocations = boardMappings.getStartingTiles();
        
            assertTrue(startingLocations.contains(boardMappings.getTile(5,6)));
            assertTrue(startingLocations.contains(boardMappings.getTile(1,7)));
            
        } catch (NoSuchRoomException | NoSuchTileException ex) {
            System.out.println(ex);
            fail(); 
        }  
        
    }
    
    @Test
    public void testAddDoorsToTileAdjacencies(){
        System.out.println("testAddDoorsToTileAdjacencies");
        BoardMappings boardMappings = null;
        try {
            boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv");
         
            //"testCsv/tiles1.csv", "testCsv/doors1.csv" files contains a board with 2 rooms and 2 doorways,
            //doorway one goes from coord 3,1 to room 1
            //doorway two goes from coord 4,4 to room 2

            Room r1 = boardMappings.getRoom(1);
            Room r2 = boardMappings.getRoom(2);



            Tile t1 = boardMappings.getTile(3,1);
            Tile t2 = boardMappings.getTile(4,4);

            assertTrue(r1.isAdjacent(t1));
            assertTrue(t1.isAdjacent(r1));

            assertTrue(r2.isAdjacent(t2));
            assertTrue(t2.isAdjacent(r2));
        } catch (NoSuchRoomException | NoSuchTileException ex) {
            System.out.println(ex);
            fail(); 
        }   


    }
    @Test
    public void testAddShortcut(){
        System.out.println("testAddShortcut");
        BoardMappings boardMappings = null;
        try {
            boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv");
            
            Room r1 = boardMappings.getRoom(1);
            Room r2 = boardMappings.getRoom(2);

            
            assertFalse(r1.isAdjacent(r2));
            assertFalse(r2.isAdjacent(r1));
            
            boardMappings.addShortcut(r1.getY(),r2.getY());
            
            assertTrue(r1.isAdjacent(r2));
            assertTrue(r2.isAdjacent(r1));


            
        } catch (NoSuchRoomException | NoSuchTileException ex) {
            System.out.println(ex);
            fail(); 
        }    
    }
    
    @Test
    public void testLoadRooms(){
        System.out.println("testLoadRooms");
        try {
            BoardMappings boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv");
            
            Room[] rooms = boardMappings.loadRooms(9);
            
            for (int i = 0 ; i < 9; i++){
                assertTrue(rooms[i].getId() == i+1);
            }
            
            
            
        } catch (NoSuchRoomException | NoSuchTileException ex) {
            System.out.println(ex);
            fail(); 
        } 
        
    }
}

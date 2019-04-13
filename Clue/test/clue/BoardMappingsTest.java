/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.Door;
import clue.tile.Room;
import clue.tile.Tile;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public void testCorrectStartingLocationsNoIds() {
        System.out.println("testCorrectStartingLocationsNoIds");
        try {
            BoardMappings boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv",6,8);
  
            //"testCsv/tiles1.csv" contains two starting locations at 5,6 and 1,7
            List<Tile> startingLocations = boardMappings.getStartingTiles();
        
            assertTrue(startingLocations.contains(boardMappings.getTile(5,6)));
            assertTrue(startingLocations.contains(boardMappings.getTile(1,7)));
            
        } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
            System.out.println(ex);
            fail(); 
        }  
        
    }
    
    @Test
    public void testCorrectStartingLocationsWithIds() {
        System.out.println("testCorrectStartingLocationsWithIds");
        try {
            BoardMappings boardMappings = new BoardMappings("testCsv/tiles1WithIds.csv", "testCsv/doors1.csv",6,8);
  
            //"testCsv/tiles1.csv" contains two starting locations at 5,6 and 1,7
            LinkedList<Tile> startingLocations = boardMappings.getStartingTiles();
        
            assertTrue(startingLocations.poll() == boardMappings.getTile(5,6));
            assertTrue(startingLocations.poll() == boardMappings.getTile(1,7));
            assertTrue(startingLocations.poll() == boardMappings.getTile(5,0));
            
        } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
            System.out.println(ex);
            fail(); 
        }  
        
    }
    
    
    
    
    
    @Test
    public void testAddDoorsToTileAdjacencies(){
        System.out.println("testAddDoorsToTileAdjacencies");
        BoardMappings boardMappings = null;
        try {
            boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv",6,8);
         
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
        } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
            System.out.println(ex);
            fail(); 
        }   


    }
    @Test
    public void testAddShortcut(){
        System.out.println("testAddShortcut");
        BoardMappings boardMappings = null;
        try {
            boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv",6,8);
            
            Room r1 = boardMappings.getRoom(1);
            Room r2 = boardMappings.getRoom(2);

            
            assertFalse(r1.isAdjacent(r2));
            assertFalse(r2.isAdjacent(r1));
            
            boardMappings.addShortcut(r1.getY(),r2.getY());
            
            assertTrue(r1.isAdjacent(r2));
            assertTrue(r2.isAdjacent(r1));


            
        } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
            System.out.println(ex);
            fail(); 
        }    
    }
    
    @Test
    public void testLoadRooms(){
        System.out.println("testLoadRooms");
        try {
            BoardMappings boardMappings = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv",6,8);
            
            Room[] rooms = boardMappings.loadRooms(9);
            
            assertEquals(9, rooms.length);
            for (int i = 0 ; i < 9; i++){
                assertTrue(rooms[i].getId() == i+1);
            }
            
            
            
        } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
            System.out.println(ex);
            fail(); 
        } 
        
    }
    
    @Test
    public void testLoadCsv2D(){
        System.out.println("testLoadCsv2D");
        try {
            BoardMappings boardMappings = new BoardMappings("testCsv/shortTiles.csv", "testCsv/shortDoors.csv",4,5);
            
            ArrayList<ArrayList<String>> out = boardMappings.loadCsv2D("testCsv/shortTiles.csv");
            
            assertTrue(out.get(0).get(0).equals("S"));
            assertTrue(out.get(0).get(1).equals("0"));
            assertTrue(out.get(0).get(2).equals(""));
            assertTrue(out.get(0).get(3).equals("0"));
            
            assertTrue(out.get(1).get(0).equals("0"));
            assertTrue(out.get(1).get(1).equals("0"));
            assertTrue(out.get(1).get(2).equals(""));
            assertTrue(out.get(1).get(3).equals("0"));
            
            assertTrue(out.get(2).get(0).equals("0"));
            assertTrue(out.get(2).get(1).equals("1"));
            assertTrue(out.get(2).get(2).equals("1"));
            assertTrue(out.get(2).get(3).equals("1"));
            
            
            assertTrue(out.get(3).get(0).equals("0"));
            assertTrue(out.get(3).get(1).equals(""));
            assertTrue(out.get(3).get(2).equals(""));
            assertTrue(out.get(3).get(3).equals("0"));
            
            assertTrue(out.get(4).get(0).equals("I"));            
            assertTrue(out.get(4).get(1).equals("0"));
            assertTrue(out.get(4).get(2).equals("0"));
            assertTrue(out.get(4).get(3).equals("2"));
            
        } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
            System.out.println(ex);
            fail(); 
        }
        
        
    
    
    }
    
    @Test
    public void testLoadCsvDoors(){
        System.out.println("testLoadCsvDoors");
        try {
            BoardMappings boardMappings = new BoardMappings("testCsv/shortTiles.csv", "testCsv/shortDoors.csv",4,5);
            
            //shortDoors contains two doors: room 1 to 0,2 and room 2 to 2,4
            ArrayList<Door> loadedDoors = boardMappings.loadCsvDoors("testCsv/shortDoors.csv");
            
            assertEquals(loadedDoors.size(), 2);
            
            assertEquals(1,loadedDoors.get(0).getRoomY());
            assertEquals(2,loadedDoors.get(0).getTileY());
            assertEquals(0,loadedDoors.get(0).getTileX());
                
            assertEquals(2,loadedDoors.get(1).getRoomY());
            assertEquals(4,loadedDoors.get(1).getTileY());
            assertEquals(2,loadedDoors.get(1).getTileX());
                    
                    
                    
                    
                    
        } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
            System.out.println(ex);
            fail(); 
        }
        
    
    }
    
        @Test
        public void testGetTile(){
    
            System.out.println("testGetTile");
            try {
                BoardMappings boardMappings = new BoardMappings("testCsv/shortTiles.csv", "testCsv/shortDoors.csv",4,5);

                for (int x =0 ; x < 4; x++){
                    for (int y = 0; y < 5; y++){
                        if (x == 2 && y == 0 || x == 2 && y == 1 || x == 1 && y == 3 || x == 2 && y == 3){//empty tiles should be at these locations

                            
                            assertTrue(boardMappings.getTile(x, y).getX() == -1 && boardMappings.getTile(x, y).getY() == -1);
   
                        }
                        else if (x == 1 && y == 2 || x == 2 && y == 2 || x == 3 && y == 2 || x == 3 && y == 4){//rooms should be at these locations
                            if (y == 4){//room 2 at y ==4 for above
                                assertEquals(2,boardMappings.getTile(x, y).getY());
                            }
                            else{//room 1 at all rest of room locations
                                assertEquals(1,boardMappings.getTile(x, y).getY());
                            }
                            
                        }                                                
                        else{//non room / non empty tiles
                            assertTrue(boardMappings.getTile(x, y).getX() == x && boardMappings.getTile(x, y).getY() == y);
                        }
                        
                    }
                }
                
                
                

            } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
                fail(); 
            }
        }
        
        @Test
        public void testGetRoom(){
            System.out.println("testGetRoom");
            try {
                BoardMappings boardMappings = new BoardMappings("testCsv/shortTiles.csv", "testCsv/shortDoors.csv",4,5);

                ArrayList<Room> rooms = new ArrayList<>();
                
                rooms.add(boardMappings.getRoom(1));
                rooms.add(boardMappings.getRoom(2));
      

            } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
                System.out.println(ex);
                fail(); 
            }
        
        
        }
        
        @Test
        public void testGetRoomCount(){
            System.out.println("testGetRoomCount");
            
            try {
                BoardMappings boardMappings1 = new BoardMappings("testCsv/shortTiles.csv", "testCsv/shortDoors.csv",4,5);
                BoardMappings boardMappings2 = new BoardMappings("testCsv/tiles1.csv", "testCsv/doors1.csv",6,8);
                BoardMappings boardMappings3 = new BoardMappings("testCsv/shortTiles2.csv", "testCsv/shortDoors2.csv",4,5);

                assertEquals(2,boardMappings1.getRoomCount(boardMappings1.loadCsv2D("testCsv/shortTiles.csv")));
                assertEquals(2,boardMappings2.getRoomCount(boardMappings2.loadCsv2D("testCsv/tiles1.csv")));
                assertEquals(4,boardMappings3.getRoomCount(boardMappings3.loadCsv2D("testCsv/shortTiles2.csv")));
                
 

            } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
                System.out.println(ex);
                fail(); 
            }
            
            
            
        }
        
        
        
        @Test
        public void testCreateTileMappings(){
            System.out.println("testCreateTileMappings TODO");
        
            
            
                        
            try {
                BoardMappings boardMappings = new BoardMappings("testCsv/shortTiles.csv", "testCsv/shortDoors.csv",4,5);

                ArrayList<ArrayList<String>> tiles = boardMappings.loadCsv2D("testCsv/shortTiles.csv");
                //boardMappings.createTileMappings(tiles, boardMappings.getRoomCount(tiles));
                
                
                Tile t00 = boardMappings.getTile(0,0);
                Tile t10 = boardMappings.getTile(1,0);
                Tile t20 = boardMappings.getTile(2,0);
                Tile t30 = boardMappings.getTile(3,0);
                
                Tile t01 = boardMappings.getTile(0,1);
                Tile t11 = boardMappings.getTile(1,1);
                Tile t21 = boardMappings.getTile(2,1);
                Tile t31 = boardMappings.getTile(3,1);
                
                
                Tile t02 = boardMappings.getTile(0,2);
                Tile t12 = boardMappings.getTile(1,2);
                Tile t22 = boardMappings.getTile(2,2);
                Tile t32 = boardMappings.getTile(3,2);
                
                Tile t03 = boardMappings.getTile(0,3);
                Tile t13 = boardMappings.getTile(1,3);
                Tile t23 = boardMappings.getTile(2,3);
                Tile t33 = boardMappings.getTile(3,3);
                
                Tile t04 = boardMappings.getTile(0,4);
                Tile t14 = boardMappings.getTile(1,4);
                Tile t24 = boardMappings.getTile(2,4);
                Tile t34 = boardMappings.getTile(3,4);
                
                
                //row 0
                assertEquals(2,t00.getAdjacent().size());
                assertTrue(t00.isAdjacent(t01));
                assertTrue(t00.isAdjacent(t10));
                
                assertEquals(2,t10.getAdjacent().size());
                assertTrue(t10.isAdjacent(t00));
                assertTrue(t10.isAdjacent(t11));
                
                assertTrue(t20.getAdjacent().isEmpty());
                assertTrue(t30.getAdjacent().size() == 1);
                assertTrue(t30.isAdjacent(t31));
                
                
                
                //row 1
                
                assertEquals(3, t01.getAdjacent().size());
                assertTrue(t01.isAdjacent(t00));
                assertTrue(t01.isAdjacent(t11));
                assertTrue(t01.isAdjacent(t02));
                
                
                assertEquals(2, t11.getAdjacent().size());
                assertTrue(t11.isAdjacent(t10));
                assertTrue(t11.isAdjacent(t01));
                
                assertEquals(0, t21.getAdjacent().size());

                assertEquals(1, t31.getAdjacent().size());
                assertTrue(t31.isAdjacent(t30));
                
                
                //row 2
                
                assertEquals(3, t02.getAdjacent().size());
                assertTrue(t02.isAdjacent(t01));
                assertTrue(t02.isAdjacent(t03));
                assertTrue(t02.isAdjacent(t12));
                
                
                assertTrue(t12.isRoom() && t22.isRoom() && t32.isRoom());//room 1
                assertTrue(t12 == t22 && t12 == t32);//should all be the same Room tile
                assertEquals(1, t12.getAdjacent().size());
                assertTrue(t12.isAdjacent(t02));//should have a door leading to this tile
                
                //row 3
                
                
                assertEquals(2, t03.getAdjacent().size());
                
                assertEquals(0,t13.getAdjacent().size());
                
                assertEquals(0,t23.getAdjacent().size());//empty tile
                
                assertEquals(0,t33.getAdjacent().size());//unreachable tile
                
                //row 4
                
                assertEquals(2, t04.getAdjacent().size());
                assertTrue(t04.isAdjacent(t03));
                assertTrue(t04.isAdjacent(t14));
                
                assertEquals(2, t14.getAdjacent().size());
                assertTrue(t14.isAdjacent(t04));
                assertTrue(t14.isAdjacent(t24));
                
                assertEquals(2, t24.getAdjacent().size());
                assertTrue(t24.isAdjacent(t14));
                assertTrue(t24.isAdjacent(t34));
                
                assertEquals(1, t34.getAdjacent().size());
                assertTrue(t34.isRoom());
                assertTrue(t34.isAdjacent(t24));
                

            } catch (NoSuchRoomException | NoSuchTileException | MissingRoomDuringCreationException ex) {
                System.out.println(ex);
                fail(); 
            }
        }
        
  
}

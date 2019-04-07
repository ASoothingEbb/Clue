/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.card.RoomCard;
import clue.tile.Room;
import clue.tile.SpecialTile;
import clue.tile.Tile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Malter
 */
public class BoardMappings {
    
    public static void main(String[] args){//TODO:              delete me final submission
        System.out.println("[BoardMappings.main (temp, delete main later)] running example1");
        try {
            BoardMappings boardMappings = new BoardMappings("tiles.csv", "doors.csv");
        } catch (NoSuchRoomException ex) {
            System.out.println("oof");
            Logger.getLogger(BoardMappings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchTileException ex) {
            System.out.println("oof2");
            Logger.getLogger(BoardMappings.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    Tile[][] mappings;
    Room[] rooms;
    int boardWidth;
    int boardHeight;
    
    public BoardMappings(String tileRoomLayoutPath, String doorLocationsPath) throws NoSuchRoomException, NoSuchTileException{
        boardWidth = 6;//24
        boardHeight = 8;//25
        int roomCount = 9;
        
        ArrayList<ArrayList<String>> tiles = loadCsvTileLocations(tileRoomLayoutPath);
        rooms = loadRooms(roomCount);
        mappings = createTileMappings(tiles, roomCount);

        List<Door> doorLocations = loadCsvDoors(doorLocationsPath);
        addDoorsToTileAdjacencies(doorLocations);
        


        
        //----------------debugging prints below--------------------------//
        //for (Tile[] tArr : mappings){        
        //    for (Tile t : tArr){
        //        if (t != null){
        //            System.out.print((t.getX()+1)+","+(t.getY()+1)+": ");
        //        }
        //        else{continue;}
        //        for (Tile aj : t.getAdjacent()){
        //            if (aj != null){   
        //                System.out.print((aj.getX()+1)+","+(aj.getY()+1)+","); 
        //            }
        //        }   
        //        System.out.println();
        //    }
        //}
        
        //System.out.println("Rooms");
        //for (Tile t : rooms){        
        //    System.out.print("id_"+(t.getY())+": ");
        //    for (Tile aj : t.getAdjacent()){
        //        System.out.print((aj.getX())+","+(aj.getY()));
        //    }
        //    System.out.println();
        //}  
    }
    
    private ArrayList<ArrayList<String>> loadCsvTileLocations(String path){
        
        ArrayList<ArrayList<String>> csvData = new ArrayList<>();
        try{
            
            BufferedReader br = new BufferedReader(new FileReader(path));//open file      
            ArrayList<String[]> lines = new ArrayList<>();            
            String thisLine;
            
            while((thisLine = br.readLine()) != null){//read all lines from csv
                lines.add(thisLine.split(","));
            }
            
            ArrayList<String> rowBuffer;
            for (String[] row : lines){//store ourput from csv into 2d arraylist
                rowBuffer = new ArrayList<>();
                for (String cell : row){                  
                    rowBuffer.add(cell);
                }
                csvData.add(rowBuffer);
            }    
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        } 
        catch (IOException ex) { 
            System.out.println(ex);
        }
        return csvData;
    }
    
    public final Tile getTile(int x, int y) throws NoSuchRoomException{
        if (x >= 0 && x < mappings[0].length && y >= 0 && y < mappings.length){//trying to get a non room tile when x >=0
            
            return mappings[y][x];
        }
        else if ( x == -1){//trying to get a room tile
            if (y <= rooms.length && y > 0){
                return rooms[y-1];//room with id n is stored at index n-1
            }
            else{
                throw new NoSuchRoomException();
            }
            
        }
        else{
            throw new ArrayIndexOutOfBoundsException("Tile.getTile was called with an illegal x,y");
        }
    }

    private List<Door> loadCsvDoors(String path) {
        ArrayList<Door> doors = new ArrayList<>();
        ArrayList<ArrayList<String>> csvData = new ArrayList<>();
        try{
            
            BufferedReader br = new BufferedReader(new FileReader(path));//open file      
            ArrayList<String[]> lines = new ArrayList<>();            
            String thisLine;
            
            while((thisLine = br.readLine()) != null){//read all lines from csv
                lines.add(thisLine.split(","));
            }
            
            ArrayList<String> rowBuffer;
            for (String[] row : lines){//store ourput from csv into 2d arraylist
                rowBuffer = new ArrayList<>();
                for (String cell : row){                  
                    rowBuffer.add(cell);
                }
                csvData.add(rowBuffer);
            }    
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        } 
        catch (IOException ex) { 
            System.out.println(ex);
        }
        
        Door door;
        for (ArrayList<String> row : csvData){
            door = new Door(Integer.parseInt(row.get(0).replaceAll("[^0-9]+", "")), Integer.parseInt(row.get(1).replaceAll("[^0-9]+", "")), Integer.parseInt(row.get(2).replaceAll("[^0-9]+", "")));
            doors.add(door);
        }
        return doors;
    }

    private Room[] loadRooms(int roomCount) {
        Room [] loadedRooms = new Room[roomCount];
        for (int i = 0; i < roomCount; i++){
            loadedRooms[i] = new Room(new RoomCard(i+1));//room n is stored at index n-1
        }
        return loadedRooms;
    }

    private Tile[][] createTileMappings(ArrayList<ArrayList<String>> tiles, int roomCount) throws NoSuchRoomException {
        Tile[][] localMappings = new Tile[boardHeight][boardWidth];      
        String cell = null;      
        for (int y = 0; y < boardHeight; y++){//create Tile objects in mappings, object may be tile,room,special or null depending on what the csv cell was
            for (int x = 0; x < boardWidth; x++){
                
                
                cell = tiles.get(y).get(x);
                //System.out.println(""+x+","+y+": "+cell);
                if (cell.equals(-1)){
                    localMappings[y][x] = null;
                    
                }
                else if (cell.equals("0")){
                    localMappings[y][x] = new Tile(x,y);
                }
                else if (cell.equals("0*")){
                    localMappings[y][x] = new SpecialTile(x,y);
                    tiles.get(y).set(x, "0");
                }
                else{
                    localMappings[y][x] = null;
                    cell = cell.replaceAll("[^0-9]+", "");
                    //System.out.println(Integer.parseInt(cell));
                    if (Integer.parseInt(cell) > roomCount){
                        throw new NoSuchRoomException();
                    }
                }
   
            }
        }
        
        Tile currentTile = null;
        for (int y = 0; y < boardHeight; y++){
            for (int x = 0; x < boardWidth; x++){
                
                currentTile = localMappings[y][x];
                //if (currentTile != null){
                    //System.out.println(currentTile.getX()+","+currentTile.getY()+","+x+","+y);
                //}
                if (tiles.get(y).get(x).equals("0")){//if current tile is a standard tile
                    if (currentTile != null){//should allways be non null
                        //System.out.println(currentTile != null);
                        if (x>0){//there is a left tile
                            if (tiles.get(y).get(x-1).equals("0")){ //if tile to the left is also a standard tile
                                currentTile.addAdjacent(localMappings[y][x-1]);
                            }
                        }    
                        if (x<boardWidth-1){//if there is a right tile
                            if (tiles.get(y).get(x+1).equals("0")){ ///if tile to the right is also a standard tile
                                currentTile.addAdjacent(localMappings[y][x+1]);
                            }
                        }
                        if (y>0){//if there is a above tile
                            if (tiles.get(y-1).get(x).equals("0")){ //if tile above is also a standard tile
                                currentTile.addAdjacent(localMappings[y-1][x]);
                            }
                        }
                        if (y<boardHeight-1){//if there is a tile below
                            if (tiles.get(y+1).get(x).equals("0")){ //if tile below is also a standard tile
                                currentTile.addAdjacent(localMappings[y+1][x]);
                            }
                        }    
                    }
                    else{throw new NullPointerException("-git blame @mw434 FATAL ERROR");}
 
                }   
            }
        }
        return localMappings;
    }

    private void addDoorsToTileAdjacencies(List<Door> doorLocations) throws NoSuchRoomException, NoSuchTileException {
        Tile outside;
        Tile room;
        for (Door door: doorLocations){
            
            try {
                room = getTile(-1, door.getRoomY());
                outside = getTile(door.getTileX(), door.getTileY());
                
                room.addAdjacentBoth(outside);
            } catch (NoSuchRoomException ex) {
                throw ex;
            }
            catch (NullPointerException ex){
                throw new NoSuchTileException("door csv file contains an illegal door, the outside tile index (1,2) must be the x y coordinates of a base tile or special tile");
            }
        }
    }
}

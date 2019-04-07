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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Malter
 */
public class BoardMappings {
    Tile[][] mappings;
    Room[] rooms;
    
    public BoardMappings(String tileRoomLayout) throws NoSuchRoomException{

        int boardWidth = 6;//24
        int boardHeight = 8;//25
        mappings = new Tile[boardHeight][boardWidth];
        ArrayList<ArrayList<String>> csvData = new ArrayList<>();
        try{
            
            BufferedReader br = new BufferedReader(new FileReader(tileRoomLayout));//open file      
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
        
        
        
        //for (Object o : csvData){
        //    System.out.println(o);
        //}

        String cell = null;
        int numberOfRooms = 9;
        rooms = new Room[numberOfRooms];
        for (int i = 0; i < numberOfRooms; i++){
            rooms[i] = new Room(new RoomCard(i+1));//room n is stored at index n-1
        }
       
        //System.out.println(boardWidth+".."+boardHeight);
        for (int y = 0; y < boardHeight; y++){//create Tile objects in mappings, object may be tile,room,special or null depending on what the csv cell was
            for (int x = 0; x < boardWidth; x++){
                
                
                cell = csvData.get(y).get(x);
                //System.out.println(""+x+","+y+": "+cell);
                if (cell.equals(-1)){
                    mappings[y][x] = null;
                    
                }
                else if (cell.equals("0")){
                    mappings[y][x] = new Tile(x,y);
                }
                else if (cell.equals("0*")){
                    mappings[y][x] = new SpecialTile(x,y);
                    csvData.get(y).set(x, "0");
                }
                else{
                    mappings[y][x] = null;
                    cell = cell.replaceAll("[^0-9]+", "");
                    //System.out.println(Integer.parseInt(cell));
                    if (Integer.parseInt(cell) > numberOfRooms){
                        throw new NoSuchRoomException();
                    }
                }
   
            }
        }
        
        Tile currentTile = null;
        for (int y = 0; y < boardHeight; y++){
            for (int x = 0; x < boardWidth; x++){
                
                currentTile = mappings[y][x];
                //if (currentTile != null){
                    //System.out.println(currentTile.getX()+","+currentTile.getY()+","+x+","+y);
                //}
                if (csvData.get(y).get(x).equals("0")){//if current tile is a standard tile
                    if (currentTile != null){//should allways be non null
                        //System.out.println(currentTile != null);
                        if (x>0){//there is a left tile
                            if (csvData.get(y).get(x-1).equals("0")){ //if tile to the left is also a standard tile
                                currentTile.addAdjacent(mappings[y][x-1]);
                            }
                        }    
                        if (x<boardWidth-1){//if there is a right tile
                            if (csvData.get(y).get(x+1).equals("0")){ ///if tile to the right is also a standard tile
                                currentTile.addAdjacent(mappings[y][x+1]);
                            }
                        }
                        if (y>0){//if there is a above tile
                            if (csvData.get(y-1).get(x).equals("0")){ //if tile above is also a standard tile
                                currentTile.addAdjacent(mappings[y-1][x]);
                            }
                        }
                        if (y<boardHeight-1){//if there is a tile below
                            if (csvData.get(y+1).get(x).equals("0")){ //if tile below is also a standard tile
                                currentTile.addAdjacent(mappings[y+1][x]);
                            }
                        }    
                    }
                    else{throw new NullPointerException("-git blame @mw434 FATAL ERROR");}
 
                }   
            }
        }
        
        //mappings[y][x] manually adding doors
        mappings[2][2].addAdjacentBoth(getTile(-1,1));
        mappings[4][4].addAdjacentBoth(getTile(-1,2));
        
        
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
        //    System.out.print("id_"+(t.getY()+1)+": ");
        //    for (Tile aj : t.getAdjacent()){
        //        System.out.print((aj.getX()+1)+","+(aj.getY()+1));
        //    }
        //    System.out.println();
        //}  
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
}

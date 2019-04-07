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
    
    public static void main(String[] args){
        BoardMappings bm = new BoardMappings("tiles.csv");
    
    }
    
    Tile[][] mappings;
    
    public BoardMappings(String tileRoomLayout){

        int boardWidth = 6;//24
        int boardHeight = 8;//25
        mappings = new Tile[boardWidth][boardHeight];
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
        ArrayList<Room> rooms = new ArrayList<>();
        for (int i = 0; i < numberOfRooms; i++){
            rooms.add(new Room(new RoomCard(i+1)));
        }
        for (int y = 0; y < boardHeight; y++){//create Tile objects in mappings, object may be tile,room,special or null depending on what the csv cell was
            for (int x = 0; x < boardWidth; x++){
                
                //System.out.println(""+x+","+y);
                cell = csvData.get(y).get(x);
                
                if (cell.equals(-1)){
                    mappings[y][x] = null;
                    
                }
                else if (cell.equals("0")){
                    mappings[y][x] = new Tile(x,y);
                }
                else if (cell.equals("0*")){
                    mappings[y][x] = new SpecialTile(null,x,y);//TODO: change when special tile is fixed
                }
                else{
                    mappings[y][x] = rooms.get(Integer.parseInt(cell));
                }
   
            }
        }
        
        Tile currentTile = null;
        for (int y = 0; y < boardHeight; y++){
            for (int x = 0; x < boardWidth; x++){
                
                currentTile = mappings[y][x];
                if (csvData.get(y).get(x).equals("0")){//if current tile is a standard tile
                    if (x>1){//there is a left tile
                        if (csvData.get(y).get(x-1).equals("0")){ //if tile to the left is also a standard tile
                            currentTile.addAdjacent(mappings[y][x-1]);
                        }
                    }    
                    if (x<boardWidth-1){//if there is a right tile
                        if (csvData.get(y).get(x+1).equals("0")){ ///if tile to the right is also a standard tile
                            currentTile.addAdjacent(mappings[y][x+1]);
                        }
                    }
                    if (y>1){//if there is a above tile
                        if (csvData.get(y-1).get(x).equals("0")){ //if tile above is also a standard tile
                            currentTile.addAdjacent(mappings[y-1][x]);
                        }
                    }
                    if (y<boardHeight){//if there is a tile below
                        if (csvData.get(y+1).get(x).equals("0")){ //if tile above is also a standard tile
                            currentTile.addAdjacent(mappings[y-1][x]);
                        }
                    }
                }   
            }
        }
        
        //mappings[y][x] manually adding doors
        mappings[2][2].addAdjacentBoth(mappings[3][2]);
        mappings[4][4].addAdjacentBoth(mappings[4][5]);
        
        
        for (Tile[] tArr : mappings){
        
            for (Tile t : tArr){
                System.out.print(t.getX()+","+t.getY()+": ");
                for (Tile aj : t.getAdjacent()){
                   System.out.print(t.getX()+","+t.getY()+","); 
                }   
                System.out.println();
            }
        }
        

    }
    
    
    public Tile getTile(int x, int y){
        return mappings[y][x];
    
    }
}

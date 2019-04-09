/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author hungb
 */
public class Room {
    
    private Tile[] roomTiles;
    private String roomName;
    private Image image;
    private ArrayList<Player> players;
    
    public Room(String name, Tile[] tiles, Image image) {
        this.roomName = name;
        this.roomTiles = tiles;
        this.image = image;
        this.players = new ArrayList<>();
    }
    
    public void enterRoom(Player player) {
        players.add(player);
    }
    
    public void leaveRoom(Player player) {
        players.remove(player);
    }    
}

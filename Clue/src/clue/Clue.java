/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.UnknownActionException;
import clue.player.Player;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.TileOccupiedException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author slb35
 */
public class Clue {

    /**
     * Initialise the main GameController instance with the GameState instance.
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws clue.action.UnknownActionException
     * @throws clue.tile.NoSuchRoomException
     * @throws clue.tile.NoSuchTileException
     * @throws clue.MissingRoomDuringCreationException
     * @throws clue.GameController.TooManyPlayersException
     */
    public static void main(String[] args) throws InterruptedException, UnknownActionException, NoSuchRoomException, NoSuchTileException, MissingRoomDuringCreationException, GameController.TooManyPlayersException, TileOccupiedException {
        // TODO code application logic here
        GameController game = new GameController(1,1,"testCsv/tiles1.csv", "testCsv/doors1.csv");
    }
    
}

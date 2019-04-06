/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.UnknownActionException;
import clue.player.AIPlayer;
import clue.player.Player;
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
     */
    public static void main(String[] args) throws InterruptedException, UnknownActionException {
        Player player = new AIPlayer(0);
        List<Player> players = new ArrayList();
        // TODO code application logic here
        GameController game = new GameController(players);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.player;

import clue.BoardMappings;
import clue.GameController;
import clue.tile.Tile;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Player being simulated by an AI
 * @deprecated use clue.ai.AiAdvanced instead. 
 * @author slb35
 */
public class AIPlayer extends Player {

    public AIPlayer(int id, GameController gc) {
        super(id, gc);
    }

    /**
     * @deprecated test purposes only
     * @param i 
     */
    public AIPlayer(int id) {
        super(id);
    }

}

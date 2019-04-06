/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

import clue.action.Action;
import clue.action.ActionType;
import clue.action.AvoidSuggestionAction;
import clue.action.ExtraTurnAction;
import clue.action.TeleportAction;
import clue.action.ThrowAgainAction;
import clue.player.AIPlayer;
import clue.player.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steve
 */
public class SpecialTileTest {
    
    public SpecialTileTest() {
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

    /**
     * Test of getIntrigue method, of class SpecialTile.
     */
    @Test
    public void testGetSpecial() throws Exception {
        Player p = new AIPlayer(1);
        
        SpecialTile t1 = new SpecialTile(ActionType.AVOIDSUGGESTIONCARD);
        assertTrue(t1.getIntrigue(p) instanceof AvoidSuggestionAction);
        
        SpecialTile t2 = new SpecialTile(ActionType.EXTRATURN);
        assertTrue(t2.getIntrigue(p) instanceof ExtraTurnAction);
        
        SpecialTile t3 = new SpecialTile(ActionType.TELEPORT);
        assertTrue(t3.getIntrigue(p) instanceof TeleportAction);
        
        SpecialTile t4 = new SpecialTile(ActionType.THROWAGAIN);
        assertTrue(t4.getIntrigue(p) instanceof ThrowAgainAction);
        
    }
    
}

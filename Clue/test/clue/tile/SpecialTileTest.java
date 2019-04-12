/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;


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
    public void testGetIntrigue() {
        System.out.println("getIntrigue");
        //Player player = new Player(0, new GameController());
        //SpecialTile sp = new SpecialTile(0,0);
        
        //int oldSize = player.getCards().size();
        
        //sp.getIntrigue(player);
        //assertEquals(oldSize+1,player.getCards().size());
        
        //assertTrue(player.getCards())
        fail();
    }
    
    @Test
    public void isSpecial(){
        System.out.println("isSpecial");
        SpecialTile sp = new SpecialTile(0,0);
        assertTrue(sp.isSpecial());
    }    
        
    
}

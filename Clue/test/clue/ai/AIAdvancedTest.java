/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.ai;

import clue.card.Card;
import clue.tile.Tile;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class AiAdvancedTest {
    
    public AiAdvancedTest() {
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
     * Test of onUpdate method, of class AiAdvanced.
     */
    @Test
    public void testOnUpdate() {
        System.out.println("onUpdate");
        AiAdvanced instance = null;
        instance.onUpdate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of BFS method, of class AiAdvanced.
     */
    @Test
    public void testBFS() {
        System.out.println("BFS");
        AiAdvanced instance = null;
        LinkedList<Tile> expResult = null;
        LinkedList<Tile> result = instance.BFS();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeLists method, of class AiAdvanced.
     */
    @Test
    public void testMakeLists() {
        System.out.println("makeLists");
        AiAdvanced instance = null;
        instance.makeLists();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveToRoom method, of class AiAdvanced.
     */
    @Test
    public void testMoveToRoom() {
        System.out.println("moveToRoom");
        AiAdvanced instance = null;
        instance.moveToRoom();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLists method, of class AiAdvanced.
     */
    @Test
    public void testGetLists() {
        System.out.println("getLists");
        AiAdvanced instance = null;
        ArrayList<ArrayList<Card>> expResult = null;
        ArrayList<ArrayList<Card>> result = instance.getLists();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPathToRoom method, of class AiAdvanced.
     */
    @Test
    public void testGetPathToRoom() {
        System.out.println("getPathToRoom");
        AiAdvanced instance = null;
        LinkedList<Tile> expResult = null;
        LinkedList<Tile> result = instance.getPathToRoom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

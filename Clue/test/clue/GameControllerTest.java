/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.action.Action;
import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
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
public class GameControllerTest {
    
    public GameControllerTest() {
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
     * Test of performAction method, of class GameController.
     */
    @Test
    public void testPerformAction() throws Exception {
        System.out.println("performAction");
        Action action = null;
        GameController instance = new GameController();
        instance.performAction(action);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of suggest method, of class GameController.
     */
    @Test
    public void testSuggest() {
        System.out.println("suggest");
        PersonCard person = null;
        RoomCard room = null;
        WeaponCard weapon = null;
        Player player = null;
        GameController instance = new GameController();
        Action expResult = null;
        Action result = instance.suggest(person, room, weapon, player);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

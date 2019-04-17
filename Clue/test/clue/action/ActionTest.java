/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.Card;
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
public class ActionTest {
    
    public ActionTest() {
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
     * Test of getPlayer method, of class Action.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        Player expResult = new Player(0);
        Action instance = new ActionImpl(expResult);
        Player result = instance.getPlayer();
        assertEquals(expResult, result);
    }

    /**
     * Test of execute method, of class Action.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        Action instance = new ActionImpl();
        instance.execute();
        assertTrue(instance.result);
    }

    public class ActionImpl extends Action {

        public ActionImpl() {
            super(null);
        }
        public ActionImpl(Player player){
            super(player);
        }
        public ActionImpl(Player player, Card card){
            super(player,card);
        }
    }

    /**
     * Test of toString method, of class Action.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Player player = new Player(0);
        Action instance = new ActionImpl(player);
        String expResult = ActionType.DEFAULT + "," + player.getId() + "," + instance.result;
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}

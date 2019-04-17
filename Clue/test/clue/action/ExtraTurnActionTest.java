/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.ExtraTurnIntrigue;
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
public class ExtraTurnActionTest {
    
    public ExtraTurnActionTest() {
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
     * Test of execute method, of class ExtraTurnAction.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        Player player = new Player(0);
        ExtraTurnIntrigue card = new ExtraTurnIntrigue(0);
        player.addIntrigue(card);
        ExtraTurnAction instance = new ExtraTurnAction(player, card);
        instance.execute();
        assertFalse(player.hasIntrigue(card));
        assertTrue(instance.result);
    }

    
}

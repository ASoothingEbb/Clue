/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.Card;
import clue.card.CardTest.CardImpl;
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
public class ShowCardActionTest {
    
    public ShowCardActionTest() {
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
     * Test of execute method, of class ShowCardAction.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        ShowCardAction instance = new ShowCardAction(new Player(0),new CardImpl());
        instance.execute();
        assertTrue(instance.result);
    }
    
    public class CardImpl extends Card{

        public CardImpl() {
            super(0);
        }
    
}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.Card;
import clue.player.Player;
import java.util.List;
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
public class ShowCardsActionTest {
    
    public ShowCardsActionTest() {
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
     * Test of execute method, of class ShowCardsAction.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        ShowCardsAction instance = null;
        instance.execute();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCardList method, of class ShowCardsAction.
     */
    @Test
    public void testGetCardList() {
        System.out.println("getCardList");
        ShowCardsAction instance = null;
        List<Card> expResult = null;
        List<Card> result = instance.getCardList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuggester method, of class ShowCardsAction.
     */
    @Test
    public void testGetSuggester() {
        System.out.println("getSuggester");
        ShowCardsAction instance = null;
        Player expResult = null;
        Player result = instance.getSuggester();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

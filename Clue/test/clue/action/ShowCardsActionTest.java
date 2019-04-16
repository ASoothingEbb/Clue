/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.Card;
import clue.player.Player;
import java.util.ArrayList;
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
        ShowCardsAction instance = new ShowCardsAction(new Player(0),new Player(1),new ArrayList<Card>());
        instance.execute();
        assertTrue(instance.result);
    }

    /**
     * Test of getCardList method, of class ShowCardsAction.
     */
    @Test
    public void testGetCardList() {
        System.out.println("getCardList");
        List<Card> expResult = new ArrayList<Card>();
        ShowCardsAction instance = new ShowCardsAction(new Player(0),new Player(1),expResult);
        List<Card> result = instance.getCardList();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSuggester method, of class ShowCardsAction.
     */
    @Test
    public void testGetSuggester() {
        System.out.println("getSuggester");
        Player player = new Player(1);
        ShowCardsAction instance = new ShowCardsAction(new Player(0), player, new ArrayList<Card>());
        Player expResult = player;
        Player result = instance.getSuggester();
        assertEquals(expResult, result);
    }
    
}

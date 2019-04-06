/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

import clue.action.Action;
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
public class IntrigueCardTest {
    
    public IntrigueCardTest() {
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

    public class IntrigueCardImpl extends IntrigueCard {

        public IntrigueCardImpl() {
            super(0);
        }
    }

    /**
     * Test of getCardType method, of class IntrigueCard.
     */
    @Test
    public void testGetCardType() {
        System.out.println("getCardType");
        IntrigueCard instance = null;
        CardType expResult = null;
        CardType result = instance.getCardType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

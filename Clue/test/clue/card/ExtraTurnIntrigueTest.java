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
public class ExtraTurnIntrigueTest {
    
    public ExtraTurnIntrigueTest() {
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
     * Test of getid method, of class ExtraTurnIntrigue.
     */
    @Test
    public void testGetid() {
        System.out.println("getid");
        ExtraTurnIntrigue instance = new ExtraTurnIntrigue(0);
        int expResult = 0;
        int result = instance.getid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCardType method, of class ExtraTurnIntrigue.
     */
    @Test
    public void testGetCardType() {
        System.out.println("getCardType");
        ExtraTurnIntrigue instance = null;
        CardType expResult = null;
        CardType result = instance.getCardType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

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

    /**
     * Test of doSpecial method, of class IntrigueCard.
     */
    @Test
    public void testDoSpecial() {
        System.out.println("doSpecial");
        IntrigueCard instance = new IntrigueCardImpl();
        Action expResult = null;
        Action result = instance.doSpecial();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IntrigueCardImpl implements IntrigueCard {

        public Action doSpecial() {
            return null;
        }

        @Override
        public int getid() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public class IntrigueCardImpl implements IntrigueCard {

        public Action doSpecial() {
            return null;
        }
    }

    public class IntrigueCardImpl implements IntrigueCard {

        public Action doSpecial() {
            return null;
        }
    }

    public class IntrigueCardImpl implements IntrigueCard {

        public Action doSpecial() {
            return null;
        }
    }

    public class IntrigueCardImpl implements IntrigueCard {

        public Action doSpecial() {
            return null;
        }
    }
    
}

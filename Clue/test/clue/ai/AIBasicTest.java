/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.ai;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zemig
 */
public class AIBasicTest {
    
    public AIBasicTest() {
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
     * Test of onUpdate method, of class AIBasic.
     */
//    @Test
//    public void testOnUpdate() {
//        System.out.println("onUpdate");
//        AIBasic instance = null;
//        instance.onUpdate();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    @Test
    public void testConstructor(){
        AIBasic aItest = new AIBasic(1);
        int expInt = 1;
        int id = aItest.getId();
        assertEquals(id, expInt);
    }
    
}

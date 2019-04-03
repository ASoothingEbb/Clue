/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

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
public class SubjectTest {
    
    public SubjectTest() {
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
     * Test of register method, of class Subject.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        Observer observer = null;
        Subject instance = new SubjectImpl();
        instance.register(observer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unregister method, of class Subject.
     */
    @Test
    public void testUnregister() {
        System.out.println("unregister");
        Observer observer = null;
        Subject instance = new SubjectImpl();
        instance.unregister(observer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of notifyAllObservers method, of class Subject.
     */
    @Test
    public void testNotifyAllObservers() {
        System.out.println("notifyAllObservers");
        Subject instance = new SubjectImpl();
        instance.notifyAllObservers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class SubjectImpl implements Subject {

        public void register(Observer observer) {
        }

        public void unregister(Observer observer) {
        }

        public void notifyAllObservers() {
        }
    }

    public class SubjectImpl implements Subject {

        public void register(Observer observer) {
        }

        public void unregister(Observer observer) {
        }

        public void notifyAllObservers() {
        }
    }

    public class SubjectImpl implements Subject {

        public void register(Observer observer) {
        }

        public void unregister(Observer observer) {
        }

        public void notifyAllObservers() {
        }
    }

    public class SubjectImpl implements Subject {

        public void register(Observer observer) {
        }

        public void unregister(Observer observer) {
        }

        public void notifyAllObservers() {
        }
    }
    
}

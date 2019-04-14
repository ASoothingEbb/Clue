/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.scene.paint.Color;
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
public class MenuItemTest {
    
    public MenuItemTest() {
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
     * Test of setActive method, of class MenuItem.
     */
    @Test
    public void testSetActive() {
        System.out.println("setActive");
        boolean active = false;
        MenuItem instance = null;
        instance.setActive(active);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActiveColor method, of class MenuItem.
     */
    @Test
    public void testSetActiveColor() {
        System.out.println("setActiveColor");
        Color color = null;
        MenuItem instance = null;
        instance.setActiveColor(color);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInactiveColor method, of class MenuItem.
     */
    @Test
    public void testSetInactiveColor() {
        System.out.println("setInactiveColor");
        Color color = null;
        MenuItem instance = null;
        instance.setInactiveColor(color);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

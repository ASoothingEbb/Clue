/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import clue.GameController;
import clue.action.Action;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
public class gameInstanceTest {
    
    public gameInstanceTest() {
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
     * Test of createLeftPanel method, of class gameInstance.
     */
    @Test
    public void testCreateLeftPanel() {
        System.out.println("createLeftPanel");
        gameInstance instance = new gameInstance();
        VBox expResult = null;
        VBox result = instance.createLeftPanel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createCardsDisplay method, of class gameInstance.
     */
    @Test
    public void testCreateCardsDisplay() {
        System.out.println("createCardsDisplay");
        gameInstance instance = new gameInstance();
        GridPane expResult = null;
        GridPane result = instance.createCardsDisplay();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionResponse method, of class gameInstance.
     */
    @Test
    public void testActionResponse() {
        System.out.println("actionResponse");
        Action action = null;
        gameInstance instance = new gameInstance();
        instance.actionResponse(action);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startGame method, of class gameInstance.
     */
    @Test
    public void testStartGame() {
        System.out.println("startGame");
        GameController gameController = null;
        gameInstance instance = new gameInstance();
        instance.startGame(gameController);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

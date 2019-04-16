/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

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
public class ActionTypeTest {

    public ActionTypeTest() {
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
     * Test of values method, of class ActionType.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        ActionType[] expResult = {ActionType.DEFAULT, ActionType.ACCUSATION, ActionType.AVOIDSUGGESTIONCARD, ActionType.ENDTURN, ActionType.EXTRATURN, ActionType.MOVE, ActionType.MOVEAGAIN, ActionType.SHOWCARD, ActionType.SHOWCARDS, ActionType.START, ActionType.STARTTURN, ActionType.SUGGEST, ActionType.TELEPORT, ActionType.THROWAGAIN};
        ActionType[] result = ActionType.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class ActionType.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "DEFAULT";
        ActionType expResult = ActionType.DEFAULT;
        ActionType result = ActionType.valueOf(name);
        assertEquals(expResult, result);
    }

}

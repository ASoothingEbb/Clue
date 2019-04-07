/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steve
 */
public class CardTypeTest {
    
    public CardTypeTest() {
    }

    /**
     * Test of values method, of class CardType.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        CardType[] expResult = null;
        CardType[] result = CardType.values();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valueOf method, of class CardType.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String arg0 = "";
        CardType expResult = null;
        CardType result = CardType.valueOf(arg0);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

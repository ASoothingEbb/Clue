/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
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
public class AccuseActionTest {
    
    public AccuseActionTest() {
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
     * Test of execute method, of class AccuseAction.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        PersonCard person = null;
        RoomCard room = null;
        WeaponCard weapon = null;
        AccuseAction instance = null;
        instance.execute(person, room, weapon);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

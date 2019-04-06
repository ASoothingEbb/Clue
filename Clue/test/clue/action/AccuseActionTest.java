/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.card.PersonCard;
import clue.card.RoomCard;
import clue.card.WeaponCard;
import clue.player.AIPlayer;
import clue.player.Player;
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
        PersonCard person = new PersonCard(1);
        RoomCard room = new RoomCard(1);
        WeaponCard weapon = new WeaponCard(1);
        Player player = new AIPlayer(1);
        AccuseAction instance = new AccuseAction(player, person, room, weapon);
        instance.execute(person, room, weapon);
        // TODO review the generated test code and remove the default call to fail.
        boolean expResult = true;
        boolean result = instance.result;
        assertEquals(expResult, result);
        assertEquals(false,player.isActive());
    }
    
}

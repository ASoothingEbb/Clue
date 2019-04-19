/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import clue.GameController;
import clue.MissingRoomDuringCreationException;
import clue.player.Player;
import clue.tile.NoSuchRoomException;
import clue.tile.NoSuchTileException;
import clue.tile.TileOccupiedException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class StartTurnActionTest {

    public StartTurnActionTest() {
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

    @Test
    public void testExecute() throws Exception {
        GameController gc = null;
        gc = new GameController(1,1,"resources/archersAvenueTiles.csv", "resources/archersAvenueDoors.csv");

        Player player = new Player(77);
        StartTurnAction instance = new StartTurnAction(player);
        gc.performAction(instance);
        assertTrue(instance.result);
        assertNotEquals(gc.getPlayer().getId(), player.getId());
    }

}

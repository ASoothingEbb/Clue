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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author steve
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({clue.player.PlayerSuite.class, clue.ai.AiSuite.class, clue.SubjectTest.class, clue.action.ActionSuite.class, clue.card.CardSuite.class, clue.ObserverTest.class, clue.GameStateTest.class, clue.ClueTest.class, clue.GameControllerTest.class, clue.tile.TileSuite.class})
public class ClueSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}

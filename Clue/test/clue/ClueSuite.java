/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import clue.ai.AiSuite;
import clue.action.ActionSuite;
import clue.card.CardSuite;
import clue.gameConstructor.GameConstructorSuite;
import clue.player.PlayerSuite;
import clue.tile.TileSuite;
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
@Suite.SuiteClasses({PlayerSuite.class, SubjectTest.class, AiSuite.class, GameConstructorSuite.class, ActionSuite.class, CardSuite.class, ObserverTest.class, GameStateTest.class, ClueTest.class, GameControllerTest.class, TileSuite.class})
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

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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author steve
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ShowCardActionTest.class, StartActionTest.class, TeleportActionTest.class, AccuseActionTest.class, AvoidSuggestionActionTest.class, UnknownActionExceptionTest.class, ActionTest.class, EndTurnActionTest.class, KickActionTest.class, ShowCardsActionTest.class, StartTurnActionTest.class, ThrowAgainActionTest.class, SuggestActionTest.class, MoveActionTest.class, ExtraTurnActionTest.class, ActionTypeTest.class})
public class ActionSuite {

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

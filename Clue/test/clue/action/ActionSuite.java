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
@Suite.SuiteClasses({clue.action.ShowCardActionTest.class, clue.action.StartActionTest.class, clue.action.AccusationActionTest.class, clue.action.AvoidSuggestionActionTest.class, clue.action.UnknownActionExceptionTest.class, clue.action.ActionTest.class, clue.action.EndTurnActionTest.class, clue.action.KickActionTest.class, clue.action.StartTurnActionTest.class, clue.action.ThrowAgainActionTest.class, clue.action.SuggestActionTest.class, clue.action.MoveActionTest.class, clue.action.ExtraTurnActionTest.class, clue.action.ActionTypeTest.class})
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

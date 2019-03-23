/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.action;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author slb35
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({clue.action.ExtraTurnActionTest.class, clue.action.ActionTest.class, clue.action.ThrowAgainActionTest.class, clue.action.AccusationActionTest.class, clue.action.MoveActionTest.class, clue.action.SuggestActionTest.class, clue.action.AvoidSuggestionActionTest.class, clue.action.ShowCardActionTest.class})
public class ActionSuite {
    
}

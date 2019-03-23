/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author slb35
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({clue.action.ActionSuite.class, clue.ClueTest.class, clue.tile.TileSuite.class, clue.ObserverTest.class, clue.ai.AiSuite.class, clue.SubjectTest.class, clue.GameStateTest.class, clue.card.CardSuite.class, clue.player.PlayerSuite.class, clue.GameControllerTest.class})
public class ClueSuite {
    
}

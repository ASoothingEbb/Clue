/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.player;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author slb35
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({clue.player.AIPlayerTest.class, clue.player.NetworkPlayerTest.class, clue.player.HostPlayerTest.class, clue.player.PlayerTest.class})
public class PlayerSuite {
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

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
@Suite.SuiteClasses({clue.client.MenuItemTest.class, clue.client.TileTest.class, clue.client.BoardTest.class, clue.client.SpecialCardTest.class, clue.client.PromptTest.class, clue.client.PlayerTest.class, clue.client.SettingsMenuItemTest.class, clue.client.ClueClientTest.class})
public class ClientSuite {

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

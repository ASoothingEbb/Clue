/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.ai;

import clue.GameController;
import clue.action.UnknownActionException;
import clue.card.Card;
import clue.card.WeaponCard;
import clue.player.AIPlayer;
import clue.ai.AIAdvanced;
import clue.card.RoomCard;
import clue.player.Player;
import clue.tile.Room;
import clue.tile.Tile;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zemig
 */
public class AIAdvancedTest {
    
    public AIAdvancedTest() {
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
     * Test of onUpdate method, of class AIAdvanced.
     * @throws java.lang.InterruptedException
     * @throws clue.action.UnknownActionException
     */
//    @Test
//    public void testOnUpdate() {
//        System.out.println("onUpdate");
//        AIAdvanced instance = null;
//        instance.onUpdate();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findNewRoom method, of class AIAdvanced.
//     */
//    @Test
//    public void testFindNewRoom() {
//        System.out.println("findNewRoom");
//        AIAdvanced instance = null;
//        instance.findNewRoom();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    @Test
    public void testMakeLists() throws InterruptedException, UnknownActionException{
        System.out.println("makeListTest");
        Player p1 = new AIAdvanced(1, 0, 0);
        Player p2 = new AIAdvanced(2, 0 ,0);
        Player p3 = new AIAdvanced(3, 0, 0);
        
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<ArrayList<Card>> testList;
        
        GameController game = new GameController(players);
        
        Card c1 = new WeaponCard(1);
        
        game.getPlayer(1).addCard(c1);
        
       // p1.makeLists();
        
       // testList = p2.getLists();
        
        //System.out.println(testList);
        
        
        
        
    }
    
    @Test
    public void testBFS(){
        //Layout of map
        // t1 t2 t3
        // t4    t6
        //       r1
        
        ArrayList<Tile> testPath = new ArrayList<>();
        
        Tile t1 = new Tile(0, 0);
        Tile t2 = new Tile(1, 0);
        Tile t3 = new Tile(2, 0);
        Tile t4 = new Tile(0, 1);
        Room r1 = new Room(new RoomCard(1));
        Tile t6 = new Tile(2, 1);
   
        t1.addAdjacentBoth(t2);
        t1.addAdjacentBoth(t4);
        t2.addAdjacentBoth(t3);
        t3.addAdjacentBoth(t6);
        t6.addAdjacentBoth(r1);
        
        AIAdvanced testPlayer = new AIAdvanced(1, 3, 3);
        
        testPlayer.setPosition(t1);
        
        testPath = testPlayer.BFS();
        
        for(Tile tile: testPath){
            System.out.println(tile.getX()+ " " + tile.getY());
        }
    }
    
}

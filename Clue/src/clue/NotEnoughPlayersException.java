/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

/**
 * Exception for when a GameController refused to start the game because starting player count was less than 2
 * @author Malter
 */
public class NotEnoughPlayersException extends Exception {

    public NotEnoughPlayersException() {
    }
    
}

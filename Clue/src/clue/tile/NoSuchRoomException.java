/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.tile;

/**
 *
 * @author Malter
 */
public class NoSuchRoomException extends Exception {

    /**
     * Creates a noSuchRoomException
     * @param message the exception message
     */
    public NoSuchRoomException(String message) {
        super(message);
    }
    
    public NoSuchRoomException() {
    }
    
}

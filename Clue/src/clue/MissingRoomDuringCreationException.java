/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

/**
 *
 * @author Malter
 */
public class MissingRoomDuringCreationException extends Exception {

    public MissingRoomDuringCreationException(String message) {
        super(message);
    }
    
    public MissingRoomDuringCreationException() {
    }
    
}

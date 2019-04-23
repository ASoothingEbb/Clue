/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

/**
 * Exception for when BoardMappings failed to create a board because the csv had a room id of N, but it was missing a room id that was less than N (board must contain all room ids from 1 to N)
 * @author Malter
 */
public class MissingRoomDuringCreationException extends Exception {

    public MissingRoomDuringCreationException(String message) {
        super(message);
    }
    
    public MissingRoomDuringCreationException() {
    }
    
}

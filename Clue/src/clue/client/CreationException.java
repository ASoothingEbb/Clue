/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

/**
 *
 * @author hungb
 */
public class CreationException extends Exception {
    public String message;
    
    /**
     * Creates a Creation Exception
     * @param message the exception message
     */
    public CreationException(String message) {
        super(message);
        this.message = message;
    }
}

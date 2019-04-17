/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.scene.image.Image;

/**
 *  How cards are represented in the gui. 
 * 
 * @author hungb
 */
public class SpecialCard extends Card {
    
    /**
     * 
     * @param image whatt the card looks like.
     * @param type
     * @param cardID 
     */
    public SpecialCard(Image image, String type, int cardID) {
        super(image, type, cardID);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.scene.image.Image;

/**
 *
 * @author hungb
 */
public class Card {
    
    private Image image;
    private String type;
    private int cardID;
    
    public Card(Image image, String type, int cardID) {
        this.image = image;
        this.type = type;
        this.cardID = cardID;
    }
    
    public Image getImage() {
        return this.image;
    }
}

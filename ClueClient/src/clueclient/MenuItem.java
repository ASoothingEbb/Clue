/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clueclient;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author hungb
 */
public class MenuItem extends Label {

    public MenuItem(String name, Font font) {
        super(name);
        setAlignment(Pos.CENTER);
        
        setFont(font);
        
        setActive(false);
        
        hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                setActive(true);
            } else {
                setActive(false);
            }
        });
    }
    
    public void setActive(boolean active) {
        setTextFill(active ? Color.WHITE : Color.GREY);
    }
    
    
}

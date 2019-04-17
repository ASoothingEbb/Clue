/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author hungb
 */
public class MenuItem extends Label {
    private Color activeColor;
    private Color inactiveColor;

    /**
     * MenuItem is a Label which has a hoverProperty.
     * 
     * @param name the text displayed in the label.
     * @param font  the font that is used for all text in this label.
     */
    public MenuItem(String name, Font font) {
        super(name);
        setAlignment(Pos.CENTER);
        
        setFont(font);
        
        activeColor = Color.WHITE;
        inactiveColor = Color.LIGHTGREY;
        
        setActive(false);
        
        hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (newValue) {
                setActive(true);
            } else {
                setActive(false);
            }
        });
    }
    /**
     * Sets the label to either is active colour or its inactive colour.
     * 
     * @param active true if you want label active, false otherwise 
     */
    public void setActive(boolean active) {
        setTextFill(active ? activeColor : inactiveColor);
    }
    
    /**
     * Sets the colour which the label is when active
     * 
     * @param color Colour which you want the label to have when it is active.
     */
    public void setActiveColor(Color color) {
        this.activeColor = color;
        //refresh colour state
        setActive(false);
    }
    
     /**
     * Sets the colour which the label is when inactive
     * 
     * @param color Colour which you want the label to have when it is inactive.
     */
    public void setInactiveColor(Color color) {
        this.inactiveColor = color;
        //refresh colour state
        setActive(false);
    }
  
    /**
     * Sets the background color of the label.
     * 
     * @param color Colour which you want the label to be.
     */
    public void setBackgroundColor(Color color) {
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}

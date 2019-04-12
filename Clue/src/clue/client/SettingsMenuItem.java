/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.client;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author hungb
 */
public class SettingsMenuItem extends MenuItem {
    
    private final Background backgroundActive;
    private final Background backgroundInactive;
    private boolean toggled; 
    
    public SettingsMenuItem(String name, Font font, Color active) {
        super(name, font);
        
        toggled = false;
        
        this.backgroundActive = new Background(new BackgroundFill(active, CornerRadii.EMPTY, Insets.EMPTY));
        this.backgroundInactive = new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY));
        
        setOnMouseClicked((MouseEvent e) -> {
            if (toggled) {
                toggled = false;
                setBackgroundActive(false);
            } else {
                toggled = true;
                setBackgroundActive(true);
            }
        });
    }
    
    
    private void setBackgroundActive(boolean active) {
        setBackground(active ? backgroundActive : backgroundInactive);
        setTextFill(active ? Color.WHITE : Color.GREY);
    }
    
    public void setToggled(boolean toggle) {
        this.toggled = toggle;
    }
}

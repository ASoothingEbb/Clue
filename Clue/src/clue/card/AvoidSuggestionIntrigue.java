/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

import clue.action.Action;

/**
 *Represents an IntrigueCard that allows players to avoid responding to a 
 *suggestion.
 * @author slb35
 */
public class AvoidSuggestionIntrigue extends Card implements IntrigueCard{

    public AvoidSuggestionIntrigue(int id) {
        super(id);
    }

    
    @Override
    public Action doSpecial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

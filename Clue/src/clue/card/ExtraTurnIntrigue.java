/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue.card;

import clue.action.Action;

/**
 *Represents an IntrigueCard that allows players to take an extra turn.
 * @author slb35
 */
public class ExtraTurnIntrigue extends Card implements IntrigueCard{

    public ExtraTurnIntrigue(int id) {
        super(id);
    }

    
    @Override
    public Action doSpecial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

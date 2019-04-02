/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

/**
 *An interface representing an Observer. The Observer should be registered to a
 * subject that will issue updates.
 * @author slb35
 */
public interface Observer {
    /**
     * Represents the Observer's reaction to a subject update. This should only
     * be called by the Subject's NotifyAllObservers() method.
     */
    public void onUpdate();
}

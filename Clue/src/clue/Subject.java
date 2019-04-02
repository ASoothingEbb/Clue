/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

/**
 *Represents a Subject. The subject should be observed by Observers.
 * @author slb35
 */
public interface Subject {
    /**
     * Registers an Observer to watch this subject.
     * @param observer the Observer to register
     */
    public void register(Observer observer);
    /**
     * De-registers an Observer from this subject
     * @param observer the Observer to de-register
     */
    public void unregister(Observer observer);
    /**
     * issues an update to all Observers watching this Subject. Should call each
     * Observer's onUpdate() method.
     */
    public void notifyAllObservers();
}

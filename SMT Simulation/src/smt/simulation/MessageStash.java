/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smt.simulation;

/**
 *
 * @author Tim
 */
public abstract class MessageStash {
    abstract boolean isEmpty();
    abstract String poll();
    abstract void add(String message);    
}

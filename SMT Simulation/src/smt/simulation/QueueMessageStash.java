/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smt.simulation;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Tim
 */
public class QueueMessageStash extends MessageStash{
    
    Queue<String> messageQueue = new ConcurrentLinkedQueue<String>();

    @Override
    boolean isEmpty() {
        return messageQueue.isEmpty();
    }

    @Override
    String poll() {
        return messageQueue.poll();
    }

    @Override
    void add(String message) {
        messageQueue.add(message);
    }
    
}

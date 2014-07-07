/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smt.simulation;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tim
 */
public class SMTSimulation extends Thread {

    static MessageStash messageStash;

    public void run() {
        boolean quit = false;
        while (!quit) {
            if (!messageStash.isEmpty()) {
                System.out.println("Message recieved: " + messageStash.poll());
            }
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                quit = true;
            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        messageStash = new FileMessageStash();
        (new SMTSimulation()).start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            messageStash.add(scanner.nextLine());
        }

    }
}

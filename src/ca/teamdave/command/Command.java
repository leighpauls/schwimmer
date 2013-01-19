/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.teamdave.command;

import ca.teamdave.RobotInterface;

/**
 *
 * @author leighpauls
 */
public interface Command {
    
    void runCommandStep(RobotInterface robot);
    boolean isDone();
    
}

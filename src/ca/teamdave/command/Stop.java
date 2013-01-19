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
public class Stop implements Command {

    public void runCommandStep(RobotInterface robot) {
        robot.setDrive(0.0, 0.0);
    }

    public boolean isDone() {
        return false;
    }
    
}
